package com.example.mckenna.musicdiscovery.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.jdo.PersistenceManager;

import javax.servlet.http.*;
/**
 * Class to store music data in the app engine
 */
public class StoreData extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        PersistenceManager pm = PMF.getPMF().getPersistenceManager();
        try {
            int id;
            try {
                id = Integer.parseInt(req.getParameter("id") + "");
            } catch (NumberFormatException nfe) {
                id = -1;
            }
            String title = req.getParameter("title");
            String artist = req.getParameter("artist");
            String album = req.getParameter("album");

            if (id < 0) {
                throw new IllegalArgumentException("Invalid song id");
            }
            if (title == null || title.length() == 0)
                throw new IllegalArgumentException("Invalid song title");
            if (artist == null || artist.length() == 0)
                throw new IllegalArgumentException("Invalid song artist");
            if (album == null || album.length() == 0)
                throw new IllegalArgumentException("Invalid song album");

            Song song = new Song();
            song.setId(id);
            song.setTitle(title);
            song.setArtist(artist);
            song.setAlbum(album);
            pm.makePersistent(song);

            out.write(formatAsJson(song));

        } catch (IllegalArgumentException iae){
            out.write(formatAsJson(iae));
        } finally {
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
        obj.put("id", Integer.toString(song.getId()));
        obj.put("title", song.getTitle());
        obj.put("artist", song.getArtist());
        obj.put("album", song.getAlbum());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rv = gson.toJson(obj);
        return rv;
    }
}
