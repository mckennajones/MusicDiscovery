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

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getArtist(){
        return artist;
    }

    public void setArtist(){
        this.artist = artist;
    }

    public String getAlbum(){
        return album;
    }

    public void setAlbum(){
        this.album = album;
    }
}