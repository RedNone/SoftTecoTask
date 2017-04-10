package com.example.rednone.softteco;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    final String FILENAME = "LogFile";
    private getJs intfObj;
    private Retrofit retrofit;
    private String TAG = "MainActivity";

    private FragmentManager manager;
    private  FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFragment(MainFragment.TAG, 0);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        intfObj = retrofit.create(getJs.class);

        downloadData(MainFragment.TAG);

    }

    private void setFragment(String TAG,int id)
    {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

     switch (TAG)
     {
         case "MainFragment":
             if(manager.findFragmentByTag(MainFragment.TAG) == null)
             {
                 AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
                 appBarLayout.setVisibility(View.INVISIBLE);
                 transaction.add(R.id.mainConteiner, new MainFragment(), MainFragment.TAG);
             }
             break;
         default:
             Log.d(this.TAG, "Incorrect Fragment");
             break;

     }

        transaction.commit();

    }

    private void downloadData(String tag)
    {
        switch (tag)
        {
            case "MainFragment":
                intfObj.getData().enqueue(new Callback<List<DataModel>>() {
                    @Override
                    public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                        MainFragment obj = (MainFragment) manager.findFragmentByTag(MainFragment.TAG);
                        obj.getData(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<DataModel>> call, Throwable t) {
                        Log.d("TAG", "бедаа");
                    }
                });
                break;
            default:
                Log.d(this.TAG, "Incorrect DataTAG");
                break;
        }
    }

    public void writeFile() {
        WriteLog writeLog = new WriteLog();
        writeLog.execute();
    }

    class WriteLog extends AsyncTask<Void, Void, Void> {

        Button button;
        BufferedWriter bufferedWriter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button = (Button) findViewById(R.id.buttonLog);
            button.setEnabled(false);

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Process process = Runtime.getRuntime().exec("logcat -d long *:*");

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)));

                String str;
                while ((str = reader.readLine()) != null) {

                    Log.d(TAG, str);

                    bufferedWriter.write(str);
                    bufferedWriter.newLine();

                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, String.valueOf(e));
            } catch (IOException e) {
                Log.e(TAG, String.valueOf(e));
            } finally {
                try {
                    if (bufferedWriter != null)
                        bufferedWriter.close();
                } catch (IOException ex) {
                    Log.e(TAG, String.valueOf(ex));
                }

                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            button.setEnabled(true);
        }


    }
}
