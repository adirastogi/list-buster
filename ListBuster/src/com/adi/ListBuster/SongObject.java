package com.adi.ListBuster;
/*
 * This is the data structure that stores the information for a song
 */

public class SongObject {
    public SongObject(String filepath, String ranking, String album,
            String info, String artist) {
        super();
        this.filepath = filepath;
        this.ranking = ranking;
        this.album = album;
        this.info = info;
        this.artist = artist;
    }
    private String filepath;
    private String ranking;
    private String album;
    private String info;
    private String artist;
    /**
     * @return the filepath
     */
    public String getFilepath() {
        return filepath;
    }
    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    /**
     * @return the ranking
     */
    public String getRanking() {
        return ranking;
    }
    /**
     * @param ranking the ranking to set
     */
    public void setRanking(String ranking) {
        this.ranking = ranking;
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
