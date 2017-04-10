package com.example.rednone.softteco;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RedNone on 08.04.2017.
 */

public class GridAdapter extends ArrayAdapter  {

    private final LayoutInflater inflater;
    private final List<DataModel> list;
    private final int layout;

    static class ViewHolder{
        TextView idTextView,titleTextView;
    }

    public GridAdapter(Context context, int resource, List<DataModel> objects)
    {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        this.list = objects;
        this.layout = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            convertView = inflater.inflate(this.layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.idTextView = (TextView) convertView.findViewById(R.id.textViewId);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.textViewTitle);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DataModel dataModel = (DataModel) list.get(position);
        String str = getContext().getResources().getString(R.string.idMain);
        viewHolder.idTextView.setText(String.format(str,String.valueOf(dataModel.getId())));
        viewHolder.titleTextView.setText(dataModel.getTitle());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
