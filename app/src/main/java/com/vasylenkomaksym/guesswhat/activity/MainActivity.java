package com.vasylenkomaksym.guesswhat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.vasylenkomaksym.guesswhat.R;
import com.vasylenkomaksym.guesswhat.fragment.StartUpFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Fragment startUpFragment = new StartUpFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, startUpFragment).commit();

    }


}
