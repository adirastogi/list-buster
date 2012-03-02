package com.adi.ListBuster;

import android.net.Uri;

/*
 * This is the data structure that stores the information for a song
 */

public class SongObject {
    public SongObject(Uri imagepath,String song,
            String artist, String info) {
        super();
        this.galleryImages = imagepath;
        this.song = song;
        this.info = info;
        this.artist = artist;
    }
    
    private String songID;      //the song ID from the media store.//
    private String song;        //this maps to the field display name from the media store.//
    private String info;        //this field will be populated with data from a service.//  
    private String artist;      //this is fetched from the media store.//
    private Uri galleryImages;   //album art from the media store. not sure if it should be a pathname.//
    /**
     * @return the imagepath
     */
    public Uri getImagepath() {
        //if the song ws able to get an associated song album art
        //from the content provider,use its uri or else use the uri of the default image.
        return galleryImages;
    }
    /**
     * @param imagepath the imagepath to set
     */
    public void setImagepath(Uri imagepath) {
        this.galleryImages = imagepath;
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
    public String getInfo() {
        return info;
    }
    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
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
    
}
