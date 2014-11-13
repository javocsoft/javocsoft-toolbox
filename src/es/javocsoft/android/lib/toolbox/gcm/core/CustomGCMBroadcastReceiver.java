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

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;



/**
 * This receiver will process the notifications from GCM. It will 
 * deliver each notification to a service to be processed, waiting 
 * until process of the notification is done. It also avoids the 
 * device to enter in suspend mode while the notification is being 
 * processed. 
 * 
 * CustomGCMBroadcastReceiver -> GCMIntentService -> CustomNotificationReceiver
 * (receives the notification>      (process)		   (user opens the not.)
 * 
 * @author JavocSoft 2014
 * @since  2014
 *
 */
public class CustomGCMBroadcastReceiver extends WakefulBroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(NotificationModule.TAG, "CustomGCMBroadcastReceiver called.");
        
		// Explicitly specify that GCMIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
        		GCMIntentService.class.getName());
        
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        Log.i("SimpleWakefulReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
	}

}
