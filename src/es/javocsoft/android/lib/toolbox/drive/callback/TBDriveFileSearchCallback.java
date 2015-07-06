package es.javocsoft.android.lib.toolbox.drive.callback;

import java.util.List;

import android.content.Context;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.Metadata;

/**
 * Base callback class to use when searching for files in Drive.
 *  
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveFileSearchCallback extends Thread implements Runnable {
	
	protected List<FileInfo> searchResult = null;
	
	protected int errorCode;
	protected String errorMessage;
	protected String errorDetails;
	
	protected Context context = null;
	
	public TBDriveFileSearchCallback(Context context) {
		this.context = context;
	}

	
	
	@Override
	public void run() {
		doWork();
	}
	
	
	public abstract void doWork();
	
	
	public void setSearchResult(List<FileInfo> searchResult) {
		this.searchResult = searchResult;
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
	
	
	
	public static class FileInfo {
		DriveFile driveFile;
		Metadata metaData;
		
		
		public FileInfo(DriveFile driveFile, Metadata metaData) {
			super();
			this.driveFile = driveFile;
			this.metaData = metaData;
		}

		
		public DriveFile getDriveFile() {
			return driveFile;
		}

		public Metadata getMetaData() {
			return metaData;
		}
	}
}


