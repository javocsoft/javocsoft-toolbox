package es.javocsoft.android.lib.toolbox.drive.callback;

import android.content.Context;

import com.google.android.gms.drive.DriveContents;

/**
 * Base callback class to use when opening a file in Drive.
 *  
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveFileOpenCallback extends Thread implements Runnable {

	
	protected DriveContents contents = null;
	protected int errorCode;
	protected String errorMessage;
	protected String errorDetails;
	protected Context context = null;
	
	
	public TBDriveFileOpenCallback(Context context) {
		this.context = context;
	}

	
	
	@Override
	public void run() {
		doWork();
	}
	
	
	public abstract void doWork();
	
	
	public void setDriveContents(DriveContents contents) {
		this.contents = contents;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	
}
