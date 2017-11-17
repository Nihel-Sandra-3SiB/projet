package android.projet.reuniondetails;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
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
   // DataBaseHelp help = new DataBaseHelp(this);
    private TextView txt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txt = (TextView) findViewById(R.id.text2);
        boolean test = checkNetworkConnection();

        if(test)
        {
            //récupération des données depuis le serveur web s'il y a connexion

           // new detail.SendPostRequest().execute("http://192.168.8.100:80/test/detail.php");
            txt.setText("vrai");

        }
        else
        {
            //récupération des données depuis sqlite s'il y a pas connexion
            //String det="";
            //det=help.getDetail();
            //txt.setText(det);
            txt.setText("faux");

        }

    }

    public void onButton3Click(View v) {
        Intent i = new Intent(detail.this, MainActivity.class);

        startActivity(i);
    }
    public void onButton1Click(View v) {
        Intent i = new Intent(detail.this, local.class);

        startActivity(i);
    }
    public void onButton2Click(View v) {
        Intent i = new Intent(detail.this, externe.class);

        startActivity(i);
    }
    public boolean checkNetworkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return(networkInfo.isConnectedOrConnecting());
    }



   /* public class SendPostRequest extends AsyncTask<String, String, String> {


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
                    finalBufferData.append(idu+"\t\t"+nom+"\t\t"+mdp+"\n");


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
            help.setDetail(s);
        }
    }
*/
}
