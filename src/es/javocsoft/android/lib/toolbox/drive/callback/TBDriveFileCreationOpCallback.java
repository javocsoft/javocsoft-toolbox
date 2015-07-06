package es.javocsoft.android.lib.toolbox.drive.callback;

import android.content.Context;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.Metadata;

/**
 * Base callback class to use when creating a file in Drive.
 *  
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveFileCreationOpCallback extends Thread implements Runnable {

	
	protected DriveFile driveFile = null;
	protected Metadata driveFileMetaData = null;
	protected int errorCode;
	protected String errorMessage;
	protected String errorDetails;
	protected Context context = null;
	
	
	public TBDriveFileCreationOpCallback(Context context) {
		this.context = context;
	}

	
	
	@Override
	public void run() {
		doWork();
	}
	
	
	public abstract void doWork();
	
	
	public void setDriveFile(DriveFile driveFile) {
		this.driveFile = driveFile;
	}
	
	public void setDriveFileMetaData(Metadata driveFileMetaData) {
		this.driveFileMetaData = driveFileMetaData;
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
