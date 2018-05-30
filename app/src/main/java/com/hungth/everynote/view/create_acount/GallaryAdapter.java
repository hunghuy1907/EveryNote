package com.hungth.everynote.view.create_acount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Admin on 3/5/2018.
 */

public class GallaryAdapter extends BaseAdapter{
    private Context context;
    private int[] imageIds = null;

    public GallaryAdapter(Context context, int[] imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(imageIds[position]);

        return imageView;
    }
}
