package com.esmt.httpclientspring.config;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    //https://<DOMAIN>/api/users
    @GET("/api/users")
    Call<List<Result>> allUsers();

    //https://<DOMAIN>/api/user?userId=1
    @GET("/api/user")
    Call<ResponseBody> getUsers(@Query("id") int id);

    //https://<DOMAIN>/api/login?email=xxx&password=xxx
    @GET("/api/login")
    Call<Result> login(@Query("email") String email, @Query("password") String password);

    @GET("/api/user/{id}")
    Call<ResponseBody> delete(@Path("id") int id);

    //NB
    /*Avec ResponseBody, on obtient ce resultat:
    D/Response: Response{protocol=http/1.1, code=200, message=, url=http://192.168.90.167:8081/api/login?email=seck%40seck.sn&password=passer}
    Log.d("Response :", response.toString());*/

    /*@POST("/api/user")  
    Call<Result> createUser(@Body RequestBody user);*/
}
