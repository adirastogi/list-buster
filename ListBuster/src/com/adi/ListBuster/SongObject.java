package com.adi.ListBuster;

import android.net.Uri;

/*
 * This is the data structure that stores the information for a song
 */

public class SongObject {
    public SongObject(String imagepath,String song,
            String artist, String info) {
        super();
        this.imagepath = imagepath;
        this.song = song;
        this.info = info;
        this.artist = artist;
    }
    
    private String song;
    private String info;
    private String artist;
    private String imagepath;
    /**
     * @return the imagepath
     */
    public Uri getImagepath() {
        //if the song ws able to get an associated song album art
        //from the content provider,use its uri or else use the uri of the default image.
        Uri albumArtURI = null;
        albumArtURI = Uri.parse(imagepath);
        return albumArtURI;
    }
    /**
     * @param imagepath the imagepath to set
     */
    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
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
