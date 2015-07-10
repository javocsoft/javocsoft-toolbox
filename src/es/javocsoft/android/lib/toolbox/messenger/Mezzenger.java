/*
 * Copyright (C) 2010-2015 - JavocSoft - Javier Gonzalez Serrano
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
package es.javocsoft.android.lib.toolbox.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


/**
 * This class allows to connect to a Messenger, see 
 * {@link Messenger}, and send message through its channel.
 * 
 * @author JavocSoft, 2015
 * @version 1.0<br>
 * $Rev$<br>
 * $LastChangedDate$<br>
 * $LastChangedBy$
 */
public class Mezzenger {

	private static final String TAG = "ToolBox:Messenger";
	private String name = null;
	private Context context = null;
	
	/** 
	 * Messenger for communicating with the service. 
	 *  	
	 * http://developer.android.com/guide/components/bound-services.html#Messenger
	 * */
	private Messenger messengerService = null;	
    /** Flag indicating whether we have called bind on the service. */
	private boolean messengerBound;
	
	/**
	 * Connection listener for the messenger.
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
		
		public void onServiceConnected(ComponentName className, IBinder service){
			messengerService = new Messenger(service);
			messengerBound = true;
			Log.i(TAG, "Messenger [" + name + "] BINDED with '" + className.getClassName() + "'");
		}
		
		public void onServiceDisconnected(ComponentName className) {
			messengerService = null;
			messengerBound = false;
			Log.i(TAG, " Messenger [" + name + "] UN-BINDED from '" + className.getClassName() + "'");
		}
	};
	
	
	
	/**
	 * Creates an instance of a Messenger. This will
	 * allow to connect with an external/internal messenger
	 * service.
	 * 
	 * @param name	A name to this messenger
	 * @param context
	 */
	public Mezzenger(String name, Context context) {
		this.name = name;
		this.context = context;
	}

	
	//METHODS
	
	/**
	 * Opens the communication with a third party messenger.<br><br>
	 * 
	 * We have to provide the messenger service ACTION.
	 * 
	 * @param context	
	 * @param action	The action name to initialize the communication 
	 * 					with the Messenger.
	 */
	public void connect(final String action) {
		
		Thread t = new Thread(new Runnable() {			
			@Override
			public void run() {
				Intent i = new Intent(action);
				Log.i(TAG, " Messenger [" + name + "] connecting (" + action +") ...");
		    	context.bindService(i, mConnection, Context.BIND_AUTO_CREATE);
			}
		});    	
		t.start();
    }
	
	/**
	 * Opens the communication with a third party messenger.<br><br>
	 * 
	 * We have to provide the messenger service ACTION.
	 * 
	 * @param context	
	 * @param action	The action name to initialize the communication 
	 * 					with the Messenger.
	 * @param messengerClass	The messenger service class to connect with.
	 */
	public void connect(final Context context, final String action, final Class<?> messengerClass) {
		
		Thread t = new Thread(new Runnable() {			
			@Override
			public void run() {
				Intent i = new Intent(context, messengerClass);
		    	i.setAction(action);
		    	Log.i(TAG, " Messenger [" + name + "] connecting (" + action +", class=" + messengerClass.getName() + ") ...");
		    	context.bindService(i, mConnection, Context.BIND_AUTO_CREATE);
			}
		});    	
		t.start();
    }
	
	/**
	 * Sends a message through the messenger channel.
	 * 
	 * @param whatValue
	 * @param arg1
	 * @param arg2
	 * @param object
	 */
	public void sendMessage(final int whatValue, final int arg1, final int arg2, final Object object) {
    	
    	Thread t = new Thread(new Runnable() {    		
			@Override
			public void run() {
				
				if (!messengerBound) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				};
				
				//Prepare the message.
		    	Message msg = Message.obtain(null, whatValue, arg1, arg2);
		    	if(object!=null){
		    		msg.obj = object;
		    	}
		    	
		    	//Send the message
		    	try {
		    		Log.i(TAG, " Messenger [" + name + "] sending message...");
		    		messengerService.send(msg);		    		
		    	} catch (RemoteException e) {
		    		Log.e(TAG, "Messenger send message ERROR: " + e.getMessage(), e);    		
		    	}
			}
		});    	
		t.start();
    }
	
}
