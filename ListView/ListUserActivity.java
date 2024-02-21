package isi.sn.webservicesjsonbest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import entities.User;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tools.MyAdapterUser;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<User> users = new ArrayList<User>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        progressDialog=new ProgressDialog(ListUserActivity.this);
        progressDialog.setMessage("Veuillez patienter SVP ...");

        list = (ListView) findViewById(R.id.listUser);

        String url="http://192.168.43.196/mesprojets/android/gesSchool/listeuserjson.php";
        ListUserActivity.ClientServer clientServer=new ListUserActivity.ClientServer();
        clientServer.execute(url);
    }
    protected  class ClientServer extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            try {

                HttpClient client =new DefaultHttpClient();
                HttpGet get=new HttpGet( params[0] ) ;
                ResponseHandler<String> tunnel=new BasicResponseHandler();
                String result= client.execute(get, tunnel);
                return result;

            } catch (Exception e)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            if(result==null){
                Toast.makeText(ListUserActivity.this, "Impossible de joindre le serveur", Toast.LENGTH_LONG).show() ;
            }
            else {
                try{
                    ArrayList<User> liste_users = new ArrayList<User>();
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0; i<jArray.length(); i++) {
                        User user = new User();
                        user.setId(jArray.getJSONObject(i).getInt("id"));
                        user.setNom(jArray.getJSONObject(i).getString("nom"));
                        user.setPrenom(jArray.getJSONObject(i).getString("prenom"));
                        user.setEmail(jArray.getJSONObject(i).getString("email"));
                        user.setPassword(jArray.getJSONObject(i).getString("password"));

                        liste_users.add(user);
                    }
                    chargerListe(liste_users);

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
        private void chargerListe(ArrayList<User> liste_users){

            users = liste_users;
            MyAdapterUser adpt = new MyAdapterUser(ListUserActivity.this,users);

            list.setAdapter(adpt);
        }
    }
}
