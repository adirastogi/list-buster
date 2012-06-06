package com.adi.ListBuster.ServiceClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.adi.ListBuster.SongInfo.SongObject;

public abstract class MusicClient {
    
    


    /**This method is the template method that defines the 
     * steps in the metadata query process. The method also has
     * hooks in between for different clients to hook into the
     * process and do their own stuff.
     * @return
     */
    protected SongObject trackResults;
    protected SongObject trackReceived;
    
    protected MusicClient(SongObject tr){
    	trackReceived = tr;
    	trackResults = new SongObject();

    
    
    }
    
    protected final String queryServer(String root) {
        

        
        
        String response = null;
        //HttpURLConnection conn = null; 
        try {
            //conn = (HttpURLConnection)rootServer.openConnection();
            HttpClient client = new DefaultHttpClient();
            HttpGet req = new HttpGet(root);
            HttpResponse resp = client.execute(req);
            //InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
            StringWriter writer = new StringWriter();
            String buffer;
            while((buffer = reader.readLine())!=null)
                writer.write(buffer);
            response = writer.toString();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            //conn.disconnect();
            
        }
        
        return response;
        
    }
    
    public final SongObject getMusicMetadata() {
        
        if(searchByTrackName()) {
           if(trackResults.getArtist()==null)
               searchForArtist();
           if(trackResults.getAlbum()==null)
               searchForAlbum();
           if(trackResults.getAlbumArtImages()==null|| trackResults.getAlbumArtImages().size()<3)
               searchForImages();
           if(trackResults.getAlbumInfo()==null)
               searchForAlbumInfo();
           if(trackResults.getTrackInfo()==null)
        	   searchForTrackInfo();
           if(trackResults.getArtistInfo()==null)
               searchForArtistInfo();
           
           return trackResults;
        }
        else return null;
            
    }
    
    /** This is an abstarct method that service client classes must
     *  provide. the basis of this method is that all service providers
     *  should be able to find something related to the trackName on the
     *  first attempt. if not the exact track name, then the best guess 
     *  that they can make. Once this is done they can use the remaining
     *  hook methods to fill the remaining info or just do it in  searchByTrackName() itself.
     * @param trackName
     * @return whether the track name was found or not
     */
    protected abstract boolean searchByTrackName();
        
    /**sets the artistname in the trackresults obj. default implementation does nothing
     * @param artistName
     */
    protected void searchForArtist() {
        
    }
    
    protected void searchForAlbum() {
        
    }
    
    protected void searchForImages() {
        
    }
    
    protected void searchForAlbumInfo() {
        
    }
    
    protected void searchForTrackInfo(){
    	
    }
    
    protected void searchForArtistInfo() {
        
    }
}
