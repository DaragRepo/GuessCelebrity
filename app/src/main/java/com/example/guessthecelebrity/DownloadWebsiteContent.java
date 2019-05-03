package com.example.guessthecelebrity;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebsiteContent extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... url) {
        String result = "";

        URL myURL;
        HttpURLConnection urlConnection = null;

        try {
            myURL = new URL(url[0]);
            urlConnection = (HttpURLConnection) myURL.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();

            while (data != -1) {
                char current = (char) data;

                result += current;

                data = inputStreamReader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return  "Failed";
        }
    }

}
