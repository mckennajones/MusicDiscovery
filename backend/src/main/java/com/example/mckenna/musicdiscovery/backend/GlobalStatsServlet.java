/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.mckenna.musicdiscovery.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

public class GlobalStatsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PersistenceManager pm = PMF.getPMF().getPersistenceManager();

        try {

            List<Song> all_songs = Song.loadAll(pm);
            Map.Entry<String, Integer> topSong = Song.mostPlayedSong(all_songs);

            //create TOPSong object
            TOPSong top = new TOPSong();

            //pull song title from map.entry key
            String top_title = topSong.getKey();

            //fill in TOPSong title
            top.setTitle(top_title);


            //loop through all songs list to get album and artist information and set TOPSong artist/album
            for (Song s : all_songs) {
                if (s.getTitle().equals(top_title)) {
                    top.setArtist(s.getArtist());
                    top.setAlbum(s.getAlbum());
                }
            }

            //create a date object
            Date current_date = new Date();

            //format to yyyy/MM/dd and get that as a string
            SimpleDateFormat year_month_day = new SimpleDateFormat("yyyy/MM/dd");
            String date_string = year_month_day.format(current_date);

            //fill in TOPSong day
            top.setDay(date_string);

            //put in datastore
            pm.makePersistent(topSong);

        } catch (IllegalArgumentException iae) {
            //the fuck goes here

        } finally {
            pm.close();
        }
    }

}
