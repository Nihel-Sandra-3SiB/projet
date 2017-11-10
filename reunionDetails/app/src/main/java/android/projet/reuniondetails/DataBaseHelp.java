package android.projet.reuniondetails;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static android.R.attr.name;

/**
 * Created by user on 08/11/2017.
 */

public class DataBaseHelp extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME= "reunion.db";
    private static final String TABLE_NAME= "details";
    private static final String COLUMN_ID= "id";
    private static final String COLUMN_DATE= "date";
    private static final String COLUMN_SALLE= "salle";
    SQLiteDatabase db;
    private static final String TABLE_CREATE ="create table users (id integer , "+
            "date text not null , salle text not null); ";

    public DataBaseHelp(Context context){
        super (context , DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_CREATE);
        this.db =db;

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }




    public void insertDetail(int idR,String dat, String sal) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, idR);
        values.put(COLUMN_DATE, dat);
        values.put(COLUMN_SALLE, sal);
        db.insert(TABLE_NAME, null, values);
    }





}
