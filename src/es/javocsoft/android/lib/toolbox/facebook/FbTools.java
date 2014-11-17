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
package es.javocsoft.android.lib.toolbox.facebook;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.facebook.beans.AppRequestBean;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnAppRequestCancelledActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnAppRequestDeleteSuccessActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnAppRequestFailActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnAppRequestReceivedActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnAppRequestReceivedErrorActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnAppRequestSuccessActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.exception.FBSessionException;

/**
 * Facebook utility class.
 * 
 * @author JavocSoft 2014
 * @since  2014
 *
 */
public class FbTools {

	private static final Gson gson = new Gson();
	
	
	/** To avoid being instantiated. */
	protected FbTools(){}
	
	/**
	 * Creates a Facebook application request dialog.<br><br>
	 * 
	 * More info at <a href="https://developers.facebook.com/docs/android/send-requests/">Facebook request</a>
	 * 
	 * @param message		The message of the request.
	 * @param extraParams	You can pass arbitrary data as an additional parameter when making the 
	 * 						dialog request call. This could be data to make a gift, for example, to 
	 * 						the user if he opens the app.
	 * @param onSuccessCallback	What to do if the request is successfully done. See {@link OnAppRequestSuccessActionCallback}
	 * @param onFailCallback	What to do if the request fails. See {@link OnAppRequestFailActionCallback}
	 * @param onCancelCallback	What to do if the request is cancelled. See {@link OnAppRequestCancelledActionCallback}
	 */
	public static void createSendRequestDialog(Session session, Context context, String message, Map<String, String> extraParams,
			final OnAppRequestSuccessActionCallback onSuccessCallback,
			final OnAppRequestFailActionCallback onFailCallback,
			final OnAppRequestCancelledActionCallback onCancelCallback) throws FBSessionException {
	    
		if(Session.getActiveSession()!=null && Session.getActiveSession().isOpened()) {
		
			//Prepare the request
			Bundle params = new Bundle();
		    params.putString("message", message);
	
		    //Set extra parameters if there are. This can be used to 
		    //send a gift to the user receiving the request.
		    if(extraParams!=null && extraParams.size()>0){
			    params.putString("data", gson.toJson(extraParams));
			}
		    
		    //make the application request.
		    WebDialog requestsDialog = (
			        new WebDialog
			        	.RequestsDialogBuilder(context, session, params))
			            .setOnCompleteListener(new OnCompleteListener() {
	
			                @Override
			                public void onComplete(Bundle values,
			                    FacebookException error) {
			                    if (error != null) {
			                        if (error instanceof FacebookOperationCanceledException) {
			                        	Log.i(ToolBox.TAG, "Facebook request cancelled.");
			                            if(onCancelCallback!=null)
			                            	onCancelCallback.start();
			                        } else {
			                        	Log.i(ToolBox.TAG, "Facebook request error.");
			                            if(onFailCallback!=null)
			                            	onFailCallback.start();
			                        }
			                    } else {
			                        final String requestId = values.getString("request");
			                        if (requestId != null) {
			                        	Log.i(ToolBox.TAG, "Facebook request sent.");
			                            if(onSuccessCallback!=null)
			                            	onSuccessCallback.start();
			                        } else {
			                        	Log.i(ToolBox.TAG, "Facebook request cancelled.");
			                            if(onCancelCallback!=null)
			                            	onCancelCallback.start();
			                        }
			                    }   
			                }
	
			            })
			            .build();
			    requestsDialog.show();
			    
		}else{
			Log.i(ToolBox.TAG, "No current Facebook active session or not logged in.");
			throw new FBSessionException("No current Facebook active session or not logged in.");
		}
	}
	
	/**
	 * Checks if there is in the intent a Facebook App request data.
	 * 
	 * Returns the Facebook requestId. Use it with the method 
	 * "getRequestExtraData" to get the request information.<br><br>
	 * 
	 * See <a href="https://developers.facebook.com/docs/android/send-requests/">Facebook requests</a>
	 * 
	 * @param intent
	 * @return The facebook requestId or null if there is none.
	 */
	public static String proccessRequest(Intent intent) {
		String requestId = null;
		
		// Check for an incoming notification. Save the info
	    Uri intentUri = intent.getData();
	    if (intentUri != null) {
	        String requestIdParam = intentUri.getQueryParameter("request_ids");
	        if (requestIdParam != null) {
	            String array[] = requestIdParam.split(",");
	            requestId = array[0];
	            Log.i(ToolBox.TAG, "Facebook received Request id: "+requestId);
	        }
	    }
	    
	    return requestId;
	}
	
	/**
	 * Gets a facebook request information connecting with the
	 * Facebook Graph API.<br><br>
	 * 
	 * See <a href="https://developers.facebook.com/docs/android/send-requests/">Facebook requests</a>
	 * 
	 * @param onAppRequestReceived
	 * @param onAppRequestReceivedError
	 * @param appContext
	 * @param requestId
	 */
	public static void getRequestExtraData(final OnAppRequestReceivedActionCallback onAppRequestReceived,
			final OnAppRequestReceivedErrorActionCallback onAppRequestReceivedError,
			final Context appContext, final String requestId) throws FBSessionException {
		
		if(Session.getActiveSession()!=null && Session.getActiveSession().isOpened()) {
			// Create a new request for an HTTP GET with the
		    // request ID as the Graph path.
		    Request request = new Request(Session.getActiveSession(), 
		    		requestId, null, HttpMethod.GET, new Request.Callback() {
	
		                @Override
		                public void onCompleted(Response response) {
		                    // Process the returned response
		                    GraphObject graphObject = response.getGraphObject();
		                    FacebookRequestError error = response.getError();
		                    
		                    if (graphObject != null) {
		                    	// Check if there is extra data	                        
		                    	if (graphObject.getProperty("data") != null) {
		                            try {
		                            	AppRequestBean appRequestInfo = gson.fromJson((String)graphObject.getProperty("data"), AppRequestBean.class);
		                            	
		                                if(onAppRequestReceived!=null) {
		                                	//We set the received info and launch user event thread.
		                                	onAppRequestReceived.setAppRequestInfo(appRequestInfo);
		                                	onAppRequestReceived.start();
		                                }	                            	
		                                
		                            } catch (JsonSyntaxException e) {
		                            	if(onAppRequestReceivedError!=null) {
		                            		Log.e(ToolBox.TAG, "Error getting request info (" + e.getMessage() + ").", e);
		                            		onAppRequestReceivedError.setErrorMessage("Error getting request info (" + e.getMessage() + ")");
		                            		onAppRequestReceivedError.start();
		                            	}	                                
		                            }
		                        } else if (error != null) {
		                        	if(onAppRequestReceivedError!=null) {
	                            		Log.i(ToolBox.TAG, "Error getting request info (" + error.getErrorCode() + "/'" + error.getErrorMessage() + "').");
	                            		onAppRequestReceivedError.setErrorMessage("Error getting request info (" + error.getErrorCode() + "/'" + error.getErrorMessage() + "')");
	                            		onAppRequestReceivedError.start();
	                            	}	                            
		                        }
		                    }else{
		                    	if(onAppRequestReceived!=null) {
	                            	//We set the received info and launch user event thread.
	                            	onAppRequestReceived.setAppRequestInfo(null);
	                            	onAppRequestReceived.start();
	                            }	                    	
		                    }
		                }
		        });
		    // Execute the request asynchronously.
		    Request.executeBatchAsync(request);
		    
		}else{
			Log.i(ToolBox.TAG, "No current Facebook active session or not logged in.");
			throw new FBSessionException("No current Facebook active session or not logged in.");
		}
	}
	
	/**
	 * Once the Facebook application request is completed, we 
	 * should remove the request from Facebook. It uses the
	 * Facebook Graph API.<br><br>
	 * 
	 * See <a href="https://developers.facebook.com/docs/android/send-requests/">Facebook requests</a>
	 * 
	 * @param inRequestId
	 * @param onSuccessCallback
	 */
	public static void deleteRequest(String  inRequestId, 
				final OnAppRequestDeleteSuccessActionCallback onSuccessCallback) throws FBSessionException{
		
		if(Session.getActiveSession()!=null && Session.getActiveSession().isOpened()) {
			// Create a new request for an HTTP delete with the
		    // request ID as the Graph path.
			Request request = 
					new Request(Session.getActiveSession(),
								inRequestId, null, HttpMethod.DELETE, new Request.Callback() {
						
						            @Override
						            public void onCompleted(Response response) {
						                // Show a confirmation of the deletion
						                // when the API call completes successfully.					                
						            	if(onSuccessCallback!=null)
						            		onSuccessCallback.start();					            	
						            }
					});
		    
			// Execute the request asynchronously.
		    Request.executeBatchAsync(request);		
		}else{
			Log.i(ToolBox.TAG, "No current Facebook active session or not logged in.");
			throw new FBSessionException("No current Facebook active session or not logged in.");
		}
	}
}
