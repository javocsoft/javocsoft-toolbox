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
package es.javocsoft.android.lib.toolbox.sms.observer;


import java.text.Normalizer;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
 
/**
 * SMS Observer for sent/received sms.
 * 
 * Requires permissions:
 * 
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * <uses-permission android:name="android.permission.READ_SMS" />
 * <uses-permission android:name="android.permission.SEND_SMS"/>
 * 
 * Usage:
 * 
 * 	Handler smsHandler = new Handler();	
 *	if(ToolBox.device_isHardwareFeatureAvailable(this, PackageManager.FEATURE_TELEPHONY)){
 *		smsObserver = new SMSObserver(smsHandler, getApplicationContext(), msgReceivedCallback, msgSentCallback);		
 *		
 *		ContentResolver mContentResolver = getContentResolver();
 * 		mContentResolver.registerContentObserver(Uri.parse("content://sms/"),true, smsObserver);
 *	}
 * 
 * @author JavocSoft 2013
 * @version 1.0
 */
public class SMSObserver extends ContentObserver {
	
    private Context context;
    private static int initialPos;
    /** Enables or disables the log. */
    public static boolean LOG_ENABLE = true;
    private static final String TAG = "javocsoft-toolbox: SMSObserver";
    private static final Uri uriSMS = Uri.parse("content://sms/");
    
    private SMSRunnableTask msgReceivedCallback;
    private SMSRunnableTask msgSentCallback;
    
    /**
     * This is a general SMS observer. It allows to do something when a SMS is sent
     * or received.
     * 
     * @param handler
     * @param ctx
     * @param msgReceivedCallback
     * @param msgSentCallback
     */
    public SMSObserver(Handler handler, Context ctx, SMSRunnableTask msgReceivedCallback, SMSRunnableTask msgSentCallback) {
        super(handler);
        
        context = ctx;
        this.msgReceivedCallback = msgReceivedCallback;
        this.msgSentCallback = msgSentCallback;
        initialPos = getLastMsgId();        
    }
 
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        
        queryLastSentSMS();
    }
 
    public synchronized int getLastMsgId() {
    	int lastMsgId = -1;
    	
        Cursor cur = context.getContentResolver().query(uriSMS, null, null, null, null);
        if(cur.getCount()>0){
	        cur.moveToFirst();
	        lastMsgId = cur.getInt(cur.getColumnIndex("_id"));
	        if(LOG_ENABLE)
	        	Log.i(TAG, "Last sent message id: " + String.valueOf(lastMsgId));
        }
        
        return lastMsgId;
    }
 
    protected synchronized void queryLastSentSMS() {
 
        new Thread(new Runnable() {
 
            @SuppressWarnings("unused")
			@Override
            public void run() {
            	
                Cursor cur = context.getContentResolver().query(uriSMS, null, null, null, null);
 
                if (cur.moveToNext()) { 
                    TelephonyManager tm = (TelephonyManager)
                        context.getSystemService(Context.TELEPHONY_SERVICE);
 
                    String myDeviceId = tm.getDeviceId();
                    String myTelephoneNumber = tm.getLine1Number();
 
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    int hour = c.get(Calendar.HOUR);
                    int minute = c.get(Calendar.MINUTE);
                    int seconds = c.get(Calendar.SECOND);
                    
                    try { 
                    	String receiver = cur.getString(cur.getColumnIndex("address"));
                        String body = cur.getString(cur.getColumnIndex("body"));
                        String protocol = cur.getString(cur.getColumnIndex("protocol"));
                        String reformated_body = formatBody(body);
                        	
                        if (initialPos != getLastMsgId()) {
                        	// Only get the last SMS.
                        	
                        	SMSData smsData = new SMSData(cur);
                        	
                    		String logMessage = null;
                    		String notificationMessage = null;
                    		String smsEventType = null;
                    		if (protocol != null) {
                                //SMS received
                    			smsEventType = "R";
                    			logMessage = "SMS Sent: [" + 
	                    				day + "-" + month + "-" + year + " "
	                                    + hour + ":" + minute + ":" + seconds + "]" +
	                    				" From: [" + receiver + "]" +
	                                    " Message: [" + reformated_body + "]";
                    			
                    			if(msgReceivedCallback!=null){
                    				msgReceivedCallback.setSMSData(smsData);
                    				msgReceivedCallback.setContext(context);
                    				msgReceivedCallback.start();
                    			}
                    			
                            }else{
                            	//SMS sent
                            	smsEventType = "S";
                            	logMessage = "SMS Sent: [" + 
                    				day + "-" + month + "-" + year + " "
                                    + hour + ":" + minute + ":" + seconds + "]" +
                    				" Receiver: [" + receiver + "]" +
                                    " Message: [" + reformated_body + "]";
                            	
                            	if(msgSentCallback!=null){
                            		msgSentCallback.setSMSData(smsData);
                            		msgSentCallback.setContext(context);
                            		msgSentCallback.start();
                            	}
                            }                    		
                    		
                    		if(LOG_ENABLE)
                    			Log.i(TAG, logMessage);                        		
                        	
                            // Then, set initialPos to the current position.
                            initialPos = getLastMsgId();
                        }
                    } catch (Exception e) {
                    	Log.e(TAG, "Error getting SMS (" + e.getMessage() + ")", e);                        
                    }
                }
                cur.close();
            }
        }).start();
 
    }
    
    
    //AUXILIAR
    
    @SuppressWarnings("unused")
	private void printSMSFieldNames(Cursor sms_sent_cursor){
    	//Get Column names.
        String[] colNames = sms_sent_cursor.getColumnNames();                       
        if(colNames != null){
            for(int k=0; k<colNames.length; k++){
                Log.i("Info","colNames["+k+"] : " + colNames[k]);
            }
        }
    }
    
    @SuppressWarnings("unused")
	private void printSMSData(Cursor sms_sent_cursor){
    	Log.i("Info","Id : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("_id")));
        Log.i("Info","Thread Id : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("thread_id")));
        Log.i("Info","Address : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("address")));
        Log.i("Info","Person : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("person")));
        Log.i("Info","Date : " + sms_sent_cursor.getLong(sms_sent_cursor.getColumnIndex("date")));
        Log.i("Info","Read : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("read")));
        Log.i("Info","Status : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("status")));
        Log.i("Info","Type : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("type")));
        Log.i("Info","Rep Path Present : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("reply_path_present")));
        Log.i("Info","Subject : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("subject")));
        Log.i("Info","Body : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("body")));
        Log.i("Info","Err Code : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("error_code")));        
    }
 
    /**
     * Replace all non ASCII, non-printable and control characters
     * 
     * @param body
     * @return
     */
    @SuppressLint("NewApi")
    private String formatBody(String body){
    	String reformated_body = null;
        
    	if(android.os.Build.VERSION.SDK_INT<9){
        	reformated_body = body.replaceAll("[^\\x20-\\x7E]", "");
        }else{
        	reformated_body =
                    Normalizer.normalize(body, Normalizer.Form.NFD)
                    			.replaceAll("[^\\p{ASCII}]", "");
        }
    	
    	return reformated_body;
    }
    
    /**
     * This class allows to do something when a sms is sent or recived.
     * 
     * @author JavocSoft 2013.
     * @since 2013
     */
    public static abstract class SMSRunnableTask extends Thread implements Runnable {
    	
    	protected Context context;
    	protected SMSData sms;
    	
    	public SMSRunnableTask() {}
    	
    	
		@Override
		public void run() {
			pre_task();
			task();
			post_task();
		}
    	
		/**
		 * Allows to set the SMS content.
		 *  
		 * @param sms
		 */
		protected void setSMSData (SMSData sms) {
			this.sms = sms;
		}
		
		
		protected void setContext (Context context) {
			this.context = context;
		}
		
		protected abstract void pre_task();
		protected abstract void task();
		protected abstract void post_task();
    }
    
    /**
     * SMS information.
     * 
     * @author JavocSoft 2013
     * @since 2013
     *
     */
    public class SMSData {
    	
    	public String thread_id;
    	public String address;
    	public String person;
    	public long date;
    	public String read;
    	public String status;
    	public String type;
    	public String reply_path_present;
    	public String subject;
    	public String body;
    	public String formattedBody;
    	public String error_code;
    	
    	public SMSData(Cursor sms_cursor) {
    		thread_id = sms_cursor.getString(sms_cursor.getColumnIndex("thread_id"));
    		address = sms_cursor.getString(sms_cursor.getColumnIndex("address"));
    		person = sms_cursor.getString(sms_cursor.getColumnIndex("person"));
    		date = sms_cursor.getLong(sms_cursor.getColumnIndex("date"));
    		read = sms_cursor.getString(sms_cursor.getColumnIndex("read"));
    		status = sms_cursor.getString(sms_cursor.getColumnIndex("status"));
    		type = sms_cursor.getString(sms_cursor.getColumnIndex("type"));
    		reply_path_present = sms_cursor.getString(sms_cursor.getColumnIndex("reply_path_present"));
    		subject = sms_cursor.getString(sms_cursor.getColumnIndex("subject"));
    		body = sms_cursor.getString(sms_cursor.getColumnIndex("body"));
    		formattedBody = formatBody(body);
    		error_code = sms_cursor.getString(sms_cursor.getColumnIndex("error_code"));
    	}
    }
}
