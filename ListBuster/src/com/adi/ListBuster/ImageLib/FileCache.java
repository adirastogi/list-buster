/**
 * 
 */
package com.adi.ListBuster.ImageLib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * @author adrastog
 *
 */
public class FileCache {
	
	private HashMap<String,File> fTable;
	private static FileCache singletonFileCache;
	private File cacheDir;
	
	private FileCache(){
		fTable = new HashMap<String, File>();
		cacheDir = Environment.getDownloadCacheDirectory();
	}
	
	public static FileCache getFileCache(){
		if(singletonFileCache==null)
			singletonFileCache = new FileCache();
		return singletonFileCache;
	}
	
	public Bitmap getImageFromDisk(String imgUrl){
		if(fTable.containsKey(imgUrl)){
			Bitmap b=null;
			try{
				File f = fTable.get(imgUrl);
				FileInputStream fin=null;
				BufferedInputStream bin=null;
				try {
					fin = new FileInputStream(f);
					bin = new BufferedInputStream(fin);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					fin.close();
					bin.close();
					
				}
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return b;
		}
		else return null;
	}
	
	public void putImageFromBitmap(String url,Bitmap bmp){
		
		File path;
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            path=new File(cacheDir,String.valueOf(url.hashCode()));
            try {
			    OutputStream os=null;
				try {
					os = new FileOutputStream(path);
					bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					os.close();
				}
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		}
		else return;
	}
}
