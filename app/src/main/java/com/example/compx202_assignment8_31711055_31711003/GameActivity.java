package com.example.compx202_assignment8_31711055_31711003;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends FullScreenActivity {

    //Create a graphic view class extending view
    public class GameCanvas extends View {

        private Paint paint = new Paint();
        GestureDetector gestureDetector;

        // objects
        private DrawCircle moveBall;
        private DrawRectangle endGameBar; //the target object in the top of screen
        private DrawCircle[] scoreBall = new DrawCircle[6]; //How many scoring balls are preset
        private DrawRectangle[] obstacles = new DrawRectangle[6]; //How many obstacles are preset

        private float speedX = 0;
        private float speedY = 0;
        protected boolean directRight = true;
        protected boolean directUp = true;
        private float screenWidth = getWidth();
        private float screenHeight = getHeight();

        private float x = 500;
        private float y = 1900;


        public GameCanvas(Context context) {
            super(context);
            setupScoreBall();
            setupObstacles();
            setupMoveBall();
            setupEndGameBar();

            //gesture
            gestureDetector = new GestureDetector(context, new MyGestureListener());
        }


        private void setupScoreBall() {
            // create targets

            //default scoreBall Coordinates
            int[][] scoreBallCoordinates = {{550, 900}, {800, 300}, {150, 700}, {700, 1100}, {350, 1600}, {300, 1200}};
            int scores = 3; //Default score
            for (int i = 0; i < scoreBall.length; i++) {
                scoreBall[i] = new DrawCircle(scoreBallCoordinates[i][0], scoreBallCoordinates[i][1], 50, scores);
                scoreBall[i].setColor(getResources().getColor(R.color.goldCoin)); //scoreBall color
            }
        }

        private void setupObstacles() {
            // create obstacles

            //default triangle Coordinates
            int[][] obstaclesCoordinates = {{550, 1250}, {750, 450}, {250, 750}, {350, 400}, {300, 1350}, {700, 1550}};
            for (int i = 0; i < obstacles.length; i++) {
                obstacles[i] = new DrawRectangle(obstaclesCoordinates[i][0], obstaclesCoordinates[i][1], 100, 100);
                obstacles[i].setColor(getResources().getColor(R.color.obstacles)); //obstacles color
            }
        }

        private void setupMoveBall() {
            // create move ball

            //default moveBall Coordinates
            moveBall = new DrawCircle(x, y, 50, 0);
            moveBall.setColor(getResources().getColor(R.color.pinkBall)); //scoreBall color
            Log.d("moveBall", "X = " + moveBall.getX() + "Y = " + moveBall.getY());
        }

        private void setupEndGameBar() {
            // create bottom targets

            //default targets Coordinates
            endGameBar = new DrawRectangle(0, 0, 1200, 35);
            endGameBar.setColor(getResources().getColor(R.color.pinkBall)); //bottom targets color

        }


        //Set OnDraw to draw a circle
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawObjects(canvas);
            flingBall();


            invalidate();
        }

        private void flingBall() {
            if (x >= screenWidth) {
                directRight = false;
                x = screenWidth;
                speedX = speedX + 2;
            }
            if (directRight == false) {
                x = x - speedX;
                if (y <= 0) {
                    directUp = false;
                    y = 0;
                    speedY = speedY + 2;
                }
                if (y >= screenHeight) {
                    directUp = true;
                    y = screenHeight;
                    speedY = speedY + 2;
                }
                if (directUp == true) {
                    y += speedY;
                }
                if (directUp == false) {
                    y = y - speedY;
                }
            }
            if (x <= 0) {
                directRight = true;
                x = 0;
                speedX = speedX + 2;
            }
            if (directRight == true) {
                x += speedX;
                if (y <= 0) {
                    directUp = false;
                    y = 0;
                    speedY = speedY + 2;
                }
                if (y >= screenHeight) {
                    directUp = true;
                    y = screenHeight;
                    speedY = speedY + 2;
                }
                if (directUp == true) {
                    y += speedY;
                }
                if (directUp == false) {
                    y = y - speedY;
                }
            }

            moveBall.setX(x);
            moveBall.setY(y);
        }

        private void collisionWithOb(){

        }

        private void collisionWithScoreBall(){

        }

        private void endGame(){

        }

        private void drawObjects(Canvas canvas) {

            // draw targets
            paint.setColor(scoreBall[0].getColor());
            for (DrawCircle target : scoreBall) {
                canvas.drawCircle((float) target.getX(), (float) target.getY(), (float) target.getR(), paint);
            }

            //draw obstacles
            paint.setColor(obstacles[0].getColor());
            for (DrawRectangle obstacle : obstacles) {
                canvas.drawRect((float) obstacle.getX(), (float) obstacle.getY(),
                        (float) (obstacle.getX() + obstacle.getWidth()), (float) (obstacle.getY() + obstacle.getHeight()), paint);
            }

            //draw moveBall
            paint.setColor(moveBall.getColor());
            canvas.drawCircle((float) moveBall.getX(), (float) moveBall.getY(), (float) moveBall.getR(), paint);

            //draw bottom targets
            paint.setColor(endGameBar.getColor());
            canvas.drawRect((float) endGameBar.getX(), (float) endGameBar.getY(),
                    (float) (endGameBar.getX() + endGameBar.getWidth()), (float) (endGameBar.getY() + endGameBar.getHeight()), paint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            screenWidth = w;
            screenHeight = h;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (gestureDetector.onTouchEvent(event)) {
                return true;
            }
            x = 500;
            y = 1900;
            speedX = 0;
            speedY = 0;
            return super.onTouchEvent(event);
        }

        private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                speedX = velocityX / 200;
                speedY = velocityY / 200;

                // To the left
                if (e1.getX() > e2.getX()) {
                    directRight = false;
                    speedX = -speedX;
                }
                //To the right
                if (e1.getX() < e2.getX()) {
                    directRight = true;
                }
                //down
                if (e1.getY() < e2.getY()) {
                    directUp = false;
                    speedY = -speedY;
                }
                //Up
                if (e1.getY() > e2.getY()) {
                    directUp = true;
                }
                return true;
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