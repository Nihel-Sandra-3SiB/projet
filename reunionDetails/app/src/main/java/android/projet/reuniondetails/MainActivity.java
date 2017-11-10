package android.projet.reuniondetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

                public void showAlert(View v,String nouveau)
                {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                    if (nouveau == "true")
                    {
                        myAlert.setMessage("vous pouvez coonectez").setPositiveButton("cliquez ici pour continuer", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog , int wich){
                                dialog.dismiss();
                                }
                             })
                    .setTitle("Welcome!").create();
                    }
                    else if (nouveau == "false")
                    {
                        myAlert.setMessage("aucun nouveau utilisateur a été ajouté").setPositiveButton("cliquez ici pour continuer", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog , int wich){
                                dialog.dismiss();
                            }
                        })
                                .setTitle("désolé!").create();
                    }
                    myAlert.show();
                }

    public void onTextClick(View v) {
        //helper.onCreate(db);
        //helper.suppTable();
        new SendPostRequest().execute("http://192.168.8.100:80/test/users.php");
       // String nouveau = helper.setNewUser();
        showAlert(v,"true");

    }


    public void onButtonClick(View v)
    {
            EditText nom =(EditText)findViewById(R.id.editText1);
            String str = nom.getText().toString();
            EditText motp =(EditText)findViewById(R.id.editText1);
            String pass = motp.getText().toString();
          // String password = helper.searchPass(str);
        //  if(pass.equals(password))
         //  {
                Intent i = new Intent(MainActivity.this, detail.class);
                i.putExtra("username", str);
                startActivity(i);
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
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("");
                StringBuffer finalBufferData = new StringBuffer();


                for(int i=0;i<parentArray.length();i++)
                {

                        JSONObject finalObject = parentArray.getJSONObject(i);
                        int idu = finalObject.getInt("id");
                        String nom = finalObject.getString("name");
                        String mdp = finalObject.getString("pass");
                        finalBufferData.append(idu+","+nom+","+mdp+"*");
                        //helper.insertUser(idu,nom,mdp);

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

            StringTokenizer si = new StringTokenizer(s,"*");
            String sii="";
            while(si.hasMoreTokens()) {
                int i=0;
                sii = si.nextToken();
                ArrayList tab = new ArrayList ();
                StringTokenizer siii = new StringTokenizer(sii, "+");
                while(siii.hasMoreTokens())
                {

                    tab.add(i,siii.nextToken()) ;
                    i++;
                }
                helper.insertUser((int)tab.get(0),(String)tab.get(1),(String)tab.get(2));
            }
        }
    }
}
