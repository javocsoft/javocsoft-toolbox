package es.javocsoft.android.lib.toolbox.gcm.core;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

/**
 * We create this class to avoid using the default GCMIntentService name. 
 * 
 * By default this Intent-Service should be called "GCMIntentService" and located
 * in the user main application package. We do not want this, so we override the
 * method that return the name :).
 * 
 * @author JavocSoft 2013
 * @since  2013
 *
 */
public class CustomGCMBroadcastReceiver extends GCMBroadcastReceiver {

	
	@Override
	protected String getGCMIntentServiceClassName(Context context) {
		
		return "es.javocsoft.android.lib.toolbox.gcm.core.GCMIntentService";
	}

}
