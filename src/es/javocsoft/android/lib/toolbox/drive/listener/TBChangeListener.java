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
package es.javocsoft.android.lib.toolbox.drive.listener;

import android.util.Log;

import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;

import es.javocsoft.android.lib.toolbox.drive.TBDrive;

/**
 * A listener to handle Drive file change events. See {@link ChangeEvent}
 * 
 * Events only are notified to the app if is running. 
 * See {@link TBDrive#addFileChangeListener(com.google.android.gms.drive.DriveFile, TBChangeListener)}
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public abstract class TBChangeListener implements ChangeListener {

	public TBChangeListener() {
		
	}

	@Override
	public void onChange(ChangeEvent event) {
		Log.i(TBDrive.TAG, (String.format("Drive file change event: %s", event)));
		doWork();
	}

	
	protected abstract void doWork();
}
