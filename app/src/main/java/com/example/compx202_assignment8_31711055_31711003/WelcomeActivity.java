package com.example.compx202_assignment8_31711055_31711003;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class WelcomeActivity extends FullScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set logo drawable
        ImageView logo_image = (ImageView)findViewById(R.id.imageViewLogo);
        Drawable drawLogo = getDrawable(R.drawable.logo);
        logo_image.setImageDrawable(drawLogo);
    }

    public void onClickButtonStart(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
