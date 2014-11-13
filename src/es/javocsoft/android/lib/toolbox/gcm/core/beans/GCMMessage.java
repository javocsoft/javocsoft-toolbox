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
import java.util.Map;

/**
 * This class contains a GCM message required
 * fields in order to get a valid JSON for a
 * delivery request.<br><br>
 * 
 * A message for a GCM delivery request has 
 * the format:<br><br>
 * 
 * <pre>
 * { "collapse_key": "score_update",
 *   "time_to_live": 108,
 *   "delay_while_idle": true,
 *   "data": {
 *     "score": "4x8",
 *     "time": "15:16.2342"
 *   },
 *   "registration_ids":["4", "8", "15", "16", "23", "42"]
 * }
 * </pre>
 * 
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
public class GCMMessage {
	
	public String collapse_key;
	public Long time_to_live;
	public Boolean delay_while_idle;
	
	public Map<String,String> data;
	
	public List<String> registration_ids;
	
}
