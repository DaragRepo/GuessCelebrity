package com.example.guessthecelebrity;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringManipulation {


    private ArrayList<String> celebrityNames = new ArrayList<>();

    private ArrayList<String> celebrityURLs = new ArrayList<>();

    private String result = "";

    private void downloadWebsiteContent () {

        DownloadWebsiteContent downloadWebsiteContent = new DownloadWebsiteContent();

        try {
            AsyncTask<String, Void, String> task =downloadWebsiteContent.execute("http://www.posh24.se/kandisar");
            result = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCelebrityNameAndURL() {

        downloadWebsiteContent();

        String[] splitResult = result.split("<div class=\"listedArticles\">");

        Pattern p = Pattern.compile("img src=\"(.*?)\"");
        Matcher m = p.matcher(splitResult[0]);

        while (m.find()) {
            celebrityURLs.add(m.group(1));
        }

        p = Pattern.compile("alt=\"(.*?)\"");
        m = p.matcher(splitResult[0]);

        while (m.find()) {
            celebrityNames.add(m.group(1));
        }


//        Pattern p = Pattern.compile("img src=\"(.*?)\"");
//
//        Matcher m = p.matcher(result);
//
//        Pattern p2 = Pattern.compile("alt=\"(.*?)\"");
//
//        Matcher m2 = p2.matcher(result);
//
//        while (m.find() && m2.find()) {
//            celebrityNames.add(m2.group(1));
//            celebrityURLs.add(m.group(1));
//        }


    }

    public ArrayList<String> getCelebrityNames() {
        return celebrityNames;
    }

    public ArrayList<String> getCelebrityURLs() {
        return celebrityURLs;
    }
}
