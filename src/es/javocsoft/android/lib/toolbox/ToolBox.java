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
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

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
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PowerManager;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
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
import com.google.gson.Gson;

import es.javocsoft.android.lib.toolbox.encoding.Base64;
import es.javocsoft.android.lib.toolbox.io.IOUtils;


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

	public static enum ApiLevel {
		
		LEVEL_1(1), LEVEL_2(2), LEVEL_3(3), LEVEL_4(4), LEVEL_5(5), LEVEL_6(6),
		LEVEL_7(7), LEVEL_8(8), LEVEL_9(9), LEVEL_10(10), LEVEL_11(11), LEVEL_12(12),
		LEVEL_13(13), LEVEL_14(14), LEVEL_15(15), LEVEL_16(16), LEVEL_17(17), LEVEL_18(18),
		LEVEL_19(19), LEVEL_20(20), LEVEL_21(21), LEVEL_22(22);

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
	
	
	private ToolBox(){}
	
	
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
     public static void toast_createCustomToast(Activity context, String message, TOAST_TYPE type, boolean centerOnScreen) {
    	 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    	 View linearLayout = inflater.inflate(R.layout.toast_view, (ViewGroup) context.findViewById(R.id.toast_layout_root));
    	 
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
		 
		AlertDialog dialog = new AlertDialog.Builder(context).create();
			
		dialog.setTitle(title);			
		dialog.setMessage(message);
		
		dialog.setCancelable(true);			
		dialog.setButton(AlertDialog.BUTTON_NEUTRAL, neutralBtnText,
		new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
		    	if(neutralBtnActions!=null){
		    		neutralBtnActions.run();
		    	}
		    	dialog.dismiss();		      
		    }
		});		
		
		if(negativeBtnActions!=null){
			dialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeBtnText,
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			    	negativeBtnActions.run();		      
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
	 public static void dialog_showToastAlert(Activity context, String message, 
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
	  * settings the GPS.
	  * 
	  * @param context	The activity that opens the dialog.
	  * @param message	Optional.
	  * @param okButtonText	Optional.
	  * @param cancelButtonText	Optional.
	  */
	 public static void dialog_showGPSDisabledAlert(final Activity context, String message, String okButtonText, String cancelButtonText) {		 
		 
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
		
	/*
	 * Opens system setting in location path.
	 */
	 private static void showAndroidGpsOptions(final Activity context) {
		 Intent gpsOptionsIntent = new Intent(
	              android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	     context.startActivityForResult(gpsOptionsIntent, ENABLE_GPS_REQUEST);
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
    	
    	try{
    		
    		//1.- Prepare the intent that is launched by the notification
    		//
    		//This is the intent that runs in case the user clicks on the notification
    		Intent notificationIntent = new Intent(context, notClazz);    		
	        notificationIntent.setAction(notClazz.getName()+"."+notAction);
	        //We save in the intent all the info received.
	        if(extras!=null){
	        	notificationIntent.putExtras(extras);
	        }	        
	       	 
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
    		
    
    /*
     * Gets the application Icon.
     *  
     * @param context
     * @return
     * @throws ApplicationPackageNotFoundException
     */
    private static int notification_getApplicationIcon(Context context) throws Exception{
    	try {
			ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
			return app.icon;
			
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
	 * action is consumed.
	 * 
	 * @param iconId
	 * @param title
	 * @param actionIntent		
	 * @param notificationId	Used to be able to cancel a notification once
	 * 							the action is consumed.
	 * @return
	 */
	public static Action notification_createAction(int iconId, String title, PendingIntent actionIntent, int notificationId) {
		Action action = new Action(iconId, title, actionIntent);
		action.getExtras().putInt(NOTIFICATION_ID, notificationId);
		
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
	
	/**
	 * Permissions required to interact with the calendar.
	 */
    public static final Map<String, String> PERMISSION_CALENDAR;
    static {
    	PERMISSION_CALENDAR = new HashMap<String, String>();
    	PERMISSION_CALENDAR.put("android.permission.READ_CALENDAR", "Calendar");
    	PERMISSION_CALENDAR.put("android.permission.WRITE_CALENDAR", "Calendar");
    }
    /** 
     * Permissions required to interact with the camera
     */
    public static final Map<String, String> PERMISSION_CAMERA;
    static {
    	PERMISSION_CAMERA = new HashMap<String, String>();
    	PERMISSION_CAMERA.put("android.permission.CAMERA", "Camera");    	
    }
    /**
     * Permissions required to interact with the location services
     */
    public static final Map<String, String> PERMISSION_LOCATION;
    static {
    	PERMISSION_LOCATION = new HashMap<String, String>();
    	PERMISSION_LOCATION.put("android.permission.ACCESS_COARSE_LOCATION", "Location");
    	PERMISSION_LOCATION.put("android.permission.ACCESS_FINE_LOCATION", "Location");
    }
    /**
     * Permissions required to interact with the microphone
     */
    public static final Map<String, String> PERMISSION_MICROPHONE;
    static {
    	PERMISSION_MICROPHONE = new HashMap<String, String>();
    	PERMISSION_MICROPHONE.put("android.permission.RECORD_AUDIO", "Microphone");    	
    }
    /**
     * Permissions required to interact with the phone functions like calls,
     * voice mail, call log, etc.
     */
    public static final Map<String, String> PERMISSION_PHONE;
    static {
    	PERMISSION_PHONE = new HashMap<String, String>();
    	PERMISSION_PHONE.put("android.permission.READ_PHONE_STATE", "Phone");
    	PERMISSION_PHONE.put("android.permission.CALL_PHONE", "Phone");
    	PERMISSION_PHONE.put("android.permission.READ_CALL_LOG", "Phone");
    	PERMISSION_PHONE.put("android.permission.WRITE_CALL_LOG", "Phone");
    	PERMISSION_PHONE.put("android.permission.ADD_VOICEMAIL", "Phone");
    	PERMISSION_PHONE.put("android.permission.USE_SIP", "Phone");
    	PERMISSION_PHONE.put("android.permission.PROCESS_OUTGOING_CALLS", "Phone");    	    	
    }
    /**
     * Permissions required to interact with the device sensors
     */
    public static final Map<String, String> PERMISSION_SENSORS;
    static {
    	PERMISSION_SENSORS = new HashMap<String, String>();
    	PERMISSION_SENSORS.put("android.permission.BODY_SENSORS", "Sensors");    	    	    	
    }
    /**
     * Permissions required to interact with the SMS/WAP/MMS 
     * device capabilities. 
     */
    public static final Map<String, String> PERMISSION_SMS;
    static {
    	PERMISSION_SMS = new HashMap<String, String>();
    	PERMISSION_SMS.put("android.permission.SEND_SMS", "SMS");
    	PERMISSION_SMS.put("android.permission.RECEIVE_SMS", "SMS");
    	PERMISSION_SMS.put("android.permission.READ_SMS", "SMS");
    	PERMISSION_SMS.put("android.permission.RECEIVE_WAP_PUSH", "SMS");
    	PERMISSION_SMS.put("android.permission.RECEIVE_MMS", "SMS");    	    	    	
    }
    /**
     * Permissions required to interact with the storage service.
     */
    public static final Map<String, String> PERMISSION_STORAGE;
    static {
    	PERMISSION_STORAGE = new HashMap<String, String>();
    	PERMISSION_STORAGE.put("android.permission.READ_EXTERNAL_STORAGE", "Storage");
    	PERMISSION_STORAGE.put("android.permission.WRITE_EXTERNAL_STORAGE", "Storage");    	    	    	    	
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
	 * @param pList		The list of permissions to be granted. Format: Map of permission,permission_name
	 * @param requestCode	The request code to be checked in onRequestPermissionsResult of the activity.
	 * @param dialogTitle	If there are permissions to be granted, a dialog pop-ups, set here the title.
	 * @param dialogAcceptBtnText If there are permissions to be granted, a dialog pop-ups, set here 
	 * 							  the accept button text.
	 * @param dialogText If there are permissions to be granted, a dialog pop-ups, set here the text that
	 * 					 presents the permission that need to be granted.
	 * @param showRationaleDialog By default, a rationale dialog explaining why permissions are required
	 * 							  is only showed once user denies a permission for the application. If
	 * 							  this parameter is set to TRUE, the dialog will always appear unless user
	 * 							  checked "Never ask again" checkbox.
	 * @param showRememberPermissions If set to TRUE, if user check "Never ask again" checkbox, a rationale
	 * 								  pop-up with cusomizable text will appear (usualy used to remember to 
	 * 								  the user that enabling permissions should be the best).
	 * @param rememberPermissionsText The text to show in case showRememberPermissions parameter is TRUE.
	 */
	public static void permission_askFor (final Activity context, final Map<String, String> pList, 
			final int requestCode, String dialogTitle, String dialogAcceptBtnText, String dialogDenyBtnText,
			String dialogText, boolean showRationaleDialog, boolean showRememberPermissions,
			String rememberPermissionsText) {
    	
		//We only check if not all permissions are granted and we are above/equal 23 API level.
		if(device_getAPILevel()<23 || 
				(device_getAPILevel()>=23 && permission_areGranted(context, pList.keySet())) ) {
			return;
		}
				
    	final List<String> permissionsList = new ArrayList<String>();
    	List<String> permissionsNeeded = new ArrayList<String>();
    	
    	//Ask for permissions
    	Set<String> keys = pList.keySet();
    	for(String p:keys) {
    		if (permission_add(context, permissionsList, p)) {
    			if(!permissionsNeeded.contains(pList.get(p)))
    				permissionsNeeded.add(pList.get(p)); //We add the name
    		}
    	}
    	
    	if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
            	//Once the user denies some permissions, we show a dialog asking for 
            	//them with an explanation before show Android ask dialog.
            	//
            	// Need Rationale (explanation before ask for permissions)
            	showDescriptivePermissionsDialog(context, permissionsList, permissionsNeeded, 
            			requestCode, dialogTitle, dialogAcceptBtnText, dialogDenyBtnText, dialogText);
            	return;
            }
            
            //We can choose to show always a rationale dialog explaining why we need the permissions.
            if(showRationaleDialog) {
            	if(permissionsNeeded.size()>0) {
            		showDescriptivePermissionsDialog(context, permissionsList, permissionsNeeded, 
	            			requestCode, dialogTitle, dialogAcceptBtnText, dialogDenyBtnText, dialogText);
            	}else{
            		//If user checked "Never ask again", we should not ask for them again.
            		//
            		//If we want, we show a rationale dialog with a custom text to remember 
            		//him that enabling permissions is the best.
            		if(showRememberPermissions) {
            			ToolBox.dialog_showCustomActionsDialog(context, 
            	        		dialogTitle, rememberPermissionsText, 
            	        		"OK", new Runnable() {
            						
            						@Override
            						public void run() {
            														
            						}
            					}, 
            					null, null, 
            	        		null, null);
            		}
            	}
            }else{
	            //Show directly the permissions approval
	            ActivityCompat.requestPermissions(context, 
	            		permissionsList.toArray(new String[permissionsList.size()]),
	            		requestCode);
            }
            
            return;
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
	
	/**
	 * Checks the results of an Android 6+ permissions ask. Returns TRUE only if 
	 * all permissions are granted.
	 * 
	 * @param permissions	Permissions asked.
	 * @param grantResults	Results of the ask.
	 * @return TRUE/FALSE
	 */
	public static boolean permission_checkAskPermissionsresult(String[] permissions, int[] grantResults) {
    	
    	boolean res = false;
    		
    	// Create the list with the results
    	Map<String, Integer> perms = new HashMap<String, Integer>();    		
        for (int i = 0; i < permissions.length; i++) {
        	perms.put(permissions[i], grantResults[i]);
        }            
            
        // Check if all permissions are granted
        Set<String> pNameList = perms.keySet();
        for(String pName:pNameList) {
        	if(perms.get(pName) == PackageManager.PERMISSION_GRANTED){
        		res = true;        		
            }else{
            	res = false;
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
	 * Checks a permission list. If a permission needs to be granted, it returns TRUE.
	 * 
	 * @param context	An activity context
	 * @param permissionsList	Adds to this list the processed permission for afterwards usage.
	 * @param permission	The permission to check.
	 * @return	TRUE/FALSE
	 */
	private static boolean permission_add(Activity context, List<String> permissionsList, String permission) {
		if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option. To know if we need to show a message
            // telling to the user why we need this permissions.
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission))
                return false;
        }
        return true;
    }
	
	
    // Power saving ----------------------------------------------------------------------------------------------------------------------------
    
    /*
     * Makes the device to wake-up. yeah!
     * 
     * Requires the permission android.permission.WAKE_LOCK
     *  
     * @param ctx
     */
    public static void powersaving_wakeUp(Context ctx) {
    	    	
    	if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "javocsoft_library_wakeup");
        wakeLock.acquire();
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
    	String responseData = null;
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
    	
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
    	    
    	    if(LOG_ENABLE)
    	    	Log.i(TAG, "HTTP OPERATION: Read from server - return: " + responseData);
    	}
    	
    	if (response.getStatusLine().getStatusCode() != 200) {
    		throw new Exception("Http operation "+method.name()+" failed with error code " + 
    				response.getStatusLine().getStatusCode() + "("+ 
    				response.getStatusLine().getReasonPhrase() +")");
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
		return net_httpclient_doAction(method, url, null, jsonData, headers);
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
	
	public static boolean prefs_savePreference(Context ctx, String prefName, String key, Class<?> valueType, Object value){
		boolean res = false;
		
		SharedPreferences prefs = ctx.getSharedPreferences(
				prefName, Context.MODE_PRIVATE);
		
		if(value==null){
			prefs.edit().remove(key).commit();
		}else{
			if(valueType == Long.class){
				res = prefs.edit().putLong(key, (Long)value).commit();
			}else if(valueType == Boolean.class){
				res = prefs.edit().putBoolean(key, (Boolean)value).commit();
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
	
	public static Object prefs_readPreference(Context ctx, String prefName, String key, Class<?> valueType){
		SharedPreferences prefs = ctx.getSharedPreferences(
				prefName, Context.MODE_PRIVATE);
		
		if(valueType == Long.class){
			return prefs.getLong(key, Long.valueOf(-1));
		}else if(valueType == Boolean.class){
			return prefs.getBoolean(key, Boolean.valueOf(false));
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
		
		if(prefs.contains(key)){
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
	        player.start();
	        
	    } catch (Exception e) {
	    	if(LOG_ENABLE){
	    		Log.e(TAG, "Error playing sound: '" + assetSoundPath + "' (" + e.getMessage() + ")", e);
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
	public static String device_getId(Context context) {
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
	 * Get the device language (two letter code value)
	 * @return
	 */
	public static String device_getLanguage(){
		return Locale.getDefault().getLanguage();
	}
	
	
	/**
	 * Get the current android API Level.
	 * 
	 * Android 4.2        17
	 * Android 4.1        16
	 * Android 4.0.3      15
	 * Android 4.0        14
	 * Android 3.2        13
	 * Android 3.1        12
	 * Android 3.0        11
	 * Android 2.3.3      10
	 * Android 2.3        9
	 * Android 2.2        8
	 * Android 2.1        7
	 * Android 2.0.1      6
	 * Android 2.0        5
	 * Android 1.6        4
	 * Android 1.5        3
	 * Android 1.1        2
	 * Android 1.0        1
	 * 
	 * @return
	 */
	public static int device_getAPILevel(){
		return Build.VERSION.SDK_INT;
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
	 * Gets the device EMEI if is available, NULL otherwise.
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
	
	
	/** The available latitude and longitude address information types. */
	public static enum LOCATION_INFO_TYPE {COUNTRY, COUNTRY_CODE, CITY, POSTAL_CODE, ADDRESS, ALL}; 
	
	/**
	 * From a latitude and longitude, return the desired address information type
	 * or null in case of error.
	 * 
	 * @param context
	 * @param locationInfoType	The desired location info. See LOCATION_INFO_TYPE enum.
	 * @param lattitude
	 * @param longitude
	 * @return
	 */
	public static String location_addressInfo(Context context, LOCATION_INFO_TYPE locationInfoType, 
			double latitude, double longitude) {

        String res = "Not Found"; 
        
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
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
							data = adrs.getSubThoroughfare();
							break;	
						case ALL:	
							res = adrs.toString();
							break;
					}                    
                    if (data != null && data.length()>0) {
                        res = data;         
                    }
                }
            }
        } catch (IOException e) {
            return null;
        }
        
        return res;
    }	
	
	/**
	 * Calculates the great circle distance between two points on the Earth. 
	 * Uses the Haversine Formula.
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
		
		double earthRadius = 6378137;
		
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
		
	
	//-------------------- CRYPTO ------------------------------------------------------------------------
 	 
	/**
	 * Generates a HASH from the specified byte array.
	 * 
	 * @param data	The data to get the HASH from.
	 * @param hashType	{@link HASH_TYPE}
	 * @return
	 */
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
	 
}
