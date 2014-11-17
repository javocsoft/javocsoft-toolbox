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
package es.javocsoft.android.lib.toolbox.facebook.exception;


/**
 * Facebook exception. 
 * 
 * @author JavocSoft 2014
 * @since  2014
 */
public class FBException extends Exception {
	
	
	private static final long serialVersionUID = 1L;
	

	public FBException(){
		super();		
	}
	
	public FBException(Throwable cause){
		super(cause);		
	}

	public FBException(String msg){
		super(msg);		
	}

	public FBException(String msg, Throwable cause){
		super(msg,cause);		
	}

}
