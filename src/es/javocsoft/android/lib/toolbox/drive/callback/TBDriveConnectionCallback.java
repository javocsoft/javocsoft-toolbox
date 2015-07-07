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
package es.javocsoft.android.lib.toolbox.drive.callback;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import es.javocsoft.android.lib.toolbox.drive.TBDrive;
import es.javocsoft.android.lib.toolbox.drive.exception.TBDriveException;

/**
 * Base callback class to use when getting connection to Google Drive.
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveConnectionCallback extends Thread implements Runnable {

	protected Context context = null;
	protected ConnectionResult connResult = null;
	protected Bundle connectionHint = null;
	protected int connectionSuspendedCause = 0;
	protected String connectionSuspendedCauseName = null;
	
	
	/** Available connection suspended causes. */
	protected enum ConnectionSuspendedCause {
		CAUSE_NETWORK_LOST(GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST), 
		CAUSE_SERVICE_DISCONNECTED(GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED);
		 
		 private int code;
		 
		 private ConnectionSuspendedCause(int c) {
		   code = c;
		 }
		 
		 public int getCode() {
		   return code;
		 }
		 
		 public static ConnectionSuspendedCause getCause(int cause) throws TBDriveException {
			 switch (cause) {
			case GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST:
				return CAUSE_NETWORK_LOST;				
			case GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED:
				return CAUSE_SERVICE_DISCONNECTED;				
			default:
				throw new TBDriveException("Unrecognized cause.");
			}
		 }
		 
	}
	
	
	public TBDriveConnectionCallback(Context context)  {
		this.context = context;
	}

	
	@Override
	public void run() {
		doWork();
	}
	
	
	public abstract void doWork(); 
	
	
	/**
	 * In case of error, allows to set the connection result.
	 * 
	 * @param connResult
	 */
	public void setConnectionResult(ConnectionResult connResult) {
		this.connResult = connResult;	
	}
	
	/**
	 * Once connected.
	 * 
	 * param connectionHint Bundle of data provided to clients by 
	 * 						Google Play services. May be null if no 
	 * 						content is provided by the service.
	 */
	public void setConnectionHint(Bundle connectionHint) {
		this.connectionHint = connectionHint;
	}
	
	/**
	 * Called when the client is temporarily in a disconnected state. 
	 * This can happen if there is a problem with the remote service (e.g. 
	 * a crash or resource problem causes it to be killed by the system). 
	 * When called, all requests have been canceled and no outstanding 
	 * listeners will be executed. GoogleApiClient will automatically 
	 * attempt to restore the connection
	 * 
	 * @param connectionSuspendedCause
	 */
	public void setConnectionSuspendedCause(int connectionSuspendedCause) {
		this.connectionSuspendedCause = connectionSuspendedCause;
		
		//We try to get the cause name.
		try {
			connectionSuspendedCauseName = ConnectionSuspendedCause.getCause(connectionSuspendedCause).name();
		} catch (TBDriveException e) {
			connectionSuspendedCauseName = null;
			Log.e(TBDrive.TAG, "Error getting suspended cause name from code: " + connectionSuspendedCause + "[" + e.getMessage() + "]");
		}
	}
	
	
}
