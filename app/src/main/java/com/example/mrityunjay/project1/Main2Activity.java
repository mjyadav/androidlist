package com.example.mrityunjay.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import database.TableClass;


public class Main2Activity extends AppCompatActivity {
    ListViewAdapter listViewAdapter;
    ListView listView;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Completed Tasks");
//        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        listView = (ListView) findViewById(R.id.list22);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {


                final AlertDialog.Builder dialog = new AlertDialog.Builder(Main2Activity.this);

                dialog.setMessage("Are you sure? You want to delete this task?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        TableClass db = new TableClass(getApplicationContext());

                        String ids = ((TextView) view.findViewById(R.id.text1)).getText().toString();
                        db.delete(Long.parseLong(ids));
                        Toast.makeText(Main2Activity.this, "Task is deleted.", Toast.LENGTH_LONG).
                                show();
                        updateUI();
                        db.close();
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.create();
                dialog.show();
                return true;
            }

        });
        updateUI();
    }

    private void updateUI() {
        TableClass db = new TableClass(getApplicationContext());
        Cursor cursor = db.fetch();
        final ArrayList<Integer> id = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> status = new ArrayList<>();

        while (cursor.moveToNext()) {
            id.add(cursor.getInt(cursor.getColumnIndex(TableClass.Constant.KEY_ID)));
            title.add(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_DATE)));
            status.add(cursor.getInt(cursor.getColumnIndex(TableClass.Constant.KEY_STATUS)));
        }
        listViewAdapter = new ListViewAdapter(this, title, description,date);
        listView.setAdapter(listViewAdapter);
        cursor.close();
        db.close();
    }
}


