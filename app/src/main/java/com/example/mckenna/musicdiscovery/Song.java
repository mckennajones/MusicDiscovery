package com.example.mckenna.musicdiscovery;

/**
 * Class implementation for Songs
 */
public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;

    public Song(){

    }

    public Song(int id, String title, String artist, String album){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public Song(String title, String artist, String album){
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
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

    public void setAlbum(String album){
        this.album = album;
    }

}