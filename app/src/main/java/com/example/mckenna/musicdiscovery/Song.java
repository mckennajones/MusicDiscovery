package com.example.mckenna.musicdiscovery;

/**
 * Class implementation for Songs
 */
public class Song {
    private int _id;
    private String title;
    private String artist;
    private String album;
    private int count;

    public Song(){

    }

    public Song(int id, String title, String artist, String album){
        this._id = id;
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
        return this._id;
    }

    public void setId(int id){
        this._id = id;
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

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return this.count;
    }

}