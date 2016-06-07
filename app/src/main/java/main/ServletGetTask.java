package main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mckenna.musicdiscovery.R;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;

/**
 * Servlet post async task to execute backend functions
 */

class ServletGetTask extends AsyncTask<Context, Void, String> {
    private Context context;
    private View rootView;

    public ServletGetTask(View rv){
        rootView = rv;
    }

    @Override
    protected String doInBackground(Context... params) {
        context = params[0];

        try {
            // GET request
            URL url = new URL("https://music-discovery-1316.appspot.com/storedata");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // End of Get request.

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return response.toString();
            }
            return "Error: " + responseCode + " " + connection.getResponseMessage();

        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String buildPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        try {
            HashMap<String, String> map = new HashMap<String, String>();
            JSONObject jObject = new JSONObject(result.toString());
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = jObject.getString(key);
                map.put(key, value);
            }

            TextView songCardTitle = (TextView) rootView.findViewById(R.id.globalSongCardTitle);
            TextView songCardSong = (TextView) rootView.findViewById(R.id.globalMpSong);
            TextView artistCardTitle = (TextView) rootView.findViewById(R.id.globalArtistCardTitle);
            TextView artistCardArtist = (TextView) rootView.findViewById(R.id.globalMpArtist);
            TextView albumCardTitle = (TextView) rootView.findViewById(R.id.globalAlbumCardTitle);
            TextView albumCardAlbum = (TextView) rootView.findViewById(R.id.globalMpAlbum);

            songCardTitle.setText("Global Most Played Song");
            artistCardTitle.setText("Global Most Played Artist");
            albumCardTitle.setText("Global Most Played Album");
            songCardSong.setText(map.get("title"));
            artistCardArtist.setText(map.get("artist"));
            albumCardAlbum.setText(map.get("album"));

        }
        catch (JSONException e){
            // Too late, don't care
        }
    }
}
