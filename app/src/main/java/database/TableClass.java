package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.ArrayList;

import static com.example.mrityunjay.project1.R.id.listView;

/**
 * Created by Mrityunjay on 21-05-2017.
 */

public class TableClass extends SQLiteOpenHelper {

    public class Constant {

        public static final String DATABASE_NAME = "todo";

        public static final int DATABASE_VERSION = 1;
        public final static String KEY_ID = "key_id";
        public final static String KEY_TITLE = "title";
        public final static String KEY_DESCRIPTION = "description";
        public final static String KEY_DATE = "date";
        public final static String KEY_STATUS = "key_status";

    }

    Context context;

    public TableClass(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " + Constant.DATABASE_NAME + " ("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constant.KEY_TITLE + " TEXT, " + Constant.KEY_DATE + " TEXT, "
                + Constant.KEY_DESCRIPTION + " TEXT, " + Constant.KEY_STATUS + " INTEGER )";

        db.execSQL(table1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.deleteDatabase(Constant.DATABASE_NAME);
        onCreate(db);

    }



    public void insert(String title, String description, String date, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_TITLE, title);
        contentValues.put(Constant.KEY_DESCRIPTION, description);
        contentValues.put(Constant.KEY_DATE, date);
        contentValues.put(Constant.KEY_STATUS, status);
        db.insert(Constant.DATABASE_NAME, null, contentValues);
    }


    public Cursor fetch() {
        Log.d("data","fetch");
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = new String[]{Constant.KEY_ID, Constant.KEY_TITLE, Constant.KEY_DESCRIPTION, Constant.KEY_DATE, Constant.KEY_STATUS};
        Cursor cursor = db.query(Constant.DATABASE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public int update(Integer _id, String title, String description, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_ID, title);
        contentValues.put(Constant.KEY_DESCRIPTION, description);
        contentValues.put(Constant.KEY_DATE, date);
        contentValues.put(Constant.KEY_STATUS, status);
        // contentValues.put(MyDatabaseHelper.COLUMN_STATUS, status);

        int i = db.update(Constant.DATABASE_NAME, contentValues, Constant.KEY_DESCRIPTION + " = " + _id, null);
        return i;
    }


    public void delete(long _id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.DATABASE_NAME, Constant.KEY_ID + " = " + _id, null);
    }


    }




