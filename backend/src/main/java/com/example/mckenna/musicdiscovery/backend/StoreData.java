package com.example.mckenna.musicdiscovery.backend;

import com.google.appengine.repackaged.com.google.common.base.Pair;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import javax.servlet.http.*;

import sun.rmi.runtime.Log;

/**
 * Class to store music data in the app engine
 */
@SuppressWarnings("serial")
public class StoreData extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        PersistenceManager pm = PMF.getPMF().getPersistenceManager();
        try {
            String title = req.getParameter("title");
            String artist = req.getParameter("artist");
            String album = req.getParameter("album");

            if (title == null || title.length() == 0)
                throw new IllegalArgumentException("Invalid song title");
            if (artist == null || artist.length() == 0)
                throw new IllegalArgumentException("Invalid song artist");
            if (album == null || album.length() == 0)
                throw new IllegalArgumentException("Invalid song album");

            Song song = new Song();
            song.setTitle(title);
            song.setArtist(artist);
            song.setAlbum(album);
            pm.makePersistent(song);

            out.write(formatAsJson(song));

        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        PersistenceManager pm = PMF.getPMF().getPersistenceManager();
        try {
            List<Song> all_songs = Song.loadAll(pm);

            HashMap<String, String> obj = new HashMap<String, String>();

            Map.Entry<String, Integer> topArtist = Song.mostPlayedArtist(all_songs);
            obj.put("artist", topArtist.getKey());

            Map.Entry<String, Integer> topSong = Song.mostPlayedSong(all_songs);
            obj.put("title", topSong.getKey());

            Map.Entry<String, Integer> topAlbum = Song.mostPlayedAlbum(all_songs);
            obj.put("album", topAlbum.getKey());

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String rv = gson.toJson(obj);

            out.write(rv);
        } catch (
                JDOObjectNotFoundException ex
                )

        {
            out.write("JDO Object not found\n");
        } finally

        {
            pm.close();
        }

    }

    public static String formatAsJson(Exception e) {
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("errormsg", e.getMessage());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rv = gson.toJson(obj);
        return rv;
    }

    public static String formatAsJson(Song song) {
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("title", song.getTitle());
        obj.put("artist", song.getArtist());
        obj.put("album", song.getAlbum());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rv = gson.toJson(obj);
        return rv;
    }

    private long getLong(HttpServletRequest req, String key, long dflt) {
        long rv;
        try {
            rv = Long.parseLong(req.getParameter(key) + "");
        } catch (NumberFormatException nfe) {
            rv = dflt;
        }
        return rv;
    }


}

