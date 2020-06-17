package com.example.compx202_assignment8_31711055_31711003;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RankingActivity extends FullScreenActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);//Get the ConstraintLayout

        // get scores and player name
        Intent intent = getIntent();
        String[] scores = intent.getStringArrayExtra("scores");

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);


    }

    public void onClickButtonBack(View v) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
