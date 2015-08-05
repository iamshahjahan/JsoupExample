package com.example.shiza.jsoupexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


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
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTitle = (Button) findViewById(R.id.buttonTitle);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        listView = (ListView)findViewById(R.id.listView);


//        for (page = 1; page < 10; page++) {
//
//            Log.d("Page no: flag: ", page + " " + flag);
//            if ( page > 1 )
                url = "https://muslimmemo.com/";
            new MyTask(getApplicationContext()).execute(url);
//        }

    }
    private class Wrapper
    {
        ArrayList<String> heading = new ArrayList<>();
        ArrayList<String> headingLinks = new ArrayList<>();
        ArrayList<String> headingSummary = new ArrayList<>();
        ArrayList<String> author = new ArrayList<>();
        ArrayList<String> authorLinks = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> categoryLinks = new ArrayList<>();
        ArrayList<String> published = new ArrayList<>();
    }



    private class MyTask extends AsyncTask<String, Void, Boolean> {

        String url;

        Context mContext;

        public MyTask(Context context)
        {
            mContext = context;
        }

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
                new Title(mContext).execute(url);
            } else {
                flag = true;
                Log.d("result", "flag is true here.");
            }
        }
    }


    private class Title extends AsyncTask<String, Void, Wrapper> {

        Context mContext;

        public Title(Context context)
        {
            mContext = context;
        }
        protected void onPreExecute() {
            super.onPreExecute();

            linlaHeaderProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected Wrapper doInBackground(String... params) {
            Wrapper w = new Wrapper();
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


                for ( Element headings : heading)
                {
                    w.heading.add(headings.text());
                }
                for ( Element headingLinks : headingLink)
                {
                    w.headingLinks.add(headingLinks.text());
                }

                for ( Element headingSummarys : headingSummary)
                {
                    w.headingSummary.add(headingSummarys.text());
                }

                for ( Element authors : author)
                {
                    w.author.add(authors.text());
                }
                for ( Element publisheds : published)
                {
                    w.published.add(publisheds.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return w;
        }

        protected void onPostExecute(Wrapper result) {

            TextView textTitle = (TextView) findViewById(R.id.textTitle);
            textTitle.setText("in post execute.");
            listView.setAdapter(new CustomAdapter(mContext,result.heading,result.headingSummary,
                    result.author,result.published));

            Log.d("listView","the size is " + result.headingSummary.size());
            linlaHeaderProgress.setVisibility(View.GONE);
        }
    }

}


