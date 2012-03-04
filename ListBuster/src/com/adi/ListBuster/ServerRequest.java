/**
 * 
 */
package com.adi.ListBuster;

import android.os.AsyncTask;

/**
 * @author vsales
 *
 */
public class ServerRequest extends AsyncTask<LastFMClient, Void, SongObject>{

    
    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(URL,Void,String)
     */
    @Override
    protected SongObject doInBackground(LastFMClient... client) {
        // TODO Auto-generated method stub
        LastFMClient lfmClient = client[0];
        SongObject track = lfmClient.getMusicMetadata();
        return track;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(SongObject response) {
        // process the xml recived here
        
        
        
    }

    
    
}
