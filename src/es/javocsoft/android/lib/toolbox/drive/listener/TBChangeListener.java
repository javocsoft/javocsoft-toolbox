package es.javocsoft.android.lib.toolbox.drive.listener;

import android.util.Log;

import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;

import es.javocsoft.android.lib.toolbox.drive.TBDrive;

/**
 * A listener to handle Drive file change events. See {@link ChangeEvent}
 * 
 * Events only are notified to the app if is running. 
 * See {@link TBDrive#addFileChangeListener(com.google.android.gms.drive.DriveFile, TBChangeListener)}
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBChangeListener implements ChangeListener {

	public TBChangeListener() {
		
	}

	@Override
	public void onChange(ChangeEvent event) {
		Log.i(TBDrive.TAG, (String.format("Drive file change event: %s", event)));
		doWork();
	}

	
	protected abstract void doWork();
}
