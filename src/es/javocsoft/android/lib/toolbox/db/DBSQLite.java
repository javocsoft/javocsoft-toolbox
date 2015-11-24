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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is the base class to use SQLite in an
 * Android application.<br><br>
 * 
 * When using SQLite, extend this class to create your
 * database implementing {@link DBSQLite#doOnCreate 
 * and {@link DBSQLite#doOnUpgrade}.
 * 
 * See:<br><br>
 * http://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html 
 * 
 * @author javocSoft 2015.
 * @since  2015
 */
public abstract class DBSQLite extends SQLiteOpenHelper {
	
	protected static String DATABASE_NAME = "database.db";
	protected static int DATABASE_VERSION = 1;

	
	
	public DBSQLite(Context context, String dbName, int dbVersion) {
		super(context, (dbName!=null?dbName:DATABASE_NAME), null, (dbVersion>0?dbVersion:DATABASE_VERSION));
		if(dbName!=null)
			DATABASE_NAME = dbName;
		if(dbVersion>0)
			DATABASE_VERSION = dbVersion;
	}
	
		

	@Override
	public void onCreate(SQLiteDatabase db) {
		doOnCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		doOnUpgrade(db, oldVersion, newVersion);
	}

	
	/** Implement this medthod to create the database. 
	 * See {@link SQLiteOpenHelper#onCreate}. */
	public abstract void doOnCreate(SQLiteDatabase db);
	
	/** Implement this method to make changes when database 
	 * version changes without loosing existing data. 
	 * See {@link SQLiteOpenHelper#onUpgrade}. */
	public abstract void doOnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
