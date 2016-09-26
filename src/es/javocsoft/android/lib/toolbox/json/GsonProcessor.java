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
package es.javocsoft.android.lib.toolbox.json;

import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This singleton is used to get a valid JSON parser.<br><br>
 * 
 * @author JavocSoft 2015
 * @since  2015 
 */
public class GsonProcessor {

	private static GsonProcessor gsonProcessor = null;
	
	/* A Gson instance for JSON parsing. */
	private Gson gson = null;
	/* A Gson instance for JSON parsing. Allows to avoid fields with 'transient' attribute. */
	private Gson gsonTransientFilter = null;
	/* A Gson instance for JSON parsing. Allows to avoid fields without '@Exposed' annotation. */
	private Gson gsonExposedFilter = null;

	/**
	 * The desired GSON processor type:<br>
	 * <ul>
	 * <li>GSONP. Normal GSON processor.</li>
	 * <li>GSONP_NON_TRANSIENT. A GSON processor that only accepts properties of a class that does not have the @transient attribute.</li>
	 * <li>GSONP_ONLY_EXPOSED. A GSON processor that only accepts properties of a class that has the @Expoxed attribute. </li>
	 * </ul>
	 */
	public enum GSON_PROCESSOR_TYPE {GSONP, GSONP_NON_TRANSIENT, GSONP_ONLY_EXPOSED};
	
	public static GsonProcessor getInstance() {
		if (gsonProcessor == null) {
			gsonProcessor = new GsonProcessor();
		}

		return gsonProcessor;
	}
	
	private GsonProcessor(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		//A normal JSON processor
		gson = gsonBuilder.create();
		
		//This allows to avoid fields with the transient attribute
		gsonTransientFilter = gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
		
		//This allows to avoid fields without the @Exposed annotation
		gsonExposedFilter = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
	}

	
	/**
	 * Returns the GSON processor of the desired type.
	 * 
	 * @return
	 */
	public Gson getGson(GSON_PROCESSOR_TYPE type) {
		
		switch (type) {			
			case GSONP_NON_TRANSIENT:
				return gsonTransientFilter;
			case GSONP_ONLY_EXPOSED:
				return gsonExposedFilter;
			default:			
				return gson;
		}
	}

	/**
	 * Deprecated. Use {@link GsonProcessor#getGson}
	 * @return
	 */
	@Deprecated
	public Gson getGson() {
		return gson;
	}
	
	/**
	 * A JSON Gson processor instance. This processor allows to avoid 
	 * fields with 'transient' attribute.
	 * 
	 * Deprecated. Use {@link GsonProcessor#getGson}
	 * 
	 * @return
	 */
	@Deprecated
	public Gson getGsonWithTransientFilter() {
		return gsonTransientFilter;
	}

	/**
	 * A JSON Gson processor instance. Allows to avoid fields 
	 * without '@Exposed' annotation.
	 * 
	 * Deprecated. Use {@link GsonProcessor#getGson}
	 * 
	 * @return
	 */
	@Deprecated
	public Gson getGsonWithExposedFilter() {
		return gsonExposedFilter;
	}
	
}
