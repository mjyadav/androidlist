package com.example.mrityunjay.project1;

/**
 * Created by Mrityunjay on 10-07-2017.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;

class ListViewAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<String> date;
    private ArrayList<String> title;
    private ArrayList<String> description;

    ListViewAdapter(Activity context, ArrayList<String> title, ArrayList<String> description, ArrayList<String> date) {
        super();
        this.context = context;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private class ViewHolder {
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textDAte;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_layout, null);
            holder = new ViewHolder();
            holder.textViewDate = (TextView) view.findViewById(R.id.text1);
            holder.textViewTitle = (TextView) view.findViewById(R.id.text2);
            holder.textViewDescription = (TextView) view.findViewById(R.id.text3);
            holder.textDAte = (TextView) view.findViewById(R.id.timestamp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewDate.setText(date.get(position));
        holder.textViewTitle.setText(title.get(position));
        holder.textViewDescription.setText(description.get(position));
        holder.textDAte.setText(date.get(position));
        return view;
    }
}
