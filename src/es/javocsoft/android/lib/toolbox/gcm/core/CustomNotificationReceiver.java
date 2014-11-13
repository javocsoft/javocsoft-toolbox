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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;


/**
 * Used to show the notification in the application UI.
 * 
 * @author JavocSoft 2014
 * @since  2014
 * 
 */
public class CustomNotificationReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equals(NotificationModule.NEW_NOTIFICATION_ACTION)){
			if(NotificationModule.LOG_ENABLE)
				Log.d(NotificationModule.TAG,"CustomNotificationReceiver. A received notification is going to be shown.");
				
			//Do something when notification is opened.
	        if(NotificationModule.ackRunnable!=null &&
	           !NotificationModule.ackRunnable.isAlive()){
	        	
	        	if(NotificationModule.LOG_ENABLE)
	        		Log.i(NotificationModule.TAG, "ACK.");
	        		//Set the intent extras
	        		NotificationModule.ackRunnable.setNotificationBundle(intent.getExtras());
	        		Thread tAck = new Thread(NotificationModule.ackRunnable);
	        		tAck.start();
	        }
		}
	}
	
	
	//AUXILIAR CLASSES
	
	
	
	/**
	 * This class allows to do something when user opens the notification.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2013
	 */
	public static abstract class OnAckCallback extends Thread implements Runnable {
		
		private Bundle notificationBundle;
		
		public OnAckCallback() {}
		
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
			return NotificationModule.APPLICATION_CONTEXT;
		}
	}
}
