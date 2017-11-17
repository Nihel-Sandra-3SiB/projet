package android.projet.reuniondetails;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by user on 08/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reunion.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASS = "pass";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table users (id integer AUTO_INCREMENT PRIMARY KEY , " +
            "name text not null , pass text not null); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
    public void insertUser(String nom, String mdp) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, nom);
        values.put(COLUMN_PASS, mdp);
        db.insert(TABLE_NAME, null, values);
    }

   /* public String searchPass(String uname) {
        db = this.getReadableDatabase();
        String query = "select name, pass from" + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;

        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(uname)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;

    }
    public void setUser(String s) {
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
            this.insertUser((int)tab.get(0),(String)tab.get(1),(String)tab.get(2));
        }

    }


    public void suppTable() {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
    }

    public int getTotalRows() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        return count;
    }

    public String setNewUser() {

        String nouvea = "false";
        // int count = this.getTotalRows();
        // suppTable();

        // int count2 = this.getTotalRows();
        // if(count<count2) {
        nouvea = "true";
        //  }

        return nouvea;
    }


   */

}
