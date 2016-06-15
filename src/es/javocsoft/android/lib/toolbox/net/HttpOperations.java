/*
 * Copyright (C) 2010-2014 - JavocSoft - Javier Gonzalez Serrano
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
package es.javocsoft.android.lib.toolbox.net;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.HTTP_METHOD;

/**
 * Common HTTP operations helper class. It uses HttpClient
 * library.
 *
 * @author JavocSoft 2013
 * @version 1.0
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
	 * @param headersData	Optional. Headers for the request.
	 * @param ignoreSSL		If set to TRUE, all SSL errors are ignored.
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception	In case of any error.
	 */
	public static String doPost(String url, String data, Map<String, String> headersData, boolean ignoreSSL) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		String res;
		
		String theJSON = data;
		if(LOG_ENABLE)
			Log.d(TAG, "POST: " + theJSON);
		
		URL urlPath = null;
		try {
			urlPath = new URL(url);
			res = ToolBox.net_httpclient_doAction(HTTP_METHOD.POST, urlPath.toString(), theJSON, headersData, ignoreSSL);

		} catch (Exception e) {
			if(LOG_ENABLE)
				Log.e(TAG, "POST: Error sending data (POS) to service url '"+urlPath.toString()+"': "+e.getMessage(),e);
			
			throw e;
		}
		
		return res;
	}
	
	/**
	 * A POST operation.
	 * 
	 * @param url			Url for the POST operation 
	 * @param data			JSON data
	 * @return 
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception	In case of any error.
	 */
	public static String doPost(String url, String data, Map<String, String> headersData) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		return doPost(url, data, headersData, false);
	}
	
	/**
	 * A GET operation.
	 * 
	 * @param url			Url for the POST operation
	 * @param headersData	Optional. Headers for the request.
	 * @param ignoreSSL		If set to TRUE, all SSL errors are ignored.
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> headersData, boolean ignoreSSL) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		String res = null;
		
		if(LOG_ENABLE)
			Log.d(TAG, "GET: " + url);
		
		URL urlPath = null;
		try {
			urlPath = new URL(url);
			res = ToolBox.net_httpclient_doAction(HTTP_METHOD.GET, urlPath.toString(), null, headersData, ignoreSSL);

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
	
	/**
	 * A GET operation.
	 * 
	 * @param url			Url for the POST operation
	 * @param headersData	Optional. Headers for the request.
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> headersData) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		return doGet(url, headersData, false);
	}
	
}
