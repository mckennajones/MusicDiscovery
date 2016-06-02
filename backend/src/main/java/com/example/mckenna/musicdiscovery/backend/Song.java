package com.example.mckenna.musicdiscovery.backend;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
/**
 * Song class for backend
 */
@PersistenceCapable
public class Song {
    @PrimaryKey
    private int id;

    @Persistent
    private String title;

    @Persistent
    private String artist;

    @Persistent
    private String album;

    @Persistent int count;

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title != null ? title : "";
    }

    public String getArtist(){
        return artist != null ? artist : "";
    }

    public String getAlbum(){
        return album != null ? album : "";
    }

    public int getCount(){ return count;}

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title != null ? title : "";
    }

    public void setArtist(String artist){
        this.artist = artist != null ? artist : "";
    }

    public void setAlbum(String album){
        this.album = album != null ? album : "";
    }

    public void setCount(int count){ this.count = count;}
}
