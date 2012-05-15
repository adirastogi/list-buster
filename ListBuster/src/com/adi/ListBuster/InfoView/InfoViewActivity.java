package com.adi.ListBuster.InfoView;

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
import com.adi.ListBuster.ServiceClients.LastFMClient;
import com.adi.ListBuster.SongInfo.SongObject;

public class InfoViewActivity extends Activity{

	@Override
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
				super.onPostExecute(result);
				Handler h = new Handler(){
					@Override
					  public void handleMessage(Message msg) {
					//display each item in a single line
						Bitmap bmp=(Bitmap)(msg.getData().getParcelable("image"));
					  }
				};
			}
			
			
			
		};
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
