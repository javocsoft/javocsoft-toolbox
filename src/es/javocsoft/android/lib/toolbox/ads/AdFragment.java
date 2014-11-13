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

import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import es.javocsoft.android.lib.toolbox.R;


/**
 * This fragment class allows to manage Google AdMob Banner ads.
 *
 * Do not forget:<br><br>
 *
 * To set <u>your AdMob banner id</u> and <u>banner size</u> in the "fragment_ad.xml" ads fragment
 * layout. Banner sizes explained here:<br><br>
 *     https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner?hl=es<br><br>
 *
 * See documentation for more information about the usage.
 *
 * @author JavocSoft 2014
 * @version 1.0
 */
public class AdFragment extends AdBase {

    private AdView mAdView;
    
    
    private String adUnitId = "";
    private final AdSize adSize = AdSize.BANNER;

    
    public AdFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
    	//Create the AdView fragment container layout
        View v = inflater.inflate(R.layout.fragment_ad, container, false);
        
        //Generate the AdView and configure it
        mAdView = new AdView(v.getContext());
        mAdView.setVisibility(View.GONE);
        RelativeLayout.LayoutParams mAdViewLayout = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mAdView.setLayoutParams(mAdViewLayout);
        //...set the Ads info
        mAdView.setAdSize(adSize);
        mAdView.setAdUnitId(adUnitId);
        
        //Add the AdView to the fragment container layout :)
        ((RelativeLayout)v).addView(mAdView);
        
        return v;
    }


    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        if(getView()!=null) {}
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public void hideAds() {
        if (mAdView != null &&
                (mAdView.isEnabled() || mAdView.getVisibility()==View.VISIBLE)) {
            mAdView.setEnabled(false);
            mAdView.setVisibility(View.GONE);
        }
    }

    public void showAds() {
        if (mAdView != null &&
                (!mAdView.isEnabled() || mAdView.getVisibility()==View.GONE)) {
            mAdView.setEnabled(true);
            mAdView.setVisibility(View.VISIBLE);
            bannerAdRequest(mAdView);
        }
    }

    public void forceRefresh() {
        if (mAdView != null){
            mAdView.setEnabled(true);
            mAdView.setVisibility(View.VISIBLE);
            bannerAdRequest(mAdView);
        }
    }

    public void setAdUnitId(String adUnitId) {
    	this.adUnitId = adUnitId;
    }
}
