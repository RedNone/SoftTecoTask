package com.example.rednone.softteco;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridViewFragment extends Fragment {

    private GridView gridView;
    private GridAdapter arrayAdapter;
    private List<DataModel> list;

    public GridViewFragment() {
        // Required empty public constructor
    }

    static Fragment getNewInstance(List<DataModel> list)
    {
        GridViewFragment gridViewFragment = new GridViewFragment();
        gridViewFragment.list = list;
        return gridViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid_view, container, false);

        arrayAdapter = new GridAdapter(getContext(),R.layout.recycler,list);
        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(arrayAdapter);
        gridView.setNumColumns(3);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel = list.get(position);
                ((MainActivity) getActivity()).setPostId(Integer.parseInt(dataModel.getId()));
                ((MainActivity) getActivity()).downloadData(UserFragment.TAG, Integer.parseInt(dataModel.getUserId()));

            }
        });


        return view;
    }

}
