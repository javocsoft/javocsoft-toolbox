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
package es.javocsoft.android.lib.toolbox.sms.cmt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.io.Unzipper;


/**
 * This class gets the official CMT short numbers owner
 * company information. This will allow you to know the owner
 * of any registered in CMT short number.<br><br>
 * 
 * See: <a href="http://www.cmt.es/descarga-ficheros-numeracion">Short-numbers information download</a><br>
 * See: <a href="http://www.cmt.es/instrucciones">CMT Numeration related information</a><br><br>
 * 
 * @author JavocSoft 2014
 * @version 1.0
 */
public class CMTInfoHelper {
	/** Under which key is stored the CMT Short Numbers information in SharedPreferences. */
	public static final String PREF_KEY_CMT_NUMBERS_INFO = "pref_cmt_numbers_info";
	/** Tells if the CMT information updater thread is running. */
	public static boolean isUpdaterRunning = false;
	/** Tells if the CMT information updater is scheduled to run periodically. */
	public static boolean isUpdaterScheduled = false;
	
	
	/** Where to find the CMT information ZIP file */
	private final static String CMT_INFO_FILE_URL = "http://www.cmt.es/Numeracion/bd-num.zip";	
	/** Default update interval for CMT information file. */
	private static final long DEFAULT_UPDATE_INTERVAL = 1440; // 24 hours.
	
	private static CMTInfoHelper thiz;
	private static Timer timer = null;
	private static TimerTask cmtTimerTask = null;
	private static CMTInfoDownloader cmtInfoDownloader = null;
	private static boolean keepUpdated = false;
	private static long updateInterval;
	private static Handler handler = null;
	
	
	/**
	 * Gets an instance of CMTInfoHelper class.
	 * 
	 * @param context
	 * @param Optional. A callback class to do something 
	 * 		  when CMT information is downloaded.
	 * @return
	 */
	public static CMTInfoHelper getInstance(Context context, CMTEventCallback callback) {
		if(thiz==null) {
			thiz = new CMTInfoHelper(context, callback, false, 0);
		}		
		return thiz;
	}
	
	/**
	 * Gets an instance of CMTInfoHelper class.
	 * 
	 * @param context
	 * @param callback	Optional. A callback class to do something 
	 * 		  			when CMT information is downloaded.
	 * @param keepUpdated	Set to TRUE to get automatic update intervals for the
	 * 						specified update interval of CMT downloaded information.
	 * @param updateInterval	The desired update interval (in minutes) for CMT information.
	 * @return
	 */
	public static CMTInfoHelper getInstance(Context context, CMTEventCallback callback, boolean keepUpdated, int updateInterval) {
		if(thiz==null) {
			thiz = new CMTInfoHelper(context, callback, keepUpdated, updateInterval);
		}		
		return thiz;
	}
	
	
	//Protected constructor.
	
	protected CMTInfoHelper(Context context, CMTEventCallback callback, boolean keepUpdated, long updateInterval){
		cmtInfoDownloader = new CMTInfoDownloader(context, callback);
		
		if(keepUpdated) {
			if(updateInterval<=0) {
				updateInterval = TimeUnit.MILLISECONDS.convert(DEFAULT_UPDATE_INTERVAL, TimeUnit.MINUTES);
			}else{
				updateInterval = TimeUnit.MILLISECONDS.convert(updateInterval, TimeUnit.MINUTES);
			}
		}
		
		startCMTInfoUpdater();		
	}
	
	
	//PUBLIC METHODS
	
	/**
	 * Starts the CMT updater. You can use it to
	 * force an update from the CMT.
	 */
	public void startCMTInfoUpdater() {
		
		boolean start = false;
		if(cmtTimerTask==null) { //First time
			start = true;			
		}else{
			if(!isUpdaterRunning){
				start = true;
			}
		}
		
		if(start)
			scheduleCMTLoadDataTask();
	}
	
	/**
	 * Stops the periodical CMT info updater
	 * if is running.
	 */
	public void stopCMTInfoUpdater (){
		if(timer!=null && isUpdaterScheduled){
			timer.cancel();
		}
	}	
	
	/**
	 * Gets the CMT short number information as a 
	 * SHORT_NUMBER - COMPANY key-pair list.
	 * 
	 * @return The list or NULL in case list could 
	 * 		   not be retrieved.
	 */
	public Map<String, String> getShortNumbersInformation() {
		
		Map<String, String> res = cmtInfoDownloader.getShortNumbersInformation();
		if(res==null) {
			//We try to get from the cache in SharedPreferences
			res = cmtInfoDownloader.getShortNumbersInformationFromSharedPreferences();
		}
		
		return res;
	}
	
	
	//AUXILIAR METHODS
	
	/*
	 * Schedules the task that gets and processes the CMT 
	 * informative ZIP file.
	 * 
	 * (An Asynchronous task can not be scheduled inside 
	 * just a Runnable, we have to use a TimerTask for a 
	 * Timer).
	 */
	private static void scheduleCMTLoadDataTask() {
		
		if(cmtTimerTask==null) {
			handler = new Handler();		
			timer = new Timer();
			
			cmtTimerTask = new TimerTask() {       
				@Override
				public void run() {
					handler.post(new Runnable() {
						public void run() {       
							try {
								if(!isUpdaterRunning)
									cmtInfoDownloader.execute(CMT_INFO_FILE_URL);
							} catch (Exception e) {
								Log.e(ToolBox.TAG, "CMT short numbers informative data getter could not be executed (" + e.getMessage() + ")", e);	                    	
							}
						}
					});
				}
			};
		}
		
		if(keepUpdated && !isUpdaterScheduled) {
			//Schedule periodical downloads of the CMT information
			timer.schedule(cmtTimerTask, 0, updateInterval);
			isUpdaterScheduled = true;
		}else{
			//When no periodical update setting is set or when
			//periodical update is set but the user forces the update.
			timer.schedule(cmtTimerTask, 0);			
			isUpdaterScheduled = false;
		}
	}
	
	
	/**
     * This class allows to do something when a sms is sent or recived.
     * 
     * @author JavocSoft 2013.
     * @since 2013
     */
    public static abstract class CMTEventCallback extends Thread implements Runnable {
    	
    	protected Context context;
    	/** Official CMT short number information list. */
    	protected Map<String, String> shortNumbers;
    	/** The file path to the official CMT shor number information file. */
    	protected String CMTInfoFile;
    	
    	public CMTEventCallback() {}
    	
    	
		@Override
		public void run() {
			pre_task();
			task();
			post_task();
		}
    	
		
		protected void setContext (Context context) {
			this.context = context;
		}
		
		protected void setShortNumbers(Map<String, String> shortNumbers) {
			this.shortNumbers = shortNumbers;
		}
		
		protected void setCMTInfoFile(String CMTInfoFile) {
			this.CMTInfoFile = CMTInfoFile;
		}
		
		protected abstract void pre_task();
		protected abstract void task();
		protected abstract void post_task();
    }
	
}


/**
 * Gets the CMT informative ZIP file extracting the SMS short numbers
 * related information file and saving it in an accessible Map and also
 * in SharedPreferences.
 * 
 * @author JavocSoft 2014
 * @version 1.0
 */
class CMTInfoDownloader extends AsyncTask < String, Void, String >{
	
	private Context context;
	private CMTInfoHelper.CMTEventCallback callback = null;
	private Map<String, String> shortNumbers;
	private String dataSeparator = "###";
	
	private final static String INFO_FILE_NAME = "sms_mms.txt";
	
	
	public CMTInfoDownloader(Context context, CMTInfoHelper.CMTEventCallback callback){
		this.context = context;
		this.callback = callback;
		shortNumbers = new HashMap<String, String>();		
	}

	@Override
	protected void onPreExecute() {
		CMTInfoHelper.isUpdaterRunning = true;
	} 

	@Override
	protected String doInBackground(String... params) {
		String res = null;
		
		//DOWNLOAD THE ZIP FILE AND EXTRACT SHORTNUMBER INFORMATION FILE
		String fileURL=params[0];	    
		try{
			Log.i(ToolBox.TAG, fileURL);
			
			//Download CMT zip file
			String savedFileName = ToolBox.storage_saveUrlToInternal(context, fileURL, 1024);
			
			if(savedFileName!=null && savedFileName.length()>0){
				Log.i(ToolBox.TAG, "CMT short numbers information ZIP file downloaded.");
				
				//Unzip the file				
				String zippedFile = ToolBox.storage_getAppInternalStorageFilePath(context, null).getAbsolutePath() +"/"+ savedFileName;
				String unzipTo = ToolBox.storage_getAppInternalStorageFilePath(context, null).getAbsolutePath() + "/";
				Unzipper unzipper = new Unzipper(zippedFile, unzipTo, false);
				List<String> files = unzipper.unzip();
				
				//Look for specific short numbers information file
				for(String file:files){
					Log.d(ToolBox.TAG, file);
					if(file.endsWith(INFO_FILE_NAME)){
						res = file;
						break;
					}
				}
			}else{
				Log.w(ToolBox.TAG, "CMT short numbers information could not be loaded.");
			}
			
			return res;
    	}catch(Exception e){        	
    		Log.e(ToolBox.TAG,"Error loading CMT Short numbers information from '" + fileURL + "' ["+e.getMessage()+"]",e);
		}
		
	    return null; 
	} 
	
	@Override
	protected void onProgressUpdate(Void... progress) {}
	
	@Override
	protected void onPostExecute(String filePath) { 
		
		if(filePath!=null){
			//PARSE CMT SHORTNUMBER INFORMATION FILE
			try{
				String data = ToolBox.storage_getTextFileContentAsString(filePath, "ISO-8859-1");
				if(data!=null && data.length()>0){
					getShortNumbersInformation().clear();
					
					String[] infoLines = data.split("\\n");
					String[] lineParts = null;
					for(int i=0;i<infoLines.length;i++){
						lineParts = infoLines[i].split("#");
						shortNumbers.put(lineParts[0], lineParts[1]);
					}					
					Log.w(ToolBox.TAG, "CMT short numbers information extracted from CMT ZIP file.");
					
					//Just if we could need it in this way
					saveShortNumberInfoInPreferences();
					
					//We launch a custom user event if defined.
					if(callback!=null) {
						callback.setContext(context);
						callback.setShortNumbers(shortNumbers);
						callback.setCMTInfoFile(filePath);
						callback.start();
					}
					
					Log.i(ToolBox.TAG, "CMTInfoHelper finished.");
				}else{
					Log.w(ToolBox.TAG, "CMT short numbers information file content is wrong.");					
				}
				
			}catch(Exception e){
				Log.e(ToolBox.TAG, "CMT short numbers information file could not be loaded. Error (" + e.getMessage() + ").", e);
			}			
		}else{
			Log.w(ToolBox.TAG, "No downloaded CMT short numbers file available.");
		}
		
		CMTInfoHelper.isUpdaterRunning = false;
	}
    
	/**
	 * Gets the CMT short numbers information. (Number and Owner company).
	 * 
	 * @return
	 */
	public Map<String, String> getShortNumbersInformation(){
		return shortNumbers;		
	}
	
	/**
	 * Gets the CMT short numbers information from the SharedPreferences. 
	 * (Number and Owner company).
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getShortNumbersInformationFromSharedPreferences(){
		Map<String, String> res = null;
		
		//read the data from the shared preferences converting it to a Map.
		Set<String> dataCollection = null;
		dataCollection = (Set<String>) ToolBox.prefs_readPreference(context, ToolBox.PREF_FILE_NAME, CMTInfoHelper.PREF_KEY_CMT_NUMBERS_INFO, Set.class);
		
		if(dataCollection!=null) {
			res = new HashMap<String, String>();
			
			String dataParts[] = null;
			for(String s:dataCollection) {
				dataParts = s.split(dataSeparator);
				res.put(dataParts[0], dataParts[1]);
			}
		}
			
		return res;
	}
	
	
	//AUXILIAR
	
	private void saveShortNumberInfoInPreferences(){
		String numberInfo = null;
		String data = null;
		
		Set<String> dataCollection = new HashSet<String>();
		for(String number:shortNumbers.keySet()){
			numberInfo = shortNumbers.get(number);
			data = number + dataSeparator + numberInfo;
			dataCollection.add(data);			
		}
		ToolBox.prefs_savePreference(context, ToolBox.PREF_FILE_NAME, CMTInfoHelper.PREF_KEY_CMT_NUMBERS_INFO, Set.class, dataCollection);
	}	
}





