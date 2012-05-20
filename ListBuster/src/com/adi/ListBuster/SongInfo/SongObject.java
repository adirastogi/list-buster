package com.adi.ListBuster.SongInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * This is the data structure that stores the information for a song.
 * This data strucutre is used to pack and pass the information about
 * the song between different parts of the aplication, i.e., the music service 
 * client, the android media store etc.
 */

public class SongObject implements Parcelable {
	
    
    
    /**
     * 
     */
    private String song;        //this maps to the field display name from the media store.//
    private String artist;      //this is fetched from the media store.//
    private String album;		//this is fetched from the media store.//
    private Uri mediaStoreImage;   //album art from the media store. not sure if it should be a pathname.//
    

    private String albumInfo;        //this field will be populated with data from a service.//  
    private String artistInfo;	     //this field will be populated with data from a service.//
    private String trackInfo;		//this field will be populated with data from a service.//	
    private List<URL> albumArtImages;//this field will be populated with data from a service.//
    
    
    public SongObject(){
    	
    }
    
    private SongObject(Parcel in) {
        song = in.readString();
        artist = in.readString();
        album = in.readString();
        mediaStoreImage = in.readParcelable(null);
        albumInfo = in.readString();
        artistInfo = in.readString();
        trackInfo = in.readString();
        in.readList(albumArtImages,null);
    }
    
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(song);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeParcelable(mediaStoreImage, 0);
        
        dest.writeString(albumInfo);
        dest.writeString(artistInfo);
        dest.writeString(trackInfo);
        dest.writeList(albumArtImages);
        
        
    }
    
    public Uri getMediaStoreImage() {
		return mediaStoreImage;
	}

	public void setMediaStoreImage(Uri mediaStoreImage) {
		this.mediaStoreImage = mediaStoreImage;
	}

    
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

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

   
	
}
