package com.esmt.httpclientspring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esmt.httpclientspring.config.Api;
import com.esmt.httpclientspring.config.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button testapibt;
    private Button testapiusersbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testapibt = (Button) findViewById(R.id.testbt);
        testapiusersbt = (Button) findViewById(R.id.testusersbt);

        testapibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.90.167:8081")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);

                Call<Result> call = api.login("seck@seck.sn", "passer");

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Log.d("Response :", response.body().toString());
                            String message = response.body().getMessage();
                            String email = response.body().getEmail();
                            Toast.makeText(MainActivity.this, message + "  " + email, Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("error message exception", response.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d("Error : ", t.getMessage());
                        //D/Error :: CLEARTEXT communication to 192.168.90.167 not permitted by network security policy
                        Toast.makeText(MainActivity.this, "Impossible d'acceder au serveur !", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        testapiusersbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.90.167:8081")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);

                Call<List<Result>> call = api.allUsers();

                call.enqueue(new Callback<List<Result>>() {
                    @Override
                    public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                        if (response.isSuccessful()) {
                            Log.d("Response :", response.body().size()+"");
                            //recuperation du premier user de la liste
                            String message = response.body().get(0).getMessage();
                            String email = response.body().get(0).getEmail();
                            Toast.makeText(MainActivity.this, message + "  " + email, Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("error message exception", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Result>> call, Throwable t) {
                        Log.d("Error : ", t.getMessage());
                        //D/Error:: CLEARTEXT communication to 192.168.90.167 not permitted by network security policy
                        Toast.makeText(MainActivity.this, "Impossible d'acceder au serveur !", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
    }
}