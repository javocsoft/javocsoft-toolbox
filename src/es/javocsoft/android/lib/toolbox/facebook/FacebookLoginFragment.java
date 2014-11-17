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

import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import es.javocsoft.android.lib.toolbox.R;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnLoginActionCallback;
import es.javocsoft.android.lib.toolbox.facebook.callback.OnLogoutActionCallback;

/**
 * Facebook Login Button Module. 
 * 
 * @author JavocSoft 2014
 * @since  2014
 */
public class FacebookLoginFragment extends Fragment {
	
	/** A comma separated list of permissions that application needs.<br>
	 *
	 *	The permission:<br><br>
	 *		{@code
	 *		<uses-permission android:name="android.permission.INTERNET"/>
	 *		}
	 *  <br><br>
	 *	is required. Also in the manifest file, "application" section:<br><br>
	 *		{@code
	 *		<meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/fb_app_id"/>		
	 *		... where "app_id" is the Facebook application id.
	 *		
	 *		<activity android:name="com.facebook.LoginActivity"/>
	 *		}
	 *	<br><br>
	 *  Permissions:<br><br>
	 *	
	 *	These permissions do not require a review from Facebook of your app.<br><br>
	 *  
	 *	- user_friends<br>
	 *	- public_profile<br>
	 *	- email	<br><br>
	 *
	 *	Adding others will require a validation from Facebook:<br><br>
	 *
	 *	- user_likes<br>
	 *  - user_status<br>
	 *  - user_place_visits<br>
	 *	- user_birthday<br>
	 *	- publish_actions<br>
	 *	- user_about_me<br><br>
	 *   
	 *  See <br>
	 *  	https://developers.facebook.com/docs/android/login-with-facebook/v2.1
	 *  	https://developers.facebook.com/docs/android/getting-started/<br>
	 *  	https://developers.facebook.com/docs/facebook-login/permissions/v2.1<br>   
	*/
	protected String fbPermissions = null;
	
	/** The actions to take when a login is done. */
	protected OnLoginActionCallback onLoginCallback = null;
	
	/** The action to take when a logout is done. */
	protected OnLogoutActionCallback onLogoutCallback = null;
	
	/** The application context */
	protected static Context appContext = null;
	
	private boolean enableCustomButton = false;
	private String buttonloginText;
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
	 * Initilizes the Fragment.
	 * 
	 * @param fbPermissions		A comma separated list of Facebook permissions. default is "public_profile,email,user_friends"
	 * 							See <a href="https://developers.facebook.com/docs/facebook-login/permissions/v2.1">Permissions in Facebook</a>
	 * @param onLoginCallback	What to do when a login happens, {@link OnLoginActionCallback}
	 * @param onLogoutCallback	What to do when a logout happens, {@link OnLogoutActionCallback}
	 */
	public void initialize(String fbPermissions, OnLoginActionCallback onLoginCallback,
							OnLogoutActionCallback onLogoutCallback){
		if(fbPermissions==null || (fbPermissions!=null && fbPermissions.length()==0))
			fbPermissions = "public_profile,email,user_friends"; //Defaults.
				
		this.fbPermissions = fbPermissions;
		this.onLoginCallback = onLoginCallback;
		this.onLogoutCallback = onLogoutCallback;
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
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_fb_login, container, false);
		    
	    //During the authentication flow, the results are returned back to the main 
	    //activity by default. This two lines allows your activity to send the flow
	    //to the fragment of Facebook login. 
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.fbAuthButton);
	    
	    //Customization
	    if(enableCustomButton) {	    	
	    	if(buttonBackgroundImage!=null || buttonloginText!=null) {
	    		authButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
	    		
	    		if(buttonBackgroundImage!=null) {
		    		//authButton.setBackgroundResource(<res_id>);
		    		authButton.setBackgroundDrawable(buttonBackgroundImage);
		    	}	    	
		    	if(buttonloginText!=null && buttonloginText.length()>0) {
		    		authButton.setText(buttonloginText);	    		
		    	}
	    	}
	    }
	    
	    authButton.setFragment(this);
	    
	    //Set special permissions.
	    if(fbPermissions!=null && fbPermissions.length()>0) {
	    	authButton.setReadPermissions(Arrays.asList(fbPermissions));
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
	    uiHelper.onActivityResult(requestCode, resultCode, data); 
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
	
	/**
	 * Allows to customize the login button.
	 * 
	 * @param enable
	 * @param buttonLoginText
	 * @param buttonBackgroundImage
	 */
	public void enableCustomButton(boolean enable, String buttonLoginText, Drawable buttonBackgroundImage ) {
		this.enableCustomButton = enable;
		if(enable) {
			if(buttonLoginText!=null && buttonLoginText.length()>0){
				this.buttonloginText = buttonLoginText;
			}
			if(buttonBackgroundImage!=null) {
				this.buttonBackgroundImage = buttonBackgroundImage;
			}
		}
	}
	
	
	//AUXILIAR
	
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
