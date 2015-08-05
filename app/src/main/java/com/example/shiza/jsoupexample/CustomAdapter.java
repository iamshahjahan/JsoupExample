package com.example.shiza.jsoupexample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shiza on 05-08-2015.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> heading;
    ArrayList<String> content;
    ArrayList<String> author;
    ArrayList<String> published;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context activity,ArrayList<String> heading,ArrayList<String> content,
                         ArrayList<String> author,ArrayList<String> published)
    {
        context = activity;
        this.heading = heading;
        this.content = content;
        this.author = author;
        this.published = published;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return heading.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView heading;
        TextView content;
        TextView author;
        TextView published;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView;
        rowView = inflater.inflate(R.layout.single_row, null);

        Holder holder = new Holder();


        holder.heading = (TextView)rowView.findViewById(R.id.heading);
        holder.content = (TextView)rowView.findViewById(R.id.content);
        holder.author = (TextView)rowView.findViewById(R.id.author);
        holder.published = (TextView)rowView.findViewById(R.id.published);


        holder.heading.setText(heading.get(position));
        holder.content.setText(content.get(position));
        holder.author.setText(author.get(position));
        holder.published.setText(published.get(position));



        return rowView;
    }
}
