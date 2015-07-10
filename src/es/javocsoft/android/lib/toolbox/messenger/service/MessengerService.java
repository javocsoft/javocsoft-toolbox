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
package es.javocsoft.android.lib.toolbox.messenger.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import es.javocsoft.android.lib.toolbox.messenger.service.incoming.MessengerIncomingHandler;

/**
 * A Messenger service to receive messages from other 
 * applications.
 * 
 * @author JavocSoft, 2015
 * @version 1.0<br>
 * $Rev$<br>
 * $LastChangedDate$<br>
 * $LastChangedBy$
 *
 */
public abstract class MessengerService extends Service {
	
	protected Context context;
	
	/**
     * Target we publish for clients to send messages to MessengerIncomingHandler.
     */
    protected static Messenger messenger = null;
    
	
    @Override
	public void onCreate() {
		super.onCreate();		
		context = getApplicationContext();
	}


	@Override
    public IBinder onBind(Intent intent) {
    	return getMessenger().getBinder();
    }
	
	/**
	 * Returns an instance of {@link MessengerIncomingHandler}
	 * @return
	 */
	protected abstract MessengerIncomingHandler getMessageIncomingHandler();
	
	//AUXILIAR
    
	/**
	 * Gets a Messenger for incoming communications.
	 * 
	 * @return	The Messenger.
	 */
    public Messenger getMessenger() {
    	if(messenger==null)
    		messenger = new Messenger(getMessageIncomingHandler());
    	
    	return messenger;
    }
}
