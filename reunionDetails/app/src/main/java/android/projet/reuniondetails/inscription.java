package android.projet.reuniondetails;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by user on 16/11/2017.
 */

public class inscription extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
    }
    public void onButtonClick(View v) {
        DatabaseHelper helper = new DatabaseHelper(this);
        EditText nom = (EditText) findViewById(R.id.editText1);
        String str = nom.getText().toString();
        EditText motp = (EditText) findViewById(R.id.editText2);
        String pass = motp.getText().toString();
        EditText email = (EditText) findViewById(R.id.editText3);
        String mail = email.getText().toString();
        helper.insertUser(str,pass);
        new inscription.SendPostRequest().execute("http://benhajyahianihel1c.000webhostapp.com/insert.php", str, pass, mail);

    }
        public class SendPostRequest extends AsyncTask<String, String, String> {


            @Override
            protected String doInBackground(String...params) {


                BufferedReader reader = null;

                try {


                    URL url = new URL(params[0]);

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("name", params[1]);
                    postDataParams.put("passe", params[2]);
                    postDataParams.put("email", params[3]);
                    Log.e("params", postDataParams.toString());

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
            }


            @Override
            protected void onPostExecute(String s){

                super.onPostExecute(s);

                     Intent i = new Intent(inscription.this, detail.class);
                     i.putExtra("username", s);
                     startActivity(i);

                //helper.setUser(s);
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
