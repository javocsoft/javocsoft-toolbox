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
package es.javocsoft.android.lib.toolbox.gcm.core;

import java.lang.reflect.Constructor;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;

/**
 * Service responsible for handling GCM messages.
 * 
 * It will process and create a notification in the 
 * system status bar.
 * 
 * 
 * @author JavocSoft 2014
 * @since  2014
 * 
 */
public class GCMIntentService extends IntentService {
	
	
	public GCMIntentService() {
        super(NotificationModule.SENDER_ID);        
    }
	
	
	
	
	@Override
	protected void onHandleIntent(Intent intent) {		
		if(NotificationModule.LOG_ENABLE)
    		Log.i(NotificationModule.TAG, "Received a new notification message");
    	
		//1.- Generate the notification in the task bar.
		generateNotification(super.getApplicationContext().getApplicationContext(), intent);
        
		//2.- Run the runnable set for a new notification received event.				
		launchOnNewNotificationEventRunnable(intent);
        
        Log.i("SimpleWakefulReceiver", "Completed service @ " + SystemClock.elapsedRealtime());
        CustomGCMBroadcastReceiver.completeWakefulIntent(intent);
	}
    
    
    
    //AUXILIAR
    
	/**
	 * Try to launch the configured runnable (if set) fro the
	 * GCM OnNewNotification received event.
	 *  
	 * @param intent
	 */
	private void launchOnNewNotificationEventRunnable(Intent intent) {
		//2.- Run the runnable set for a new notification received event.				
		if(NotificationModule.doWhenNotificationRunnable==null) {
			//...if null, we try to get the runnable from the saved configuration
			//in the SharedPreferences. This could happen if the app was closed because
			//the service does not know nothing when is called in this case.
			String notificationOnNotReceivedThreadToCall = (String)ToolBox.prefs_readPreference(super.getApplicationContext().getApplicationContext(), 
															NotificationModule.GCM_PREF_NAME, 
															NotificationModule.GCM_PREF_KEY_APP_NOTIFICATION_ONNOTRECEIVEDTHREAD_TO_CALL, String.class);
			Log.i(NotificationModule.TAG, "Runnable to run when a new notification is received: " + notificationOnNotReceivedThreadToCall);
			
			if(notificationOnNotReceivedThreadToCall!=null) {
				//Get a new instance from the class with reflection.
				try{				
					Class<?> c = Class.forName(notificationOnNotReceivedThreadToCall);
					Constructor<?> cons = c.getConstructor();
					Object onNewNotificationRunnableObject = cons.newInstance();
					NotificationModule.doWhenNotificationRunnable = (OnNewNotificationCallback)onNewNotificationRunnableObject;
					NotificationModule.doWhenNotificationRunnable.context = getApplicationContext();
					
				}catch(Exception e) {
					if(NotificationModule.LOG_ENABLE)
						Log.e(NotificationModule.TAG,"Runnable for a new notification received could not be run. " +
								"Class could not be found/get (" + e.getMessage() + ").", e);
				}
			}else{
				if(NotificationModule.LOG_ENABLE)
					Log.i(NotificationModule.TAG,"No Runnable(NULL in SharedPreferences) specified for a new notification received event.");
			}
		}
		
		//Do something when new notification arrives.
        if(NotificationModule.doWhenNotificationRunnable!=null &&
		   !NotificationModule.doWhenNotificationRunnable.isAlive()){
        	//Set the intent extras
        	NotificationModule.doWhenNotificationRunnable.setNotificationBundle(intent.getExtras());
        	//Launch the thread
			Thread t = new Thread(NotificationModule.doWhenNotificationRunnable);			
			t.start();	
		}
	}
	
    /**
     * Creates the Android system notification to alert the user.
     *  
     * @param context
     * @param i
     */
    private void generateNotification(Context context, Intent i) {
        
    	
    	String notStyle = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_STYLE_KEY);
    	String notTitle = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_TITLE_KEY);
    	String notMessage = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_MESSAGE_KEY);
    	String notTicker = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_TICKER_KEY);
    	String notContentInfo = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_CONTENT_INFO_KEY);
    	String notBigStyleTitle = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_TITLE_KEY);
    	String notBigStyleContent = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_CONTENT_KEY);    	
    	String notBigStyleSummary = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_SUMMARY_KEY);
    	String notBigStyleImage = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_IMAGE_KEY);
    	String notBigStyleLargeIcon = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_LARGE_ICON_KEY);
    	String notBigStyleInboxContent = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_INBOX_CONTENT_KEY);
    	String notBigStyleInboxLineSeparator = getNotificationKeyContent(i.getExtras(), NotificationModule.ANDROID_NOTIFICATION_BIG_STYLE_INBOX_LINE_SEPARATOR_KEY);
    	
    	
    	//This service must be isolated from the possible status of the app to be able to
    	//do its job even if the app is closed. This is the reason we get the required stuff
    	//from the previously saved information in SharedPreferences.
    	String notificationActToCall = (String)ToolBox.prefs_readPreference(context, NotificationModule.GCM_PREF_NAME, 
    			NotificationModule.GCM_PREF_KEY_APP_NOTIFICATION_ACTIVITY_TO_CALL, String.class);		 
    	NotificationModule.NOTIFICATION_TITLE = (String)ToolBox.prefs_readPreference(context, NotificationModule.GCM_PREF_NAME, 
    			NotificationModule.GCM_PREF_KEY_APP_NOTIFICATION_TITLE, String.class);
    	NotificationModule.multipleNot = (Boolean)ToolBox.prefs_readPreference(context, NotificationModule.GCM_PREF_NAME, 
    			NotificationModule.GCM_PREF_KEY_APP_NOTIFICATION_MULTIPLENOT, Boolean.class);
		NotificationModule.groupMultipleNotKey = (String)ToolBox.prefs_readPreference(context, NotificationModule.GCM_PREF_NAME, 
    			NotificationModule.GCM_PREF_KEY_APP_NOTIFICATION_GROUPMULTIPLENOTKEY, String.class);
    	
    	try{
    		//Get the Activity class to call when notification is opened.
			Class<?> clazz = Class.forName(notificationActToCall);			
			NotificationModule.NOTIFICATION_ACTIVITY_TO_CALL = clazz;
			
			//Get the notification style.
			ToolBox.NOTIFICATION_STYLE nStyle = ToolBox.NOTIFICATION_STYLE.NORMAL_STYLE;
			try{
				if(notStyle!=null && notStyle.length()>0){
					nStyle = ToolBox.NOTIFICATION_STYLE.valueOf(notStyle);
				}				
			}catch(Exception e) {/* Invalid value, we use the NORMAL_STYLE. */}
			
			
	        ToolBox.notification_create(context, 
	        		true, null, false,
	        		NotificationModule.multipleNot, NotificationModule.groupMultipleNotKey, 
	        		NotificationModule.NOTIFICATION_ACTION_KEY, 
	        		(notTitle!=null?notTitle:NotificationModule.NOTIFICATION_TITLE), notMessage, 
	    			notTicker, notContentInfo, 
	    			notBigStyleTitle, notBigStyleContent,
	    			notBigStyleSummary, notBigStyleImage, 
	    			notBigStyleInboxContent, notBigStyleInboxLineSeparator, 
	    			NotificationModule.notBackgroundColor,
	    			NotificationModule.NOTIFICATION_ACTIVITY_TO_CALL, 
	    			i.getExtras(), false, 
	    			ToolBox.NOTIFICATION_PRIORITY.DEFAULT, 
	    			nStyle, 
	    			ToolBox.NOTIFICATION_LOCK_SCREEN_PRIVACY.PRIVATE, 
	    			(notBigStyleLargeIcon!=null?notBigStyleLargeIcon:null),
	    			null, 
	    			null,
	    			ToolBox.NOTIFICATION_PROGRESSBAR_STYLE.NONE, 
	    			null, null,
					null, NotificationModule.vibrate);
	        
	    	
	    	if(NotificationModule.LOG_ENABLE)
	    		Log.d(NotificationModule.TAG, "Notification created for the recieve PUSH message.");
	    	
			
		}catch(Exception e) {
			if(NotificationModule.LOG_ENABLE)
				Log.e(NotificationModule.TAG,"Notification could not be created for the received PUSH message. " +
						"Notification activity to open class could not be found (" + e.getMessage() + ").", e);			
		} 
    }
    
    
    /**
     * Gets the string content from the notification bundle.
     *  
     * @param extras
     * @param key
     * @return
     */
    private String getNotificationKeyContent(Bundle extras, String key) {
    	String res = null;
    	
    	if(extras!=null && extras.get(key)!=null){
    		res = (String)extras.get(key); 
    	}
    	
    	return res;
    }
    
    
    
    //AUXILIAR CLASSES
    
    /**
	 * This class allows to do something with the received notification.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnNewNotificationCallback extends Thread implements Runnable {
		
		private Bundle notificationBundle;
		public Context context;
		
		public OnNewNotificationCallback() {}
		
		@Override
		public void run() {
			pre_task();
			task();
			post_task();
		}
		    	
		protected abstract void pre_task();
		protected abstract void task();
		protected abstract void post_task();
		
		
		public void setNotificationBundle(Bundle notificationBundle) {
			this.notificationBundle = notificationBundle;
		}
		
		/**
		 * Gets the notification extras.
		 * 
		 * @return
		 */
		protected Bundle getExtras() {
			return notificationBundle;
		}
		
		/**
		 * Gets the context.
		 * 
		 * @return
		 */
		protected Context getContext(){
			return context;
		}
	}
    
    /**
	 * This class allows to do something when registration is done.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnRegistrationCallback extends Thread implements Runnable {
		
		
		protected OnRegistrationCallback() {}
		
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
		 * Gets the GCM registration token.
		 * 
		 * @return
		 */
		protected String getGCMRegistrationToken() {
			if(getContext()!=null)
				return NotificationModule.getRegistrationId(getContext());
			else
				return null;
		}
		
		/**
		 * Gets the context.
		 * 
		 * @return
		 */
		protected Context getContext(){
			return NotificationModule.APPLICATION_CONTEXT;
		}
	}
	
	/**
	 * This class allows to do something when unregistration is done.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnUnregistrationCallback extends Thread implements Runnable {
		
		
		protected OnUnregistrationCallback() {}
		
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
		 * Gets the context.
		 * 
		 * @return
		 */
		protected Context getContext(){
			return NotificationModule.APPLICATION_CONTEXT;
		}
	}

	
}
