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
import java.util.ArrayList;
import java.util.StringTokenizer;

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
    public void setDetail(String s) {
        StringTokenizer si = new StringTokenizer(s,"\n");
        String sii="";
        while(si.hasMoreTokens()) {
            int i=0;
            sii = si.nextToken();
            ArrayList tab = new ArrayList ();
            StringTokenizer siii = new StringTokenizer(sii, "\t\t");
            while(siii.hasMoreTokens())
            {

                tab.add(i,siii.nextToken()) ;
                i++;
            }
            this.insertDetail((int)tab.get(0),(String)tab.get(1),(String)tab.get(2));
        }

    }





    public void insertDetail(int idR,String dat, String sal) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, idR);
        values.put(COLUMN_DATE, dat);
        values.put(COLUMN_SALLE, sal);
        db.insert(TABLE_NAME, null, values);
    }

    public String getDetail(){

        StringBuffer buffer = new StringBuffer();
        db=this.getReadableDatabase();
        String query ="select date, salle from"+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a, b;

        if(cursor.moveToFirst())
        {
            do {
                a=cursor.getString(0);
                b=cursor.getString(1);
                buffer.append(a);
                buffer.append("\t\t");
                buffer.append(b);
            }
            while(cursor.moveToNext());
        }

        return buffer.toString();
    }




}
