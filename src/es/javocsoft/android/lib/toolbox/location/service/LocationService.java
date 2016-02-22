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
 *   </ul>  
 *   To set these values, set them through the service starting intent by 
 *   using these keys in the bundle:
 *   <ul>
 *     <li>LOCATION_SERVICE_PARAM_MIN_DISTANCE</li> 
 * 	   <li>LOCATION_SERVICE_PARAM_MIN_TIME</li>
 * 	   <li>LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD</li>
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
 * @version 1.0<br>
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
	
	public static final String ACTION_LOCATION_SERVICE_STARTED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_SERVICE_STARTED";
	public static final String ACTION_LOCATION_SERVICE_SHUTDOWN = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_SERVICE_SHUTDOWN";
	public static final String ACTION_LOCATION_CHANGED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_CHANGED";
	public static final String ACTION_LOCATION_GPS_ENABLED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_GPS_ENABLED";
	public static final String ACTION_LOCATION_GPS_DISABLED = LocationService.class.getPackage().getName() + ".intent.action.LOCATION_GPS_DISABLED";
	
	public static final String LOCATION_KEY = "location";
	
	private static final int TWO_MINUTES = (1000*60)*2;
    
    private static final int UPDATE_MIN_DISTANCE = 2; //meters 
    private static final long UPDATE_MIN_TIME = 4000l; //Milliseconds
    private static final int UPDATE_ACCURACY_THRESHOLD = 0; //meters
    
    
    public LocationManager locationManager;
    public Location previousBestLocation = null;
    private int minDistance = UPDATE_MIN_DISTANCE;
    private long minTime = UPDATE_MIN_TIME;
    private int accuracyThreshold = UPDATE_ACCURACY_THRESHOLD;
    
    
	
	public LocationService() {}

	
	@Override
    public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Location service created.");
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Location service starting...");
		doOnStart(intent);	
		return Service.START_STICKY;
	}
	
    @Override
    public void onDestroy() {       
    	Log.d(TAG, "Location service destroyed");
        super.onDestroy();
        
        previousBestLocation = null;
        locationManager.removeUpdates(this);
        locationManager = null;
        
        deliverBroadcast(ACTION_LOCATION_SERVICE_SHUTDOWN, null);
    }
    
    
    //AUXILIAR
    
    private void doOnStart(Intent intent) {
    	
    	if(intent!=null) {
    		//We get the initialization parameters from the intent.
    		minDistance = intent.getIntExtra(LOCATION_SERVICE_PARAM_MIN_DISTANCE, UPDATE_MIN_DISTANCE);
        	minTime = intent.getLongExtra(LOCATION_SERVICE_PARAM_MIN_TIME, UPDATE_MIN_TIME);
        	accuracyThreshold = intent.getIntExtra(LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD, UPDATE_ACCURACY_THRESHOLD);
        	
        	//We save the initialization for later usage in case services gets rebooted.
        	ToolBox.prefs_savePreference(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_DISTANCE, Integer.class, minDistance);
        	ToolBox.prefs_savePreference(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_TIME, Long.class, minTime);
        	ToolBox.prefs_savePreference(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD, Integer.class, accuracyThreshold);
    	}else{
    		//No data in the intent, we try to get from saved preferences if there is one.
    		if(ToolBox.prefs_existsPref(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_DISTANCE)) {
    			minDistance = ((Integer)ToolBox.prefs_readPreference(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_DISTANCE, Integer.class)).intValue();
    		}
    		if(ToolBox.prefs_existsPref(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_TIME)) {
    			minTime = ((Long)ToolBox.prefs_readPreference(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_MIN_TIME, Long.class)).longValue();
    		}
    		if(ToolBox.prefs_existsPref(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD)) {
    			accuracyThreshold = ((Integer)ToolBox.prefs_readPreference(getApplicationContext(), ToolBox.PREF_FILE_NAME, LOCATION_SERVICE_PARAM_ACCURACY_THRESHOLD, Integer.class)).intValue();
    		}
    	}
    	
    	if(ToolBox.permission_areGranted(getApplicationContext(), ToolBox.PERMISSION_LOCATION.keySet())) {
    		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(
            		LocationManager.NETWORK_PROVIDER, 
            		minTime, minDistance, this);
            locationManager.requestLocationUpdates(
            		LocationManager.GPS_PROVIDER, 
            		minTime, minDistance, this);
            
            if(ToolBox.LOG_ENABLE)
            	Log.d(TAG, "Location service started. Parameters 'minTime': " + minTime + " / 'minDistance': " + minDistance + " / 'accuracyUmbral': " + accuracyThreshold);
            
            deliverBroadcast(ACTION_LOCATION_SERVICE_STARTED, null);
    	}else{
    		Log.d(TAG, "Location service not started, permissions not granted. Parameters 'minTime': " + minTime + " / 'minDistance': " + minDistance + " / 'accuracyUmbral': " + accuracyThreshold);
    	}
    	
    }
	
    /**
     * Checks if the new location is better than the old one.
     * 
     * @param location
     * @param currentBestLocation
     * @return
     */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
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
        		Log.d(TAG, "isNever set to FALSE (same location).");
        }else{
        	double distanceBetweenMeasurements = ToolBox.location_distance(location.getLatitude(), location.getLongitude(), 
        			currentBestLocation.getLatitude(), currentBestLocation.getLongitude());
        	if(ToolBox.LOG_ENABLE)
        		Log.d(TAG, "Elapsed distance (Haversine) since last location: " + distanceBetweenMeasurements);
        	
        	if(distanceBetweenMeasurements<=minDistance){
        		//New measurement is newer yes but the distance between last and new location is less
        		//than the minimal distance.
        		isNewer = false;
        		if(ToolBox.LOG_ENABLE)
            		Log.d(TAG, "isNever set to FALSE (Haversine distance less than threshold).");
        	}
        }

        //If it's been more than two minutes since the current location, we use the 
        //new location because the user has probably moved.
        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
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
    		Log.d(TAG, "isMore accurated? " + isMoreAccurate);

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
        return false;
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
    
    public void onLocationChanged(final Location loc) {
      	if(ToolBox.LOG_ENABLE)
       		Log.d(TAG, "Location changed.");
      		if(isBetterLocation(loc, previousBestLocation)) {
            previousBestLocation = loc;
            	
            //Send the change to an application receiver.
            Bundle extras = new Bundle(); 
            loc.getLatitude();
            loc.getLongitude();
            extras.putParcelable(LOCATION_KEY, loc);
            deliverBroadcast(ACTION_LOCATION_CHANGED, extras);            	
        }                               
    }

    public void onProviderDisabled(String provider) {
      	if(ToolBox.LOG_ENABLE)
       		Log.d(TAG, "Location provider disabled [" + provider + "].");
      	if(provider.equals(LocationManager.GPS_PROVIDER)){
       		previousBestLocation = null;
       		deliverBroadcast(ACTION_LOCATION_GPS_DISABLED, null);
       	}
    }

    public void onProviderEnabled(String provider) {
      	if(ToolBox.LOG_ENABLE)
      		Log.d(TAG, "Location provider enabled [" + provider + "].");
       	if(provider.equals(LocationManager.GPS_PROVIDER)){
       		previousBestLocation = null;
       		deliverBroadcast(ACTION_LOCATION_GPS_ENABLED, null);        		
       	}
    }

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
