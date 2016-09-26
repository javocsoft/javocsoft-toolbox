package es.javocsoft.android.lib.toolbox.json.exception;

/**
 * An exception thrown by {@link es.javocsoft.android.lib.toolbox.json.JsonDataReader}.
 * 
 * @author JavocSoft 2015
 * @since  2016
 */
public class JsonDataException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public JsonDataException() {
		
	}

	public JsonDataException(String detailMessage) {
		super(detailMessage);
		
	}

	public JsonDataException(Throwable throwable) {
		super(throwable);
		
	}

	public JsonDataException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		
	}

}
