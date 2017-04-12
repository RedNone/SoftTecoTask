package com.example.rednone.softteco;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    private getJs intfObj;
    private Retrofit retrofit;
    private String TAG = "MainActivity";

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private List<DataModel> postsList;
    private UsersData usersData;

    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        setFragment(MainFragment.TAG);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        intfObj = retrofit.create(getJs.class);

        downloadData(MainFragment.TAG, 0);
    }

    private void setFragment(String TAG) {

        transaction = manager.beginTransaction();

        switch (TAG) {
            case "MainFragment":
                if (manager.findFragmentByTag(MainFragment.TAG) == null) {
                    transaction.add(R.id.mainConteiner, new MainFragment(), MainFragment.TAG);
                }
                break;
            case "UserFragment":

                if (manager.findFragmentByTag(UserFragment.TAG) == null) {
                    transaction.replace(R.id.mainConteiner, UserFragment.getNewInstance(postId, usersData), UserFragment.TAG);
                    transaction.addToBackStack(null);
                }
                break;
            default:
                Log.d(this.TAG, "Incorrect Fragment");
                break;
        }
        transaction.commit();
    }

    public List<DataModel> getPostsList() {
        return postsList;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void downloadData(String tag, int id) {
        switch (tag) {
            case "MainFragment":

                intfObj.getData().enqueue(new Callback<List<DataModel>>() {
                    @Override
                    public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                        if (response.isSuccessful()) {
                            MainFragment obj = (MainFragment) manager.findFragmentByTag(MainFragment.TAG);
                            postsList = response.body();
                            obj.getData(postsList);
                        } else {
                            Log.d(TAG, "Response: " + response.isSuccessful());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DataModel>> call, Throwable t) {
                        Log.d(TAG, "Data download fail");
                    }
                });

                break;
            case "UserFragment":

                intfObj.getUserData(id).enqueue(new Callback<UsersData>() {
                    @Override
                    public void onResponse(Call<UsersData> call, Response<UsersData> response) {
                        if (response.isSuccessful()) {
                            usersData = response.body();
                            setFragment(UserFragment.TAG);
                        } else {

                            Log.d(TAG, "Response: " + response.isSuccessful());
                        }
                    }


                    @Override
                    public void onFailure(Call<UsersData> call, Throwable t) {
                        Log.d(TAG, "Data download fail");
                    }
                });

                break;
            default:
                Log.d(this.TAG, "Incorrect DataTAG");
                break;
        }
    }

}
