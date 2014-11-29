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
package es.javocsoft.android.lib.toolbox.ads;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.gcm.NotificationModule;

/**
 * Google AdMob interstitial lifecycle listener.
 *
 * @author JavocSoft 2014
 * @version 1.0
 */
public class InterstitialAdsListener extends AdListener {

    private InterstitialAd adView;
    private OnInterstitialClickCallback clickCallback;

    public InterstitialAdsListener(InterstitialAd adView, OnInterstitialClickCallback clickCallback) {
        this.adView = adView;
        this.clickCallback = clickCallback;
    }

    /** Called when an ad is loaded. */
    @Override
    public void onAdLoaded() {
        Log.d(ToolBox.TAG, "Ads: The banner was loaded.");
        adView.show();
    }

    /** Called when an ad failed to load. */
    @Override
    public void onAdFailedToLoad(int errorCode) {
        String message = "onAdFailedToLoad: " + getErrorReason(errorCode);
        Log.d(ToolBox.TAG, "Ads: Error loading ads [" + message + "].");
    }

    /**
     * Called when an Activity is created in front of the app (e.g. an
     * interstitial is shown, or an ad is clicked and launches a new Activity).
     */
    @Override
    public void onAdOpened() {
        Log.d(ToolBox.TAG, "Ads: Interstitial was shown or a banner was clicked!");
    }

    /** Called when an ad is clicked and about to return to the application. */
    @Override
    public void onAdClosed() {
        Log.d(ToolBox.TAG, "Ads: The banner was closed.");
    }

    /**
     * Called when an ad is clicked and going to start a new Activity that will
     * leave the application (e.g. breaking out to the Browser or Maps
     * application).
     */
    public void onAdLeftApplication() {
        Log.d(ToolBox.TAG, "Ads: The banner was clicked and application is going to be put in background.");
        if(clickCallback!=null)
        	clickCallback.start();        
    }




    /** Gets a string error reason from an error code. */
    private String getErrorReason(int errorCode) {
        String errorReason = "";
        switch(errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
        }
        return errorReason;
    }
    
    
    /**
	 * This class allows to do something when an user clicks
	 * on an interstitial.
	 * 
	 * @author JavocSoft 2013.
	 * @since 2014
	 */
	public static abstract class OnInterstitialClickCallback extends Thread implements Runnable {
		
		
		protected OnInterstitialClickCallback() {}
		
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
