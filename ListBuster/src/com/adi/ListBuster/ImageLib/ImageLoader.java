/**
 * 
 */
package com.adi.ListBuster.ImageLib;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author adrastog
 * This class is the ImageLoader class that downloads images from given url using a
 * thread safe download queue, file cache and memory cache.
 *
 */
public class ImageLoader {
	
	private MemCache mCache;
	private Vector<URL> imgUrls;
	
	
	public ImageLoader(Vector<URL> imgUrls){
		mCache = MemCache.getMemCache();
		this.imgUrls = imgUrls;
	}
	
	
	public void getImages(){
		
		Thread imgDownloader = new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				URL u;
				for(int i=0;i<imgUrls.size();i++){
					u = imgUrls.elementAt(i);
					Bitmap bmp;
					//found in cache
					if((bmp=mCache.get(u.toString()))!=null){
						//send a message to handler;
						
					}else{
						//get from net
						HttpURLConnection conn = null;
						try {
							conn = (HttpURLConnection) u.openConnection();
							InputStream in = new BufferedInputStream(conn.getInputStream());
							bmp = BitmapFactory.decodeStream(in);
							//send the message to handler
							
							mCache.put(u.toString(), bmp);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
				
			}
			
		});
	}

}
