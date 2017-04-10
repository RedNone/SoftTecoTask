package com.example.rednone.softteco;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    public static String TAG = "UserFragment";
    private UsersData usersData;
    private Toolbar toolbar;
    private int id;
    private TextView postIdTextView,
            nameTextView,
            nickNameTextView,
            emailTextView,
            siteTextView,
            numberTextView,
            cityTextView;
    private FloatingActionButton fab;
    private Intent intent;

    static Fragment getNewInstance(int id, UsersData usersData) {
        UserFragment userFragment = new UserFragment();
        userFragment.id = id;
        userFragment.usersData = usersData;
        return userFragment;
    }

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        String str = getString(R.string.ToolbarUser);
        toolbar.setTitle(String.format(str, String.valueOf(usersData.getId())));

        postIdTextView = (TextView) view.findViewById(R.id.postIdTextView);
        nameTextView = (TextView) view.findViewById(R.id.NameTextView);
        nickNameTextView = (TextView) view.findViewById(R.id.NickNametextView);
        emailTextView = (TextView) view.findViewById(R.id.EmailTextView);
        siteTextView = (TextView) view.findViewById(R.id.SiteTextView);
        numberTextView = (TextView) view.findViewById(R.id.NumberTextView);
        cityTextView = (TextView) view.findViewById(R.id.CityTextView);


        emailTextView.setOnClickListener(this);
        siteTextView.setOnClickListener(this);
        numberTextView.setOnClickListener(this);
        cityTextView.setOnClickListener(this);

        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbModel dbModel = new DbModel(usersData.getName(),usersData.getUsername(),usersData.getEmail(),
                                              usersData.getWebsite(),usersData.getPhone(),usersData.address.getCity());
                dbModel.save();
            }
        });

        str = getContext().getResources().getString(R.string.idMain);
        postIdTextView.setText(String.format(str, String.valueOf(id)));

        setUserData();

        return view;
    }

    public void setUserData() {

        String str = "";
        nameTextView.setText(usersData.getName());
        nickNameTextView.setText(usersData.getUsername());

        str = getContext().getResources().getString(R.string.userEmail);
        emailTextView.setText(String.format(str, usersData.getEmail()));

        str = getContext().getResources().getString(R.string.UserSite);
        siteTextView.setText(String.format(str, usersData.getWebsite()));

        str = getContext().getResources().getString(R.string.UserTelephone);
        numberTextView.setText(String.format(str, usersData.getPhone()));

        str = getContext().getResources().getString(R.string.UserCity);
        cityTextView.setText(String.format(str, usersData.address.getCity()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.EmailTextView:
                intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:" + usersData.getEmail()));
                startActivity(intent);
                break;
            case R.id.SiteTextView:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + usersData.getWebsite()));
                startActivity(intent);
                break;
            case R.id.NumberTextView:
                intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + usersData.getPhone()));
                startActivity(intent);
                break;
            case R.id.CityTextView:
                intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:" + usersData.address.geo.getLat() + "," + usersData.address.geo.getLng()));
                startActivity(intent);
                break;
        }
    }
}
