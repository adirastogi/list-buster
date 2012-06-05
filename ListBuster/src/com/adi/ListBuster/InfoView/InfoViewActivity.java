package com.adi.ListBuster.InfoView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.ListBuster.R;
import com.adi.ListBuster.ImageLib.ImageLoader;
import com.adi.ListBuster.ServiceClients.LastFMClient;
import com.adi.ListBuster.SongInfo.SongObject;

public class InfoViewActivity extends Activity{

	private Gallery gView;
	private TextView title;
	private ExpandableListView elView;
	private ImageView iView;
	private SongObject song;     //the song object received from previous activity
	private final Handler imageMessageHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			Bitmap newImg = (Bitmap)b.getParcelable("image");
			((GalleryAdapter)gView.getAdapter()).addImage(newImg);
		}
		
	};;  
			
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoview);
		song = (SongObject) this.getIntent().getParcelableExtra("track"); 
		title = (TextView)findViewById(R.id.titleText);
		elView = (ExpandableListView)findViewById(R.id.elView);
		iView = (ImageView)findViewById(R.id.imageViewMain);
		gView = (Gallery)findViewById(R.id.galleryView);
		
		//if the song already has an album art, use that for display instead of stock.
		Uri defaultImage;
		Bitmap b =null;
		if((defaultImage = song.getMediaStoreImage())!=null) {
		    try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), defaultImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		       
		}
		
		if(b==null) 
		    b = BitmapFactory.decodeResource(getResources(), R.drawable.disc);
		    
		
		ArrayList<Bitmap> bmpList = new ArrayList<Bitmap>();
        bmpList.add(b);
        gView.setAdapter(new GalleryAdapter(getApplicationContext(),bmpList));

        elView.setAdapter(new InfoListAdapter(getApplicationContext(), song));
        title.setText(song.getSong());
        
        
        
		//set the click listeners for the metadata expandable list.
		elView.setOnGroupClickListener(new OnGroupClickListener() {
			
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(parent.isGroupExpanded(groupPosition))
					parent.collapseGroup(groupPosition);
				else parent.expandGroup(groupPosition);
				return false;
			}
		});
		
		//listener for the selection change of gallery, so that the current selection is displayed in iView.
		gView.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				iView.setImageBitmap((Bitmap) arg0.getAdapter().getItem(position));
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
		;
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
				 * Use the results to populate the expandable list view.
				 * 
				 */
				((InfoListAdapter)elView.getExpandableListAdapter()).setDisplayTrack(result);
				/*
				 * get the list of the URLs from the response object and then call the ImageLoader with
				 * that list and the handler.
				 */
				List<URL> albumArtLinks = result.getAlbumArtImages();
				//check if there is a possibility that it is null
				
				if(albumArtLinks!=null){
					ImageLoader iLoader = new ImageLoader(new Vector<URL>(albumArtLinks),imageMessageHandler);
			    //now start the Image loading thread, that will make callbacks to the handler to handle messages.
					iLoader.getImages();
				}
			}
			
			
			
		};
		getMetadata.execute(new LastFMClient(getApplicationContext(),song));
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
