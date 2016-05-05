package com.example.mckenna.musicdiscovery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.ConnectException;

/**
 * Created by mckenna on 5/4/16.
 */
public class SongCursorAdapter extends CursorAdapter {

    public SongCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.song_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView)view.findViewById(R.id.title);
        TextView tvArtist = (TextView)view.findViewById(R.id.artist);
        TextView tvAlbum = (TextView)view.findViewById(R.id.album);
        TextView tvCount = (TextView)view.findViewById(R.id.count);

        ImageView coverView = (ImageView)view.findViewById(R.id.cover);

        String artist = cursor.getString(cursor.getColumnIndexOrThrow("artist"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String album = cursor.getString(cursor.getColumnIndexOrThrow("album"));
        String count = cursor.getString(cursor.getColumnIndexOrThrow("count"));
        String cover = cursor.getString(cursor.getColumnIndexOrThrow("cover"));

        Uri coverUri = Uri.parse(cover);

        coverView.setImageURI(coverUri);
        tvTitle.setText(title);
        tvArtist.setText(artist);
        tvAlbum.setText(album);
        tvCount.setText(count);
    }
}
