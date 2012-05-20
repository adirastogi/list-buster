package com.adi.ListBuster.MainView;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.adi.ListBuster.R;
import com.adi.ListBuster.SongInfo.AudioProvider;
import com.adi.ListBuster.SongInfo.SongObject;

public class MainListAdapter extends CursorAdapter{

    
	
    
	private AudioProvider ap;
    
    public MainListAdapter(Context context, AudioProvider ap) {
    	// TODO Auto-generated constructor stub
    	super(context,ap.getSongsCursor());
        this.ap = ap;
    }

    @Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
    	Cursor c = this.getCursor();
    	c.moveToPosition(position);
    	SongObject track = new SongObject();
    	track.setSong(ap.getSongName());
    	track.setArtist(ap.getArtistName());
    	track.setAlbum(ap.getAlbumName());
    	track.setMediaStoreImage(ap.getAlbumArtUri());
    	return track;
    }    
    
	@Override
	public void bindView(View view, Context context, Cursor songCursor) {
		// TODO Auto-generated method stub
		
        RowHolder holder = (RowHolder)view.getTag();
        
        /*We access the methods of theutility class AudioProvider to 
         * get the formatted information from the cursor since the AudiProvider
         * object apparently holds a reference to the same cursor object as is 
         * received in this method.
         * TODO fix this.
         */

        holder.getArtistName().setText(ap.getArtistName());
        holder.getSongName().setText(ap.getSongName());
        holder.getAlbumArt().setImageURI(ap.getAlbumArtUri()); 
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		 RowHolder holder;
	     View rowView = null;
	      
	     LayoutInflater inflator = LayoutInflater.from(context);
	     //TODO had to change the parent to null to get this to work , why ??
	     rowView = inflator.inflate(R.layout.row,null);
	     holder = new RowHolder(rowView);
	     rowView.setTag(holder);
	       	        
	     return rowView;

	}
    
    

}
