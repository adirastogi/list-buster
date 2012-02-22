package com.adi.ListBuster;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class MusicAdapter extends CursorAdapter{

    
    private AudioProvider ap;
    
    public MusicAdapter(Context context, AudioProvider ap) {
    	// TODO Auto-generated constructor stub
    	super(context,ap.getSongsCursor());
        this.ap = ap;
    }

	@Override
	public void bindView(View view, Context context, Cursor songCursor) {
		// TODO Auto-generated method stub
		
        RowHolder holder = (RowHolder)view.getTag();
        
        /*We access the methods of theutility class AudioProvider to 
         * get the formatted information from the cusor since the AudiProvider
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
	     rowView = inflator.inflate(R.layout.row,parent);
	     holder = new RowHolder(rowView);
	     rowView.setTag(holder);
	       	        
	     return rowView;

	}
    
    

}
