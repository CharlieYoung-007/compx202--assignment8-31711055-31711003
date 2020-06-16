package com.example.compx202_assignment8_31711055_31711003;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends FullScreenActivity{

    private TextView scoreView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);//Get the ConstraintLayout

        Intent intent = getIntent();
        String title = intent.getStringExtra("score");
        scoreView2 = findViewById(R.id.scoreView2);
        scoreView2.setText(title);

    }
    public void onClickButtonBack(View v){
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
