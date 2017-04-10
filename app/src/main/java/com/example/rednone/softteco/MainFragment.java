package com.example.rednone.softteco;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    private List<DataModel> list;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView imageView;
    private Button button;

    public static String TAG = "MainFragment";




    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        list = new ArrayList<>();



        viewPager = (ViewPager) view.findViewById(R.id.viewPager);;
        dotsLayout = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        button = (Button) view.findViewById(R.id.buttonLog);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(this);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                for (int i = 0; i < dotsCount; i++) {
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

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.animation);
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

    public void getData(List<DataModel> list)
    {
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),list);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        setUiPageViewController();

    }

    private void setUiPageViewController() {

        dotsCount = viewPagerAdapter.getCount();
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
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
        ((MainActivity) getActivity()).writeFile();
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private List<DataModel> list;

        public ViewPagerAdapter(FragmentManager fm,List<DataModel> list)
        {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            int count = list.size()/6;
            if((list.size()%6) == 0 )
            {
                return count;
            }
            else
            {
                return ++count;
            }
        }

        @Override
        public Fragment getItem(int position) {
            List<DataModel> data = new ArrayList<>();


            if(position == 0){

                for(int i = position; i < 6; i++)
                {
                    data.add(list.get(i));
                    if(i + 1 == list.size())
                    {
                        return GridViewFragment.getNewInstance(data);
                    }

                }

            }
            else
            {
                int i = position * 6;
                int g = i + 6;
                for(; i < g; i++)
                {
                    data.add(list.get(i));
                    Log.d("TAG", data.toString());

                    if(i + 1 == list.size())
                    {
                        return GridViewFragment.getNewInstance(data);
                    }

                }
            }

            return GridViewFragment.getNewInstance(data);

        }


    }

}
