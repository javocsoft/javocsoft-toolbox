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

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import es.javocsoft.android.lib.toolbox.ToolBox;

/**
 * Base class for Google AdMob Ads.
 *
 * @author JavocSoft 2014
 * @version 1.0
 */
public class AdBase extends Fragment {

    /**
     * Prepares an AdMob BANNER request.
     *
     * @param adView
     */
    protected void bannerAdRequest(AdView adView){
        AdRequest adRequest = requestAdBanner();
        if(adRequest!=null)
            adView.loadAd(adRequest);
    }

    /**
     * Prepares an AdMob interstitial.
     *
     * @param adView
     */
    protected AdRequest interstitialAdRequest(InterstitialAd adView, Activity activity){
        return requestAdsInterstitial(activity);
    }



    //PRIVATE AND AUXILIAR METHODS


    private AdRequest requestAdBanner() {
        if(getView()!=null) { //Avoid NullPointerException in case of no View.
            return requestAds(getActivity());
        }else{
            return null;
        }
    }

    private AdRequest requestAdsInterstitial(Activity activity) {
        return requestAds(activity);
    }

    private AdRequest requestAds(Activity activity) {
        Log.i(ToolBox.TAG, "Ads: Creating adRequest.");

        // Create ad request.
        AdRequest.Builder adBuilder = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        if (ToolBox.application_isAppInDebugMode(activity)) {
            //We exclude current device to avoid being penalized by AdMob.
            String deviceAdmobId = getAdmobDeviceId(activity.getApplicationContext());
            Log.i(ToolBox.TAG, "AdMob -> Debug Mode ON. Adding current device AdMobId '" +
                    deviceAdmobId + "' " + "to test device list.");
            adBuilder.addTestDevice(deviceAdmobId);
        }

        AdRequest adRequest = adBuilder.build();

        return adRequest;
    }

    private static final String getAdmobDeviceId(final Context context) {

        final String android_id = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        final String deviceId = md5(android_id).toUpperCase(Locale.US);

        return deviceId;
    }

    private static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.e(ToolBox.TAG, "AdMob -> Error doing MD5! [" + e.getMessage() + "].", e);
        }

        return "";
    }
}
