package com.adi.ListBuster.MainView;

import com.adi.ListBuster.R;
import com.adi.ListBuster.R.id;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RowHolder{

    
    private ImageView albumArt;
    private TextView songName;
    private TextView artistName;
    private View parent;
    
    public RowHolder(View parent) {
        super();
        this.parent = parent;
        albumArt = null;
        songName = null;
        artistName = null;
    }
    /**
     * @return the albumArt
     */
    public ImageView getAlbumArt() {
        
        if(albumArt==null) 
            albumArt = (ImageView)parent.findViewById(R.id.albumArt);
            
        return albumArt;
    }
    /**
     * @return the songName
     */
    public TextView getSongName() {
        
        if(songName==null)
            songName = (TextView)parent.findViewById(R.id.songName);
        
        return songName;
    }
    /**
     * @return the artistName
     */
    public TextView getArtistName() {
        
        if(artistName==null)
            artistName = (TextView)parent.findViewById(R.id.artistName);
        
        return artistName;
    }
    
}
