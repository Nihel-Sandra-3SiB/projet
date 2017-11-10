package android.projet.reuniondetails;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import static android.support.v7.appcompat.R.styleable.View;

/**
 * Created by user on 08/11/2017.
 */

public class detail extends AppCompatActivity {

    private TextView txt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txt = (TextView) findViewById(R.id.text2);

           new SendPostRequest2().execute("http://192.168.8.100:80/test/detail.php");

    }



    public class SendPostRequest2 extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {


            HttpURLConnection conn;
            BufferedReader reader = null;

            try
            {
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line="";
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONArray parentArray = new JSONArray(finalJson);
               // JSONArray parentArray = parentObject.getJSONArray("");
                StringBuffer finalBufferData = new StringBuffer();


                for(int i=0;i<parentArray.length();i++)
                {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    int idu = finalObject.getInt("id");
                    String nom = finalObject.getString("date");
                    String mdp = finalObject.getString("salle");
                    finalBufferData.append(idu+" , "+nom+" , "+mdp);


                }
                return finalBufferData.toString();







                }
            catch(Exception e)
            {
                return new String("Exception: " + e.getMessage());
            }

        }


        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);
            txt.setText(s);
        }
    }

}