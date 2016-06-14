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
package es.javocsoft.android.lib.toolbox.drive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFile.DownloadProgressListener;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource.MetadataResult;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.SortableField;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveConnectionCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveFileCreationOpCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveFileOpenCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveFileSearchCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveFileSearchCallback.FileInfo;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveGetFileCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveGetMetadataOpCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDrivePinFileCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveTrashOpCallback;
import es.javocsoft.android.lib.toolbox.drive.callback.TBDriveWriteFileOpCallback;
import es.javocsoft.android.lib.toolbox.drive.exception.TBDriveException;
import es.javocsoft.android.lib.toolbox.drive.listener.TBChangeListener;
import es.javocsoft.android.lib.toolbox.drive.listener.TBDriveEventService;

/**
 * Google Drive integration.<br><br>
 * <ol>
 * 	<li>Authorize the application, see 
 * 	<a href="https://developers.google.com/drive/android/auth">Drive AUTH</a>.</li>
 * 	<li>Get an instance of TBDrive by using getInstance()</li> 
 * 	<li>Add drive_onPause() in your Activity's onPause().</li>
 * 	<li>Add in your Activity's onActivityResult the call to drive_checkForResolutionResult().</li>
 * 	<li>Call drive_connect() to connect with Google Drive.</li>
 * </ol>
 * 
 * Use TBDrive.drive_disconnect() to disconnect from Google Drive.<br><br>
 * 
 * All methods can be use in asynchronous mode (with callbacks) or in synchronous mode.<br><br>
 * 
 * For more information, see: 
 * <ul>
 * 	<li><a href="https://developers.google.com/drive/android/intro">Google Drive API</a></li>
 * 	<li><a href="https://developers.google.com/android/reference/com/google/android/gms/drive/package-summary">Google Drive API Reference</a></li>
 * </ul>
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public class TBDrive implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	public static final String TAG = "ToolBox::BaseDriveActivity";
	
	public static final int ERROR_FILE_NOT_FOUND = -1;
	public static final int ERROR_FILE_SEARCH = -2;
	public static final int ERROR_DRIVE_GET_CONTENTS = -3;
	public static final int ERROR_FILE_CREATE = -4;
	public static final int ERROR_FILE_CREATE_WRITE_CONTENT = -5;
	public static final int ERROR_FILE_GET_METADATA = -6;
	public static final int ERROR_FILE_TRASH_ALREADY_TRASHED = -7;
	public static final int ERROR_FILE_TRASH_PERMISSION = -8;
	public static final int ERROR_FILE_TRASH_NOT_IN_TRASH = -9;
	public static final int ERROR_FILE_TRASH = -10;
	public static final int ERROR_FILE_UNTRASH = -11;
	public static final int ERROR_FILE_OPEN = -12;
	public static final int ERROR_FILE_WRITE = -13;
	public static final int ERROR_FILE_PIN = -14;
	
	public static final String ERROR_FILE_NOT_FOUND_STRING = "File not found.";
	public static final String ERROR_FILE_SEARCH_STRING = "File search error.";
	public static final String ERROR_DRIVE_GET_CONTENTS_STRING = "List contents error.";
	public static final String ERROR_FILE_CREATE_STRING = "Error creating file.";
	public static final String ERROR_FILE_CREATE_WRITE_CONTENT_STRING = "Error writing content to new file.";
	public static final String ERROR_FILE_GET_METADATA_STRING = "Error getting drive file metadata.";
	public static final String ERROR_FILE_TRASH_ALREADY_TRASHED_STRING = "File is already in the trash.";
	public static final String ERROR_FILE_TRASH_PERMISSION_STRING = "Resource is not owned by the user or is in the AppFolder.";
	public static final String ERROR_FILE_TRASH_NOT_IN_TRASH_STRING = "File is not in the trash.";
	public static final String ERROR_FILE_TRASH_STRING = "Error sending to trash the file.";
	public static final String ERROR_FILE_UNTRASH_STRING = "Error recovering from trash the file.";
	public static final String ERROR_FILE_OPEN_STRING = "Error recovering from trash the file.";
	public static final String ERROR_FILE_WRITE_STRING = "Error writing the file.";
	public static final String ERROR_FILE_PIN_STRING = "Error pinning the file.";
	
	
	/**
     * Request code for auto Google Play Services error resolution.
     */
    protected static final int REQUEST_CODE_RESOLUTION = 1;
    
    /**
     * Next available request code.
     */
    protected static final int NEXT_AVAILABLE_REQUEST_CODE = 2;
	
	private static TBDrive tbDrive;
	private Activity activity;
	private Context context;
	private TBDriveConnectionCallback onConnFailureCallback;
	private TBDriveConnectionCallback onConnCallback;
	private TBDriveConnectionCallback onConnSuspendedCallback;
	
	
	/**
	 * Google API client.
	 */
	private static GoogleApiClient mGoogleApiClient;

	private TBDrive(Activity activity) {
		this.activity = activity;
		this.context = activity.getApplicationContext();
	}

	/**
	 * 
	 * @param context
	 */
	public static TBDrive getInstance(Activity activity, 
			TBDriveConnectionCallback onConnFailureCallback,
			TBDriveConnectionCallback onConnCallback,
			TBDriveConnectionCallback onConnSuspendedCallback) {
		
		if(tbDrive==null) {
			tbDrive = new TBDrive(activity);
			tbDrive.onConnCallback = onConnCallback;
			tbDrive.onConnFailureCallback = onConnFailureCallback;
			tbDrive.onConnSuspendedCallback = onConnSuspendedCallback;
			
			initialize();
		}
		
		return tbDrive;
	}
	
	
	
	
	private static void initialize() {
		if (mGoogleApiClient == null) {
			mGoogleApiClient = new GoogleApiClient.Builder(tbDrive.getContext())
					.addApi(Drive.API).addScope(Drive.SCOPE_FILE)
					.addScope(Drive.SCOPE_APPFOLDER)
					// required for App Folder sample
					.addConnectionCallbacks(tbDrive)
					.addOnConnectionFailedListener(tbDrive).build();
		}
	}	

	/**
	 * Connects with Drive
	 */
	public void drive_connect() {
		if (getGoogleApiClient() != null) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					getGoogleApiClient().connect();					
				}
			}).start();
		}
	}
	
	/**
	 * Disconnects from Drive
	 */
	public void drive_disconnect() {
		if (getGoogleApiClient() != null) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					getGoogleApiClient().disconnect();					
				}
			}).start();
		}
	}
	
	/**
	 * Get the specified context to the ToolBox drive instance.
	 * 
	 * @return
	 */
	public Context getContext() {
		return context;
	}
	
	/**
	 * Gets the Google API Client instance.
	 * 
	 * @return
	 */
	public GoogleApiClient getGoogleApiClient()  {
		return mGoogleApiClient;
	}
	
	/**
	 * To be used in onPause() of an Activity.
	 */
	public void drive_onPause()  {
		if (mGoogleApiClient != null) {
			drive_disconnect();
        }     
	}
	
	/**
     * When there was an error in connection with Drive but resolution
     * is available, this handles resolution callbacks. use it on
     * onActivityResult() activity method.
     * 
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void drive_checkForResolutionResult(int requestCode, int resultCode,
            Intent data) {
    	if (requestCode == REQUEST_CODE_RESOLUTION && resultCode == Activity.RESULT_OK) {
    		getGoogleApiClient().connect();
        }
    }
    
    
	//FILE MANIPULATION
    
    
    /**
     * Gets a Drive file metadata information.
     * 
     * @param driveFile	The Drive file
     * @param callback See {@link TBDriveGetMetadataOpCallback}
     */
    public void async_getDriveFileMetadata(DriveFile driveFile, final TBDriveGetMetadataOpCallback callback) {
    	
    	final ResultCallback<MetadataResult> fileGetMetadataCallback = new
	            ResultCallback<MetadataResult>() {
	        
			@Override
	        public void onResult(MetadataResult result) {
	            if (!result.getStatus().isSuccess()) {
	            	Log.e(TAG, "Problem getting drive file metadata: '" + result.getStatus().getStatusMessage() + "'");
	                if(callback!=null) {
    					callback.setErrorCode(ERROR_FILE_GET_METADATA);
    					callback.setErrorMessage(ERROR_FILE_GET_METADATA_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}
	                return;
	            }
	            
	            if(callback!=null) {
	            	callback.setDriveFileMetaData(result.getMetadata());
	            	callback.run();
				}
	        }
	    };
    	
    	driveFile.getMetadata(getGoogleApiClient())
    		.setResultCallback(fileGetMetadataCallback);
    }
    
    /**
     * Gets a Drive file metadata information.
     * 
     * @param driveFile
     * @return	The file metadata, see {@link MetadataResult}
     */
    public MetadataResult sync_getDriveFileMetadata(DriveFile driveFile) throws TBDriveException {
    	
    	MetadataResult result = driveFile.getMetadata(getGoogleApiClient()).await();    	
    	if (!result.getStatus().isSuccess()) {
        	Log.e(TAG, "Problem getting drive file metadata: '" + result.getStatus().getStatusMessage() + "'");
        	throw new TBDriveException("Problem getting drive file metadata: '" + result.getStatus().getStatusMessage() + "'");
        }
	    
	    return result;
    }
        
    /**
     * Create a file.
     * 
     * @param title		The title/file-name for the file.
     * @param mimeType	The mime type
     * @param file		The file to upload.
     * @param callback See {@link TBDriveFileCreationOpCallback}
     * @param useAppFolder 	If set to TRUE, app folder will be used. Files created
	 * 						in such folder can be directly deleted. If set to FALSE,
	 * 						root folder of Drive will be used to store the file.<br>
	 * 						The App Folder is a special folder that is only accessible 
	 * 						by your application. Its content is hidden from the user 
	 * 						and from other apps. Despite being hidden from the user, 
	 * 						the App Folder is stored on the user's Drive and therefore 
	 * 						uses the user's Drive storage quota. The App Folder can be 
	 * 						used to store configuration files, temporary files, or any 
	 * 						other types of files that belong to the user but should not 
	 * 						be tampered with. See <a href="https://developers.google.com/drive/android/appfolder">
	 * 						Google Drive App Folder</a>.
     */
	public void async_createFile(final String title, final String mimeType, final File file, 
			final TBDriveFileCreationOpCallback callback, final boolean useAppFolder) {
		
		// Callback for a file creation operation
		final ResultCallback<DriveFileResult> fileCreationCallback = new
	            ResultCallback<DriveFileResult>() {
	        
			@Override
	        public void onResult(DriveFileResult result) {
	            if (!result.getStatus().isSuccess()) {
	            	Log.e(TAG, "Problem while creating file: '" + result.getStatus().getStatusMessage() + "'");
	                if(callback!=null) {
    					callback.setErrorCode(ERROR_FILE_CREATE);
    					callback.setErrorMessage(ERROR_FILE_CREATE_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}
	                return;
	            }
	            
	            Log.i(TAG, "Created a file with DriveId: " + result.getDriveFile().getDriveId());
	            if(callback!=null) {
	            	callback.setDriveFile(result.getDriveFile());
	            	callback.run();
				}
	        }
	    };
		
		final ResultCallback<DriveContentsResult> driveContentsCallback = new
	            ResultCallback<DriveContentsResult>() {
			
	        @Override
	        public void onResult(DriveContentsResult result) {
	            if (!result.getStatus().isSuccess()) {
	            	Log.e(TAG, "Error getting drive contents: '" + result.getStatus().getStatusMessage() + "'");
	                if(callback!=null) {
    					callback.setErrorCode(ERROR_DRIVE_GET_CONTENTS);
    					callback.setErrorMessage(ERROR_DRIVE_GET_CONTENTS_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}	            	
	            	return;
	            }	            
	            
	            final DriveContents driveContents = result.getDriveContents();

	            // Perform I/O off the UI thread.
	            new Thread() {
	                
	            	@Override
	                public void run() {
	            		BufferedInputStream fis = null;
	            		BufferedOutputStream fos = null;
	            		OutputStream outputStream = null;
	            		/*FileOutputStream fos = null;
	            		Writer writer = null;*/
	            		
	            		
	            		try {
	            			fis = new BufferedInputStream(new FileInputStream(file));
	            			
	            			/*
	            			fos = new FileOutputStream(driveContents.getParcelFileDescriptor().getFileDescriptor());
	            			writer = new OutputStreamWriter(fos);*/
	            			
	            			outputStream = driveContents.getOutputStream();
		            		fos = new BufferedOutputStream(outputStream);
		            		
		            		//Read and write in Drive the contents
		            		int read;
		            		while ((read = fis.read()) >= 0) {
		            			//writer.write(read);
		            			fos.write(read);
		            		}		            		
	            		
	                    } catch (IOException e) {
	                        Log.e(TAG, "Error writing contents '" + e.getMessage() + "'", e);
	                        if(callback!=null) {
	        					callback.setErrorCode(ERROR_FILE_CREATE_WRITE_CONTENT);
	        					callback.setErrorMessage(ERROR_FILE_CREATE_WRITE_CONTENT_STRING);
	        					callback.setErrorDetails(e.getMessage());
	        					callback.run();
	        				}
	                    } finally {
	                    	try {
	                    		fis.close();	                    	
	                    		fos.close();
	                    		outputStream.close();
	                    		/*writer.close();
	                    		fos.close();*/
	                    	}catch(Exception e){}
	                    }
	                    
	                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
	                            .setTitle(title)
	                            .setMimeType(mimeType)
	                            .setStarred(true).build();

	                    if(useAppFolder) {
	                    	Drive.DriveApi.getAppFolder(getGoogleApiClient())
            						.createFile(getGoogleApiClient(), changeSet, driveContents)
            						.setResultCallback(fileCreationCallback);
	                    }else{
	                    	//Create a file on root folder	                    
		                    Drive.DriveApi.getRootFolder(getGoogleApiClient())
		                            .createFile(getGoogleApiClient(), changeSet, driveContents)
		                            .setResultCallback(fileCreationCallback);
	                    }
	                    
	                }
	            }.start();
	        }	        
	    };
	    
	    //Create new file
	    Drive.DriveApi.newDriveContents(getGoogleApiClient())
        .setResultCallback(driveContentsCallback);	    
	}
	
	/**
	 * Create a file from a set of bytes.
     * 
     * @param title		The title/file-name for the file.
     * @param mimeType	The mime type 
	 * @param data		The data byte array.
	 * @param callback See {@link TBDriveFileCreationOpCallback}
	 * @param useAppFolder 	If set to TRUE, app folder will be used. Files created
	 * 						in such folder can be directly deleted. If set to FALSE,
	 * 						root folder of Drive will be used to store the file.<br>
	 * 						The App Folder is a special folder that is only accessible 
	 * 						by your application. Its content is hidden from the user 
	 * 						and from other apps. Despite being hidden from the user, 
	 * 						the App Folder is stored on the user's Drive and therefore 
	 * 						uses the user's Drive storage quota. The App Folder can be 
	 * 						used to store configuration files, temporary files, or any 
	 * 						other types of files that belong to the user but should not 
	 * 						be tampered with. See <a href="https://developers.google.com/drive/android/appfolder">
	 * 						Google Drive App Folder</a>.
	 */
	public void async_createFile(final String title, final String mimeType, final byte[] data, 
			final TBDriveFileCreationOpCallback callback, final boolean useAppFolder) {
		
		// Callback for a file creation operation
		final ResultCallback<DriveFileResult> fileCreationCallback = new
	            ResultCallback<DriveFileResult>() {
	        
			@Override
	        public void onResult(DriveFileResult result) {
	            if (!result.getStatus().isSuccess()) {
	            	Log.e(TAG, "Problem while creating file: '" + result.getStatus().getStatusMessage() + "'");
	                if(callback!=null) {
    					callback.setErrorCode(ERROR_FILE_CREATE);
    					callback.setErrorMessage(ERROR_FILE_CREATE_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}
	                return;
	            }
	            
	            Log.i(TAG, "Created a file with DriveId: " + result.getDriveFile().getDriveId());
	            if(callback!=null) {
	            	callback.setDriveFile(result.getDriveFile());
	            	callback.run();
				}
	        }
	    };
		
		final ResultCallback<DriveContentsResult> driveContentsCallback = new
	            ResultCallback<DriveContentsResult>() {
			
	        @Override
	        public void onResult(DriveContentsResult result) {
	            if (!result.getStatus().isSuccess()) {
	            	Log.e(TAG, "Error getting drive contents: '" + result.getStatus().getStatusMessage() + "'");
	                if(callback!=null) {
    					callback.setErrorCode(ERROR_DRIVE_GET_CONTENTS);
    					callback.setErrorMessage(ERROR_DRIVE_GET_CONTENTS_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}	            	
	            	return;
	            }	            
	            
	            final DriveContents driveContents = result.getDriveContents();

	            // Perform I/O off the UI thread.
	            new Thread() {
	                
	            	@Override
	                public void run() {
	            		ByteArrayInputStream in = null;
	            		BufferedInputStream fis = null;
	            		BufferedOutputStream fos = null;
	            		OutputStream outputStream = null;
	            		/*FileOutputStream fos = null;
	            		Writer writer = null;*/
	            		
	            		try {
	            			in = new ByteArrayInputStream(data);
	            			fis = new BufferedInputStream(in);	            			
	            			outputStream = driveContents.getOutputStream();
		            		fos = new BufferedOutputStream(outputStream);
	            			
		            		/*
	            			fos = new FileOutputStream(driveContents.getParcelFileDescriptor().getFileDescriptor());
	            			writer = new OutputStreamWriter(fos);*/
		            		
		            		//Read and write in Drive the contents		            				            		
		            		int read;		            				            		
		            		while ((read = fis.read()) >= 0) {
		            			fos.write(read);
		            			//writer.write(read);
		            		}		            		
	            		
	                    } catch (IOException e) {
	                        Log.e(TAG, "Error writing contents '" + e.getMessage() + "'", e);
	                        if(callback!=null) {
	        					callback.setErrorCode(ERROR_FILE_CREATE_WRITE_CONTENT);
	        					callback.setErrorMessage(ERROR_FILE_CREATE_WRITE_CONTENT_STRING);
	        					callback.setErrorDetails(e.getMessage());
	        					callback.run();
	        				}
	                    } finally {
	                    	try {
	                    		fis.close();	                    	
	                    		fos.close();
	                    		outputStream.close();
	                    		/*writer.close();
	                    		fos.close();*/
	                    	}catch(Exception e){}
	                    }
	                    
	                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
	                            .setTitle(title)
	                            .setMimeType(mimeType)
	                            .setStarred(true).build();

	                    if(useAppFolder) {
	                    	Drive.DriveApi.getAppFolder(getGoogleApiClient())
                    				.createFile(getGoogleApiClient(), changeSet, driveContents)
                    				.setResultCallback(fileCreationCallback);
	                    } else {
	                    	//Create a file on root folder	                    
		                    Drive.DriveApi.getRootFolder(getGoogleApiClient())
		                            .createFile(getGoogleApiClient(), changeSet, driveContents)
		                            .setResultCallback(fileCreationCallback);
	                    }
	                }
	            }.start();
	        }	        
	    };
	    
	    Drive.DriveApi.newDriveContents(getGoogleApiClient())
	        .setResultCallback(driveContentsCallback);
	    
	}
	
	/**
	 * Looks for the specified file name (or title).<br><br>
     * 
     * <b>Note</b><br>
     *  Only looks for the first found file if there is more than one file
     *  with the same filename/title.
     * 
     * @param fileName	The file name or title of the desired file.
     * @param callback	See {@link TBDriveGetFileCallback}
	 * 
	 */
	public void async_getFile(String fileName, final TBDriveGetFileCallback callback) {
    	
		final ResultCallback<DriveApi.MetadataBufferResult> fileGetCallback = 
				new ResultCallback<DriveApi.MetadataBufferResult>() {

					@Override
					public void onResult(MetadataBufferResult result) {
						if (!result.getStatus().isSuccess()) {
				            Log.e(TAG, "Problem while retrieving files: '" + result.getStatus().getStatusMessage() + "'");
				            if(callback!=null) {
		    					callback.setErrorCode(ERROR_FILE_SEARCH);
		    					callback.setErrorMessage(ERROR_FILE_SEARCH_STRING);
		    					callback.setErrorDetails(result.getStatus().getStatusMessage());
		    					callback.run();
		    				}
				            result.release();
				            return;
				        }
				    
				    	Iterator<Metadata> iterator = result.getMetadataBuffer().iterator();            	
				    	Metadata mData = null;
				    	while(iterator.hasNext()) {
				    		mData = iterator.next();
				    		
				    		if(!mData.isFolder()){
				    			DriveFile driveFile = Drive.DriveApi.getFile(getGoogleApiClient(), mData.getDriveId());
				    			if(driveFile==null) {
				    				//Error getting Drive File from id. Should never happen
				    				Log.e(TAG, "Problem while retrieving file with DriveId: " + mData.getDriveId());
				    				if(callback!=null) {
				    					callback.setErrorCode(ERROR_FILE_NOT_FOUND);
				    					callback.setErrorMessage(ERROR_FILE_NOT_FOUND_STRING);
				    					callback.setErrorDetails(result.getStatus().getStatusMessage());
				    					callback.run();
				    				}
				    				result.release();
				    				return;
				    			}
				    			
				    			if(callback!=null) {
					            	callback.setDriveFile(driveFile);
					            	callback.setDriveFileMetaData(mData);
					            	callback.run();
								}
				    		}
				    		break; //Should only be one.
				    	}
				    	
				    	result.release();
					}
		};
		
		//Look for a file with title/filename
        SortOrder sortOrder = new SortOrder.Builder()
        	.addSortAscending(SortableField.TITLE)
        	.addSortDescending(SortableField.MODIFIED_DATE).build();
        
    	Query query = new Query.Builder().addFilter(
    			Filters.and(
    					Filters.eq(SearchableField.TITLE, fileName)
    					, Filters.eq(SearchableField.TRASHED, false)
    					//, Filters.eq(SearchableField.MIME_TYPE, "text/plain")    					
    					//, Filters.eq(customPropertyKey, "world")
    					)
    			)
    			.setSortOrder(sortOrder)
    	        .build();
    	
    	Drive.DriveApi.query(getGoogleApiClient(), query)
    		.setResultCallback(fileGetCallback);
    }
		
    /**
     * Looks for the specified file name (or title).<br><br>
     * 
     * <b>Note</b><br>
     *  Only returns the first found file if there is more than one file
     *  with the same filename/title.
     * 
     * @param fileName	The file name or title of the desired file.
     * @returns DriveFile The desired file or null if there is none. See
     * 					  {@link DriveFile}.
     * 
     */
    public synchronized DriveFile sync_getFile(String fileName) throws TBDriveException {
    	
    	DriveFile driveFile = null;
    	
        //Look for a file with title/filename
        SortOrder sortOrder = new SortOrder.Builder()
        	.addSortAscending(SortableField.TITLE)
        	.addSortDescending(SortableField.MODIFIED_DATE).build();
        
    	Query query = new Query.Builder().addFilter(
    			Filters.and(
    					Filters.eq(SearchableField.TITLE, fileName)
    					, Filters.eq(SearchableField.TRASHED, false)
    					//, Filters.eq(SearchableField.MIME_TYPE, "text/plain")    					
    					//, Filters.eq(customPropertyKey, "world")
    					)
    			)
    			.setSortOrder(sortOrder)
    	        .build();
    	
    	MetadataBufferResult result = Drive.DriveApi.query(getGoogleApiClient(), query).await();
    	
    	if (!result.getStatus().isSuccess()) {
            Log.e(TAG, "Problem while retrieving files: '" + result.getStatus().getStatusMessage() + "'");
            result.release();
            throw new TBDriveException("Problem while retrieving files: '" + result.getStatus().getStatusMessage() + "'");         
        }
    
    	Iterator<Metadata> iterator = result.getMetadataBuffer().iterator();            	
    	Metadata mData = null;
    	while(iterator.hasNext()) {
    		mData = iterator.next();
    		
    		if(!mData.isFolder()){
    			driveFile = Drive.DriveApi.getFile(getGoogleApiClient(), mData.getDriveId());
    			if(driveFile==null) {
    				//Error getting Drive File from id. Should never happen
    				Log.e(TAG, "Problem while retrieving file with DriveId: " + mData.getDriveId());
    				result.release();
    				throw new TBDriveException("Problem while retrieving file with DriveId: " + mData.getDriveId() + ". Not found in Drive!!");
    			}         			
    		}
    		break; //Should only be one.
    	}
    	result.release();
    	
    	return driveFile;
    }
	
	/**
	 * Trashes a file.
	 * 
	 * @param driveFile	The Drive file
	 * @param driveFileMetaData	The Drive file metadata, see {@link Metadata}
	 * @param callback See {@link TBDriveTrashOpCallback}
	 */
	public void async_trashFile(final DriveFile driveFile, final Metadata driveFileMetaData, 
			final TBDriveTrashOpCallback callback) {
		
		/**
	     * Callback when call to trash or untrash is complete.
	     */
	    final ResultCallback<Status> trashStatusCallback =
	    	new ResultCallback<Status>() {
	            @Override
	            public void onResult(Status status) {
	                if (!status.isSuccess()) {
	                	Log.e(TAG, "Problem trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " [" + status.getStatusMessage() + "].");
	                	if(callback!=null) {
	    					callback.setErrorCode(ERROR_FILE_TRASH);
	    					callback.setErrorMessage(ERROR_FILE_TRASH_STRING);
	    					callback.setErrorDetails(status.getStatus().getStatusMessage());
	    					callback.run();
	    				}
	                	return;
	                }
	                
	                if(callback!=null) {
	                	callback.setStatus(status);
    					callback.run();
    				}
	            }
        };
		
        //If a DriveResource is a folder it will only be trashable if all of its children
        //are also accessible to this app.
        if (driveFileMetaData.isTrashable()) {
          if (!driveFileMetaData.isTrashed()) {
        	  driveFile.trash(mGoogleApiClient).setResultCallback(trashStatusCallback);        	  
          } else {
        	  Log.e(TAG, "The file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " is already in trash.");
        	  if(callback!=null) {
					callback.setErrorCode(ERROR_FILE_TRASH_ALREADY_TRASHED);
					callback.setErrorMessage(ERROR_FILE_TRASH_ALREADY_TRASHED_STRING);					
					callback.run();
        	  }
          }
        } else {
        	Log.e(TAG, "Error trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + ", resource is not owned by the user or is in the AppFolder.");
        	if(callback!=null) {
				callback.setErrorCode(ERROR_FILE_TRASH_PERMISSION);
				callback.setErrorMessage(ERROR_FILE_TRASH_PERMISSION_STRING);					
				callback.run();
        	}
        }
	}
	
	/**
	 * Trashes a file.
	 * 
	 * @param driveFile	The Drive file
	 * @param driveFileMetaData	The Drive file metadata, see {@link Metadata}
	 * @returns Boolean. TRUE if file is trashed ok, otherwise FALSE.
	 */
	public boolean sync_trashFile(final DriveFile driveFile, final Metadata driveFileMetaData) throws TBDriveException {
		
		boolean res = false;
		
        //If a DriveResource is a folder it will only be trashable if all of its children
        //are also accessible to this app.
        if (driveFileMetaData.isTrashable()) {
          if (!driveFileMetaData.isTrashed()) {
        	  Status status = driveFile.trash(mGoogleApiClient).await();
        	  if (!status.isSuccess()) {
              	Log.e(TAG, "Problem trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " [" + status.getStatusMessage() + "].");
              	throw new TBDriveException("Problem trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " [" + status.getStatusMessage() + "].");
              }else{
            	  res = true;
              }
        	  
          } else {
        	  Log.e(TAG, "The file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " is already in trash.");
        	  throw new TBDriveException("The file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " is already in trash.");
          }
        } else {
        	Log.e(TAG, "Error trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + ", resource is not owned by the user or is in the AppFolder.");
        	throw new TBDriveException("Error trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + ", resource is not owned by the user or is in the AppFolder.");
        }
        
        return res;
	}
	
	/**
	 * Un-Trashes a file.
	 * 
	 * @param driveFile	The Drive file
	 * @param driveFileMetaData	The Drive file metadata, see {@link Metadata}
	 * @param callback See {@link TBDriveTrashOpCallback}
	 */
	public void async_untrashFile(final DriveFile driveFile, final Metadata driveFileMetaData,
			final TBDriveTrashOpCallback callback) {
		
		/**
	     * Callback when call to trash or untrash is complete.
	     */
	    final ResultCallback<Status> trashStatusCallback =
	    	new ResultCallback<Status>() {
	            @Override
	            public void onResult(Status status) {
	                if (!status.isSuccess()) {
	                	Log.e(TAG, "Problem untrashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " [" + status.getStatusMessage() + "].");
	                	if(callback!=null) {
	    					callback.setErrorCode(ERROR_FILE_UNTRASH);
	    					callback.setErrorMessage(ERROR_FILE_UNTRASH_STRING);
	    					callback.setErrorDetails(status.getStatus().getStatusMessage());
	    					callback.run();
	    				}
	                    return;
	                }
	            }
        };
		
        //If a DriveResource is a folder it will only be trashable if all of its children
        //are also accessible to this app.
        if (driveFileMetaData.isTrashable()) {
        	if (driveFileMetaData.isTrashed()) {
        		driveFile.untrash(mGoogleApiClient).setResultCallback(trashStatusCallback);        	
        	} else {
        		Log.e(TAG, "The file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " is not in the trash.");  
        		if(callback!=null) {
					callback.setErrorCode(ERROR_FILE_TRASH_NOT_IN_TRASH);
					callback.setErrorMessage(ERROR_FILE_TRASH_NOT_IN_TRASH_STRING);					
					callback.run();
        		}
        	}
        } else {
        	Log.e(TAG, "Error trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + ", resource is not owned by the user or is in the AppFolder.");
        	if(callback!=null) {
				callback.setErrorCode(ERROR_FILE_TRASH_PERMISSION);
				callback.setErrorMessage(ERROR_FILE_TRASH_PERMISSION_STRING);					
				callback.run();
        	}            
        }
	}
	
	/**
	 * Un-Trashes a file.
	 * 
	 * @param driveFile	The Drive file
	 * @param driveFileMetaData	The Drive file metadata, see {@link Metadata}
	 * @returns Boolean. TRUE if file is un-trashed ok, otherwise FALSE.
	 */
	public boolean sync_untrashFile(final DriveFile driveFile, final Metadata driveFileMetaData) 
			throws TBDriveException {
		
		boolean res = false;
		
        //If a DriveResource is a folder it will only be trashable if 
		//all of its children are also accessible to this app.
        if (driveFileMetaData.isTrashable()) {
        	if (driveFileMetaData.isTrashed()) {
        		Status status = driveFile.untrash(mGoogleApiClient).await();
        		if (!status.isSuccess()) {
                	Log.e(TAG, "Problem untrashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " [" + status.getStatusMessage() + "].");
                	throw new TBDriveException("Problem untrashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " [" + status.getStatusMessage() + "].");
        		} else {
                	res = true;
                }
        		
        	} else {
        		Log.e(TAG, "The file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " is not in the trash.");  
        		throw new TBDriveException("The file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + " is not in the trash.");
        	}
        } else {
        	Log.e(TAG, "Error trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + ", resource is not owned by the user or is in the AppFolder.");
        	throw new TBDriveException("Error trashing the file '" + driveFileMetaData.getTitle() + "' with DriveId: " + driveFile.getDriveId() + ", resource is not owned by the user or is in the AppFolder.");
        }
        
        return res;
	}
	
	/**
	 * Gets the file contents, see {@link DriveContents}
	 * 
	 * @param driveFile	The Drive file.
	 * @param openMode	The open mode. See {@link DriveFile}
	 * @param downloadProgressListener	If set, opening progress
	 * 									information can be show.
	 * @param callback See {@link TBDriveFileOpenCallback}
	 */
	public void async_getFileContents(DriveFile driveFile, int openMode, 
			DownloadProgressListener downloadProgressListener,
			final TBDriveFileOpenCallback callback) {
		
		ResultCallback<DriveContentsResult> contentsOpenedCallback =
		        new ResultCallback<DriveContentsResult>() {
		    @Override
		    public void onResult(DriveContentsResult result) {
		        if (!result.getStatus().isSuccess()) {
		            // display an error saying file can't be opened
		        	Log.e(TAG, "Problem opening the file [" + result.getStatus().getStatusMessage() + "].");
                	if(callback!=null) {
    					callback.setErrorCode(ERROR_FILE_OPEN);
    					callback.setErrorMessage(ERROR_FILE_OPEN_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}		        	
		            return;
		        }
		        // DriveContents object contains pointers
		        // to the actual byte stream
		        DriveContents contents = result.getDriveContents();
		        if(callback!=null) {
		        	callback.setDriveContents(contents);
					callback.run();
				}
		    }
		};
		
		if(downloadProgressListener!=null) {
			driveFile.open(getGoogleApiClient(), openMode, downloadProgressListener)
			.setResultCallback(contentsOpenedCallback);
		}else{
			driveFile.open(getGoogleApiClient(), openMode, null)
	        .setResultCallback(contentsOpenedCallback);
		}
	}
	
	/**
	 * Gets the file contents, see {@link DriveContents}
	 * 
	 * @param driveFile	The Drive file.
	 * @param openMode	The open mode. See {@link DriveFile}
	 * @param downloadProgressListener	If set, opening progress
	 * 									information can be show.
	 * @returns The Drive file contents, see {@link DriveContents}
	 */
	public DriveContents sync_getFileContents(DriveFile driveFile, int openMode, 
			DownloadProgressListener downloadProgressListener) throws TBDriveException {
		
		DriveContentsResult result = null;
		if(downloadProgressListener!=null) {
			result = driveFile.open(getGoogleApiClient(), openMode, downloadProgressListener).await();
		}else{
			result = driveFile.open(getGoogleApiClient(), openMode, null).await();	        
		}
		
		if (!result.getStatus().isSuccess()) {
            //Display an error saying file can't be opened
        	Log.e(TAG, "Problem opening the file [" + result.getStatus().getStatusMessage() + "].");
        	throw new TBDriveException("Problem opening the file [" + result.getStatus().getStatusMessage() + "].");
            
        }
		
        //DriveContents object contains pointers to the byte stream
        return result.getDriveContents();
	}
	
	/**
	 * Discards the changes to the file contents and close.
	 * 
	 * @param driveContent	the file content changes to discard
	 */
	public void discardChangesAndClose(DriveContents driveContent) {
		driveContent.discard(getGoogleApiClient());		
	}
	
	/**
	 * Writes the changes and closes.
	 * 
	 * @param driveContent	the contents to save.
	 * @param callback See {@link TBDriveWriteFileOpCallback}
	 * @param notifyOnCompletion	If set to TRUE, once an action is completed
	 * 								on Google Drive servers, you will be notified.
	 * 								See {@link TBDriveEventService}.<br>
	 * <b>Note</b>:<br>
	 * To get this working, a Google Play Services service must be added in
	 * applixcatAndroid manifest XML file:<br><br>
	 * 
	 * <b>TBDriveEventListener</b> is a class that must extends TBDriveEventService class.
	 * See {@link TBDriveEventService}.
	 * <br><br>
	 * 
	 * <code>
	 * 	 &lt;service android:name=".<b>TBDriveEventListener</b>" android:exported="true"&gt;<br>
     *   	&lt;intent-filter&gt;<br>
     *      	&lt;action android:name="com.google.android.gms.drive.events.HANDLE_EVENT"/&gt;<br>
     *   	&lt;/intent-filter&gt;<br>
     *   &lt;/service&gt;<br>
	 * </code>
	 * 
	 */
	public void async_commitChangesAndClose(DriveContents driveContent, 
			final TBDriveWriteFileOpCallback callback, boolean notifyOnCompletion) {
		
		MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
	    	.setLastViewedByMeDate(new Date()).build();
		
		if(notifyOnCompletion) {
			ExecutionOptions executionOptions = new ExecutionOptions.Builder()
	    		.setNotifyOnCompletion(true)
	    		.build();
			driveContent.commit(getGoogleApiClient(), changeSet, executionOptions).setResultCallback(new ResultCallback<Status>() {
			    @Override
			    public void onResult(Status result) {
			    	if (!result.getStatus().isSuccess()) {
			            // display an error saying file can't be opened
			        	Log.e(TAG, "Problem commiting changes [" + result.getStatus().getStatusMessage() + "].");
	                	if(callback!=null) {
	    					callback.setErrorCode(ERROR_FILE_WRITE);
	    					callback.setErrorMessage(ERROR_FILE_WRITE_STRING);
	    					callback.setErrorDetails(result.getStatus().getStatusMessage());
	    					callback.run();
	    				}		        	
			            return;
			        }
			    	
			    	if(callback!=null) {
						callback.setStatus(result);
						callback.run();
					}
			    }
			});
		}else{
			driveContent.commit(getGoogleApiClient(), changeSet).setResultCallback(new ResultCallback<Status>() {
			    @Override
			    public void onResult(Status result) {
			    	if (!result.getStatus().isSuccess()) {
			            // display an error saying file can't be opened
			        	Log.e(TAG, "Problem commiting changes [" + result.getStatus().getStatusMessage() + "].");
	                	if(callback!=null) {
	    					callback.setErrorCode(ERROR_FILE_WRITE);
	    					callback.setErrorMessage(ERROR_FILE_WRITE_STRING);
	    					callback.setErrorDetails(result.getStatus().getStatusMessage());
	    					callback.run();
	    				}		        	
			            return;
			        }
			    	
			    	if(callback!=null) {
						callback.setStatus(result);
						callback.run();
					}
			    }
			});
		}
	}
	
	/**
	 * Writes the changes and closes.
	 * 
	 * @param driveContent	the contents to save.
	 * @param notifyOnCompletion	If set to TRUE, once an action is completed
	 * 								on Google Drive servers, you will be notified.
	 * 								See {@link TBDriveEventService}.<br>
	 * <b>Note</b>:<br>
	 * To get this working, a Google Play Services service must be added in
	 * applixcatAndroid manifest XML file:<br><br>
	 * 
	 * <b>TBDriveEventListener</b> is a class that must extends TBDriveEventService class.
	 * See {@link TBDriveEventService}.
	 * <br><br>
	 * 
	 * <code>
	 * 	 &lt;service android:name=".<b>TBDriveEventListener</b>" android:exported="true"&gt;<br>
     *   	&lt;intent-filter&gt;<br>
     *      	&lt;action android:name="com.google.android.gms.drive.events.HANDLE_EVENT"/&gt;<br>
     *   	&lt;/intent-filter&gt;<br>
     *   &lt;/service&gt;<br>
	 * </code>
	 *  
	 * @throws TBDriveException
	 */
	public boolean sync_commitChangesAndClose(DriveContents driveContent, boolean notifyOnCompletion) throws TBDriveException {
		boolean res = false;
		
		MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
	    	.setLastViewedByMeDate(new Date()).build();
		
		Status result = null;
		if(notifyOnCompletion){
			ExecutionOptions executionOptions = new ExecutionOptions.Builder()
		    .setNotifyOnCompletion(true)
		    .build();
			result = driveContent.commit(getGoogleApiClient(), changeSet, executionOptions).await();
		}else{
			result = driveContent.commit(getGoogleApiClient(), changeSet).await();
		}
		
		if (!result.getStatus().isSuccess()) {
            // display an error saying file can't be opened
        	Log.e(TAG, "Problem commiting changes [" + result.getStatus().getStatusMessage() + "].");
        	throw new TBDriveException("Problem commiting changes [" + result.getStatus().getStatusMessage() + "].");            
        }else{
        	res = true;
        }
		
		return res;
	}
	
	/**
	 * Converts the content of the DriveFile to an String.
	 * 
	 * <b>NOTE</b>:<br><br>
	 * 	To use this method, Drive file must be opened in MODE_READ_ONLY.
	 * 
	 * @param driveContent	The Drive file contents, see {@link DriveContents}
	 * @return	The contents in string format.
	 * @throws IOException 	In case of error or if the Drive file was not opened
	 * 						in MODE_READ_ONLY or MODE_READ_WRITE, see {@link DriveFile}.
	 */
	public String drive_fileContentToString(DriveContents driveContent) throws IOException {
		
		String contentsAsString = null;
		
		BufferedReader reader = null;
		if(driveContent.getMode()==DriveFile.MODE_READ_ONLY) {
			reader = new BufferedReader(new InputStreamReader(driveContent.getInputStream()));			
		}else if(driveContent.getMode()==DriveFile.MODE_READ_WRITE) {
			reader = new BufferedReader(new FileReader(driveContent.getParcelFileDescriptor().getFileDescriptor()));			
		}else{
			throw new IOException("Getting text file contents requires MODE_READ_ONLY/MODE_READ_WRITE open mode.");
		}
		
		//Read the file contents
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
		    builder.append(line);
		}
		contentsAsString = builder.toString();
		
		//Close the reader
		try{
			reader.close();
		}catch(Exception e){}
				
		return contentsAsString;
	}
	
	/**
	 * Adds to the end of the file the specified string content data.
	 * 
	 * @param driveContent	The Drive file contents, see {@link DriveContents}
	 * @param data
	 * @return The modified contents.
	 * @throws IOException	In case of any error or bad open mode.
	 */
	public synchronized DriveContents drive_appendTextToFile(DriveContents driveContent, String data) throws IOException {
		
		FileInputStream fileInputStream = null;
		Writer writer = null;
		
		if(driveContent.getMode()==DriveFile.MODE_READ_WRITE) {
			try {
			    ParcelFileDescriptor parcelFileDescriptor = driveContent.getParcelFileDescriptor();
			    
			    //Read to the end of the file just to add to the end
			    fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
			    fileInputStream.read(new byte[fileInputStream.available()]);

			    //Append new data content
			    FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
			    writer = new OutputStreamWriter(fileOutputStream);
			    writer.write(data);		    
				
			} finally {
				try {
					fileInputStream.close();
					writer.close();
				}catch(Exception e){}
			}
		}else{
			throw new IOException("Adding content in a file needs MODE_READ_WRITE open mode.");
		}
		
		
		//Returns the modified contents
		return driveContent;
	}
	
	/**
	 * Replaces the text content of the file with the specified new string content data.
	 * 
	 * <b>NOTE</b>:<br><br>
	 * 	To use this method, Drive file must be opened in MODE_WRITE_ONLY.
	 * 
	 * @param driveContent	The Drive file contents, see {@link DriveContents}
	 * @param data
	 * @return The modified contents.
	 * @throws IOException	In case of any error or bad open mode.
	 */
	public synchronized DriveContents drive_replaceTextOfFile(DriveContents driveContent, String data) throws IOException {
		
		Writer writer = null;
		
		if(driveContent.getMode()==DriveFile.MODE_WRITE_ONLY) {
			try {
			    ParcelFileDescriptor parcelFileDescriptor = driveContent.getParcelFileDescriptor();
			    
			    //Append new data content
			    FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
			    writer = new OutputStreamWriter(fileOutputStream);
			    writer.write(data);		    
				
			} finally {
				try {			
					writer.close();
				}catch(Exception e){}
			}		
		}else{
			throw new IOException("Replacing whole content of a file needs MODE_WRITE_ONLY open mode.");
		}
		
		//Returns the modified contents
		return driveContent;
	}
	
    /**
     * Search for a file.
     * 
     * @param fileName	The filename/title to search for.
     * @param callback See {@link TBDriveFileSearchCallback}
     */
	public void async_search(String fileName, final TBDriveFileSearchCallback callback) {
		
		final ResultCallback<MetadataBufferResult> metadataBufferCallback = new
                ResultCallback<MetadataBufferResult>() {
            @Override
            public void onResult(MetadataBufferResult result) {
                
            	if (!result.getStatus().isSuccess()) {
                    Log.e(TAG, "Problem while retrieving files: '" + result.getStatus().getStatusMessage() + "'");
                    if(callback!=null) {
    					callback.setErrorCode(ERROR_FILE_SEARCH);
    					callback.setErrorMessage(ERROR_FILE_SEARCH_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}
                    result.release();
                    return;
                }
            
            	List<FileInfo> searchResults = new ArrayList<FileInfo>();
            	FileInfo fInfo = null;
            	
            	Iterator<Metadata> iterator = result.getMetadataBuffer().iterator();            	
            	Metadata mData = null;
            	while(iterator.hasNext()) {
            		mData = iterator.next();
            		//Files found!
            		DriveFile driveFile = Drive.DriveApi.getFile(getGoogleApiClient(), mData.getDriveId());
            		if(driveFile!=null) {
            			fInfo = new FileInfo(driveFile, mData);
            			searchResults.add(fInfo);
            		}
            	}
            	
            	if(callback!=null) {
        			callback.setSearchResult(searchResults);
        			callback.run();
        		}
            	
            	result.release();
            }
        };
        
		
		//Look for a file with title/filename
        SortOrder sortOrder = new SortOrder.Builder()
        	.addSortAscending(SortableField.TITLE)
        	.addSortDescending(SortableField.MODIFIED_DATE).build();
        
    	Query query = new Query.Builder().addFilter(
    			
    			Filters.and(
    					Filters.eq(SearchableField.TITLE, fileName)
    					, Filters.eq(SearchableField.TRASHED, false)
    					//, Filters.eq(SearchableField.MIME_TYPE, "text/plain")    					
    					//, Filters.eq(customPropertyKey, "world")
    					)
    			)
    			.setSortOrder(sortOrder)
    	        .build();
    	
    	Drive.DriveApi.query(getGoogleApiClient(), query)
    		.setResultCallback(metadataBufferCallback);
	}
	
	/**
     * Search for a file.
     * 
     * @param fileName	The filename/title to search for.
     * @throws TBDriveFileSearchCallback
     */
	public List<FileInfo> sync_search(String fileName) throws TBDriveException {
		
		List<FileInfo> searchResults = null;
		
		//Look for a file with title/filename
        SortOrder sortOrder = new SortOrder.Builder()
        	.addSortAscending(SortableField.TITLE)
        	.addSortDescending(SortableField.MODIFIED_DATE).build();
        
    	Query query = new Query.Builder().addFilter(
    			
    			Filters.and(
    					Filters.eq(SearchableField.TITLE, fileName)
    					, Filters.eq(SearchableField.TRASHED, false)
    					//, Filters.eq(SearchableField.MIME_TYPE, "text/plain")    					
    					//, Filters.eq(customPropertyKey, "world")
    					)
    			)
    			.setSortOrder(sortOrder)
    	        .build();
    	
    	MetadataBufferResult result = Drive.DriveApi.query(getGoogleApiClient(), query).await();
    	
    	if (!result.getStatus().isSuccess()) {
            Log.e(TAG, "Problem while retrieving files: '" + result.getStatus().getStatusMessage() + "'");
            result.release();
            throw new TBDriveException("Problem while retrieving files: '" + result.getStatus().getStatusMessage() + "'");
        }
    	
    	searchResults = new ArrayList<FileInfo>();
    	
    	DriveFile driveFile = null;
    	FileInfo fInfo = null;    	            	
    	Metadata mData = null;
    	Iterator<Metadata> iterator = result.getMetadataBuffer().iterator();
    	while(iterator.hasNext()) {
    		mData = iterator.next();
    		driveFile = Drive.DriveApi.getFile(getGoogleApiClient(), mData.getDriveId());
    		if(driveFile!=null) {
    			fInfo = new FileInfo(driveFile, mData);
    			searchResults.add(fInfo);
    		}
    	}
    	result.release();
    	
    	return searchResults;
	}
		
	/**
	 * Pinning a file causes the latest version of that file's contents and metadata 
	 * to be downloaded to the local device whenever a new version is available. 
	 * Once a file is pinned by one application it is locally available to all 
	 * applications that have permissions to that file.<br><br>
	 * 
	 * <b>Note</b><br>
	 * Because pinned files are downloaded to the device whenever a new version is 
	 * available, pinning files can significantly increase a user's mobile data and 
	 * device storage usage. You should avoid pinning files without a user's consent 
	 * and inform them of the extra storage and bandwidth requirements of pinning files. 
	 * These requirements increase for larger and frequently updated files.
	 * 
	 * @param driveFile	The Drive file to PIN.
	 * @param callback
	 */
	public void async_pinFile(final DriveFile driveFile, boolean pin, final TBDrivePinFileCallback callback) {
		
		/**
    	 * Handles the pinning request's response.
    	 */
    	final ResultCallback<MetadataResult> pinningCallback = new ResultCallback<MetadataResult>() {
    	    @Override
    	    public void onResult(MetadataResult result) {
    	        if (!result.getStatus().isSuccess()) {
    	            Log.e(TAG, "Problem while trying to pin the file. '" + result.getStatus().getStatusMessage() + "'");
    	            if(callback!=null) {
    					callback.setErrorCode(ERROR_FILE_PIN);
    					callback.setErrorMessage(ERROR_FILE_PIN_STRING);
    					callback.setErrorDetails(result.getStatus().getStatusMessage());
    					callback.run();
    				}
    	            return;
    	        }
    	        
    	        Log.e(TAG, "File successfully pinned to the device.");
    	        if(callback!=null) {
        			callback.setDriveFile(driveFile);
        			callback.setDriveFileMetaData(result.getMetadata());
        			callback.setStatus(result.getStatus());
        			callback.run();
        		}
    	    }
    	};
    	
    	
    	//DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), mFileId);
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setPinned(pin)
                .build();
        driveFile.updateMetadata(getGoogleApiClient(), changeSet)
                .setResultCallback(pinningCallback);
	}
	
	/**
	 * Pinning a file causes the latest version of that file's contents and metadata 
	 * to be downloaded to the local device whenever a new version is available. 
	 * Once a file is pinned by one application it is locally available to all 
	 * applications that have permissions to that file.<br><br>
	 * 
	 * <b>Note</b><br>
	 * Because pinned files are downloaded to the device whenever a new version is 
	 * available, pinning files can significantly increase a user's mobile data and 
	 * device storage usage. You should avoid pinning files without a user's consent 
	 * and inform them of the extra storage and bandwidth requirements of pinning files. 
	 * These requirements increase for larger and frequently updated files.
	 * 
	 * @param driveFile The Drive file to PIN.
	 * @param pin
	 * @return	TRUE if is done successfully otherwise FALSE.
	 */
	public boolean sync_pinFile(final DriveFile driveFile, boolean pin) throws TBDriveException {
		
		boolean res = false;
		
    	//DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), mFileId);
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setPinned(pin)
                .build();
        MetadataResult result = driveFile.updateMetadata(getGoogleApiClient(), changeSet).await();
        
        if (!result.getStatus().isSuccess()) {
            Log.e(TAG, "Problem while trying to pin the file. '" + result.getStatus().getStatusMessage() + "'");
            throw new TBDriveException("Problem while trying to pin the file. '" + result.getStatus().getStatusMessage() + "'");            
        }
        
        Log.e(TAG, "File successfully pinned to the device.");
        res = true;
        
        return res;
	}
		
	/**
	 * Adds a Drive file change listener to receive notifications whenever a specified 
	 * file or folder has changes to its contents or metadata.<br><br>
	 * 
	 * Events only are notified to the app if is running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * If you attempt to add the same change listener to a file multiple times or remove a 
	 * listener from a file that does not have a listener of the same name as the callback 
	 * parameter, the request is silently ignored.  
	 * 
	 * @param driveFile	The Drive file to watch for.
	 * @param changeListener	See {@link TBChangeListener}
	 */
	public void addFileChangeListener(DriveFile driveFile, TBChangeListener changeListener) {
		driveFile.addChangeListener(mGoogleApiClient, changeListener);
	}
	
	/**
	 * Removes a change listener from a Drive file to stop receiving notifications whenever a 
	 * specified file or folder has changes to its contents or metadata.<br><br>
	 * 
	 * Events only are notified to the app if is running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * If you attempt to add the same change listener to a file multiple times or remove a 
	 * listener from a file that does not have a listener of the same name as the callback 
	 * parameter, the request is silently ignored.
	 * 
	 * @param driveFile	The Drive file to watch for.
	 * @param changeListener	See {@link TBChangeListener}
	 */
	public void removeFileChangeListener(DriveFile driveFile, TBChangeListener changeListener) {
		driveFile.removeChangeListener(mGoogleApiClient, changeListener);
	}
	

	/**
	 * Adds a Drive file change listener to receive notifications whenever a specified 
	 * file or folder has changes to its contents or metadata.<br><br>
	 * 
	 * Events only are notified to the app if is running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * If you attempt to add the same change listener to a file multiple times or remove a 
	 * listener from a file that does not have a listener of the same name as the callback 
	 * parameter, the request is silently ignored.  
	 * 
	 * @param driveFileId	The Drive file Id in string format.
	 * @param changeListener	See {@link TBChangeListener}
	 */
	public void addFileChangeListener(String driveFileId, TBChangeListener changeListener) {
		DriveFile file = drive_getDriveFileFromDriveIdString(driveFileId);
		addFileChangeListener(file, changeListener);		
	}
	
	/**
	 * Removes a change listener from a Drive file to stop receiving notifications whenever a 
	 * specified file or folder has changes to its contents or metadata.<br><br>
	 * 
	 * Events only are notified to the app if is running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * If you attempt to add the same change listener to a file multiple times or remove a 
	 * listener from a file that does not have a listener of the same name as the callback 
	 * parameter, the request is silently ignored.
	 * 
	 * @param driveFileId	The Drive file Id in String format.
	 * @param changeListener	See {@link TBChangeListener}
	 */
	public void removeFileChangeListener(String driveFileId, TBChangeListener changeListener) {
		DriveFile file = drive_getDriveFileFromDriveIdString(driveFileId);		
		removeFileChangeListener(file, changeListener);
	}
	
	/**
	 * Subscribes to a Drive file in order to receive notifications, 
	 * See {@link ChangeEvents}, whenever the file is modified, even when the application 
	 * is not running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * To get this working, a Google Play Services service must be added in
	 * applixcatAndroid manifest XML file:<br><br>
	 * 
	 * <b>TBDriveEventListener</b> is a class that must extends TBDriveEventService class.
	 * See {@link TBDriveEventService}.
	 * <br><br>
	 * 
	 * <code>
	 * 	 &lt;service android:name=".<b>TBDriveEventListener</b>" android:exported="true"&gt;<br>
     *   	&lt;intent-filter&gt;<br>
     *      	&lt;action android:name="com.google.android.gms.drive.events.HANDLE_EVENT"/&gt;<br>
     *   	&lt;/intent-filter&gt;<br>
     *   &lt;/service&gt;<br>
	 * </code>
	 * 
	 * @param driveFile	The Drive file to subscribe to.
	 */
	public void subscribeToFile(DriveFile driveFile) {
		driveFile.addChangeSubscription(mGoogleApiClient);
	}
	
	/**
	 * UnSubscribes from Drive file in order to stop receiving notifications, 
	 * See {@link ChangeEvents}, whenever the file is modified, even when the application 
	 * is not running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * To get this working, a Google Play Services service must be added in
	 * applixcatAndroid manifest XML file:<br><br>
	 * 
	 * <b>TBDriveEventListener</b> is a class that must extends TBDriveEventService class.
	 * See {@link TBDriveEventService}.
	 * <br><br>
	 * 
	 * <code>
	 * 	 &lt;service android:name=".<b>TBDriveEventListener</b>" android:exported="true"&gt;<br>
     *   	&lt;intent-filter&gt;<br>
     *      	&lt;action android:name="com.google.android.gms.drive.events.HANDLE_EVENT"/&gt;<br>
     *   	&lt;/intent-filter&gt;<br>
     *   &lt;/service&gt;<br>
	 * </code>
	 * 
	 * @param driveFile	The Drive file to unsubscribe from.
	 */
	public void unsubscribeToFile(DriveFile driveFile) {
		driveFile.removeChangeSubscription(mGoogleApiClient);
	}
	
	/**
	 * Subscribes to a Drive file in order to receive notifications, 
	 * See {@link ChangeEvents}, whenever the file is modified, even when the application 
	 * is not running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * To get this working, a Google Play Services service must be added in
	 * applixcatAndroid manifest XML file:<br><br>
	 * 
	 * <b>TBDriveEventListener</b> is a class that must extends TBDriveEventService class.
	 * See {@link TBDriveEventService}.
	 * <br><br>
	 * 
	 * <code>
	 * 	 &lt;service android:name=".<b>TBDriveEventListener</b>" android:exported="true"&gt;<br>
     *   	&lt;intent-filter&gt;<br>
     *      	&lt;action android:name="com.google.android.gms.drive.events.HANDLE_EVENT"/&gt;<br>
     *   	&lt;/intent-filter&gt;<br>
     *   &lt;/service&gt;<br>
	 * </code>
	 * 
	 * @param driveFileId	The Drive file Id in string format.
	 */
	public void subscribeToFile(String driveFileId) {
		DriveFile driveFile = drive_getDriveFileFromDriveIdString(driveFileId);
		driveFile.addChangeSubscription(mGoogleApiClient);
	}
	
	/**
	 * UnSubscribes from Drive file in order to stop receiving notifications, 
	 * See {@link ChangeEvents}, whenever the file is modified, even when the application 
	 * is not running.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * To get this working, a Google Play Services service must be added in
	 * applixcatAndroid manifest XML file:<br><br>
	 * 
	 * <b>TBDriveEventListener</b> is a class that must extends TBDriveEventService class.
	 * See {@link TBDriveEventService}.
	 * <br><br>
	 * 
	 * <code>
	 * 	 &lt;service android:name=".<b>TBDriveEventListener</b>" android:exported="true"&gt;<br>
     *   	&lt;intent-filter&gt;<br>
     *      	&lt;action android:name="com.google.android.gms.drive.events.HANDLE_EVENT"/&gt;<br>
     *   	&lt;/intent-filter&gt;<br>
     *   &lt;/service&gt;<br>
	 * </code>
	 * 
	 * @param driveFileId	The Drive file Id to unsubscribe from, in string format.
	 */
	public void unsubscribeToFile(String driveFileId) {
		DriveFile driveFile = drive_getDriveFileFromDriveIdString(driveFileId);
		driveFile.removeChangeSubscription(mGoogleApiClient);
	}
	
	
    //END DRIVE OPERATIONS
	
	
	
	//OTHER OPERATIONS
	
	/**
	 * Gets a drive file given the encoded string of it.
	 * 
	 * @param driveIdString
	 * @return
	 */
	public DriveFile drive_getDriveFileFromDriveIdString(String driveIdString) {
		return Drive.DriveApi.getFile(mGoogleApiClient,
		        DriveId.decodeFromString(driveIdString));
	}
		
	
	/**
	 * Available cloud result codes
	 */
	public static enum CloudOperationResult {
		OK_SAVE(0), OK_LOAD(1), LOAD_ERROR(-1), WRITE_ERROR(-2), NOT_EXISTS(-3), UNKNOWN(-100);
		 
		private int opResult;
		 
		private CloudOperationResult(int opResult) {
			this.opResult = opResult;
		}
		 
		public int getValue() {
			return opResult;
		}
		
		public static CloudOperationResult getCloudOperationResult(int value) {
			switch (value) {
				case 0:
					return OK_SAVE;
				case 1:
					return OK_LOAD;	
				case -1:
					return LOAD_ERROR;					
				case -2:
					return WRITE_ERROR;					
				case -3:
					return NOT_EXISTS;					
				default:
					return UNKNOWN;
			}
		}
	}
	
	/**
	 * Saves string data, an string, in the cloud using Google Drive.<br><br>
	 * 
	 * When operation finishes, handler will receive in "what" field the result code, see
	 * {@link CloudOperationResult} and an informative message.<br><br>
	 * 
	 * <b>Note</b>:<br>
	 * When file still does not exists, callback parameter is used to notify
	 * once the file is created with the result of the operation. See 
	 * {@link TBDriveFileCreationOpCallback}.
	 * 
	 * @param data				The string data to save.
	 * @param fileName			The desired file name or title for the Drive.
	 * @param creationCallback	The callback to call once file is created. 
	 * 							See {@link TBDriveFileCreationOpCallback}. 
	 * @param handler			Android Handler to notify about the result. For result codes 
	 * 							see {@link CloudOperationResult}
	 * @param useAppFolder		Set to TRUE to store the file in the application
	 * 							folder.
	 */
	public void saveStringDataInCloud(final String data, final String fileName, 
			final TBDriveFileCreationOpCallback creationCallback, final Handler handler,
			final boolean useAppFolder) {
    	
    	new Thread(new Runnable() {
    		
			@Override
			public void run() {
				
				Message msg = null;
				
		        DriveFile driveFile;
				try {
					driveFile = sync_getFile(fileName);
					if(driveFile==null) { //We create the file						
			        	async_createFile(fileName, "text/plain", data.getBytes(), creationCallback, useAppFolder);
			        }else{	//We update the file			        	
			        	DriveContents fileContents = sync_getFileContents(driveFile, DriveFile.MODE_WRITE_ONLY, null);			        	
			        	if(fileContents!=null) {
				        	DriveContents modifiedFileContents = drive_replaceTextOfFile(fileContents, data);
				        	if(sync_commitChangesAndClose(modifiedFileContents, false)){
				        		Log.i(TAG, "Data commited successfully to Google Drive.");
				        		msg = handler.obtainMessage(
			    						CloudOperationResult.OK_SAVE.getValue(), "Data commited to Google Drive.");				        		
				        	}else{
				        		Log.e(TAG, "Data could not be commited to Google Drive correctly!");
				        		msg = handler.obtainMessage(
			    						CloudOperationResult.WRITE_ERROR.getValue(), "Data could not be commited to Google Drive.");				        						        		
				        	}
			        	}else{
			        		msg = handler.obtainMessage(
		    						CloudOperationResult.WRITE_ERROR.getValue(), 
		    						"Current contents could not be loaded from Google Drive to update them!");
			        	}
			        }
				} catch (TBDriveException e) {
					Log.e(TAG, "Error saving in Google Drive data [" + e.getMessage() + "]", e);
					msg = handler.obtainMessage(
    						CloudOperationResult.WRITE_ERROR.getValue(), 
    						"Error writing data to Google Drive [" + e.getMessage() + "]");
				} catch (Exception e) {
					Log.e(TAG, "Error saving in Google Drive data [" + e.getMessage() + "]", e);
					msg = handler.obtainMessage(
    						CloudOperationResult.WRITE_ERROR.getValue(), 
    						"Un-expected error writing data to Google Drive [" + e.getMessage() + "]");					
				} finally {
					if(msg!=null){
						handler.sendMessage(msg);
					}
				}
			}
		}).start();
    }
	
	/**
	 * Loads string data from the Google Drive cloud.<br><br>
	 * 
	 * When operation finishes, handler will receive in "what" field the result code, see
	 * {@link CloudOperationResult} and an informative message.
	 * 
	 * @param fileName	The file name or title of the desired file.
	 * @param handler	Android Handler to notify about the result. For result codes 
	 * 					see {@link CloudOperationResult}
	 */
	public void getStringDataFromCloud(final String fileName, final Handler handler) {
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				Message msg = null;
	    		DriveFile driveFile;
	    		try {
	    			driveFile = sync_getFile(fileName);
	    			if(driveFile!=null) {
		    			DriveContents fileContent = sync_getFileContents(driveFile, DriveFile.MODE_READ_ONLY, null);
		    			if(fileContent!=null) {
			    			String data = drive_fileContentToString(fileContent);
			    			if(data!=null) {
			    				Log.i(TAG, "Data loaded from Google Drive cloud :)");
			    				msg = handler.obtainMessage(
			    						CloudOperationResult.OK_LOAD.getValue(), data);			    				
			    			}else{
			    				Log.e(TAG, "Data could not be loaded from Google Drive cloud!");
			    				msg = handler.obtainMessage(
			    						CloudOperationResult.LOAD_ERROR.getValue(), 
			    						"Data could not be loaded from Google Drive contents!");			    				
			    			}
		    			}else{
		    				msg = handler.obtainMessage(
		    						CloudOperationResult.LOAD_ERROR.getValue(), 
		    						"Contents could not be loaded from Google Drive!");		    				
		    			}
	    			}else{
	    				msg = handler.obtainMessage(
	    						CloudOperationResult.LOAD_ERROR.getValue(), 
	    						"The file does not exist in Google Drive!");	    				
	    			}
				} catch (TBDriveException e) {
					Log.e(TAG, "Data not loaded from Google Drive cloud [" + e.getMessage() + "]", e);
					msg = handler.obtainMessage(
    						CloudOperationResult.LOAD_ERROR.getValue(), 
    						"Error loading data from Google Drive [" + e.getMessage() + "]");					
				} catch (Exception e) {
					Log.e(TAG, "Data not loaded from Google Drive cloud [" + e.getMessage() + "]", e);
					msg = handler.obtainMessage(
    						CloudOperationResult.LOAD_ERROR.getValue(), 
    						"Un-expected Error loading data from Google Drive [" + e.getMessage() + "]");					
				}
	    		
	    		handler.sendMessage(msg);
			}
		}).start();		
    }
	
	
	//END OTHER OPERATIONS
	
	
		
	//AUXILIAR
    
	/**
	 * Gets an output stream to the Drive file contents.
	 * 
	 * @param driveFileContent
	 * @return
	 */
	private OutputStream getOutputStream(DriveContents driveFileContent) {
		OutputStream res = null;
		
		switch (driveFileContent.getMode()) {
			case DriveFile.MODE_READ_ONLY:
				break;
			case DriveFile.MODE_READ_WRITE:
				//Use this mode to append data to a file
				res = new FileOutputStream(driveFileContent.getParcelFileDescriptor().getFileDescriptor());
				break;
			case DriveFile.MODE_WRITE_ONLY:
				//Use this mode to truncate the file before writing in it
				OutputStream outputStream = driveFileContent.getOutputStream();
				res = new BufferedOutputStream(outputStream);
				break;
		}
		
		
		return res;
	}
	
	/**
	 * Gets an input stream to the Drive file contents.
	 * 
	 * @param driveFileContent
	 * @return
	 */
	private InputStream getInputStream(DriveContents driveFileContent) {
		InputStream res = null;
		
		switch (driveFileContent.getMode()) {
			case DriveFile.MODE_READ_ONLY:
				res = driveFileContent.getInputStream();
				break;
			case DriveFile.MODE_READ_WRITE:
				//Use this mode to append data to a file
				new FileInputStream(driveFileContent.getParcelFileDescriptor().getFileDescriptor());				
				break;
			case DriveFile.MODE_WRITE_ONLY:				
		}
		
		return res;
	}
    
    /**
     * Shows a toast message.
     */
    public void showMessage(String message) {
        ToolBox.dialog_showToastAlert(activity, message, false, false, ToolBox.TOAST_TYPE.INFO);
    }
    
    
    
	//GOOGLE API CLIENT - Listener
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
		
		if (result.hasResolution()) {
	        try {
	        	result.startResolutionForResult(activity, REQUEST_CODE_RESOLUTION);
	        } catch (IntentSender.SendIntentException e) {
	            // Unable to resolve, message user appropriately
	        	if(onConnFailureCallback!=null) {
	    			onConnFailureCallback.setConnectionResult(result);
	    			try {
	    				onConnFailureCallback.start();
		        	}catch(Exception ex) {
						Log.w(TAG, "On connection failed callback could not be started [" + ex.getMessage() + "].");
					}
	    		}
	        }
	    } else {
	        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity, 0).show();
	        if(onConnFailureCallback!=null) {
				onConnFailureCallback.setConnectionResult(result);
				try {
					onConnFailureCallback.start();
	        	}catch(Exception e) {
	        		Log.w(TAG, "On connection failed callback could not be started [" + e.getMessage() + "].");
	        	}
			}
	    }
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG, "GoogleApiClient connected");
		
		if(onConnCallback!=null) {
			onConnCallback.setConnectionHint(connectionHint);			
			try {
				onConnCallback.start();
			}catch(Exception e) {
				Log.w(TAG, "On connection callback could not be started [" + e.getMessage() + "].");
			}
		}
	}

	@Override
	public void onConnectionSuspended(int cause) {
		Log.i(TAG, "GoogleApiClient connection suspended");
		
		if(onConnSuspendedCallback!=null) {
			onConnSuspendedCallback.setConnectionSuspendedCause(cause);
			try {
				onConnSuspendedCallback.start();
			}catch(Exception e) {
				Log.w(TAG, "On connection callback could not be started [" + e.getMessage() + "].");
			}
		}
	}

}
