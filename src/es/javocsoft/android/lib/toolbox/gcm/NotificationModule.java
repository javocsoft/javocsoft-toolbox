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
package es.javocsoft.android.lib.toolbox.gcm;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.gcm.core.CustomNotificationReceiver.OnAckCallback;
import es.javocsoft.android.lib.toolbox.gcm.core.GCMIntentService.OnNewNotificationCallback;
import es.javocsoft.android.lib.toolbox.gcm.core.GCMIntentService.OnRegistrationCallback;
import es.javocsoft.android.lib.toolbox.gcm.core.GCMIntentService.OnUnregistrationCallback;
import es.javocsoft.android.lib.toolbox.gcm.exception.GCMException;


/**
 * Notification module.
 * 
 * + Registration. 
 * 			1- Register the device for push with GCM.
 * 			2- Execute any process that your backend can need.
 *  
 * + Un-registration.
 * 			1- Unregisters from Google GCM.
 * 			2- Execute any process that your backend can need.
 * 
 * + Notification receive.
 * 
 * + Notification ACKs
 * 			
 * See:  https://developer.android.com/google/gcm/client.html
 * 
 * @author javocSoft 2013.
 * @since  2013
 *
 */
public class NotificationModule {

	/** Enables or disables the log. */
	public static boolean LOG_ENABLE = true;
	public static final String TAG = "javocsoft-toolbox: NotificationModule";

	private static NotificationModule instance = null;
	
	/** This will hold the GCM device registration token. */
	public String deviceToken =null;
	
	/** Just in case the application owner wants some kind of feedback */
	public static final String MESSAGE_EFFICACY_KEY = "notificationId";
	/** The key where the notification type of the notification is */
	public static final String ANDROID_NOTIFICATION_STYLE_KEY = "notStyle";
	/** The key where the message of the notification is */
	public static final String ANDROID_NOTIFICATION_TITLE_KEY = "title";
	/** The key where the message of the notification is */
	public static final String ANDROID_NOTIFICATION_MESSAGE_KEY = "message";
	/** The key where the ticker of the notification is */
	public static final String ANDROID_NOTIFICATION_TICKER_KEY = "ticker";
	/** The key where the content info of the notification is */
    public static final String ANDROID_NOTIFICATION_CONTENT_INFO_KEY = "contentInfo";
    /** The key where the big style title of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_TITLE_KEY = "bsTitle";
    /** The key where the big style content of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_CONTENT_KEY = "bsContent";
    /** The key where the big style summary of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_SUMMARY_KEY = "bsSummary";
    /** The key where the big picture style image of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_IMAGE_KEY = "bsImage";
    /** The key where the big style line content of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_INBOX_CONTENT_KEY = "bsInboxStyleContent";
    /** The key where the inbox style line separator character of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_INBOX_LINE_SEPARATOR_KEY = "bsInboxStyleLineSeparator";
    /** The key where the large icon of the notification is */
    public static final String ANDROID_NOTIFICATION_BIG_STYLE_LARGE_ICON_KEY = "bslargeIcon";
		
	private final static String APP_NOTIFICATION_ACTION_KEY = "<app_package>";
	/** Custom intent used to show the alert in the UI about a received push. */
    public static String SHOW_NOTIFICATION_ACTION = "com.google.android.gcm."+ APP_NOTIFICATION_ACTION_KEY+".DISPLAY_MESSAGE";
    /** Used to do something in particular when a notification arrives */ 
    public static String NEW_NOTIFICATION_ACTION = "com.google.android.gcm."+ APP_NOTIFICATION_ACTION_KEY+".NEW_DISPLAY_MESSAGE";
    
	public static String NOTIFICATION_TITLE = null;
    public static Class<?> NOTIFICATION_ACTIVITY_TO_CALL = null;
    
    
    /** Google API project id registered to use GCM. */
    public static String SENDER_ID = null;
    
    public static Context APPLICATION_CONTEXT;
    public static EnvironmentType ENVIRONMENT_TYPE = EnvironmentType.PRODUCTION;
    public static final String NOTIFICATION_ACTION_KEY = "NotificationActionKey";
    public static OnAckCallback ackRunnable;
    public static OnRegistrationCallback registerRunnable;
    public static OnUnregistrationCallback unregisterRunnable;
    public static OnNewNotificationCallback doWhenNotificationRunnable;
    
    public static boolean multipleNot;
    public static String groupMultipleNotKey;    
    public static String notBackgroundColor;
    public static boolean vibrate;
    
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    
    
    protected NotificationModule(Context context, EnvironmentType environmentType, String appSenderId,  
    		OnRegistrationCallback registerRunnable, OnAckCallback ackRunnable, OnUnregistrationCallback unregisterRunnable,
    		OnNewNotificationCallback doWhenNotificationRunnable,
			boolean multipleNot, String groupMultipleNotKey, String notBackgroundColor,
			boolean vibrate) {
		APPLICATION_CONTEXT = context;
		if(environmentType!=null){
			ENVIRONMENT_TYPE = environmentType;
		}
		SENDER_ID = appSenderId;
		NotificationModule.ackRunnable = ackRunnable;
		NotificationModule.unregisterRunnable = unregisterRunnable;
		NotificationModule.registerRunnable = registerRunnable;
		NotificationModule.doWhenNotificationRunnable = doWhenNotificationRunnable;
		
		SHOW_NOTIFICATION_ACTION = SHOW_NOTIFICATION_ACTION.replaceAll(APP_NOTIFICATION_ACTION_KEY, APPLICATION_CONTEXT.getPackageName());
		NEW_NOTIFICATION_ACTION = NEW_NOTIFICATION_ACTION.replaceAll(APP_NOTIFICATION_ACTION_KEY, APPLICATION_CONTEXT.getPackageName());
		
		NotificationModule.multipleNot = multipleNot;
		NotificationModule.groupMultipleNotKey = groupMultipleNotKey;
		
		NotificationModule.notBackgroundColor = notBackgroundColor;
		NotificationModule.vibrate = vibrate;
		
		//Required, when application is closed, for the service that processes the notification 
		//to be able to create the notification for the app.
		ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_NOTIFICATION_MULTIPLENOT, Boolean.class, NotificationModule.multipleNot);
		ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_NOTIFICATION_GROUPMULTIPLENOTKEY, String.class, NotificationModule.groupMultipleNotKey);
		ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_NOTIFICATION_ONNOTRECEIVEDTHREAD_TO_CALL, String.class, NotificationModule.doWhenNotificationRunnable.getClass().getName());
	}	
	
	
    /**
     * 
     * Initializes the GCM notification module.
     * 
     * @param context							The application context.
     * @param environmentType					The environment type. See EnvironmentType enumerator.
     * @param appSenderId						The allowed SENDER_ID (from Google API Console) that is 
     * 											allowed to send notifications to the application.
     * @param registerRunnable					Optional. Something to do once the application registers with GCM.
     * @param ackRunnable						Optional. Something to do once the user opens a received push notification from GCM..
     * @param unregisterRunnable				Optional. Something to do once the application un-registers from GCM.
     * @param doWhenNotificationRunnable		Optional. Something to do once the application receives a push notification from GCM.
     * @param multipleNot 						Setting to True allows showing multiple notifications.
	 * @param groupMultipleNotKey 				If is set, multiple notifications can be grupped by 
	 * 											this key.
	 * @param notBackgroundColor				Optional. Since Android 5.0+ notification icons must 
	 * 											follow a design guidelines to be showed correctly and allows to set the background 
	 * 											color for the icon. The specified color must be in hexadecimal, 
	 * 											for example "#ff6600".
	 * @param vibrate							Set to TRUE to enable vibration when notification arrives.
	 * 											Requires the permission android.permission.VIBRATE.
	 *
     * @return
     */
	public static NotificationModule getInstance(Context context, EnvironmentType environmentType, String appSenderId,  
			OnRegistrationCallback registerRunnable, OnAckCallback ackRunnable, OnUnregistrationCallback unregisterRunnable,
			OnNewNotificationCallback doWhenNotificationRunnable,
			boolean multipleNot, String groupMultipleNotKey, String notBackgroundColor,
			boolean vibrate) {

		if (instance == null) {
			instance = new NotificationModule(context, environmentType, appSenderId,  
						registerRunnable, ackRunnable, unregisterRunnable, 
						doWhenNotificationRunnable,
						multipleNot, groupMultipleNotKey, notBackgroundColor, vibrate);
		}
		return instance;
	}
	
	

	// NOTIFICATIONS - GCM	
	
	/**
	 * Registers the device with Google Cloud Messaging system (GCM).
	 * 
	 * NOTE: 
	 * The environment is set by looking for the application debug mode,
	 * if is set to TRUE, the environment will be SANDBOX, otherwise PRODUCTION.
	 * 
	 * @param 	context		Context.  
	 * @param	title		Title for the notification
	 * @param 	clazz		Class to call when clicking in the notification
	 */
	public void gcmRegisterDevice(Context context, String title, Class<?> clazz) throws GCMException {
		
		EnvironmentType environment = ENVIRONMENT_TYPE;
		if(ToolBox.application_isAppInDebugMode(context)){
			environment = EnvironmentType.SANDBOX;
		}
		
		gcmRegisterDevice(context, environment, title, clazz);
	}
	
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	
	
	/**
	 * Registers the device with Google Cloud Messaging system (GCM)
	 * 
	 * @param 	context		Context.
	 * @param 	environment	Allows to set the environment type. 
	 * @param	title		Title for the notification
	 * @param 	clazz		Class to call when clicking in the notification
	 */
	public void gcmRegisterDevice(final Context context, final EnvironmentType environment,
								  String title, Class<?> clazz) throws GCMException {
		
		ENVIRONMENT_TYPE = environment;
		NOTIFICATION_TITLE = title;
		NOTIFICATION_ACTIVITY_TO_CALL = clazz;
		
		//Required, when application is closed, for the service that processes the notification 
		//to be able to create the notification for the app.
		ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_NOTIFICATION_ACTIVITY_TO_CALL, String.class, NotificationModule.NOTIFICATION_ACTIVITY_TO_CALL.getName());
		ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_NOTIFICATION_TITLE, String.class, NotificationModule.NOTIFICATION_TITLE);
		
		
		executorService.execute(new GCMRegistration(context));		
	}
	
	
	/**
	 * Returns the Google Cloud Messaging system (GCM) registration token.
	 * 
	 * @param context
	 * @return	The token or null if the device is not registered or needs
	 * 			a registration id update if application version changed.
	 */
	public String gcmGetRegistrationToken(Context context){		
		return getRegistrationId(context);		
	}
	
	/**
	 * Returns the Android device unique id.
	 * <br><br>
	 * Requires the permission "READ_PHONE_STATE".
	 * 
	 * @param context
	 * @return	The android device unique id.
	 */
	public String gcmGetDeviceUdid(Context context){
		return ToolBox.device_getId(context);
	}
	
	/**
	 * Unregister the device from Google Cloud Messaging system (GCM)
	 * 
	 * @param context
	 */
	public void gcmUnregisterDevice(Context context){		
		executorService.execute(new GCMUnRegistration(context));
	}
	
	/**
	 * Makes the UI show the alert for any received notification.
	 * 
	 * @param context
	 * @param intent
	 */
	public void gcmCheckForNotificationReceival(Context context, Intent intent){
		
		Log.i(NotificationModule.TAG, "onNewIntent - " + intent.getAction());
		
		if(intent!=null && intent.getAction()!=null && NOTIFICATION_ACTIVITY_TO_CALL.getName()!=null && 
			intent.getAction().equals(NotificationModule.NOTIFICATION_ACTIVITY_TO_CALL.getName()+"."+NotificationModule.NOTIFICATION_ACTION_KEY)){
			//The event is a PUSH notification from GCM
			if(LOG_ENABLE)
				Log.d(NotificationModule.TAG,"Notification received. Sending show order to broadcast.");
			
			if(intent.getExtras()!=null){
				//Tells the UI to show the alert for the notification (using the BroadcastReceiver "CustomNotificationReceiver".
		        Intent intentOpenAlert = new Intent(NotificationModule.NEW_NOTIFICATION_ACTION);
		        intentOpenAlert.putExtras(intent.getExtras());
		        
		        context.sendBroadcast(intentOpenAlert);
			}
		}
	}
	
	
	
	//AUXILIAR
	
	
	public static final String GCM_PREF_NAME = "GCM_PREFS";
	
	private static final String GCM_PREF_KEY_REGID = "GCM_PREF_REG_ID";
	private static final String GCM_PREF_KEY_APP_VERSION = "GCM_PREF_APP_VERSION";
	private static final String GCM_PREF_KEY_REG_ERROR_CODE = "GCM_PREF_REG_ERROR_CODE";
	private static final String GCM_PREF_KEY_UNREG_ERROR_CODE = "GCM_PREF_UNREG_ERROR_CODE";
	
	public static final String GCM_PREF_KEY_APP_NOTIFICATION_ACTIVITY_TO_CALL = "GCM_PREF_APP_NOTIFICATION_ACTIVITY_TO_CALL";
	public static final String GCM_PREF_KEY_APP_NOTIFICATION_ONNOTRECEIVEDTHREAD_TO_CALL = "GCM_PREF_APP_NOTIFICATION_ONNOTRECEIVEDTHREAD_TO_CALL";
	public static final String GCM_PREF_KEY_APP_NOTIFICATION_TITLE = "GCM_PREF_APP_NOTIFICATION_TITLE";
	public static final String GCM_PREF_KEY_APP_NOTIFICATION_MULTIPLENOT = "GCM_PREF_APP_NOTIFICATION_MULTIPLENOT";
	public static final String GCM_PREF_KEY_APP_NOTIFICATION_GROUPMULTIPLENOTKEY = "GCM_PREF_APP_NOTIFICATION_GROUPMULTIPLENOTKEY";
	
	
	class GCMUnRegistration extends Thread implements Runnable {
		
		private Context context;
		
		public GCMUnRegistration (Context context) {
			this.context = context;
		}
		
		
		@Override
		public void run() {
			try {
				doUnRegistration();
				//We will receive an intent once the un-registration is done and
				//propagated in Google GCM servers. It can last up to 5 minutes.
			} catch (GCMException e) {
				ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_UNREG_ERROR_CODE, Integer.class, e.errorCode);				
			}			
		}
		
		private void doUnRegistration() throws GCMException {
			String regId = getRegistrationId(context);
			if(regId!=null && regId.length()>0){
				try {
					gcm.unregister();
					
					if(NotificationModule.unregisterRunnable!=null &&
		        	   !NotificationModule.unregisterRunnable.isAlive()){        		
		    			Thread t = new Thread(NotificationModule.unregisterRunnable);
		    	        t.start();
		    		}
				} catch (IOException e) {
					// If there is an error, don't just keep trying to register.
		            // Require the user to click a button again, or perform
		            // exponential back-off.
					if(LOG_ENABLE)
						Log.i(TAG, "Error unregistering from GCM [" + e.getMessage() + "].");
					
					throw new GCMException(GCMException.ERROR_GCM_UNREG, "Error unregistering from GCM servers."); 
				}
			}
		}
		
	}
	
	class GCMRegistration extends Thread implements Runnable {
		
		private Context context;
		
		public GCMRegistration (Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			try {
	    		doRegistration();
				ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_REG_ERROR_CODE, Integer.class, GCMException.NO_ERROR_GCM);
				
				if(NotificationModule.registerRunnable!=null &&
		    	   !NotificationModule.registerRunnable.isAlive()){    		
		    		Thread t = new Thread(NotificationModule.registerRunnable);
		    		t.start();
				}
				
			} catch (GCMException e) {
				ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_REG_ERROR_CODE, Integer.class, e.errorCode);				
			}	
		}
		
		private void doRegistration() throws GCMException {			
			
			try {
				checkPlayServices(context);
				
				gcm = GoogleCloudMessaging.getInstance(context);
				
				String gcmRegistrationId = getRegistrationId(context);			
				if(gcmRegistrationId==null) {
					//Need to register
					
					//Get registration Id
					gcmRegistrationId = gcm.register(SENDER_ID);
					
					//Store registration Id
					if(!ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_REGID, String.class, gcmRegistrationId)){
						if(LOG_ENABLE)
							Log.i(TAG, "Save GCM RegistrationId in preferences error (RegId: " + gcmRegistrationId + ").");
						throw new GCMException(GCMException.ERROR_GCM_SAVING_REGID, "Error saving the GCM registrationId.");					
					}
					//Save the application version for the GCM registrationId.
					//(To afterwards determine if a new registration is required when appkication
					// gets updated)
					int currentVersion = ToolBox.application_getVersionCode(context);
					ToolBox.prefs_savePreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_VERSION, Integer.class, currentVersion);
				}else{
					if(LOG_ENABLE)
						Log.i(TAG, "Device already registered in GCM. GCM RegistrationId: " + gcmRegistrationId + ".");
				}
				
			} catch (GCMException e) {
				if(LOG_ENABLE)
					Log.i(TAG, "Error getting GCM RegistrationId from Google. Google Play services issue [" + e.getMessage() + "].", e); 
				
				throw e;
				
			} catch (IOException e) {
				// If there is an error, don't just keep trying to register.
	            // Require the user to click a button again, or perform
	            // exponential back-off.
				if(LOG_ENABLE)
					Log.i(TAG, "Error getting GCM RegistrationId from Google [" + e.getMessage() + "].");
				
				throw new GCMException(GCMException.ERROR_GCM_GETTING_REGID, "Error getting the GCM registrationId from Google [" + e.getMessage() + "]", e);
			}
			
		}
	}
	
	
	/*
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 * 
	 * @param context
	 */
	private void checkPlayServices(Context context) throws GCMException {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            throw new GCMException(GCMException.ERROR_GCM_NOT_ENABLED_INSTALL, "GCM: Google Play services not installed or disabled!.");
	        } else {
	        	throw new GCMException(GCMException.ERROR_GCM_DEVICE_NOT_SUPPORTED, "GCM: Device is not supported by Google Play Services!.");
	        }	        
	    }
	}
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public static String getRegistrationId(Context context) {
		
		String gcmRegistrationId = (String)ToolBox.prefs_readPreference(context, GCM_PREF_NAME, GCM_PREF_KEY_REGID, String.class);
		if(gcmRegistrationId==null) {
			//Not registered yet
			return null;
		}
		
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredAppVersion = (Integer)ToolBox.prefs_readPreference(context, GCM_PREF_NAME, GCM_PREF_KEY_APP_VERSION, Integer.class);	    
	    int currentVersion = ToolBox.application_getVersionCode(context);
	    if (registeredAppVersion != currentVersion) {
	        //App version changed. Need to register again
	        return null;
	    }
	    
	    //Return the current registration id if is OK.
	    return gcmRegistrationId;
	}	
	

}
