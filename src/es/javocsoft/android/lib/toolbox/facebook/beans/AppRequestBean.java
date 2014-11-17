package es.javocsoft.android.lib.toolbox.facebook.beans;

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
import java.util.Map;

/**
 * Class that will hold a Facebook Application
 * request information.<br><br>
 * 
 * More info at <a href="https://developers.facebook.com/docs/android/send-requests/">Facebook request</a>
 * 
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
public class AppRequestBean {
	
	private String id;
	
	private ApplicationInfo application;
	private ToInfo to;
	private FromInfo from;
	
	private String message;
	private Map<String,String> data;
	private String created_time;
	
		
	public AppRequestBean(){}	
	
	public AppRequestBean(String id, ApplicationInfo application, ToInfo to,
			FromInfo from, String message, Map<String, String> data,
			String created_time) {
		super();
		this.id = id;
		this.application = application;
		this.to = to;
		this.from = from;
		this.message = message;
		this.data = data;
		this.created_time = created_time;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ApplicationInfo getApplication() {
		return application;
	}
	public void setApplication(ApplicationInfo application) {
		this.application = application;
	}

	public ToInfo getTo() {
		return to;
	}
	public void setTo(ToInfo to) {
		this.to = to;
	}

	public FromInfo getFrom() {
		return from;
	}
	public void setFrom(FromInfo from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	
	
	
	
	class ApplicationInfo {
		private String name;
		private String id;
	}
	
	class ToInfo {
		private String name;
		private String id;
	}
	
	class FromInfo {
		private String name;
		private String id;
	}
}
