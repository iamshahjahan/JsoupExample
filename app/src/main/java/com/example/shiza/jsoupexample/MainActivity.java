package com.example.shiza.jsoupexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    String url = "https://muslimmemo.com/";
    Button buttonTitle;
    Elements heading;
    Elements headingLink;
    Elements headingSummary;
    Elements author;
    Elements authorLinks;
    Elements category;
    Elements categoryLinks;
    Elements published;
    static int page = 1;
    LinearLayout linlaHeaderProgress;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTitle = (Button) findViewById(R.id.buttonTitle);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);


        for (page = 1; page < 100; page++) {

            Log.d("Page no: flag: ", page + " " + flag);
            if ( page > 1 )
                url = "https://muslimmemo.com/page/" + page + "/";
            new MyTask().execute(url);
        }

    }

    private class MyTask extends AsyncTask<String, Void, Boolean> {

        String url;

        @Override
        protected void onPreExecute() {
//            Log.d("result", "I am in pre execute");
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) new URL(params[0]).openConnection();
                con.setRequestMethod("HEAD");
                System.out.println(con.getResponseCode());
                url = params[0];
                return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            boolean bResponse = result;
            if (bResponse) {
//                Log.d("result", "File exists");
                new Title().execute(url);
            } else {
                flag = true;
                Log.d("result", "flag is true here.");
            }
        }
    }

    private class Title extends AsyncTask<String, Void, Void> {




        protected void onPreExecute() {
            super.onPreExecute();

            linlaHeaderProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                Document document = Jsoup.connect(params[0]).get();

                heading = document.getElementsByClass("entry-title");
                headingLink = document.select("h1.entry-title > a[href]");
                headingSummary = document.getElementsByClass("entry-summary");
                author = document.getElementsByClass("author");
                authorLinks = document.select("span.author > a[href]");
                category = document.getElementsByClass("cat-links");
                categoryLinks = document.select("span.cat-links > a[href]");
                published = document.getElementsByClass("published");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            TextView textTitle = (TextView) findViewById(R.id.textTitle);
            textTitle.setText("in post execute.");

            linlaHeaderProgress.setVisibility(View.GONE);
        }
    }

}


