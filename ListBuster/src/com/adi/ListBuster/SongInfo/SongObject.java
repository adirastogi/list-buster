package com.adi.ListBuster.SongInfo;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;

/*
 * This is the data structure that stores the information for a song.
 * This data strucutre is used to pack and pass the information about
 * the song between different parts of the aplication, i.e., the music service 
 * client, the android media store etc.
 */

public class SongObject implements Serializable {
	
    public SongObject(Uri imagepath,String song,
            String artist, String info) {
        super();
        this.mediaStoreImage = imagepath;
        this.song = song;
        this.albumInfo = info;
        this.artist = artist;
        this.albumArtImages = new ArrayList<URL>();
    }
    
    public SongObject(){
    	
    }
    
    private String songID;      //the song ID from the media store.//
    private String song;        //this maps to the field display name from the media store.//
    private String albumInfo;        //this field will be populated with data from a service.//  
    private String artistInfo;	     //this field will be populated with data from a service.//
    private String trackInfo;		//this field will be populated with data from a service.//	
    

	private String artist;      //this is fetched from the media store.//
    private Uri mediaStoreImage;   //album art from the media store. not sure if it should be a pathname.//
    private String album;
    private List<URL> albumArtImages;
    
    public String getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}
    
    /**
     * @return the image uri collection
     */
	public List<URL> getAlbumArtImages() {
		return albumArtImages;
	}
	/**
     * @param the image uri to add to collection
     */
	public void addAlbumArtImage(String imageUrl) {
		if(imageUrl!=null){
			try {
				albumArtImages.add(new URL(imageUrl));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
     * @return the album
     */
    public String getAlbum() {
        return album;
    }
    /**
     * @param album the album to set
     */
    public void setAlbum(String album) {
        this.album = album;
    }
    /**
     * @return the imagepath
     */
    public Uri getImagepath() {
        //if the song ws able to get an associated song album art
        //from the content provider,use its uri or else use the uri of the default image.
        return mediaStoreImage;
    }
    /**
     * @param imagepath the imagepath to set
     */
    public void setImagepath(Uri imagepath) {
        this.mediaStoreImage = imagepath;
    }
    /**
     * @return the song
     */
    public String getSong() {
        return song;
    }
    /**
     * @param song the song to set
     */
    public void setSong(String song) {
        this.song = song;
    }
    /**
     * @return the info
     */
    public String getAlbumInfo() {
        return albumInfo;
    }
    /**
     * @param info the info to set
     */
    public void setAlbumInfo(String info) {
        this.albumInfo = info;
    }
    /**
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }
    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }
    /**
     * @return the artistInfo
     */
    public String getArtistInfo() {
        return artistInfo;
    }
    /**
     * @param artistInfo the artistInfo to set
     */
    public void setArtistInfo(String artistInfo) {
        this.artistInfo = artistInfo;
    }
	
}
