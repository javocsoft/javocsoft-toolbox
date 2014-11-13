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
package es.javocsoft.android.lib.toolbox.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.util.Log;


/**
 * Class to unzip zipped files.
 *
 * Example of usage:
 * 
 * 		String zipFile = Environment.getExternalStorageDirectory() + "/files.zip"; 
 *		String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/"; 
 *		 
 *		Unzipper d = new Unzipper(zipFile, unzipLocation, true);
 *		d.unzip();
 * 
 * @author JavocSoft 2013
 * @version 1.0
 */
public class Unzipper {

	private String zipFile;
	private String location;
	private boolean createFolders;
	private int bufferSize;
	
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	
	public Unzipper(String zipFile, String location, boolean createFolders) {
		this(zipFile, location, createFolders, DEFAULT_BUFFER_SIZE);
	}
	
	public Unzipper(String zipFile, String location, boolean createFolders, int bufferSize) {
		this.zipFile = zipFile;
		this.location = location;
		this.createFolders = createFolders;

        this.bufferSize = bufferSize;

		dirChecker("");
	}

	
	/**
	 * Unzips the ZIP file.
	 * 
	 * @return The list of unzziped files.
	 */
	public List<String> unzip() {
		ArrayList<String> res = new ArrayList<String>();
		
		try {
			FileInputStream fin = new FileInputStream(zipFile);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				Log.v("Unzipper", "Unzipping " + ze.getName());

				if (ze.isDirectory()) {
					dirChecker(ze.getName());
				} else {
					byte[] buffer = new byte[bufferSize];
					FileOutputStream fout = new FileOutputStream(location + ze.getName());
					
					int bytesRead = 0;
			        while ((bytesRead = zin.read(buffer, 0, buffer.length)) >= 0) {
			        	fout.write(buffer, 0, bytesRead);
			        }

					zin.closeEntry();
					fout.close();
					
					res.add(location + ze.getName());
				}

			}
			zin.close();
		} catch (Exception e) {
			Log.e("Unzipper", "Unzip Error (" + e.getMessage() + ")", e);
		}
		
		return res;
	}
	
	
	//AUXILIAR
	
	private void dirChecker(String dir) {
		File f = new File(location + dir);

		if (!f.isDirectory()) {
			if(createFolders)
				f.mkdirs();
		}
	}
}