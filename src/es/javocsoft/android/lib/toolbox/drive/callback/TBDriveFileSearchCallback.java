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

import java.util.List;

import android.content.Context;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.Metadata;

/**
 * Base callback class to use when searching for files in Drive.
 *  
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBDriveFileSearchCallback extends Thread implements Runnable {
	
	protected List<FileInfo> searchResult = null;
	
	protected int errorCode;
	protected String errorMessage;
	protected String errorDetails;
	
	protected Context context = null;
	
	public TBDriveFileSearchCallback(Context context) {
		this.context = context;
	}

	
	
	@Override
	public void run() {
		doWork();
	}
	
	
	public abstract void doWork();
	
	
	public void setSearchResult(List<FileInfo> searchResult) {
		this.searchResult = searchResult;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	
	
	public static class FileInfo {
		DriveFile driveFile;
		Metadata metaData;
		
		
		public FileInfo(DriveFile driveFile, Metadata metaData) {
			super();
			this.driveFile = driveFile;
			this.metaData = metaData;
		}

		
		public DriveFile getDriveFile() {
			return driveFile;
		}

		public Metadata getMetaData() {
			return metaData;
		}
	}
}


