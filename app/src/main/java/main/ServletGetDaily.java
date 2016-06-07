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
 * Servlet post async task to get Daily stats
 */

class ServletGetDaily extends AsyncTask<Context, Void, String> {
    private Context context;
    private View rootView;

    public ServletGetDaily(View rv){
        rootView = rv;
    }

    @Override
    protected String doInBackground(Context... params) {
        context = params[0];

        try {
            // GET request
            URL url = new URL("https://music-discovery-1316.appspot.com/getdaily");
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

            TextView dailyTitle = (TextView) rootView.findViewById(R.id.dailyTitle);
            TextView dailyDate = (TextView) rootView.findViewById(R.id.dailyDate);
            TextView dailySong = (TextView) rootView.findViewById(R.id.dailySong);
            TextView dailyArtist = (TextView) rootView.findViewById(R.id.dailyArtist);

            dailyTitle.setText("The most played song for");
            dailyDate.setText(map.get("date") + " is ");
            dailyArtist.setText("By " + map.get("artist"));
            dailySong.setText(map.get("title"));

        }
        catch (JSONException e){
            // Too late, don't care
        }
    }
}
