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
package es.javocsoft.android.lib.toolbox.db;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Any table for your SQLite schema must extend 
 * this class.
 * 
 * @author javocSoft 2015.
 * @since  2015
 *
 */
public class DBTable {

	protected String tableName = null;
	
	
	public DBTable(String tableName) {
		this.tableName = tableName;
	}

	
	/**
	 * Returns an array containing the public fields 
	 * of the table object.
	 * 
	 * @return	The array with all public fields.
	 */
	public static String[] getColumnsAsArray(Class<? extends DBTable> o) {
		String[] res = null;
		Field[] fields = o.getFields();
		if(fields!=null){
			res = new String[fields.length];
			for(int i = 0; i < fields.length; i++) {
				res[i] = fields[i].getName();
			}
		}
		
		return res;
	}
	
	/**
	 * Returns a list containing the public fields 
	 * of the table object.
	 *  
	 * @return	The list with all public fields.
	 */
	public static List<String> getColumnsAsList(Class<? extends DBTable> o) {
		return Arrays.asList(getColumnsAsArray(o));
	}
}
