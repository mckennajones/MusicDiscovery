package com.example.mckenna.musicdiscovery.backend;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.repackaged.com.google.common.base.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Song class for backend
 */
@PersistenceCapable
public class Song {

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

    public void setTitle(String title){
        this.title = title != null ? title : "";
    }

    public void setArtist(String artist){
        this.artist = artist != null ? artist : "";
    }

    public void setAlbum(String album){
        this.album = album != null ? album : "";
    }

    public static List<Song> loadAll(PersistenceManager pm) {
        Query query = pm.newQuery(Song.class);
        query.setOrdering("artist");

        List<Song> rv = (List<Song>) query.execute();
        rv.size(); // forces all records to load into memory
        return rv;
    }

    public static Pair<String, Integer> mostPlayedArtist(List<Song> songs) {

        Map<String, Integer> map = new HashMap<>();
        for (Song s : songs) {
            Integer val = map.get(s.getArtist());
            map.put(s.getArtist(), val == null ? 1 : val + 1);
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        Pair<String, Integer> rv = new Pair<String, Integer>(max.getKey(), max.getValue());

        return rv;

    }

    public Pair<String, Integer> mostPlayedAlbum(List<Song> songs) {

        Map<String, Integer> map = new HashMap<>();
        for (Song s : songs) {
            Integer val = map.get(s.getAlbum());
            map.put(s.getAlbum(), val == null ? 1 : val + 1);
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        Pair<String, Integer> rv = new Pair<String, Integer>(max.getKey(), max.getValue());

        return rv;

    }

    public static Pair<String, Integer> mostPlayedSong(List<Song> songs) {

        Map<String, Integer> map = new HashMap<>();
        for (Song s : songs) {
            Integer val = map.get(s.getTitle());
            map.put(s.getTitle(), val == null ? 1 : val + 1);
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        Pair<String, Integer> rv = new Pair<String, Integer>(max.getKey(), max.getValue());

        return rv;

    }


}
