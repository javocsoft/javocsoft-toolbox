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
package es.javocsoft.android.lib.toolbox.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.analytics.CampaignTrackingReceiver;

import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;

/**
 * This is a custom Google Analytics Tracking receiver for INSTALL_REFERRER
 * intents. The purpose of this custom receiver is:
 *
 *  - Catch the campaign data before it is delivered to GA Campaign
 *    Tracking receiver.
 *  - Forward the intent to Google Analytics Tracking receiver for
 *    normal behaviour.
 *
 *  The campaign URL can be made using the URL:
 *
 *      https://developers.google.com/analytics/devguides/collection/android/v4/campaigns?hl=es#google-play-url-builder
 *
 *      ...an example (the URL in the "referrer" url parameter is url encoded):
 *
 *      https://play.google.com/store/apps/details?id=es.javocsoft.basetest
 *                     &referrer=utm_source%3Dfacebook
 *                               %26utm_medium%3Dbanner
 *                               %26utm_content%3Dbanner1
 *                               %26utm_campaign%3DcampaignOne
 *
 *      The receiver can be test by using the ADN command:
 *
 *          adb shell am broadcast
 *                  -a com.android.vending.INSTALL_REFERRER
 *                  -n es.javocsoft.basetestapp/es.javocsoft.basetestapp.CustomCampaignTrackingReceiver
 *                  --es "referrer" "utm_source%3Dfacebook%26utm_medium%3Dbanner%26utm_content%3Dbanner1%26utm_campaign%3DcampaignOne"
 *
 *      ..if the test goes well an output like this should be seen:
 *
 *          Broadcasting: Intent { act=com.android.vending.INSTALL_REFERRER cmp=es.javocsoft.basetestapp/.CustomCampaignTrackingReceiver (has extras) }
 *          Broadcast completed: result=0
 *
 *	@author JavocSoft 2014
 * 	@since  2014		
 */
public class CustomCampaignTrackingReceiver extends BroadcastReceiver {

	
	public static OnProcessCampaignDataCallback onCampaignInfoReceivedCallback;
	
	public static final String NO_CAMPAIGN_INFO = "NONE";
	
	
    @Override
    public void onReceive(Context context, Intent intent) {

        // Pass the intent to other receivers.
        Uri uri = intent.getData();

        //Get the received Referrarl info
        CampaignInfo info = new CampaignInfo();
        if (uri != null) {
            Log.i("MeasureInstall", "URI:" + uri.getPath());
            if(uri.getQueryParameter("utm_source") != null) {
                // Use campaign parameters if available.
                info.setInstallReferral(uri.getPath());
            } else if (uri.getQueryParameter("referrer") != null) {
                info.setInstallReferral(uri.getQueryParameter("referrer"));
            }else{
                info.setInstallReferral(NO_CAMPAIGN_INFO + ". Not a valid URI parameter. Only 'utm_source' or 'referral' are accepted.");
            }
        }else{
            //We do not have an URI, we try to get the parameter from the extras
            String referralInfo = intent.getStringExtra("referrer");
            if(referralInfo!=null){
            	Log.i("MeasureInstall", "Referral Info:" + referralInfo);
                info.setInstallReferral(referralInfo);                
            }else {
                info.setInstallReferral(NO_CAMPAIGN_INFO + ". No URI in the intent data.");
                Log.i("MeasureInstall", "No URI.");
            }
        }

        //Do something if the user specifies.
        if(onCampaignInfoReceivedCallback!=null) {
        	Log.i("Analytics Campaign Module", "User specified an action for received Campaign information.");
    		
        	onCampaignInfoReceivedCallback.setCampaignData(info);
    		Thread tAck = new Thread(onCampaignInfoReceivedCallback);
    		tAck.start();
        }
        
        // When you're done, pass the intent to the Google Analytics receiver.
        new CampaignTrackingReceiver().onReceive(context, intent);
    }
    
    
    
    /**
     * This class allows to implement a class that can do something 
     * when the campaign data is received.
     * 
     * @author JavocSoft 2014.
     * @since 2014
     */
    public static abstract class OnProcessCampaignDataCallback extends Thread implements Runnable {
    	
    	private CampaignInfo campaignInfo;
    	
    	public OnProcessCampaignDataCallback() {}
    	
    	@Override
    	public void run() {
    		pre_task();
    		task();
    		post_task();
    	}
    	    	
    	protected abstract void pre_task();
    	protected abstract void task();
    	protected abstract void post_task();
    	
    	public void setCampaignData(CampaignInfo campaignInfo) {
    		this.campaignInfo = campaignInfo;
    	}
    	
    	/**
    	 * Gets the campaign information.
    	 * 
    	 * @return
    	 */
    	protected CampaignInfo getCampaignInfo() {
    		return campaignInfo;
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
