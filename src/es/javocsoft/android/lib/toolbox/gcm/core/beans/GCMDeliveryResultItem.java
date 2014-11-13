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
package es.javocsoft.android.lib.toolbox.gcm.core.beans;

/**
 * Array of objects representing the status of the messages 
 * processed. The objects are listed in the same order as 
 * the request (i.e., for each registration ID in the 
 * request, its result is listed in the same index in 
 * the response).<br><br>
 * 
 * See:<br>
 * 	Error codes : https://developer.android.com/google/gcm/http.html#error_codes<br>
 *  Explanation : https://developer.android.com/google/gcm/http.html<br>
 * 
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
public class GCMDeliveryResultItem {
	
	public static final int ERROR_HTTP_CODE_AUTHZ = 401;
	public static final int ERROR_HTTP_CODE_INTERNAL_SERVER = 500;
	public static final int ERROR_HTTP_CODE_TIMEOUT = 501; //Up to 599.	
	
	public static final String ERROR_STRING_MISSING_REGISTRATION = "MissingRegistration";
	public static final String ERROR_STRING_INVALID_REGISTRATION = "InvalidRegistration";
	public static final String ERROR_STRING_MISMATCH_SENDERID = "MismatchSenderId";
	public static final String ERROR_STRING_NOT_REGISTERED = "NotRegistered";
	public static final String ERROR_STRING_MESSAGE_TOO_BIG = "MessageTooBig";
	/** When an internal GCM exclusive words are used for the "data" parameter */
	public static final String ERROR_STRING_INVALID_DATA_KEY = "InvalidDataKey";
	public static final String ERROR_STRING_INVALID_TTL = "InvalidTtl";
	public static final String ERROR_STRING_UNAVAILABLE = "Unavailable";
	public static final String ERROR_STRING_INTERNAL_SERVER_ERROR = "InternalServerError";
	public static final String ERROR_STRING_INVALID_PACKAGE_NAME = "InvalidPackageName";
	
	
	/** String representing the message when it was 
	 * successfully processed */
	public String message_id;
	
	/**
	 * If set, means that GCM processed the message but it 
	 * has another canonical registration ID for that device, 
	 * so sender should replace the IDs on future requests 
	 * (otherwise they might be rejected). This field is 
	 * never set if there is an error in the request.
	 */
	public String registration_id;
	
	/**
	 * String describing an error that occurred while 
	 * processing the message for that recipient
	 */
	public String error;
}
