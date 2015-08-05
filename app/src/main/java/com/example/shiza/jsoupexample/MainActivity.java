package com.example.shiza.jsoupexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    String url = "https://muslimmemo.com/page/2";
    Button buttonTitle;
    Elements heading;
    Elements headingLink;
    Elements headingSummary;
    Elements author;
    Elements authorLinks;
    Elements category;
    Elements categoryLinks;
    Elements published;

    LinearLayout linlaHeaderProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTitle = (Button)findViewById(R.id.buttonTitle);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);

        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Title title = new Title(getApplicationContext());
               title.execute();
            }
        });


    }

    private class Title extends AsyncTask<Void,Void,Void>
    {

        String title;

        private Context mContext;

        public Title(Context context) {
            mContext = context;

        }

        protected void onPreExecute()
        {
            super.onPreExecute();

            linlaHeaderProgress.setVisibility(View.VISIBLE);
//            progressDialog = new ProgressDialog(MainActivity.this);
//
//            progressDialog.setTitle("Fetching from site.");
//            progressDialog.setMessage("Loading...");
//
//            progressDialog.setIndeterminate(false);
//
//            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                /*
* What I need to fetch from the site:
* 1. heading. -> document.getElementByClassName("entry-title");
* 2. heading-link -> document.select("span.entry-title > a[href]")
* 3. summary of the heading. -> document.getElementByClassName("entry-summary");
* 4. author's link. -> document.select("span.author > a[href]")
* 5. author's name. ->  document.getElementByClassName("author");
* 6. category. -> document.getElementByClassName("cat-links");
* 7. category's link. -> document.select("span.cat-links > a[href]")
* 8. image from the post.
*
* */
                Document document = Jsoup.connect(url).get();

                heading = document.getElementsByClass("entry-title");
                headingLink = document.select("h1.entry-title > a[href]");
                headingSummary = document.getElementsByClass("entry-summary");
                author = document.getElementsByClass("author");
                authorLinks = document.select("span.author > a[href]");
                category = document.getElementsByClass("cat-links");
                categoryLinks = document.select("span.cat-links > a[href]");
                published = document.getElementsByClass("published");
//                link = document.select("a").first();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result)
        {
            TextView textTitle = ( TextView )findViewById(R.id.textTitle);
            textTitle.setText("in post execute.");
            for (Element headingdiff : heading )
            {
                Log.d("heading",headingdiff.text());
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            for (Element headingdiff : headingSummary )
            {
                Log.d("headingSummary",headingdiff.text());
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            for (Element headingdiff : headingLink )
            {
                Log.d("headingLink",headingdiff.attr("href"));
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            for (Element headingdiff : author )
            {
                Log.d("author",headingdiff.text());
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            for (Element headingdiff : authorLinks )
            {
                Log.d("authorLink",headingdiff.attr("href"));
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            for (Element headingdiff : category )
            {
                Log.d("category",headingdiff.text());
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            for (Element headingdiff : categoryLinks )
            {
                Log.d("categoryLink",headingdiff.attr("href"));
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }

            for (Element headingdiff : published )
            {
                Log.d("published",headingdiff.text());
                Toast.makeText(mContext,headingdiff.text(),Toast.LENGTH_LONG).show();
            }
            linlaHeaderProgress.setVisibility(View.GONE);
        }
    }

}


