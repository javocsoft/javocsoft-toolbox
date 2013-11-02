package es.javocsoft.android.lib.toolbox.sms.observer;

import java.text.Normalizer;
import java.util.Calendar;

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
 * Handler smsHandler = new Handler();
 * smsObserver = new SMSObserver(smsHandler, getApplicationContext());
 * ContentResolver mContentResolver = getContentResolver();
 * mContentResolver.registerContentObserver(Uri.parse("content://sms/"),true, smsObserver);
 * 
 * @author JavocSoft 2013
 * @version 1.0<br>
 * $Rev: 347 $<br>
 * $LastChangedDate: 2013-11-02 11:50:09 +0100 (Sat, 02 Nov 2013) $<br>
 * $LastChangedBy: jgonzalez $
 *
 */
public class SMSObserver extends ContentObserver {
	
    private Context context;
    private static int initialPos;
    private static final String TAG = "SMSObserver";
    private static final Uri uriSMS = Uri.parse("content://sms/");
    
    
    public SMSObserver(Handler handler, Context ctx) {
        super(handler);
        
        context = ctx;
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
                        String reformated_body =
                            Normalizer.normalize(body, Normalizer.Form.NFD)
                            			.replaceAll("[^\\p{ASCII}]", "");
                    	
                    	String protocol = cur.getString(cur.getColumnIndex("protocol"));
                        	
                        if (initialPos != getLastMsgId()) {
                            // Only get the last SMS.
                        		
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
                    			
                    			//Do something...
                    			
                            }else{
                            	//SMS sent
                            	smsEventType = "S";
                            	logMessage = "SMS Sent: [" + 
                    				day + "-" + month + "-" + year + " "
                                    + hour + ":" + minute + ":" + seconds + "]" +
                    				" Receiver: [" + receiver + "]" +
                                    " Message: [" + reformated_body + "]";
                            	
                            	//Do something...
                            }                    		
                    		
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
                Log.e("Info","colNames["+k+"] : " + colNames[k]);
            }
        }
    }
    
    @SuppressWarnings("unused")
	private void printSMSData(Cursor sms_sent_cursor){
    	Log.e("Info","Id : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("_id")));
        Log.e("Info","Thread Id : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("thread_id")));
        Log.e("Info","Address : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("address")));
        Log.e("Info","Person : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("person")));
        Log.e("Info","Date : " + sms_sent_cursor.getLong(sms_sent_cursor.getColumnIndex("date")));
        Log.e("Info","Read : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("read")));
        Log.e("Info","Status : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("status")));
        Log.e("Info","Type : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("type")));
        Log.e("Info","Rep Path Present : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("reply_path_present")));
        Log.e("Info","Subject : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("subject")));
        Log.e("Info","Body : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("body")));
        Log.e("Info","Err Code : " + sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("error_code")));        
    }
 
}
