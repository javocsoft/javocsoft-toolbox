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
package es.javocsoft.android.lib.toolbox.facebook.callback;

import android.content.Context;
import android.support.v4.app.FragmentActivity;


/**
 * This class allows to do something when FB share fails.
 * 
 * The application context is available and also the activity
 * where the Facebook Share is being shown.
 * 
 * @author JavocSoft 2013.
 * @since 2014
 */
public abstract class OnShareFailActionCallback extends Thread implements Runnable {
	
	protected FragmentActivity activity;
	protected Context appContext;
	
	protected OnShareFailActionCallback(FragmentActivity activity) {
		this.activity = activity;
		this.appContext = activity.getApplicationContext();
	}
	
	@Override
	public void run() {
		pre_task();
		task();
		post_task();
	}
	    	
	protected abstract void pre_task();
	protected abstract void task();
	protected abstract void post_task();
	
	
	/**
	 * Gets the context.
	 * 
	 * @return
	 */
	protected Context getContext(){
		return appContext;
	}				
}
