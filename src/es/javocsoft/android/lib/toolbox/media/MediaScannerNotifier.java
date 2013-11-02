package es.javocsoft.android.lib.toolbox.media;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

/**
 * Allows to make a call to the Android media scanner so the file will
 * be added to the gallery :)
 * 
 * Usage:
 * 
 * 	//Adds something to the gallery
 *	String filePath = getFileStreamPath(res).getAbsolutePath();
 *	new MediaScannerNotifier(context,filePath,"image/*", false);
 * 
 */
public class MediaScannerNotifier implements MediaScannerConnectionClient { 
    private Context mContext; 
    private MediaScannerConnection mConnection; 
    private String mPath; 
    private String mMimeType; 
    private boolean showFile;

    public MediaScannerNotifier(Context context, String path, String mimeType, boolean showFile) { 
        mContext = context; 
        mPath = path; 
        mMimeType = mimeType;
        this.showFile = showFile;
        mConnection = new MediaScannerConnection(context, this); 
        mConnection.connect(); 
    } 

    public void onMediaScannerConnected() { 
        mConnection.scanFile(mPath, mMimeType); 
    } 

    public void onScanCompleted(String path, Uri uri) { 
        // OPTIONAL: scan is complete, this will cause the viewer to render it
    	try { 
            if (uri != null && showFile) { 
                Intent intent = new Intent(Intent.ACTION_VIEW); 
                intent.setData(uri); 
                mContext.startActivity(intent); 
            } 
        } finally { 
            mConnection.disconnect(); 
            mContext = null; 
        } 
    } 
} 
