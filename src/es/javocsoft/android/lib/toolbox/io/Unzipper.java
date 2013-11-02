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
 * Class To unzip zipped files.
 * 
 * (See also http://stackoverflow.com/questions/8295496/unzip-file-in-android)
 * 
 * Example of usage:
 * 
 * 		String zipFile = Environment.getExternalStorageDirectory() + "/files.zip"; 
 *		String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/"; 
 *		 
 *		Decompress d = new Decompress(zipFile, unzipLocation); 
 *		d.unzip();
 * 
 * @author JavocSoft 2013
 * 
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