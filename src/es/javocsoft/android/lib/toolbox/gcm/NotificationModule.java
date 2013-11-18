package es.javocsoft.android.lib.toolbox.gcm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.gcm.core.CustomNotificationReceiver.OnAckRunnableTask;
import es.javocsoft.android.lib.toolbox.gcm.core.CustomNotificationReceiver.OnNewNotificationRunnableTask;
import es.javocsoft.android.lib.toolbox.gcm.core.GCMIntentService.OnRegistrationRunnableTask;
import es.javocsoft.android.lib.toolbox.gcm.core.GCMIntentService.OnUnregistrationRunnableTask;


/**
 * Notification module.
 * 
 * + Registration. 
 * 			1- Register the device for push with GCM.
 * 			2- Execute any process that your backend can need. 
 * + Un-registration.
 * 			1- Unregisters from Google GCM.
 * 			2- Execute any process that your backend can need.
 * + Notification receive.
 * + Notification ACKs
 * 			
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
	/** The key where the message of the notification is */
	public static final String ANDROID_MESSAGE_KEY = "message";
		
	private final static String APP_NOTIFICATION_ACTION_KEY = "<app_package>";
	/** Custom intent used to show the alert in the UI about a received push. */
    public static String SHOW_NOTIFICATION_ACTION = "com.google.android.gcm."+APP_NOTIFICATION_ACTION_KEY+".DISPLAY_MESSAGE";
    /** Used to do something in particular when a notification arrives */ 
    public static String NEW_NOTIFICATION_ACTION = "com.google.android.gcm."+APP_NOTIFICATION_ACTION_KEY+".NEW_DISPLAY_MESSAGE";
    
	public static String NOTIFICATION_TITLE = null;
    public static Class<?> NOTIFICATION_ACTIVITY_TO_CALL = null;
    
    /** Google API project id registered to use GCM. */
    public static String SENDER_ID = null;
    
    public static Context APPLICATION_CONTEXT;
    public static EnvironmentType ENVIRONMENT_TYPE = EnvironmentType.PRODUCTION;
    public static final String NOTIFICATION_ACTION_KEY = "NotificationActionKey";
    public static OnAckRunnableTask ackRunnable;
    public static OnRegistrationRunnableTask registerRunnable;
    public static OnUnregistrationRunnableTask unregisterRunnable;
    public static OnNewNotificationRunnableTask doWhenNotificationRunnable;
    
    public static boolean multipleNot;
    public static String groupMultipleNotKey;
    
    
    protected NotificationModule(Context context, EnvironmentType environmentType, String appSenderId,  
    		OnRegistrationRunnableTask registerRunnable, OnAckRunnableTask ackRunnable, OnUnregistrationRunnableTask unregisterRunnable,
			OnNewNotificationRunnableTask doWhenNotificationRunnable,
			boolean multipleNot, String groupMultipleNotKey) {
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
	}	
	
	
    /**
     * 
     * Initializes the GCM notification module.
     * 
     * @param context
     * @param environmentType
     * @param appSenderId
     * @param registerRunnable
     * @param ackRunnable
     * @param unregisterRunnable
     * @param doWhenNotificationRunnable
     * @param multipleNot 						Setting to True allows showing multiple notifications.
	 * @param groupMultipleNotKey 				If is set, multiple notifications can be grupped by this key.
	 *
     * @return
     */
	public static NotificationModule getInstance(Context context, EnvironmentType environmentType, String appSenderId,  
			OnRegistrationRunnableTask registerRunnable, OnAckRunnableTask ackRunnable, OnUnregistrationRunnableTask unregisterRunnable,
			OnNewNotificationRunnableTask doWhenNotificationRunnable,
			boolean multipleNot, String groupMultipleNotKey) {

		if (instance == null) {
			instance = new NotificationModule(context, environmentType, appSenderId,  
						registerRunnable, ackRunnable, unregisterRunnable, 
						doWhenNotificationRunnable,
						multipleNot, groupMultipleNotKey);			
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
	public void gcmRegisterDevice(Context context, String title, Class<?> clazz){
		
		EnvironmentType environment = ENVIRONMENT_TYPE;
		if(ToolBox.application_isAppInDebugMode(context)){
			environment = EnvironmentType.SANDBOX;
		}
		
		gcmRegisterDevice(context, environment, title, clazz);
	}
	
	/**
	 * Registers the device with Google Cloud Messaging system (GCM)
	 * 
	 * @param 	context		Context.
	 * @param 	environment	Allows to set the environment type. 
	 * @param	title		Title for the notification
	 * @param 	clazz		Class to call when clicking in the notification
	 */
	public void gcmRegisterDevice(Context context, final EnvironmentType environment,
								  String title, Class<?> clazz){
		
		ENVIRONMENT_TYPE = environment;
		NOTIFICATION_TITLE = title;
		NOTIFICATION_ACTIVITY_TO_CALL = clazz;
		
		// Verifies if the device supports GCM. Throws an exception if it does not 
		//(for instance, in case of the emulator if the AVD does not contain the Google APIs)
        GCMRegistrar.checkDevice(context);
        // Makes sure the manifest was properly set.       
        GCMRegistrar.checkManifest(context);
        
        final String regId = GCMRegistrar.getRegistrationId(context);
        if (regId==null || (regId!=null && regId.length()==0)) {            
            GCMRegistrar.register(context, SENDER_ID);            
        }else{
        	if(LOG_ENABLE)
        		Log.i(TAG, "Already registered with id " + regId);
        }
	}
	
	
	/**
	 * Returns the Google Cloud Messaging system (GCM) registration token.
	 * 
	 * @param context
	 * @return	The token or null if the device is not registered.
	 */
	public String gcmGetRegistrationToken(Context context){
		if(GCMRegistrar.isRegistered(context))
			return GCMRegistrar.getRegistrationId(context);
		else
			return null;
	}
	
	/**
	 * Returns the Android device unique id.
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
		if(GCMRegistrar.isRegistered(context)){
			//Un-register the device from GCM.
			GCMRegistrar.unregister(context);
		}
		GCMRegistrar.onDestroy(context);
	}
	
	/**
	 * Makes the UI show the alert for any received notification.
	 * 
	 * @param context
	 * @param intent
	 */
	public void gcmCheckForNotificationReceival(Context context, Intent intent){
		
		if(intent!=null && intent.getAction()!=null && 
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

}
