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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;

/**
 * This class holds common SQLite commands
 * and utility methods.<br><br>
 * 
 * When using SQLite, extend this class to create DB operations
 * for your application.<br><br>
 * 
 * Use in conjunction with {@link DBSQLite}.<br><br>
 * 
 * See:<br><br>
 * <a href="http://developer.android.com/reference/android/database/sqlite/package-summary.html">SQLite Package Summary</a><br>
 * <a href="http://developer.android.com/training/basics/data-storage/databases.html">Android Database</a>
 * 
 * @author javocSoft 2015.
 * @since  2015
 *
 * @param <T>	A {@link DBSQLite} class type.
 */
public class DBHelper<T extends DBSQLite> {
	
	@SuppressLint("SimpleDateFormat")
	protected static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    protected T mDbHelper;	
	protected SQLiteDatabase mDatabase;
	
	public final long DB_OPERATION_OK = 0;
	public final long DB_ERROR = -1;
	public final long DB_ERROR_CLOSED = -2;
	public final long DB_ERROR_BAD_TYPE = -3;
	public final long DB_ERROR_BAD_DATA = -4;
	public final long DB_ERROR_INSERT_DATA = -5;
	
	public final int DB_ERROR_UNEXPECTED = -100;
	
	/** An SQL query result order types. */
	public static enum QUERY_ORDER {ASC, DESC};

	
	
	
	public DBHelper(T mDbHelper) {
		this.mDbHelper = mDbHelper;
	}

	
	
	// COMMON DB SQLite methods.
	
	
	/**
	 * Opens a SQLiteDatabase database helper.
	 * 
	 * @throws SQLException
	 */
	public synchronized  void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}
	
	/**
	 * Closes a SQLiteDatabase database helper.
	 */
	public synchronized  void close() {
		mDbHelper.close();		
	}
	
	/**
	 * Converts a date to an string.
	 * 
	 * @param date	The date in string format.
	 * @return	The date object formatted and in string format.
	 */
	protected String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}
	
	/**
	 * Converts an string to a date.
	 * 
	 * @param date	The date in string format.
	 * @return	The date object formatted from the specified string.
	 * @throws ParseException
	 */
	protected Date parseDate(String date) throws ParseException {
		return DATE_FORMAT.parse(date);
	}
	
	/**
	 * Gets a IN clause for the specified column and values.
	 * 
	 * @param inColumnName
	 * @param inValues
	 * @return	The IN clause.
	 */
	protected String getInClause(String inColumnName, List<Integer> inValues) {
		StringBuilder sb = new StringBuilder();
		for (int value : inValues) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(value);
		}
		sb.insert(0, inColumnName + " IN (");
		sb.append(")");
		
		return sb.toString();
	}

	
	//DB OPERATIONS
	
	/**
	 * To delete all rows of the specified table.
	 *  
	 * @param tableName 
	 * @return 	The number of deleted rows or a negative value 
	 * 			on error.
	 */	
	public long deleteAll(String tableName) {		
		if(mDatabase.isOpen()) {
			//We set whereClause to "1" to remove all rows and get a count.
			return mDatabase.delete(tableName, "1", null);			
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return DB_ERROR_CLOSED;
		}
	}
		
	/**
	 * To delete rows of a table given the column names and values
	 * for each column.
	 * 
	 * @param tableName
	 * @param whereColumnNames
	 * @param whereValues
	 * @return 	The number of deleted rows or a negative value 
	 * 			on error.
	 */
	public long deleteWhere(String tableName, String[] whereColumnNames, String[] whereValues) {
		if(mDatabase.isOpen()) {
			StringBuilder sbWhere = new StringBuilder();		
			for(String wColumn:whereColumnNames){
				if(sbWhere.length()>0) {
					sbWhere.append(" AND " + wColumn + "=?");
				}else{
					sbWhere.append(wColumn + "=?");
				}
			}				
			return mDatabase.delete(tableName, sbWhere.toString(), whereValues);
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return DB_ERROR_CLOSED;
		}
	}
		
	/**
	 * To delete a row of a table with a column id equal to the specified id.
	 * 
	 * @param tableName
	 * @param idColumnName
	 * @param value
	 * @return 	The number of deleted rows or a negative value 
	 * 			on error.
	 */
	public long deleteById(String tableName, String idColumnName, int value) {
		if(mDatabase.isOpen()) {
			return mDatabase.delete(tableName, idColumnName + "=" + value, null);
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return DB_ERROR_CLOSED;
		}
	}
		
	/**
	 * To delete rows with a column id with values equal to one of the
	 * specified list.
	 * 
	 * @param tableName
	 * @param inColumnName
	 * @param inValues
	 * @return 	The number of deleted rows or a negative value 
	 * 			on error.
	 */
	public long deleteIn(String tableName, String inColumnName, List<Integer> inValues) {
		if(mDatabase.isOpen()) {
			return mDatabase.delete(tableName, getInClause(inColumnName, inValues), null);
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return DB_ERROR_CLOSED;
		}
	}
	
	/**
	 * To delete rows with a column id with values equal to one of the
	 * specified list.
	 * 
	 * @param tableName
	 * @param inColumnName
	 * @param inValues
	 * @return	The number of deleted rows or a negative value 
	 * 			on error.
	 */
	public long deleteIn(String tableName, String inColumnName, Integer[] inValues) {
		return deleteIn(tableName, inColumnName, Arrays.asList(inValues));
	}
	
	/**
	 * A get to a SQLite table. Returns a cursor with the results.
	 * 
	 * @param tableName	The table name to query
	 * @param columns	Optional. If set, only the specified columns will be returned.
	 * @param where		Optional. The WHERE SQL clause (without the WHERE word itself)
	 * @param selection	Optional. If in the where clause ? were specified, then this will
	 * 					contain the values in the same order of the specified ? in the where.
	 * @param groupBy	Optional. The group by SQL clause (without the GROUP BY word itself)
	 * @param having	Optional. The having SQL clause (without the HAVING word itself)
	 * @param orderBy	Optional. The order by SQL clause (without the ORDER BY word itself)
	 * @param orderType	Optional. See {@link DBHelper#QUERY_ORDER}.
	 * @return	A {@link Cursor} object, which is positioned before the first entry. Note that 
	 * 			Cursors are not synchronized, see the documentation for more details.
	 */
	public Cursor get(String tableName, String[] columns,
			String where, String[] selection,
			String groupBy, String having,
			String orderBy, QUERY_ORDER orderType) {
		
		if(mDatabase.isOpen()) {
			return mDatabase.query(tableName, 
					(columns!=null && columns.length>0?columns:null), 
					(where!=null?where:null), (selection!=null && selection.length>0?selection:null), 
					(groupBy!=null?groupBy:null), (having!=null?having:null), 
					(orderBy!=null && orderType!=null?orderBy + " " + orderType.name():null));		
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return null;
		}
	}
	
	/**
	 * A get to a SQLite tables. When the query is about more than a table,
	 * this method should be used.<br><br> 
	 * 
	 * Returns a cursor with the results.
	 * 
	 * @param tables	The tables to query.
	 * @param columns	Optional. If set, only the specified columns will be returned.
	 * @param where		Optional. The WHERE SQL clause (without the WHERE word itself)
	 * @param selection	Optional. If in the where clause ? were specified, then this will
	 * 					contain the values in the same order of the specified ? in the where.
	 * @param groupBy	Optional. The group by SQL clause (without the GROUP BY word itself)
	 * @param having	Optional. The having SQL clause (without the HAVING word itself)
	 * @param orderBy	Optional. The order by SQL clause (without the ORDER BY word itself)
	 * @param orderType	Optional. See {@link DBHelper#QUERY_ORDER}.
	 * @return	A {@link Cursor} object, which is positioned before the first entry. Note that 
	 * 			Cursors are not synchronized, see the documentation for more details.
	 */
	public Cursor getFromTables(String tables, String[] columns,
			String where, String[] selection,
			String groupBy, String having,
			String orderBy, QUERY_ORDER orderType) {
		
		if(mDatabase.isOpen()) {
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			qb.setTables(tables);		
			
			return qb.query(mDatabase, (columns!=null && columns.length>0?columns:null), 
					(where!=null?where:null), (selection!=null && selection.length>0?selection:null), 
					(groupBy!=null?groupBy:null), (having!=null?having:null), 
					(orderBy!=null && orderType!=null?orderBy + " " + orderType.name():null));
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return null;
		}
		
	}
	
	/**
	 * Inserts an object in the database.
	 * 
	 * @param t	Must be of type {@link DBTable}.
	 * @param fieldNames	Optional. If specified, the fields of the table. 
	 * 						They must be in the same order of t fields.
	 * @return	Returns {@link DB_OPERATION_OK} in case of no errors, otherwise a
	 * 			number minor than 0 indicating the error. 
	 */
	protected long insert(DBTable o, String[] fieldNames) {
		long res = -1;
		
		if(mDatabase.isOpen()) {
			
			if(o.getClass().getSuperclass()!=null && 
					o.getClass().getSuperclass()==DBTable.class) {
				
				//We prepare field names.
				Field[] fields = o.getClass().getFields();
				if(fieldNames==null){
					fieldNames = new String[fields.length];
					for(int i = 0; i < fields.length; i++) {
						fieldNames[i] = fields[i].getName();
					}
				}
				
				if(fields.length!=fieldNames.length) {
					//Both should match
					res = DB_ERROR_BAD_DATA;
				}else{
					ContentValues contentValues = new ContentValues();
					
					try{
						//We iterate through fields adding the values to the columns.
						//(fieldColumns and object fields must be in the same order)
						for(int i = 0; i < fields.length; i++) {
					        Class<?> fieldType = fields[i].getType();
		
					        if(String.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (String) fields[i].get(o));
					        } else if(Integer.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Integer) fields[i].get(o));		        
					        } else if(Long.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Long) fields[i].get(o));		        
					        } else if(Short.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Short) fields[i].get(o));		            
					        } else if(Float.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Float) fields[i].get(o));
					        } else if(Double.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Double) fields[i].get(o));		        
					        } else if(Boolean.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Boolean) fields[i].get(o));		            
					        } else if(Byte.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Byte) fields[i].get(o));		            
					        } else if(byte[].class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (byte[]) fields[i].get(o));
					            
					        } else if(int.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Integer) fields[i].get(o));		            
					        } else if(long.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Long) fields[i].get(o));		            
					        } else if(short.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Short) fields[i].get(o));		            
					        } else if(float.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Float) fields[i].get(o));
					        } else if(double.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Double) fields[i].get(o));		        
					        } else if(boolean.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Boolean) fields[i].get(o));		            
					        } else if(byte.class.isAssignableFrom(fieldType)) {
					            contentValues.put(fieldNames[i], (Byte) fields[i].get(o));		            
					        } else {
					        	//Not valid type.
					        	Log.w(ToolBox.TAG, "Bad field type [" + fieldType + "] for inserting data in SQLite.");
					        }
						}
						
						if(contentValues.size()>0) {
							long rowId = mDatabase.insert( (DBTable.class.cast(o)).tableName, null, contentValues);
							res = (rowId!=-1?rowId:DB_ERROR);
						}else{
							res = DB_ERROR_INSERT_DATA;
						}
						
					}catch(IllegalAccessException e){
						Log.e(ToolBox.TAG, "Error inserting data in SQLite [" + e.getMessage() + "]", e);
						res = DB_ERROR_UNEXPECTED;
					}
				}
			}else{
				Log.w(ToolBox.TAG, "Database is not opened!");
				return DB_ERROR_CLOSED;
			}
		}else{
			Log.e(ToolBox.TAG, "Error inserting data in SQLite [Object must be of DBTable type]");
			return DB_ERROR_BAD_TYPE;
		}
		
		return res;
	}
	
	/**
	 * Inserts a list of objects in the database.
	 * 
	 * @param oList The list must be a list of objects of type {@link DBTable}.
	 * @return	Returns {@link DB_OPERATION_OK} in case of no errors, otherwise a
	 * 			number minor than 0 indicating the error. 
	 */
	protected long insert(List<DBTable> oList) {
		
		long res = DB_ERROR;
		
		mDatabase.beginTransaction();
		for(DBTable o:oList) {
			if((res = insert(o, null))!=DB_OPERATION_OK){				
				break;
			}
		}
		
		if(res==DB_OPERATION_OK) {
			//All went OK so we mark all transactions as OK so
			//they are committed
			mDatabase.setTransactionSuccessful();
		}
		mDatabase.endTransaction();
		
		return res;
	}
	
	/**
	 * Update rows of a table given the where clause and content to be
	 * updated.
	 * 
	 * @param tableName			The table to update
	 * @param whereColumnNames	The columns for the conditions.
	 * @param whereValues		The values of the columns in the WHERE clause.
	 * @param contentValues		The content to update in the table (fields and values). 
	 * 							See {@link #ContentValues}
	 * @return 	The number of updated rows or a negative value on error.
	 */
	public long updateWhere(String tableName, String[] whereColumnNames, String[] whereValues, ContentValues contentValues) {
		if(mDatabase.isOpen()) {
			StringBuilder sbWhere = new StringBuilder();		
			for(String wColumn:whereColumnNames){
				if(sbWhere.length()>0) {
					sbWhere.append(" AND " + wColumn + "=?");
				}else{
					sbWhere.append(wColumn + "=?");
				}
			}				
			return mDatabase.update(tableName, contentValues, sbWhere.toString(), whereValues);
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return DB_ERROR_CLOSED;
		}
	}
	
	/**
	 * Update rows of a table given the where clause and content to be
	 * updated.
	 * 
	 * @param tableName			The table to update
	 * @param whereColumnNames	The where clause (use ?)
	 * @param whereValues		The values of the WHERE clause (the values of the "?")
	 * @param contentValues		The content to update in the table (fields and values). 
	 * 							See {@link #ContentValues}
	 * @return 	The number of updated rows or a negative value on error.
	 */
	public long updateWhere(String tableName, String whereClause, String[] whereValues, ContentValues contentValues) {
		if(mDatabase.isOpen()) {							
			return mDatabase.update(tableName, contentValues, whereClause, whereValues);
		}else{
			Log.w(ToolBox.TAG, "Database is not opened!");
			return DB_ERROR_CLOSED;
		}
	}
	
}
