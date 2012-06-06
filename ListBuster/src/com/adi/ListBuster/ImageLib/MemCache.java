/**
 * 
 */
package com.adi.ListBuster.ImageLib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author adrastog
 *
 */
public class MemCache {
	
	private  HashMap<String,Bitmap> mTable;
	private static MemCache singletonMemCache;
	private static final int CACHESIZE = 8;
	
	private FileCache fCache;
	
	private MemCache(Context appContext){
		
		mTable = new HashMap<String, Bitmap>();
		fCache = FileCache.getFileCache(appContext);
	}
	
	
	public static MemCache getMemCache(Context appContext){
		if(singletonMemCache == null)
			singletonMemCache = new MemCache(appContext);
		return singletonMemCache;
	}
	
	//cooment
	public Bitmap get(String url){
		Bitmap b=null;
		if(mTable.containsKey(url)){
			b= mTable.get(url);
			return b;
		}
		else if((b=fCache.getImageFromDisk(url))!=null){
			put(url,b);
			return b;
		}else return null;
		
	}
	
	public void put(String url,Bitmap bmp){
		if(!mTable.containsKey(url)){
			if(mTable.size()==CACHESIZE){
				//remove LRU
				Iterator<Entry<String, Bitmap>> iter= mTable.entrySet().iterator();
				while(iter.hasNext()){
					Entry<String, Bitmap> entry=iter.next();
	                iter.remove();
	                break;
				}
			}
			mTable.put(url, bmp);
			fCache.putImageFromBitmap(url, bmp);		}
		
	}
}
