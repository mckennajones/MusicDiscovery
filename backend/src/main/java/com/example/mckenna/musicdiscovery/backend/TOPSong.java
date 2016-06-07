package com.example.mckenna.musicdiscovery.backend;

import java.util.Date;

import javax.jdo.annotations.PersistenceAware;
import javax.jdo.annotations.Persistent;

public class TOPSong extends Song {

    @Persistent
    private String day;

    public void setDay(String day){
        this.day = day != null ? day : "";
    }

}
