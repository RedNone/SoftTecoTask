package com.example.rednone.softteco;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by RedNone on 09.04.2017.
 */

public interface getJs {
    @GET("/posts")
    Call<List<DataModel>> getData();

    @GET("/users/{id}")
    Call<UsersData> getUserData(@Path("id") int id);


}
