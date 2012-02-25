package com.adi.ListBuster;

import android.content.Context;

public class LastFMClient {
    
    private Context context;
    private final String serverRoot; 
    private final String apiKey;
    
    public LastFMClient(Context context) {
        this.context = context;
        this.serverRoot = context.getString(R.string.lastfm_root); 
        this.apiKey = context.getString(R.string.lastfm_api_key);
    }
    
    
    public LastFMClient getAlbumArt(String albumName) {
        
        
        
        return this;
    }
            
}
