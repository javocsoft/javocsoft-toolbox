package es.javocsoft.android.lib.toolbox.drive.callback;

import android.content.Context;

import com.google.android.gms.common.api.Status;

/**
 * Base callback class to use when writing a file to Drive.
 *  
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveWriteFileOpCallback extends Thread implements Runnable {

	
	protected Status status = null;
	protected int errorCode;
	protected String errorMessage;
	protected String errorDetails;
	protected Context context = null;
	
	
	public TBDriveWriteFileOpCallback(Context context) {
		this.context = context;
	}

	
	
	@Override
	public void run() {
		doWork();
	}
	
	
	public abstract void doWork();
	
	
	public void setStatus(Status status) {
		this.status = status;
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
