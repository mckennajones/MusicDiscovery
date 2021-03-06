package com.example.mckenna.musicdiscovery.backend;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.PersistenceAware;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class TOPSong {

    @Persistent
    private String day;

    @Persistent
    private String title;

    @Persistent
    private String artist;

    @Persistent
    private String album;

    public String getTitle(){
        return title != null ? title : "";
    }

    public String getArtist(){
        return artist != null ? artist : "";
    }

    public String getAlbum(){
        return album != null ? album : "";
    }

    public String getDay(){
        return day != null ? day : "";
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

    public void setDay(String day){
        this.day = day != null ? day : "";
    }

    public static List<TOPSong> loadAll(PersistenceManager pm) {
        Query query = pm.newQuery(TOPSong.class);
        query.setOrdering("artist");

        List<TOPSong> rv = (List<TOPSong>) query.execute();
        rv.size(); // forces all records to load into memory
        return rv;
    }
}
