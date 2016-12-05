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
package es.javocsoft.android.lib.toolbox;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import es.javocsoft.android.lib.toolbox.encoding.Base64;
import es.javocsoft.android.lib.toolbox.io.IOUtils;
import es.javocsoft.android.lib.toolbox.javascript.WebviewJavascriptInterface;


/**
 * This class will hold utility functions related with Android.
 * 
 * @author JavocSoft 2015
 * @since  2012
 */
public final class ToolBox {
	
	/** Enables or disables log */
	public static boolean LOG_ENABLE = true;
	/** Shared Preferences file under some data is saved by the Android Toolbox Library */
	public static final String PREF_FILE_NAME = "prefs_toolbox";
	
	/** Http Method type for a request. */
	public static enum HTTP_METHOD{POST,DELETE,GET};
	
	public static final String TAG = "javocsoft-toolbox: ToolBox";
	
	private static final int CONNECTION_DEFAULT_TIMEOUT = 5000; // 5 sgs.
	private static final int CONNECTION_DEFAULT_DATA_RECEIVAL_TIMEOUT = 10000; // 10 sgs.
	
	private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB

	/** A field name to use with notification Id. */
	public static final String NOTIFICATION_ID = "notificationId";
	
	public static final String NETWORK_OPERATOR_EMU = "Android";
	public static final String NETWORK_OPERATOR_NOSIM = "NO_SIM";

	/** See <a href="http://developer.android.com/intl/es/guide/topics/manifest/uses-sdk-element.html#ApiLevels">Android API Levels</a> */
	public static enum ApiLevel {
		
		LEVEL_1(1), LEVEL_2(2), LEVEL_3(3), LEVEL_4(4), LEVEL_5(5), LEVEL_6(6),
		LEVEL_7(7), LEVEL_8(8), LEVEL_9(9), LEVEL_10(10), LEVEL_11(11), LEVEL_12(12),
		LEVEL_13(13), LEVEL_14(14), LEVEL_15(15), LEVEL_16(16), LEVEL_17(17), LEVEL_18(18),
		LEVEL_19(19), LEVEL_20(20), LEVEL_21(21), LEVEL_22(22), LEVEL_23(23);

		private int value;

		ApiLevel(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}		
	}
	
	public static enum NETWORK_PROVIDER {
		MOVISTAR, YOIGO, VODAFONE, ORANGE, EMU, NOSIM, UNKNOWN
	};
			
	public static enum SCREEN_ORIENTATION {PORTRAIT, LANDSCAPE, SQUARE};
	
	/** Accepted HASH types. */
	public static enum HASH_TYPE{md5,sha1};
	
	/** The type of device by screen. */
	public static enum DEVICE_BY_SCREEN {DP320_NORMAL, DP480_TWEENER, DP600_7INCH, DP720_10INCH};
	
	/** The type of resolution that is using the device. */
	public enum DEVICE_RESOLUTION_TYPE {ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi, unknown};
	
	private static PowerManager.WakeLock wakeLock = null;
	
	private static Random random = null;
	
	private static Set<String> systemAppsList = null;
	
	/** Exit application style */
	public static enum EXIT_CONFIRMATION_STYLE {NONE, BACK_PRESS, DIALOG};
	
	public static final String NOTIFICATION_FLAG = "ToolBox-Notification-Flag";
	
	
	private ToolBox(){}
	
	//-------------- LOGGING --------------------------------------------------------------------------
	
	/**
	 * Enables or disables logging for ToolBox. By default the log
	 * is enabled.
	 * 
	 * @param enable	
	 */
	public static void logging_enableDebug(boolean enable) {
		LOG_ENABLE = enable;
	}
	
	//-------------- GSON -----------------------------------------------------------------------------
	
	/**
	 * Converts a GSON JSON LinkedMap of objects to list of objects.
	 * 
	 * @param jsonData	JSON string
	 * @param type		See http://hmkcode.com/gson-json-java/
	 * @return
	 */
	public static <T> List<T> gson_linkedMapAsList(String jsonData, java.lang.reflect.Type type) {
		// Now convert the JSON string back to your java object		
	    List<T> jsonObjectList = new Gson().fromJson(jsonData, type);	    
	    return jsonObjectList;
	}
	
	//--------------- URL Shortener ---------------------------------------------------------------------
	
	/**
	 * Generates a shortened URL for a specified URL using Google shortening API.
	 * <br><br>
	 * See <a href="https://developers.google.com/url-shortener/v1/getting_started">Google Shortener API</a>
	 * 
	 * @param apiKey	The Google Shortener API Key. Required in order to work.
	 * @param longURL	The URL that we need to get shortened
	 * @return	The shortened URL or null in case of error.
	 */
	public static String shortener_gooShortURL(String apiKey, String longURL) {
		String shortenedURL = null;
		
		String GOOGLE_API_SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=%s";
		URL url = null;
		String response=null;
        try {
        	BufferedReader reader;
            StringBuffer buffer;
            
            //Prepare the connection
            url = new URL(String.format(GOOGLE_API_SHORTENER_URL, apiKey));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(40000);
            con.setConnectTimeout(40000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //Prepare the JSON to send
            String json = "{\"longUrl\": \""+longURL+"\"}";
            //Write the JSON
            writer.write(json);
            writer.flush();
            writer.close();
            os.close();

            //Get response
            int status=con.getResponseCode();
            InputStream inputStream;
            if(status==HttpURLConnection.HTTP_OK)
            	inputStream=con.getInputStream();
            else
                inputStream = con.getErrorStream();

            reader= new BufferedReader(new InputStreamReader(inputStream));

            //Convert response to an String
            buffer= new StringBuffer();
            String line=null;
            while((line=reader.readLine())!=null) {
                buffer.append(line);
            }
            response= buffer.toString();
            if(LOG_ENABLE)
            	Log.d(TAG, "Shortening API RAW response: " + response);
            
            //Parse the response
            JSONObject jsonObject=new JSONObject(response);
            shortenedURL = jsonObject.getString("id");
            //"OK" for most URLs. If Google believes that the URL is fishy, status may be something else, such as "MALWARE".
            String urlStatus = jsonObject.getString("status");
            
            if(LOG_ENABLE)
            	Log.d(TAG, "Shortened URL: [" + shortenedURL + "], status: " + urlStatus);            	

        } catch (MalformedURLException e) {
        	Log.e(TAG, "Invalid shortening URL: [" + GOOGLE_API_SHORTENER_URL + "] for apiKey: " + apiKey + " for URL: " + longURL, e);
        } catch (ProtocolException e) {
        	Log.e(TAG, "Invalid protocol when shortening URL: [" + GOOGLE_API_SHORTENER_URL + "] for apiKey: " + apiKey + " for URL: " + longURL, e);
        } catch (IOException e) {
        	Log.e(TAG, "Error shortening URL: [" + GOOGLE_API_SHORTENER_URL + "] for apiKey: " + apiKey + " for URL: " + longURL + ", message: " + e.getMessage(), e);
        } catch (JSONException e) {
        	Log.e(TAG, "Error parsing shortening response for URL: [" + GOOGLE_API_SHORTENER_URL + "] for apiKey: " + apiKey + " for URL: " + longURL, e);
        } catch (Exception e) {
        	Log.e(TAG, "Unexpected error shortening URL for URL: [" + GOOGLE_API_SHORTENER_URL + "] for apiKey: " + apiKey + " for URL: " + longURL, e);
        } 
        
        return shortenedURL;
    }
	
	/**
	 * Expands a Google shortened URL for a specified shortened URL 
	 * using Google shortening API.
	 * <br><br>
	 * See <a href="https://developers.google.com/url-shortener/v1/getting_started">Google Shortener API</a>
	 * 
	 * @param apiKey	The Google Shortener API Key. Required in order to work.
	 * @param shortURL	The URL that we need to get shortened
	 * @return	The expanded URL or null in case of error.
	 */
	public static String shortener_gooExpandURL(String apiKey, String shortURL) {
		String expandedURL = null;
		
		String GOOGLE_API_SHORTENER_EXPAND_URL = "https://www.googleapis.com/urlshortener/v1/url?key=%s&shortUrl=%s";
		URL url = null;
		String response=null;
        try {
        	BufferedReader reader;
            StringBuffer buffer;
            
            //Prepare the connection
            url = new URL(String.format(GOOGLE_API_SHORTENER_EXPAND_URL, apiKey, shortURL));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(40000);
            con.setConnectTimeout(40000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
          
            //Get response
            int status=con.getResponseCode();
            InputStream inputStream;
            if(status==HttpURLConnection.HTTP_OK)
            	inputStream=con.getInputStream();
            else
                inputStream = con.getErrorStream();

            reader= new BufferedReader(new InputStreamReader(inputStream));

            //Convert response to an String
            buffer= new StringBuffer();
            String line=null;
            while((line=reader.readLine())!=null) {
                buffer.append(line);
            }
            response= buffer.toString();
            if(LOG_ENABLE)
            	Log.d(TAG, "Shortening API RAW response: " + response);
            
            //Parse the response
            JSONObject jsonObject=new JSONObject(response);
            expandedURL = jsonObject.getString("longUrl");
            //"OK" for most URLs. If Google believes that the URL is fishy, status may be something else, such as "MALWARE".
            String urlStatus = jsonObject.getString("status");
            
            if(LOG_ENABLE)
            	Log.d(TAG, "Shortened URL: [" + expandedURL + "], status: " + urlStatus);            	

        } catch (MalformedURLException e) {
        	Log.e(TAG, "Invalid expand URL: [" + GOOGLE_API_SHORTENER_EXPAND_URL + "] for apiKey: " + apiKey + " for URL: " + shortURL, e);
        } catch (ProtocolException e) {
        	Log.e(TAG, "Invalid protocol when expanding URL: [" + GOOGLE_API_SHORTENER_EXPAND_URL + "] for apiKey: " + apiKey + " for URL: " + shortURL, e);
        } catch (IOException e) {
        	Log.e(TAG, "Error expanding URL: [" + GOOGLE_API_SHORTENER_EXPAND_URL + "] for apiKey: " + apiKey + " for URL: " + shortURL + ", message: " + e.getMessage(), e);
        } catch (JSONException e) {
        	Log.e(TAG, "Error parsing expanded URL response for URL: [" + GOOGLE_API_SHORTENER_EXPAND_URL + "] for apiKey: " + apiKey + " for URL: " + shortURL, e);
        } catch (Exception e) {
        	Log.e(TAG, "Unexpected error expanding URL for URL: [" + GOOGLE_API_SHORTENER_EXPAND_URL + "] for apiKey: " + apiKey + " for URL: " + shortURL, e);
        }
        
        return expandedURL;
    }
	
	//--------------- ANIMATIONS ------------------------------------------------------------------------
	
	/**
	 * Applies an effect to an Activity load.<br><br>
	 * 
	 * <b>Notes</b>:<br><br>
	 * Animations will not run if they are disabled. To check it, enter into "Developer options" 
	 * in Setting (where you activated debugging mode), find the user's interface section and 
	 * check if the animation's transition scale have the animations disabled. If so, set it 
	 * to .5x<br><br>
	 * 
	 * There is a set of ready to use animations under the <b><code>toolbox_library/res/anim</code></b> folder.
	 * 
	 * @param from	The current activity.
	 * @param activityToIntent	The intent of the activity to launch.
	 * @param animationIn	
	 * @param animationOut
	 */
	public static void animation_applyAnimationToActivityLoad(final Activity from, final Intent activityToIntent, 
			final int animationIn, final int animationOut) {
		
	    //We make this in this way to ensure animation runs in the UI Thread.
		from.runOnUiThread(new Runnable() {
	    	public void run() {                		
	    		from.startActivity(activityToIntent);
	            from.finish();
	            from.overridePendingTransition(animationIn, animationOut);
	        }
	    });
	}	
	
	/**
	 * This method allows to get an Animation object from its resource id.
	 * 
	 * @param context
	 * @param animationResourceId	Animation XMLs should be in the res/anim folder.
	 * @return
	 */
	public static Animation animation_getFromAnimResourceId(Context context, int animationResourceId) {
		return android.view.animation.AnimationUtils.loadAnimation(context, animationResourceId);
	}
	
	/**
	 * Applies an animation to a view.
	 * 
	 * @param context
	 * @param applyTo
	 * @param animation	An animation object. You can get it from predefined ones with
	 * 					{@link ToolBox#animation_getFromAnimResourceId}
	 */
	public static void animation_applyAnimationTo(Context context, View applyTo, Animation animation) {
		if(animation!=null) {
			applyTo.startAnimation(animation);
		}else{
			if(LOG_ENABLE){
				Log.w(TAG, "Not a valid animation.");
			}
		}
	}
	
	/**
	 * Applies an animation to a view.
	 * 
	 * @param context
	 * @param applyTo
	 * @param animationResourceId	Animation XMLs should be in the res/anim folder.
	 */
	public static void animation_applyAnimationTo(Context context, View applyTo, int animationResourceId) {
		Animation animation = animation_getFromAnimResourceId(context, animationResourceId);
		animation_applyAnimationTo(context, applyTo, animation);
	}
	
	
	//--------------- ANALYTICS ------------------------------------------------------------------------
	
	/**
	 * Gets a new Analytics v4 tracker.
	 * 
	 * See: https://developers.google.com/analytics/devguides/collection/android/v4/
	 * 
	 * @param context				
	 * @param analyticsTrackingId
	 * @param debugMode	If enabled, GA operations will not be sent to Analytics but
	 * 					are logged.	
	 * @return
	 */
	public static synchronized Tracker analytics_getTracker(Context context, String analyticsTrackingId, boolean debugMode) {
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
		
		if(debugMode) {
			// When dry run is set, hits will not be dispatched, but will still be logged as
			// though they were dispatched.
			analytics.setDryRun(true);
			//Enable logging for GA.
			analytics.getLogger().setLogLevel(LogLevel.VERBOSE);
		}
			
		return analytics.newTracker(analyticsTrackingId);
	}
	
	/**
	 * Sends to Google Analytics an event.
	 * 
	 * @param tracker		The analytics tracker to use when sending the 
	 * 						event.
	 * @param eventCategory	Category of the event
	 * @param eventAction	Action of the event
	 * @param eventlabel	label of the event
	 */
	public static void analytics_sendEvent (Tracker tracker, String eventCategory, String eventAction, String eventlabel, Long eventValue) {
		
		EventBuilder eBuilder = new HitBuilders.EventBuilder();
		eBuilder.setCategory(eventCategory)
				.setAction(eventAction)
				.setLabel(eventlabel);
		
		if(eventValue!=null) {
			eBuilder.setValue(eventValue);			
		}
		
		tracker.send(eBuilder.build());
	}

	/**
	 * Sends to Google Analytics a screen view event.
	 * 
	 * @param tracker		The analytics tracker to use when sending the 
	 * 						event.
	 * @param screenName	The screen name.
	 */
	public static void analytics_sendScreenName (Tracker tracker, String screenName) {
		tracker.setScreenName(screenName);
		tracker.send(new HitBuilders.ScreenViewBuilder().build());
	}
	
	
	
	
	//--------------- SYSTEM ---------------------------------------------------------------------------
	
	/**
	 * Tells if an application is installed or not.
	 * 
	 * @param context		Your application context
	 * @param appPackage	The application package.	
	 * @return
	 */
	public static boolean system_isAppInstalled(Context context, String appPackage) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(appPackage, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        
        return app_installed ;
    }
	
	/**
	 * Gets the list of system applications.
	 * 
	 * @return	The list. If error the list will be empty.
	 */
	public static Set<String> system_getSystemApplicationList(Context context) {
		Set<String> systemApps = new HashSet<String>();
		
		
		try {
			final PackageManager pm = context.getPackageManager();
			final List<ApplicationInfo> installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);			
			for ( ApplicationInfo app : installedApps ) {
				if(system_isSystemPackage(app)){
					systemApps.add(app.packageName);
				}
			}		
			
		}catch(Exception e){
			if(LOG_ENABLE){
				Log.e(TAG, "Error getting system application list [" + e.getMessage() + "]", e);
			}
		}
		
		return systemApps;
	}
	
	/**
	 * Checks if an application is a system application.
	 * 
	 * @param appPackage
	 * @return
	 */
	public static boolean system_isSystemApplication(Context context, String appPackage) {
		boolean res = false;
		
		if(systemAppsList==null){
			systemAppsList = system_getSystemApplicationList(context);
		}		 
		for(String sysAppPackage:systemAppsList){
			if(appPackage.equals(sysAppPackage)){
				res = true;
				break;
			}
		}
		
		return res;
	}
	
	/**
	 * Return whether the given PackgeInfo represents a system package or not.
	 * User-installed packages should not be denoted as system packages.
	 * 
	 * @param appInfo
	 * @return
	 */
	public static boolean system_isSystemPackage(ApplicationInfo appInfo) {
	    return ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
	}
	
	
	/**
	 * This function allows to know if there is an application that responds to
	 * the specified action.
	 * 
	 * @param context
	 * @param action	Action that requires an application.
	 * @return
	 */
	public static boolean system_isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	
	//--------------- ADDMOB ---------------------------------------------------------------------------
	
	/**
	 * This disables correctly the AdMob ads.
	 * 
	 * @param activity
	 * @param adLayout	The AdView view.
	 */
	public static void adMob_hideAds(Activity activity, final AdView adLayout) {
	    activity.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	            adLayout.setEnabled(false);
	            adLayout.setVisibility(View.GONE);
	        }
	    });
	}
	
	/**
	 * This enables again the AdMob ads.
	 * 
	 * @param activity
	 * @param adLayout	The AdView view.
	 */
	public static void adMob_showAds(Activity activity, final AdView adLayout) {
	    activity.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	            adLayout.setEnabled(true);
	            adLayout.setVisibility(View.VISIBLE);

                AdRequest.Builder adBuilder = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

			    adLayout.loadAd(adBuilder.build());
	        }
	    });
	}
	
	/**
	 * This enables again the AdMob ads.
	 * 
	 * @param activity
	 * @param adLayout
	 * @param excludedDevices
	 */
	public static void adMob_showAds(Activity activity, final AdView adLayout, final List<String> excludedDevices) {
	    activity.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	            adLayout.setEnabled(true);
	            adLayout.setVisibility(View.VISIBLE);

                AdRequest.Builder adBuilder = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
			    
			    for(String dev:excludedDevices){
                    adBuilder.addTestDevice(dev);
			    }
			    
			    adLayout.loadAd(adBuilder.build());
	        }
	    });
	}
	
	//--------------- APPLICATIONS RELATED -------------------------------------------------------------
	
	/**
	 * gets the application icon.
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static Drawable appInfo_getIconFromPackageName(Context context, String packageName)
    {
        PackageManager pm = context.getPackageManager();       
        ApplicationInfo appInfo = null;
        try{
            appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        }catch (NameNotFoundException e){
            return null;
        }

        return appInfo.loadIcon(pm);
    }
	
	/**
	 * Gets the size of an APK.
	 * 
	 * @param installPath
	 * @return
	 */
	public static String appInfo_getSize(String installPath){
		File file = new File(installPath);
		 
		String unit = "Bytes";
		double sizeInUnit = 0d;
		 
		if (file.exists()) {
		  double size = (double) file.length();
		
		  if (size > 1024 * 1024 * 1024) { // Gigabyte
		    sizeInUnit = size / (1024 * 1024 * 1024);
		    unit = "GB";
		  } else if (size > 1024 * 1024) { // Megabyte
		    sizeInUnit = size / (1024 * 1024);
		    unit = "MB";
		  } else if (size > 1024) { // Kilobyte
		    sizeInUnit = size / 1024;
		    unit = "KB";
		  } else { // Byte
		    sizeInUnit = size;
		  }
		}
		 
		// only show two digits after the comma
		return new DecimalFormat("###.##").format(sizeInUnit) + " " + unit;
	}
	
	/**
	 * Creates a application informative dialog with options to
	 * uninstall/launch or cancel.
	 * 
	 * @param context
	 * @param appPackage
	 */
	public static void appInfo_getLaunchUninstallDialog(final Context context, String appPackage){
		
		try{
		
			final PackageManager packageManager = context.getPackageManager();
			final PackageInfo app = packageManager.getPackageInfo(appPackage, PackageManager.GET_META_DATA);
			
			
			AlertDialog dialog = new AlertDialog.Builder(context).create();
			
			dialog.setTitle(app.applicationInfo.loadLabel(packageManager));
			
			String description = null;
			if(app.applicationInfo.loadDescription(packageManager)!=null){
				description = app.applicationInfo.loadDescription(packageManager).toString();
			}			
			
			String msg = app.applicationInfo.loadLabel(packageManager) + "\n\n" +
			  "Version " + app.versionName + " (" + app.versionCode + ")" +
			   "\n" + (description!=null?(description+ "\n"):"") +
			   app.applicationInfo.sourceDir + "\n" + appInfo_getSize(app.applicationInfo.sourceDir);
			dialog.setMessage(msg);
			
			dialog.setCancelable(true);		
			dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			      dialog.dismiss();		      
			    }
			});					
			dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Uninstall",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			      Intent intent = new Intent(Intent.ACTION_DELETE);
			      intent.setData(Uri.parse("package:" + app.packageName));
			      context.startActivity(intent);		      
			    }
			});		
			dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Launch",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			      Intent i = packageManager.getLaunchIntentForPackage(app.packageName);
			      context.startActivity(i);		      		      
			    }
			});
			
			dialog.show();
		}catch(Exception e){
			if(LOG_ENABLE){
				Log.e(TAG, "Dialog could not be made for the specified application '" + appPackage + "'. [" + e.getMessage() + "].", e);
			}
		}
	}
	

    /**
     * Allows to install a new icon for the application.
     *
     * This method need two additional permissions in the application:
     *
     * <code>
     *  <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     * </code>
     *
     * @param context       The application context.
     * @param appMain       The application main class
     * @param appName       The application name
     * @param appIcon       The bitmap of the application icon. Can be null. If null, the
     *                      appIconResId must be provided.
     * @param appIconResId  Specify this only if no bitmap is set in the call to this method.
     */
    public static void application_shortcutAdd(Context context, Class appMain, String appName,
                                                  Bitmap appIcon, int appIconResId,
                                                  boolean removeCurrent) {

        // Intent launcher of the application
        Intent shortcutIntent = new Intent("android.intent.action.MAIN");
        shortcutIntent.addCategory("android.intent.category.LAUNCHER");
        shortcutIntent.setClass(context, appMain);
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Intent to add the new application icon.
        //
        // Decorate the shortcut
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        //This avoid the icon to be duplicated if created 
        //addIntent.putExtra("duplicate", false);
        
        if(appIcon!=null) {
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, appIcon);
        }else if(appIconResId!=0) {
            addIntent.putExtra(
                    Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    Intent.ShortcutIconResource.fromContext
                            (
                                    context.getApplicationContext(),
                                    appIconResId
                            )
            );
        }

        // Inform launcher to create shortcut
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.sendBroadcast(addIntent);
    }

    /**
     * Gets the application package name.
     * 
     * @param context
     * @return
     */
    public static String application_packageName(Context context) {
    	return context.getPackageName();    	
    }
    
    /**
     * Gets the application package info.
     * 
     * @param context
     * @return
     */
    public static PackageInfo application_packageInfo(Context context) {
    	PackageInfo pkgInfo = null;
    	
    	String pkgName = application_packageName(context);
    	PackageManager pkgManager = context.getPackageManager();
    	try {
			pkgInfo = pkgManager.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Package name not found! [" + e.getMessage() + "].");
		}
    	
    	return pkgInfo;
    }
    
    /**
     * Gets an application package info.
     * 
     * @param context
     * @param pkgName	The application package name to get the package from
     * @return
     */
    public static PackageInfo application_packageInfo(Context context, String pkgName) {
    	PackageInfo pkgInfo = null;
    	
    	PackageManager pkgManager = context.getPackageManager();
    	try {
			pkgInfo = pkgManager.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Package name not found! [" + e.getMessage() + "].");
		}
    	
    	return pkgInfo;
    }
    
    /**
     * Gets an application Name information.
     * 
     * @param context
     * @param pkgName
     * @return
     */
    public static String application_nameInfo(Context context, String pkgName) {
    	String appName = null;
    	    	
    	final PackageManager packageManager = context.getPackageManager();
    	PackageManager pkgManager = context.getPackageManager();
    	try {
    		PackageInfo pkgInfo = pkgManager.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
			appName = pkgInfo.applicationInfo.loadLabel(packageManager).toString();
			
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Package name not found! [" + e.getMessage() + "].");
		}
    	
    	return appName;
    }
    
    /**
     * Gets an application X509 certificates from application signatures.<br><br>
     * 
     * With each certificate it is also available:
     * 
     * <ul>
     * 		<li>Issuer</li>
     * 		<li>Subject</li>
     * 		<li>Serial Number</li>
     * 		<li>Signature SHA-1 hash</li>
     * 		<li>Signature MD5 hash</li>
     * </ul>
     * 
     * @param context
     * @param pkgName
     * @return
     */
    public static List<ApplicationCertificate> application_certificates(Context context, String pkgName) {
    	List<ApplicationCertificate> appCerts = new ArrayList<ApplicationCertificate>();
    	    	
    	PackageManager pkgManager = context.getPackageManager();
    	try {
    		PackageInfo pkgInfo = pkgManager.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
			
    		ApplicationCertificate appCert = null;
    		String signatureSHA1HASH = null;
    		String signatureMD5HASH = null;
    		final Signature[] arrSignatures = pkgInfo.signatures;
    	    for (final Signature sig : arrSignatures) {
    	    	signatureSHA1HASH = ToolBox.crypto_getHASH(sig.toString().getBytes(), ToolBox.HASH_TYPE.sha1);
    	    	signatureMD5HASH = ToolBox.crypto_getHASH(sig.toString().getBytes(), ToolBox.HASH_TYPE.md5);
    	    	
    	        /*
    	        * Get the X.509 certificate.
    	        */
    	        final byte[] rawCert = sig.toByteArray();
    	        InputStream certStream = new ByteArrayInputStream(rawCert);

    	        final CertificateFactory certFactory;
    	        final X509Certificate x509Cert;
    	        try {
    	        	//Get the X509 certificate.
    	            certFactory = CertificateFactory.getInstance("X509");
    	            x509Cert = (X509Certificate) certFactory.generateCertificate(certStream);
    	            
    	            //Create the application certificate information.
    	            appCert = new ApplicationCertificate(x509Cert.getSubjectDN().getName(), 
    	            						x509Cert.getIssuerDN().getName(), 
    	            						x509Cert.getSerialNumber().toString(), 
    	            						x509Cert,
    	            						signatureSHA1HASH, signatureMD5HASH);
    	            
    	            appCerts.add(appCert);    	            
    	        }
    	        catch (CertificateException e) {
    	            Log.e(TAG, "Error getting X509 certificate from signature stream [" + e.getMessage() + "].", e);
    	        }
    	    }
			
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Package name not found! [" + e.getMessage() + "].");
		}
    	
    	return appCerts;
    }
    
    /**
     * Application X509 certificate and certificate information.
     */
    public static class ApplicationCertificate {
    	
    	private String subject;
    	private String issuer;
    	private String serialNumber;
    	private String signatureSHA1Hash;
    	private String signatureMD5Hash;
    	private X509Certificate x509Certificate;
    	
    	protected ApplicationCertificate() {}
    	
    	public ApplicationCertificate(String subject, String issuer, String serialNumber, X509Certificate x509Certificate) {
    		this.subject = subject;
    		this.issuer = issuer;
    		this.serialNumber = serialNumber;
    		this.x509Certificate = x509Certificate;
    	}

    	public ApplicationCertificate(String subject, String issuer, String serialNumber, X509Certificate x509Certificate, String signatureSHA1Hash, String signatureMD5Hash) {
    		this.subject = subject;
    		this.issuer = issuer;
    		this.serialNumber = serialNumber;
    		this.x509Certificate = x509Certificate;
    		this.signatureSHA1Hash = signatureSHA1Hash;
    		this.signatureMD5Hash = signatureMD5Hash;
    	}
    	
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getIssuer() {
			return issuer;
		}
		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}

		public String getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public X509Certificate getX509Certificate() {
			return x509Certificate;
		}
		public void setX509Certificate(X509Certificate x509Certificate) {
			this.x509Certificate = x509Certificate;
		}

		public String getSignatureSHA1Hash() {
			return signatureSHA1Hash;
		}
		public void setSignatureSHA1Hash(String signatureSHA1Hash) {
			this.signatureSHA1Hash = signatureSHA1Hash;
		}

		public String getSignatureMD5Hash() {
			return signatureMD5Hash;
		}
		public void setSignatureMD5Hash(String signatureMD5Hash) {
			this.signatureMD5Hash = signatureMD5Hash;
		}		
		
    }
    
    
    /**
     * Deletes a application desktop shortcut icon.
     *
     * This method need two additional permissions in the application:
     *
     * <code>
     *  <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
     * </code>
     *
     * @param context   The application context.
     * @param appClass  Shortcut's  activity class.
     * @param appName   The shortcut's name
     */
    public static void application_shortcutRemove_method1(Context context, Class appClass, String appName) {
        Intent shortcutIntent = new Intent(context, appClass);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent delIntent = new Intent();
        delIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        delIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);

        // Inform launcher to remove shortcut
        delIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        context.sendBroadcast(delIntent);
    }

    /**
     * Deletes a application desktop shortcut icon.
     *
     * Note:
     *  Manual way.
     *
     *  This method need two additional permissions in the application:
     *
     * <code>
     *  <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
     * </code>
     *
     * @param context   The application context.
     * @param appClass  Shortcut's  activity class.
     */
    public static void application_shortcutRemove_method2(Context context, Class appClass, String appName) {
        Intent intent = new Intent();
        String oldShortcutUri = "#Intent;action=android.intent.action.MAIN;category=android.intent.category.LAUNCHER;launchFlags=0x10200000;package="+ appClass.getPackage().getName() + ";component=" + appClass.getPackage().getName() + "/." + appClass.getSimpleName() + ";end";
        try {
            Intent altShortcutIntent  = Intent.parseUri(oldShortcutUri,0);
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, altShortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } catch (URISyntaxException e) {}
        intent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        context.sendBroadcast(intent);
    }

    /**
     * Deletes all application data. After using this method application
     * is closed.
     * 
     * @param context
     * @return Returns TRUE if all data is successfully erased, otherwise FALSE.
     */
    @SuppressLint("NewApi")
	public static boolean application_deleteAllData(Context context) {
    	
    	if (device_hasAPILevel(ApiLevel.LEVEL_19)) {
    		//We better use the regular way.
    	    return ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE))
    	            .clearApplicationUserData(); // note: it has a return value!
    	} else {
    		try {
    	        //We achieve this by using a shell executing the package manager.
    	    	//This way we do not need any permission to clear all application data.
    			//See https://developer.android.com/studio/command-line/shell.html
    	        Runtime runtime = Runtime.getRuntime();            
    	        runtime.exec("pm clear " + application_packageName(context));
    	        return true;
    		} catch (Exception e) {
    	        if(LOG_ENABLE)
    	        	Log.e("DELETE_ALL_APP_DATA:ERROR:",e.getMessage(),e);
    	        return false;
    	    }
    	}
    }
    
    /**
     * Deletes all shared preferences for the specified application context.
     * 
     * @param context	The application context.
     * @param preferences	Optional. A list of shared preferences to be cleared.
     * @return TRUE when doing without any issue, otherwise FALSE.
     */
    public static boolean application_deleteAllPreferences(Context context, String[] preferences) {
    	try{
	    	//Delete the default application preferences
	    	PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();    	
	    	
	    	//Delete any other established preference
	    	SharedPreferences appPref = null;
	    	if(preferences!=null && preferences.length>0){
	    		for(String pref:preferences){
	    			if((appPref = context.getSharedPreferences(pref, Context.MODE_PRIVATE))!=null){
	    				appPref.edit().clear().apply();
	    				break;
	    			}else if((appPref = context.getSharedPreferences(pref, Context.MODE_WORLD_READABLE))!=null){
	    				appPref.edit().clear().apply();
	    				break;
	    			}else if((appPref = context.getSharedPreferences(pref, Context.MODE_WORLD_WRITEABLE))!=null){
	    				appPref.edit().clear().apply();
	    				break;
	    			}
	    		}
	    	}    	
	    	
	    	return true;
    	}catch(Exception e){
    		if(LOG_ENABLE)
	        	Log.e("DELETE_ALL_APP_DATA:ERROR:",e.getMessage(),e);
    		
    		return false;
    	}
    }
    
    /**
     * Allows to run code in the UI Thread.
     * 
     * @param activity	Optional.
     * @param runnable	The runnable to run.
     */
    public static void application_runOnUIThread(Activity activity, Runnable runnable) {
    	if(activity!=null) {
    		activity.runOnUiThread(runnable);    		
    	}else{
	    	new Handler(Looper.getMainLooper()).post(runnable);
    	}
    }
    
    /**
     * Disables the component status of an Activity. If the activity has the
     * android.intent.category.LAUNCHER intent, it will remove the launcher icon
     * in the applications menu.
     *
     * @param context   The application context
     * @param appClass  Class of the activity
     */
    public static void application_activityDisable (Context context, Class appClass) {
        application_activityStatusSwitch(context, appClass, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    /**
     * Enables the component status of an Activity. If the activity has the
     * android.intent.category.LAUNCHER intent, it will add the launcher icon
     * in the applications menu.
     *
     * @param context   The application context
     * @param appClass  Class of the activity
     */
    public static void application_activityEnable (Context context, Class appClass) {
        application_activityStatusSwitch(context, appClass, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    /*
     * Enables or disables an activity component status.
     *
     * @param context   The application context
     * @param appClass  Class of the activity
     * @param status    The desired status.
     */
    private static void application_activityStatusSwitch (Context context, Class appClass, int status) {
        ComponentName component = new ComponentName(appClass.getPackage().getName(), appClass.getName());

        if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED &&
                (context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                 context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)) {
            context.getPackageManager().setComponentEnabledSetting(
                    component,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);

        }else if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED &&
                (context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                 context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)) {
            context.getPackageManager().setComponentEnabledSetting(
                    component,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    /**
     * Enables the component status of an Activity Alias. If the activity alias points to an
     * Activity with android.intent.category.LAUNCHER intent, it will add the launcher icon
     * in the applications menu.
     *
     * @param context
     * @param appClass
     * @param appLaunchAlias
     */
    public static void application_activityAliasEnable (Context context, Class appClass, String appLaunchAlias) {
        application_activityAliasStatusSwitch(context, appClass, appLaunchAlias, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    /**
     * Disables the component status of an Activity Alias. If the activity alias points to an
     * Activity with android.intent.category.LAUNCHER intent, it will remove the launcher icon
     * in the applications menu.
     *
     * @param context           The application context
     * @param appClass          Class of the activity alias
     * @param appLaunchAlias    The android:name of the activity-alias entry in the manifest.
     */
    public static void application_activityAliasDisable (Context context, Class appClass, String appLaunchAlias) {
        application_activityAliasStatusSwitch(context, appClass, appLaunchAlias, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    /**
     * Switches the component status of an Activity Alias. Use this to enable or disable it. If the
     * activity alias points to an Activity with android.intent.category.LAUNCHER intent, it will
     * remove/add the launcher icon in the applications menu.
     *
     * @param context           The application context
     * @param appClass          Class of the activity alias
     * @param appLaunchAlias    The android:name of the activity-alias entry in the manifest.
     */
    public static void application_activityAliasSwitchStatus (Context context, Class appClass, String appLaunchAlias) {
        ComponentName component = new ComponentName(appClass.getPackage().getName(), appClass.getPackage().getName() + "." + appLaunchAlias);
        if(context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            application_activityAliasStatusSwitch(context, appClass, appLaunchAlias, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        }else if(context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
            application_activityAliasStatusSwitch(context, appClass, appLaunchAlias, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        }
    }

    /*
     * Enables or disables an activity alias component status.
     *
     * @param context           The application context
     * @param appClass          Class of the activity alias
     * @param appLaunchAlias    The android:name of the activity-alias entry in the manifest.
     * @param status            The desired status.
     */
    private static void application_activityAliasStatusSwitch (Context context, Class appClass,
                                                               String appLaunchAlias, int status) {
        ComponentName component = new ComponentName(appClass.getPackage().getName(), appClass.getPackage().getName() + "." + appLaunchAlias);

        if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED &&
                (context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)) {
            context.getPackageManager().setComponentEnabledSetting(
                    component,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);

        }else if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED &&
                (context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                context.getPackageManager().getComponentEnabledSetting(component)==PackageManager.COMPONENT_ENABLED_STATE_DEFAULT)){
            context.getPackageManager().setComponentEnabledSetting(
                    component,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

	/**
	 * This method reads the info form the App. Manifest file.
	 * 
	 * @return
	 */
	public static boolean application_isAppInDebugMode(Context context){
		boolean res=false;
		
		try{
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0);
			   
			int flags=info.applicationInfo.flags;
			if ((flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
			    // development mode
				res=true;
			} else {
			    // release mode
			}
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e("IS_DEBUG_MODE:ERROR",e.getMessage(),e);
		}
		
		return res;
	}
	
	/**
	  * This method reads the info form the App. Manifest file.
	  * 
	  * @return
	  */
	 public static String application_getVersion(Context context){
		String res=null;
		
		try{
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0);
			   
			res = info.versionName;
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e("GET_APP_VERSION:ERROR",e.getMessage(),e);
		}
		
		return res;
	 }
	 
	 /**
	  * This method reads the info form the App. Manifest file.
	  * 
	  * @return
	  */
	 public static int application_getVersionCode(Context context){
		int res=-1;
		
		try{
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0);
			   
			res = info.versionCode;
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e("GET_APP_VERSION:ERROR",e.getMessage(),e);
		}
		
		return res;
	 }
	 
	 public static boolean application_isCallable(Activity activity, Intent intent) {
	    List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	 }

	 
	 /**
	 * Returns a list with the current signatures SHA-1 hash 
	 * of the application.
	 * 
	 * @param context	Application context
	 * @param appPackageName	The package name of the application.
	 * @return	A list or null if no signatures are found or error.
	 */
	public static List<String> application_getSignatures (Context context, String appPackageName) {
		List<String> appSignatures = null;
		
		try {
			if(appPackageName!=null && appPackageName.length()>0) {
		        PackageInfo info = context.getPackageManager().getPackageInfo(
		        		appPackageName, PackageManager.GET_SIGNATURES);
		        appSignatures = new ArrayList<String>();
		        String signatureString = null;
		        for (Signature signature : info.signatures) {
		            MessageDigest md = MessageDigest.getInstance("SHA");
		            md.update(signature.toByteArray());
		            signatureString = android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT);
		            Log.d(ToolBox.TAG, "KeyHash (" + appPackageName + "): " + signatureString);
		            appSignatures.add(signatureString);
		        }
			}else{
				Log.d(ToolBox.TAG, "No package name to get signaturs from.");
			}
	    } catch (NameNotFoundException e) {
	    	Log.e(ToolBox.TAG, "Error getting application (" + appPackageName + ") signatures. Package not found [" + e.getMessage() + "].", e);
	    } catch (NoSuchAlgorithmException e) {
	    	Log.e(ToolBox.TAG, "Error getting application (" + appPackageName + ") signatures. Algorithm SHA not found [" + e.getMessage() + "].", e);
	    }
		
		return appSignatures;
	}
	
	
	/**
	 * Returns an application package by its UID value.<br><br>
	 * 
	 * @param context	
	 * @param uid	The application UID (<i>In a service, use {@link Binder#getCallingUid()}  
	 * 				inside the service Binder object to get the UID parameter. If used 
	 * 				outside of the binder object, it will always return the application 
	 * 				itself, not the calling application</i>).
	 * @return
	 */
	public static String application_getPackageByUID(Context context, int uid) {
		return context.getPackageManager().getNameForUid(uid);
	}
	
	
	/**
	 * Returns an application package by its PID value.<br><br>
	 * 
	 * @param context
	 * @param pid	The application PID (<i>In a service, use {@link Binder#getCallingPid()}
	 * 				inside the service Binder object to get the PID parameter. If used 
	 * 				outside of the binder object, it will always return the application 
	 * 				itself, not the calling application</i>).
	 * @return
	 */
	public static String application_getPackageByPID(Context context, int pid) {
		String processName = null;
		
        ActivityManager am = (ActivityManager) context.getSystemService( Context.ACTIVITY_SERVICE );
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo proc : processes) {
            if (proc.pid == pid) {
            	//processName will be the package name of the caller process :)
            	processName = proc.processName;
            	break;
            }
        }
        
        return processName;
	}
	
	/**
	 * Returns activity's calling application package name.
	 * 
	 * @param activity	The activity being called.
	 * @return
	 */
	public static String application_getCallingApplicationPackage(Activity activity) {
		return activity.getCallingActivity().getPackageName();
	}
	
	
	/**
	 * Checks the signature of the application identified by its UID. Returns TRUE if is 
	 * the expected one.
	 * 
	 * @param context	The application context.
	 * @param packageName	The application package to check.
	 * @param signatureType	See {@link ToolBox#HASH_TYPE}
	 * @param expectedSignature	The expected application signature.
	 * @return	Return TRUE if the application has the expected signature, otherwise FALSE.
	 * 
	 */
	public static boolean application_checkSignature(Context context, String packageName, HASH_TYPE signatureType, String expectedSignature)  {
		boolean res = false;
		
		if(packageName!=null) {
			//Gets the application certificates
			List<ApplicationCertificate> appCerts = ToolBox.application_certificates(context, packageName);
			
			String appCerHash = null;
			for(ApplicationCertificate c:appCerts){
				
				switch (signatureType) {
					case sha1:
						appCerHash = c.getSignatureSHA1Hash();
						break;
					case md5:
						appCerHash = c.getSignatureMD5Hash();
				}
				
				if(appCerHash.equals(expectedSignature)){
					res = true;
					break;
				}
			}
		}else{
			if(ToolBox.LOG_ENABLE)
				Log.e(TAG, "No package name!.");
		}
		
		return res;
	}

	//-------------------- RANDOM-------------------------------------------------------------------------
	
	/**
	 * Gets a Random SHA-1 hash.
	 * 
	 * @return
	 */
	public static String random_HASH() {
		return crypto_getHASH(random_UUID(UUID.randomUUID().toString().getBytes()).getBytes(),HASH_TYPE.sha1);
	}
	
	/**
	 * Gets a random UUID string.
	 * 
	 * @param data	An UUID bytes.
	 * @return
	 */
	public static String random_UUID(byte[] data) {
		if(data!=null)
			return UUID.nameUUIDFromBytes(data).toString();
		else
			return UUID.randomUUID().toString();
	}
	
	/**
	 * Gets a Random generator.
	 * 
	 * @return
	 */
	public static Random random_getRandom() {
		return new Random(new Date().getTime());
	}
	
	/**
	 * Gets a secure Random generator.
	 *  
	 * @return
	 */
	public static Random random_getSecureRandom() {
		return new SecureRandom();		
	}
	
    //-------------------- ACTIVITY-----------------------------------------------------------------------

    /**
     * This method changes the ActionBar Icon.
     *
     * Requires API level 14 or higher.
     *
     * @param context
     * @param activity  Should be a ActionBarActivity of support compatibility pack.
     * @param iconResourceId
     */
	 public static void activity_actionBar_changeIcon(Context context, ActionBarActivity activity, int iconResourceId){
        activity.getSupportActionBar().setIcon(iconResourceId);
	 }

    /**
     * Converts the ActionBar icon to Back icon mode. Must be used
     * in the onCreate method.
     *
     * Requires API level 14 or higher.
     *
     * @param context
     * @param activity  Should be a ActionBarActivity of support compatibility pack.
     * @param iconResourceId
     */
	 public static void activity_actionBar_enableBackButton(Context context, ActionBarActivity activity, int iconResourceId){
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	 }


	//-------------------- DIALOGS ------------------------------------------------------------------------
	 
	 /** When was pressed last time the back button. */
	 public static long mBackPressed = 0l;
	 
	 /**
     * Ask for double tap to back to exit.
     * (avoids accidental exits)
     */
	 
	 /**
	  * Ask for double tap to back to exit.
     *  (avoids accidental exits)
     * 
	  * @param context
	  * @param backPressTimeInterval
	  * @param message
	  * @param defaultToast	If set to TRUE uses the default system toast.
	  * @param type Toast type {@link TOAST_TYPE} if not using default Toast.
	  */
     public static void backPressedAction(Activity context, long backPressTimeInterval, String message, boolean defaultToast, TOAST_TYPE type) {
        if((mBackPressed + backPressTimeInterval) > System.currentTimeMillis()) {
        	mBackPressed = 0l;
        	context.finish();
        }else{
        	if(defaultToast) {
        		Toast.makeText(context.getBaseContext(), message, Toast.LENGTH_SHORT).show();
        	}else{
        		toast_createCustomToast(context, message, type, false);        		
        	}
        }
        mBackPressed = System.currentTimeMillis();
     }
     
     /** Custom Toast types */
     public static enum TOAST_TYPE {INFO, WARNING, ERROR, REMINDER};
     
     /**
      * Creates a custom Toast.
      * 
      * @param context	The context
      * @param message	The message.
      * @param type		The custom toast type {@link TOAST_TYPE}
      * @param centerOnScreen	Set to TRUE to center in the middle of 
      * 						the screen.
      */
     @SuppressLint("InflateParams")
	public static void toast_createCustomToast(Context context, String message, TOAST_TYPE type, boolean centerOnScreen) {
    	 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    	 View linearLayout = inflater.inflate(R.layout.toast_view, null);
    	 //View linearLayout = inflater.inflate(R.layout.toast_view, (ViewGroup) context.findViewById(R.id.toast_layout_root));
    	 
    	 //Customize view of the Toast
    	 ImageView imageZone = (ImageView) linearLayout.findViewById(R.id.toastImage);    	 
    	 int imgResourceId = R.drawable.info_tip;
    	 switch (type) {
			case INFO:
				imgResourceId = R.drawable.info_tip;
				break;
			case WARNING:
				imgResourceId = R.drawable.warning4_icon;
				break;
			case ERROR:
				imgResourceId = R.drawable.error2_icon;
				break;
			case REMINDER:
				imgResourceId = R.drawable.reminder2_icon;
				break;
			default:
				break;
    	 }
    	 imageZone.setImageResource(imgResourceId);
    	 
    	 TextView textZone = (TextView) linearLayout.findViewById(R.id.toastText);
    	 textZone.setText(message);
    	 
    	 //Create and show the toast.
    	 Toast toast = new Toast(context);
    	 if(centerOnScreen) {
    		 toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
    		//toast.setGravity(Gravity.BOTTOM, 0, 0);
    	 }
    	 toast.setDuration(Toast.LENGTH_SHORT);
    	 toast.setView(linearLayout);
    	 toast.show();
     }
     
	 /**
	  * Creates an exit popup dialog.
	  * 
	  * @param context
	  * @param title
	  * @param message
	  * @param yesLabel
	  * @param cancelLabel
	  * @param moveTaskToBack	Set to TRUE to instead closing the activity move to
	  * 						background.
	  */
	 public static void dialog_showExitConfirmationDialog(final Context context, 
			 									   int title, int message, 
			 									   int yesLabel, int cancelLabel,
			 									   final boolean moveTaskToBack){
		 new AlertDialog.Builder(context)
	        .setIcon(android.R.drawable.ic_menu_info_details)
	        .setTitle(context.getResources().getString(title))
	        .setMessage(context.getResources().getString(message))
	        .setPositiveButton(context.getResources().getString(yesLabel), new DialogInterface.OnClickListener()
	    {
	        
	        public void onClick(DialogInterface dialog, int which) {
	        	if(moveTaskToBack){
	        		((Activity)context).moveTaskToBack (true); 
	        	}else{
	        		((Activity)context).finish();
	        	}   
	        }

	    })
	    .setNegativeButton(context.getResources().getString(cancelLabel), null)
	    .show();
	 }
	 
	 /**
	  * Creates an alert with an OK button.
	  * 
	  * @param context
	  * @param msgResourceId
	  */
	 public static void dialog_showAlertOkDialog(Context context, int titleResourceId, int msgResourceId){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(context.getResources().getString(msgResourceId))
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			
			if(titleResourceId!=0){
				builder.setTitle(context.getResources().getString(titleResourceId));
			}
			
			AlertDialog alert = builder.create();
			alert.show();
	 } 
	 
	 /**
	  * Creates an alert with an OK button.
	  * 
	  * @param context
	  * @param message
	  */
	 public static void dialog_showAlertOkDialog(Context context, String tittle, String message){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			
			if(tittle!=null && tittle.length()>0){
				builder.setTitle(tittle);
			}
			
			AlertDialog alert = builder.create();
			alert.show();
	 }
	 
	 /**
	  * Creates and shows a custom Alert dialog that will execute
	  * the actions specified for positive, negative and 
	  * neutral buttons.
	  * 
	  * @param context
	  * @param title
	  * @param message
	  * @param positiveBtnActions	Can be null. When null button is not shown.
	  * @param negativeBtnActions	Can be null. When null button is not shown.
	  * @param neutralBtnActions	Can be null.
	  */
	 public static void dialog_showCustomActionsDialog(Context context, String title, String message, 
			 String positiveBtnText, final Runnable positiveBtnActions, 
			 String negativeBtnText, final Runnable negativeBtnActions, 
			 String neutralBtnText, final Runnable neutralBtnActions){
		
		dialog_showCustomActionsDialog(context, title, message, positiveBtnText, positiveBtnActions, negativeBtnText, negativeBtnActions, neutralBtnText, neutralBtnActions, false);
	 }
	 
	 /**
	  * Creates and shows a custom Alert dialog that will execute
	  * the actions specified for positive, negative and 
	  * neutral buttons.
	  * 
	  * @param context
	  * @param title
	  * @param message
	  * @param positiveBtnActions	Can be null. When null button is not shown.
	  * @param negativeBtnActions	Can be null. When null button is not shown.
	  * @param neutralBtnActions	Can be null.
	  * @param modal				If set to TRUE, dialog can not be cancelled until action is done.
	  */
	 public static void dialog_showCustomActionsDialog(Context context, String title, String message, 
			 String positiveBtnText, final Runnable positiveBtnActions, 
			 String negativeBtnText, final Runnable negativeBtnActions, 
			 String neutralBtnText, final Runnable neutralBtnActions, boolean modal){
		 
		AlertDialog dialog = new AlertDialog.Builder(context).create();
			
		dialog.setTitle(title);			
		dialog.setMessage(message);		
		dialog.setCancelable(!modal);
		
		//First Cancel button to be able to cancel.		
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeBtnText,
			new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
		    	if(negativeBtnActions!=null)
		    		negativeBtnActions.run();
		    	
		    	dialog.dismiss();
		    }
		});
		
		if(neutralBtnActions!=null){
			dialog.setButton(AlertDialog.BUTTON_NEUTRAL, neutralBtnText,
			new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			    	if(neutralBtnActions!=null){
			    		neutralBtnActions.run();
			    	}			    			      
			    }
			});
		}		
		
		if(positiveBtnActions!=null){
			dialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveBtnText,
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			    	positiveBtnActions.run();		      		      
			    }
			});
		}
		
		dialog.show();
	 }
	 
	 /**
	  * Creates and shows a custom Alert dialog, with the specified layout, 
	  * that will execute some actions if specified (for positive, negative and 
	  * neutral buttons).
	  * 
	  * @param context
	  * @param title					The title of the alert.
	  * @param layoutResourceId			The layout resource Id
	  * @param positiveBtnResourceId	The layout positive button resource id
	  * @param positiveBtnActions		If specified, action to launch when clicking in the positive button.
	  * @param negativeBtnResourceId	The layout negative button resource id
	  * @param negativeBtnActions		If specified, action to launch when clicking in the negative button.
	  * @param neutralBtnResourceId		The layout neutral button resource id
	  * @param neutralBtnActions		If specified, action to launch when clicking in the neutral button.
	  * @param modal					If set to TRUE, dialog can not be cancelled until action is done.
	  */
	 public static void dialog_showCustomLayoutActionsDialog(Context context, String title, int layoutResourceId, 
			 Integer positiveBtnResourceId, final Runnable positiveBtnActions, 
			 Integer negativeBtnResourceId, final Runnable negativeBtnActions, 
			 Integer neutralBtnResourceId, final Runnable neutralBtnActions, boolean modal){
		
		final AlertDialog dialog = new AlertDialog.Builder(context).create();		
		dialog.setTitle(title);	
		dialog.setCancelable(!modal);
		 
		//Create the Inflater service and load the layout
		//LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		LayoutInflater inflater = LayoutInflater.from(context);
		View dialogContent = inflater.inflate(layoutResourceId, null);
		if(positiveBtnResourceId!=null) {
			Button positiveButton = (Button) dialogContent.findViewById( positiveBtnResourceId );
			positiveButton.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					positiveBtnActions.run();
					dialog.dismiss();
				}
			});
		}
		if(negativeBtnResourceId!=null) {
			Button negativeButton = (Button) dialogContent.findViewById( negativeBtnResourceId );
			negativeButton.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					negativeBtnActions.run();
					dialog.dismiss();
				}
			});
		}
		if(neutralBtnResourceId!=null) {
			Button neutralButton = (Button) dialogContent.findViewById( neutralBtnResourceId );
			neutralButton.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					neutralBtnActions.run();
					dialog.dismiss();
				}
			});
		}
		
		dialog.setView(dialogContent);		
		
		dialog.show();
	 }
	 
	 /**
	  * Opens a url in an alert dialog.
	  * 
	  * @param context
	  * @param title
	  * @param url
	  */
	 public static void dialog_showUrlInDialog(Context context, String title, String url){
		 AlertDialog.Builder alert = new AlertDialog.Builder(context);
		 alert.setTitle(title);
		 WebView wv = new WebView(context);
		 wv.loadUrl(url);
		 wv.setHorizontalScrollBarEnabled(false);
		 alert.setView(wv);
		 alert.setNegativeButton("Close", null);
		 alert.show();
	 }
	 
	 /**
	  * "Coach mark" (help overlay image)
	  * 
	  * @param context
	  * @param coachMarkLayoutId	Is "Help overlay" layout id in UX talk :-) 
	  * 				[coach_mark.xml is your coach mark layout]
	  * @param coachMarkMasterViewId	is the id of the top most view in coach_mark.xml
	  */
	 public static void dialog_onCoachMark(Context context, int coachMarkLayoutId, int coachMarkMasterViewId, int bgColor){

	    final Dialog dialog = new Dialog(context);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(bgColor));
	    dialog.setContentView(coachMarkLayoutId);
	    dialog.setCanceledOnTouchOutside(true);
	    
	    //for dismissing anywhere you touch
	    View masterView = dialog.findViewById(coachMarkMasterViewId);
	    masterView.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View view) {
	            dialog.dismiss();
	        }
	    });
	    
	    dialog.show();
	}
	 
	 /**
	  * Shows a Toast alert.
	  * 
	  * @param context				The context.
	  * @param message				The message to show
	  * @param centerOnScreen		Set to TRUE to center on the screen.
	  * @param defaultToastStyle	Set to true to use the default system Toast style.
	  * @param customToastType		The custom toast type {@link TOAST_TYPE}
	  */
	 public static void dialog_showToastAlert(Context context, String message, 
			 boolean centerOnScreen, boolean defaultToastStyle, 
			 TOAST_TYPE customToastType) {
		 
		 if(defaultToastStyle) {
			 Toast msg = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			 if(centerOnScreen){
				 msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
			 }
			 msg.show();
		 }else{
			 toast_createCustomToast(context, message, customToastType, centerOnScreen);
		 }
	 }
	 
	 /**
	  * The GPS enable request code when using "showGpsDisabledAlert()"
	  * method. Use this to check the result in "onActivityResult()"
	  * of the activity.
	  */
	 public final static int ENABLE_GPS_REQUEST = 0x69;
	 
	 /**
	  * Shows an alert dialog asking to enable in system
	  * settings the GPS. Once selected an option, the result
	  * is returned to the activity, result can be get by using
	  * the request code {@link ToolBox#ENABLE_GPS_REQUEST} in your
	  * activity onActivityResult.
	  * 
	  * @param context	The activity that opens the dialog.
	  * @param message	Optional.
	  * @param okButtonText	Optional.
	  * @param cancelButtonText	Optional.
	  */
	 public static void dialog_showGPSDisabledAlert(final Activity context, String message, String okButtonText, String cancelButtonText) {		 
		 dialog_showGPSDisabledAlert(context, message, okButtonText, null, cancelButtonText, null);
	 }
	 
	 /**
	  * Shows an alert dialog asking to enable in system
	  * settings the GPS. Once selected an option, the result
	  * is returned to the activity, result can be get by using
	  * the request code {@link ToolBox#ENABLE_GPS_REQUEST} in your
	  * activity onActivityResult.
	  * 
	  * @param context	The activity that opens the dialog.
	  * @param message	Optional.
	  * @param okButtonText	Optional.
	  * @param okRunnable	Optional.
	  * @param cancelButtonText	Optional.
	  * @param cancelRunnable	Optional.
	  */
	 public static void dialog_showGPSDisabledAlert(final Activity context, String message, String okButtonText, final Runnable okRunnable, String cancelButtonText, final Runnable cancelRunnable) {		 
		 
		 if(message==null || message!=null && message.length()==0) {
			 message = "&The GPS is disabled. Application needs GPS location. ¿Do you want to enable it?";
		 }
		 if(okButtonText==null || okButtonText!=null && okButtonText.length()==0) {
			 okButtonText = "Activate GPS";
		 }
		 if(cancelButtonText==null || cancelButtonText!=null && cancelButtonText.length()==0) {
			 cancelButtonText = "Cancel";
		 }
		 
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 builder
                .setMessage(message)
                .setCancelable(false).setPositiveButton(okButtonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            	dialog.dismiss();
                            	showAndroidGpsOptions(context);
                            	if(okRunnable!=null)
                            		okRunnable.run();
                            }
                        });
        builder.setNegativeButton(cancelButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if(cancelRunnable!=null)
                        	cancelRunnable.run();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
	 }
		
	/*
	 * Opens system location setting. Once selected an action, the result
	 * is returned to the activity.
	 */
	 private static void showAndroidGpsOptions(final Activity context) {
		 Intent gpsOptionsIntent = new Intent(
	              android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	     context.startActivityForResult(gpsOptionsIntent, ENABLE_GPS_REQUEST);
	 }
	
	 /**
	  * Shows an alert dialog asking to enable in system
	  * settings the GPS.
	  * 
	  * @param context	The activity that opens the dialog.
	  * @param message	Optional.
	  * @param okButtonText	Optional.
	  * @param cancelButtonText	Optional.
	  */
	 public static void dialog_showGPSDisabledAlert(final Context context, String message, String okButtonText, String cancelButtonText) {		 
		 
		 if(message==null || message!=null && message.length()==0) {
			 message = "The GPS is disabled. Application needs GPS location. ¿Do you want to enable it?";
		 }
		 if(okButtonText==null || okButtonText!=null && okButtonText.length()==0) {
			 okButtonText = "Activate GPS";
		 }
		 if(cancelButtonText==null || cancelButtonText!=null && cancelButtonText.length()==0) {
			 cancelButtonText = "Cancel";
		 }
		 
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 builder
                .setMessage(message)
                .setCancelable(false).setPositiveButton(okButtonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            	dialog.dismiss();
                            	showAndroidGpsOptions(context);                            	
                            }
                        });
        builder.setNegativeButton(cancelButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
	 }
	 
	 public static void dialog_showGPSDisabledAlert(final Context context, String message, String okButtonText, final Runnable okRunnable, String cancelButtonText, final Runnable cancelRunnable) {		 
		 
		 if(message==null || message!=null && message.length()==0) {
			 message = "The GPS is disabled. Application needs GPS location. ¿Do you want to enable it?";
		 }
		 if(okButtonText==null || okButtonText!=null && okButtonText.length()==0) {
			 okButtonText = "Activate GPS";
		 }
		 if(cancelButtonText==null || cancelButtonText!=null && cancelButtonText.length()==0) {
			 cancelButtonText = "Cancel";
		 }
		 
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 builder
                .setMessage(message)
                .setCancelable(false).setPositiveButton(okButtonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            	dialog.dismiss();
                            	showAndroidGpsOptions(context);
                            	if(okRunnable!=null)
                            		okRunnable.run();
                            }
                        });
        builder.setNegativeButton(cancelButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if(cancelRunnable!=null)
                        	cancelRunnable.run();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
	 }
	 
	 /*
	 * Opens system location settings.
	 */	 
	 private static void showAndroidGpsOptions(final Context context) {
		 Intent gpsOptionsIntent = new Intent(
	              android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	     context.startActivity(gpsOptionsIntent);
	 }
	
	//-------------------- GRAPHICS ----------------------------------------------------------------------
	 
	 public static Bitmap graphics_addWaterMarkToImage(Bitmap src, String watermark, Point location, int size, boolean underline) {
		 Bitmap result = null;
		 
		 try{
		 	int w = src.getWidth();
		    int h = src.getHeight();
		    result = Bitmap.createBitmap(w, h, src.getConfig());
		 
		    Canvas canvas = new Canvas(result);
		    canvas.drawBitmap(src, 0, 0, null);
		 
		    Paint paint = new Paint();
		    paint.setColor(Color.RED);
		    //paint.setAlpha(alpha);
		    paint.setTextSize(size);
		    paint.setAntiAlias(true);
		    paint.setUnderlineText(underline);
		    canvas.drawText(watermark, location.x, location.y, paint);
		 }catch(Exception e){
			 result = null;
			 if(LOG_ENABLE) 
		    		Log.e(TAG, "ERROR: graphics_addWaterMarkToImage() [" + e.getMessage()+"]", e);
		 }
		 
		 return result;
	}
	
	 
	 /**
	 * Make a direct NIO FloatBuffer from an array of floats.
	 * 
	 * @param array The float array
	 * @return The newly created FloatBuffer
	 */
	 public static FloatBuffer makeFloatBuffer(float[] array) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(array.length*4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(array);
	    fb.position(0);
	    return fb;
	 }
	  
	 /**
	 * Make a direct NIO ByteBuffer from an array of floats.
	 * 
	 * @param array The byte array
	 * @return The newly created FloatBuffer
	 */
	 public static ByteBuffer makeByteBuffer(byte[] array) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(array.length);
	    bb.order(ByteOrder.nativeOrder());
	    bb.put(array);
	    bb.position(0);
	    return bb;
	 }
	  
	 /**
	  * Make a direct NIO ByteBuffer of the specified size.
	  * 
	  * @param size	The desired size
	  * @return
	  */
	 public static ByteBuffer makeByteBuffer(int size) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(size);
	    bb.position(0);
	    return bb;
	 }	 
	 
	 
	//-------------------- VIEWS -------------------------------------------------------------------------
	 
	 public static void view_showAboutDialog(Context context, int titleResourceId, int aboutLayout, int okButtonId){		
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(aboutLayout);
		dialog.setTitle(titleResourceId);
		
		Button dialogButton = (Button) dialog.findViewById(okButtonId);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();	
	}
	
	//STATUS BAR ------------------------------------------------------------------------------------------
	 
	 /**
	  * Closes the status bar.
	  *  
	  * @param context
	  */
	 public static void statusBarClose(Context context) {
		Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		context.sendBroadcast(it);
	 }
	 
	 
	//SYSTEM NOTIFICATIONS ---------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Creates a normal system notification.
	 *    
	 * @param context				Context.
	 * @param notSound				Enable or disable the sound
	 * @param notSoundRawId			Custom raw sound id. If enabled and not set 
	 * 								default notification sound will be used. Set to -1 to 
	 * 								default system notification.
	 * @param multipleNot			Setting to True allows showing multiple notifications.
	 * @param groupMultipleNotKey	If is set, multiple notifications can be gruopped by 
	 * 								this key.
	 * @param notAction				Action for this notification
	 * @param notTitle				Title
	 * @param notMessage			Message. Received message could have non-latin characters so 
	 * 								it should be always received URLEncoded.
	 * @param notClazz				Class to be executed
	 * @param extras				Extra information
	 * 
	 * @deprecated Use {@link ToolBox#notification_create} instead.
	 */
	@Deprecated	
    public static void notification_generate(Context context, 
    		boolean notSound, int notSoundRawId, 
    		boolean multipleNot, String groupMultipleNotKey, 
    		String notAction, 
    		String notTitle, String notMessage, 
    		Class<?> notClazz, Bundle extras,
    		boolean wakeUp) {
        
		notification_create(context, 
    			notSound, notSoundRawId, 
    			false, multipleNot, groupMultipleNotKey, 
    			notAction, notTitle, notMessage, 
    			null, null, 
    			null, notMessage, 
    			null, null, 
    			null, null, 
    			notClazz, extras, wakeUp, 
    			ToolBox.NOTIFICATION_PRIORITY.DEFAULT, 
    			ToolBox.NOTIFICATION_STYLE.EXPANDABLE_BIG_TEXT_STYLE, 
    			ToolBox.NOTIFICATION_LOCK_SCREEN_PRIVACY.PRIVATE, 
    			null, 
    			null, 
    			null, 
    			ToolBox.NOTIFICATION_PROGRESSBAR_STYLE.NONE, null, null,
    			null);
    	        
    }

    
    /**
     * In lock screen how notifications are show. For Android 5.0+ (API level 21+)<br><br><br>
     * 
     * The default level in Android is PRIVATE, behaves exactly as notifications 
     * have always done on Android: The notification's icon and tickerText 
     * (if available) are shown in all situations, but the contents are only 
     * available if the device is unlocked for the appropriate user.<br><br>
     *
     * A more permissive policy can be expressed by PUBLIC; such a notification
     * can be read even in an "insecure" context (that is, above a secure lockscreen).
     * To modify the public version of this notification—for example, to redact 
     * some portions—see {@link Builder#setPublicVersion(Notification)}.<br><br>
     *
     * Finally, a notification can be made SECRET, which will suppress its icon
     * and ticker until the user has bypassed the lockscreen.
     * 
     */
    public static enum NOTIFICATION_LOCK_SCREEN_PRIVACY {
    	PUBLIC(1), PRIVATE(0), SECRET(-1);

        private final int number;

        private NOTIFICATION_LOCK_SCREEN_PRIVACY(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }
    
    /**
     * The notification priority.<br><br>
     * 
     * <i><code>HIGH</code>,<code>MAX</code> priorities:<br><br>
     * The notification, with Android 5.0+ (API level 21+), if 
     * using <code>HIGH, MAX</code> priority (with vibration or ringtone), 
     * appears in a small floating window (also called a heads-up 
     * notification) when the device is active (that is, the device 
     * is unlocked and its screen is on). These notifications appear 
     * similar to the compact form of your notification, except that 
     * the heads-up notification also shows action buttons. Users can 
     * act on, or dismiss, a heads-up notification without leaving 
     * the current app</i>.
     * 
     */
    public static enum NOTIFICATION_PRIORITY {
    	DEFAULT(0), MIN(-2), LOW(-1), HIGH(1), MAX(2);

        private final int number;

        private NOTIFICATION_PRIORITY(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }
    
    /**
     * Notification style types.<br><br>
     * 
     * Expandable ones are only supported in Android 4.1+<br><br>
     * 
     * See <a href="http://developer.android.com/reference/android/app/Notification.BigTextStyle.html">EXPANDABLE_BIG_TEXT_STYLE</a><br>
     * See <a href="http://developer.android.com/reference/android/app/Notification.BigPictureStyle.html">EXPANDABLE_BIG_PICTURE_STYLE</a><br>
     * See <a href="http://developer.android.com/reference/android/app/Notification.InboxStyle.html">EXPANDABLE_INBOX_STYLE</a><br><br>
     * 
     * For CUSTOM_STYLE, a RemoteViews object with a custom layout is required. See 
     * <a href="http://developer.android.com/guide/topics/ui/notifiers/notifications.html#CustomNotification">CustomNotification</a>.
     */
    public static enum NOTIFICATION_STYLE {NORMAL_STYLE, EXPANDABLE_BIG_TEXT_STYLE, EXPANDABLE_BIG_PICTURE_STYLE, EXPANDABLE_INBOX_STYLE, CUSTOM_STYLE};
  
    /**
     * A notification can have a progress bar, useful when notification has a
     * process that is being done in background.
     * 
     */
    public static enum NOTIFICATION_PROGRESSBAR_STYLE {NONE, DETERMINATE, INDETERMINATE};
    
    /**
     * Creates and generates a new notification.<br><br>
     * 
     * The notification has a FLAg in the extras bundle (NOTIFICATION_FLAG) that is set to 1.
     * This class can be used afterwards to know within an app if the app was opened from
     * a notification checking this value.<br><br>
     * 
     * See:<br><br>
     * 							
     * 	Notification:			http://developer.android.com/design/patterns/notifications.html<br>
     * 							http://developer.android.com/guide/topics/ui/notifiers/notifications.html<br> 									
     * 							Previous to Android 5.0 https://stuff.mit.edu/afs/sipb/project/android/docs/guide/topics/ui/notifiers/notifications.html	
     * 	Notification.Builder:	http://developer.android.com/reference/android/app/Notification.Builder.html<br>
     * 	PendingIntent:			http://developer.android.com/reference/android/app/PendingIntent.html<br>
     * 	Big View Styles: 		http://developer.android.com/training/notify-user/expanded.html<br>	
     * 							http://developer.android.com/reference/android/app/Notification.BigTextStyle.html<br>
     * 							http://developer.android.com/reference/android/app/Notification.InboxStyle.html<br>
     * 							http://developer.android.com/reference/android/support/v4/app/NotificationCompat.InboxStyle.html<br>
     *  Iconografy				http://developer.android.com/design/style/iconography.html#notification
     * 	
     * @param context							The context of the notification.
     * @param notSound							Set to TRUE to enable sound in the notification.
     * @param notSoundRawId						Optional. Set one to use this sound instead the default one.
     * @param forceSound						If enabled, sound is enabled avoiding any user setting. 
     * @param multipleNot						Setting to True allows showing multiple notifications.
	 * @param groupMultipleNotKey				If is set, multiple notifications can be grouped by this key.
	 * @param notAction							Action for this notification
     * @param notTitle							The title of the notification.
     * @param notMessage						The message of the notification.
     * @param notTicker							Optional. Text that appears for only a few seconds when notification 
     * 											raises. (text which is sent to accessibility services). 
     * @param notContentInfo					Optional. A small piece of additional information pertaining 
     * 											to this notification. The platform template will draw this on 
     * 											the last line of the notification, at the far right (to the 
     * 											right of a smallIcon if it has been placed there).
     * @param bigContentTitle					Optional. Android 4.1+. Overrides ContentTitle in the big form 
     * 											of the template
     * @param bigContentText					Optional. Android 4.1+. Overrides ContentMessage in the big form 
     * 											of the template
     * @param bigContentSummary					Optional. Android 4.1+. Adds a line at the bottom of the notification.
     * @param bigContentImage					Optional. In BigPicture Expandable notification type. Android 4.1+.
     * 											It is the image to show. Can be a drawable resourceId, an assets 
     * 											resource file name or an URL to an image.
     * @param bigStyleInboxContent				Optional. In InboxStyle Expandable notification type. It is the
     * 											content of the notification for the expandable.
     * @param bigStyleInboxSeparator			Optional. In InboxStyle Expandable notification type. It is the 
     * 											separator of each line in the received message (notMessage)
     * @param notBackgroundColor				Optional. Since Android 5.0+ notification icons must follow a design guidelines 
     * 											to be showed correctly and allows to set the background color for 
     * 											the icon. The specified color must be in <b>hexadecimal<b>, for 
     * 											example "#ff6600".
     * @param notClazz							Class to be executed
	 * @param extras							Extra information to attach to the notification to be available
	 * 											in the application or the destination intent class (notClazz). 
     * @param wakeUp							Set to TRUE to wake-up the device when notification is received.
     * @param notPriority						Optional. Select the desired notification priority. 
     * 											See {@link NOTIFICATION_PRIORITY}
     * @param notStyle							Select the notification style. See {@link NOTIFICATION_STYLE}
     * @param notVisibility						Optional. The Default is PRIVATE. How and when the SystemUI reveals 
     * 											the notification's presence and contents in untrusted situations 
     * 											(namely, on the secure lockscreen). See {@link NOTIFICATION_LOCK_SCREEN_PRIVACY}
     * @param largeIconResource					Optional. Set one to use a larger icon for the notification. Can be a 
     * 											drawable resourceId, an assets/raw resource file name or an URL to an image.
     * @param contentView						Optional. Avoid setting a background Drawable on your RemoteViews 
     * 											object, because your text color may become unreadable. 
     * 											See {@link RemoteViews} and How-To at 
     * 											<a href="http://developer.android.com/guide/topics/ui/notifiers/notifications.html#CustomNotification">Information</a>
     * @param notifyID							Optional. If set, this Id will be used instead a generated one.
     * 											This is useful to reuse the notification in order to update the intent
     * 											instead of generate a new one.
     * @param progressBarStyle					Only for Android 4.0+ (API Level 14+). See {@link NOTIFICATION_PROGRESSBAR_STYLE}
     * @param progressBarRunnable				If parameter <code>progressBarStyle</code> is other than <code>NONE</code>,
     * 											this parameter is a {@link NotificationProgressBarRunnable} type. use it to do 
     * 											some taks while notification progress bar is shown. 
     * @param progressBarFinishText  			If parameter <code>progressBarStyle</code> is other than <code>NONE</code>, when
     * 											tasks is finished, parameter <code>progressBarRunnable</code>, this text is shown
     * 											in the notification.
     * @param actions							Optional. For Android 4.1+ (API Level 16+). If set, such actions will be presented 
     * 											in the notification. <b>Remember</b> that to close the notification after action is
     * 											clicked, you should use the "notifyID" parameter and set it as an extra of your
     * 											action to be able to close the notification from your application.
     */
    public static void notification_create(Context context,
    		boolean notSound, Integer notSoundRawId, boolean forceSound,
    		boolean multipleNot, String groupMultipleNotKey,
    		String notAction,
    		String notTitle, String notMessage,
    		String notTicker, String notContentInfo,
    		String bigContentTitle, String bigContentText, 
    		String bigContentSummary,
    		String bigContentImage, 
    		String bigStyleInboxContent, String bigStyleInboxSeparator,
    		String notBackgroundColor,
    		Class<?> notClazz, Bundle extras,
    		boolean wakeUp,
    		NOTIFICATION_PRIORITY notPriority,
    		NOTIFICATION_STYLE notStyle,
    		NOTIFICATION_LOCK_SCREEN_PRIVACY notVisibility,    		
    		String largeIconResource,
    		RemoteViews contentView,    		
    		Integer notifyID,
    		NOTIFICATION_PROGRESSBAR_STYLE progressBarStyle,
    		NotificationProgressBarRunnable progressBarRunnable,
    		String progressBarFinishText,
    		List<Action> actions
    		) {
    	
    	try{
    		
    		//1.- Prepare the intent that is launched by the notification
    		//
    		//This is the intent that runs in case the user clicks on the notification
    		Intent notificationIntent = new Intent(context, notClazz);    		
	        notificationIntent.setAction(notClazz.getName()+"."+notAction);
	        if(extras==null){
	        	extras = new Bundle();	        	
	        }
	        //Add a flag that can be used to know if the app is opened from a notification :)
	        extras.putInt(NOTIFICATION_FLAG, 1);
	        //We save in the intent all the info received.
	        notificationIntent.putExtras(extras);
	       	 
	        if(actions!=null && actions.size()>0) {
	        	for(Action action:actions){
	        		Set<String> aExtrasKeys = action.getExtras().keySet();
	        		Object value;
	        		for(String key:aExtrasKeys) {
	        			value = action.getExtras().get(key);
	        			//TODO Improve this.
	        			if(value instanceof String){
	        				if(!extras.containsKey(key))
	        					extras.putString(key, (String)value);
	        			}else if(value instanceof Integer){
	        				if(!extras.containsKey(key))
	        					extras.putInt(key, (Integer)value);
	        			}else if(value instanceof Boolean){
	        				if(!extras.containsKey(key))
	        					extras.putBoolean(key, (Boolean)value);
	        			}else if(value instanceof Character){
	        				if(!extras.containsKey(key))
	        					extras.putChar(key, (Character)value);
	        			}else if(value instanceof CharSequence){
	        				if(!extras.containsKey(key))
	        					extras.putCharSequence(key, (CharSequence)value);
	        			}else if(value instanceof Double){
	        				if(!extras.containsKey(key))
	        					extras.putDouble(key, (Double)value);
	        			}else if(value instanceof Float){
	        				if(!extras.containsKey(key))
	        					extras.putFloat(key, (Float)value);
	        			}else if(value instanceof Long){
	        				if(!extras.containsKey(key))
	        					extras.putLong(key, (Long)value);
	        			}else if(value instanceof Serializable){
	        				if(!extras.containsKey(key))
	        					extras.putSerializable(key, (Serializable)value);
	        			/*}else if(value instanceof Size){
	        				if(!extras.containsKey(key))
	        					extras.putSize(key, (Size)value);*/
	        			}else if(value instanceof Parcelable){
	        				if(!extras.containsKey(key))
	        					extras.putParcelable(key, (Parcelable)value);
	        			}else if(value instanceof Byte){
	        				if(!extras.containsKey(key))
	        					extras.putByte(key, (Byte)value);
	        			}else if(value instanceof String[]){
	        				if(!extras.containsKey(key))
	        					extras.putStringArray(key, (String[])value);
	        			}else if(value instanceof byte[]){
	        				if(!extras.containsKey(key))
	        					extras.putByteArray(key, (byte[])value);
	        			}
	        		}					
				}
	        	notificationIntent.putExtras(extras);
	        }
	        
	        //Set intent so it does not start a new activity
	        //
	        //Notes:
	        //	- The flag FLAG_ACTIVITY_SINGLE_TOP makes that only one instance of the activity exists 
	        //	  (each time the activity is summoned, instead onCreate(), a call to  onNewIntent() is made.
	        //  - If we use FLAG_ACTIVITY_CLEAR_TOP, it will make that the last "snapshot"/TOP of the activity
	        //	  be called. We do not want this because the HOME button will call this "snapshot". 
	        //	  To avoid this behavior we use FLAG_ACTIVITY_BROUGHT_TO_FRONT that simply takes to 
	        //	  foreground the activity.
	        //
	        //See http://developer.android.com/reference/android/content/Intent.html	        
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 
	        							| Intent.FLAG_ACTIVITY_SINGLE_TOP 
	        							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        
		    // This ensures that the back button follows the recommended
		    // convention for the back key.
		    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);		      
		    // Adds the back stack for the Intent (but not the Intent itself)
		    stackBuilder.addParentStack(notClazz);		      
		    // Adds the Intent that starts the Activity to the top of the stack
		    stackBuilder.addNextIntent(notificationIntent);
	        		    
	        int REQUEST_UNIQUE_ID = 0;
	        if(notifyID!=null) {
	        	//User specified a custom notification id so we use it
	        	REQUEST_UNIQUE_ID = notifyID.intValue();
	        }else{
	        	if(multipleNot){
	        		//user wants multiple notifications in the system tray.
	        		if(groupMultipleNotKey!=null && groupMultipleNotKey.length()>0){
	        			//We set specified custom collapse_key. This means that all 
	        			//notifications will be collapsed in one, the most recent one.
			       		REQUEST_UNIQUE_ID = groupMultipleNotKey.hashCode();
			       	}else{
			       		//We generate a new ID so this notification with no collapse key
			       		//will produce a new notification in the system try.
			       		if(random==null){
		        			random = new Random();
		        		}
		        		REQUEST_UNIQUE_ID = random.nextInt();
			       	}
	        	}else{
	        		//We use default, 0, value for all multiple notifications.	        		
	        	}
	        }
	        
	        PendingIntent intent = PendingIntent.getActivity(context, REQUEST_UNIQUE_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	        
	        
	        //2.- Prepare the notification
	        //
    		//Get the application icon.

	        /** 
	         * Since Android 5.0+ notification icons must follow a design
	         * guidelines to be showed correctly.
	         * 
	         * See: http://developer.android.com/design/style/iconography.html#notification 
	         **/
	    	int iconResId = notification_getApplicationIcon(context);
	    	long when = System.currentTimeMillis();
	        
			// Create the notification
			NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context);
			notifyBuilder.setWhen(when);
			notifyBuilder.setSmallIcon(iconResId);
			if(notBackgroundColor!=null && notBackgroundColor.length()>0 && notBackgroundColor.startsWith("#"))
				notifyBuilder.setColor(Color.parseColor(notBackgroundColor));
			notifyBuilder.setAutoCancel(true); //Make this notification automatically dismissed when the user touches it.
			if(notTicker!=null) {
				notifyBuilder.setTicker(notTicker); //Text that appears for only a few seconds when notification raises.
			}
			if(notContentInfo!=null){
				notifyBuilder.setContentInfo(notContentInfo); //Text (auxiliar) that appears to the right.
			}			
			if(largeIconResource!=null) {
				Bitmap bIconLarge = media_getBitmap(context, largeIconResource);
				if(bIconLarge!=null) {
					notifyBuilder.setLargeIcon(bIconLarge);
					
					//In this case, in Android 5.0+, the application icon is 
					//show in the bottom right of the large icon and should be
					//one flat notification icon following:
					//http://developer.android.com/design/style/iconography.html#notification
					
				}
			}			
			//Lock Screen Notifications, Android 5.0+ (API level 21+)
	    	if(notVisibility!=null) {
	    		notifyBuilder.setVisibility(notVisibility.getNumber());
	    	}
	    	//Priority of the notification
			if(notPriority!=null) {
				notifyBuilder.setPriority(notPriority.getNumber());
			}else{
				notifyBuilder.setPriority(NOTIFICATION_PRIORITY.DEFAULT.getNumber());
			}
			//Set the specified actions to the notification.
			if(actions!=null && actions.size()>0){
				for(Action action:actions){
					notifyBuilder.addAction(action);
				}
			}
			
			//Set the notification pending intent.
			notifyBuilder.setContentIntent(intent);
			
			//Set the notification sound
			notifyBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
			if(notSound){
	        	if(notSoundRawId!=null && notSoundRawId.intValue()>0){
	        		try {
	        			//We try to use the specified notification sound
	        			notifyBuilder.setSound(Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + notSoundRawId.intValue()));
	        		}catch(Exception e){
	        			if(LOG_ENABLE){
	        				Log.w(TAG, "Custom sound " + notSoundRawId.intValue() + "could not be found. Using default.");
	        			}
	        		}
	        	}
	        }
			
			//Conform the Notification style
			//			
			String bContentTitle = notTitle;
			String bContentSummary = notMessage;
			if(bigContentTitle!=null){
				bContentTitle = bigContentTitle; 
			}
			if(bigContentSummary!=null){
				bContentSummary = bigContentSummary; 
			}
			notifyBuilder.setContentTitle(notTitle);
    	    notifyBuilder.setContentText(Html.fromHtml((notMessage!=null?notMessage:"")));
			
			//Custom notification layout
	    	//
	    	//The height available for a custom notification layout depends on the 
	    	//notification view:
	    	// - Normal view layouts are limited to 64 dp.
	    	// - Expanded view layouts are limited to 256 dp.
			if(notStyle==NOTIFICATION_STYLE.CUSTOM_STYLE) {
				if(contentView!=null) {				
					notifyBuilder.setContent(contentView);					
				}
			}else if(notStyle==NOTIFICATION_STYLE.EXPANDABLE_BIG_PICTURE_STYLE) {
				Bitmap bContentImage = media_getBitmap(context, bigContentImage);
				
				notifyBuilder.setStyle(
						new NotificationCompat.BigPictureStyle()
							.setBigContentTitle(bContentTitle)
							.setSummaryText(bContentSummary)
							.bigPicture((bContentImage!=null?bContentImage:null)) // Add the big picture to the style.
				);
				
			}else if(notStyle==NOTIFICATION_STYLE.EXPANDABLE_BIG_TEXT_STYLE) {
				String bsContentText = "";
				String nShortMessage = (notMessage!=null?notMessage:"");
				if(bigContentText!=null && bigContentText.length()>0){
					bsContentText = bigContentText;
				}else{
					bsContentText = notMessage;
					/*if(notMessage!=null && notMessage.length()>12) {
						nShortMessage = notMessage.substring(0, 12) + "...";
					}*/
				}
				
				notifyBuilder.setStyle(
						new NotificationCompat.BigTextStyle()						
							.setBigContentTitle(bContentTitle)
							.setSummaryText(bContentSummary)
							.bigText(Html.fromHtml(bsContentText))
						);
				
				notifyBuilder.setContentText(Html.fromHtml(nShortMessage));
				
			}else if(notStyle==NOTIFICATION_STYLE.EXPANDABLE_INBOX_STYLE){
				//Prepare the style
				NotificationCompat.InboxStyle iStyle = new NotificationCompat.InboxStyle();					
				iStyle.setBigContentTitle(bContentTitle);
				iStyle.setSummaryText(bContentSummary);
				
				//Now we try to get lines for the style. Each line should be separated 
				//in the message string by some separation character.
				int nLines = 1;
				if(bigStyleInboxSeparator!=null && bigStyleInboxContent!=null && 
						bigStyleInboxContent.length()>0) {
					//Prepare all lines from the received message.
					String[] lines = bigStyleInboxContent.split(bigStyleInboxSeparator);
					nLines = lines.length;
					for(String line:lines) {
						iStyle.addLine(Html.fromHtml(line));
					}
				}else{
					//No separator character found so we show all the message in one
					//single line.
					iStyle.addLine(notMessage);
				}
				
				notifyBuilder.setStyle(iStyle);
				//To show the number of lines
				notifyBuilder.setNumber(nLines);				
				
			}else if(notStyle==NOTIFICATION_STYLE.NORMAL_STYLE){
				//Some other initializations
			}
			
			
			//This makes the device to wake-up is is idle with the screen off.
	        if(wakeUp){
	        	powersaving_wakeUp(context);
	        }
	        
	        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	        
	        //We check if the sound is disabled to enable just for a moment
	        AudioManager amanager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	        int previousAudioMode = amanager.getRingerMode();
	        if(forceSound) {
		        if(notSound && previousAudioMode!=AudioManager.RINGER_MODE_NORMAL){	        	
		        	amanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		        }
	        }
	        
	        if(progressBarStyle!=null && 
	        		progressBarRunnable!=null && 
	        		(progressBarRunnable instanceof NotificationProgressBarRunnable)) {
	        	
	        	switch (progressBarStyle) {
					case DETERMINATE:
						progressBarRunnable.initialize(notificationManager, notifyBuilder, 
			        			REQUEST_UNIQUE_ID, false, progressBarFinishText);						
						progressBarRunnable.run();
						break;
					case INDETERMINATE:
						progressBarRunnable.initialize(notificationManager, notifyBuilder, 
			        			REQUEST_UNIQUE_ID, true, progressBarFinishText);
						progressBarRunnable.run();
						break;
					case NONE:
						//Show the notification to the user.
				        //(Sets an ID for the notification, so it can be updated)
				        notificationManager.notify(REQUEST_UNIQUE_ID, notifyBuilder.build());
				}
	        }else{
	        	//Show the notification to the user.
		        //(Sets an ID for the notification, so it can be updated)
		        notificationManager.notify(REQUEST_UNIQUE_ID, notifyBuilder.build());
	        }
	        
	        //We restore the sound setting
	        if(forceSound) {
		        if(previousAudioMode!=AudioManager.RINGER_MODE_NORMAL){
		        	//We wait a little so sound is played
		        	try{
			        	Thread.sleep(3000);
			        }catch(Exception e){}		        
		        }
		        amanager.setRingerMode(previousAudioMode);
	        }
			
	        Log.d(TAG, "Android Notification created.");
	        
    	}catch(Exception e) {
    		if(LOG_ENABLE)
				Log.e(TAG, "The notification could not be created (" +e.getMessage() + ")", e);
    	}
    }
    
    /**
     * Creates and generates a new notification.<br><br>
     * 
     * The notification has a FLAg in the extras bundle (NOTIFICATION_FLAG) that is set to 1.
     * This class can be used afterwards to know within an app if the app was opened from
     * a notification checking this value.<br><br>
     * 
     * See:<br><br>
     * 							
     * 	Notification:			http://developer.android.com/design/patterns/notifications.html<br>
     * 							http://developer.android.com/guide/topics/ui/notifiers/notifications.html<br> 									
     * 							Previous to Android 5.0 https://stuff.mit.edu/afs/sipb/project/android/docs/guide/topics/ui/notifiers/notifications.html	
     * 	Notification.Builder:	http://developer.android.com/reference/android/app/Notification.Builder.html<br>
     * 	PendingIntent:			http://developer.android.com/reference/android/app/PendingIntent.html<br>
     * 	Big View Styles: 		http://developer.android.com/training/notify-user/expanded.html<br>	
     * 							http://developer.android.com/reference/android/app/Notification.BigTextStyle.html<br>
     * 							http://developer.android.com/reference/android/app/Notification.InboxStyle.html<br>
     * 							http://developer.android.com/reference/android/support/v4/app/NotificationCompat.InboxStyle.html<br>
     *  Iconografy				http://developer.android.com/design/style/iconography.html#notification
     * 	
     * @param context							The context of the notification.
     * @param notSound							Set to TRUE to enable sound in the notification.
     * @param notSoundRawId						Optional. Set one to use this sound instead the default one.
     * @param forceSound						If enabled, sound is enabled avoiding any user setting. 
     * @param multipleNot						Setting to True allows showing multiple notifications.
	 * @param groupMultipleNotKey				If is set, multiple notifications can be grouped by this key.
	 * @param notAction							Action for this notification
     * @param notTitle							The title of the notification.
     * @param notMessage						The message of the notification.
     * @param notTicker							Optional. Text that appears for only a few seconds when notification 
     * 											raises. (text which is sent to accessibility services). 
     * @param notContentInfo					Optional. A small piece of additional information pertaining 
     * 											to this notification. The platform template will draw this on 
     * 											the last line of the notification, at the far right (to the 
     * 											right of a smallIcon if it has been placed there).
     * @param bigContentTitle					Optional. Android 4.1+. Overrides ContentTitle in the big form 
     * 											of the template
     * @param bigContentText					Optional. Android 4.1+. Overrides ContentMessage in the big form 
     * 											of the template
     * @param bigContentSummary					Optional. Android 4.1+. Adds a line at the bottom of the notification.
     * @param bigContentImage					Optional. In BigPicture Expandable notification type. Android 4.1+.
     * 											It is the image to show. Can be a drawable resourceId, an assets 
     * 											resource file name or an URL to an image.
     * @param bigStyleInboxContent				Optional. In InboxStyle Expandable notification type. It is the
     * 											content of the notification for the expandable.
     * @param bigStyleInboxSeparator			Optional. In InboxStyle Expandable notification type. It is the 
     * 											separator of each line in the received message (notMessage)
     * @param notClazz							Class to be executed
	 * @param extras							Extra information to attach to the notification to be available
	 * 											in the application or the destination intent class (notClazz). 
     * @param wakeUp							Set to TRUE to wake-up the device when notification is received.
     * @param notPriority						Optional. Select the desired notification priority. 
     * 											See {@link NOTIFICATION_PRIORITY}
     * @param notStyle							Select the notification style. See {@link NOTIFICATION_STYLE}
     * @param notVisibility						Optional. The Default is PRIVATE. How and when the SystemUI reveals 
     * 											the notification's presence and contents in untrusted situations 
     * 											(namely, on the secure lockscreen). See {@link NOTIFICATION_LOCK_SCREEN_PRIVACY}
     * @param largeIconResource					Optional. Set one to use a larger icon for the notification. Can be a 
     * 											drawable resourceId, an assets/raw resource file name or an URL to an image.
     * @param contentView						Optional. Avoid setting a background Drawable on your RemoteViews 
     * 											object, because your text color may become unreadable. 
     * 											See {@link RemoteViews} and How-To at 
     * 											<a href="http://developer.android.com/guide/topics/ui/notifiers/notifications.html#CustomNotification">Information</a>
     * @param notifyID							Optional. If set, this Id will be used instead a generated one.
     * 											This is useful to reuse the notification in order to update the intent
     * 											instead of generate a new one.
     * @param progressBarStyle					Only for Android 4.0+ (API Level 14+). See {@link NOTIFICATION_PROGRESSBAR_STYLE}
     * @param progressBarRunnable				If parameter <code>progressBarStyle</code> is other than <code>NONE</code>,
     * 											this parameter is a {@link NotificationProgressBarRunnable} type. use it to do 
     * 											some taks while notification progress bar is shown. 
     * @param progressBarFinishText  			If parameter <code>progressBarStyle</code> is other than <code>NONE</code>, when
     * 											tasks is finished, parameter <code>progressBarRunnable</code>, this text is shown
     * 											in the notification.
     * @param actions							Optional. For Android 4.1+ (API Level 16+). If set, such actions will be presented 
     * 											in the notification. <b>Remember</b> that to close the notification after action is
     * 											clicked, you should use the "notifyID" parameter and set it as an extra of your
     * 											action to be able to close the notification from your application.
     */
    @Deprecated
    public static void notification_create(Context context,
    		boolean notSound, Integer notSoundRawId, boolean forceSound,
    		boolean multipleNot, String groupMultipleNotKey,
    		String notAction,
    		String notTitle, String notMessage,
    		String notTicker, String notContentInfo,
    		String bigContentTitle, String bigContentText, 
    		String bigContentSummary,
    		String bigContentImage, 
    		String bigStyleInboxContent, String bigStyleInboxSeparator,
    		Class<?> notClazz, Bundle extras,
    		boolean wakeUp,
    		NOTIFICATION_PRIORITY notPriority,
    		NOTIFICATION_STYLE notStyle,
    		NOTIFICATION_LOCK_SCREEN_PRIVACY notVisibility,    		
    		String largeIconResource,
    		RemoteViews contentView,    		
    		Integer notifyID,
    		NOTIFICATION_PROGRESSBAR_STYLE progressBarStyle,
    		NotificationProgressBarRunnable progressBarRunnable,
    		String progressBarFinishText,
    		List<Action> actions
    		) {
    	
    	notification_create(context, notSound, notSoundRawId, forceSound, multipleNot, groupMultipleNotKey, notAction, notTitle, notMessage, notTicker, notContentInfo, bigContentTitle, bigContentText, bigContentSummary, bigContentImage, bigStyleInboxContent, bigStyleInboxSeparator, null, notClazz, extras, wakeUp, notPriority, notStyle, notVisibility, largeIconResource, contentView, notifyID, progressBarStyle, progressBarRunnable, progressBarFinishText, actions);
    	
    }
    
    /**
     * This class is intented to show a progress bar in a notification 
     * , with {@link ToolBox#notification_create} method, while doing some 
     * tasks. When finished, progress bar ends an notification can be 
     * then discarded when opened or clicked.
     * 
     * <ul>
     * <li>Overwrite <code>doTask()</code> with your task code.</li>
     * <li>Use <code>notify(int progress)</code> to notify your progress.</li>
     * <li>Use <code>notifyFinish(String message)</code> when your process 
     * finishes to end progress bar. <i>The "message" parameter can be null, 
     * in this case, the parameter of notification creation will be used</i>.</li>
     * </ul>
     * 
     */
    public static abstract class NotificationProgressBarRunnable implements Runnable {
    	
    	private NotificationManager notificationManager = null;
    	private NotificationCompat.Builder notificationBuilder = null;
    	private boolean indeterminate = false;
    	protected String finalizationText = null;
    	private int notificationId = 0;
    	protected int pBarProgress = 0;
    	
    	public void initialize(NotificationManager notificationManager, 
    			NotificationCompat.Builder notificationBuilder,
    			int notificationId, boolean indeterminate, 
    			String finalizationText) {
    		this.notificationManager = notificationManager;
    		this.notificationBuilder = notificationBuilder;    		
    		this.notificationId = notificationId;
    		this.indeterminate = indeterminate;
    		this.finalizationText = 
    				(finalizationText!=null && finalizationText.length()>0?finalizationText:"Done");
    		
    		//This avoids the notification to be discarded 
    		this.notificationBuilder.setOngoing(true);
    	}
    	
    	@Override
        public void run() {
    		notify(0);
    		doTask();    		
    	}
    	
    	protected void notify(int pBarProgress) {
    		//We ensure max i always 100.
    		if(pBarProgress>100)
    			pBarProgress = 100;
    		
    		if(!indeterminate) 
    			notificationBuilder.setProgress(100, pBarProgress, false);
    		else
    			notificationBuilder.setProgress(0, 0, true);
    		    		
            notificationManager.notify(notificationId, notificationBuilder.build());
            notificationBuilder.setSound(null);
    	}
    	
    	protected void notifyFinish(String finalizationMessage) {
    		//When the task is finished, update the notification.
            if(finalizationMessage==null || 
            		(finalizationMessage!=null && finalizationMessage.length()==0)){
            	if(finalizationText!=null && finalizationText.length()>0){
            		notificationBuilder.setContentText(finalizationText);
            	}
            }else{
            	notificationBuilder.setContentText(finalizationMessage);
            }
    		
            //Removes the progress bar
            notificationBuilder.setProgress(0,0,false);
            notificationBuilder.setOngoing(false);
            notificationManager.notify(notificationId, notificationBuilder.build());
    	}
    	
    	
    	protected abstract void doTask();    	
    	
    }
    		
    
    /**
     * Gets the application Icon.
     * <br><br>
     * Since Android 5.0+ notification icons must follow a design guidelines,
     * Silhouette style, to be showed correctly.<br> 
     * See <a href="http://developer.android.com/design/style/iconography.html#notification">Silhouette icons</a>.
     * <br><br>
     * <b>ATTENTION</b>: This method will try to get the silhouette style icon if required. In
     * order this to work, the application has to have an icon named "ic_stat_silhouette" in png
     * format so it can be used. If does not exists, normal application icon will be used.
     *  
     * @param context
     * @return
     * @throws ApplicationPackageNotFoundException
     */
    private static int notification_getApplicationIcon(Context context) throws Exception{
    	try {
    		int appIconResourceId = 0;
    		//Since Android 5.0+ notification icons must follow a design guidelines to 
    		//be showed correctly.
    		//
	        //See: http://developer.android.com/design/style/iconography.html#notification
    		boolean useSilhouette = ToolBox.device_hasAPILevel(ApiLevel.LEVEL_21);
    		
    		if(useSilhouette){
    			appIconResourceId = context.getResources().getIdentifier("ic_stat_silhouette", "drawable", context.getPackageName());
    			if(appIconResourceId==0) {
    				//In this case, we will have an invalid icon that is not going to be viewed well.
    				ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        			appIconResourceId = app.icon;
    			}	
    		}else{
    			ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
    			appIconResourceId = app.icon;
    		}    		
			
			return appIconResourceId;
			
		} catch (NameNotFoundException e) {
			if(LOG_ENABLE)
				Log.e(TAG, "Application package not found!.");
			
			throw e;
		}
    }
	
    /**
	 * Creates an action for a notification. Only for Android 4.1 (API level 16+)
	 * <br><br>
	 * The generated Action also has a field {@link ToolBox#NOTIFICATION_ID} in its extra
	 * Bundle for afterwards being able to cancel the parent notification once the
	 * action is consumed.<br><br>
	 * 
	 * See also {@link ToolBox#notification_createActionButton}
	 * 
	 * @param iconId			The icon resource id for the action.
	 * @param title				The title.
	 * @param actionIntent		The pending intent of the action.
	 * @param notificationId	Used to be able to cancel a notification once
	 * 							the action is consumed.
	 * @return
	 * @Deprecated Use {@link ToolBox#notification_createActionButton}
	 */
	public static android.support.v4.app.NotificationCompat.Action 
		notification_createAction(int iconId, String title, PendingIntent actionIntent, 
				int notificationId) {
		
		Action action = new Action(iconId, title, actionIntent);
		action.getExtras().putInt(NOTIFICATION_ID, notificationId);
		
		return action;
	}
	
	/** When a notification has action buttons (since Android 4.1+), these actions
	 * can target an activity, service or receiver. */
	public static enum NOTIFICATION_ACTION_TARGET_TYPE {RECEIVER, SERVICE, ACTIVITY};
	
	/**
	 * Creates a notification action.
	 * 
	 * @param context				A context.
	 * @param actiontargetType		Optional. The target type of this action. 
	 * 								See {@link NOTIFICATION_ACTION_TARGET_TYPE}. if null,
	 * 								BroadcastReceiver type is used.
	 * @param actionKey				The action, an String key, of this action.
	 * @param targetClazz			The broadcast receiver target of this action.
	 * @param notificationIdKey		The notification Id field in extras that will contain
	 * 								the notification id. This notification Id will be used
	 * 								to close the notification after action is triggered. 								
	 * @param notificationId		The notification id. Used to be able to cancel a 
	 * 								notification once the action is consumed.
	 * @param iconId				The icon resource Id.
	 * @param title					The title of the action.
	 * @param extras				Optional. Some extras of the notification.
	 * @return		The action object, see {@link android.support.v4.app.NotificationCompat.Action}.
	 */
	public static android.support.v4.app.NotificationCompat.Action 
		notification_createActionButton(Context context,
			NOTIFICATION_ACTION_TARGET_TYPE targetType,
			String actionKey,				
			Class<?> targetClazz, 
			String notificationIdKey, int notificationId,
			int iconId, String title, Bundle extras) {
		
		Intent i = new Intent(context, targetClazz);
		i.setAction(actionKey);
		i.putExtra(notificationIdKey, notificationId);
		
		if(extras!=null){
			i.putExtras(extras);
		}
		
		PendingIntent pIntent = null;
		if(targetType==null) {
			pIntent = PendingIntent.getBroadcast(context, ToolBox.random_getRandom().nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);
		}else{
			switch (targetType) {
				case ACTIVITY:
					pIntent = PendingIntent.getActivity(context, ToolBox.random_getRandom().nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);
					break;
				case RECEIVER:
					pIntent = PendingIntent.getBroadcast(context, ToolBox.random_getRandom().nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);
					break;
				case SERVICE:
					pIntent = PendingIntent.getService(context, ToolBox.random_getRandom().nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);
					break;
				default:
					pIntent = PendingIntent.getBroadcast(context, ToolBox.random_getRandom().nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);
					break;
			}
		}
		
		android.support.v4.app.NotificationCompat.Action action = 
    			new android.support.v4.app.NotificationCompat.Action(iconId, title, pIntent);
		
		return action;
	}
	
	/**
	 * This method dismisses a notification from the status bar.
	 * 
	 * @param context
	 * @param notificationId	The notification id to cancel.
	 */
	public static void notification_cancel(Context context, int notificationId) {
		if(notificationId!=0) {
			NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);		
			manager.cancel(notificationId);
		}
	}
    
	
	// Android 6 Permissions new system --------------------------------------------------------------------------------------------------------
	/**
	 * The methods in this section allows you to ensure that this points bellow can be achivied
	 * in order to run an application with Android 6+ versions.
	 * 
	 * 	Android >= 6+
	 * 	=============
	 *
	 *	Start the App:
	 *	  Ask for permission (OK)
	 *	    Accept     -> App runs normally with the service that requires the permissions (OK)
	 *	    Not accept -> App runs normally without the service that requires the permissions (OK)
	 *	
	 *	Granted permissions:
	 *	  Start the App -> App runs normally with the service that requires the permissions (OK)
	 *	  
	 *  In a running App with granted permissions:
	 *  	Deny the permissions -> When returning to the App, it Asks for permissions (OK)
	 *        Accept     -> App runs normally with the service that requires the permissions (OK)
	 *	      Not accept -> App runs normally without the service that requires the permissions (OK)	
	 *	
	 *	User denies the permission and marks "Never ask again"
	 *	  Start the App:
	 *	    - Show a message to the user about the required permissions (OK)
	 *		    Accept. Ask for permission (OK)
	 *	    	  		  Accept     -> App runs normally with the service that requires the permissions (OK)
	 *	    			  Not accept -> App runs normally without the service that requires the permissions (OK)
	 *	
	 *	Android < 6
	 *	===========
	 *	
	 *	Start the App:
	 *	    Do not ask for permissions and runs normally (OK)
	 */	
	
	public static final String ANDROID_PERMISSION_READ_CALENDAR = "android.permission.READ_CALENDAR";
	public static final String ANDROID_PERMISSION_WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";
	public static final String ANDROID_PERMISSION_CAMERA = "android.permission.CAMERA";
	public static final String ANDROID_PERMISSION_ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
	public static final String ANDROID_PERMISSION_ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
	public static final String ANDROID_PERMISSION_RECORD_AUDIO = "android.permission.RECORD_AUDIO";
	public static final String ANDROID_PERMISSION_READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
	public static final String ANDROID_PERMISSION_CALL_PHONE = "android.permission.CALL_PHONE";
	public static final String ANDROID_PERMISSION_READ_CALL_LOG = "android.permission.READ_CALL_LOG";
	public static final String ANDROID_PERMISSION_WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
	public static final String ANDROID_PERMISSION_ADD_VOICEMAIL = "android.permission.ADD_VOICEMAIL";
	public static final String ANDROID_PERMISSION_USE_SIP = "android.permission.USE_SIP";
	public static final String ANDROID_PERMISSION_PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
	public static final String ANDROID_PERMISSION_BODY_SENSORS = "android.permission.BODY_SENSORS";
	public static final String ANDROID_PERMISSION_SEND_SMS = "android.permission.SEND_SMS";
	public static final String ANDROID_PERMISSION_RECEIVE_SMS = "android.permission.RECEIVE_SMS";
	public static final String ANDROID_PERMISSION_READ_SMS = "android.permission.READ_SMS";
	public static final String ANDROID_PERMISSION_RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
	public static final String ANDROID_PERMISSION_RECEIVE_MMS = "android.permission.RECEIVE_MMS";
	public static final String ANDROID_PERMISSION_READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
	public static final String ANDROID_PERMISSION_WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
	
	/**
	 * Permissions required to interact with the calendar.
	 */
    public static final Map<String, String> PERMISSION_CALENDAR;
    static {
    	PERMISSION_CALENDAR = new HashMap<String, String>();
    	PERMISSION_CALENDAR.put(ANDROID_PERMISSION_READ_CALENDAR, "Calendar");
    	PERMISSION_CALENDAR.put(ANDROID_PERMISSION_WRITE_CALENDAR, "Calendar");
    }
    /** 
     * Permissions required to interact with the camera
     */
    public static final Map<String, String> PERMISSION_CAMERA;
    static {
    	PERMISSION_CAMERA = new HashMap<String, String>();
    	PERMISSION_CAMERA.put(ANDROID_PERMISSION_CAMERA, "Camera");    	
    }
    /**
     * Permissions required to interact with the location services
     */
    public static final Map<String, String> PERMISSION_LOCATION;
    static {
    	PERMISSION_LOCATION = new HashMap<String, String>();
    	PERMISSION_LOCATION.put(ANDROID_PERMISSION_ACCESS_COARSE_LOCATION, "Location");
    	PERMISSION_LOCATION.put(ANDROID_PERMISSION_ACCESS_FINE_LOCATION, "Location");
    }
    /**
     * Permissions required to interact with the microphone
     */
    public static final Map<String, String> PERMISSION_MICROPHONE;
    static {
    	PERMISSION_MICROPHONE = new HashMap<String, String>();
    	PERMISSION_MICROPHONE.put(ANDROID_PERMISSION_RECORD_AUDIO, "Microphone");    	
    }
    /**
     * Permissions required to interact with the phone functions like calls,
     * voice mail, call log, etc.
     */
    public static final Map<String, String> PERMISSION_PHONE;
    static {
    	PERMISSION_PHONE = new HashMap<String, String>();
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_READ_PHONE_STATE, "Phone");
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_CALL_PHONE, "Phone");
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_READ_CALL_LOG, "Phone");
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_WRITE_CALL_LOG, "Phone");
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_ADD_VOICEMAIL, "Phone");
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_USE_SIP, "Phone");
    	PERMISSION_PHONE.put(ANDROID_PERMISSION_PROCESS_OUTGOING_CALLS, "Phone");    	    	
    }
    /**
     * Permissions required to interact with the device sensors
     */
    public static final Map<String, String> PERMISSION_SENSORS;
    static {
    	PERMISSION_SENSORS = new HashMap<String, String>();
    	PERMISSION_SENSORS.put(ANDROID_PERMISSION_BODY_SENSORS, "Sensors");    	    	    	
    }
    /**
     * Permissions required to interact with the SMS/WAP/MMS 
     * device capabilities. 
     */
    public static final Map<String, String> PERMISSION_SMS;
    static {
    	PERMISSION_SMS = new HashMap<String, String>();
    	PERMISSION_SMS.put(ANDROID_PERMISSION_SEND_SMS, "SMS");
    	PERMISSION_SMS.put(ANDROID_PERMISSION_RECEIVE_SMS, "SMS");
    	PERMISSION_SMS.put(ANDROID_PERMISSION_READ_SMS, "SMS");
    	PERMISSION_SMS.put(ANDROID_PERMISSION_RECEIVE_WAP_PUSH, "SMS");
    	PERMISSION_SMS.put(ANDROID_PERMISSION_RECEIVE_MMS, "SMS");    	    	    	
    }
    /**
     * Permissions required to interact with the storage service.
     */
    public static final Map<String, String> PERMISSION_STORAGE;
    static {
    	PERMISSION_STORAGE = new HashMap<String, String>();
    	PERMISSION_STORAGE.put(ANDROID_PERMISSION_READ_EXTERNAL_STORAGE, "Storage");
    	PERMISSION_STORAGE.put(ANDROID_PERMISSION_WRITE_EXTERNAL_STORAGE, "Storage");    	    	    	    	
    }
    
    //Note: 
    // validateRequestPermissionsRequestCode in FragmentActivity requires requestCode 
    // to be of 8 bits, meaning the range is from 0 to 255.
    /** Android 6 request code for Calendar usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_CALENDAR = 90;
    /** Android 6 request code for Camera usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_CAMERA = 91;
    /** Android 6 request code for Location service usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_LOCATION = 92;
    /** Android 6 request code for Microphone usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_MICROPHONE = 93;
    /** Android 6 request code for Phone (calls, call log, etc) usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_PHONE = 94;
    /** Android 6 request code for Sensors usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_SENSORS = 95;
    /** Android 6 request code for SMS/WAP/MMS usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_SMS = 96;
    /** Android 6 request code for Storage usage permission request. */
    final public static int PERMISSIONS_REQUEST_CODE_ASK_STORAGE = 97;
    
	
	/**
	 * Beginning in Android 6.0 (API level 23), users grant permissions to apps 
	 * while the app is running, not when they install the app. If the device is 
	 * running Android 5.1 (API level 22) or lower, or the app's targetSdkVersion 
	 * is 22 or lower, the system asks the user to grant the permissions at 
	 * install time<br><br>
	 * 
	 * System permissions are divided into two categories, normal and dangerous 
	 * (<a href="http://developer.android.com/intl/es/guide/topics/security/permissions.html#normal-dangerous">
	 * http://developer.android.com/intl/es/guide/topics/security/permissions.html#normal-dangerous</a>):
	 * <ul>
	 * 	<li>Normal permissions do not directly risk the user's privacy. If your app 
	 * 		lists a normal permission in its manifest, the system grants the 
	 * 		permission automatically. See the list at  		
	 * 		<a href="http://developer.android.com/intl/es/guide/topics/security/normal-permissions.html">
	 * 		http://developer.android.com/intl/es/guide/topics/security/normal-permissions.html</a>
	 *  </li>
	 *  <li>Dangerous permissions can give the app access to the user's confidential data. If 
	 *  	your app lists a normal permission in its manifest, the system grants the permission 
	 *  	automatically. If you list a dangerous permission, the user has to explicitly 
	 *  	give approval to your app. If an app requests a dangerous permission listed in 
	 *  	its manifest, and the app already has another dangerous permission in the same 
	 *  	permission group, the system immediately grants the permission without any 
	 *  	interaction with the user</li>
	 * </ul>
	 * 
	 * This method checks for permissions to ask to the user to allow them if they are
	 * not already granted.<br><br>
	 * 
	 * See http://developer.android.com/intl/es/training/permissions/index.html
	 * <br><br>
	 * 
	 * @param context	The activity context.
	 * @param pList		The list of permissions to be granted. Format: Map of [permission,permission_name]
	 * @param requestCode	The request code to be checked in onRequestPermissionsResult of the activity.
	 * @param dialogTitle	If there are permissions to be granted, a dialog pop-ups, set here the title.
	 * @param dialogAcceptBtnText If there are permissions to be granted, a dialog pop-ups, set here 
	 * 							  the accept button text.
	 * @param dialogText If there are permissions to be granted, a dialog pop-ups, set here the text that
	 * 					 presents the permission that need to be granted.
	 * @param showRationaleDialog If set to TRUE, a rationale dialog explaining why permissions are required
	 * 							  is showed before permission approval window. If FALSE, the permission 
	 * 							  approval window will be presented without any explanation.
	 */
	public static void permission_askFor (final Activity context, final Map<String, String> pList, 
			final int requestCode, String dialogTitle, String dialogAcceptBtnText, String dialogDenyBtnText,
			String dialogText, boolean showRationaleDialog) {
    	
		//We only check if not all permissions are granted and we are above/equal 23 API level.
		if(device_getAPILevel()<23 || 
				(device_getAPILevel()>=23 && permission_areGranted(context, pList.keySet())) ) {
			return;
		}
				
    	final List<String> permissionsListToAsk = new ArrayList<String>();
    	final List<String> permissionsNamesListToAsk = new ArrayList<String>(); 
    	
    	//Prepare the permissions (and their labels) we need to ask for    
    	Set<String> keys = pList.keySet();
    	for(String p:keys) {
    		permission_add(context, permissionsListToAsk, permissionsNamesListToAsk, p, pList.get(p));    			
    	}
    	//Ask for permissions
    	if (permissionsListToAsk.size() > 0) { 
    		//Permissions we are able to ask for
    		//
    		//We can choose to show always a rationale dialog explaining why 
    		//we need the permissions.
    		if(showRationaleDialog) {
    			showDescriptivePermissionsDialog(context, permissionsListToAsk, permissionsNamesListToAsk, 
            			requestCode, dialogTitle, dialogAcceptBtnText, dialogDenyBtnText, dialogText);    			
    		}else{
    			//Show directly the permissions approval without any explanation
	            ActivityCompat.requestPermissions(context, 
	            		permissionsListToAsk.toArray(new String[permissionsListToAsk.size()]),
	            		requestCode);
    		}
    	}else{
    		//No permission ask is required
    	}    	
    }
	
	/**
	 * Shows a rationale dialog explaining why permissions are required
	 * before asking for them.
	 *  
	 * @param context
	 * @param permissionsList
	 * @param permissionsNeeded
	 * @param requestCode
	 * @param dialogTitle
	 * @param dialogAcceptBtnText
	 * @param dialogDenyBtnText
	 * @param dialogText
	 */
	private static void showDescriptivePermissionsDialog(final Activity context, 
			final List<String> permissionsList,
			List<String> permissionsNeeded,
			final int requestCode,
			String dialogTitle, String dialogAcceptBtnText, 
			String dialogDenyBtnText, String dialogText
			) {
		// Need Rationale (explanation before ask for permissions)
        String message = dialogText + permissionsNeeded.get(0);                
        for (int i = 1; i < permissionsNeeded.size(); i++)
            message = message + ", " + permissionsNeeded.get(i);
        
        message = message + ".";
        ToolBox.dialog_showCustomActionsDialog(context, 
        		dialogTitle, message, 
        		dialogAcceptBtnText, new Runnable() {
					
					@Override
					public void run() {
						ActivityCompat.requestPermissions(context,
								permissionsList.toArray(new String[permissionsList.size()]),
								requestCode);							
					}
				}, 
				dialogDenyBtnText, new Runnable() {
					
					@Override
					public void run() {
						//Do nothing							
					}
				}, 
        		null, null);
	}
	
	/** The possible result after an user decides about a permission. See {@link ToolBox#permission_checkAskPermissionsresult} */
	public static enum PermissionResult {DENIED, DENIED_NEVER_ASK_AGAIN, GRANTED};
	
	/**
	 * Checks the results of an Android 6+ permissions ask. Use it in the method
	 * {@link Activity#onRequestPermissionsResult}. This method returns:<br>
	 * <br> 
	 * -1: DENIED_NEVER_ASK_AGAIN Denied permission and user selected "Never ask again". 
	 * 							  If is the case, do not forget to not ask again 
	 * 							  for permissions.  
	 *  0: DENIED Denied permission.
	 *  1: GRANTED if all permissions are granted. 
	 * 
	 * @param permissions	Permissions asked.
	 * @param grantResults	Results of the ask.
	 * @return TRUE/FALSE
	 */
	public static PermissionResult permission_checkAskPermissionsresult(Activity context, String[] permissions, int[] grantResults) {
    	
		PermissionResult res = PermissionResult.DENIED;
    		
    	// Create the list with the results
    	Map<String, Integer> perms = new HashMap<String, Integer>();    		
        for (int i = 0; i < permissions.length; i++) {
        	perms.put(permissions[i], grantResults[i]);
        }            
            
        // Check if all permissions are granted
        Set<String> pNameList = perms.keySet();
        for(String pName:pNameList) {
        	if(perms.get(pName) == PackageManager.PERMISSION_GRANTED){
        		res = PermissionResult.GRANTED;
            }else{
            	if (!ActivityCompat.shouldShowRequestPermissionRationale(context, pName)){
            		res = PermissionResult.DENIED_NEVER_ASK_AGAIN;
            	}else{
            		res = PermissionResult.DENIED;
            	}
            	
            	break;
            }
        }
        
    	return res;
    }
	
	/**
	 * Checks if a permission is granted. In case of API level minor than 23,
	 * we always return TRUE.
	 * 
	 * @param context	An activity context.
	 * @param permission	The permission to check.
	 * @return	TRUE/FALSE
	 */
	public static boolean permission_isGranted(Activity context, String permission) {
		if(device_getAPILevel()<23) {
			return true;
		}else{
			return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
		}
	}
	
	/**
	 * Checks if a permission is granted. In case of API level minor than 23,
	 * we always return TRUE.
	 * 
	 * @param context	The context.
	 * @param permission	The permission to check.
	 * @return	TRUE/FALSE
	 */
	public static boolean permission_isGranted(Context context, String permission) {
		if(device_getAPILevel()<23) {
			return true;
		}else{
			return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
		}
	}
	
	/**
	 * Returns TRUE only if all permissions are granted. In case of API level minor than 23,
	 * we always return TRUE.
	 * 
	 * @param context	An activity context.
	 * @param permissions	The permissions list.	
	 * @return	TRUE only if all permissions are granted.
	 */
	public static boolean permission_areGranted(Activity context, List<String> permissions) {
		boolean granted = true;
		for(String p:permissions) {
			if(!permission_isGranted(context, p)){
				granted = false;
				break;
			}
		}
		
		return granted;
	}
	
	/**
	 * Returns TRUE only if all permissions are granted. In case of API level minor than 23,
	 * we always return TRUE.
	 * 
	 * @param context	An activity context.
	 * @param permissions	The permissions list.	
	 * @return	TRUE only if all permissions are granted.
	 */
	public static boolean permission_areGranted(Activity context, Set<String> permissions) {
		boolean granted = true;
		for(String p:permissions) {
			if(!permission_isGranted(context, p)){
				granted = false;
				break;
			}
		}
		
		return granted;
	}
	
	/**
	 * Returns TRUE only if all permissions are granted. In case of API level minor than 23,
	 * we always return TRUE.
	 * 
	 * @param context	The context.
	 * @param permissions	The permissions list.	
	 * @return	TRUE only if all permissions are granted.
	 */
	public static boolean permission_areGranted(Context context, Set<String> permissions) {
		boolean granted = true;
		for(String p:permissions) {
			if(!permission_isGranted(context, p)){
				granted = false;
				break;
			}
		}
		
		return granted;
	}
	
	/**
	 * Checks if a permission is the specified Android permissions list.
	 * 
	 * @param permissions	A list of Android permissions to look in. See 
	 * 						<a href="https://developer.android.com/guide/topics/security/permissions.html?hl=es"> Android permission</a>
	 * @param permissionToCheck	A permission to find in the permissions list. 
	 * 							Use ToolBox.ANDROID_PERMISSION_<permission_name>.
	 * @return
	 */
	public static boolean permission_isPermissionInList(String[] permissions, String permissionToCheck){
		for(String p:permissions){
			if(p.equals(permissionToCheck)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Conforms the list of needed permissions and the list of permissions to ask. This lists
	 * can differ because the could have answer "Never ask again" in the permission ask window.
	 * 
	 * @param context	An activity context
	 * @param permissionsListToAsk	The list of permissions that need to be asked for. This list is for afterwards usage.
	 * @param permissionsNamesListToAsk	The list of permission names that need to be asked for. This list is for afterwards usage.
	 * @param permission	The permission to check.
	 * @param permissionLabel The permission label. 
	 */
	private static void permission_add(Activity context, 
			List<String> permissionsListToAsk,
			List<String> permissionsNamesListToAsk,
			String permission, String permissionLabel) {
		
		if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
			if(!permissionsListToAsk.contains(permission)) {
				permissionsListToAsk.add(permission);
				permissionsNamesListToAsk.add(permissionLabel);
			}			
        }        
    }
	
	
    // Power saving ----------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Makes the device to wake-up. yeah!<br><br>
     * 
     * <b>Note</b>:<br><br>
     * 	Requires the permission android.permission.WAKE_LOCK
     *  
     *  @deprecated use {@link ToolBox#powersaving_getWakeUp} instead.
     *   
     * @param ctx
     */
	@Deprecated
    public static void powersaving_wakeUp(Context ctx) {
    	    	
    	if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "javocsoft_library_wakeup");
        wakeLock.acquire();
    }
    
   /**
    * Generates a new WakeUp object.<br><br> 
    * 
    * To start using it do:
    * <pre>wakeLock.acquire();</pre>
    * To stop using it:
    * <pre>wakeLock.release();</pre> 
    * 
    * <b>Note</b>:<br><br>
     * 	Requires the permission {@link android.permission.WAKE_LOCK}<br><br>
    * 
    * If you need to keep the CPU running in order to complete some work before
    * the device goes to sleep, you can use a PowerManager system service feature
    * called wake locks. Wake locks allow your application to control the power 
    * state of the host device.<br><br>
	*
	* Creating and holding wake locks can have a dramatic impact on the host 
	* device's battery life. Thus you should use wake locks only when strictly 
	* necessary and hold them for as short a time as possible.<br><br>
	* 
	* Recommendation:<br><br>
	* 
	* You should never need to use a wake lock in an activity. If you want to keep 
	* the screen on in your activity, use FLAG_KEEP_SCREEN_ON. To do it put in
	* onCreate() the following:<br>
	* <pre>getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);</pre>
	* You can also put in the activity layout the following parameter:
	* <pre>android:keepScreenOn="true"</pre>
	* 
	* More info about WakeLocks at <a href="http://developer.android.com/intl/es/training/scheduling/wakelock.html">
	* Google Developer</a>.
	* 
    * @param ctx
    * @param wakeUpName
    * @param switchOnDevice
    * @return
    */
    public static PowerManager.WakeLock powersaving_getWakeUp(Context ctx, String wakeUpName, boolean switchOnDevice) {
    	
    	PowerManager.WakeLock wakeLock = null;

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        
        int flags = PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE;
        if(switchOnDevice) {
        	flags = flags | PowerManager.ACQUIRE_CAUSES_WAKEUP;
        }
        String wkName = "javocsoft_library_wakeup";
        if(wakeUpName!=null && wakeUpName.length()>0)
        	wkName = wakeUpName;
        
        wakeLock = pm.newWakeLock(flags, wkName);
        
        return wakeLock; 
    }
    
	// Net Related -----------------------------------------------------------------------------------------------------------------------------
	
    
    /**
	 * Makes a Http operation.
	 * 
	 * This method set a parameters to the request that avoid being waiting 
	 * for the server response or once connected, being waiting to receive 
	 * the data.
	 * 
	 * @param method		Method type to execute. @see HTTP_METHOD.
	 * @param url			URL of the request.
	 * @param jsonDataKey	Optional. If not null, the JSON data will be sent under
	 * 						this key in the POST. Otherwise the JSON data will be 
	 * 						directly all the body of the POST.
	 * @param jsonData		Optional. The body content of the request (JSON).
	 * @param headers		The headers to include in the request.
	 * @return The content of the request if there is one.
	 * @throws Exception
	 */
	public static String net_httpclient_doAction(HTTP_METHOD method, String url, String jsonDataKey, String jsonData, Map<String, String> headers) throws ConnectTimeoutException, SocketTimeoutException, Exception{
    	return net_httpclient_doAction(method, url, jsonDataKey, jsonData, headers, false);		
    }
	
	/**
	 * Makes a Http operation.
	 * 
	 * This method set a parameters to the request that avoid being waiting 
	 * for the server response or once connected, being waiting to receive 
	 * the data.
	 * 
	 * @param method		Method type to execute. @see HTTP_METHOD.
	 * @param url			URL of the request.
	 * @param jsonDataKey	Optional. If not null, the JSON data will be sent under
	 * 						this key in the POST. Otherwise the JSON data will be 
	 * 						directly all the body of the POST.
	 * @param jsonData		Optional. The body content of the request (JSON).
	 * @param headers		The headers to include in the request.
	 * @param ignoreSSL		If set to TRUE, we ignore any error relative to 
	 * 						certificates when accessing with HTTPS.
	 * @return The content of the request if there is one.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String net_httpclient_doAction(HTTP_METHOD method, String url, String jsonDataKey, String jsonData, Map<String, String> headers, boolean ignoreSSL) throws ConnectTimeoutException, SocketTimeoutException, Exception{
    	String responseData = null;
		
		DefaultHttpClient httpclient = null;
		
    	if(!ignoreSSL){
    		httpclient = new DefaultHttpClient();
    	}else{
    		//We allow any site with any certificate when using HTTPS
    		SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
    	    sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    	    SchemeRegistry schemeRegistry = new SchemeRegistry();
    	    schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    	    schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
    	    ClientConnectionManager cm = new SingleClientConnManager(null, schemeRegistry);
    	    httpclient = new DefaultHttpClient(cm, null);
    	}
		
    	// The time it takes to open TCP connection.
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_DEFAULT_TIMEOUT);
        // Timeout when server does not send data.
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_DEFAULT_DATA_RECEIVAL_TIMEOUT);
        // Some tuning that is not required for bit tests.
		//httpclient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
		httpclient.getParams().setParameter(CoreConnectionPNames.TCP_NODELAY, true);
    	
    	HttpRequestBase httpMethod = null;    	
    	switch(method){
			case POST:
				httpMethod = new HttpPost(url);
				//Add the body to the request.				
				List<NameValuePair> params = null;
				if(jsonDataKey!=null && jsonDataKey.length()>0) {
					//We set the JSON data under a specific data key.
					params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair(jsonDataKey, jsonData));
					((HttpPost)httpMethod).setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				}else{
					//The POSt will only contain the JSON
					StringEntity se = new StringEntity(jsonData);
			    	((HttpPost)httpMethod).setEntity(se);
				}
				break;
			case DELETE:
				httpMethod = new HttpDelete(url);
				break;
			case GET:
				httpMethod = new HttpGet(url);
				break;
    	}
    	
    	//Add the headers to the request.
    	if(headers!=null){
	    	for(String header:headers.keySet()){
	    		httpMethod.setHeader(header, headers.get(header));
	    	}	    	
    	}
    	
    	HttpResponse response = httpclient.execute(httpMethod);
    	if(LOG_ENABLE) {
    		Log.d(TAG, "HTTP OPERATION: Read from server - Status Code: " + response.getStatusLine().getStatusCode());
    		Log.d(TAG, "HTTP OPERATION: Read from server - Status Message: " + response.getStatusLine().getReasonPhrase());
		}
    	
    	//Get the response body if there is one.
    	HttpEntity entity = response.getEntity();
    	if (entity != null) {
    		//responseData = EntityUtils.toString(entity, "UTF-8");
    	    
    		InputStream instream = entity.getContent();
    	    responseData = IOUtils.convertStreamToString(instream);
    	    instream.close();
    	    
    	    if(LOG_ENABLE)
    	    	Log.i(TAG, "HTTP OPERATION: Read from server - return: " + responseData);
    	}
    	
    	if (response.getStatusLine().getStatusCode() != 200) {
    		throw new Exception("Http operation "+method.name()+" failed with error code " + 
    				response.getStatusLine().getStatusCode() + "("+ 
    				response.getStatusLine().getReasonPhrase() +")");
    	}
    	
    	if( response.getEntity() != null ) {
            response.getEntity().consumeContent();
        }
    	
    	return responseData;
    }
	
	/**
	 * Makes a Http operation.
	 * 
	 * This method set a parameters to the request that avoid being waiting 
	 * for the server response or once connected, being waiting to receive 
	 * the data.
	 * 
	 * @param method		Method type to execute. @See HTTP_METHOD.
	 * @param url			Url of the request.
	 * @param jsonData		The body content of the request (JSON). Can be null.
	 * @param headers		The headers to include in the request.
	 * @return The content of the request if there is one.
	 * @throws Exception
	 */
	public static String net_httpclient_doAction(HTTP_METHOD method, String url, String jsonData, Map<String, String> headers) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		return net_httpclient_doAction(method, url, null, jsonData, headers, false);
    }
	
	/**
	 * Makes a Http operation.
	 * 
	 * This method set a parameters to the request that avoid being waiting 
	 * for the server response or once connected, being waiting to receive 
	 * the data.
	 * 
	 * @param method		Method type to execute. @See HTTP_METHOD.
	 * @param url			Url of the request.
	 * @param jsonData		The body content of the request (JSON). Can be null.
	 * @param headers		The headers to include in the request.
	 * @param ignoreSSL		If set to TRUE, we ignore any error relative to 
	 * 						certificates when accessing with HTTPS.
	 * @return The content of the request if there is one.
	 * @throws Exception
	 */
	public static String net_httpclient_doAction(HTTP_METHOD method, String url, String jsonData, Map<String, String> headers, boolean ignoreSSL) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		return net_httpclient_doAction(method, url, null, jsonData, headers, ignoreSSL);
    }
	
	 /**
	  * Gets the status of the network service.
	  * 
	  * @param context
	  * @return
	  */
	 public static boolean net_isNetworkAvailable(Context context) {
	 	    ConnectivityManager connectivityManager 
	 	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	 	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	 	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	 }
	 
	 /**
	  * Gets the status of the location service.
	  * 
	  * @param context
	  * @return
	  */
	 @SuppressWarnings("deprecation")
	 @TargetApi(Build.VERSION_CODES.KITKAT)
	 public static boolean isLocationEnabled(Context context) {
		 int locationMode = 0;
		 String locationProviders;

		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
	    	locationMode = Settings.Secure.LOCATION_MODE_OFF;
	    	try {
	    		locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

	        } catch (SettingNotFoundException e) {}

	        return locationMode != Settings.Secure.LOCATION_MODE_OFF;

		 }else{
	        locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	        return !TextUtils.isEmpty(locationProviders);
		 }
	 } 
	 
	 /**
	 * Gets the network carrier.
	 *
	 * If the device is an emulator returns "EMU", if no SIM is present returns
	 * NOSIM.
	 *
	 * @See NETWORK_PROVIDER.
	 *
	 * @param ctx
	 * @return
	 */
	public static NETWORK_PROVIDER net_getCurrentNetworkOperator(Context ctx) {
		final String emulatorDevice = "Android";
		final String noSIMDevice = "";

		// if(Debug.isDebuggerConnected()){}

		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String networkOperator = tm.getNetworkOperatorName();
		if (emulatorDevice.equalsIgnoreCase(networkOperator)) {
			return NETWORK_PROVIDER.EMU;
		} else if (emulatorDevice.equalsIgnoreCase(noSIMDevice)) {
			return NETWORK_PROVIDER.NOSIM;
		} else {
			try {
				return NETWORK_PROVIDER.valueOf(networkOperator.toUpperCase());
			} catch (Exception e) {
				// Non expected network provider.
				return NETWORK_PROVIDER.UNKNOWN;
			}
		}
	}
	
	/** The mobile telecommunication technologies.  */
	public static enum NETWORK_TYPE {_2G, _3G, _4G, WiFi, UNKNOWN};
	
	/**
	 * Gets the network type that is consuming the device. See {@link NETWORK_TYPE}.
	 *
	 * @param context
	 * @return 	The network type, see {@link NETWORK_TYPE}. UNKNOWN is returned in case of a 
	 * 			non-recognized technology or in case TelephonyManager could not be get.
	 */
	public static NETWORK_TYPE net_getNetworkType(Context context) {
	    
		TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);	    
	    if(mTelephonyManager!=null) {

	    	if(net_isWifiOn(context)) {
	    		return NETWORK_TYPE.WiFi;
	    	}
	    	
	    	int networkType = mTelephonyManager.getNetworkType();
		    switch (networkType) {
		        case TelephonyManager.NETWORK_TYPE_GPRS:
		        case TelephonyManager.NETWORK_TYPE_EDGE:
		        case TelephonyManager.NETWORK_TYPE_CDMA:
		        case TelephonyManager.NETWORK_TYPE_1xRTT:
		        case TelephonyManager.NETWORK_TYPE_IDEN:
		            return NETWORK_TYPE._2G;
		        case TelephonyManager.NETWORK_TYPE_UMTS:
		        case TelephonyManager.NETWORK_TYPE_EVDO_0:
		        case TelephonyManager.NETWORK_TYPE_EVDO_A:
		            /**
		             https://en.wikipedia.org/wiki/Evolution-Data_Optimized says that  
		             NETWORK_TYPE_EVDO_0 & NETWORK_TYPE_EVDO_AEV-DO is an evolution 
		             of the CDMA2000 (IS-2000) standard that supports high data rates.
		             CDMA2000 - https://en.wikipedia.org/wiki/CDMA2000, CDMA2000 is a 
		             family of 3G[1] mobile technology standards for sending voice, 
		             data, and signaling data between mobile phones and cell sites.*/
		        case TelephonyManager.NETWORK_TYPE_HSDPA:
		        case TelephonyManager.NETWORK_TYPE_HSUPA:
		        case TelephonyManager.NETWORK_TYPE_HSPA:
		        case TelephonyManager.NETWORK_TYPE_EVDO_B:
		        case TelephonyManager.NETWORK_TYPE_EHRPD:
		        case TelephonyManager.NETWORK_TYPE_HSPAP:
		            /**
		             * 3g HSDPA, HSPAP(HSPA+) are main network type which are under 
		             * the 3g Network. But from other constants, also it will be 3G, 
		             * like HSPA, HSDPA, etc which are in 3g case.
		             * Some other cases added after checking them.
		             * See https://en.wikipedia.org/wiki/4G#Data_rate_comparison */
		            return NETWORK_TYPE._3G;
		        case TelephonyManager.NETWORK_TYPE_LTE:
		        	/** 
		        	 * LTE https://en.wikipedia.org/wiki/LTE_(telecommunication)
		        	 * (marketed as 4G LTE) */		            
		            return NETWORK_TYPE._4G;
		        default:
		            return NETWORK_TYPE.UNKNOWN;
		    }
	    }else{
	    	if(ToolBox.LOG_ENABLE)
	    		Log.w(TAG, "Could not access to TelephonyManager!.");
	    }
	    
	    return NETWORK_TYPE.UNKNOWN;
	}
	 
	/**
	 * Returns TRUE if WiFi is ON and connected.
	 * <br><br>
	 * This method requires the caller to hold the permission android.Manifest.permission.ACCESS_NETWORK_STATE
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static boolean net_isWifiOn(Context context) {
		boolean res = false;
		
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connManager!=null) {
			
			if(ToolBox.device_hasAPILevel(ApiLevel.LEVEL_21)){
				Network[] networks = connManager.getAllNetworks();
				for(Network n:networks) {
					NetworkInfo nInfo = connManager.getNetworkInfo(n);
					if( nInfo != null && nInfo.getType() == ConnectivityManager.TYPE_WIFI && nInfo.isConnected()){
						res = true;
						break;
					}
				}
			}else{
				NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mWifi!=null && mWifi.isConnected()) {
					res = true;
				}
			}			
		}
		return res;
	}
	
	// Storage Related -----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Gets bytes from a resource located in the "assets" folder.
	 * 
	 * @param context
	 * @param fileName	The file name of the resource.
	 * @return
	 */
	public static byte[] storage_readAssetResource(Context context, String fileName){
		
		 try{
			 InputStream asset = context.getAssets().open(fileName);
			 
			 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 
			 byte[] buffer = new byte[asset.available()];
		     
			 asset.read(buffer);		        
		     byteArrayOutputStream.write(buffer);
		     asset.close();
		 
		     return byteArrayOutputStream.toByteArray();
		 }catch (Exception e){
			 if(LOG_ENABLE)
				 Log.e("TollBox_ERROR","storage_readRawResource() Error obtaining raw data: " + e.getMessage(),e);   
		     return null;
		 }
	}
	
	/**
	 * Gets bytes from a resource located in the "raw" folder.
	 * 
	 * @param context
	 * @param fileName	The file name of the resource.
	 * @return
	 */
	public static byte[] storage_readRawResource(Context context, String fileName){
		 int resId = context.getResources().getIdentifier(fileName,"raw", context.getPackageName());
		 
		 try{
			 InputStream raw = context.getResources().openRawResource(resId);
			 
			 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 
			 byte[] buffer = new byte[raw.available()];
		     
			 raw.read(buffer);		        
		     byteArrayOutputStream.write(buffer);
		     raw.close();
		 
		     return byteArrayOutputStream.toByteArray();
		 }catch (Exception e){
			 if(LOG_ENABLE)
				 Log.e("TollBox_ERROR","storage_readRawResource() Error obtaining raw data: " + e.getMessage(),e);   
		     return null;
		 }
	 }
	 
	 
	 /**
	  * This method returns the application external folder. All the stuff
	  * saved in this folder is deleted when the application is uninstalled.
	  * 
	  * If the SD folder is not mounted null is returned.
	  * 
	  * @param app			Application context.
	  * @param folder		The type of files directory to return. 
	  * 					May be null for the root of the app. data directory or 
	  * 					some folder value.
	  * 
	  * @return
	  */
	 public static File storage_getAppExternalStorageFolder(Application app, String folder){
		 File res=null;
		 if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			 res=app.getExternalFilesDir(folder);
		 }
		 return res;
	 }
	 
	 /**
	  * This function returns a File object pointing to the specified folder. If none is
	  * specified, root dir of the SD is returned.
	  * 
	  * Returns null if the media is not mounted.	  
	  * 
	  * @param folderType	The type of files directory to return. 
	  * 					May be null to get the root of the SD or 
	  * 					any of the Environment.DIRECTORY_ options.
      *
	  * @return
	  */
	 public static File storage_getExternalFolder(String folderType){
		 File res=null;
		 if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			  res=new File(Environment.getExternalStorageDirectory(),folderType);
		 }
		 return res;
	 }
	 	 
	 /**
	  * This function returns a File object pointing to a public folder of the the specified 
	  * type in the external drive.
	  * 
	  * User media is saved here. Be carefull.
	  * 	  
	  * Returns null if the media is not mounted.
	  * 
	  * @param folderType	The type of files directory to return. Use one of the avaliable
	  * 					Environment.DIRECTORY_ values.
	  * @param folder		An specific folder in the public folder. May be null to get 
	  * 					the root of the public folder type.
	  * @param createFolder	Set to TRUE to create the folder if it does not exists.  
	  * 					
	  * @return
	  */
	 public static File storage_getExternalPublicFolder(String folderType, String folder, boolean createFolder){
		 File res=null;
		 if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			  res=new File(Environment.getExternalStoragePublicDirectory(folderType),folder);
			  
			  if(!res.exists()){
				  //We create the folder if is desired.
				  if(folder!=null && folder.length()>0 && createFolder){
					 if(!res.mkdir()){
						 res = null;
					 }
				  }else{
					  res = null;
				  }
			  }
		 }
		 return res;
	 }
	  
	 
    /**
     * Saves the content of the specified input stream into the outputFile.
     * 
     * @param is
     * @param outputFile
     * @param closeInput
     */
    public static void storage_saveInputData(InputStream is, File outputFile, boolean closeInput) throws Exception{
        try{
	    	OutputStream os = new FileOutputStream(outputFile);               
	        storage_copyStream(is, os, 1024);
	        os.close();
	        if(closeInput){
	        	is.close();
	        }
        }catch(Exception e){
        	if(LOG_ENABLE)
        		Log.e(TAG,"storage_saveInputData(): "+e.getMessage(),e);
        	
        	throw new Exception(TAG+"[storage_saveInputData()]: "+e.getMessage(),e);     
        }
    }
	 
	/**
	 * Gets the application internal storage path.
	 * 
	 * @param context
	 * @param file
	 * @return
	 */
	 public static File storage_getAppInternalStorageFilePath(Context context, String file){
		 String filePath = context.getFilesDir().getAbsolutePath();//returns current directory.
		 if(file==null){
			 return new File(filePath);
		 }else{
			 return new File(filePath, file);
		 }
	 }
	 
	/**
	 * Reads a text file returning its contents as an string.
	 *  
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String storage_getTextFileContentAsString(String file, String encoding) throws Exception{
		String res;
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			
			String read;
			StringBuilder builder = new StringBuilder("");
			
			while((read = bufferedReader.readLine()) != null){
				builder.append(read + "\n");
			}
			
			res = builder.toString();			
			bufferedReader.close();
			
		}catch(Exception e){
        	if(LOG_ENABLE)
        		Log.e(TAG,"storage_getTextFileContentAsString(): "+e.getMessage(),e);
        	
        	throw new Exception(TAG+"[storage_getTextFileContentAsString()]: "+e.getMessage(),e);     
        }
		
		return res;
	}
	
	/**
	 * This method copies the input to the specified output.
	 * 
	 * @param is	Input source
	 * @param os	Output destiny
	 * @return		Total bytes read
	 */
    public static int storage_copyStream(InputStream is, OutputStream os, int buffer_size) throws IOException{
    	int readbytes=0;
    	
        if(buffer_size<=0){
        	buffer_size=1024;
        }
        
        try{
            byte[] bytes=new byte[buffer_size];
            for(;;){
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
              readbytes+=count;
            }
        }catch(Exception e){
        	throw new IOException("Failed to save data ("+e.getMessage()+").",e);
        }
        
        return readbytes;
    }
		
	/**
	 * Saves data to the application internal folder.
	 * 
	 * @param context
	 * @param fileName
	 * @param data
	 * @throws Exception
	 */
	public static synchronized void storage_storeDataInInternalStorage(Context context, String fileName, byte[] data) throws Exception{
		 try {
			 /* We have to use the openFileOutput()-method
		      * the ActivityContext provides, to
		      * protect your file from others and
		      * This is done for security-reasons.
		      * We chose MODE_WORLD_READABLE, because
		      *  we have nothing to hide in our file */             
		      FileOutputStream fOut = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
			   
		      // Write the string to the file
		      fOut.write(data);
	
		      /* ensure that everything is really written out and close */
		      fOut.flush();
		      fOut.close();
		      
		 } catch (Exception e) {
			  throw new Exception("Error saving data to '" + fileName + "' (internal storage) : "+ e.getMessage(),e);
		 } 
	 }
	 
	/**
	 * Reads data from the application internal storage data folder.
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static byte[] storage_readDataFromInternalStorage(Context context, String fileName) throws Exception{
		FileInputStream fIn;
		
		try {
			fIn = context.openFileInput(fileName);
			
			byte[] buffer = new byte[fIn.available()];
			 
			fIn.read(buffer);
			fIn.close();
			 
			return buffer;
			
		} catch (Exception e) {
			throw new Exception("Error reading data '" + fileName + "' (internal storage) : "+ e.getMessage(),e);
		}
	 }
	
	/**
	 * Deletes a file from the application internal storage private folder.
	 * 
	 * @param context
	 * @param fileName
	 * @throws Exception
	 */
	public static void storage_deleteDataFromInternalStorage(Context context, String fileName) throws Exception{
			
		 try {
			context.deleteFile(fileName);				
		 } catch (Exception e) {
			throw new Exception("Error deleting data '" + fileName + "' (internal storage) : "+ e.getMessage(),e);
		 }
	 }
	 
	 /**
	  * Checks if a file exists in the application internal private data folder.
	  * 
	  * @param context
	  * @param fileName
	  * @return
	  */
	 public static boolean storage_checkIfFileExistsInInternalStorage(Context context, String fileName){
		
			try {
				context.openFileInput(fileName);
				
				return true;
			} catch (FileNotFoundException e) {
				return false;
			}
	 }
	
	/**
	 * Saves an URL link into the external SD card of the Device.
	 * 
	 * @param dataUrl
	 * @param fileName
	 * @param bufferSize
	 * @return
	 * @throws Exception
	 */
	public static String storage_saveUrlToExternal(String dataUrl, String fileName, int bufferSize) throws Exception{
		String res;
		
		InputStream input = null;
		OutputStream output = null;
		
		try {		
			URL url = new URL (dataUrl);
			input = url.openStream();
			
			//The sdcard directory '/sdcard' can be used directly but is
			//better and safety if we use "getExternalStorageDirectory()" :)
		    File storagePath = Environment.getExternalStorageDirectory();
		    File f = new File(storagePath, fileName);
		    output = new FileOutputStream (f);
		    
	        byte[] buffer = new byte[bufferSize];
	        int bytesRead = 0;
	        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
	            output.write(buffer, 0, bytesRead);
	        }
	        
	        res = f.getAbsolutePath();
		}catch(Exception e){
			throw new Exception("Error saving data in external storage : "+ e.getMessage(),e);
	    } finally {
	    	try{
	    		input.close();
	    		output.close();
	    	}catch(Exception e){}
	    }
		 
		return res;
	}
	
	/**
	 * Saves the Url link into the internal memory card in the application private zone.
	 * 
	 * @param context
	 * @param dataUrl
	 * @param bufferSize
	 * @return
	 * @throws Exception
	 */
	public static String storage_saveUrlToInternal(Context context, String dataUrl, int bufferSize) throws Exception{
		String res = null;
		
		try{
			String filename = String.valueOf(dataUrl.hashCode()+
		    		dataUrl.substring(dataUrl.lastIndexOf("."),dataUrl.length()) );
			
			if(!storage_checkIfFileExistsInInternalStorage(context, filename)){
				
				URL url = new URL (dataUrl);
				InputStream input = url.openStream();
				
			    ByteArrayOutputStream output = new ByteArrayOutputStream ();
			    try {
			        byte[] buffer = new byte[bufferSize];
			        int bytesRead = 0;
			        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
			            output.write(buffer, 0, bytesRead);
			        }
			    } finally {
			        output.close();
			    }
			    
			    byte[] data = output.toByteArray();
			    
			    storage_storeDataInInternalStorage(context, filename, data);
			}
		    
		    res = filename;
		} catch (Exception e) {
			throw new Exception("Error saving data in internal storage : "+ e.getMessage(),e);
		}		
		
		return res;
	}
	
	/**
	 * 
	 * @param context
	 * @param dataUrl
	 * @param bufferSize
	 * @param watermark
	 * @param watermark_underlined
	 * @return
	 * @throws Exception
	 */
	public static String storage_saveImageUrlToInternalAddingWatermark(Context context, String dataUrl, int bufferSize, String watermark, boolean watermark_underlined) throws Exception{
		String res = null;
		
		try{
			String filename = String.valueOf(dataUrl.hashCode()+
		    		dataUrl.substring(dataUrl.lastIndexOf("."),dataUrl.length()) );
			
			if(!storage_checkIfFileExistsInInternalStorage(context, filename)){
				
				URL url = new URL (dataUrl);
				InputStream input = url.openStream();
				
			    ByteArrayOutputStream output = new ByteArrayOutputStream ();
			    try {
			        byte[] buffer = new byte[bufferSize];
			        int bytesRead = 0;
			        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
			            output.write(buffer, 0, bytesRead);
			        }
			    } finally {
			        output.close();
			    }
			    
			    byte[] data = output.toByteArray();
			    
			    //Add watermark --------------------------------------------------------------------------------
			    try{
				    Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
				    Bitmap imageW = graphics_addWaterMarkToImage(image, watermark, new Point(5,image.getHeight()-5), 
				    		24, watermark_underlined);
				    if(imageW!=null){
				    	//Get modified image bytes to save it
					    ByteArrayOutputStream stream = new ByteArrayOutputStream();
					    imageW.compress(Bitmap.CompressFormat.PNG, 100, stream);
					    data = stream.toByteArray();
					    
					    stream.close();
				    }
			    }catch(Exception e){} //Nothing needs to be done.
			    //----------------------------------------------------------------------------------------------
			    			    
			    storage_storeDataInInternalStorage(context, filename, data);
			}
		    
		    res = filename;
		} catch (Exception e) {
			throw new Exception("Error saving data in internal storage : "+ e.getMessage(),e);
		}		
		
		return res;
	}
	
	/**
	 * Return a temporal empty file.
	 * 
	 * @param filePrefix
	 * @param fileSuffix
	 * @param outputDirectory
	 * @return
	 * @throws java.io.IOException
	 */
	public static File storage_createUniqueFileName(String filePrefix, String fileSuffix, File outputDirectory) throws IOException {
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
	    String fileName = filePrefix + timeStamp;
	    File filePath = File.createTempFile(fileName, fileSuffix, outputDirectory);
	    if(filePath.exists()){
	    	return filePath;
	    }else{
	    	return null;
	    }	    
	}
	
	
	//--------------- PREFS ---------------------------------------------------------------------------
	
	/**
	 * Saves a prefrence. You can remove it setting its value to null.
	 * 
	 * @param preferences
	 * @param prefName
	 * @param key
	 * @param valueType
	 * @param value
	 * @return
	 */
	public static boolean prefs_savePreference(SharedPreferences preferences, String prefName, String key, Class<?> valueType, Object value){
		boolean res = false;
		
		if(preferences==null)
			return res;
		
		if(value==null){
			preferences.edit().remove(key).commit();
		}else{
			if(valueType == Long.class){
				res = preferences.edit().putLong(key, (Long)value).commit();
			}else if(valueType == Boolean.class){
				res = preferences.edit().putBoolean(key, (Boolean)value).commit();
			}else if(valueType == Float.class){
				res = preferences.edit().putFloat(key, (Float)value).commit();
			}else if (valueType == Double.class) {
				res = preferences.edit().putLong(key, Double.doubleToRawLongBits(((Double)value))).commit();		        
			}else if(valueType == Integer.class){
				res = preferences.edit().putInt(key, (Integer)value).commit();
			}else if(valueType == String.class){
				res = preferences.edit().putString(key, (String)value).commit();
			}else if(valueType == Set.class){
				if(((Set<String>)value).size()==0){
					preferences.edit().remove(key).commit();
				}else{
					//res = prefs.edit().putStringSet(key, (Set<String>)value).commit(); //Only from 11 API level.
					res = saveSetListAsCommaSeparatedString(preferences, prefName, key, (Set<String>)value);
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Saves a prefrence. You can remove it setting its value to null.
	 * 
	 * @param ctx
	 * @param prefName
	 * @param key
	 * @param valueType
	 * @param value
	 * @return
	 */
	public static boolean prefs_savePreference(Context ctx, String prefName, String key, Class<?> valueType, Object value){
		boolean res = false;
		
		SharedPreferences prefs = ctx.getSharedPreferences(
				prefName, Context.MODE_PRIVATE);
		
		if(prefs==null) //In case of a Service, for example, we take the default preferences. 
			prefs  = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		if(value==null){
			prefs.edit().remove(key).commit();
		}else{
			if(valueType == Long.class){
				res = prefs.edit().putLong(key, (Long)value).commit();
			}else if(valueType == Boolean.class){
				res = prefs.edit().putBoolean(key, (Boolean)value).commit();
			}else if (valueType == Double.class) {
				res = prefs.edit().putLong(key, Double.doubleToRawLongBits(((Double)value))).commit();
			}else if(valueType == Float.class){
				res = prefs.edit().putFloat(key, (Float)value).commit();
			}else if(valueType == Integer.class){
				res = prefs.edit().putInt(key, (Integer)value).commit();
			}else if(valueType == String.class){
				res = prefs.edit().putString(key, (String)value).commit();
			}else if(valueType == Set.class){
				if(((Set<String>)value).size()==0){
					prefs.edit().remove(key).commit();
				}else{
					//res = prefs.edit().putStringSet(key, (Set<String>)value).commit(); //Only from 11 API level.
					res = saveSetListAsCommaSeparatedString(ctx, prefName, key, (Set<String>)value);
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Gets a value from given key in the preferences.
	 * 
	 * @param preferences
	 * @param prefName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public static Object prefs_readPreference(SharedPreferences preferences, String prefName, String key, Class<?> valueType){
		
		if(preferences==null)
			return null;		
		
		if(valueType == Long.class){
			return preferences.getLong(key, Long.valueOf(-1));
		}else if(valueType == Boolean.class){
			return preferences.getBoolean(key, Boolean.valueOf(false));
		}else if (valueType == Double.class) {
			return Double.longBitsToDouble(preferences.getLong(key, Double.doubleToRawLongBits(-1)));
		}else if(valueType == Float.class){
			return preferences.getFloat(key, Float.valueOf(-1));
		}else if(valueType == Integer.class){
			return preferences.getInt(key, Integer.valueOf(-1));
		}else if(valueType == String.class){
			return preferences.getString(key, null);
		}else if(valueType == Set.class){
			//return prefs.getStringSet(key, null); //Only from 11 API level.
			return getSetListFromCommaSeparatedString(preferences, prefName, key);
		}		
		
		return null;
	}
	
	/**
	 * Gets a value from given key in the preferences.
	 * 
	 * @param ctx
	 * @param prefName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public static Object prefs_readPreference(Context ctx, String prefName, String key, Class<?> valueType){
		SharedPreferences prefs = ctx.getSharedPreferences(
				prefName, Context.MODE_PRIVATE);
		
		if(prefs==null) //In case of a Service, for example, we take the default preferences. 
			prefs  = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		
		if(valueType == Long.class){
			return prefs.getLong(key, Long.valueOf(-1));
		}else if(valueType == Boolean.class){
			return prefs.getBoolean(key, Boolean.valueOf(false));
		}else if (valueType == Double.class) {
			return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToRawLongBits(-1)));
		}else if(valueType == Float.class){
			return prefs.getFloat(key, Float.valueOf(-1));
		}else if(valueType == Integer.class){
			return prefs.getInt(key, Integer.valueOf(-1));
		}else if(valueType == String.class){
			return prefs.getString(key, null);
		}else if(valueType == Set.class){
			//return prefs.getStringSet(key, null); //Only from 11 API level.
			return getSetListFromCommaSeparatedString(ctx, prefName, key);
		}		
		
		return null;
	}
	
	/**
	 * Tells if the preferemce exists.
	 * 
	 * @param ctx
	 * @param prefName
	 * @param key
	 * @return
	 */
	public static Boolean prefs_existsPref(Context ctx, String prefName, String key){
		SharedPreferences prefs = ctx.getSharedPreferences(
				prefName, Context.MODE_PRIVATE);
		
		if(prefs==null) //In case of a Service, for example, we take the default preferences. 
			prefs  = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		if(prefs.contains(key)){
			return true;
		}else{
			return false;
		}		
	}
	
	/**
	 * Tells if the preferemce exists.
	 * 
	 * @param preferences
	 * @param prefName
	 * @param key
	 * @return
	 */
	public static Boolean prefs_existsPref(SharedPreferences preferences, String prefName, String key){
		if(preferences==null)
			return false;
		
		if(preferences.contains(key)){
			return true;
		}else{
			return false;
		}		
	}
		
	/*
	 * This method is for compatibility mode because SharedPreferences only works with
	 * Set data values starting at API Level 11.
	 *  
	 * @param context
	 * @param prefName
	 * @param key
	 * @return
	 */
	private static Set<String> getSetListFromCommaSeparatedString(Context context, String prefName, String key){
		Set<String> res = new HashSet<String>();
		String resString = (String)ToolBox.prefs_readPreference(context, prefName, key, String.class);
   	 	if(resString!=null && resString.length()>0){
	    	res = new HashSet<String>(Arrays.asList(resString.split(",")));
   	 	}
   	 	
		return res;
	}
	
	/*
	 * This method is for compatibility mode because SharedPreferences only works with
	 * Set data values starting at API Level 11.
	 *  
	 * @param preferences
	 * @param prefName
	 * @param key
	 * @return
	 */
	private static Set<String> getSetListFromCommaSeparatedString(SharedPreferences preferences, String prefName, String key){
		Set<String> res = new HashSet<String>();
		String resString = (String)ToolBox.prefs_readPreference(preferences, prefName, key, String.class);
   	 	if(resString!=null && resString.length()>0){
	    	res = new HashSet<String>(Arrays.asList(resString.split(",")));
   	 	}
   	 	
		return res;
	}
	
	/*
	 * This method is for compatibility mode because SharedPreferences only works with
	 * Set data values starting at API Level 11.
	 * 
	 * @param context
	 * @param prefName
	 * @param key
	 * @param value
	 * @return
	 */
	private static boolean saveSetListAsCommaSeparatedString(Context context, String prefName, String key, Set<String> value){
		boolean res = false;
		
		if(value!=null && value.size()>0){
			StringBuffer buff = new StringBuffer();
			int pos = 0;
			for(String s:value){
				if(pos>0){
					buff.append(",");
				}
				buff.append(s);
				pos++;
			}
			
			String data = buff.toString();
			if(data!=null && data.length()>0){
				res = prefs_savePreference(context, prefName, key, String.class, data);
			}
		}
		
		return res;
	}
	
	/*
	 * This method is for compatibility mode because SharedPreferences only works with
	 * Set data values starting at API Level 11.
	 * 
	 * @param preferences
	 * @param prefName
	 * @param key
	 * @param value
	 * @return
	 */
	private static boolean saveSetListAsCommaSeparatedString(SharedPreferences preferences, String prefName, String key, Set<String> value){
		boolean res = false;
		
		if(value!=null && value.size()>0){
			StringBuffer buff = new StringBuffer();
			int pos = 0;
			for(String s:value){
				if(pos>0){
					buff.append(",");
				}
				buff.append(s);
				pos++;
			}
			
			String data = buff.toString();
			if(data!=null && data.length()>0){
				res = prefs_savePreference(preferences, prefName, key, String.class, data);
			}
		}
		
		return res;
	}
	
	//--------------- SHARE ---------------------------------------------------------------------------
	
	
	/**
	 * Allows to create a share intent and it can be launched.
	 * 
	 * @param context
	 * @param type		Mime type.
	 * @param shareDialogTitle	The title of the share dialog
	 * @param appNamesToShareWith	You can filter the application you want to share with. Use "wha", "twitt", etc.
	 * @param title		The title of the share. Take in account that sometimes is not possible to add the title.
	 * @param data		The data, can be a file or a text.
	 * @param isBinaryData	If the share has a data file, set to TRUE otherwise FALSE.
	 */
	@SuppressLint("DefaultLocale")
	public static Intent share_newSharingIntent(Context context, String shareDialogTitle, String type, String[] appNamesToShareWith, String title, String data, boolean isBinaryData, boolean launch) {
	    
		Intent res = null;
		
	    Intent share = new Intent(Intent.ACTION_SEND);
	    share.setType(type);
	    
	    List<Intent> targetedShareIntents = new ArrayList<Intent>();
	    List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
	    if (!resInfo.isEmpty()){
	        for (ResolveInfo info : resInfo) {
	            Intent targetedShare = new Intent(Intent.ACTION_SEND);
	            targetedShare.setType(type);

	            if(title!=null){
	            	targetedShare.putExtra(Intent.EXTRA_SUBJECT, title);
	            	targetedShare.putExtra(Intent.EXTRA_TITLE, title);
	            	if(data!=null && !isBinaryData){
	            		targetedShare.putExtra(Intent.EXTRA_TEXT, data);
	            	}
            	}            	
            	if(data!=null && isBinaryData){
            		targetedShare.putExtra(Intent.EXTRA_TEXT, title);
            		targetedShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(data)));
            	}        
	            
	            if(appNamesToShareWith!=null && appNamesToShareWith.length>0){
	            	for(String appName: appNamesToShareWith) {
	            		if (info.activityInfo.packageName.toLowerCase().contains(appName) || 
	                    info.activityInfo.name.toLowerCase().contains(appName)) {
	            			targetedShare.setPackage(info.activityInfo.packageName);	                
	            			targetedShareIntents.add(targetedShare);
	            		}
	            	}
	            }else{
	            	targetedShare.setPackage(info.activityInfo.packageName);	                
	                targetedShareIntents.add(targetedShare);
	            }
	        }

	        Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), shareDialogTitle);
	        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
	        
	        res = chooserIntent;
	        
	        if(launch){
	        	context.startActivity(chooserIntent);
	        }
	    }
	    
	    return res;
	}
	
	
	//--------------- IO RELATED -----------------------------------------------------------------------
	 
	 /**
	   * Reads the data of an input stream to a String.
	   * 
	   * @param is
	   * @return
	   * @throws java.io.IOException
	   */
	  public static String io_convertStreamToString(InputStream is) throws IOException {
		  	/*
			 * To convert the InputStream to String we use the
			 * BufferedReader.readLine() method. We iterate until the BufferedReader
			 * return null which means there's no more data to read. Each line will
			 * appended to a StringBuilder and returned as String.
			 */
			if (is != null) {
				StringBuilder sb = new StringBuilder();
				String line;
			
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
					while ((line = reader.readLine()) != null) {
						sb.append(line).append("\n");
					}
				} finally {
					is.close();
				}
				return sb.toString();
			} else {
				return "";
			}
	  }  
	 
	 /**
	  * Reads the byte data from a input stream.
	  * 
	  * @param input
	  * @return
	  * @throws java.io.IOException
	  */
	 public static byte[] io_inputStreamToByteArray(InputStream input) throws IOException{
		 byte[] res = null;
		 
		 try{
			 // this dynamically extends to take the bytes you read
			 ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		
			 // this is storage overwritten on each iteration with bytes
			 int bufferSize = 1024;
			 byte[] buffer = new byte[bufferSize];
		
			 // we need to know how may bytes were read to write them to the byteBuffer
			 int len = 0;
			 while (input.available()>0 && (len = input.read(buffer)) != -1) {
			   byteBuffer.write(buffer, 0, len);
			 }
		
			 byteBuffer.flush();
			 res = byteBuffer.toByteArray();
			 
			 byteBuffer.close();
		 }catch(Exception e){
			 throw new IOException("Failed to read byte data from the stream ("+e.getMessage()+").",e);
		 } 
		 
		 return res;
	 }
	 
	 /**
	  * Deletes the specified folder content.
	  * 
	  * @param path	The desired folder.
	  * @throws IOException
	  */
	 public static void io_deleteFolderContent(String path) throws IOException{
		 Runtime.getRuntime().exec(String.format("rm -rf '%s'", path));
	 }
	 
	
	 //--------------- MARKET ---------------------------------------------------------------------------- 
	 
	 /**
	  * Prepares an intent for the Android Market application. In an
	  * error occurs a url is used.
	  * 
	  * @param appName
	  * @return
	  */
	 public static Intent market_getMarketAppIntent(String appName){
		Intent i = new Intent(Intent.ACTION_VIEW);
		try {
			i.setData(Uri.parse("market://details?id="+appName));
		} catch (android.content.ActivityNotFoundException anfe) {
			i.setData(Uri.parse("http://play.google.com/store/apps/details?id="+appName));
		}
		return i;
	}
	 
	//--------------- WEBVIEW ------------------------------------------------------------------------------- 
	
	/**
	 * Get a cookie value for a cookie name and site.
	 * 
	 * @param siteName	The site where the cookie is in.
	 * @param cookieName	The cookie mame.
	 * @return The cookie value or null if there is no cookie.
	 */
	public static String webview_getCookie(String siteName, String cookieName) {
		String cookieValue = null;
		
		if(siteName!=null && siteName.length()>0){
			CookieManager cookieManager = CookieManager.getInstance();
			String cookiesRAW = cookieManager.getCookie(siteName);
			if(cookiesRAW!=null && cookiesRAW.length()>0) {
				String[] cookies = cookiesRAW.split(",");
				if(cookieName!=null && cookieName.length()>0){
					for(String ck: cookies){
						if(ck.contains(cookieName)){
							String[] cookieKV = ck.split("=");
							cookieValue = cookieKV[1];
							break;
						}
					}
				}
			}
		}		
		
		return cookieValue;
	}
	
	/**
	 * Erases any cookie of any webview of an application.
	 * 
	 * @param callbackOk	You can provide a runnable. This will be executed
	 * 						if cookies are successfully deleted. Can be null.
	 * @param callbackError	You can provide a runnable. This will be executed
	 * 						if cookies are not successfully deleted. Can be
	 * 						null.
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void webview_destroyAllCookies(final Runnable callbackOk, final Runnable callbackError) {
		CookieManager cookieManager = CookieManager.getInstance();
		
		if(device_hasAPILevel(ApiLevel.LEVEL_21)) {
			cookieManager.removeAllCookies(new ValueCallback<Boolean>() {			
				@Override
				public void onReceiveValue(Boolean value) {
					if(LOG_ENABLE)
						Log.i(TAG, "Webview cookies deleted? " + value);
					if(value){
						if(callbackOk!=null)
							callbackOk.run();
					}else{
						if(callbackError!=null)
							callbackError.run();
					}
				}
			});
		}else{
			cookieManager.removeAllCookie();
		}
    }
	
	/**
	 * Erases any session cookie of any webview of an application.
	 * 
	 * @param callbackOk	You can provide a runnable. This will be executed
	 * 						if session cookies are successfully deleted. Can be null.
	 * @param callbackError	You can provide a runnable. This will be executed
	 * 						if session cookies are not successfully deleted. Can be
	 * 						null.
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void webview_destroyAllSessionCookies(final Runnable callbackOk, final Runnable callbackError) {
		CookieManager cookieManager = CookieManager.getInstance();
		
		if(device_hasAPILevel(ApiLevel.LEVEL_21)) {
			cookieManager.removeSessionCookies(new ValueCallback<Boolean>() {			
				@Override
				public void onReceiveValue(Boolean value) {
					if(LOG_ENABLE)
						Log.w(TAG, "Webview session cookies deleted? " + value);
					if(value){
						if(callbackOk!=null)
							callbackOk.run();
					}else{
						if(callbackError!=null)
							callbackError.run();
					}
				}
			});
		}else{
			cookieManager.removeSessionCookie();
		}
    }	
	
	/** 
	 * When using HTTPS, if accessing to resources not under HTTPS, Android by default
	 * denies them. These are the options for mixed-mode.
	 * <br><br>
	 * <ul>
	 * <li>ALLOW: In this mode, the WebView will allow a secure origin to load content from any 
	 * other origin, even if that origin is insecure. This is the least secure mode of 
	 * operation for the WebView, and where possible apps should not set this mode.</li>
	 * <li>ALLOW_ALL: In this mode, the WebView will allow a secure origin to load content from 
	 * any other origin, even if that origin is insecure. This is the least secure mode of 
	 * operation for the WebView, and where possible apps should not set this mode.</li>
	 * <li>DISALLOW: Mised content is not allowed.</li>
	 * <ul>
	 */	
	public static enum WEBVIEW_MIXED_MODE_ALLOWANCE {ALLOW, ALLOW_ALL, DISALLOW};
	
	/**
	 * Applies some optimizations and adjustements to the webview.
	 * 
	 * @param myWebview
	 * @param sslMixedMode	When using HTTPS, if accessing to resources not under HTTPS, Android by default
	 * denies them. Choose an option to tell Android what to do, see {@link WEBVIEW_MIXED_MODE_ALLOWANCE}
	 * @param adjustContentToDeviceScreen	Set to TRUE to tell the webview to adjust the content to the screen.
	 * @param useViewportHTMLTag	Set to TRUE to tell the webview to use the HTML viewport TAG.
	 * @param userAgentSuffix Optional. If set, user-agent is modified adding at the end the specified text.
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void webview_applyOptimizations(WebView myWebview, WEBVIEW_MIXED_MODE_ALLOWANCE sslMixedMode, boolean adjustContentToDeviceScreen, boolean useViewportHTMLTag, String userAgentSuffix, boolean javaScriptCanOpenWindowsAutomatically) {
		
		if(!ToolBox.device_hasAPILevel(ApiLevel.LEVEL_18)){
			myWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        }
		
        if (ToolBox.device_hasAPILevel(ApiLevel.LEVEL_19)) {
            // Chromium, enable hardware acceleration. Chromium handles well 3D
        	myWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // Older Android version, disable hardware acceleration
        	myWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        
        //FIX. This avoids possible flickering when loading a page under version minor to 
        //JELLY BEAN or in KITKAT.
        myWebview.setBackgroundColor(Color.argb(1, 0, 0, 0));
        
        if(ToolBox.device_hasAPILevel(ToolBox.ApiLevel.LEVEL_21)) {
	        //When having a address with HTTPS, we have to decide what to do with
	        //content not in HTTPS (mixed-mode)
	        switch (sslMixedMode) {
				case DISALLOW:
					myWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
					break;
				case ALLOW:
					myWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
					break;
				case ALLOW_ALL:
					myWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
					break;
			}
        }
        
        //See:
        //	http://developer.android.com/reference/android/webkit/WebSettings.html#setAllowContentAccess(boolean)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
        	myWebview.getSettings().setAllowContentAccess(true);
        }  
        
        //Makes the content to be adjusted to the screen
        myWebview.getSettings().setLoadWithOverviewMode(adjustContentToDeviceScreen);
        
        //Makes the webview to use the viewport defined tag in 
        //the HTML instead default wide viewport
        myWebview.getSettings().setUseWideViewPort(useViewportHTMLTag);
        
        //JS will be able to open new windows without asking
        myWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(javaScriptCanOpenWindowsAutomatically);
        
        //This will add application info to user agent.
        //(We could use this also to determine if web is running 
        // inside an Android application)
        if(userAgentSuffix!=null && userAgentSuffix.length()>0) {
        myWebview.getSettings().setUserAgentString(
        		myWebview.getSettings().getUserAgentString() 
    		    + " "
    		    + userAgentSuffix
    		);
        }
	}
	
	/**
	 * Established a native Android javascript interface for the web side of
	 * the loaded pages by the webview.<br><br>
	 * 
	 * Tips: From the web side, you can check if the web is loaded within the
	 * Android app by looking in web javascript:
     * <pre>		
     * 	if("jsInterfaceName" in window){....} 
     *  	...where "jsInterfaceName" is the native app JS object implementation.
	 * </pre>
	 * Also t6o execute a native Android method within javascript using your 
	 * specified interface just call:
	 * <pre>
	 * 	jsInterfaceName.some_native_method(some_args)
	 * </pre>
	 * @param myWebview	The webview
	 * @param jsInterface	An instance of {@link WebviewJavascriptInterface}. This will expose 
	 * 						Android native methods to the webview loaded pages.
	 * @param jsInterfaceName	The desired name for the Android native interface. This is used
	 * 							in the web side to access the native methods.
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public static void webview_addJavascriptInterface(WebView myWebview, WebviewJavascriptInterface jsInterface, String jsInterfaceName) {
		
        //This enables the JS in the webviews's content.
        myWebview.getSettings().setJavaScriptEnabled(true);
        //This enables to expose to webviews's web some native android app methods.
        myWebview.addJavascriptInterface(jsInterface, jsInterfaceName);
	}
	
	/**
	 * This method runs the specified javascript using the KitKat asynchronous
	 * mode if is available (Android 19+).
	 * 
	 * @param webView			The webview that runs the Javascript
	 * @param javascript		The javascript to run
	 * @param runInWebviewPOST	If set to TRUE, the javascript will be run in the 
	 * 							UI thread of the webview.
	 * @param callback			The callback to call after javascript runs.
	 */
	@SuppressLint("NewApi")
	public static void webview_runJavascript(final WebView webView, final String javascript, boolean runInWebviewUIThread, final ValueCallback<String> callback) {
		if(LOG_ENABLE){
			Log.d("ToolBox","Javascript to run: ["+javascript+"].");
		}
		
		if(runInWebviewUIThread) {
			webView.post(new Runnable() {				
				@Override
				public void run() {
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			    		webView.evaluateJavascript(javascript, callback);
			        } else {
			        	webView.loadUrl(javascript);
			        }					
				}
			});
		}else{		
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
				webView.evaluateJavascript(javascript, callback);
			} else {
				webView.loadUrl(javascript);
			}			
		}
    }
	 
	//--------------- (PENDING) INTENTS ---------------------------------------------------------------------
	
	/**
	 * Creates a new intent for a Broadcast.
	 *  
	 * @param context
	 * @param action	Required.	
	 * @param bundle	Any additional information to get in the service.
	 * @return
	 */
	public static Intent intent_toBroadcast(Context context, String action, Bundle bundle) {
		
		Intent intent = new Intent();
		if(action!=null)
			intent.setAction(action);
		if(bundle!=null)
			intent.putExtras(bundle);
		
		return intent;
	}
	
	/**
	 * Creates a new intent for a Service.
	 * 
	 * @param context
	 * @param serviceClass	Required.
	 * @param action		Optional.
	 * @param bundle		Any additional information to get in the service.
	 * @return
	 */
	public static Intent intent_toService(Context context, @SuppressWarnings("rawtypes") Class serviceClass, String action, Bundle bundle) {
		
		Intent intent = new Intent(context, serviceClass);
		if(action!=null)
			intent.setAction(action);
		if(bundle!=null)
			intent.putExtras(bundle);
		
		return intent;
	}
	
	/**
	 * Creates a new intent from its package name to open the application. 
	 * 
	 * Note:<br><br>
	 * 
	 * Remember that in the application there must be an activity with an intent
	 * filter that has at least:<br><br>
	 * 
	 * 		<ul>
	 * 			<li>The action name "<b>android.intent.action.MAIN</b>"</li>
	 * 			<li>The category name "<b>android.intent.category.LAUNCHER</b>". 
	 * 				(<i>This creates also a launcher icon</i>).</li>
	 * 		</ul>
	 * 
	 * <b>If the application does not have to have an icon</b>, use as category 
	 * "<b>android.intent.category.INFO</b>" instead of "android.intent.category.LAUNCHER". 
	 * Doing this, the application will have internally a launcher activity but the launcher 
	 * icon will not be created.
	 * 
	 * @param context
	 * @param appPackage	Required. The application, package, to launch.
	 * @param bundle		Any additional information to get in the service.
	 * @return	The intent or null if the application does not exists or no launcher 
	 * 			intent is found in the application.
	 */
	public static Intent intent_openApp(Context context, String appPackage, Bundle bundle) {
		
		PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(appPackage);
        if(intent!=null) {
        	intent.addCategory(Intent.CATEGORY_LAUNCHER);
        	if(bundle!=null){
        		intent.putExtras(bundle);
        	}
        }else{
        	return null;
        }
        
		return intent;
	}
	
	/**
	 * Creates a new intent to open an URL in the default browser.
	 * 
	 * @param url	The URL
	 * @return
	 */
	public static Intent intent_openURL(String url) {
		Intent intent = null;
		
		if(url!=null){
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
		}
		
		return intent;
	}
	
	/**
	 * Creates a new intent to send an e-mail.
	 * 
	 * @param address
	 * @param subject
	 * @param bodyText
	 * @return
	 */
	public static Intent intent_sendMail(String address, String subject, String bodyText) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);   
		emailIntent.setType("plain/text");   
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
		emailIntent .putExtra(Intent.EXTRA_TEXT, bodyText);
		
		return emailIntent;
	}
	
	/**
	 * Creates a new intent to call to a phone number.
	 * 
	 * @param phone
	 * @return
	 */
	public static Intent intent_callPhoneNumber(String phone) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+phone));
		
		return callIntent;		 
	}
	
	/**
	 * Extracts the parameters of an intent open application URI. This kind of URL looks:
	 * 
	 * market://details?id=com.colectivosvip.orange.porserorange&url=serdeorange://serdeorange.orange.es?param1=https%3A%2F%2Fprogramaserdeorange.orange.es%2Fprivada%2Foffer-details.action%3Fo%3D403
	 * 
	 * @param intent	The intent to extract its URI parameters from.
	 * @return	A Map with the parameters and values.
	 * @throws Exception
	 */
	public static Map<String, List<String>> intent_getURIParameters(Intent intent) throws Exception {
		Uri uri = intent.getData();
		return intent_getURIParameters(uri);        
	}
	
	/**
	 * Extracts the parameters of an intent open application URI. This kind of URL looks like:
	 * <br><br>
	 * market://details?id=com.colectivosvip.orange.porserorange&url=serdeorange://serdeorange.orange.es?param1=https%3A%2F%2Fprogramaserdeorange.orange.es%2Fprivada%2Foffer-details.action%3Fo%3D403
	 * 
	 * @param uri	The URI to extract parameters from.
	 * @return	A Map with the parameters and values.
	 * @throws Exception
	 */
	public static Map<String, List<String>> intent_getURIParameters(Uri uri) throws Exception {
		Map<String, List<String>> query_pairs = null;
		
        if(uri!=null) {
        	query_pairs = new LinkedHashMap<String, List<String>>();
	    	final String[] pairs = uri.getEncodedQuery().split("&");
	    	for (String pair : pairs) {
	    		final int idx = pair.indexOf("=");
	    		final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
	    		if (!query_pairs.containsKey(key)) {
	    			query_pairs.put(key, new LinkedList<String>());
	    		}
	    		final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
	    		query_pairs.get(key).add(value);
	    	}
        }
		
    	return query_pairs;
	}
	
	/**
	 * Generates an application link URL that when loaded from a mobile, makes the application 
	 * to start if is installed, otherwise, opens the market so user can install the app. 
	 * This method also allows to stablish some parameters to this URL.<br><br>
	 * 
	 * The URL should looks like:
	 * <br>
	 * market://details?id=com.application.package&url=protocol://specific.domain.app?param1=param1value&param2=param2value
	 * <br><br>
	 * See: <a href="https://developer.android.com/training/app-links/index.html">Application links</a>
	 * <br><br>
	 * 
	 * <b>Note</b><br><br>
	 * To enable link handling verification for your app:
	 * <ul>
	 * 	<li>In your app manifest, set the launchMode to "singleTask" on the activity you want to handle these URLs.</li>
	 * 	<li>Configure the intent-filter of this activity setting the scheme and host to respond to.</li>
	 * 	<li>Set intent filter action to android.intent.action.VIEW and add categories android.intent.category.DEFAULT, android.intent.category.BROWSABLE and android.intent.category.VIEW</li>
	 * </ul> 
	 * 
	 * See the following manifest code snippet:<br><br>
	 * 
	 * <pre>
	 * {@code
	 * 	<intent-filter>
	 *      <action android:name="android.intent.action.VIEW" />
	 *      <category android:name="android.intent.category.DEFAULT" />
	 *      <category android:name="android.intent.category.BROWSABLE" />
	 *      <category android:name="android.intent.category.VIEW" />
	 *          
	 *      <data android:scheme="http" android:host="host.com" />
	 *      <data android:scheme="https" android:host="host.com" />
	 *      <data android:host="host.com"
     *            android:pathPattern=".*"                    
     *            android:scheme="appscheme">
     *      </data>
	 *      
	 *  </intent-filter>
	 * }</pre>
	 * 
	 * Also, handle these URLs in both methods, "onCreate" and "onNewIntent" events of 
	 * the activity. You can get parameters with {@link ToolBox#intent_getURIParameters(Intent)}.
	 * <br><br>
	 * 
	 * @param appPkgName	The application package.
	 * @param appProtocol	The application protocol.
	 * @param appURL		The application specific URL
	 * @param params		Optional. Some parameters to add to the URL.
	 * @return
	 */
	public static String intent_generateOpenInstallURL(String appPkgName, String appScheme, String appHost, Map<String,String> params) throws Exception {
		String url = "market://details?id=%s&url=%s://%s";
		
		String paramsString = "";
		if(params!=null && params.size()>0){
			paramsString = "?";
			Set<String> keys = params.keySet();
			for(String k:keys){
				if(paramsString.length()>=1)
					paramsString+="&";
					
				paramsString+=k+"="+ URLEncoder.encode(params.get(k), "UTF-8");
			}
		}
		
		url = String.format(url, appPkgName, appScheme, appHost);		
		return (url+paramsString);
	}
	
	
	/**
	 * Returns TRUE if the intent has the specified FLAG, otherwise FALSE.
	 * 
	 * @param intent
	 * @param flag
	 * @return
	 */
	public static boolean intent_hasFlag(Intent intent, int flag){
		if ((intent.getFlags() & flag) != 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Creates a new intent to open the browser and navigate to some address.
	 * <br><br>
	 * The "destiny" parameter can be an address in which all spaces must be replaced
	 * by the "+" character or some coordinates following the format "XX.YYYYYY,XX.YYYYYY"
	 * 
	 * @param destiny
	 * @return
	 */
	public static Intent intent_routeTo(String destiny){
		return new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+destiny));
	}
	
	/**
	 * Checks if there is a launcher intent for the specified action
	 * for the application package name.
	 * 
	 * @param context
	 * @param action		Action that is checked.
	 * @param packageName	Application package to launch.
	 * @return TRUE if the an intent for the given action for the specified 
	 * 		   application package exists, otherwise FALSE.
	 */
	public static boolean intent_checkForAppLauncherAction(Context context, String action, String packageName) {
		boolean res = false;
		
		PackageManager manager = context.getPackageManager();
        Intent i = new Intent();
        i.setAction(action);
        
        //I check if an activity is found with such action
        List<ResolveInfo> actFound = manager.queryIntentActivities(i, PackageManager.GET_RESOLVED_FILTER);
        if(actFound.size()>0){
        	for(ResolveInfo info:actFound){
        		if(info.activityInfo.packageName.equals(packageName)){
        			res = true;
        			break;
        		}
        	}        	
        }
        
        return res;
	}
	
	
	/**
	 * Gets a pending intent.
	 * 
	 * @param context	
	 * @param intent
	 * @param flags		If not set, default {@link PendingIntent#FLAG_UPDATE_CURRENT}.
	 * @return
	 */
	public static PendingIntent pendingIntent_getForIntent(Context context, Intent intent, int flags) {
		
		if(random==null)
			random = new Random();		
		if(flags==0)
			flags = PendingIntent.FLAG_UPDATE_CURRENT;
		
		return PendingIntent.getActivity(context, random.nextInt(), intent, flags);		
	}
	
	
	//--------------- SCREEN ----------------------------------------------------------------------------
	 
	/**
	 * Sets LinearLayout parameters using density points.
	 * 
	 * @param context
	 * @param container
	 * @param viewId
	 * @param height
	 * @param width
	 * @param weight
	 */
	public static void screen_setLinearLayoutParams(Context context, View container, int viewId, float height, float width, float weight){
		
		container.findViewById(viewId).setLayoutParams(
				new LinearLayout.LayoutParams(
						screen_getDensityPoints(context, width),
		                screen_getDensityPoints(context, height), weight)
				);
	}
	
	/**
	 * Gets the current orientation.
	 * 
	 * @param context
	 * @return	{@link es.javocsoft.android.lib.toolbox.ToolBox.SCREEN_ORIENTATION}
	 */
	@SuppressWarnings("deprecation")
	public static SCREEN_ORIENTATION screen_getOrientation(Context context){
		SCREEN_ORIENTATION res = SCREEN_ORIENTATION.PORTRAIT;
		
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    //int orientation = Configuration.ORIENTATION_UNDEFINED;
	    if(display.getWidth()==display.getHeight()){
	    	res = SCREEN_ORIENTATION.SQUARE;
	        //orientation = Configuration.ORIENTATION_SQUARE;
	    } else{ 
	        if(display.getWidth() < display.getHeight()){
	        	res = SCREEN_ORIENTATION.PORTRAIT;
	            //orientation = Configuration.ORIENTATION_PORTRAIT;
	        }else { 
	        	res = SCREEN_ORIENTATION.LANDSCAPE;
	            //orientation = Configuration.ORIENTATION_LANDSCAPE;
	        }
	    }
	    return res;
	}
	
	/**
	 * Gets the density points for a given size.
	 * 
	 * @param context
	 * @param size
	 * @return
	 */
	public static int screen_getDensityPoints(Context context, float size){
			float d = context.getResources().getDisplayMetrics().density;
			return (int)(size * d); // margin in pixels
	}
	 
	 
	//--------------- WEB RELATED -----------------------------------------------------------------------
	 
	 /**
	 * Checks if the url exists.
	 * 
	 * @param URLName
	 * @return
	 */
	public static boolean web_fileExists(String URLName){
	    try {
	      HttpURLConnection.setFollowRedirects(false);
	      // It may also needed
	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	      HttpURLConnection con =
	         (HttpURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    } catch (Exception e) {
	    	if(LOG_ENABLE)
	    		Log.d("ToolBox","Url does not exist ("+URLName+").");
	    	
	       return false;
	    }
	  }

	/**
	 * Enables http cache. (when using webview for example)
	 * 
	 * @param ctx
	 */
	public static void web_enableHttpResponseCache(Context ctx) {
		try {
			long httpCacheSize = HTTP_CACHE_SIZE;
			File httpCacheDir = new File(ctx.getCacheDir(), "http");
			Class.forName("android.net.http.HttpResponseCache")
					.getMethod("install", File.class, long.class)
					.invoke(null, httpCacheDir, httpCacheSize);
		} catch (Exception e) {
			if(LOG_ENABLE)
				Log.e(TAG,"Http Response cache could not be initialized ["+ e.getMessage() + "]", e);
		}
	}
 
 
     // Media Related -----------------------------------------------------------------------------------------------------------------------------
 	
	/**
	 * Corrects the orientation of a Bitmap. Orientation, depending of the device
	 * , is not correctly set in the EXIF data of the taken image when it is saved
	 * into disk.
	 * 
	 * Explanation:
	 * 	Camera orientation is not working ok (as is when capturing an image) because 
	 *  OEMs do not adhere to the standard. So, each company does this following their 
	 *  own way.
	 * 
	 * @param imagePath	path to the file
	 * @return
	 */
	public static Bitmap media_correctImageOrientation (String imagePath){
		Bitmap res = null;
		
		try {
	        File f = new File(imagePath);
	        ExifInterface exif = new ExifInterface(f.getPath());
	        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

	        int angle = 0;

	        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
	            angle = 90;
	        } 
	        else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
	            angle = 180;
	        } 
	        else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
	            angle = 270;
	        }

	        Matrix mat = new Matrix();
	        mat.postRotate(angle);
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 2;
	        
	        Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
	        res = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
	                bmp.getHeight(), mat, true);
	        
	    }catch(OutOfMemoryError e) {
	    	if(LOG_ENABLE)
        		Log.e(TAG,"media_correctImageOrientation() [OutOfMemory!]: "+ e.getMessage(),e);
	    }catch (Exception e) {
	    	if(LOG_ENABLE)
        		Log.e(TAG,"media_correctImageOrientation(): "+e.getMessage(),e);
	    }catch(Throwable e){
	    	if(LOG_ENABLE)
        		Log.e(TAG,"media_correctImageOrientation(): "+e.getMessage(),e);
	    }
	    
		
		return res;
	}
	
	/**
	 * Converts an image file from Assets folder to Base64 string.
	 *  
	 * @param pathToFile
	 * @return
	 */
	public static String media_getAssetImageAsB64(Context context, String pathToFile){
		String encodedImage = null;
		
		byte[] b = storage_readAssetResource(context, pathToFile);
		encodedImage = Base64.encodeToString(b, false);
		
		return encodedImage; 
	}
	
	
	/**
	 * Gets a Bitmap from a file in the asset folder.<br><br>
	 * 
	 * NOTE:<br>
	 * When decoding Bitmap we more often get memory overflow exceptions 
	 * if Image size is very big. This method of loading a bitmap avoids
	 * these kind of exceptions.
	 * 
	 * @param context
	 * @param fileName	The file to load
	 * @param reqWidth	The desired bitmap width
	 * @param reqHeight	The desired bitmap heigh
	 * @return	The {@link Bitmap} or {@code null} if failed to decode the file.
	 */
	public static Bitmap media_getBitmapFromAsset(Context context, String fileName, int reqWidth, int reqHeight){
		InputStream is = null;
	    Bitmap bitmap = null;
	    try {
	        is = context.getAssets().open(fileName);
	        
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;	        
	        BitmapFactory.decodeStream(is, null, options);
	        
	        // Calculate inSampleSize
	        options.inSampleSize = bitmapCalculateInSampleSize(options, reqWidth, reqHeight);
	        
	        // Decode bitmap with inSampleSize set
	        options.inJustDecodeBounds = false;
	        bitmap = BitmapFactory.decodeStream(is, null, options);
	        
	    } catch (final IOException e) {
	        bitmap = null;
	    } finally {
	        if (is != null) {
	            try {
	                is.close();
	            } catch (IOException ignored) {
	            }
	        }
	    }
	    return bitmap;
	}
	
	/**
	 * This method obtains a Bitmao object for the specified resource where
	 * resource can be a drawable resource Id, an assets/raw folder file name or 
	 * an URL.<br><br>
	 * 
	 * The order of load is:<br>
	 *		DRAWABLE -> ASSETS -> RAW -> URL
	 *	
	 * @param context
	 * @param resource	A drawable resource Id, an assets folder file name or 
	 * 					an URL
	 * @return
	 */
	public static Bitmap media_getBitmap(Context context, String resource) {
		Bitmap bResource = null;
		
		if(resource!=null) {
			//First we try to get the image from the drawable resources
			try{
				int imgResourceId = Integer.decode(resource);
				bResource = media_getBitmapFromResourceId(context, imgResourceId);
			}catch(Exception e){}
			
			//If no drawable found, we try with assets folder
			if(bResource==null){
				try{
					bResource = media_getBitmapFromAsset(context, resource);
				}catch(Exception e){}
			}	
			
			//If no drawable found, we try with raw folder
			if(bResource==null){
				try{
					bResource = media_getBitmapFromRaw(context, resource);
				}catch(Exception e){}
			}
					
			//If no raw found, we try download the image
			if(bResource==null && resource.startsWith("http")){
				try{
					bResource = media_getBitmapFromURL(context, resource);
				}catch(Exception e){}
			}
		}
		
		return bResource;
	}
	
	
	/**
	 * Gets an Image as drawable from the Raw folder.
	 *   
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Drawable media_getDrawableFromRaw(Context context, String fileName){
		Drawable image = null;
		InputStream in = null;
		int resId = context.getResources().getIdentifier(fileName,"raw", context.getPackageName());
		try{
			in = context.getResources().openRawResource(resId);
			image = Drawable.createFromStream(in, fileName);			
		}catch (Exception e){
			if(LOG_ENABLE)
				Log.e("TollBox_ERROR","media_getDrawableFromRaw() Error obtaining raw data: " + e.getMessage(),e);
			image = null;
		}finally {
			if (in != null) {
			 		try {
		            	in.close();
		            } catch (IOException ignored) {
		        }
		     }
		 }
		
		return image;
	 }
	 
	
	/**
	 * Gets an Image as bitmap from the Raw folder.
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	 public static Bitmap media_getBitmapFromRaw(Context context, String fileName){
		 
		 Bitmap image = null;
		 InputStream in = null;
		 BufferedInputStream inBuff = null;
		 
		 if(fileName!=null) {
			 int resId = context.getResources().getIdentifier(fileName,"raw", context.getPackageName());
			 try{
				 in = context.getResources().openRawResource(resId);			 
				 inBuff = new BufferedInputStream(in);
				 image = BitmapFactory.decodeStream(inBuff);
				 
			 }catch (Exception e){
				 image = null;
				 if(LOG_ENABLE)
					 Log.e(TAG,"media_getDrawableFromRaw() Error obtaining raw data: " + e.getMessage(),e);   
			     
			 }finally {
				if (in != null) {
					try{
	            		inBuff.close();
	            	}catch (IOException ignored) {}
			 		try{
		            	in.close();
		            }catch (IOException ignored) {}
			    }
			 }
		 }
		 
		 return image;
	 }
	
	
	/**
	 * Gets a Bitmap from a file in the asset folder.
	 * 
	 * @param context
	 * @param fileName
	 * @return	The {@link Bitmap} or {@code null} if failed to decode the file.
	 */
	public static Bitmap media_getBitmapFromAsset(Context context, String fileName){
		InputStream is = null;
	    Bitmap bitmap = null;
	    
	    if(fileName!=null) {
		    try {
		        is = context.getAssets().open(fileName);
		        bitmap = BitmapFactory.decodeStream(is);	   
		    } catch (final IOException e) {
		        bitmap = null;
		        if(LOG_ENABLE)
					 Log.e(TAG,"media_getBitmapFromAsset() Error obtaining image form asset: " + e.getMessage(),e);
		    } catch (final Exception e) {
		    	bitmap = null;
		    	if(LOG_ENABLE)
					 Log.e(TAG,"media_getBitmapFromAsset() Error obtaining image form asset: " + e.getMessage(),e);
		    } finally {
		        if (is != null) {
		            try {
		                is.close();
		            } catch (IOException ignored) {
		            }
		        }
		    }
	    }
	    
	    return bitmap;
	}
	
	private static int bitmapCalculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    
		// Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	        if (width > height) {
	            inSampleSize = Math.round((float)height / (float)reqHeight);
	        } else {
	            inSampleSize = Math.round((float)width / (float)reqWidth);
	        }
	    }
	    return inSampleSize;
	}
	
	/**
     * Decodes the specified image of the provided URL, scales it to reduce memory 
     * consumption and returning as a Bitmap object.
     * 
     * First tries from the cache, if does not exists it tries from the net.
     * 
     * @param storageDir	
     * @param urlImage
     * @param cacheExists
     * @return
     */
    public static Bitmap media_getBitmapFromNet(File storageDir, String urlImage, boolean cacheExists) throws Exception{
    	Bitmap res=null;
    	
    	//I creates the names of the images by the hashcode of its url
    	String filename=String.valueOf(urlImage.hashCode());
    	File f= null;
    	
        try {
        	//First, try to load then from the cache SD dir        
            if(cacheExists){
            	f=new File(storageDir, filename);
            	
            	Bitmap b = media_getBitmapFromFile(f);
    	        if(b!=null)
    	            return b;
            }
        	
            //If is not in the cache (or cache is not activated), tries from web        	
        	//
            //...get the image from the net
            Bitmap bitmap=null;
            
            if(cacheExists){
            	//We store the image in the cache
            	storage_saveInputData(new URL(urlImage).openStream(),f,true);            	
                //...and get from the cache
                bitmap = media_getBitmapFromFile(f);
            }else{
            	//Read directly from the net without saving in cache
        		URL imageURL=new URL(urlImage);
                InputStream is=imageURL.openConnection().getInputStream();
        		BufferedInputStream bis=new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis); 
                bis.close();is.close();                
            }            
            
            res=bitmap;
        } catch (Exception e){
        	if(LOG_ENABLE)
        		Log.e(TAG,"media_getBitmapFromNet(): "+e.getMessage(),e);
        	
        	throw new Exception(TAG+"[media_getBitmapFromNet()]: "+e.getMessage(),e);        	
        }
        
        return res;
    }

    /**
     * returns the Bitmap of a specified image resource id.
     * 
     * @param context
     * @param resourceId
     * @return
     * @throws Exception
     */
    public static Bitmap media_getBitmapFromResourceId(Context context, int resourceId) throws Exception{
    	return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }
    
    /**
     * Returns a Bitmap from an URL.
     * 
     * @param context
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap media_getBitmapFromURL(Context context, String url) throws Exception{
    	Bitmap res = null;
    	
    	if(url!=null){
	    	try {
	    		res = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
	    	} catch (IOException e) {
	    		if(LOG_ENABLE)
	        		Log.e(TAG,"media_getBitmapFromURL(): "+e.getMessage(),e);
	    	}
    	}
    	
    	return res;
    }    
    
    /**
     * Decodes the specified image from the storage, scales it to reduce memory consumption
     * and returning as a Bitmap object.
     * 
     * @param imgFile	File to get as a Bitmap object.
     * @return
     */
    public static Bitmap media_getBitmapFromFile(File imgFile) throws Exception{
        Bitmap res=null;
    	
    	try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;            
            BitmapFactory.decodeStream(new FileInputStream(imgFile),null,o);
            
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            
            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            res=BitmapFactory.decodeStream(new FileInputStream(imgFile), null, o2);
            
        }catch (FileNotFoundException e){
        	if(LOG_ENABLE)
        		Log.e(TAG,"media_generateBitmapFromFile() - FileNotFoundException: "+e.getMessage(),e);
        	
        	throw new Exception(TAG+"[media_generateBitmapFromFile() - FileNotFoundException]: "+e.getMessage(),e);
        }catch (Exception e){
        	if(LOG_ENABLE)
        		Log.e(TAG,"media_generateBitmapFromFile(): "+e.getMessage(),e);
        	
        	throw new Exception(TAG+"[media_generateBitmapFromFile()]: "+e.getMessage(),e);
        }
        
        return res;
    }
    
    /**
     * Grabs an image direct from a URL into a Drawable without saving a cache
     * 
     * @param urlImage
     * @param src_name
     * @return
     * @throws Exception
     */
	public static Drawable media_getDrawableFromNet(String urlImage, String src_name) throws Exception {
		Drawable res=null;
		
	    try{
	    	InputStream is=((InputStream)new URL(urlImage).getContent());	    	
			res=Drawable.createFromStream(is, src_name);
			is.close();
		}catch (MalformedURLException e) {
			if(LOG_ENABLE)
				Log.e(TAG,"media_generateBitmapFromFile() - MalformedURLException: "+e.getMessage(),e);
			
        	throw new Exception(TAG+"[media_generateBitmapFromFile() - MalformedURLException]: "+e.getMessage(),e);
		}catch (IOException e) {
			if(LOG_ENABLE)
				Log.e(TAG,"media_generateBitmapFromFile() - IOException: "+e.getMessage(),e);
			
        	throw new Exception(TAG+"[media_generateBitmapFromFile() - IOException]: "+e.getMessage(),e);
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e(TAG,"media_generateBitmapFromFile(): "+e.getMessage(),e);
			
        	throw new Exception(TAG+"[media_generateBitmapFromFile()]: "+e.getMessage(),e);
		}
	    	    
	    return res;
	}
    
	/**
     * Grabs an image directly from a file into a Drawable without saving a cache
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
	public static Drawable media_getDrawableFromFile(String filePath) throws Exception {
		Drawable res=null;
		
	    try{	    		    	
			res=Drawable.createFromPath(filePath);
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e(TAG,"media_getDrawableFromFile(): "+e.getMessage(),e);
			
        	throw new Exception(TAG+"[media_getDrawableFromFile()]: "+e.getMessage(),e);
		}
	    	    
	    return res;
	} 
	
	/**
	 * Gets bytes from an image resource id.
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public static byte[] media_getBytesFromDrawableResourceId(Context context, int resourceId){
		try{
			Drawable d = context.getResources().getDrawable(resourceId);
			return media_getBytesFromDrawable(context, d);
			
		}catch(NotFoundException e) {
			Log.e(TAG,"media_getBytesFromDrawableResourceId() - Image bytes could not be get, invalid resource id ["+e.getMessage() + "].",e);
			return null;
		}
		
	}
	
	/**
	 * Gets bytes from an asset folder image.
	 * 
	 * @param context
	 * @param resourceName
	 * @return
	 */
	public static byte[] media_getBytesFromAssetImage(Context context, String resourceName){
		
		try{
			//Get the drawable from an asset. 
		    InputStream ims = context.getAssets().open(resourceName);
		    Drawable d = Drawable.createFromStream(ims, null);
		    return media_getBytesFromDrawable(context, d);
		    
		}catch(IOException e){
			Log.e(TAG, "media_getBytesFromAssetImage() - Error getting image bytes from asset folder [" + e.getMessage() + "].", e);
		    return null;
		}
	}
	
	/**
	 * Gets bytes from an Drawable object.
	 * 
	 * @param context
	 * @param drawable
	 * @return
	 */
	public static byte[] media_getBytesFromDrawable(Context context, Drawable drawable){
		//Get the bytes from the Drawable
		if(drawable!=null) {
		    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		    byte[] bitmapData = stream.toByteArray();
		    if(bitmapData==null || (bitmapData!=null && bitmapData.length==0)) {
		    	Log.e(TAG, "media_getBytesFromDrawable() - Error getting image bytes from Drawable object.");
		    	return null;
		    }else{
		    	return bitmapData;		    	
		    }		
		}else{
			Log.i(TAG, "media_getBytesFromDrawable() - Drawable object is null.");
			return null;
		}
	}
	
	/**
	 * Loads a Bitmap image form the internal storage.
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Bitmap media_loadBitmapFromInternalStorage(Context context, String fileName) throws Exception{
		 
		try{
			 FileInputStream is = context.openFileInput(fileName);
			 Bitmap b = BitmapFactory.decodeStream(is);
			 
			 return b;
		} catch (Exception e) {			 
			 throw new Exception("Error reading data '" + fileName + "' (internal storage) : "+ e.getMessage(),e);
		}
	}
	 
	 
	/**
	 * Makes rounded corners on a bitmap.
	 * 
	 * Requires Level 17 of Android API!!
	 * 
	 * @param bitmap
	 * @return
	 */
	/*public static Bitmap media_getRoundedCornerBitmap(Bitmap bitmap) {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = 8;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}*/

	/**
	 * Convers a Bitmap to grayscale.
	 * 
	 * @param bmpOriginal
	 * @return
	 */
	 public static Bitmap media_getGrayScale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	 }
	 
	 
	 /**
	  * Plays the specified ringtone from the application raw folder.
	  * 
	  * @param appPackage			Application package. (you can get it by using: 
	  * 							AppVigilant.thiz.getApplicationContext().getPackageName())
	  * @param soundResourceId		Sound resource id
	  */
	 public static void media_soundPlayFromRawFolder(Context context, String appPackage, int soundResourceId){
		try {
			Uri soundUri = Uri.parse("android.resource://" + appPackage + "/" + soundResourceId);
	        Ringtone r = RingtoneManager.getRingtone(context, soundUri);	        
	        r.play();
		
	    } catch (Exception e) {
	    	if(LOG_ENABLE){
	    		Log.e(TAG, "Error playing sound with resource id: '" + soundResourceId + "' (" + e.getMessage() + ")", e);
	    	}
	    }
	 }
	 
	 /**
	  * Plays the default system notification ringtone.
	  * 
	  * @param context
	  */
	 public static void media_soundPlayNotificationDefault(Context context){
		try {
			Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        Ringtone r = RingtoneManager.getRingtone(context, soundUri);
	        r.play();
	        
	    } catch (Exception e) {
	    	if(LOG_ENABLE){
	    		Log.e(TAG, "Error playing default notification sound (" + e.getMessage() + ")", e);
	    	}
	    }
	 }
	 
	 /**
	  * Plays the specified sound from the application asset folder.
	  * 
	  * @param context
	  * @param assetSoundPath	Path to the sound in the assets folder.
	  */
	 public static void media_soundPlayFromAssetFolder(Context context, String assetSoundPath){
		try {
			AssetFileDescriptor afd = context.getAssets().openFd(assetSoundPath);
			
	        MediaPlayer player = new MediaPlayer();
	        player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
	        player.prepare();
	        player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
	        player.start();
	        
	    } catch (Exception e) {
	    	if(LOG_ENABLE){
	    		Log.e(TAG, "Error playing sound: '" + assetSoundPath + "' (" + e.getMessage() + ")", e);
	    	}
	    }
	 }
	 
	 /**
	  * Plays the specified sound.
	  * 
	  * @param context
	  * @param soundId	The resource to play.
	  */
	 public static void media_soundPlay(Context context, int soundId){
		try {
			//int fileResourceId = context.getResources().getIdentifier(rawSoundPath,"raw", context.getPackageName());
			MediaPlayer player = MediaPlayer.create(context, soundId);
			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
	        player.start();
	        
	    } catch (Exception e) {
	    	if(LOG_ENABLE){
	    		Log.e(TAG, "Error playing sound (" + e.getMessage() + ")", e);
	    	}
	    }
	 }
	 
	 /**
	  * Plays the specified filename of the RAW Android folder.
	  * 
	  * @param context
	  * @param rawSoundName	The filename without the extension in the raw folder.
	  */
	 public static void media_soundPlayFromRawFolder(Context context, String rawSoundName){
			try {
				int fileResourceId = context.getResources().getIdentifier(rawSoundName,"raw", context.getPackageName());
				MediaPlayer player = MediaPlayer.create(context, fileResourceId);
				player.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.release();
					}
				});
		        player.start();
		        
		    } catch (Exception e) {
		    	if(LOG_ENABLE){
		    		Log.e(TAG, "Error playing sound (" + e.getMessage() + ")", e);
		    	}
		    }
		 }
	 	 
	 // Device Related -----------------------------------------------------------------------------------------------------------------------------
	 
   /**
	* Returns a unique UUID for the an android device. As with any UUIDs,
    * this unique ID is "very highly likely" to be unique across all Android
    * devices. Much more than ANDROID_ID is.
	* 
	* It uses as the base the ANDROID_ID only if is not null and not the 
	* some device manufacturers buggy ID 9774d56d682e549c for 2.2, 2.3 android
	* version (@see http://code.google.com/p/android/issues/detail?id=10603). 
	* If is not available or is the buggy one, a unique UUID will be 
	* generated using the SERIAL property of the device and if not available,
	* a bunch of device properties will be used to generated a unique UUID string.
	* 
	* @param context
	* @return a UUID that may be used, in most cases, to uniquely identify your 
	* 		  device for most.
	*/
	private static String device_getLikelyId(Context context) {
		 UUID uuid = null;
		 
		 String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);		 
		 if(androidId==null) {
			 uuid = generateUniqueDeviceUUIDId();
		 }else{
			//Several devices by several manufacturers are affected by the ANDROID_ID bug in 2.2.
		   	//All affected devices have the same ANDROID_ID, which is 9774d56d682e549c. Which is 
		   	//also the same device id reported by the emulator.
		   	if(!"9774d56d682e549c".equals(androidId)){
		   		try{
		   			uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
			   	} catch (UnsupportedEncodingException e) {
			        Log.e(TAG, "UnsupportedEncodingException (" + e.getMessage() + ").", e);
			    }
		   	}else{
		   		uuid = generateUniqueDeviceUUIDId();		   		
		   	}
		 }
		 
		 return uuid.toString();
	 }
	 
	/**
	 * Generates a unique device id using the device 
	 * "serial" property if is available. If not, a bunch
	 * of device properties will be used to get a reliable
	 * unique string key for the device.
	 * 
	 * If there is an error in UUID generation null is
	 * returned.
	 *    
	 * @return	The unique UUID or nul in case of error.
	 */
	private static UUID generateUniqueDeviceUUIDId() {
		UUID uuid = null;
   		
		try{
			//We generate a unique id
			String serial = null;
	   	    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) { 
	   	    	serial = Build.SERIAL;
	   	    	uuid = UUID.nameUUIDFromBytes(serial.getBytes("utf8"));
	   	    }else{
	   	    	//This bunch of data should be enough to "ensure" the 
	   	    	//uniqueness.
	   	    	String m_szDevIDAlterbative = "35" + //To look like a valid IMEI
	   	             Build.BOARD.length()%10+ Build.BRAND.length()%10 +
	   	             Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
	   	             Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
	   	             Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
	   	             Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
	   	             Build.TAGS.length()%10 + Build.TYPE.length()%10 +
	   	             Build.USER.length()%10 ; //13 digits
	   	    	
	   	    	uuid = UUID.nameUUIDFromBytes(m_szDevIDAlterbative.getBytes("utf8"));
	   	    }
   	    
		} catch (UnsupportedEncodingException e) {
	        Log.e(TAG, "UnsupportedEncodingException (" + e.getMessage() + ").", e);
	    }
   	    
   	    return uuid;
	 }
	
	/**
	 * Returns a unique UUID for an android device.<br>
	 * <br>
	 * <b>Requires the permission "READ_PHONE_STATE"</b>. Remember to ask 
	 * for permissions in Android 6+ (use ToolBox.PERMISSION_LOCATION and permission
	 * related methods in ToolBox before using this).<br>
	 * <br>
	 * If the device IMEI and the SIM IMSI are available, these values are used
	 * to construct an UUID that starts with "@". If these are not available, the 
	 * UUID will be constructed by using as the base the ANDROID_ID only if is 
	 * not null and not the some device manufacturers buggy ID 9774d56d682e549c 
	 * for 2.2, 2.3 android version (@see http://code.google.com/p/android/issues/detail?id=10603). 
	 * If is not available or is the buggy one, a unique UUID will be generated 
	 * using the SERIAL property of the device and if not available, a bunch of 
	 * device properties will be used to generated a unique UUID string. In this case,
	 * the UUID string will begin with "#".
	 * 
	 * @param context
	 * @return The UUID string or null if is not possible to get.
	 */
	public static String device_getId(Context context) {
		String uuid = null;
		
		String imei = null;
		String imsi = null;
		try{
			imei = device_getIMEI(context);
			imsi = device_getSIMIMSI(context);
		}catch(SecurityException e){
			//In Android 6+, READ_PHONE_STATE permission must be granted.
			if(LOG_ENABLE)
				Log.w(TAG, "SecurityException (" + e.getMessage() + "). Remember to grant permissions if Android 6+.");
		}
		
		if((imei!=null && imei.length()>0) &&
		   (imsi!=null && imsi.length()>0)){
			String uniqueIdString = imei + "/" + imsi;
			try{
				uuid = "@" + (UUID.nameUUIDFromBytes(uniqueIdString.getBytes("utf8"))).toString();
			} catch (UnsupportedEncodingException e) {
		        Log.e(TAG, "UnsupportedEncodingException (" + e.getMessage() + ").", e);
		    }
		}else{
			uuid = "#" + device_getLikelyId(context);
		}
		
		return uuid;
	}
	
	
	/**
	 * Gets the SIM unique Id, IMSI.
	 * <br><br>
	 * <b>Requires the permission "READ_PHONE_STATE"</b>. Remember to ask 
	 * for permissions in Android 6+ (use ToolBox.PERMISSION_LOCATION and permission
	 * related methods in ToolBox before using this).
	 * 
	 * @param context
	 * @return The SIM subscriberId or NULL if is not available or empty if no SIM is detected.
	 */
	public static String device_getSIMIMSI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(tm!=null)
			return tm.getSubscriberId();
		else
			return null;
	}
	
	/**
	 * Gets the device screen size in pixels.
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static Point device_screenSize(Context context){
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		final Point size = new Point();
		try{
			display.getSize(size);
		} catch (NoSuchMethodError ignore) { // Older device
			size.x = display.getWidth();
			size.y = display.getHeight();
	    }
    	
    	return size;
	}
	
	
	public static DisplayMetrics device_screenMetrics(Context context){
		DisplayMetrics metrics = new DisplayMetrics();
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		display.getMetrics(metrics);
		//display.getRealMetrics(metrics);
    	
    	return metrics;
	}
	
	/**
	 * Makes the device to vibrate for a specified period of time.
	 * <br><br>
	 * <b>Note</b>: requires the permission android.permission.VIBRATE.
	 * 
	 * @param context
	 * @param millis	The time to vibrate (milliseconds)
	 */
	@SuppressLint("NewApi")
	public static void device_vibrate(Context context, long millis) {
		try{		
			Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			
			boolean canVibrate = true;
			if(device_hasAPILevel(11)){
				canVibrate = v.hasVibrator(); 
			}
			if (canVibrate) {
				v.vibrate(millis);
			}else{
				if(LOG_ENABLE)
					Log.i(TAG, "Device can not vibrate.");
			}
			
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e(TAG, "Vibration exception (" + e.getMessage() + "). Remember to set permission android.permission.VIBRATE in your manifest application file.");
		}
	}
	
	/**
	 * Makes the device to vibrate for a specified pattern. A pattern is a set
	 * of periods, in milliseconds.
	 * <br><br>
	 * <b>Note</b>: requires the permission android.permission.VIBRATE.
	 * 
	 * @param context
	 * @param pattern	The pattern to use when device vibrates
	 * @param indefinitely	If set to TRUE, vibration never ends.
	 * @return The vibrator object, just in case "indefinitely" parameter is set to TRUE,
	 *         to be able to stop the vibration.
	 */
	@SuppressLint("NewApi")
	public static Vibrator device_vibrate(Context context, long[] pattern, boolean indefinitely) {
		try{
			Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			
			int vibrateIndefinitely = -1;		
			if(indefinitely)
				vibrateIndefinitely = 0;
			
			boolean canVibrate = true;
			if(device_hasAPILevel(11)){
				canVibrate = v.hasVibrator(); 
			}
			
			if (canVibrate) {
				v.vibrate(pattern, vibrateIndefinitely);
			}else{
				if(LOG_ENABLE)
					Log.i(TAG, "Device can not vibrate.");
			}
			
			return v;
			
		}catch(Exception e){
			if(LOG_ENABLE)
				Log.e(TAG, "Vibration exception (" + e.getMessage() + "). Remember to set permission android.permission.VIBRATE in your manifest application file.");
		}
		
		return null;
	}	
	
	/**
	* Get the optimal preview size for the given screen size.
	* @param sizes			The camera available sizes. See {@link android.hardware.Camera} 
	* 						(mCamera.getParameters().getSupportedPreviewSizes())
	* @param screenWidth		The device screen width. You can get it with {@link ToolBox#device_screenSize(Context)}.
	* @param screenHeight	The device screen height. You can get it with {@link ToolBox#device_screenSize(Context)}.
	* @param fasterRendering	Often there is not an adequate resolution with the correct 
	* 							aspect ratio available. If set to TRUE, aspect ratio will be trade for  
								for faster rendering.
	* @return
	*/
	@SuppressWarnings("deprecation")
	public static Size device_cameraGetOptimalPreviewSize(List<Size> sizes, int screenWidth, int screenHeight, boolean fasterRendering) {
		double epsilon = 0.001;
		if(fasterRendering)
			epsilon = 0.17;
		
	    double aspectRatio = ((double)screenWidth)/screenHeight;
	    Size optimalSize = null;
	    for (Iterator<Size> iterator = sizes.iterator(); iterator.hasNext();) {
	      Size currSize =  iterator.next();
	      double curAspectRatio = ((double)currSize.width)/currSize.height;
	      //do the aspect ratios equal?
	      if ( Math.abs( aspectRatio - curAspectRatio ) < epsilon ) {
	        //they do
	        if(optimalSize!=null) {
	          //is the current size smaller than the one before
	          if(optimalSize.height>currSize.height && optimalSize.width>currSize.width) {
	            optimalSize = currSize;
	          }
	        } else {
	          optimalSize = currSize;
	        }
	      }
	    }
	    if(optimalSize == null) {
	      //did not find a size with the correct aspect ratio.. let's choose the smallest instead
	      for (Iterator<Size> iterator = sizes.iterator(); iterator.hasNext();) {
	        Size currSize =  iterator.next();
	        if(optimalSize!=null) {
	          //is the current size smaller than the one before
	          if(optimalSize.height>currSize.height && optimalSize.width>currSize.width) {
	            optimalSize = currSize;
	          } else {
	            optimalSize = currSize;
	          }
	        }else {
	          optimalSize = currSize;
	        }
	      }
	    }
	    return optimalSize;
	}

	/**
	 * Set camera to continuous focus if supported, otherwise use software
	 * auto-focus.
	 * 
	 * @param camera	The camera.
	 * @return	The camera parameters
	 */
	@SuppressLint("InlinedApi")
	@SuppressWarnings("deprecation")
	public static Camera.Parameters device_cameraSetFocusMode(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();
		List<String> supportedFocusModes = parameters.getSupportedFocusModes();
	    if (ToolBox.device_hasAPILevel(ToolBox.ApiLevel.LEVEL_14) &&
	        supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
	        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
	    }else if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
	        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
	    }else if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
	        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
	    }
	    
	    return parameters;
	}
	
	/**
	 * Gets the smallest size of the available camera sizes.
	 * 
	 * @param sizes	The camera available sizes. See {@link android.hardware.Camera} 
	* 						(mCamera.getParameters().getSupportedPreviewSizes())
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Size device_cameraGetSmallestSize(List<Size> sizes) {
	    Size optimalSize = null;
	    for (Iterator<Size> iterator = sizes.iterator(); iterator.hasNext();) {
	      Size currSize =  iterator.next();    
	      if(optimalSize == null) {
	        optimalSize = currSize;
	      } else if(optimalSize.height>currSize.height && optimalSize.width>currSize.width) {
	        optimalSize = currSize;
	      }
	    }
	    return optimalSize;
	}
	
	/**
	 * Properly rotates the camera to match the device orientation.
	 * 
	 * @param context
	 * @param camera
	 */
	@SuppressWarnings("deprecation")
	public static void device_cameraAlignRotationWithDeviceOrientation(Context context, final Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(0, info);
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        
        int degrees = 0;
        switch (rotation) {
          case Surface.ROTATION_0: degrees = 0; break;
          case Surface.ROTATION_90: degrees = 90; break;
          case Surface.ROTATION_180: degrees = 180; break;
          case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
          result = (info.orientation + degrees) % 360;
          result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
          result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
	}
	
	/**
	 * Returns true if the camera has the spcified size available.
	 * 
	 * @param sizes	The camera available sizes. See {@link android.hardware.Camera} 
	* 						(mCamera.getParameters().getSupportedPreviewSizes())
	 * @param size	The size to find
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean device_cameraContainsSize(List<Size> sizes, Size size) {
	    for (Iterator<Size> iterator = sizes.iterator(); iterator.hasNext();) {
	      Size currSize =  iterator.next();
	      if(currSize.width == size.width && currSize.height == size.height) {
	        return true;
	      }      
	    }
	    return false;
	}
	
	/**
	 * Gets the device type from its screen properties.
	 * 
	 * @param context	
	 * @param strictlyInInches	This makes that all calculations be made by 
	 * 							inches, using the number of pixels per inch,
	 * 							instead using density points. Useful for some 
	 * 							weird non standard devices.
	 * @return	{@link es.javocsoft.android.lib.toolbox.ToolBox.DEVICE_BY_SCREEN}.
	 */
	public static DEVICE_BY_SCREEN device_getTypeByScreen(Context context, boolean strictlyInInches){
		DEVICE_BY_SCREEN res = DEVICE_BY_SCREEN.DP320_NORMAL;
		
		DisplayMetrics metrics = device_screenMetrics(context);
		
		int widthPixels = -1;
		int heightPixels = -1;
		
		//The width and height in pixels
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
	    	// For JellyBeans and onward
	        widthPixels = metrics.widthPixels;
			heightPixels = metrics.heightPixels;
	    } else {
	    	//To avoid excluding the dimensions of the Navigation Bar
	        try {
	        	Method mGetRawH = Display.class.getMethod("getRawHeight");
		        Method mGetRawW = Display.class.getMethod("getRawWidth");		        
	        	widthPixels = (Integer) mGetRawW.invoke(metrics);
	        	heightPixels = (Integer) mGetRawH.invoke(metrics);
	        } catch (Exception e) {
	        	if(LOG_ENABLE)
	        		Log.w(TAG, "Controlled error getting width and height in pixels [" + e.getMessage() + "]",e);
	        }
	    }
		
	    if(widthPixels==-1 || heightPixels==-1){
	    	//We do nothing, return NORMAL.
	    }else{
			if(strictlyInInches){
				//Physical pixels per inch  
				float widthDpi = metrics.xdpi;
				float heightDpi = metrics.ydpi;
				
				float widthInches = widthPixels / widthDpi;
				float heightInches = heightPixels / heightDpi;
				
				/* However, we also know that given the height of a triangle and the width, 
				 * we can use the Pythagorean theorem to work out the length of the 
				 * hypotenuse (In this case, the size of the screen diagonal).
				 * 
				 * a� + b� = c� 
				 */
				//The size of the diagonal in inches is equal to the square root of the height 
				//in inches squared plus the width in inches squared.
				double diagonalInches = Math.sqrt((widthInches * widthInches)+ (heightInches * heightInches));
				
				if (diagonalInches >= 7) {
					res = DEVICE_BY_SCREEN.DP600_7INCH;
				}else if (diagonalInches >= 10) {
					res = DEVICE_BY_SCREEN.DP720_10INCH;
				}else{
					res = DEVICE_BY_SCREEN.DP320_NORMAL;
				}
				
			}else{
				//We get in density points (dp)
				float scaleFactor = metrics.density;		
				float widthDp = widthPixels / scaleFactor;
				float heightDp = heightPixels / scaleFactor;
				
				/*
				 * Now we get the inches according to Google:
				 * 
				 * 320dp: a typical phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc).
				 * 480dp: a tweener tablet like the Streak (480x800 mdpi).
				 * 600dp: a 7" tablet (600x1024 mdpi).
				 * 720dp: a 10" tablet (720x1280 mdpi, 800x1280 mdpi, etc).
				 */		
				float smallestWidth = Math.min(widthDp, heightDp);		
				if (smallestWidth <= 320 ) {
				    res = DEVICE_BY_SCREEN.DP320_NORMAL;
				}else if (smallestWidth > 320 && smallestWidth <= 480) {
					res = DEVICE_BY_SCREEN.DP480_TWEENER;
				}else if (smallestWidth > 600 && smallestWidth < 720) {
					res = DEVICE_BY_SCREEN.DP600_7INCH;
				}else if (smallestWidth > 720) {
					res = DEVICE_BY_SCREEN.DP720_10INCH;
				}
			}
	    }
		
		return res;
	}
	
	/**
	 * Returns the device resolution type or unknown
	 * if not recognized.
	 * 
	 * @param context
	 * @return	{@link es.javocsoft.android.lib.toolbox.ToolBox.DEVICE_RESOLUTION_TYPE}
	 */
	public static DEVICE_RESOLUTION_TYPE device_getResolutionType(Context context) {
		DEVICE_RESOLUTION_TYPE res = null;
		
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		
		switch (metrics.densityDpi) {
			case DisplayMetrics.DENSITY_LOW:
				res = DEVICE_RESOLUTION_TYPE.ldpi;
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				res = DEVICE_RESOLUTION_TYPE.mdpi;
				break;
			case DisplayMetrics.DENSITY_HIGH:
				res = DEVICE_RESOLUTION_TYPE.hdpi;
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				res = DEVICE_RESOLUTION_TYPE.xhdpi;
				break;
			case DisplayMetrics.DENSITY_XXHIGH:
				res = DEVICE_RESOLUTION_TYPE.xxhdpi;
				break;		
			case DisplayMetrics.DENSITY_XXXHIGH:
				res = DEVICE_RESOLUTION_TYPE.xxxhdpi;
				break;
			default:
				res = DEVICE_RESOLUTION_TYPE.unknown;
		}
		
		return res;
	}
	
	/**
	 * Get the device language (two letter code value).
	 * <br><br>
	 * See:<br>
	 * <a href="https://en.wikipedia.org/wiki/ISO_639-1">ISO639-1</a><br>
	 * <a href="http://developer.android.com/intl/es/reference/java/util/Locale.html">Android Locale</a>
	 * 
	 * @return	The language code.
	 */
	public static String device_getLanguage(){
		return Locale.getDefault().getLanguage();
	}
	
	
	/**
	 * Get the current android API Level.
	 * <br><br>
	 * See:  
	 * <a href="http://developer.android.com/intl/es/guide/topics/manifest/uses-sdk-element.html#ApiLevels">API Level</a>
	 * 
	 * @return	The device current Android API level.
	 */
	public static int device_getAPILevel(){
		return Build.VERSION.SDK_INT;
	}
	
	/**
	 * Checks if the device has at least the specified API level.
	 * <br><br>
	 * See:  
	 * <a href="http://developer.android.com/intl/es/guide/topics/manifest/uses-sdk-element.html#ApiLevels">API Level</a>
	 * 
	 * @param apiLevel
	 * @return
	 */
	public static boolean device_hasAPILevel(int apiLevel){
		if(device_getAPILevel()>=apiLevel){
			return true;
		}
		
		return false;
	}	
	
	/**
	 * Checks if the device has at least the specified API level.
	 * <br><br>
	 * See:  
	 * <a href="http://developer.android.com/intl/es/guide/topics/manifest/uses-sdk-element.html#ApiLevels">API Level</a>
	 * 
	 * @param apiLevel See {@link ApiLevel}
	 * @return
	 */
	public static boolean device_hasAPILevel(ApiLevel apiLevel){
		if(device_getAPILevel()>=apiLevel.getValue()){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get the version number of Android.
	 * 
	 * @return
	 */
	public static String device_getOSVersion() {
		return String.valueOf(Build.VERSION.RELEASE);
	}
	
	/**
	 * Gets the device version codename.
	 * 
	 * @return
	 */
	public static String device_getOSCodename() {
		return String.valueOf(Build.VERSION.CODENAME);
	}
	
	/**
	 * Get device extra information like Manufacturer,
	 * brand and device model info.
	 * 
	 * @return
	 */
	public static String device_getExtraInfo() {
		String extra = 	android.os.Build.MANUFACTURER + 
						(android.os.Build.BRAND!=null?"/" + android.os.Build.BRAND:"") +
						"/" + android.os.Build.DEVICE + 
						"/" + android.os.Build.MODEL +
						"/" + android.os.Build.HARDWARE +
						"/" + android.os.Build.PRODUCT;
						
		return extra;
	}
	
	
	/**
	 * Gets the device mobile number if is available,
	 * NULL otherwise.
	 * 
	 * @param context
	 * @return
	 */
	public static String device_getDeviceMobileNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if(tm!=null) {
			return tm.getLine1Number();
		}else{
			return null;
		}
	}
	
	/**
	 * Gets the device unique id, IMEI, if is available, NULL otherwise.
	 * <br><br>
	 * <b>Requires the permission "READ_PHONE_STATE"</b>. Remember to ask 
	 * for permissions in Android 6+ (use ToolBox.PERMISSION_LOCATION and permission
	 * related methods in ToolBox before using this).
	 * 
	 * @param context
	 * @return
	 */
	public static String device_getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if(tm!=null) {
			return tm.getDeviceId();
		}else{
			return null;
		}
		
	}
	
	/**
	 * Returns TRUE if a specified hardware feature is present.
	 * 
	 * Use PackageManager.FEATURE_[feature] to specify the feature
	 * to query.
	 * 
	 * @param context
	 * @param hardwareFeature	Use PackageManager.FEATURE_[feature] to specify 
	 * 							the feature to query.
	 * @return
	 */
	public static boolean device_isHardwareFeatureAvailable(Context context, String hardwareFeature){
		return context.getPackageManager().hasSystemFeature(hardwareFeature);
	}
	
	/**
	 * Return TRUE if there is a hardware keyboard present.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean device_isHardwareKeyboard(Context context){
		//You can also get some of the features which are not testable by the PackageManager via the Configuration, e.g. the DPAD.
        Configuration c = context.getResources().getConfiguration();
        if(c.keyboard != Configuration.KEYBOARD_NOKEYS){
            return true;
        }else{
        	return false;
        }
	}
	
	/**
	 * Return TRUE if there is a hardware DPAD navigation button.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean device_isHardwareDPAD(Context context){
		//You can also get some of the features which are not testable by the PackageManager via the Configuration, e.g. the DPAD.
        Configuration c = context.getResources().getConfiguration();
        if(c.navigation == Configuration.NAVIGATION_DPAD){
        	return true;
        }else{
        	return false;
        }
	}
	
	/**
	 * Return TRUE if there is a hardware keyboard and is being made hidden.
	 *  
	 * @param context
	 * @return
	 */
	public static boolean device_isHardwareKeyboardHidden(Context context){
		//You can also get some of the features which are not testable by the PackageManager via the Configuration, e.g. the DPAD.
        Configuration c = context.getResources().getConfiguration();
        if(c.hardKeyboardHidden != Configuration.HARDKEYBOARDHIDDEN_UNDEFINED && 
           c.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES){
        	return true;
        }else{
        	return false;
        }
	}
	
	/**
	 * Return TRUE if there is a hardware keyboard and is being made visible.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean device_isHardwareKeyboardVisible(Context context){
		//You can also get some of the features which are not testable by the PackageManager via the Configuration, e.g. the DPAD.
        Configuration c = context.getResources().getConfiguration();
        if(c.hardKeyboardHidden != Configuration.HARDKEYBOARDHIDDEN_UNDEFINED && 
           c.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO){
        	return true;
        }else{
        	return false;
        }
	}
	
	/**
	 * Returns true if the menu hardware menu is present on the device
	 * 
	 * This function requires API Level 14.
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static boolean device_isHardwareMenuButtonPresent(Context context){
		if(device_getAPILevel()>=14){
			//This requires API Level 14
			return ViewConfiguration.get(context).hasPermanentMenuKey();
		}else{
			return false;
		}
	}
	
	//-------------------- LOCATION ----------------------------------------------------------------------
	
	public static class LocationInfo {
		private String country;
		private String countryCode;
		private String city;
		private String postalCode;
		private String address;
		private String addressStreet;
		private String addressStreetNumber;
		
		public LocationInfo(String country, String countryCode, String city,
				String postalCode, String address, String addressStreet,
				String addressStreetNumber) {			
			this.country = country;
			this.countryCode = countryCode;
			this.city = city;
			this.postalCode = postalCode;
			this.address = address;
			this.addressStreet = addressStreet;
			this.addressStreetNumber = addressStreetNumber;			
		}

		public LocationInfo() {
		}
		

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getCountryCode() {
			return countryCode;
		}

		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getAddressStreet() {
			return addressStreet;
		}

		public void setAddressStreet(String addressStreet) {
			this.addressStreet = addressStreet;
		}

		public String getAddressStreetNumber() {
			return addressStreetNumber;
		}

		public void setAddressStreetNumber(String addressStreetNumber) {
			this.addressStreetNumber = addressStreetNumber;
		}
	}
	
	/** The available latitude and longitude address information types. */
	public static enum LOCATION_INFO_TYPE {COUNTRY, COUNTRY_CODE, CITY, POSTAL_CODE, ADDRESS, ADDRESS_STREET, ADDRESS_STREET_NUMBER, ALL}; 
	
	/**
	 * From a latitude and longitude, returns the desired address information type
	 * or null in case of error or not found.
	 * <br><br>
	 * <b>NOTE</b>: This method uses the Android SDK Geocoder so is an expensive, in time, 
	 * operation so in case you need more than one property, use the versión 
	 * of this method that returns an object with all location data.
	 * 
	 * @param context
	 * @param locationInfoType	The desired location info. See {@link LOCATION_INFO_TYPE} enum.
	 * @param lattitude
	 * @param longitude
	 * @return
	 */
	public static String location_addressInfo(Context context, LOCATION_INFO_TYPE locationInfoType, 
			double latitude, double longitude) {

        String res = null; 
        
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        if(gcd!=null) {
	        try {
	            List<Address> addresses = gcd.getFromLocation(latitude, longitude,10);
	
	            for (Address adrs : addresses) {
	                if (adrs != null) {                	
	                	String data = null;
	                	switch (locationInfoType) {
							case COUNTRY_CODE:
								data = adrs.getCountryCode();
								break;
							case COUNTRY:
								data = adrs.getCountryName();
								break;
							case CITY:
								data = adrs.getLocality();
								break;
							case POSTAL_CODE:
								data = adrs.getPostalCode();
								break;
							case ADDRESS:
								if(adrs.getThoroughfare()!=null){
									data = adrs.getThoroughfare() + ((adrs.getSubThoroughfare()!=null && adrs.getSubThoroughfare().trim().length()>0)?("," + adrs.getSubThoroughfare()):"");
								}
								break;	
							case ADDRESS_STREET:
								data = adrs.getThoroughfare();
								break;
							case ADDRESS_STREET_NUMBER:
								data = adrs.getSubThoroughfare();
								break;
							case ALL:	
								res = adrs.toString();
								break;
						}                    
	                    if (data != null && data.length()>0) {
	                        res = data;
	                        break;
	                    }
	                }
	            }
	        } catch (IOException e) {
	        	if(LOG_ENABLE)
					Log.e(TAG, "location_addressInfo(). Could not get information [" + e.getMessage() + "]. Trying with Maps API.", e);
	        	
	        	try {
					List<Address> addresses = location_addressInfoFromGoogleGeocodeAPI(latitude, longitude);
					
					for (Address adrs : addresses) {
						if (adrs != null) {                	
		                	String data = null;
		                	switch (locationInfoType) {
								case COUNTRY_CODE:
									data = adrs.getCountryCode();
									break;
								case COUNTRY:
									data = adrs.getCountryName();
									break;
								case CITY:
									data = adrs.getLocality();
									break;
								case POSTAL_CODE:
									data = adrs.getPostalCode();
									break;
								case ADDRESS:
									if(adrs.getThoroughfare()!=null){
										data = adrs.getThoroughfare() + ((adrs.getSubThoroughfare()!=null && adrs.getSubThoroughfare().trim().length()>0)?("," + adrs.getSubThoroughfare()):"");
									}
									break;	
								case ADDRESS_STREET:
									data = adrs.getThoroughfare();
									break;
								case ADDRESS_STREET_NUMBER:
									data = adrs.getSubThoroughfare();
									break;
								case ALL:	
									res = adrs.toString();
									break;
							}                    
		                    if (data != null && data.length()>0) {
		                        res = data;
		                        break;
		                    }
		                }
		            }					
								
				} catch (Exception e1) {
					if(LOG_ENABLE)
						Log.e(TAG, "location_addressInfo(). Could not get information neither through Maps API [" + e.getMessage() + "].", e);
					
					return null;
				}
	        	
	        } catch(Exception e) {
	        	//Un-handled exception. Should never happen
	        	if(LOG_ENABLE)
					Log.e(TAG, "location_addressInfo(). Unknown exception [" + e.getMessage() + "].", e);
	        	
	        	return null;
	        }
        }
        
        return res;
    }	
	
	/**
	 * From a latitude and longitude, returns all the address information or 
	 * null in case of error or not found.
	 * <br><br>
	 * <b>NOTE</b>: This method uses the Android SDK Geocoder.
	 * 
	 * @param context
	 * @param lattitude
	 * @param longitude
	 * @return
	 */
	public static LocationInfo location_addressInfo(Context context, double latitude, double longitude) {

        LocationInfo res = null; 
        
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        if(gcd!=null) {
	        try {
	            List<Address> addresses = gcd.getFromLocation(latitude, longitude,10);
	
	            for (Address adrs : addresses) {
	            	if(res==null)
	            		res = new LocationInfo();
	            	
	                if (adrs != null) {                	
	                	
	                	res.setCountryCode(adrs.getCountryCode());
	                	res.setCountry(adrs.getCountryName());
	                	res.setCity(adrs.getLocality());
	                	res.setPostalCode(adrs.getPostalCode());
	                	if(adrs.getThoroughfare()!=null && adrs.getSubThoroughfare()!=null){
	                		res.setAddress(adrs.getThoroughfare() + ((adrs.getSubThoroughfare()!=null && adrs.getSubThoroughfare().trim().length()>0)?("," + adrs.getSubThoroughfare()):""));
						}						
						res.setAddressStreet(adrs.getThoroughfare());
						res.setAddressStreetNumber(adrs.getSubThoroughfare());
						
						if((res.getAddress()!=null && res.getAddress().length()>0) && 
							(res.getAddressStreet()!=null && res.getAddressStreet().length()>0) &&
							(res.getAddressStreetNumber()!=null && res.getAddressStreetNumber().length()>0) &&
							(res.getCity()!=null && res.getCity().length()>0) && 
							(res.getCountry()!=null && res.getCountry().length()>0)	&&
							(res.getCountryCode()!=null && res.getCountryCode().length()>0) && 
							(res.getPostalCode()!=null && res.getPostalCode().length()>0)){
							//If we have all data we stop iterating
							break;
						}
	                }
	            }
	        } catch (IOException e) {
	        	if(LOG_ENABLE)
					Log.e(TAG, "location_addressInfo(). Could not get information [" + e.getMessage() + "]. Trying with Maps API.", e);
	        	
	        	try {
					List<Address> addresses = location_addressInfoFromGoogleGeocodeAPI(latitude, longitude);
					
					for (Address adrs : addresses) {
		            	if(res==null)
		            		res = new LocationInfo();
		            	
		                if (adrs != null) {
		                	res.setCountryCode(adrs.getCountryCode());
		                	res.setCountry(adrs.getCountryName());
		                	res.setCity(adrs.getLocality());
		                	res.setPostalCode(adrs.getPostalCode());
		                	if(adrs.getThoroughfare()!=null && adrs.getSubThoroughfare()!=null){
		                		res.setAddress(adrs.getThoroughfare() + ((adrs.getSubThoroughfare()!=null && adrs.getSubThoroughfare().trim().length()>0)?("," + adrs.getSubThoroughfare()):""));
							}						
							res.setAddressStreet(adrs.getThoroughfare());
							res.setAddressStreetNumber(adrs.getSubThoroughfare());
							
							if((res.getAddress()!=null && res.getAddress().length()>0) && 
								(res.getAddressStreet()!=null && res.getAddressStreet().length()>0) &&
								(res.getAddressStreetNumber()!=null && res.getAddressStreetNumber().length()>0) &&
								(res.getCity()!=null && res.getCity().length()>0) && 
								(res.getCountry()!=null && res.getCountry().length()>0)	&&
								(res.getCountryCode()!=null && res.getCountryCode().length()>0) && 
								(res.getPostalCode()!=null && res.getPostalCode().length()>0)){
								//If we have all data we stop iterating
								break;
							}
		                }
		            }					
										
				} catch (Exception e1) {
					if(LOG_ENABLE)
						Log.e(TAG, "location_addressInfo(). Could not get information neither through Maps API [" + e.getMessage() + "].", e);
					
					return null;
				}
	            
	        } catch(Exception e) {
	        	//Un-handled exception. Should never happen
	        	if(LOG_ENABLE)
					Log.e(TAG, "location_addressInfo(). Unknown exception [" + e.getMessage() + "].", e);
	        	
	        	return null;
	        }
        }
        
        return res;
    }
	
	/**
	 * From a latitude and longitude, returns all the address information or 
	 * null in case not found.
	 * <br><br>
	 * <b>NOTE</b>: This method uses the Google Geocoding API. In some devices, the normal
	 * Android SDK Geocoder can fail. Any method "location_addressInfo" uses internally the
	 * Android SDK geocoder but in case of fail it will try to use this method to get data.
	 * 
	 * @param lattitude
	 * @param longitude
	 * @throws Exception In case of error.
	 * @return 
	 */
	@SuppressWarnings("deprecation")
	public static List<Address> location_addressInfoFromGoogleGeocodeAPI(double lattitude, double longitude) throws Exception {

		List<Address> retList = null;
		
		try{
		    String address = String
		            .format(Locale.ENGLISH, "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=true&language="
		                            + Locale.getDefault().getCountry(), lattitude, longitude);
		    HttpGet httpGet = new HttpGet(address);
		    HttpClient client = new DefaultHttpClient();
		    HttpResponse response;
		    StringBuilder stringBuilder = new StringBuilder();
	
		    response = client.execute(httpGet);
		    HttpEntity entity = response.getEntity();
		    InputStream stream = entity.getContent();
		    int b;
		    while ((b = stream.read()) != -1) {
		        stringBuilder.append((char) b);
		    }
		    
		    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
	
		    if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
		    	retList = new ArrayList<Address>();
		    	
		        JSONArray results = jsonObject.getJSONArray("results");
		        String indiStr = null;
		        String dLongName = null;
		        String dShortName = null;
		        String dType = null;
		        for (int i = 0; i < results.length(); i++) {
		            JSONObject result = results.getJSONObject(i);
		            
		            //Get parts of the information
		            indiStr = result.getString("formatted_address");
		            
		            Address addr = new Address(Locale.getDefault());
		            addr.setAddressLine(0, indiStr);
		            addr.setLatitude(lattitude);
		            addr.setLongitude(longitude);
		            
		            JSONArray addComponents = result.getJSONArray("address_components");
		            for (int j = 0; j < addComponents.length(); j++) {
		            	JSONObject addComponentData = addComponents.getJSONObject(j);
		            	dLongName = addComponentData.getString("long_name");
	            		dShortName = addComponentData.getString("short_name");
	            		JSONArray addComponentDataTypes = addComponentData.getJSONArray("types");
		            	for (int k = 0; k < addComponentDataTypes.length();) {
		            		dType = addComponentDataTypes.getString(k);
		            		break;
		            	}
		            	
		            	if(dType.equalsIgnoreCase("street_number")) {
			            	 addr.setSubThoroughfare(dLongName);
			            }else if(dType.equalsIgnoreCase("route")) {
			            	addr.setThoroughfare(dLongName);
			            }else if(dType.equalsIgnoreCase("locality")) {
			            	addr.setLocality(dLongName);
			            }else if(dType.equalsIgnoreCase("administrative_area_level_2")) {
			            	addr.setSubAdminArea(dLongName);
			            }else if(dType.equalsIgnoreCase("administrative_area_level_1")) {
			            	addr.setAdminArea(dLongName);
			            }else if(dType.equalsIgnoreCase("country")) {
			            	addr.setCountryCode(dShortName);
			            	addr.setCountryName(dLongName);
			            }else if(dType.equalsIgnoreCase("postal_code")) {
			            	addr.setPostalCode(dLongName);
			            }
		            }
		            
		            retList.add(addr);
		        }
		    }
		    
		}catch(Exception e){
			Log.e(TAG, "Error (location_addressInfoFromGoogleGeocodeAPI). Could not get data [" + e.getMessage() + "]", e);
			throw new Exception("Error (location_addressInfoFromGoogleGeocodeAPI). Could not get data [" + e.getMessage() + "]", e);
		}
		
		 return retList;
	}
	
	/**
	 * Gets the coordinates given an address or null if not found.
	 * <br><br>
	 * <b>NOTE</b>: This method uses the Google Geocoding API.
	 * 
	 * @param address
	 * @return
	 * @throws Exception In case of error.
	 */
	@SuppressWarnings("deprecation")
	public static LatLng location_addressLatLngFromGoogleGeocodeAPI(String address) throws Exception {
		try{
			HttpGet httpGet = new HttpGet(
		            "http://maps.google.com/maps/api/geocode/json?address="
		                    + URLEncoder.encode(address, "UTF-8") + "&ka&sensor=false");
		    
			HttpClient client = new DefaultHttpClient();
		    HttpResponse response;
		    StringBuilder stringBuilder = new StringBuilder();
		    
		    response = client.execute(httpGet);
		    HttpEntity entity = response.getEntity();
		    InputStream stream = entity.getContent();
		    int b;
		    while ((b = stream.read()) != -1) {
		    	stringBuilder.append((char) b);
		    }
		    
		    JSONObject jsonObject = new JSONObject(stringBuilder.toString());	
		    
		    if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
		    	double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
			            .getJSONObject("geometry").getJSONObject("location")
			            .getDouble("lng");	
			    double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
			            .getJSONObject("geometry").getJSONObject("location")
			            .getDouble("lat");
			    
			    return new LatLng(lat, lng);
		    }else{
		    	return null;
		    }
		    
		}catch(Exception e) {
			Log.e(TAG, "Error (location_addressLatLngFromGoogleGeocodeAPI). Could not get data [" + e.getMessage() + "]", e);
			throw new Exception("Error (location_addressLatLngFromGoogleGeocodeAPI). Could not get data [" + e.getMessage() + "]", e);
		}				
	}
	
	/**
	 * Calculates the great circle distance (distance in meters) between 
	 * two points on the Earth using the Haversine Formula.
	 * 
	 * 	http://en.wikipedia.org/wiki/Haversine_formula.
	 * 	http://es.wikipedia.org/wiki/F%C3%B3rmula_del_Haversine
	 * 
	 * @param latitude1 	Latitude of first location in decimal degrees.
	 * @param longitude1 	Longitude of first location in decimal degrees.
	 * @param latitude2 	Latitude of second location in decimal degrees.
	 * @param longitude2	Longitude of second location in decimal degrees.
	 * @return distance in meters.
	 */
	public static double location_distance(double latitude1, double longitude1, double latitude2, double longitude2) {
		
		final double eartRadiusKm = 6371;
		//final double eartRadiusMil = 3959;
		
		double earthRadius = eartRadiusKm * 1000; //Mts (3959 mil)
		
		double latitudeSin = Math.sin(Math.toRadians(latitude2 - latitude1) / 2);
		double longitudeSin = Math.sin(Math.toRadians(longitude2 - longitude1) / 2);
		double a = latitudeSin * latitudeSin 
								+ Math.cos(Math.toRadians(latitude1)) 
								* Math.cos(Math.toRadians(latitude2)) 
								* longitudeSin	
								* longitudeSin;
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		return earthRadius * c;
	}
	
	/**
	 * Calculates the great circle distance (distance in meters) between 
	 * two points on the Earth using:<br>
	 * <ul>
	 * <li>The native Android Location distanceTo. Distance is defined 
	 * using the WGS84 ellipsoid (see <a href="https://en.wikipedia.org/wiki/World_Geodetic_System">WGS84</a>). 
	 * </li>
	 * <li>The Haversine Formula. See <a href="http://en.wikipedia.org/wiki/Haversine_formula">Haversine Wiki</a></li>
	 * </ul>
	 * 
	 * @param locFrom	The start point
	 * @param locTo		The end point
	 * @param haversine	Set to TRUE to use the Haversine method.
	 * @return	The distance in meters between the two points. If one of the points 
	 * 			is null 0 is returned.
	 */
	public static float location_distance(Location locFrom, Location locTo, boolean haversine) {
		if(locFrom==null || locTo==null) {
			return 0f;
		}
		
		if(haversine){
			return (float) location_distance(locFrom.getLatitude(), locFrom.getLongitude(), 
								locTo.getLatitude(), locTo.getLongitude());			
		}else{
			return locFrom.distanceTo(locTo);
		}
	}
	
	/**
	 * Returns TRUE if location service is enabled (GPS or WiFi/radio).
	 * 
	 * @param context
	 * @return
	 */
	public static boolean location_checkAvalibility(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager!=null) {
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);	    
			if(isGPSEnabled || isNetworkEnabled) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get the last known location or null if is not available.
	 * 
	 * @param context
	 * @return
	 */
	public static Location location_getLastKnownLocation(Context context, String locationProvider) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager!=null) {
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);	    
			if(isGPSEnabled || isNetworkEnabled) {
				return locationManager.getLastKnownLocation(locationProvider);				
			}
		}
		
		return null;
	}
	
	/**
	 * Creates a circular area, or fence, around the location of interest.
	 * <br><br>
	 * Geofencing combines awareness of the user's current location with 
	 * awareness of the user's proximity to locations that may be of 
	 * interest. You can have multiple active geofences, with a limit of 
	 * 100 per device user.<br>
	 * <br>
	 * See Geofences at <a href="http://developer.android.com/intl/es/training/location/geofencing.html">Google developer</a>.
	 * <br><br>
	 * Note:<br><br>
	 * 
	 * Requires the permission {@link android.permission.ACCESS_FINE_LOCATION}.
	 * 
	 * @param name				The name of the Geofence.
	 * @param latitute			The latitude.
	 * @param longitude			The longitude.
	 * @param radius			To adjust the proximity for the location.
	 * @param expirationMillis	After this time geofence expires. If expiration 
	 * 							is not needed, use {@link Geofence#NEVER_EXPIRE}.
	 * @return
	 */
	public static Geofence location_geofencesCreate(String name, 
			double latitute, double longitude, float radius,
			long expirationMillis) {
		
		return new Geofence.Builder()
	    // Set the request ID of the geofence. This is a string to identify this
	    // geofence.
	    .setRequestId(name)
	    .setCircularRegion(latitute,longitude,radius)
	    .setExpirationDuration(expirationMillis)
	    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
	    					Geofence.GEOFENCE_TRANSITION_EXIT)
	    .build();
	}
	
	/**
	 * Creates a circular area, or fence, around the location of interest.
	 * <br><br>
	 * Geofencing combines awareness of the user's current location with 
	 * awareness of the user's proximity to locations that may be of 
	 * interest. You can have multiple active geofences, with a limit of 
	 * 100 per device user.<br>
	 * <br>
	 * See Geofences at <a href="http://developer.android.com/intl/es/training/location/geofencing.html">Google developer</a>.
	 * <br><br>
	 * Note:<br><br>
	 * 
	 * Requires the permission {@link android.permission.ACCESS_FINE_LOCATION}.
	 * 
	 * @param name				The name of the Geofence.
	 * @param location			The location.
	 * @param radius			To adjust the proximity for the location.
	 * @param expirationMillis	After this time geofence expires. If expiration 
	 * 							is not needed, use {@link Geofence#NEVER_EXPIRE}.
	 * @return
	 */
	public static Geofence location_geofencesCreate(String name,
			Location location, float radius,
			long expirationMillis) {
		return location_geofencesCreate(name, location.getLatitude(), location.getLongitude(), 
				radius, expirationMillis);
	}
	
	/**
	 * Creates a geofencing trigger request. This will make the location service
	 * to launch geofencing events (enter/exit events) with the specified
	 * geofence list.
	 * <br>
	 * See Geofences at <a href="http://developer.android.com/intl/es/training/location/geofencing.html">Google developer</a>.
	 * <br><br>
	 * Note:<br><br>
	 * 
	 * Requires the permission {@link android.permission.ACCESS_FINE_LOCATION}.
	 * 
	 * @param geofenceList	The list of Geofences to watch.
	 */
	public static GeofencingRequest location_geofencesCreateTriggerRequest(List<Geofence> geofenceList) {
		GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
		//The GEOFENCE_TRANSITION_ENTER/GEOFENCE_TRANSITION_EXIT transition 
		//triggers when a device enters/exits a geofence.
		//
		//Specifying INITIAL_TRIGGER_ENTER tells Location services that 
		//GEOFENCE_TRANSITION_ENTER should be triggered if the the device is 
		//already inside the geofence.
		//
		//In many cases, it may be preferable to use instead INITIAL_TRIGGER_DWELL,
		//which triggers events only when the user stops for a defined duration 
		//within a geofence.
	    builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
	    builder.addGeofences(geofenceList);	    
	    return builder.build();
	}
	
	/**
	 * Creates a pending intent to be launched once location service detects
	 * that user enters/exists a Geofence. This intent is received by the 
	 * specified service in order to process the geofencing enter/exit events.	 * 
	 * <br>
	 * See Geofences at <a href="http://developer.android.com/intl/es/training/location/geofencing.html">Google developer</a>.
	 * <br><br>
	 * Note:<br><br>
	 * 
	 * Requires the permission {@link android.permission.ACCESS_FINE_LOCATION}.
	 * @param context
	 * @param service
	 * @return
	 */
	public static PendingIntent location_geofencesCreatePendingIntent(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(context, 0, intent, PendingIntent. FLAG_UPDATE_CURRENT);
	}
	
	/**
	 * Makes the location service to watch for any location change
	 * that could interact with the geofences set in the parameter   
	 * "geofencingRequest".<br> 
	 * <br>
	 * See Geofences at <a href="http://developer.android.com/intl/es/training/location/geofencing.html">Google developer</a>.
	 * <br><br>
	 * Note:<br><br>
	 * 
	 * Requires the permission {@link android.permission.ACCESS_FINE_LOCATION}.
	 * 
	 * @param gApiClient			The Google API client. You can use {@link #googleAPI_getApiClient}.
	 * See <a href="http://developer.android.com/intl/es/training/location/retrieve-current.html#play-services">Google Play Services</a>
	 * @param geofencingRequest		The geofencing trigger request. You can use {@link #location_geofencesCreateTriggerRequest}.
	 * @param pendingIntent			The intent that is launched once a geofencing event occurs. You can use {@link #location_geofencesCreatePendingIntent}.
	 * @param resultCallback		The class that processes the result of this call. See 
	 * <a href="https://developers.google.com/android/reference/com/google/android/gms/common/api/ResultCallback">resultCallback</a>
	 * 
	 */
	public static void location_geofencesAddAwareness(GoogleApiClient gApiClient, 
			GeofencingRequest geofencingRequest,
			PendingIntent pendingIntent,
			ResultCallback<? super Status> resultCallback) {
		
		LocationServices.GeofencingApi.addGeofences(
				gApiClient,
				geofencingRequest,
				pendingIntent
        ).setResultCallback(resultCallback);
	}
	
	/**
	 * Makes the location service to stop watching for location changes
	 * that could interect with the geofences in your application.
	 * <br>
	 * See Geofences at <a href="http://developer.android.com/intl/es/training/location/geofencing.html">Google developer</a>.
	 * <br><br>
	 * Note:<br><br>
	 * 
	 * Requires the permission {@link android.permission.ACCESS_FINE_LOCATION}.
	 * 
	 * @param gApiClient			The Google API client. You can use {@link #googleAPI_getApiClient}.
	 * See <a href="http://developer.android.com/intl/es/training/location/retrieve-current.html#play-services">Google Play Services</a>
	 * @param pendingIntent			The intent that is launched once a geofencing event occurs. You can use {@link #location_geofencesCreatePendingIntent}.
	 * @param resultCallback		The class that processes the result of this call. See 
	 * <a href="https://developers.google.com/android/reference/com/google/android/gms/common/api/ResultCallback">resultCallback</a>
	 */
	public static void location_geofencesRemoveAwareness(GoogleApiClient gApiClient, 
			PendingIntent pendingIntent,
			ResultCallback<? super Status> resultCallback) {
		
		LocationServices.GeofencingApi.removeGeofences(
				gApiClient,	            
				pendingIntent
	    ).setResultCallback(resultCallback);
	}
	
		
	
	//-------------------- CRYPTO ------------------------------------------------------------------------
 	 
	/**
	 * Generates a HASH from the specified byte array.
	 * 
	 * @param data	The data to get the HASH from.
	 * @param hashType	{@link HASH_TYPE}
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String crypto_getHASH(byte[] data, HASH_TYPE hashType){
		 
		 MessageDigest digest = null;
		 byte[] resData = null;
		 
		 try{
			 switch(hashType){
				 case md5:
					 digest = MessageDigest.getInstance("MD5");
					 resData = digest.digest(data);
					 break;
				 case sha1:
					 digest = MessageDigest.getInstance("SHA-1");
					 resData = digest.digest(data);
					 break;				 
			 }
			 
			 if(resData!=null)
				 return new String(Hex.encodeHex(resData));
			 else
				 return null;
			 
		 }catch(Exception e){
			 if(LOG_ENABLE)
				 Log.e("TollBox_ERROR","crypto_getHASH() Error getting HASH data: " + e.getMessage(),e);
			 
			 return null;
		 }
	}
	
	//-------------------- STRICTMODE--------------------------------------------------------------------
	
	/**
	 * Use this method to get warned in the logcat when an 
	 * operation that should be done outside of the UI thread 
	 * is done in it. A sympton of this:
	 * <br><br>
	 * “Choreographer(abc): Skipped xx frames! The application may be doing too much work on its main thread.”
	 * <br><br>
	 * It also stablishes the VmPolicy detecting leaked SQL
	 * object and leaked closable objects with the penalty of
	 * logging them and killing the whole process on violation.
	 * <br><br>
	 * See: <a href="http://developer.android.com/intl/es/reference/android/os/StrictMode.html">StricMode</a>
	 * <br><br>
	 * <b>Note</b>: This method requires API level 11 or greater.
	 * 
	 * @param all	If set to TRUE, all detectable problems are watched.
	 * 				If set to FALSE, only disk read/write and network 
	 * 				operations are watched.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void enableStrictMode(boolean all) {
		
		//ThreadPolicy tPolicy = new StrictMode.ThreadPolicy.Builder();
		StrictMode.ThreadPolicy.Builder tPolicyBuilder = new StrictMode.ThreadPolicy.Builder();
		if(all) {
			tPolicyBuilder.detectAll();
		}else{
			tPolicyBuilder.detectDiskReads()
				.detectDiskWrites()
        		.detectNetwork()
        		.penaltyLog();
		}        
		StrictMode.setThreadPolicy(tPolicyBuilder.build());
		
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		        .detectLeakedSqlLiteObjects()
		        .detectLeakedClosableObjects()
		        .penaltyLog()
		        .penaltyDeath()
		        .build());
				
		Log.i(TAG, "StrictMode enabled!");
	}
	
	//-------------------- THREAD-----------------------------------------------------------------------
	
	/**
	 * No long term process should be done in the main application
	 * UI thread. This method checks if the current current thread 
	 * is the main application UI thread.
	 * 
	 * @return
	 */
	public static boolean threadIsMainApplicationUIThread() {
		if(Looper.getMainLooper().getThread() == Thread.currentThread()) {
			return true;
		}else{
			return false;
		}
	}
	
	//-------------------- FONTS ------------------------------------------------------------------------
	 
	/**
	 *	Set a font to a text view.
	 * 
	 * @param context
	 * @param textView
	 * @param fontPath		The path to the font resource. Must be placed in asset 
	 * 					folder.
	 */
	public static void font_applyTitleFont(Context context, TextView textView, String fontPath) {
		 if (textView != null) {
			 Typeface font = Typeface.createFromAsset(context.getAssets(),fontPath);
			 textView.setTypeface(font);
		 }
	}
	
	//-------------------- Google API -------------------------------------------------------------------
	
	/**
	 * Gets a Google API client for the desired Google API.<br><br>
	 * 
	 * See:<br><br>
	 * <a href="https://developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient">Google API Client</a><br>
	 * <a href="https://developers.google.com/android/guides/setup">Google API Client Setup</a>
	 * 
	 * @param context	The context for this Google API client.
	 * @param connectionCallback		Processes connection results.
	 * @param connectionFailListener	The listener that processes connection fails. 
	 * @param requestedApi				The Google API to request.
	 * @return GoogleApiClient			The Google API client.
	 */
	public static GoogleApiClient googleAPI_getApiClient(Context context, 
			GoogleApiClient.ConnectionCallbacks connectionCallback,
			GoogleApiClient.OnConnectionFailedListener connectionFailListener,
			Api<? extends Api.ApiOptions.NotRequiredOptions> requestedApi) {
		
		return new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(connectionCallback)
        .addOnConnectionFailedListener(connectionFailListener)
        .addApi(requestedApi)
        .build();
	}
	
	//---------------------- Tasks -------------------------------------------------------------------
	
	/**
	 * An utility method to perform a job in a separated thread.
	 *  
	 * @param runnable	The job to do in a separate thread
	 * @return
	 */
    public static Thread task_doInBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try { runnable.run(); } finally {}
            }
        };
        t.start();
        
        return t;
    }
}
