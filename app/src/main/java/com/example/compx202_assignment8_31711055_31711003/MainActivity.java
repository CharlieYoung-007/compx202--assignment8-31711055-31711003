package com.example.compx202_assignment8_31711055_31711003;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    float[] x;
    float[] y;
    int radius;
    Paint paint;
    int numPoints;

    public class GraphicView extends View {

        public GraphicView(Context context) {
            super(context);
            x = new float[5];
            y = new float[5];
            radius = 50;
            paint = new Paint();
            paint.setColor(getColor(R.color.colorPrimary));
            numPoints = 0;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (int p = 0; p < numPoints; p++) {
                canvas.drawCircle(x[p], y[p], radius, paint);
            }
            invalidate();
        }

        public boolean onTouchEvent(MotionEvent event) {
            numPoints = event.getPointerCount();
            for (int p = 0; p < numPoints; p++) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x[p] = event.getX(p);
                        y[p] = event.getY(p);
                        Log.i("TAG", "Touch DOWN at " + event.getX() + "," + event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x[p] = event.getX(p);
                        y[p] = event.getY(p);
                        Log.i("TAG", "Touch MOVE at " + event.getX() + "," + event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        x[p] = -100;
                        y[p] = -100;
                        Log.i("TAG", "Touch UP at " + event.getX() + "," + event.getY());
                        break;
                }
            }

            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout rootView = (ConstraintLayout) findViewById(R.id.rootView);

        GraphicView myView = new GraphicView(this);

        rootView.addView(myView);

    }

}
