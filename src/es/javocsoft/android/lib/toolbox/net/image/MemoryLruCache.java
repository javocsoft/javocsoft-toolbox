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
package es.javocsoft.android.lib.toolbox.net.image;

import java.util.LinkedHashMap;

/**
 * Memory LRU Cache.
 * 
 * @author JavocSoft 2014
 * @since  2015
 *
 * @param <K>
 * @param <V>
 */
public class MemoryLruCache<K, V> extends LinkedHashMap<K, V> {

	private int mMaxEntries;

	public MemoryLruCache(int maxEntries) {
		//Note:
		// removeEldestEntry() will be called after a put(). 
		// Parameters:
		// 	- To allow maxEntries in cache, capacity should be 
		//		maxEntries + 1 (+1 for the entry which will be removed). 
		// 	- Size is fixed so Load factor is taken as 1. This is less 
		//		space efficient when very less entries are present, but 
		//		there will be no effect on time complexity for get(). 
		// 	- Third parameter in the base class constructor means that 
		//		this map is access-order oriented.
		super(maxEntries + 1, 1, true);
		
		mMaxEntries = maxEntries;
	}

	@Override
	protected boolean removeEldestEntry(Entry<K, V> eldest) {
		// After size exceeds max entries, this statement returns true and the
		// oldest value will be removed. Since this map is access oriented the
		// oldest value would be least recently used.
		return size() > mMaxEntries;
	}

}