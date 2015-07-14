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
package es.javocsoft.android.lib.toolbox.messenger.service.incoming;

import es.javocsoft.android.lib.toolbox.messenger.service.MessengerService;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Handler for messenger incoming messages delivered from clients.<br><br>
 * 
 * See {@link MessengerService}.
 * 
 * @author JavocSoft, 2015
 * @version 1.0<br>
 * $Rev: 773 $<br>
 * $LastChangedDate: 2015-07-14 09:53:17 +0200 (Tue, 14 Jul 2015) $<br>
 * $LastChangedBy: admin $
 */
public abstract class MessengerIncomingHandler extends Handler {

	private final String TAG = "ToolBox:MessengerIncoming";
	
	protected Context context = null;
	
	
	public MessengerIncomingHandler(Context context) {
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		Log.i(TAG, "Messenger received WHAT value: " + msg.what);
		doWork(msg);
	}
	
	/**
	 * Implement this method to do whatever you need with 
	 * the message.
	 * 
	 * @param msg
	 */
	protected abstract void doWork(Message msg);
	
}
