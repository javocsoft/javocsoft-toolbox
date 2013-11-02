package es.javocsoft.android.lib.toolbox.net;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import android.util.Base64;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.HTTP_METHOD;

/**
 * 
 * @author JavocSoft 2013
 * @version 1.0<br>
 * $Rev: 346 $<br>
 * $LastChangedDate: 2013-11-02 11:42:49 +0100 (Sat, 02 Nov 2013) $<br>
 * $LastChangedBy: jgonzalez $
 *
 */
public class HttpOperations {


private static final boolean LOG_ENABLE = false;
private static final String TAG = "WCTime HTTP Operation";
		
	
	/**
	 * A POST operation to WCTime API.
	 * 
	 * @param url			Url for the POST operation 
	 * @param data			JSON data 
	 * @throws Exception	In case of any error.
	 */
	public static String doPost(String url, String data) throws Exception{
		String res;
		
		String theJSON = data;
		if(LOG_ENABLE)
			Log.d(TAG, "POST: " + theJSON);
		
		URL urlPath = null;
		try {
			urlPath = new URL(url);
						
			Map<String, String> headersData = new HashMap<String, String>();
			headersData.put("Authorization", "Basic " + new String(Base64.encodeToString(new String("root-wc" + ":" + "wcTimeApiUserRoot#7123").getBytes(), Base64.NO_WRAP )));
			//headersData.put("Authorization", "basic " + new String(Base64.encode(new String("root-wc" + ":" + "wcTimeApiUserRoot#7123").getBytes())));
			headersData.put("Content-Type", "application/json");
			res = ToolBox.net_httpclient_doAction(HTTP_METHOD.POST, urlPath.toString(), theJSON, headersData);

		} catch (Exception e) {
			if(LOG_ENABLE)
				Log.e(TAG, "POST: Error sending data (POS) to WCTime API service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			
			throw e;
		}
		
		return res;
	}
	
	/**
	 * A GET operation to WCTime API.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		String res = null;
		
		if(LOG_ENABLE)
			Log.d(TAG, "GET: " + url);
		
		URL urlPath = null;
		try {
			urlPath = new URL(url);
						
			Map<String, String> headersData = new HashMap<String, String>();
			//headersData.put("Content-Type", "application/json; charset=UTF-8");
			headersData.put("Authorization", "Basic " + new String(Base64.encodeToString(new String("root-wc" + ":" + "wcTimeApiUserRoot#7123").getBytes(), Base64.NO_WRAP )));
			res = ToolBox.net_httpclient_doAction(HTTP_METHOD.GET, urlPath.toString(), null, headersData);

		} catch (ConnectTimeoutException e) {
			if(LOG_ENABLE)
				Log.e(TAG, "GET: Error doing get to WCTime API service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			throw e;
		} catch (SocketTimeoutException e) {
			if(LOG_ENABLE)
				Log.e(TAG, "GET: Error doing get to WCTime API service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			throw e;
		} catch (Exception e){
			if(LOG_ENABLE)
				Log.e(TAG, "GET: Error doing get to WCTime API service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			throw e;
		}
		
		return res;
	}
	
}
