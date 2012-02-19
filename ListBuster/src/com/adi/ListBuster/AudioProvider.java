package com.adi.ListBuster;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class AudioProvider {
    
    public static final int HAS_RESULTS=1;
    public static final int IS_NULL=2;
    public static final int CURSOR_ERROR=3;
    
    
    private Context appContext;
    private Cursor songCursor;
    private Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    
    public AudioProvider(Context ctxt) {
        appContext = ctxt;
        
    }
    
    public Cursor getSongCursor(){
        
        
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM_ART,
        };
        
        songCursor = appContext.getContentResolver().query(musicUri, projection, null, null, null);
        
        return songCursor;
    }
   
    public Uri getAlbumArtUri() {
        
        if(songCursor==null)
            return null;
        
        int albumIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int albumID = songCursor.getInt(albumIndex);
        Uri authority = Uri.parse("content://media/external/audio/albumart");
        Uri artUri = ContentUris.withAppendedId(authority, albumID);
        return artUri;
    }
   
}
