package com.example.compx202_assignment8_31711055_31711003;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class RankingActivity extends FullScreenActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);//Get the ConstraintLayout
    }

    public void onClickButtonBack(View v) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
