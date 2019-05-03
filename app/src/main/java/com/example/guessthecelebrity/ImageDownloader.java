package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.connect();

            InputStream in = httpURLConnection.getInputStream();

            Bitmap myBitMap = BitmapFactory.decodeStream(in);

            return myBitMap;


        } catch (Exception e) {
            return  null;
        }

    }
}
