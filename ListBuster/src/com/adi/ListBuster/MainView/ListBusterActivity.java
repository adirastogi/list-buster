package com.adi.ListBuster.MainView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.adi.ListBuster.R;
import com.adi.ListBuster.InfoView.InfoViewActivity;
import com.adi.ListBuster.SongInfo.AudioProvider;
import com.adi.ListBuster.SongInfo.SongObject;

public class ListBusterActivity extends ListActivity {
    /** Called when the activity is first created. */
    
    private ListView trackList;
    private TextView listHeader;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listHeader = (TextView)findViewById(R.layout.main);
        trackList = (ListView)findViewById(R.layout.main);
        //initialize the AudioProvider and use it to get the results into the List adapter
        AudioProvider ap = new AudioProvider(this.getApplicationContext());
        trackList.setAdapter(new MainListAdapter(this.getApplicationContext(),ap));
        
        //set the click listener for the list items.
        trackList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> lView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				ListView lv = (ListView)lView;
				SongObject s =(SongObject)(lv.getAdapter().getItem(position));
				Intent i = new Intent(getApplicationContext(),InfoViewActivity.class);
				i.putExtra("track", s);
				startActivity(i);
			}
		});
    }
    
    
    /**add the set on click listener that instantiates the other activity 
     * that displays the album cover and the album info as the album cover
     * from a gallery. This is how a workflow must proceed -
     * 1. User launches activity, sees the list containing all his songs
     * 2. Whrn the list acitivity is launched the list is populated with the data
     *    that is retrieved from the android local store.
     * 3. When the user clicks on a particular song the osng detail acticity
     *    is launched.
     * 4. Before launching the song detail activity , the server is queried in an 
     *    async background thread that is started as soon as the activity transition
     *    starts so that while the next acctivity is being created the server fetching
     *    is done. 
     * 5. When the next activity is initialized it displays the following - 
     *    -song name in the centre
     *    -a gallery view with the centre image set to the same image that was displayed in the 
     *    list view. the other images in the gallery show the fetched options from the server.
     *    -the text view contains the aritst bio, song info, album info, other tracks in the album.
     * 6. The set this as cover button is activated if the gallery selection is changed from the 
     *    initial gallery selection when the activity was started , otherwise it is inactive.
     * 7. The retrieved server datais parsed using a parser(last FM or any other). The parser returns 
     * 	  a song object that is used to populate the controls.
     * 8. All the persistent data storage needs to be managed in the activity life
     *    cycle callbacks
     *    
     *    
     *    
     *    
     *    Image loading mechanism : - 
     *    
     *    1. create an intent with the song details in Activity 1.
     *    2. Passs this intent to the other Activity where it will be used
     *    	 to get the song object.
     *    3. Call the AsyncTask inside Activity 2 that queries lastFM and doenloads the images
     *    	.then inside onPostexecute of the AsyncTask update the files.
     *    
     *    for the Image Loading Mechanism:
     *    1. Create a file cache class , that caches the images on the external 
     *    storage.
     *    2. Create an in memory cache that caches the images in memory.
     *    3. When a request for an image is made, first the memory cache is used, then the file cache is queried ,
     *    	 if the file is found in the file cache, it is loaded into the memory cache and then returned
     *    	 from there. If it is not found in the file cahce, then it is loaded from the web put into the file cache, 
     *    	then put into the memory cache.
     */
    
    
}