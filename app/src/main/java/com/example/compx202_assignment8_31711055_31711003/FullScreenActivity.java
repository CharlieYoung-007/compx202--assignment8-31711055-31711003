package com.example.compx202_assignment8_31711055_31711003;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
    }

    /**
     * set device into full screen mode
     */
    protected void setFullScreen() {
        // hide action bar
        getSupportActionBar().hide();

        // sticky immersive fullscreen
        int uiOpts = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiOpts);
    }
}