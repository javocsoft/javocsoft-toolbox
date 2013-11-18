package es.javocsoft.android.lib.toolbox.gcm.core;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;

/**
 * Service responsible for handling GCM messages.
 * 
 * See:
 *  http://developer.android.com/reference/com/google/android/gcm/server/package-summary.html
 * 	http://developer.android.com/reference/com/google/android/gcm/GCMRegistrar.html
 * 
 * @author JavocSoft 2013
 * @since  2013
 */
public class GCMIntentService extends GCMBaseIntentService {

    private static Context context;
    
    public GCMIntentService() {
        super(NotificationModule.SENDER_ID);        
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
    	if(NotificationModule.LOG_ENABLE)
    		Log.i(NotificationModule.TAG, "Device registered with id = " + registrationId);
    	
    	GCMIntentService.context = context;
    	
    	if(NotificationModule.registerRunnable!=null &&
    	   !NotificationModule.registerRunnable.isAlive()){    		
    		Thread t = new Thread(NotificationModule.registerRunnable);
    		t.start();
		}
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
    	if(NotificationModule.LOG_ENABLE)
    		Log.i(NotificationModule.TAG, "Device unregistered");
    	
        if (GCMRegistrar.isRegisteredOnServer(context)) {        	
        	if(NotificationModule.unregisterRunnable!=null &&
        	   !NotificationModule.unregisterRunnable.isAlive()){        		
    			Thread t = new Thread(NotificationModule.unregisterRunnable);
    	        t.start();
    		}
        }
    }
    
    @Override
    protected void onMessage(Context context, Intent intent) {
    	if(NotificationModule.LOG_ENABLE)
    		Log.i(NotificationModule.TAG, "Received a new notification message");
    	
        generateNotification(context.getApplicationContext(), intent);
    }

    @Override
    public void onError(Context context, String errorId) {
    	if(NotificationModule.LOG_ENABLE)
    		Log.i(NotificationModule.TAG, "Received error: " + errorId);
    }
    
    /*@Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
      
    }*/

    /*@Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);        
        return super.onRecoverableError(context, errorId);
    }*/

    
    
    
    //AUXILIAR
        
    /*
     * Creates the Android systen notification to alert the user.
     *  
     * @param context
     * @param i
     */
    private static void generateNotification(Context context, Intent i) {
        
    	String message = (String)i.getExtras().get(NotificationModule.ANDROID_MESSAGE_KEY);
    	
    	ToolBox.notification_generate(context, 
    			true, -1, 
    			NotificationModule.multipleNot, NotificationModule.groupMultipleNotKey, 
    			NotificationModule.NOTIFICATION_ACTION_KEY, 
    			NotificationModule.NOTIFICATION_TITLE, message, 
    			NotificationModule.NOTIFICATION_ACTIVITY_TO_CALL, i.getExtras(), 
    			false);
    	
    	if(NotificationModule.LOG_ENABLE)
    		Log.d(NotificationModule.TAG, "Notification created for the recieve PUSH message.");    	
    }
    
    
    //AUXILIAR CLASSES
    
    /**
	 * This class allows to do something when registration is done.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnRegistrationRunnableTask extends Thread implements Runnable {
		
		protected OnRegistrationRunnableTask() {}
		
		@Override
		public void run() {
			pre_task();
			task();
			post_task();
			
		}
		    	
		protected abstract void pre_task();
		protected abstract void task();
		protected abstract void post_task();
		
		
		/**
		 * Allows to tell the GCM that this application has been 
		 * successfully registered with your server-side back-end.
		 */
		protected void setRegisteredOnServersideFlag(){
			GCMRegistrar.setRegisteredOnServer(context, true);
		}
		
		/**
		 * Gets the GCM registration token.
		 * 
		 * @return
		 */
		protected String getGCMRegistrationToken() {
			if(context!=null)
				return GCMRegistrar.getRegistrationId(context);
			else
				return null;
		}
	}
	
	/**
	 * This class allows to do something when unregistration is done.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnUnregistrationRunnableTask extends Thread implements Runnable {
		
		protected OnUnregistrationRunnableTask() {}
		
		@Override
		public void run() {
			pre_task();
			task();
			post_task();
		}
		    	
		protected abstract void pre_task();
		protected abstract void task();
		protected abstract void post_task();
		
		/**
		 * Allows to tell the GCM that this application has been successfully 
		 * unregistered from your server-side back-end.
		 */
		protected void setNotRegisteredOnServersideFlag(){
			GCMRegistrar.setRegisteredOnServer(context, false);
		}
	}
}
