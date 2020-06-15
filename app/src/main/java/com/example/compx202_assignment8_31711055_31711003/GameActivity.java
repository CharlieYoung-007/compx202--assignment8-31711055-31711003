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
            setupObstacles();
            setupMoveBall();
            setupTargets();
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

            //default scoreBall Coordinates
            int[][] scoreBallCoordinates= {{550, 900}, {800, 300}, {150, 700}, {700, 1100}, {350, 1600}, {300, 1200}};
            int scores = 3; //Default score
            for (int i = 0; i < scoreBall.length; i++) {
                scoreBall[i] = new DrawCircle(scoreBallCoordinates[i][0], scoreBallCoordinates[i][1], 50, scores);
                scoreBall[i].setColor(getResources().getColor(R.color.goldCoin)); //scoreBall color
            }
        }

        private void setupObstacles() {
            // create obstacles

            //default triangle Coordinates
            int[][] obstaclesCoordinates= {{550, 1250}, {750, 450}, {250, 750}, {350, 400}, {300, 1350}, {700, 1550}};
            for (int i = 0; i < obstacles.length; i++) {
                obstacles[i] = new DrawRectangle(obstaclesCoordinates[i][0], obstaclesCoordinates[i][1], 100,100);
                obstacles[i].setColor(getResources().getColor(R.color.obstacles)); //obstacles color
            }
        }

        private void setupMoveBall() {
            // create move ball

            //default moveBall Coordinates
            int[] moveBallCoordinates= new int[]{300, 300};
            int score = 0;
            moveBall = new DrawCircle(550, 1700, 50, score);
            moveBall.setColor(getResources().getColor(R.color.pinkBall)); //scoreBall color

        }

        private void setupTargets() {
            // create bottom targets

            //default targets Coordinates
            int[] targetCoordinates= new int[]{300, 300};
            int score = 0;
            targets = new DrawRectangle(0, 0, 1200,35);
            targets.setColor(getResources().getColor(R.color.pinkBall)); //bottom targets color

        }

        private void drawObjects(Canvas canvas) {

            // draw targets
            paint.setColor(scoreBall[0].getColor());
            for (DrawCircle target: scoreBall) {
                canvas.drawCircle((float) target.getX(), (float) target.getY(), (float) target.getR(), paint);
            }

            //draw obstacles
            paint.setColor(obstacles[0].getColor());
            for (DrawRectangle obstacle: obstacles) {
                canvas.drawRect((float) obstacle.getX(),(float)obstacle.getY(),
                        (float)(obstacle.getX()+obstacle.getWidth()),(float)(obstacle.getY()+obstacle.getHeight()),paint);
            }

            //draw moveBall
            paint.setColor(moveBall.getColor());
            canvas.drawCircle((float) moveBall.getX(), (float) moveBall.getY(), (float) moveBall.getR(), paint);

            //draw bottom targets
            paint.setColor(targets.getColor());
            canvas.drawRect((float) targets.getX(), (float) targets.getY(),
                    (float)(targets.getX()+targets.getWidth()),(float)(targets.getY()+targets.getHeight()),paint);


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