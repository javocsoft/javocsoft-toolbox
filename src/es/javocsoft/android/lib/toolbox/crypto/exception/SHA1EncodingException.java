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
package es.javocsoft.android.lib.toolbox.crypto.exception;

/**
 * Google Drive exception.
 * 
 * @author JavocSoft 2015
 * @version 1.0
 *
 */
public class SHA1EncodingException extends Exception {

	private static final long serialVersionUID = 1L;

	public SHA1EncodingException() {
	}

	public SHA1EncodingException(String detailMessage) {
		super(detailMessage);
	}

	public SHA1EncodingException(Throwable throwable) {
		super(throwable);
	}

	public SHA1EncodingException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
