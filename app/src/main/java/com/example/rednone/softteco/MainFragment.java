package com.example.rednone.softteco;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    private List<DataModel> list;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView imageView;
    private Button button;
    final String FILENAME = "LogFile";

    public static String TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        list = new ArrayList<>();


        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        dotsLayout = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        button = (Button) view.findViewById(R.id.buttonLog);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(this);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                for (int i = 0; i < dotsCount; i++)
                {
                    dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                dots[position].setTextColor(getResources().getColor(R.color.app_green));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation);
        imageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        list = ((MainActivity) getActivity()).getPostsList();
        if (list != null) {
            getData(list);
            viewPagerAdapter.notifyDataSetChanged();
        }

    }

    public void getData(List<DataModel> list) {

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(viewPagerAdapter);
        setUiPageViewController();
    }

    private void setUiPageViewController() {

        dotsCount = viewPagerAdapter.getCount();
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++)
        {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }

        dots[0].setTextColor(getResources().getColor(R.color.app_green));
    }

    @Override
    public void onClick(View v) {
        WriteLog writeLog = new WriteLog();
        writeLog.execute();
    }

    class WriteLog extends AsyncTask<Void, Void, Void> {


        BufferedWriter bufferedWriter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setEnabled(false);

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Process process = Runtime.getRuntime().exec("logcat -d long *:*");

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(getContext().openFileOutput(FILENAME, MODE_PRIVATE)));

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
