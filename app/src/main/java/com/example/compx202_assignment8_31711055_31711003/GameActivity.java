package com.example.compx202_assignment8_31711055_31711003;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;

public class GameActivity extends FullScreenActivity {

    private TextView scoreView;
    private int currentScore = 0;

    private int[] scoreArray;
    private int scoreNumber;

    //Create a graphic view class extending view
    public class GameCanvas extends View {

        private Paint paint = new Paint();
        GestureDetector gestureDetector;

        // objects
        private DrawCircle moveBall;
        private DrawRectangle endGameBar; //the target object in the top of screen
        private DrawCircle[] scoreBalls = new DrawCircle[6]; //How many scoring balls are preset
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
            for (int i = 0; i < scoreBalls.length; i++) {
                scoreBalls[i] = new DrawCircle(scoreBallCoordinates[i][0], scoreBallCoordinates[i][1], 50, scores);
                scoreBalls[i].setColor(getResources().getColor(R.color.goldCoin)); //scoreBall color
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
            collisionWithOb();
            collisionWithScoreBall();
            endGame();
            recordScores(5);
            showScore();

            invalidate();
        }

        /**
         * keep the moveBall in the screen
         */
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

        /**
         * when two objects has a collision, the moveBall will change the direction
         */
        private void collisionWithOb() {
            for (DrawRectangle obstacle : obstacles) {
                if (!moveBall.rectIntersect(obstacle)) {
                    continue;
                } else {
                    if (moveBall.afterCollisionOb(obstacle)) {
                        directUp = !directUp;
                    } else {
                        directRight = !directRight;
                    }
                }
                return;
            }

        }

        private void reset() {
            x = 500;
            y = 1900;
            speedX = 0;
            speedY = 0;
            moveBall.setScore(0);
        }

        /**
         * prepare the score which is in a array to sent to the ranking screen
         */
        public void transferRanking() {

            // prepare ranked data
            String[] scores = new String[scoreNumber == 0 ? 1 : scoreNumber];
            if (scoreNumber > 0) {
                for (int i = 0; i < scoreNumber; i++) {
                    scores[i] = scoreArray[scoreArray.length - 1 - i] + "";
                }
            }

            // jump to score screen
            Intent intent = new Intent(GameActivity.this, RankingActivity.class);
            intent.putExtra("scores", scores);
            startActivity(intent);
        }

        /**
         * store the top5 scores
         *
         * @param top5
         */
        private void recordScores(int top5) {
            // exclude 0 score
            if (currentScore == 0) {
                return;
            }
            if (scoreArray == null) {
                scoreArray = new int[top5];
            }

            // return if is duplicated
            for (int i = top5 - 1; i >= top5 - scoreNumber; i--) {
                if (scoreArray[i] == currentScore) return;
            }

            //score array is full
            if (scoreNumber == top5) {
                if (currentScore < scoreArray[0]) {
                    return;
                }

                // update and resort array
                scoreArray[0] = currentScore;
                if (scoreArray[0] > scoreArray[1]) {
                    Arrays.sort(scoreArray);
                }
                return;
            } else {
                scoreArray[top5 - scoreNumber - 1] = currentScore;
            }

            // resort the array only if new score is bigger than the smallest score
            if (scoreNumber >= 1 && scoreArray[top5 - scoreNumber - 1] > scoreArray[top5 - scoreNumber]) {
                Arrays.sort(scoreArray);
            }
            scoreNumber += 1;
        }

        /**
         * when two has collision, the scoreBall will move away from the screen
         */
        private void collisionWithScoreBall() {
            for (DrawCircle scoreBall : scoreBalls) {
                if (!moveBall.cIntersect(scoreBall)) {
                    continue;
                } else {
                    //make the ball disappear from the screen
                    scoreBall.setX(2000);
                    scoreBall.setY(1000);
                }
                moveBall.addScore(scoreBall.getScore());
                return;
            }

        }

        /**
         * when the game end, scores will go to the ranking screen
         *
         * reset the ball's score and make the scoreBall move back to the screen
         */

        private void endGame() {
            if (moveBall.rectIntersect(endGameBar)) {
                setupScoreBall();
                transferRanking();
                reset();
            }

        }

        public void showScore() {
            currentScore = moveBall.getScore();
            scoreView.setText(String.valueOf(currentScore));
        }

        /**
         * draw 4 objects
         * @param canvas
         */
        private void drawObjects(Canvas canvas) {

            // draw targets
            paint.setColor(scoreBalls[0].getColor());
            for (DrawCircle target : scoreBalls) {
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
                    return true;
                }
                //To the right
                if (e1.getX() < e2.getX()) {
                    directRight = true;
                    return true;
                }
                //down
                if (e1.getY() < e2.getY()) {
                    directUp = false;
                    speedY = -speedY;
                    return true;
                }
                //Up
                if (e1.getY() > e2.getY()) {
                    directUp = true;
                    return true;
                }

                return false;
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

        //set the score view
        scoreView = findViewById(R.id.scoreView);


        //Add the custom view to the ConstraintLayout
        root.addView(myView);
    }

}