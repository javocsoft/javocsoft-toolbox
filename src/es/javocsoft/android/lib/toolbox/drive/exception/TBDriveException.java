package es.javocsoft.android.lib.toolbox.drive.exception;

/**
 * Google Drive exception.
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public class TBDriveException extends Exception {

	private static final long serialVersionUID = 1L;

	public TBDriveException() {
	}

	public TBDriveException(String detailMessage) {
		super(detailMessage);
	}

	public TBDriveException(Throwable throwable) {
		super(throwable);
	}

	public TBDriveException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
