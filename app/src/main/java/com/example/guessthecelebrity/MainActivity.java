package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    private String correctAnswer;

    private ArrayList<String> celebrityNames = new ArrayList<>();

    private ArrayList<String> celebrityURLs = new ArrayList<>();

    int chosenCeleb = 0;
    String[] answers = new String[4];
    int locationOfCorrectAnswer = 0;
    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.celebrityPicture);
        button0 = findViewById(R.id.celebrityName0);
        button1 = findViewById(R.id.celebrityName1);
        button2 = findViewById(R.id.celebrityName2);
        button3 = findViewById(R.id.celebrityName3);

        DownloadWebsiteContent task = new DownloadWebsiteContent();
        String result = null;



//        startGame();

        try {

            task = new DownloadWebsiteContent();
            result = task.execute("http://www.posh24.se/kandisar").get();

            String[] splitResult = result.split("<div class=\"listedArticles\">");

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebrityURLs.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebrityNames.add(m.group(1));
            }

            startGame();



        } catch (Exception e) {

        }


//        TextView text = findViewById(R.id.textView);


//startGame();
//        Iterator it = h.entrySet().iterator();

//        String s = "";
//
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            s += "Key : " + pair.getKey().toString();
//            s += " Value : " + pair.getValue().toString();
//        }
//        text.setText(s);
//        Log.i("Result ",h.get("Sophie Turner"));

    }

    private void startGame () {

        try {
            Random rand = new Random();

            chosenCeleb = rand.nextInt(celebrityURLs.size());

            ImageDownloader imageTask = new ImageDownloader();

            Bitmap celebImage = imageTask.execute(celebrityURLs.get(chosenCeleb)).get();

            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer = rand.nextInt(4);

            correctAnswer = celebrityNames.get(chosenCeleb);

            int incorrectAnswerLocation;

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers[i] = celebrityNames.get(chosenCeleb);
                } else {
                    incorrectAnswerLocation = rand.nextInt(celebrityURLs.size());

                    while (incorrectAnswerLocation == chosenCeleb) {
                        incorrectAnswerLocation = rand.nextInt(celebrityURLs.size());
                    }

                    answers[i] = celebrityNames.get(incorrectAnswerLocation);
                }
            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }



//        ImageView imageView = findViewById(R.id.celebrityPicture);
//
//        StringManipulation manipulation = new StringManipulation();
//
//        manipulation.getCelebrityNameAndURL();
//
//        ArrayList<String> celebrityNames = manipulation.getCelebrityNames();
//
//        ArrayList<String> celebrityURLs = manipulation.getCelebrityURLs();
//
//        Random random = new Random();
//
//        int celebrityIndex = random.nextInt(celebrityURLs.size());
//
//        String value = "";
//
//        value += celebrityURLs.get(celebrityIndex);
//
//        ImageDownloader downlaodImage = new ImageDownloader();
//
//        correctAnswer = celebrityNames.get(celebrityIndex);
//
//        try {
//            AsyncTask<String, Void, Bitmap> task =  downlaodImage.execute(value);
//            Bitmap bitmap = task.get();
//            imageView.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        int randomPosition = random.nextInt(4);
//
//        Button btn = (Button) findViewById(getResources().getIdentifier("celebrityName" + randomPosition  , "id",this.getPackageName()));
//
//        btn.setText(celebrityNames.get(celebrityIndex));
//
//        for (int i = 0 ; i < 4 ; i++) {
//
//            Button btnWrong = (Button) findViewById(getResources().getIdentifier("celebrityName" + i, "id",this.getPackageName()));
//            int rand = random.nextInt(celebrityNames.size());
//
//            if (btnWrong.getText().toString().isEmpty()){
//
//                btnWrong.setText(celebrityNames.get(rand));
//
//            }
//
//
//        }




    }


    public void checkAnswer(View view) {

        Button btn = findViewById(view.getId());

        if (btn.getText().equals(correctAnswer)) {
            Toast.makeText(this, "Correct" + correctAnswer, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }

        startGame();


    }
}
