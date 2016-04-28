package com.example.mckenna.musicdiscovery;

/**
 * Created by mckenna on 4/24/16.
 */
public class Song {
    private String title;
    private String artist;
    private String album;

    public Song(String title, String artist, String album){
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public Song(String title, String artist){
        this.title = title;
        this.artist = artist;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getArtist(){
        return this.artist;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public String getAlbum(){
        return this.album;
    }

    public void setAlbum(String album) { this.album = album; }

}