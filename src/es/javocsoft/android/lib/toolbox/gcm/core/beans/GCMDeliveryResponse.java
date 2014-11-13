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

import java.util.List;


/**
 * This class conatins an HTTP GCM delivery sesult
 * response.<br><br>
 * 
 * The format of a response is as follows (an example):<br><br>
 * 
 * <pre>
 * { "multicast_id": 216,
 *	  "success": 3,
 *	  "failure": 3,
 *	  "canonical_ids": 1,
 *	  "results": [
 *	    { "message_id": "1:0408" },
 *	    { "error": "Unavailable" },
 *	    { "error": "InvalidRegistration" },
 *	    { "message_id": "1:1516" },
 *	    { "message_id": "1:2342", "registration_id": "32" },
 *	    { "error": "NotRegistered"}
 *	  ]
 *	}
 * </pre>
 *
 * See https://developer.android.com/google/gcm/http.html
 * 
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
public class GCMDeliveryResponse {

	public String multicast_id;
	
	public int success;
	public int failure;
	public int canonical_ids;
	
	/** The detailed information about each message delivery 
	 * result. See {@link GCMDeliveryResultItem} */
	public List<GCMDeliveryResultItem> results;
	
}
