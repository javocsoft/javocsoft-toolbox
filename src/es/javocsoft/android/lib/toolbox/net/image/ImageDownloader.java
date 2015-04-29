/*
 * Copyright (C) 2010-2015 - JavocSoft - Javier Gonzalez Serrano
 * http://javocsoft.es/proyectos/code-libs/android/javocsoft-toolbox-android-library
 * 
 * This file is part of JavocSoft Android Toolbox library.
 *
 * JavocSoft Android Toolbox library is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 *
 * JavocSoft Android Toolbox library is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General 
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavocSoft Android Toolbox library.  If not, see 
 * <http://www.gnu.org/licenses/>.
 * 
 */
package es.javocsoft.android.lib.toolbox.net.image;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * A class to download images in background.
 * 
 * @author JavocSoft 2015
 * @since  2015
 */
public class ImageDownloader {

	private static ImageDownloader imageDownloader = null;
	
	private DiskLruCache mDiskCache;
	private MemoryLruCache<String, Bitmap> mMemoryCache;

	
	/**
	 * Gets an instance of ImageDownloader.
	 * 
	 * @param context
	 * @return
	 */
	public static ImageDownloader getInstance(Context context) {
		if(imageDownloader==null)
			imageDownloader = new ImageDownloader(context);
		
		return imageDownloader;
	}
	
	
	private ImageDownloader(Context context) {
		//Initializes the required caches. 
		mMemoryCache = new MemoryLruCache<String, Bitmap>(20);
		mDiskCache = DiskLruCache.openCache(context, DiskLruCache.getDiskCacheDir(context, "images"), (1024 * 1024 * 1));
	}

	
	/**
	 * Downloads in background an image from an URL and puts
	 * it in the specified ImageView once is fully downloaded.
	 * 
	 * @param url	The URL to the image.
	 * @param imageView	Where to put the image.
	 */
	public void download(String url, ImageView imageView) {
		ImageDownloaderTask task;
		Bitmap bitmapReference;

		// Avoid unnecessary work.
		if ((url == null) || (url.length() == 0)) {
			return;
		}

		if (cancelPotentialDownload(url, imageView)) {
			bitmapReference = mMemoryCache.get(url);
			if (bitmapReference != null) {
				if (imageView != null) {
					imageView.setImageBitmap(bitmapReference);
					imageView.setVisibility(View.VISIBLE);
				}
			} else {
				task = new ImageDownloaderTask(url, imageView);
				if (imageView != null) {
					imageView.setImageDrawable(new DownloadedDrawable(task));
				}
				task.execute();
			}
		}
	}

	
	
	//AUXILIAR
	
	private static String getCacheFilename(String url) {
		return String.valueOf(url.hashCode());
	}
	
	private boolean cancelPotentialDownload(String url, ImageView imageView) {
		ImageDownloaderTask imageDownloaderTask = getImageDownloaderTask(imageView);

		if (imageDownloaderTask != null) {
			String bitmapUrl = imageDownloaderTask.url;
			if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
				imageDownloaderTask.cancel(true);
			} else {
				// The same URL is already being downloaded.
				return false;
			}
		}
		return true;
	}

	
	private Bitmap downloadBitmap(String url) {
		final Bitmap bitmap;
		final int statusCode;
		final AndroidHttpClient client;
		final HttpGet request;
		final HttpResponse response;
		final HttpEntity entity;
		InputStream is = null;

		client = AndroidHttpClient.newInstance("Android");
		request = new HttpGet(url);
		try {
			response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			entity = response.getEntity();
			if (entity != null) {
				try {
					is = entity.getContent();
					bitmap = BitmapFactory.decodeStream(new FlushedInputStream(is));
					if (bitmap != null) {
						mMemoryCache.put(url, bitmap);
						if (mDiskCache != null) {
							mDiskCache.put(getCacheFilename(url), bitmap);
						}
					}
					return bitmap;
				} finally {
					if (is != null) {
						is.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// We could provide a better or explicit error message 
			// for IOException or IllegalStateException.
			request.abort();
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}

	private ImageDownloaderTask getImageDownloaderTask(ImageView imageView) {
		Drawable drawable;

		if (imageView != null) {
			drawable = imageView.getDrawable();
			if (drawable instanceof DownloadedDrawable) {
				return ((DownloadedDrawable) drawable).getBitmapDownloaderTask();
			}
		}
		return null;
	}
	
	
	//AUXILIAR CLASSES
	
	private static class DownloadedDrawable extends ColorDrawable {

		private final WeakReference<ImageDownloaderTask> bitmapDownloaderTaskReference;

		public DownloadedDrawable(ImageDownloaderTask bitmapDownloaderTask) {
			super(Color.WHITE);
			this.bitmapDownloaderTaskReference = new WeakReference<ImageDownloaderTask>(bitmapDownloaderTask);
		}

		public ImageDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference.get();
		}
	}

	private static class FlushedInputStream extends FilterInputStream {
		/*
		 * Note: 
		 * 	Due to a bug in the previous versions of 
		 * 	BitmapFactory.decodeStream this code from working over a 
		 * 	slow connection.
		 */
		
		public FlushedInputStream(InputStream is) {
			super(is);
		}

		@Override
		public long skip(long n) throws IOException {
			int singleByte;
			long bytesSkipped;
			long totalBytesSkipped = 0L;

			while (totalBytesSkipped < n) {
				bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					singleByte = read();
					if (singleByte < 0) {
						// EOF reached.
						break;
					} else {
						// One byte read.
						bytesSkipped = 1;
					}
				}
				totalBytesSkipped += bytesSkipped;
			}

			return totalBytesSkipped;
		}
	}

	private class ImageDownloaderTask extends AsyncTask<Void, Void, Bitmap> {

		private final WeakReference<ImageView> imageViewReference;

		private final String url;

		public ImageDownloaderTask(String url, ImageView imageView) {
			this.url = url;
			this.imageViewReference = (imageView != null) ? new WeakReference<ImageView>(imageView) : null;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			Bitmap bitmap;

			if (mDiskCache != null) {
				bitmap = mDiskCache.get(getCacheFilename(url));
				if (bitmap != null) {
					mMemoryCache.put(url, bitmap);
					return bitmap;
				}
			}
			return downloadBitmap(url);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			Animation anim;
			ImageView imageView;

			if (isCancelled()) {
				bitmap = null;
			}
			// Please note that if imageView is null, imageViewReference is also null.
			if (imageViewReference != null) {
				imageView = imageViewReference.get();
				if (imageView != null) {
					anim = new AlphaAnimation(0, 1);
					anim.setDuration(400);
					imageView.setImageBitmap(bitmap);
					imageView.startAnimation(anim);
					imageView.setVisibility(View.VISIBLE);
				}
			}
		}
	}

}