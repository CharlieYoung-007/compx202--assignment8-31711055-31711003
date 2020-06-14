package com.example.compx202_assignment8_31711055_31711003;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends FullScreenActivity {

    private Paint paint = new Paint();

    // objects
    private DrawCircle moveBall;
    private DrawRectangle targets; //the target object in the bottom of screen
    private DrawCircle[] scoreBall = new DrawCircle[6]; //How many scoring balls are preset
    private DrawRectangle[] obstacles = new DrawRectangle[6]; //How many obstacles are preset

    //Create a graphic view class extending view
    public class GameCanvas extends View {
        public GameCanvas(Context context) {
            super(context);
            setupScoreBall();
        }

        //Set OnDraw to draw a circle
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            drawObjects(canvas);

            invalidate();
        }

        private void setupScoreBall() {
            // create targets

            //default scoreBall Coordinates which needs to change
            int[][] scoreBallCoordinates= {{550, 200}, {700, 400}, {150, 800}, {350, 500}, {350, 1400}, {750, 800}};
            int scores = 3; //Default score
            for (int i = 0; i < scoreBall.length; i++) {
                scoreBall[i] = new DrawCircle(scoreBallCoordinates[i][0], scoreBallCoordinates[i][1], 50, scores);
                scoreBall[i].setColor(getResources().getColor(R.color.colorPrimary)); //default Color which needs to change
            }
        }
        private void drawObjects(Canvas canvas) {

            // draw targets
            paint.setColor(scoreBall[0].getColor());
            for (DrawCircle target: scoreBall) {
                canvas.drawCircle((float) target.getX(), (float) target.getY(), (float) target.getR(), paint);
            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);//Get the ConstraintLayout
        ConstraintLayout root = findViewById(R.id.GameView);

        //Create an instance of the custom view
        GameCanvas myView = new GameCanvas(this);

        //Add the custom view to the ConstraintLayout
        root.addView(myView);


    }
}