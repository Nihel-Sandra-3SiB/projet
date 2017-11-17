package android.projet.reuniondetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    //DatabaseHelper helper = new DatabaseHelper(this);
   // SQLiteDatabase db;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void onTextClick(View v) {
        Intent i = new Intent(MainActivity.this, inscription.class);
                i.putExtra("username", "hh");
                startActivity(i);

    }


    public void onButtonClick(View v)
    {
            EditText nom =(EditText)findViewById(R.id.editText1);
            String str = nom.getText().toString();
            EditText motp =(EditText)findViewById(R.id.editText2);
             String pass = motp.getText().toString();
       // TextView txt =(TextView)findViewById(R.id.txt1);

        new MainActivity.SendPostRequest().execute("http://benhajyahianihel1c.000webhostapp.com/verif.php",str,pass);

          // String password = helper.searchPass(str);
        //  if(pass.equals(password))
         //  {
               /* Intent i = new Intent(MainActivity.this, detail.class);
                i.putExtra("username", str);
                startActivity(i);*/
        /*   }
            else
            {
                Toast temp = Toast.makeText(MainActivity.this, "invalid mot de passe or username!", Toast.LENGTH_SHORT);
                temp.show();
            }
*/
    }

    public class SendPostRequest extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String...params) {





            try
            {



                URL url = new URL(params[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", params[1]);
                postDataParams.put("passe", params[2]);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();


                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
            //return null;
                /*
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("");
                StringBuffer finalBufferData = new StringBuffer();


                for(int i=0;i<parentArray.length();i++)
                {

                        JSONObject finalObject = parentArray.getJSONObject(i);
                        int idu = finalObject.getInt("id");
                        String nom = finalObject.getString("name");
                        String mdp = finalObject.getString("pass");
                        finalBufferData.append(idu+"\t\t"+nom+"\t\t"+mdp+"\n");
                        //helper.insertUser(idu,nom,mdp);

                    }
                     return finalBufferData.toString();*/






        }


        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);
           // Toast temp = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
           // temp.show();

            if(s.equals("yes"))
            {
                Intent i = new Intent(MainActivity.this, detail.class);
                i.putExtra("username", s);
                startActivity(i);
            }
            else {

                Toast temp = Toast.makeText(MainActivity.this, "invalid mot de passe or username!", Toast.LENGTH_SHORT);
                temp.show();

            }

        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}
