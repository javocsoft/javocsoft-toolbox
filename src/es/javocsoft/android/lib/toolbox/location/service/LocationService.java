/*
 * Copyright (C) 2010-2016 - JavocSoft - Javier Gonzalez Serrano
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
package es.javocsoft.android.lib.toolbox.location.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.LocationInfo;

/**
 * A service that runs in background to watch for location changes.
 * <br><br>
 * This service informs to an application about these events:
 * <ul>
 * 	 <li><b>LOCATION_SERVICE_STARTED</b>. Intent filter name: <i>es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_SERVICE_STARTED</i></li>
 *   <li><b>LOCATION_SERVICE_SHUTDOWN</b>. Intent filter name: <i>es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_SERVICE_SHUTDOWN</i></li>
 *   <li><b>LOCATION_CHANGED</b>. Intent filter name: <i>es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_CHANGED</i></li>
 *   <li><b>LOCATION_GPS_ENABLED</b>. Intent filter name: <i>es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_GPS_ENABLED</i></li>
 *   <li><b>LOCATION_GPS_DISABLED</b>. Intent filter name: <i>es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_GPS_DISABLED</i></li>
 * </ul>
 * 
 * When a location change happens, a broadcast is sent, this broadcast will contain in its bundle:<br>
 * <ul>
 * <li>A {@link Location} in the bundle under the key LOCATION_KEY</li>
 * <li>A set of extra location information:
 * <ul>
 * 	<li>The country, under the key LOCATION_COUNTRY_KEY</li>
 *  <li>The country code, under the key LOCATION_COUNTRY_CODE_KEY</li>
 *  <li>The city, under the key LOCATION_CITY_KEY</li>
 *  <li>The address line, under the key LOCATION_ADDRESS_KEY</li>
 *  <li>The postal code, under the key LOCATION_POSTAL_CODE_KEY</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * Declare the localization service in your AndroidManifest.xml:<br>
 * <code>
 * 	&lt;service android:name="es.javocsoft.android.lib.toolbox.location.service.LocationService" 
 *	     	 android:label="Location Service"
 *	     	 android:enabled="true"/&gt;
 * </code><br><br>
 * 
 * And implement in your application a receiver that listens for the desired events in order to 
 * react to them properly.
 * <br>
 * <code>
 * &lt;receiver android:name="your_application_package.LocationReceiver"
 *		    	  android:enabled="true" 
 *		    	  android:exported="false"/&gt;
 *		    &lt;intent-filter/&gt;
 *               &lt;action android:name="es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_SERVICE_STARTED" /&gt;
 *               &lt;action android:name="es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_SERVICE_SHUTDOWN" /&gt;
 *               &lt;action android:name="es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_CHANGED" /&gt;
 *               &lt;action android:name="es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_GPS_ENABLED" /&gt;
 *               &lt;action android:name="es.javocsoft.android.lib.toolbox.location.service.intent.action.LOCATION_GPS_DISABLED" /&gt;
 *           &lt;/intent-filter/&gt;            
 *	&lt;/receiver/&gt;
 * </code>
 * 
 * <br><br>
 * <b>Notes</b><br>
 * <ul>
 * <li>The localization service can be customized when starting by setting:
 *   <ul>
 * 		<li>Time between localization changes. Default is 4 seconds (4000 milliseconds).</li>
 * 		<li>Distance (in meters) between localization changes. Default is 2 meters.</li>
 * 	    <li>Accuracy change threshold (in meters). Default is 0 meters.</li>
 * 		<li>Use also the GPS, if available, or just the Wi-Fi and Radio signals. If not set GPS is not used.</li>
 * 		<li>The algorithm type (an String) to determine if a new location is better than 
 * 			than the previous one. We can choose between SIMPLE or COMPLEX, 
 * 			see {@link LOCATION_ALGORITHM_TYPE}. If not set, SIMPLE is used.
 * 			<ul>
 * 				<li>SIMPLE. Choose this if we want only to be alerted every time me move the
 * 					specified distance in the parameter LOCATION_SERVICE_PARAM_MIN_DISTANCE.</li>
 * 				<li>COMPLEX. Choose this when we want also to check the accuracy and the age 
 * 					of locations and not only the distance.</li>
 * 			</ul>
 * 		</li>
 *   </ul>  
 *   To set these values, set them through the service starting intent by 
 *   using these keys in the bundle:
 *   <ul>
 *     <li>LOCATION_SERVICE_PARAM_MIN_DISTANCE</li> 
 * 	   <li>LOCATION_SERVICE_PARAM_MIN_TIME</li>
 * 	   <li>LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD</li>
 * 	   <li>LOCATION_SERVICE_PARAM_USE_GPS</li>
 *     <li>LOCATION_SERVICE_PARAM_ALGORITHM</li>
 *   </ul>
 *  </li> 
 *  <li>If service gets stopped, it will automatically run again.</li>
 * </ul>
 * 
 * <br>See:<br><br>
 * Services<br>
 *  http://developer.android.com/intl/es/reference/android/app/Service.html<br>
 *  http://www.vogella.com/tutorials/AndroidServices/article.html<br>
 *  http://developer.android.com/intl/es/training/run-background-service/send-request.html<br><br>
 *  
 *  
 * Location<br>
 * 	http://developer.android.com/guide/topics/location/strategies.html<br>
 *  http://developer.android.com/intl/es/reference/android/location/LocationManager.html<br>
 *  http://developer.android.com/intl/es/reference/android/location/LocationListener.html<br>
 * 
 * 
 * @author JavocSoft 2013
 * @version 2.0<br>
 * $Rev$<br>
 * $LastChangedDate$<br>
 * $LastChangedBy$
 *
 */
public class LocationService extends Service implements LocationListener {

	private static final String TAG = ToolBox.TAG + " : " + "Location Service";
	
	public static final String LOCATION_SERVICE_PARAM_MIN_DISTANCE = "LOCATION_UPDATE_MIN_DISTANCE";
	public static final String LOCATION_SERVICE_PARAM_MIN_TIME = "LOCATION_UPDATE_MIN_TIME";
	public static final String LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD = "LOCATION_UPDATE_ACCURACY_THRESHOLD";
	public static final String LOCATION_SERVICE_PARAM_USE_GPS = "LOCATION_USE_GPS";
	public static final String LOCATION_SERVICE_PARAM_ALGORITHM =  "LOCATION_ALGORITHM";
	
	public static final String ACTION_LOCATION_SERVICE_STARTED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_SERVICE_STARTED";
	public static final String ACTION_LOCATION_SERVICE_SHUTDOWN = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_SERVICE_SHUTDOWN";
	public static final String ACTION_LOCATION_CHANGED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_CHANGED";
	public static final String ACTION_LOCATION_GPS_ENABLED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_GPS_ENABLED";
	public static final String ACTION_LOCATION_GPS_DISABLED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_GPS_DISABLED";
	
	public static final String LOCATION_KEY = "location";
	public static final String LOCATION_COUNTRY_KEY = "location_country";
	public static final String LOCATION_COUNTRY_CODE_KEY = "location_country_code";
	public static final String LOCATION_CITY_KEY = "location_city";
	public static final String LOCATION_ADDRESS_KEY = "location_address";
	public static final String LOCATION_POSTAL_CODE_KEY = "location,postal_code";
	
	private static final int TWO_MINUTES = (1000*60)*2;
    
	/**
     * The algorithm to use to determine if the new position should rise a new location
     * alert broadcast intent.<br>
     * <ul>
     * 	<li>SIMPLE. Choose this if we want only to be alerted every time me move the
     * 		specified distance in the parameter LOCATION_SERVICE_PARAM_MIN_DISTANCE.</li>
     * 	<li>COMPLEX. Choose this when we want also to check the accuracy, provider and the age 
     * 		of locations and not only the distance.</li>
     * </ul>		
     */
    public static enum LOCATION_ALGORITHM_TYPE {SIMPLE, COMPLEX};
	
    private static final int UPDATE_MIN_DISTANCE = 2; //meters 
    private static final long UPDATE_MIN_TIME = 4000l; //Milliseconds
    private static final int UPDATE_ACCURACY_THRESHOLD = 0; //meters
    private static final LOCATION_ALGORITHM_TYPE LOCATION_ALGORITHM = LOCATION_ALGORITHM_TYPE.SIMPLE;
        
    
    public LocationManager locationManager;
    public Location previousBestLocation = null;
    private int minDistance = UPDATE_MIN_DISTANCE;
    private long minTime = UPDATE_MIN_TIME;
    private int accuracyThreshold = UPDATE_ACCURACY_THRESHOLD;
    private boolean useGPS = false;
    private LOCATION_ALGORITHM_TYPE locAlgorithm = LOCATION_ALGORITHM;
    
    private static boolean svcStarted = false;
    
    
	
	public LocationService() {}

	
	@Override
    public void onCreate() {
		super.onCreate();
		if(!svcStarted)
			Log.d(TAG, "Location service created.");
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if ((flags & START_FLAG_REDELIVERY)!=0) {
			//Service was restarted.
			if(ToolBox.LOG_ENABLE)
	        	Log.d(TAG, "Location proximity service re-started.");
		}
		
		if(!svcStarted) {
			Log.d(TAG, "Location service starting...");
			doOnStart(intent);
			svcStarted = true;
		}
		
		//START_STICKY, START_NOT_STICKY and START_REDELIVER_INTENT are 
		//only relevant when the phone runs out of memory and kills the 
		//service before it finishes executing. Also if the user kills the
		//application from tasks menu. See:
		//
		// http://android-developers.blogspot.com.au/2010/02/service-api-changes-starting-with.html
		//
		// - START_STICKY tells the OS to recreate the service after it 
		//	 has enough memory and call onStartCommand() again with a 
		//	 null intent. 
		// - START_NOT_STICKY tells the OS to not bother recreating the 
		//   service again. 
		// - START_REDELIVER_INTENT that tells the OS to recreate the 
		//   service AND re-deliver the same intent to onStartCommand().
		return Service.START_REDELIVER_INTENT;		
	}
	
    @Override
    public void onDestroy() {       
    	Log.d(TAG, "Location service destroyed");
        super.onDestroy();
        
        previousBestLocation = null;
        locationManager.removeUpdates(this);
        locationManager = null;
        
        svcStarted = false;
        
        deliverBroadcast(ACTION_LOCATION_SERVICE_SHUTDOWN, null);
    }
    
    
    //AUXILIAR
    
    private void doOnStart(Intent intent) {
    	
    	if(intent!=null) {
    		//We get the initialization parameters from the intent.
    		minDistance = intent.getIntExtra(LOCATION_SERVICE_PARAM_MIN_DISTANCE, UPDATE_MIN_DISTANCE);
        	minTime = intent.getLongExtra(LOCATION_SERVICE_PARAM_MIN_TIME, UPDATE_MIN_TIME);
        	accuracyThreshold = intent.getIntExtra(LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD, UPDATE_ACCURACY_THRESHOLD);
        	useGPS = intent.getBooleanExtra(LOCATION_SERVICE_PARAM_USE_GPS, false);
        	locAlgorithm = getLocationAlgorithmFromString(intent.getStringExtra(LOCATION_SERVICE_PARAM_ALGORITHM));
        			
        	//We save the initialization for later usage in case services gets rebooted.
        	Thread tSP = new Thread(new Runnable() {
				@Override
				public void run() {
					ToolBox.prefs_savePreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_DISTANCE, Integer.class, minDistance);
		        	ToolBox.prefs_savePreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_TIME, Long.class, minTime);
		        	ToolBox.prefs_savePreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD, Integer.class, accuracyThreshold);
		        	ToolBox.prefs_savePreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_USE_GPS, Boolean.class, useGPS);
		        	ToolBox.prefs_savePreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ALGORITHM, String.class, locAlgorithm.name());
				}
			});
        	tSP.start();
        	
    	}else{
    		//No data in the intent, we try to get from saved preferences if there is one.
    		if(ToolBox.prefs_existsPref(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_DISTANCE)) {
    			minDistance = ((Integer)ToolBox.prefs_readPreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_DISTANCE, Integer.class)).intValue();
    		}
    		if(ToolBox.prefs_existsPref(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_TIME)) {
    			minTime = ((Long)ToolBox.prefs_readPreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_TIME, Long.class)).longValue();
    		}
    		if(ToolBox.prefs_existsPref(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD)) {
    			accuracyThreshold = ((Integer)ToolBox.prefs_readPreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD, Integer.class)).intValue();
    		}
    		if(ToolBox.prefs_existsPref(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_USE_GPS)) {
    			useGPS = ((Boolean)ToolBox.prefs_readPreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_USE_GPS, Boolean.class));
    		}
    		if(ToolBox.prefs_existsPref(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ALGORITHM)) {
    			String locAlg = ((String)ToolBox.prefs_readPreference(getBaseContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ALGORITHM, String.class));
    			locAlgorithm = getLocationAlgorithmFromString(locAlg);
    		}
    	}
    	
    	if(ToolBox.permission_areGranted(getBaseContext(), ToolBox.PERMISSION_LOCATION.keySet())) {
    		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    		locationManager.requestLocationUpdates(
            		LocationManager.NETWORK_PROVIDER, 
            		minTime, minDistance, this);    		
    		if(useGPS) {
    			locationManager.requestLocationUpdates(
            		LocationManager.GPS_PROVIDER, 
            		minTime, minDistance, this);
    		}
	        
	        if(ToolBox.LOG_ENABLE)
	        	Log.d(TAG, "Location service started. Parameters 'minTime': " + minTime + " / 'minDistance': " + minDistance + " / 'accuracyUmbral': " + accuracyThreshold + " / 'useGPS': " + useGPS + " / 'Location Algorithm': " + locAlgorithm.name());
	        
	        deliverBroadcast(ACTION_LOCATION_SERVICE_STARTED, null);
    	}else{
    		Log.d(TAG, "Location service not started, permissions not granted. Parameters 'minTime': " + minTime + " / 'minDistance': " + minDistance + " / 'accuracyUmbral': " + accuracyThreshold + " / 'useGPS': " + useGPS + " / 'Location Algorithm': " + locAlgorithm.name());
    	}
    	
    }
	
    
    protected LOCATION_ALGORITHM_TYPE getLocationAlgorithmFromString(String locationAlgorithm) {
    	LOCATION_ALGORITHM_TYPE res = null;
    	try{
    		res = LOCATION_ALGORITHM_TYPE.valueOf(locationAlgorithm);
    	}catch(Exception e){
    		//We use the SIMPLE mode and log the error.
    		res = LOCATION_ALGORITHM_TYPE.SIMPLE;
    		if(ToolBox.LOG_ENABLE)
    			Log.d(TAG, "Location Service: could not determine the location algorithm type [" + locationAlgorithm + "]. Using SIMPLE by default.");
    	}
    	
    	return res;
    }
    
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
    	boolean res = false;
    	
    	switch (locAlgorithm) {
			case SIMPLE:
				res = isBetterLocationSimple(location, currentBestLocation);
				break;
			case COMPLEX:			
				res = isBetterLocationComplex(location, currentBestLocation);			
		}
    	
    	return res;
    }
    
    /**
     * Checks if the new location is better than the old one.
     * 
     * @param location
     * @param currentBestLocation
     * @return
     */
	protected boolean isBetterLocationComplex(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true; //New location is better than no location
        }

        //Check if new location is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;
        
        if(currentBestLocation.getLatitude()==location.getLatitude() &&
           currentBestLocation.getLongitude()==location.getLongitude()) {
        	//Same location, new measurement is newer yes but the location is the same.
        	isNewer = false;
        	if(ToolBox.LOG_ENABLE)
        		Log.d(TAG, "Location service (ALG - COMPLEX): isNever set to FALSE (same location).");
        }else{
        	double distanceBetweenMeasurements = ToolBox.location_distance(location.getLatitude(), location.getLongitude(), 
        			currentBestLocation.getLatitude(), currentBestLocation.getLongitude());
        	if(ToolBox.LOG_ENABLE)
        		Log.d(TAG, "Location service (ALG - COMPLEX): Elapsed distance (Haversine) since last location: " + distanceBetweenMeasurements);
        	
        	if(distanceBetweenMeasurements<=minDistance){
        		//New measurement is newer yes but the distance between last and new location is less
        		//than the minimal distance.
        		isNewer = false;
        		if(ToolBox.LOG_ENABLE)
            		Log.d(TAG, "Location service (ALG - COMPLEX): isNever set to FALSE (Haversine distance less than threshold).");
        	}
        }

        //If it's been more than two minutes since the current location, we use the 
        //new location because the user has probably moved.
        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
        	if(ToolBox.LOG_ENABLE)
        		Log.d(TAG, "Location service (ALG - COMPLEX): Location is not better: isSignificantlyOlder");
            return false; //If the new location older than two minutes, should be worse
        }

        //Check if the new location is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = false; 
        if(accuracyDelta < 0 && ((-1)*accuracyDelta)>=accuracyThreshold) {
        	isMoreAccurate = true;
        }
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        if(ToolBox.LOG_ENABLE)
    		Log.d(TAG, "Location service (ALG - COMPLEX): isMore accurated? " + isMoreAccurate);

        //Check if the old and new location have the same provider
        boolean isFromSameProvider = 
        		isSameProvider(location.getProvider(), currentBestLocation.getProvider());

        //Watch for location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        
        if(ToolBox.LOG_ENABLE)
        	Log.d(TAG, "Location service (ALG - COMPLEX): is not better: isNewer: " + isNewer + 
        			", isLessAccurate: " + isLessAccurate + 
        			", isSignificantlyLessAccurate: " + isSignificantlyLessAccurate + 
        			", isFromSameProvider: " + isFromSameProvider);
        
        return false;
    }
	
	protected boolean isBetterLocationSimple(Location location, Location currentBestLocation) {
		boolean shouldAlert = true;
		
		if (currentBestLocation == null) {
            return true; //New location is better than no location
        }
        
        if(currentBestLocation.getLatitude()==location.getLatitude() &&
           currentBestLocation.getLongitude()==location.getLongitude()) {
        	//Same location, new measurement is newer yes but the location is the same.
        	shouldAlert = false;
        	if(ToolBox.LOG_ENABLE)
        		Log.d(TAG, "Location Service (ALG - SIMPLE): isNever set to FALSE (same location).");
        }else{
        	double distanceBetweenMeasurements = ToolBox.location_distance(location.getLatitude(), location.getLongitude(), 
        			currentBestLocation.getLatitude(), currentBestLocation.getLongitude());
        	if(ToolBox.LOG_ENABLE)
        		Log.d(TAG, "Location Service (ALG - SIMPLE): Elapsed distance (Haversine) since last location: " + distanceBetweenMeasurements);
        	
        	if(distanceBetweenMeasurements<=minDistance){
        		//New measurement is newer yes but the distance between last and new location is less
        		//than the minimal distance.
        		shouldAlert = false;
        		if(ToolBox.LOG_ENABLE)
            		Log.d(TAG, "Location Service (ALG - SIMPLE): shouldAlert set to FALSE (Haversine distance less than threshold).");
        	}
        }
        
        return shouldAlert;
    }

	/** 
	 * Checks if two location providers are the same.
	 * */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    
    /**
     * An utility method to perform a job in a separated thread.
     */
    public static Thread doInBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try { runnable.run(); } finally {}
            }
        };
        t.start();
        return t;
    }
    
    
    
    // LocationListener methods ---------------------------------------------
    
    @Override
    public void onLocationChanged(final Location loc) {
      	if(ToolBox.LOG_ENABLE)
       		Log.d(TAG, "Location changed.");
      	
      	if(isBetterLocation(loc, previousBestLocation)) {
      		
      		Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					//...some extra information about the location
			        LocationInfo locInfo = ToolBox.location_addressInfo(getBaseContext(), loc.getLatitude(), loc.getLongitude());
		      		if(locInfo==null)
		      			return;
			        
		      		previousBestLocation = loc;
		            	
			        //Send the change to an application receiver.
			        Bundle extras = new Bundle(); 
			        loc.getLatitude();
			        loc.getLongitude();
			        extras.putParcelable(LOCATION_KEY, loc);
			        extras.putString(LOCATION_COUNTRY_KEY, locInfo.getCountry());
			        extras.putString(LOCATION_COUNTRY_CODE_KEY, locInfo.getCountryCode());
			        extras.putString(LOCATION_CITY_KEY, locInfo.getCity());
			        extras.putString(LOCATION_ADDRESS_KEY, locInfo.getAddress());
			        extras.putString(LOCATION_POSTAL_CODE_KEY, locInfo.getPostalCode());
			        
			        deliverBroadcast(ACTION_LOCATION_CHANGED, extras);
			        if(ToolBox.LOG_ENABLE)
			    		Log.d(TAG, "Location change broadcast message sent.");
				}
			});
      		
      		t.start();
      	}                               
    }

    @Override
    public void onProviderDisabled(String provider) {
      	if(ToolBox.LOG_ENABLE)
       		Log.d(TAG, "Location provider disabled [" + provider + "].");
      	if(provider.equals(LocationManager.GPS_PROVIDER)){
       		previousBestLocation = null;
       		deliverBroadcast(ACTION_LOCATION_GPS_DISABLED, null);
       	}
    }
    
    @Override
    public void onProviderEnabled(String provider) {
      	if(ToolBox.LOG_ENABLE)
      		Log.d(TAG, "Location provider enabled [" + provider + "].");
       	if(provider.equals(LocationManager.GPS_PROVIDER)){
       		previousBestLocation = null;
       		deliverBroadcast(ACTION_LOCATION_GPS_ENABLED, null);        		
       	}
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    
    //End LocationListener methods ---------------------------------------------
    
    
    /**
     * Delivers a broadcast action.
     * 
     * @param action
     * @param extras
     */
    private void deliverBroadcast(String action, Bundle extras){
    	Intent intent = new Intent(action);
    	if(extras!=null) {
    		intent.putExtras(extras);
    	}
        sendBroadcast(intent);    	
    }
}
