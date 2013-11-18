package es.javocsoft.android.lib.toolbox.gcm.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;


/**
 * Used to show the notification in the application UI.
 * 
 * @author JavocSoft 2013
 * @since  2013
 *
 */
public class CustomNotificationReceiver extends BroadcastReceiver {

	private static Bundle notificationbundle;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equals(NotificationModule.NEW_NOTIFICATION_ACTION)){
			if(NotificationModule.LOG_ENABLE)
				Log.d(NotificationModule.TAG,"CustomNotificationReceiver. A received notification is going to be shown.");
			
			notificationbundle = intent.getExtras();
			
			if(NotificationModule.doWhenNotificationRunnable!=null &&
			   !NotificationModule.doWhenNotificationRunnable.isAlive()){
				Thread t = new Thread(NotificationModule.doWhenNotificationRunnable);
				t.start();				
			}
			
	        if(NotificationModule.ackRunnable!=null &&
	           !NotificationModule.ackRunnable.isAlive()){
	        	
	        	if(NotificationModule.LOG_ENABLE)
	        		Log.i(NotificationModule.TAG, "ACK.");
	        	
	        		Thread tAck = new Thread(NotificationModule.ackRunnable);
	        		tAck.start();	        	
	        }
		}
	}
	
	
	//AUXILIAR CLASSES
	
	/**
	 * This class allows to do something with the received notification.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnNewNotificationRunnableTask extends Thread implements Runnable {
		
		public OnNewNotificationRunnableTask() {}
		
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
		 * Gets the notification extras.
		 * 
		 * @return
		 */
		protected Bundle getExtras() {
			return notificationbundle;
		}
	}
	
	/**
	 * This class allows to do something when user opens the notification.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnAckRunnableTask extends Thread implements Runnable {
		
		public OnAckRunnableTask() {}
		
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
		 * Gets the notification extras.
		 * 
		 * @return
		 */
		protected Bundle getExtras() {
			return notificationbundle;
		}
	}
}
