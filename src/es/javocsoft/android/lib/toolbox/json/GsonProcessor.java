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
	 * A normal JSON Gson processor instance.
	 * 
	 * @return
	 */
	public Gson getGson() {
		return gson;
	}

	/**
	 * A JSON Gson processor instance. This processor allows to avoid 
	 * fields with 'transient' attribute.
	 * 
	 * @return
	 */
	public Gson getGsonWithTransientFilter() {
		return gsonTransientFilter;
	}

	/**
	 * A JSON Gson processor instance. Allows to avoid fields 
	 * without '@Exposed' annotation.
	 * 
	 * @return
	 */
	public Gson getGsonWithExposedFilter() {
		return gsonExposedFilter;
	}
	
}
