package com.hungth.everynote.view.home;

import android.view.View;

/**
 * Created by Admin on 3/2/2018.
 */

public interface OnItemClickListener {
    void onItemClicked(View itemView, int position);

    void onRemoving(int position);

    void onEditting(int position);
}
