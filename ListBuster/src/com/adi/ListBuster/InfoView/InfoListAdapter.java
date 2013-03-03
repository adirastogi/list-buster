/*
 * This class is the adpater for the infolist that is populated sit data from the server.
 */



package com.adi.ListBuster.InfoView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.ListBuster.R;
import com.adi.ListBuster.SongInfo.SongObject;

public class InfoListAdapter extends BaseExpandableListAdapter{
    
    private Context context;
    private final ArrayList<String> headers;
    private ArrayList<ArrayList<String>> content;

    public InfoListAdapter(Context context,SongObject track) {
        super();
        this.context = context;
        this.headers = new ArrayList<String>();
        this.headers.add("Track");
        this.headers.add("Artist");
        this.headers.add("Album");
        content = new ArrayList<ArrayList<String>>();
        
        
        ArrayList<String> TrackInfo = new ArrayList<String>();
        TrackInfo.add(track.getSong());
        TrackInfo.add(track.getTrackInfo());
        
        ArrayList<String> ArtistInfo = new ArrayList<String>();
        ArtistInfo.add(track.getArtist());
        ArtistInfo.add(track.getArtistInfo());
        
        ArrayList<String> AlbumInfo = new ArrayList<String>();
        AlbumInfo.add(track.getAlbum());
        AlbumInfo.add(track.getAlbumInfo());
        
        content.add(TrackInfo);
        content.add(ArtistInfo);
        content.add(AlbumInfo);
    }
    
    public void setDisplayTrack(SongObject track){
    	
    	//content.clear();
    	
    	
    	content.get(0).set(0, track.getSong());
    	content.get(0).set(1, track.getTrackInfo());
    	
    	content.get(1).set(0, track.getArtist());
    	content.get(1).set(1, track.getArtistInfo());
    	
    	content.get(2).set(0, track.getAlbum());
    	content.get(2).set(1, track.getAlbumInfo());
    	
    	
        this.notifyDataSetChanged();
    }

    public Object getChild(int headerPos, int contentPos) {
        // TODO Auto-generated method stub
        return content.get(headerPos).get(contentPos);
    }

    public long getChildId(int headerPos, int contentPos) {
        // TODO Auto-generated method stub
        return contentPos;
    }

    public View getChildView(int headerPos, int contentPos, boolean isLastChild, View convertView,
            ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null) {
            LayoutInflater inflator = LayoutInflater.from(context);
            convertView = inflator.inflate(R.layout.content_layout,null);
        }
        TextView contentText = (TextView)convertView.findViewById(R.id.content_text);
        contentText.setText((String)getChild(headerPos,contentPos));
        return convertView;
    }

    public int getChildrenCount(int headerPos) {
        // TODO Auto-generated method stub
        return content.get(headerPos).size();
    }

    public Object getGroup(int headerPos) {
        // TODO Auto-generated method stub
        return headers.get(headerPos);
    }

    public int getGroupCount() {
        // TODO Auto-generated method stub
        return headers.size();
    }

    public long getGroupId(int headerPos) {
        // TODO Auto-generated method stub
        return headerPos;
    }

    public View getGroupView(int headerPos, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null) {
            LayoutInflater inflator = LayoutInflater.from(context);
            convertView = inflator.inflate(R.layout.header_layout,null);
        }
        TextView headerText = (TextView)convertView.findViewById(R.id.header_text);
        headerText.setText((String)getGroup(headerPos));
        ImageView arrowDown = (ImageView)convertView.findViewById(R.id.header_arrow);
        if(isExpanded) 
            arrowDown.setImageResource(R.drawable.arrow_down);
        else
            arrowDown.setImageResource(R.drawable.arrow_left);
        
        return convertView;
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}
