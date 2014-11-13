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
package es.javocsoft.android.lib.toolbox.gcm.exception;


/**
 * Class to hold GCM exceptions.
 *  
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class GCMException extends Exception{
	
	public static final int NO_ERROR_GCM = 0;
	
	/** When the device does not supports GCM */
	public static final int ERROR_GCM_DEVICE_NOT_SUPPORTED = -1;
	/** When the device doe snot have installed the Google Play Services
	 * or are disabled */
	public static final int ERROR_GCM_NOT_ENABLED_INSTALL = -2;
	/** When there is an error saving the registrationId in the 
	 * application's preferences. */
	public static final int ERROR_GCM_SAVING_REGID = -3;
	/** When the is an eror getting the registrationId from Google
	 * servers. */
	public static final int ERROR_GCM_GETTING_REGID = -4;
	/** When there are problems unregistering from GCM. */
	public static final int ERROR_GCM_UNREG = -5;
	
	/** When the activity to call when a notification is received can
	 * not be found. */
	public static final int ERROR_GCM_NOACTTOCALL = -6;
	
	public int errorCode;
	
	
	public GCMException(int errorCode){
		super();
		this.errorCode = errorCode;
	}
	
	public GCMException(int errorCode, Throwable cause){
		super(cause);
		this.errorCode = errorCode;
	}

	public GCMException(int errorCode, String msg){
		super(msg);
		this.errorCode = errorCode;
	}

	public GCMException(int errorCode, String msg, Throwable cause){
		super(msg,cause);
		this.errorCode = errorCode;
	}
}