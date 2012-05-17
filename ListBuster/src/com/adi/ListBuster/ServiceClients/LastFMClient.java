package com.adi.ListBuster.ServiceClients;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;

import com.adi.ListBuster.R;
import com.adi.ListBuster.SongInfo.SongObject;

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
*/
public class LastFMClient extends MusicClient {
    
    private Context context;
    private DocumentBuilder parser = null;
    private final String serverRoot; 
    private final String apiKey;
   
    
    public LastFMClient(Context context) {
    	super();
        this.context = context;
        this.serverRoot = context.getString(R.string.lastfm_root); 
        this.apiKey = context.getString(R.string.lastfm_api_key);
        this.trackResults = new SongObject();
    }
    


    /* when this is called, this will populate the track name and the artist name
     * fields authoritatively.
     * @see com.adi.ListBuster.MusicClient#searchByTrackName()
     */
    @Override
    protected boolean searchByTrackName() {
        // TODO Auto-generated method stub
        //build the track.search query. since the LFM api
        //aslo allows for artist name in the query, use that too
        //if available
        String[] requestparams;
        String response=null;
        
        //1.check if artist info is present
        if(trackReceived.getArtist()!=null)
        	requestparams = new String []{"method:track.search","track:"+trackReceived.getSong(),"artist:"+trackReceived.getArtist()};
        else
        	requestparams =  new String []{"method:track.search","track:"+trackReceived.getSong()};

        response = queryServer(createRequestString(requestparams));
        Document doc;
    	if((doc=getXMLDoc(response))!=null){
    		//2.found the results!
        	NodeList tracks = doc.getElementsByTagName("track");
        	if(tracks.getLength()>0){
        		//populate the trackname and artist with the top result
        		//and use 4 images from the results.
        		Element track = (Element)tracks.item(0);
    			this.trackResults.setSong(getValue(track,"name",null,null));
    			this.trackResults.setArtist(getValue(track,"artist",null,null));
        		int i=0,count=0;
        		String imageURL;
        		while((i<tracks.getLength()) && (count<4) && (imageURL = getValue(track,"image","size","extralarge"))!=null){
        			this.trackResults.addAlbumArtImage(imageURL);
        			count++;
        		}
        		return true;
        	}
        	else return false;
    	}
    	else
    		return false;
        
    	
    }
    
    
    /* when this is called it will populate the album name field ad optionally,
     * either the album inof field or the track info fielda as those fields 
     * will be returned in the queries.
     * @see com.adi.ListBuster.MusicClient#searchForAlbum()
     */
    @Override
	protected void searchForAlbum() {
		// TODO Auto-generated method stub
		super.searchForAlbum();
		String[] requestParams;
		String response;
		//if it does have album info of its own, try album.geInfo and match for the track.
		if(trackReceived.getAlbum()!=null){
		
			requestParams = new String []{"method:album.getInfo","album:"+trackReceived.getAlbum(),"artist:"+trackResults.getArtist(),"autocorrect:1"};
			response = queryServer(createRequestString(requestParams));
			Document doc;
	    	if((doc=getXMLDoc(response))!=null){
	    		//found the results!
	        	NodeList n = doc.getElementsByTagName("track");
	        	int i;
	        	for(i=0;i<n.getLength();i++){	            	
	        		Element track = (Element)n.item(i);
	        		String trackName = getValue(track,"name",null,null);
	        		if(trackName.equalsIgnoreCase(trackResults.getSong()))
	        			break;
	            }
	        	if(i==n.getLength()){
	        		//no track matches! album name is wrong use the album name from the 
	        		//track.getInfo query instead!
	        	}
	        	else{
	        		//match! use the corrected albumname from the result
	        		Element album = (Element)(doc.getElementsByTagName("album").item(0));
	        		trackResults.setAlbumInfo(getValue(album,"name",null,null)); 
	        		
	        		//use the albuminfo field from the result as well!
	        		Element wiki = (Element)(doc.getElementsByTagName("wiki").item(0));
	        		trackResults.setAlbumInfo(getValue(wiki,"summary",null,null));  
	        		return;
	        	}
	    	}
		}
		//else use the albunmane from track.getInfo to get album name and use it to populate trackinfo at the same time!
		else{
			requestParams = new String []{"method:track.getInfo","track:"+trackResults.getSong(),"artist:"+trackResults.getArtist(),"autocorrect:1"};
			response = queryServer(createRequestString(requestParams));
			Document doc;
	    	if((doc=getXMLDoc(response))!=null){
	    		//get the album name.
	    		Element album = (Element)(doc.getElementsByTagName("album").item(0));
	    		trackResults.setAlbumInfo(getValue(album,"title",null,null));  
	    		
	    		//now since you have called track.getInfo, use the trackinfo as well!
	    		Element wiki = (Element)(doc.getElementsByTagName("wiki").item(0));
        		trackResults.setTrackInfo(getValue(wiki,"summary",null,null));  
        		return;
	    	}
		}
	}
	
    
    @Override
    protected void searchForAlbumInfo(){
    	super.searchForImages();
    	String[] requestParams = new String []{"method:album.getInfo","album:"+trackResults.getAlbum(),"artist:"+trackResults.getArtist(),"autocorrect:1"};
		String response = queryServer(createRequestString(requestParams));
		Document doc;
    	if((doc=getXMLDoc(response))!=null){
    		Element wiki = (Element)(doc.getElementsByTagName("wiki").item(0));
    		trackResults.setAlbumInfo(getValue(wiki,"summary",null,null));  
    	}
    }
    
    
	@Override
	protected void searchForArtistInfo() {
		// TODO Auto-generated method stub
		super.searchForArtistInfo();
		String[] requestParams = new String []{"method:artist.getInfo","artist:"+trackResults.getArtist(),"autocorrect:1"};
		String response = queryServer(createRequestString(requestParams));
		Document doc;
    	if((doc=getXMLDoc(response))!=null){
    		//now since you have called track.getInfo, use the trackinfo as well!
    		Element bio = (Element)(doc.getElementsByTagName("bio").item(0));
    		trackResults.setTrackInfo(getValue(bio,"content",null,null));  
    		return;
    	}
	}
	

	@Override
	protected void searchForTrackInfo() {
		// TODO Auto-generated method stub
		super.searchForTrackInfo();
		String[] requestParams = new String []{"method:track.getInfo","track:"+trackResults.getSong(),"artist:"+trackResults.getArtist(),"autocorrect:1"};
		String response = queryServer(createRequestString(requestParams));
		Document doc;
    	if((doc=getXMLDoc(response))!=null){
    		//now since you have called track.getInfo, use the trackinfo as well!
    		Element wiki = (Element)(doc.getElementsByTagName("wiki").item(0));
    		trackResults.setTrackInfo(getValue(wiki,"summary",null,null));  	
    		return;
    	}
	}


	protected URL createRequestString(String[] paramlist){
    	String request = serverRoot+"&api_key="+apiKey;
    	//unpack params
    	for (String param : paramlist){
    		String key = param.split(":")[0];
    		String value = param.split(":")[1];
    		request = request+"&"+key+"="+value;
    	}
    	
    	URL requestURL = null;
    	
    	try {
			requestURL = new URL(request);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return requestURL;
    }
   
    
    /**this returns the text value contained in the tag inside the part of tree
     * rooted at elem and having the attrname(null) equal to attrval(null).
     * @param elem
     * @param tagname
     * @param attrname
     * @param attrval
     * @return
     */
    protected String getValue(Element elem,String tagname,String attrname,String attrval){
    	String value = null;
    	NodeList nl = elem.getElementsByTagName(tagname);
		 
		for(int i=0;i<nl.getLength();i++){
			Element el = (Element)nl.item(i);	
			if(attrname==null){
				value = el.getFirstChild().getNodeValue();
				break;
			}
			else if(el.getAttribute(attrname).equals(attrval)){
				value = el.getFirstChild().getNodeValue();
				break;
			}
		}
		return value;
    	
    }
    
    
    protected Document getXMLDoc(String response){
    	//lazily create the parser
    	if(this.parser==null){
    		try {
				parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	Document doc=null;
    	try {
			doc = parser.parse(new InputSource(new StringReader(response)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//if there is an error return null.
    	if(doc.getDocumentElement().getAttribute("status").equals("ok"))
    		return doc;
    	else return null;	
    }
    
    
            
}
