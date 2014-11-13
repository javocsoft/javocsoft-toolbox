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
 * @author JavocSoft 2013
 * @version 1.0
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
