package com.hungth.everynote;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hungth.everynote.view.home.HomeFragment;
import com.hungth.everynote.view.login.StartFragment;
import com.hungth.everynote.view.note.NoteFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        showFirstFragment();

    }

    @SuppressLint("WrongViewCast")
    private void initializeComponents() {

    }

    public void showFirstFragment() {
        HomeFragment homeFragment = new HomeFragment();

        NoteFragment noteFragment = new NoteFragment();

        StartFragment startFragment = new StartFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, noteFragment).commit();
    }

    @Override
    public void onClick(View v) {

    }
}
