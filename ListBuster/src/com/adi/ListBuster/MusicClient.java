/**
 * 
 */
package com.adi.ListBuster;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

/**
 * @author vsales
 *
 */
public class MusicClient extends AsyncTask<URL, Void, String>{

    private Parser responseParser;
    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(URL,Void,String)
     */
    @Override
    protected String doInBackground(URL... root) {
        // TODO Auto-generated method stub
        
        URL rootServer = root[0];
        String response = null;
        HttpURLConnection conn = null; 
        try {
            conn = (HttpURLConnection)rootServer.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringWriter writer = new StringWriter();
            String buffer;
            while((buffer = reader.readLine())!=null)
                writer.write(buffer);
            response = writer.toString();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        
        
        return response;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String response) {
        // process the xml recived here
        
        SongObject s = responseParser.parseResponse(response);
        
    }

    
    
}
