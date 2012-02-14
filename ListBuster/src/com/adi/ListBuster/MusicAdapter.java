package com.adi.ListBuster;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends ArrayAdapter<SongObject>{

    private Context context;
    private List<SongObject> songList;
    
    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        RowHolder holder;
        View rowView = convertView;
   
        
        if(rowView==null) {
            LayoutInflater inflator = LayoutInflater.from(context);
            rowView = inflator.inflate(R.layout.row,parent);
            holder = new RowHolder(rowView);
            rowView.setTag(holder);
        }
        else {
            holder = (RowHolder)rowView.getTag();
        }
        

        holder.getArtistName().setText(songList.get(position).getArtist());
        holder.getSongName().setText(songList.get(position).getSong());
        holder.getAlbumArt().setImageURI(songList.get(position).getImagepath()); 
        
        return rowView;
        
    }

    public MusicAdapter(Context context, int textViewResourceId, List<SongObject> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.songList = objects;
    }
    
    

}
