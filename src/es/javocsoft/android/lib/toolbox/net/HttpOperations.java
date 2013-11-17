package es.javocsoft.android.lib.toolbox.net;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.HTTP_METHOD;

/**
 * 
 * @author JavocSoft 2013
 * @version 1.0<br>
 * $Rev: 385 $<br>
 * $LastChangedDate: 2013-11-17 11:05:54 +0100 (Sun, 17 Nov 2013) $<br>
 * $LastChangedBy: jgonzalez $
 *
 */
public class HttpOperations {


	private static final boolean LOG_ENABLE = false;
	private static final String TAG = "HTTP Operation";
		
	
	
	/**
	 * Prepares the HTTP Basic AUTHZ string.
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public static String getBasicAUTHZ(String user, String password) {
		return "Basic " + new String(android.util.Base64.encodeToString(new String(user + ":" + password).getBytes(), android.util.Base64.NO_WRAP ));
	}
	

	/**
	 * A POST operation.
	 * 
	 * @param url			Url for the POST operation 
	 * @param data			JSON data 
	 * @throws Exception	In case of any error.
	 */
	public static String doPost(String url, String data, Map<String, String> headersData) throws Exception{
		String res;
		
		String theJSON = data;
		if(LOG_ENABLE)
			Log.d(TAG, "POST: " + theJSON);
		
		URL urlPath = null;
		try {
			urlPath = new URL(url);
			res = ToolBox.net_httpclient_doAction(HTTP_METHOD.POST, urlPath.toString(), theJSON, headersData);

		} catch (Exception e) {
			if(LOG_ENABLE)
				Log.e(TAG, "POST: Error sending data (POS) to service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			
			throw e;
		}
		
		return res;
	}
	
	/**
	 * A GET operation.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> headersData) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		String res = null;
		
		if(LOG_ENABLE)
			Log.d(TAG, "GET: " + url);
		
		URL urlPath = null;
		try {
			urlPath = new URL(url);
			res = ToolBox.net_httpclient_doAction(HTTP_METHOD.GET, urlPath.toString(), null, headersData);

		} catch (ConnectTimeoutException e) {
			if(LOG_ENABLE)
				Log.e(TAG, "GET: Error doing get to service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			throw e;
		} catch (SocketTimeoutException e) {
			if(LOG_ENABLE)
				Log.e(TAG, "GET: Error doing get to service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			throw e;
		} catch (Exception e){
			if(LOG_ENABLE)
				Log.e(TAG, "GET: Error doing get to service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			throw e;
		}
		
		return res;
	}
	
}
