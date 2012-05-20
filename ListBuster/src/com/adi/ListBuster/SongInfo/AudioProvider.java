package com.adi.ListBuster.SongInfo;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public class AudioProvider {
    
    public static final int HAS_RESULTS=1;
    public static final int IS_NULL=2;
    public static final int CURSOR_ERROR=3;
    private static Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    
    private Context appContext;		//for storing the application context	
    private Cursor songCursor; 		//this variable holds the present position of the cursor
    
    
    public AudioProvider(Context ctxt) {
        appContext = ctxt;
        
    }
    /*This method is responsible for querying the media store to get the cursor object
     * that contains the rows for all the songs.
    */
    public Cursor getSongsCursor(){
        
        
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
                //TODO why is this giving an exception ?? It says no such column
               // MediaStore.Audio.Media.ALBUM_ART
        };
        
        songCursor = appContext.getContentResolver().query(musicUri, projection, null, null, null);
        
        return songCursor;
    }
 
    /**Returns the albumart for the song record currently pointed to by the cursor.
     * @return Uri for the albumart resource
     */
    public Uri getAlbumArtUri() {
        
        if(songCursor==null)
            return null;
        
        int albumIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int albumID = songCursor.getInt(albumIndex);
        Uri authority = Uri.parse("content://media/external/audio/albumart");
        Uri artUri = ContentUris.withAppendedId(authority, albumID);
        return artUri;
    }
    
    /**Returns the albumart for the song record currently pointed to by the cursor.
     * @return the string for songname
     */
    public String getSongName(){
    	
    	if(songCursor==null)
    		return null;
    	
    	int songNameIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
    	String songName = songCursor.getString(songNameIndex);
    	return songName;
    }
    
    /**Returns the artistname for the song record currently pointed to by the cursor.
     * @return string artistname
     */
    public String getArtistName(){
    	
    	if(songCursor==null)
    		return null;
    	
    	int artistNameIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
    	String artistName = songCursor.getString(artistNameIndex);
    	return artistName;
    }
   
    /**Returns the albumname for the song record currently pointed to by the cursor.
     * @return string albumname
     */
    public String getAlbumName(){
    	
    	if(songCursor==null)
    		return null;
    	
    	int albumNameIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
    	String albumName = songCursor.getString(albumNameIndex);
    	return albumName;
    }
    
    /**basically calls the moveToNext() of cursor.
     * @return true if the cursor is incremented to a valid record, false otherwise
     */
    public boolean nextSong(){
    	
    	return songCursor.moveToNext();
    }
}
