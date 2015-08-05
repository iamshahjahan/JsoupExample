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



public class MainActivity extends AppCompatActivity implements AsyncResponse {

    String url = "https://muslimmemo.com";
    Button buttonTitle;
    Elements heading;
    Elements headingLink;
    Elements headingSummary;
    Elements author;
    Elements authorLinks;
    Elements category;
    Elements categoryLinks;
    Elements published;
    Elements next;
    static int page = 1;
    LinearLayout linlaHeaderProgress;
    boolean flag = false;
    ListView listView;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Wrapper wrapper_final = new Wrapper();
        buttonTitle = (Button) findViewById(R.id.buttonTitle);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        listView = (ListView)findViewById(R.id.listView);



            Title title = new Title(getApplicationContext());
            title.delegate = this;
            title.execute(url);

    }


    @Override
    public void processFinish(Wrapper output)
    {




        if ( output.nextUrl != null)
        {
            if ( output.nextUrl.contains("page/2"))
            {
                customAdapter = new CustomAdapter(this, output.heading, output.headingSummary,
                        output.author, output.published);
                listView.setAdapter(customAdapter);
            }
            else
            {
                customAdapter.addItem(output.heading, output.headingSummary,
                        output.author, output.published);
            }
            Title title = new Title(getApplicationContext());
            title.delegate = this;
            title.execute(output.nextUrl);
        }
        else
        {
            customAdapter.addItem(output.heading, output.headingSummary,
                    output.author, output.published);
            Log.d("tag","is null");
        }
    }


//    Implement an interface



    public class Wrapper
    {
        ArrayList<String> heading = new ArrayList<>();
        ArrayList<String> headingLinks = new ArrayList<>();
        ArrayList<String> headingSummary = new ArrayList<>();
        ArrayList<String> author = new ArrayList<>();
        ArrayList<String> authorLinks = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> categoryLinks = new ArrayList<>();
        ArrayList<String> published = new ArrayList<>();
        String nextUrl ;
    }

    private class Title extends AsyncTask<String, Void, Wrapper> {
        Wrapper w = new Wrapper();
        Context mContext;
        public AsyncResponse delegate=null;

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
                next = document.select("a.next");


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

                if ( !next.isEmpty() )
                {
                    w.nextUrl = next.attr("href");
                }
                else
                {
                    w.nextUrl = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return w;
        }

        protected void onPostExecute(Wrapper result) {

            delegate.processFinish(result);
            linlaHeaderProgress.setVisibility(View.GONE);
        }
    }

}


