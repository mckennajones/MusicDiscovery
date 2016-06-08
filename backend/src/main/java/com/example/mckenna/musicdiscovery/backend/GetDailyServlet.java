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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to service get request for daily stats
 */
public class GetDailyServlet extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        PersistenceManager pm = PMF.getPMF().getPersistenceManager();
        try {
            List<TOPSong> all_songs = TOPSong.loadAll(pm);

            Date current_date = new Date();
            SimpleDateFormat year_month_day = new SimpleDateFormat("yyyy/MM/dd");
            String date_string = year_month_day.format(current_date);

            TOPSong todayTop = new TOPSong();

            for(TOPSong s : all_songs){
                    if(s.getDay().equals(date_string))
                        todayTop = s;
            }

            HashMap<String, String> obj = new HashMap<String, String>();

            obj.put("artist", todayTop.getArtist());

            obj.put("title", todayTop.getTitle());

            obj.put("album", todayTop.getAlbum());

            obj.put("date", todayTop.getDay());

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
}
