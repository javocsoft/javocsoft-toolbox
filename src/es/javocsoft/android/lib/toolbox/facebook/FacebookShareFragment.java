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

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import es.javocsoft.android.lib.toolbox.R;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.TOAST_TYPE;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnLoginActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnLogoutActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnShareCancelledActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnShareFailActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnShareSuccessActionCallback;

/**
 * Facebook Share Button Module.<br><br>
 * 
 * The permission:<br><br>
 *		{@code
 *		<uses-permission android:name="android.permission.INTERNET"/>
 *		}
 *  <br><br>
 *	is required. Also in the manifest file, "application" section:<br><br>
 *		{@code
 *		<meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/fb_app_id"/>		
 *		... where "app_id" is the Facebook application id.
 *		}
 *	<br><br>
 * 
 * 
 * See <a href="https://developers.facebook.com/docs/android/share">Facebook Share</a><br><br>
 * 
 * If using the Facebook Share fragment, to publish, the permission<br>
 * "<b>publish_actions</b>" is required when user logs in Facebook. See 
 * <a href="https://developers.facebook.com/docs/facebook-login/permissions/v2.1">Facebook Permissions</a> 
 *  
 * @author JavocSoft 2014
 * @since  2014
 */
public class FacebookShareFragment extends Fragment {
	
	
	private String applicationName;
	private String caption; 
	private String description;
	private String link;
	private String pictureURL;
	
	/** The actions to take when a login is done. */
	protected OnLoginActionCallback onLoginCallback = null;
	
	/** The action to take when a logout is done. */
	protected OnLogoutActionCallback onLogoutCallback = null;
	
	/** The action to take when a share is successfully done. */
	protected OnShareSuccessActionCallback onShareSuccessCallback = null;
	
	/** The action to take when a share is not successfully done. */
	protected OnShareFailActionCallback onShareFailCallback = null;
	
	/** The action to take when a share is not successfully done. */
	protected OnShareCancelledActionCallback onShareCancelledCallback = null;
	
	
	/** The application context */
	protected static Context appContext = null;
	
	private boolean enableCustomButton = false;
	private Drawable buttonBackgroundImage;
	
	
	/** Facebook session life-cycle listener. */ 
	private UiLifecycleHelper uiHelper;	
	
	/**
	 * This callback allows to do actions when Facebook session
	 * status changes.
	 * 
	 * Note: You can add the UiLifecycleHelper and set up a 
	 * 		 corresponding Session.StatusCallback listener in 
	 * 		 any activity or fragment where you wish to track and 
	 * 		 respond to session state changes.
	 */
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	
	/**
	 * Initializes the Fragment.
	 * 
	 * @param applicationName	The application name
	 * @param caption			Some caption.
	 * @param description		The description, text, for the share.
	 * @param link				A link to share.
	 * @param pictureURL		A picture to share.
	 * @param onLoginCallback	What to do when a login happens, {@link OnLoginActionCallback}
	 * @param onLogoutCallback	What to do when a logout happens, {@link OnLogoutActionCallback}
	 * @param onShareSuccessCallback	What to do when a share happens, {@link OnShareSuccessActionCallback}
	 * @param onShareFailCallback	What to do when a share fails, {@link OnShareFailActionCallback}
	 * @param onShareCancelledCallback What to do when a share is cancelled, {@link onShareCancelledCallback}}
	 */
	public void initialize(String applicationName, String caption, 
							String description, String link, String pictureURL,
							OnLoginActionCallback onLoginCallback,
							OnLogoutActionCallback onLogoutCallback,
							OnShareSuccessActionCallback onShareSuccessCallback,
							OnShareFailActionCallback onShareFailCallback,
							OnShareCancelledActionCallback onShareCancelledCallback){
		
		this.applicationName = applicationName;
		this.caption = caption;
		this.description = description;
		this.link = link;
		this.pictureURL = pictureURL;
		
		this.onLoginCallback = onLoginCallback;
		this.onLogoutCallback = onLogoutCallback;
		this.onShareSuccessCallback = onShareSuccessCallback;
		this.onShareFailCallback = onShareFailCallback;
		this.onShareCancelledCallback = onShareCancelledCallback;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    appContext = getActivity().getApplicationContext();	    
	    
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	    
	    //Shows the application signatures
	    ToolBox.application_getSignatures(getActivity().getApplicationContext(), 
	    		getActivity().getCallingPackage());
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_fb_share, container, false);
		
		ImageButton shareButton = (ImageButton) view.findViewById(R.id.fbShareButton);
		
	    //Customization
	    if(enableCustomButton && buttonBackgroundImage!=null) {
	    	shareButton.setBackgroundDrawable(buttonBackgroundImage);		    
	    }
	    
	    //What to do when the ImageView is preset, SHARE, of course!! :P
	    shareButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(Session.getActiveSession().getState().isOpened()) {
					facebookShare();
				}else{
					//Alert to the user.
					ToolBox.dialog_showToastAlert(getActivity(), "You need to be logged to share on Facebook.", false, false, TOAST_TYPE.WARNING);
				}
			}
		});
	    
	    //Check if there is an active Facebook session.
	    if(Session.getActiveSession()==null || 
	    	(Session.getActiveSession()!=null && !Session.getActiveSession().getState().isOpened())) {
	    	shareButton.setVisibility(View.GONE);
	    	Log.i(ToolBox.TAG, "Facebook Share Button: No active session. Button will be hidden.");
	    }else{
	    	shareButton.setVisibility(View.VISIBLE);
	    }
	    
	    return view;
	}
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	        	Log.i(ToolBox.TAG, "Error posting story (" + error.getMessage() + ")", error);	        	
	        	//Launch the event thread so the user can do something about
	        	if(onShareFailCallback!=null)
	        		onShareFailCallback.start();
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	        	
	        	boolean didCancel = FacebookDialog.getNativeDialogDidComplete(data);
	        	if(!didCancel) {
	        		String completionGesture = FacebookDialog.getNativeDialogCompletionGesture(data);
		        	if(completionGesture.equalsIgnoreCase("post")) {
		        		String postId = FacebookDialog.getNativeDialogPostId(data);			        	
		        		Log.i(ToolBox.TAG, "User successfully posted in Facebook. Post Id: " + postId);
                        if(onShareSuccessCallback!=null)
                        	onShareSuccessCallback.start();
		        	}else{ //otherwise is "cancel".		        		
		        		Log.i(ToolBox.TAG, "User cancelled posting story.");
                        if(onShareCancelledCallback!=null)
                        	onShareCancelledCallback.start();
		        	}
	        	}else{
	        		Log.i(ToolBox.TAG, "User cancelled posting story.");
                    if(onShareCancelledCallback!=null)
                    	onShareCancelledCallback.start();	        		
	        	}
	        }
	    });
	    
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
	//PUBLIC METHODS
	
	/**
	 * Allows to customize the login button.
	 * 
	 * @param enable
	 * @param buttonBackgroundImage
	 */
	public void enableCustomButton(boolean enable, Drawable buttonBackgroundImage ) {
		this.enableCustomButton = enable;
		if(enable && buttonBackgroundImage!=null) {
			this.buttonBackgroundImage = buttonBackgroundImage;			
		}else{			
			this.buttonBackgroundImage = getResources().getDrawable(R.drawable.ic_action_fb_share_icon);
		}
	}
	
	
	
	//AUXILIAR
	
	/**
	 * Share something to facebook. 
	 * It will look for the Facebook application to
	 * know which kind of share method to use.
	 */
	private void facebookShare() {
		if (FacebookDialog.canPresentShareDialog(appContext,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			//We post using the native Facebook application dialog.
			facebookShareFBApp();		
		} else {
			//user does not have the native Facebook application installed so we 
			//post using the web feed dialog.
			facebookShareWebShare();
		}
	}
	
	/**
	 * Post to Facebook using the Facebook application dialog.
	 */
	private void facebookShareFBApp() {
		// Publish the post using the Share Dialog
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
			.setApplicationName(applicationName)
			.setCaption(caption)
			.setDescription(description)
			.setLink(link)
			.setPicture(pictureURL)
			.build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	
	/**
	 * Post to Facebook using the web feed dialog.
	 * 
	 * This is used when no native Facebook application
	 * is found.
	 */
	private void facebookShareWebShare() {
		
		Bundle params = new Bundle();
	    params.putString("name", applicationName);
	    params.putString("caption", caption);
	    params.putString("description", description);
	    params.putString("link", link);
	    params.putString("picture", pictureURL);

	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(getActivity(),
	            Session.getActiveSession(),
	            params))
	        .setOnCompleteListener(new OnCompleteListener() {

	            @Override
	            public void onComplete(Bundle values,
	                FacebookException error) {
	                if (error == null) {
	                    // When the story is posted, echo the success and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                    	Log.i(ToolBox.TAG, "user successfully posted in Facebook. Post Id: " + postId);
	                        if(onShareSuccessCallback!=null)
	                        	onShareSuccessCallback.start();	                    	
	                    } else {
	                        // User clicked the Cancel button
	                    	Log.i(ToolBox.TAG, "user cancelled posting story.");
	                        if(onShareCancelledCallback!=null)
	                        	onShareCancelledCallback.start();
	                    }
	                } else if (error instanceof FacebookOperationCanceledException) {
	                    // User clicked the "x" button
	                	Log.i(ToolBox.TAG, "user cancelled posting story ('X' button pressed).");
                        if(onShareCancelledCallback!=null)
                        	onShareCancelledCallback.start();
	                } else {
	                    // Generic, example: network error
	                	Log.i(ToolBox.TAG, "Error posting story (" + error.getMessage() + ")", error);
	                    if(onShareFailCallback!=null)
	                    	onShareFailCallback.start();
	                }
	            }
	        })
	        .build();
	    feedDialog.show();
	}
	
	/**
	 * To be aware of Facebook session changes.
	 * 
	 * @param session
	 * @param state
	 * @param exception
	 */
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		
	    if (state.isOpened()) {
	        Log.i(ToolBox.TAG, "Facebook Login: Logged in...");
	        if(onLoginCallback!=null)
	        	onLoginCallback.start();
	    } else if (state.isClosed()) {
	        Log.i(ToolBox.TAG, "Facebook Login: Logged out...");
	        if(onLogoutCallback!=null)
	        	onLogoutCallback.start();
	    }
	}
	
	
}
