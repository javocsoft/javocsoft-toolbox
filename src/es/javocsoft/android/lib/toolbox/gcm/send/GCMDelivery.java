package es.javocsoft.android.lib.toolbox.gcm.send;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * GCM server side delivery helper class. You can export this class onto
 * a running Java project or, you can use it within an Android app to
 * be able to deliver PUSH notification inside the app.
 * 
 * This requires the included libraries:
 * 
 * 	- gcm-server.jar
 * 	- json_simple-1.1.jar
 * 
 * @author JavocSoft 2013
 * @version 1.0<br>
 * $Rev: 410 $<br>
 * $LastChangedDate: 2013-11-22 18:03:16 +0100 (Fri, 22 Nov 2013) $<br>
 * $LastChangedBy: jgonzalez $
 *
 */
public class GCMDelivery {

	
	private GCMDelivery(){}
	
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
	 * @param device			The device GCM registration id.
	 * @param retries			Number of retries, default is 5.
	 * @throws GCMDeliveryException
	 */
	public static void gcm_sendMessageToDevice(String apiKey, Map<String, String> data,  
			String collapseKey, boolean delayWhileIdle, int timeToLive, 
			String device, int retries) throws GCMDeliveryException{
		
		Builder msgBuilder = message_prepare(collapseKey, delayWhileIdle, timeToLive);
		Set<String> keys = data.keySet();		
		for(String key:keys){			
			msgBuilder = message_addData(msgBuilder, key, data.get(key));
		}
		Message gcmMessage = msgBuilder.build();
		
		gcm_sendMessage(apiKey, gcmMessage, device, retries);
	}
	
	/**
	 * Sends a GCM PUSH message(message with multiple data, up to 4k.) to a device.
	 * 
	 * See: http://developer.android.com/google/gcm/adv.html
	 * 
	 * @param apiKey			The required API Key from Google Cloud Console GCM API.
	 * @param key				The key of the sent data.
	 * @param message			The data. Google Cloud Messaging allows for 4k of data.
	 * @param collapseKey		If set, if two messages have the same collapse key, the last will override the
	 * 							previous one so there will be always only the last message of that collapse key.
	 * 							These kind of messages are like a "ping" and are also called "Send-to-sync messages". 
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
	 * @param device			The device GCM registration id.
	 * @param retries			Number of retries, default is 5.
	 * @throws GCMDeliveryException
	 */
	public static void gcm_sendMessageToDevice(String apiKey, String key, String message, 
			String collapseKey, boolean delayWhileIdle, int timeToLive, 
			String device, int retries) throws GCMDeliveryException{
		
		Builder msgBuilder = message_prepare(collapseKey, delayWhileIdle, timeToLive); 
		msgBuilder = message_addData(msgBuilder, key, message);
		Message gcmMessage = msgBuilder.build();
			
		gcm_sendMessage(apiKey, gcmMessage, device, retries);
	}
	
	
	
	/**
	 * Sends a GCM PUSH message to a list of devices.
	 * 
	 * See: http://developer.android.com/google/gcm/adv.html
	 * 
	 * @param apiKey			The required API Key from Google Cloud Console GCM API.
	 * @param key				The key of the sent data.
	 * @param message			The data. Google Cloud Messaging allows for 4k of data.
	 * @param collapseKey		If set, if two messages have the same collapse key, the last will override the
	 * 							previous one so there will be always only the last message of that collapse key.
	 * 							These kind of messages are like a "ping" and are also called "Send-to-sync messages". 
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
	 * @throws GCMDeliveryException
	 */
	public static void gcm_sendMessageToDevices(String apiKey, String key, String message, 
			String collapseKey, boolean delayWhileIdle, int timeToLive, 
			List<String> devices, int retries) throws GCMDeliveryException{
		
		Builder msgBuilder = message_prepare(collapseKey, delayWhileIdle, timeToLive); 
		msgBuilder = message_addData(msgBuilder, key, message);
		Message gcmMessage = msgBuilder.build();
        
		gcm_sendMulticastMessage(apiKey, gcmMessage, devices, retries);
	}
	
	/**
	 * Sends a GCM PUSH message(message with multiple data, up to 4k.) to a list of devices.
	 * 
	 * See: http://developer.android.com/google/gcm/adv.html
	 * 
	 * @param apiKey			The required API Key from Google Cloud Console GCM API.
	 * @param data				The data to send. A bunch of key-data pairs, up to 4k of data.
	 * @param collapseKey		If set, if two messages have the same collapse key, the last will override the
	 * 							previous one so there will be always only the last message of that collapse key.
	 * 							These kind of messages are like a "ping" and are also called "Send-to-sync messages". 
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
	 * @throws GCMDeliveryException
	 */
	public static void gcm_sendMessageToDevices(String apiKey, Map<String, String> data, 
			String collapseKey, boolean delayWhileIdle, int timeToLive, 
			List<String> devices, int retries) throws GCMDeliveryException{
		
		Builder msgBuilder = message_prepare(collapseKey, delayWhileIdle, timeToLive); 
		msgBuilder = message_addData(msgBuilder, data);
		Message gcmMessage = msgBuilder.build();
        
		gcm_sendMulticastMessage(apiKey, gcmMessage, devices, retries);
	}
	
	
	
	//AUXILIAR	
	
	private static Builder message_prepare(String collapseKey, boolean delayWhileIdle, int timeToLive){
		//See http://developer.android.com/reference/com/google/android/gcm/server/Message.Builder.html
		
		Builder msgBuilder = new Message.Builder();
        
		if(collapseKey!=null && collapseKey.length()>0)
        	msgBuilder.collapseKey(collapseKey);
		
        msgBuilder.delayWhileIdle(delayWhileIdle);
        
        if(timeToLive>=0){
        	msgBuilder.timeToLive(timeToLive);
		}       
        
        return msgBuilder;
	}
	
	private static Builder message_addData(Builder msgBuilder, Map<String, String> data){
		Set<String> keys = data.keySet(); 
		for(String key:keys){
			msgBuilder = message_addData(msgBuilder, key, data.get(key));			
		}
		
		return msgBuilder;
	}
	
	private static Builder message_addData(Builder msgBuilder, String key, String message){
		msgBuilder.addData(key, message);
		
		return msgBuilder;
	}
	
	private static void gcm_sendMessage(String apiKey, Message gcmMessage, String device, int retries) throws GCMDeliveryException{
		//See http://developer.android.com/reference/com/google/android/gcm/server/Result.html
		try{
			if(retries<=0){
				retries = 5;
			}
			//See http://developer.android.com/reference/com/google/android/gcm/server/Sender.html
			Sender sender = new Sender(apiKey);
			Result result = sender.send(gcmMessage, device, retries);
			message_result(result);			
	        
		}catch(IOException e){
			throw new GCMDeliveryException("Error sending GCM notification PUSH to the device with id '" + 
						device + "'. [" + e.getMessage() + "]", e, GCMDeliveryException.ERROR_UNEXPECTED);
		}
	}
	
	private static long gcm_sendMulticastMessage(String apiKey, Message gcmMessage, List<String> devices, int retries) throws GCMDeliveryException{
		//See http://developer.android.com/reference/com/google/android/gcm/server/MulticastResult.html
		
		long deliveries = 0;
		
		try{
			if(retries<=0){
				retries = 5;
			}
			
			//See http://developer.android.com/reference/com/google/android/gcm/server/Sender.html
			Sender sender = new Sender(apiKey);
			MulticastResult result = sender.send(gcmMessage, devices, retries);
			
			//Check result
			if (result.getResults() != null) {
	            if(result.getSuccess()!=result.getTotal()){ //There are some failed messages
	            	List<Result> resultList = result.getResults();	            	
	            	for(Result res:resultList){
	            		try{
	            			message_result(res);
	            		}catch(GCMDeliveryException e){}
	            	}
	            	deliveries = result.getSuccess();
	            }else{
	            	deliveries = (-1)*result.getFailure(); //Negative are the failed number of deliveries.
	            }
	            
	        } else {
	        	deliveries = (-1)*result.getFailure(); //Negative are the failed number of deliveries.	            
	        }			
	        
		}catch(IOException e){
			throw new GCMDeliveryException("Error sending GCM notification PUSH to the list of devices [" + 
					e.getMessage() + "]", e, GCMDeliveryException.ERROR_UNEXPECTED);
		}
		
		return deliveries;
	}
	
	private static void message_result(Result result) throws GCMDeliveryException {
		System.out.println(result.toString());
        if (result.getMessageId() != null) { //Message sent ok.
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
                //Same device has more than on registration ID: update database to set the last valid one.
            	throw new GCMDeliveryException("OutDated GCM Registration ID", GCMDeliveryException.WARNING_OUTDATED_REGID);
            }
        } else { 
        	String error = result.getErrorCodeName(); //See Constants.ERROR_
            throw new GCMDeliveryException(error, error);            
        }
	}
	
	/**
	 * GCM Delivery exception.
	 * 
	 * Use getErrorCode() against Constants.ERROR_ to knw the exact error. If
	 * the error is not in this list, use one of the two custom errors in this
	 * class.
	 * 
	 * @author JavocSoft 2013
	 * @version 1.0<br>
	 * $Rev: 410 $<br>
	 * $LastChangedDate: 2013-11-22 18:03:16 +0100 (Fri, 22 Nov 2013) $<br>
	 * $LastChangedBy: jgonzalez $
	 *
	 */
	@SuppressWarnings("serial")
	public static class GCMDeliveryException extends Exception {
		
		public static String WARNING_OUTDATED_REGID = "WARNING_OUTDATED_REGID";
		public static String ERROR_UNEXPECTED = "ERROR_UNEXPECTED";
		
		private String errorCode;
		
		public GCMDeliveryException(String errorCode) {
			super();
			this.errorCode = errorCode;
		}

		public GCMDeliveryException(String message, Throwable cause, String errorCode) {
			super(message, cause);
			this.errorCode = errorCode;
		}

		public GCMDeliveryException(String message, String errorCode) {
			super(message);
			this.errorCode = errorCode;
		}

		public GCMDeliveryException(Throwable cause, String errorCode) {
			super(cause);
			this.errorCode = errorCode;
		}
		
		
		public String getErrorCode() {
			return errorCode;
		}
	}
	
}
