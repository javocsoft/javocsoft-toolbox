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
package es.javocsoft.android.lib.toolbox.drive.listener;

import java.io.InputStream;

import android.util.Log;

import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEventService;

import es.javocsoft.android.lib.toolbox.drive.TBDrive;

/**
 * Handles drive file change events for file subscriptions.<br><br>
 * 
 * Your application will receive a ChangeEvent whenever an file 
 * or folder gets updated by another application on the device or 
 * the Drive API downloads changes from the server.<br><br>
 * 
 * Also, whenever any change propagates to the Google Drive servers,
 * onCompletion is called to notify about the event.<br><br>
 * 
 * These notifications happens even if the application is not running.<br><br>
 * 
 * See {@link CompletionEvent} and {@link ChangeEvent}.<br><br>
 * 
 * Pending:<br> 
 * - Implement Conflicts: https://developers.google.com/drive/android/completion#conflict
 * 
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveEventService extends DriveEventService {
	
	protected ChangeEvent eventChange = null;
	protected CompletionEvent eventCompletion = null;
	
	
	public TBDriveEventService() {
		
	}

	public TBDriveEventService(String name) {
		super(name);
	}

	@Override
    public void onCompletion(CompletionEvent event) {
        Log.d(TBDrive.TAG, "Action completed for Google Drive file with status: " + event.getStatus());
        this.eventCompletion = event;
        
        //Handle completion event here.
        doOnCompletionWork();
    }
	
	@Override
    public void onChange(ChangeEvent event) {
        Log.d(TBDrive.TAG, "Google Drive File Subscription event: " + event.toString());
        this.eventChange = event;
        
        //Application-specific handling of event.
        doOnChangeWork();
    }
	
	protected abstract void doOnChangeWork();
	
	protected abstract void doOnCompletionWork();
	
	
	/**
	 * Gets the modified Drive file Metadata of the completion event. 
	 * See {@link MetadataChangeSet}.
	 * 
	 * @return	The metadata of the modified file.
	 */
	protected MetadataChangeSet getModifiedMetadata() {
		if(eventCompletion!=null)
			return eventCompletion.getModifiedMetadataChangeSet();
		else
			return null;
	}
	
	/**
	 * Gets the modified contents input stream. See {@link InputStream}.
	 * 
	 * @return	An input stream to the modified Drive File contents.
	 */
	protected InputStream getModifiedContents() {
		if(eventCompletion!=null)
			return eventCompletion.getModifiedContentsInputStream();
		else
			return null;
	}
		
	/**
	 * <b>Note</b>: 
	 * You should always dismiss events once they have been handled, or snooze them if 
	 * they should be handled later. Events that are not dismissed or snoozed could be resent 
	 * multiple times notifying your application of events that it may have already handled. 
	 * <br><br>
	 * Waking services unnecessarily is bad practice as it negatively impacts a device's battery 
	 * and memory resources.
	 */
	protected void eventDismisss() {
		if(eventCompletion!=null)
			eventCompletion.dismiss();
	}
	
	/**
	 * <b>Note</b>: 
	 * You should always dismiss events once they have been handled, or snooze them if 
	 * they should be handled later. Events that are not dismissed or snoozed could be resent 
	 * multiple times notifying your application of events that it may have already handled. 
	 * <br><br>
	 * Waking services unnecessarily is bad practice as it negatively impacts a device's battery 
	 * and memory resources.
	 */
	protected void eventSnoze() {
		if(eventCompletion!=null)
			eventCompletion.snooze();
	}
}
