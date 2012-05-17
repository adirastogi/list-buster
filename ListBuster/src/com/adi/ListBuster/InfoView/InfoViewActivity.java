package com.adi.ListBuster.InfoView;

import java.net.URL;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.ListBuster.R;
import com.adi.ListBuster.ImageLib.ImageLoader;
import com.adi.ListBuster.ServiceClients.LastFMClient;
import com.adi.ListBuster.SongInfo.SongObject;

public class InfoViewActivity extends Activity{

	
	private final Handler imageMessageHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Bundle b = msg.getData();
			Bitmap bmp = (Bitmap)b.getParcelable("image");
			//make a call to the gallery to update it 
		}
		
		
	};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoview);
		Bundle bundleLV = this.getIntent().getExtras();
		SongObject song = (SongObject) bundleLV.getSerializable("track"); 
		TextView title = (TextView)findViewById(R.id.titleText);
		ExpandableListView elView = (ExpandableListView)findViewById(R.id.elView);
		ImageView iView = (ImageView)findViewById(R.id.imageViewMain);
		Gallery gView = (Gallery)findViewById(R.id.galleryView);
		
		
		title.setText(song.getSong());
		ExpandableListAdapter ela = new InfoListAdapter(getApplicationContext(), song);
		//ifthe sng already has an albumart, use that for display instead of stock.
		elView.setAdapter(ela);
		
		
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		/*
		 * As soon as the activity starts and the UI has been loaded, make the 
		 * call to the server for fetching the song metadata. Once the data has 
		 * been received, then inside onPostExecute, call a handler on the UI 
		 */
		AsyncTask<LastFMClient, Void, SongObject> getMetadata = new AsyncTask<LastFMClient,Void,SongObject>(){

			@Override
			protected SongObject doInBackground(LastFMClient... client) {
		        // TODO Auto-generated method stub
		        LastFMClient lfmClient = client[0];
		        SongObject track = lfmClient.getMusicMetadata();
		        return track;
		    }

			@Override
			protected void onPostExecute(SongObject result) {
				// TODO Auto-generated method stub
				/*
				 * get the list of the URLs from the response object and then call the ImageLoader with
				 * that list and the handler.
				 */
				List<URL> albumArtLinks = result.getAlbumArtImages();
				ImageLoader iLoader = new ImageLoader(new Vector<URL>(albumArtLinks),imageMessageHandler);
			    //now start the Image loading thread, that will make callbacks to the handler to handle messages.
				iLoader.getImages();
			}
			
			
			
		};
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
