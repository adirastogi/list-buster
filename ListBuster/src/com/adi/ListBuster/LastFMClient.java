package com.adi.ListBuster;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

/* 
 * this class constructs query URLs for the last.fm API.
 * The following calls are made :-
 * 1. album.getInfo
 *      params: album name,artist name,autocorrect,apikey
 *      returns: albumnane,artist name,mbid,releasedate,
 *               images(5 sizes),tracklist,albuminfo
 * 2. album.search
 *      params:albumname,apikey,limit(numpages),page_to_fetch
 *      returns: list sorted by relevance. albumname,artist name,
 *               images,mbid
 * 3.track.getInfo
 *      params:artist,track name,mbid,autocorrect,apikey
 *      returns: name,artist,album,info,images
 * 4.track.search
 *      params: trackname ,limit,pages,artist,apikey
 *      returns: list sorted by relevance. trackname,images
 * 5.artist.getInfo
 *      params: artist,mbid,autocorrect,apikey
 *      returns:name,mbid,images,info
 *    
 * 
 * 


*/
public class LastFMClient extends MusicClient {
    
    private Context context;
    private final String serverRoot; 
    private final String apiKey;
    
    
    public LastFMClient(Context context) {
        this.context = context;
        this.serverRoot = context.getString(R.string.lastfm_root); 
        this.apiKey = context.getString(R.string.lastfm_api_key);
    }
    


    @Override
    protected boolean searchByTrackName(SongObject track) {
        // TODO Auto-generated method stub
        //build the track.search query. since the LFM api
        //aslo allows for artist name in the query, use that too
        //if available
        String request;
        String response;
        if(track.getArtist()!=null)
            request = serverRoot+"?method=track.search"+"&api_key="+apiKey+
                    "&track="+track.getSong()+"&artist="+track.getArtist();
        else
            request = serverRoot+"?method=track.search"+"&api_key="+apiKey+
            "&track="+track.getSong();
        
        try {
            response = queryServer(new URL(request));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        //TODO parse the lfm response xml
        
        
        
        return false;
    }
    
    
    
            
}
