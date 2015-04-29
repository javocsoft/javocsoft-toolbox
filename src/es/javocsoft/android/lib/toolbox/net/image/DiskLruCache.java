/*
 * Copyright (C) 2010-2014 - JavocSoft - Javier Gonzalez Serrano
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import es.javocsoft.android.lib.toolbox.ToolBox;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * A simple disk LRU bitmap cache used for bitmap caching. 
 * 
 * @author JavocSoft 2015
 * @since  2015
 */
public class DiskLruCache {

	private static final String TAG = "DiskLruCache";
	
	private static final String CACHE_FILENAME_PREFIX = "cache_";
	
	private static final int INITIAL_CAPACITY = 32;

	private static final int IO_BUFFER_SIZE = 8 * 1024;
	private static final float LOAD_FACTOR = 0.75f;
	private static final int MAX_REMOVALS = 4;
	
	
	/**
	 * A filename filter to use to identify the cache filenames which 
	 * have CACHE_FILENAME_PREFIX prepended.
	 */
	private static final FilenameFilter cacheFileFilter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String filename) {
			return filename.startsWith(CACHE_FILENAME_PREFIX);
		}
	};

	/**
	 * Removes all disk cache entries from the application cache directory in 
	 * the uniqueName sub-directory.
	 * 
	 * @param context	The context to use
	 * @param uniqueName	A unique cache directory name to append to the 
	 * 						app cache directory.
	 */
	public static void clearCache(Context context, String uniqueName) {
		File cacheDir = getDiskCacheDir(context, uniqueName);
		clearCache(cacheDir);
	}

	/**
	 * Creates a constant cache file path given a target cache directory and 
	 * an image key.
	 * 
	 * @param cacheDir
	 * @param key
	 * @return
	 */
	public static String createFilePath(File cacheDir, String key) {
		try {
			// Use URLEncoder to ensure we have a valid filename
			return cacheDir.getAbsolutePath() + File.separator + CACHE_FILENAME_PREFIX + URLEncoder.encode(key.replace("*", ""), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			Log.e(TAG, "createFilePath - " + e);
		}
		return null;
	}

	/**
	 * Get a usable cache directory (external if available, internal 
	 * otherwise).
	 * 
	 * If using external storage, the permission called 
	 * "android.permission.WRITE_EXTERNAL_STORAGE". You can use in the
	 * permission android:maxSdkVersion="18" to make only requested in 
	 * Android version minor to 18.
	 * 
	 * @param context		The context to use
	 * @param uniqueName	A unique directory name to append to the cache dir
	 * @return The cache dir
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		// Check if media is mounted or storage is built-in, 
		//	- If so, try and use external cache dir
		// 	- Otherwise use internal cache dir
		String cachePath = null;
		if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
			cachePath = context.getExternalCacheDir().getAbsolutePath();			
		}else{
			cachePath = context.getCacheDir().getPath();
		}
		
		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * Used to fetch an instance of DiskLruCache.
	 * 
	 * @param context
	 * @param cacheDir
	 * @param maxByteSize
	 * @return
	 */
	public static DiskLruCache openCache(Context context, File cacheDir, long maxByteSize) {
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}
		final StatFs stats = new StatFs(cacheDir.getPath());
		long usableSpace = (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
		if (cacheDir.isDirectory() && cacheDir.canWrite() && usableSpace > maxByteSize) {
			return new DiskLruCache(cacheDir, maxByteSize);
		}

		return null;
	}

	/**
	 * Removes all disk cache entries from the given directory. This should 
	 * not be called directly, 
	 * call {@link DiskLruCache#clearCache(Context, String)} or 
	 * {@link DiskLruCache#clearCache()} instead.
	 * 
	 * @param cacheDir
	 *          The directory to remove the cache files from
	 */
	private static void clearCache(File cacheDir) {
		final File[] files = cacheDir.listFiles(cacheFileFilter);
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

	private int cacheByteSize = 0;
	private int cacheSize = 0;
	private long maxCacheByteSize = 1024 * 1024 * 5; // 5MB default
	private final int maxCacheItemSize = 64; // 64 item default
	private final File mCacheDir;
	private CompressFormat mCompressFormat = CompressFormat.JPEG;
	private int mCompressQuality = 100;

	private final Map<String, String> mLinkedHashMap = Collections.synchronizedMap(new LinkedHashMap<String, String>(INITIAL_CAPACITY,
			LOAD_FACTOR, true));

	/**
	 * Constructor that should not be called directly, instead 
	 * use {@link DiskLruCache#openCache(Context, File, long)} which runs 
	 * some extra checks before creating a DiskLruCache instance.
	 * 
	 * @param cacheDir
	 * @param maxByteSize
	 */
	private DiskLruCache(File cacheDir, long maxByteSize) {
		mCacheDir = cacheDir;
		maxCacheByteSize = maxByteSize;
	}

	/**
	 * Removes all disk cache entries from this instance cache dir
	 */
	public void clearCache() {
		DiskLruCache.clearCache(mCacheDir);
	}

	/**
	 * Checks if a specific key exist in the cache.
	 * 
	 * @param key
	 *          The unique identifier for the bitmap
	 * @return true if found, false otherwise
	 */
	public boolean containsKey(String key) {
		// See if the key is in our HashMap
		if (mLinkedHashMap.containsKey(key)) {
			return true;
		}

		// Now check if there's an actual file that exists based on the key
		final String existingFile = createFilePath(mCacheDir, key);
		if (new File(existingFile).exists()) {
			// File found, add it to the HashMap for future use
			put(key, existingFile);
			return true;
		}
		return false;
	}

	/**
	 * Create a constant cache file path using the current cache directory 
	 * and an image key.
	 * 
	 * @param key
	 * @return
	 */
	public String createFilePath(String key) {
		return createFilePath(mCacheDir, key);
	}

	/**
	 * Get an image from the disk cache.
	 * 
	 * @param key
	 *          The unique identifier for the bitmap
	 * @return The bitmap or null if not found
	 */
	public Bitmap get(String key) {
		synchronized (mLinkedHashMap) {
			final String file = mLinkedHashMap.get(key);
			if (file != null) {
				return BitmapFactory.decodeFile(file);
			} else {
				final String existingFile = createFilePath(mCacheDir, key);
				if (new File(existingFile).exists()) {
					put(key, existingFile);					
					return BitmapFactory.decodeFile(existingFile);
				}
			}
			return null;
		}
	}

	/**
	 * Add a bitmap to the disk cache.
	 * 
	 * @param key
	 *          A unique identifier for the bitmap.
	 * @param data
	 *          The bitmap to store.
	 */
	public void put(String key, Bitmap data) {
		synchronized (mLinkedHashMap) {
			if (mLinkedHashMap.get(key) == null) {
				try {
					final String file = createFilePath(mCacheDir, key);
					if (writeBitmapToFile(data, file)) {
						put(key, file);
						flushCache();
					}
				} catch (final FileNotFoundException e) {
					Log.e(ToolBox.TAG + "(" + TAG + ")", "Error in put: " + e.getMessage());
				} catch (final IOException e) {
					Log.e(ToolBox.TAG + "(" + TAG + ")", "Error in put: " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Sets the target compression format and quality for images written 
	 * to the disk cache.
	 * 
	 * @param compressFormat
	 * @param quality
	 */
	public void setCompressParams(CompressFormat compressFormat, int quality) {
		mCompressFormat = compressFormat;
		mCompressQuality = quality;
	}

	/**
	 * Flush the cache, removing oldest entries if the total size is over 
	 * the specified cache size. Note that this isn't keeping track of
	 * stale files in the cache directory that aren't in the HashMap. If 
	 * the images and keys in the disk cache change often then they probably
	 * won't ever be removed.
	 */
	private void flushCache() {
		Entry<String, String> eldestEntry;
		File eldestFile;
		long eldestFileSize;
		int count = 0;

		while (count < MAX_REMOVALS && (cacheSize > maxCacheItemSize || cacheByteSize > maxCacheByteSize)) {
			eldestEntry = mLinkedHashMap.entrySet().iterator().next();
			eldestFile = new File(eldestEntry.getValue());
			eldestFileSize = eldestFile.length();
			mLinkedHashMap.remove(eldestEntry.getKey());
			eldestFile.delete();
			cacheSize = mLinkedHashMap.size();
			cacheByteSize -= eldestFileSize;
			count++;
		}
	}

	private void put(String key, String file) {
		mLinkedHashMap.put(key, file);
		cacheSize = mLinkedHashMap.size();
		cacheByteSize += new File(file).length();
	}

	/**
	 * Writes a bitmap to a file. 
	 * Call {@link DiskLruCache#setCompressParams(CompressFormat, int)} 
	 * first to set the target bitmap compression and format.
	 * 
	 * @param bitmap
	 * @param file
	 * @return
	 */
	private boolean writeBitmapToFile(Bitmap bitmap, String file) throws IOException, FileNotFoundException {

		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file), IO_BUFFER_SIZE);
			return bitmap.compress(mCompressFormat, mCompressQuality, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}