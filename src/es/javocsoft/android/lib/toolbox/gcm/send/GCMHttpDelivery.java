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
package es.javocsoft.android.lib.toolbox.gcm.send;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.params.CoreProtocolPNames;

import com.google.gson.Gson;

import es.javocsoft.android.lib.toolbox.gcm.core.beans.GCMDeliveryResponse;
import es.javocsoft.android.lib.toolbox.gcm.core.beans.GCMDeliveryResultItem;
import es.javocsoft.android.lib.toolbox.gcm.core.beans.GCMMessage;
import es.javocsoft.android.lib.toolbox.net.HttpOperations;



/**
 * HTTP GCM server implementation.
 * 
 * GCM server side delivery helper class. You can export this class onto
 * a running Java project or, you can use it within an Android app to
 * be able to deliver PUSH notification inside the app.
 * 
 * A message to a GCM server has the format:
 * 
 * <pre>
 * { "collapse_key": "score_update",
 *   "time_to_live": 108,
 *   "delay_while_idle": true,
 *   "data": {
 *     "score": "4x8",
 *     "time": "15:16.2342"
 *   },
 *   "registration_ids":["4", "8", "15", "16", "23", "42"]
 * }
 * </pre>
 * 
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
public class GCMHttpDelivery {

	
	private static final String GCM_HTTP_HEADER_AUTH_KEY = "Authorization";
	private static final String GCM_HTTP_HEADER_CONTENTTYPE_KEY = "Content-Type";
	private static final String GCM_HTTP_HEADER_CONTENTTYPE_VALUE = "application/json";
	private static final String GCM_HTTP_ENDPOINT = "https://android.googleapis.com/gcm/send";
	
	private static final Gson gson = new Gson();
	
	
	
	/**
	 * Sends a GCM PUSH message to a device.
	 * 
	 * See: http://developer.android.com/google/gcm/adv.html
	 * 
	 * @param apiKey			The required API Key from Google Cloud Console GCM API.
	 * @param data				The data to send. A bunch of key-data pairs, up to 4k of data.
	 * @param collapseKey		If set, if two messages have the same collapse key, the last will override the
	 * 							previous one so there will be always only the last message of that collapse key.
	 * 							This kind of messages are like a "ping" and are also called "Send-to-sync messages". 
	 * 							GCM allows a maximum of 4 different collapse keys so, there can be 4 diferent kind of 
	 * 							"Send-to-sync messages" per each application/device. If a collapse key is not set, the
	 * 							limit is currently 100. 
	 * @param delayWhileIdle	If set to TRUE, the message will be only delivered if the destination device is not idle.
	 * 							The time a message is stored in GCM servers by default is 4 weeks, after that time, the
	 * 							message is deleted.
	 * @param timeToLive		Setting this value overrides the default time that a message is stored in the GCM servers (4 weeks).
	 * 							The value are seconds where the maximum value is 2419200 seconds = 28 days. When setting 
	 * 							a of 0 seconds GCM will guarantee best effort for messages that must be delivered "now or never". 
	 * 							Keep in mind that a "time_to_live" value of 0 means messages that can't be delivered immediately 
	 * 							will be discarded. However, because such messages are never stored, this provides the best latency 
	 * 							for sending notifications.
	 * @param devices			The device list (GCM registration id list).
	 * @param retries			Number of retries, default is 5.
	 * @return GCMDeliveryResponse
	 * @throws GCMDeliveryException
	 */
	public static GCMDeliveryResponse gcm_sendMessageToDevice(String apiKey, Map<String, String> data,  
			String collapseKey, boolean delayWhileIdle, Long timeToLive, 
			List<String> devices, int retries) throws GCMDeliveryException{
		
		String json = message_prepare(collapseKey, delayWhileIdle, timeToLive, data, devices);
		return gcm_sendMessage(json, apiKey, retries);		
	}
	
	
	//AUXILIAR
	
	/* 
	 * Prepares a GCM JSON string to be sent in a POST request to GCM servers,
	 * 
	 * @param collapseKey		If set, if two messages have the same collapse key, the last will override the
	 * 							previous one so there will be always only the last message of that collapse key.
	 * 							This kind of messages are like a "ping" and are also called "Send-to-sync messages". 
	 * 							GCM allows a maximum of 4 different collapse keys so, there can be 4 diferent kind of 
	 * 							"Send-to-sync messages" per each application/device. If a collapse key is not set, the
	 * 							limit is currently 100.
	 * @param delayWhileIdle	If set to TRUE, the message will be only delivered if the destination device is not idle.
	 * 							The time a message is stored in GCM servers by default is 4 weeks, after that time, the
	 * 							message is deleted.
	 * @param timeToLive		Setting this value overrides the default time that a message is stored in the GCM servers (4 weeks).
	 * 							The value are seconds where the maximum value is 2419200 seconds = 28 days. When setting 
	 * 							a of 0 seconds GCM will guarantee best effort for messages that must be delivered "now or never". 
	 * 							Keep in mind that a "time_to_live" value of 0 means messages that can't be delivered immediately 
	 * 							will be discarded. However, because such messages are never stored, this provides the best latency 
	 * 							for sending notifications.
	 * @param data				The data to send. A bunch of key-data pairs, up to 4k of data.
	 * @param devices			The device list (GCM registration id list).
	 * @return
	 */
	private static String message_prepare(String collapseKey, boolean delayWhileIdle, 
										  Long timeToLive, Map<String, String> data,
										  List<String> devices) {
		
		String json = null;
		
		GCMMessage msg = new GCMMessage();
		
		msg.delay_while_idle = delayWhileIdle;
		msg.registration_ids = devices;
		
		if(data!=null && data.size()>0)
			msg.data = data;		
		if(collapseKey!=null && collapseKey.length()>0)
			msg.collapse_key = collapseKey;
        if(timeToLive>=0)
        	msg.time_to_live = timeToLive;
                
        json = gson.toJson(msg);
        
        return json;
	}
	
	
	/*
	 * Sends a JSON delivery string through GCM servers.
	 * 
	 * @param jsonData	The GCM JSON prepared string data.
	 * @param apiKey	The required API Key from Google Cloud Console GCM API.
	 */
	private static GCMDeliveryResponse gcm_sendMessage(String jsonData, String apiKey, int retries) throws GCMDeliveryException {
		
		GCMDeliveryResponse response = null;
		
		//Prepare the headers
		Map<String, String> headersData = new HashMap<String,String>();
		headersData.put(GCM_HTTP_HEADER_AUTH_KEY, "key="+apiKey);
		headersData.put(GCM_HTTP_HEADER_CONTENTTYPE_KEY, GCM_HTTP_HEADER_CONTENTTYPE_VALUE);
		headersData.put(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		
		if(retries<=0){
			retries = 5; //Default
		}
		
		for(int i=0; i<retries;i++){
			try {
				//Send the request to GCM servers.
				String responseRaw = HttpOperations.doPost(GCM_HTTP_ENDPOINT, jsonData, headersData);
				//Get the response object
				response = gson.fromJson(responseRaw, GCMDeliveryResponse.class);
				break; //Successfully submitted and get response
				
			} catch (Exception e) {}
		}
		
		if(response==null) {			 
			throw new GCMDeliveryException("Push delivery could not be done. Retries consumed.");
		}
				
		return response;
	}

	
	/**
	 * Used for GCM push deliveries exceptions.
	 * 
	 *  @author JavocSoft 2014
	 *	@version 1.0
	 */
	@SuppressWarnings("serial")
	public static class GCMDeliveryException extends Exception {
		
		public GCMDeliveryException() {
			super();			
		}

		public GCMDeliveryException(String message, Throwable cause) {
			super(message, cause);			
		}

		public GCMDeliveryException(String message) {
			super(message);			
		}

		public GCMDeliveryException(Throwable cause) {
			super(cause);			
		}
	}
	
	
	// TESTING HOWTO
	
	/*
	public static void main(String[] args) {
		
		//NOTE: These tests should be done within a
		//normal Java project, not an Android project.
		
		//Testing creation of GCM JSON request and
		//parse response.
		testingDelivery1();
		
		//Real GCM delivery test
		testingDelivery2();
	}*/
	
	
	@SuppressWarnings("unused")
	private static void testingDelivery1 () {
		//Text of GCM JSON creation:
		GCMMessage msg = new GCMMessage();
		msg.collapse_key = "score_update";
		msg.time_to_live = 108l;
		msg.delay_while_idle = true;
		msg.registration_ids = Arrays.asList(new String[]{"1","2","3","4"});
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("message", "Some message");
		msg.data = data;
		
		System.out.println(gson.toJson(msg));
		
		
		//Test of GCM JSON response parsing:
		String responseSimulated = "{ \"multicast_id\": \"216\",\"success\": 3,\"failure\": 3,\"canonical_ids\": 1,\"results\": [{ \"message_id\": \"1:0408\" },{ \"error\": \"Unavailable\" },{ \"error\": \"InvalidRegistration\" },{ \"message_id\": \"1:1516\" },{ \"message_id\": \"1:2342\", \"registration_id\": \"32\" },{ \"error\": \"NotRegistered\"}]}";
		GCMDeliveryResponse responseObj = gson.fromJson(responseSimulated, GCMDeliveryResponse.class);
		System.out.println(responseObj.success);
		System.out.println(responseObj.failure);
		System.out.println(responseObj.canonical_ids);
		
		for(GCMDeliveryResultItem resultItem:responseObj.results){
			System.out.println("ResultItem-> [" + 
									(resultItem.message_id!=null?resultItem.message_id:"") + "/" +										
									(resultItem.registration_id!=null?resultItem.registration_id:"") + "/" +											
									(resultItem.error!=null?resultItem.error:"") + "]"
								);
		}
	}
	
	@SuppressWarnings("unused")
	private static void testingDelivery2 () {
		final String MYPHONE_GCM_REGID = "some_reg_id";
		final String GCM_SERVER_KEY_TO_SEND = "some_key";
		
		GCMDeliveryResponse responseObj = null;
		try {
			//NOTE: This must be the key to use to store the notification message
			final String GCM_DELIVERY_MESSAGE_KEY = "message";
			
			final String GCM_DELIVERY_COLLAPSE_KEY = "collapse";
			final int GCM_DELIVERY_RETRIES = 3;
									
			Map<String, String> data = new HashMap<String, String>();
			data.put(GCM_DELIVERY_MESSAGE_KEY, "A notification message");
			
			responseObj = gcm_sendMessageToDevice(GCM_SERVER_KEY_TO_SEND, data, GCM_DELIVERY_COLLAPSE_KEY, false, -1l, 
								Arrays.asList(new String[]{MYPHONE_GCM_REGID}), 
								GCM_DELIVERY_RETRIES);
			
			System.out.println(responseObj.success);
			System.out.println(responseObj.failure);
			System.out.println(responseObj.canonical_ids);
			
			for(GCMDeliveryResultItem resultItem:responseObj.results){
				System.out.println("ResultItem-> [" + 
										(resultItem.message_id!=null?resultItem.message_id:"") + "/" +										
										(resultItem.registration_id!=null?resultItem.registration_id:"") + "/" +											
										(resultItem.error!=null?resultItem.error:"") + "]"
									);
			}
			
		} catch (GCMDeliveryException e) {			
			e.printStackTrace();
		}
	}
}
