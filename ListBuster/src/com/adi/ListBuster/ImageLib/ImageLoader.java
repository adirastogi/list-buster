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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

/**
 * @author adrastog
 * This class is the ImageLoader class that downloads images from given url using a
 * thread safe download queue, file cache and memory cache.
 *
 */
public class ImageLoader {
	
	private MemCache mCache;
	private Vector<URL> imgUrls; //we use a vector as es neeed a synchronised data structure for
	//access by threads.
	private Handler handler;
	
	public ImageLoader(Vector<URL> imgUrls, Handler handler,Context appContext){
		mCache = MemCache.getMemCache(appContext);
		this.imgUrls = imgUrls;
		this.handler = handler;
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
						Message m = handler.obtainMessage();
						Bundle b = new Bundle();
						b.putParcelable("image", (Parcelable)bmp);
						m.setData(b);
						handler.sendMessage(m);
					}else{
						//get from net
						HttpURLConnection conn = null;
						try {
							conn = (HttpURLConnection) u.openConnection();
							InputStream in = new BufferedInputStream(conn.getInputStream());
							bmp = BitmapFactory.decodeStream(in);
							//send the message to handler
							Message m = handler.obtainMessage();
							Bundle b = new Bundle();
							b.putParcelable("image", (Parcelable)bmp);
							m.setData(b);
							handler.sendMessage(m); 
							mCache.put(u.toString(), bmp);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
				
			}
			
		});
		imgDownloader.start();
	}

}
