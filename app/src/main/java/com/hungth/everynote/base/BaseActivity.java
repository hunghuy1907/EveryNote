package com.hungth.everynote.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Admin on 2/4/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements ViewInitializer {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        int contentViewId = getContentViewId();
        if (contentViewId <= 0) {
            return;
        }
        setContentView(contentViewId);
        initializeComponents();
    }
}
