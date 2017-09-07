package com.example.mrityunjay.project1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrityunjay.project1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.TableClass;

import static com.example.mrityunjay.project1.R.id.edit2;
import static com.example.mrityunjay.project1.R.id.listView;
import static com.example.mrityunjay.project1.R.id.text1;
import static java.nio.channels.ServerSocketChannel.open;

public class MainActivity extends AppCompatActivity {
    List<Todata> dataList;


   // FragmentManager fm = getSupportFragmentManager();


    TextView textView1;
    TextView textView2;
    TextView textView3;
    ImageView imageView;
    ListView listView;
    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        imageView = (ImageView) findViewById(R.id.imageView);*/
        listView = (ListView) findViewById(R.id.listView);
        // button1=(Button)findViewById(R.id.button1);
        //  button2=(Button)findViewById(R.id.button2);
        updateUI();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();
                final View inflaterView = inflater.inflate(R.layout.dialog_main, null);
                dialog.setView(inflaterView);


                final EditText title = (EditText) inflaterView.findViewById(R.id.edit1);
               // EditText et = (EditText) findViewById(YOUR_EDITTEXT);
                String s = title.getText().toString();
                if(s.length()  != 0){
                    title.setError("Must be some  characters!");
                } else {

                    // ...
                }
                final EditText description = (EditText) inflaterView.findViewById(R.id.edit2);
                final DatePicker date = (DatePicker) inflaterView.findViewById(R.id.date);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("ToDoListHomeActivity: ", "Task to add: " + title.getText().toString());
                        Log.d("ToDoListHomeActivity: ", "Description to add: " + description.getText().toString());
                        Log.d("ToDoListHomeActivity: ", "Date: " + date.getYear() + "-" + date.getMonth() + 1 + "-" + date.getDayOfMonth());


                        final TableClass tableClass = new TableClass(getApplicationContext());
                        tableClass.insert(title.getText().toString(), description.getText().toString(), date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth(), "1");

                        tableClass.close();

                        updateUI();

                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.create();
                dialog.show();
                return true;
            case R.id.incomlete:
               // Toast.makeText(this, "Liked", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateUI() {
        TableClass db = new TableClass(getApplicationContext());

        Log.d("Called: ", "update");
        Cursor cursor = db.fetch();

        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> date = new ArrayList<String>();

        while (cursor.moveToNext()) {
            //Log.d("title: ", cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_TITLE)));
            title.add(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_DATE)));
        }
       ListViewAdapter listViewAdapter = new ListViewAdapter(this, title, description, date);
        listView.setAdapter(listViewAdapter);
        cursor.close();
        db.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void deleteTask(View view) {
        TableClass db = new TableClass(getApplicationContext());
        Cursor cursor = db.fetch();
        String title = cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_DESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_DATE));
        String status = cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_STATUS));

        if (Objects.equals(cursor.getString(cursor.getColumnIndex(TableClass.Constant.KEY_STATUS)), "1")) {
            view.findViewById(R.id.like).setBackgroundResource(R.drawable.complete);
            db.delete(cursor.getInt(cursor.getColumnIndex(TableClass.Constant.KEY_ID)));
            Log.d("MainActivity", "deleteTask: " + status);
        } else {
            db.update(cursor.getInt(cursor.getColumnIndex(TableClass.Constant.KEY_ID)), title, description, date, "1" );
            view.findViewById(R.id.like).setBackgroundResource(R.drawable.incomplete);
        }
        updateUI();
        cursor.close();
        db.close();
    }
}