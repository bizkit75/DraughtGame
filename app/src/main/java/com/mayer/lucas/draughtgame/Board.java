package com.mayer.lucas.draughtgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View {

    int GameSize = 8;
    String[][] MatriceJeu = new String[9][9];

    public Board(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.WHITE);

        float HEIGHT = getHeight();
        float WIDTH = getWidth();
        Paint paint = new Paint();


        MatriceJeu[2][1] = "Pion";
        MatriceJeu[4][1] = "Pion";
        MatriceJeu[6][1] = "Pion";
        MatriceJeu[1][2] = "Pion";
        MatriceJeu[3][2] = "Pion";
        MatriceJeu[5][2] = "Pion";
        MatriceJeu[7][2] = "Pion";


        paint.setColor(Color.DKGRAY);

        for (int i = 0; i < GameSize; i++) {
            canvas.drawLine((float) (WIDTH * (i * 0.125)), HEIGHT, (float) (WIDTH * (i * 0.125)), 0, paint);
            canvas.drawLine((float) ((WIDTH * (i * 0.125) + 1)), HEIGHT, (float) (WIDTH * (i * 0.125)) + 1, 0, paint);
            canvas.drawLine((float) ((WIDTH * (i * 0.125) + 2)), HEIGHT, (float) (WIDTH * (i * 0.125)) + 2, 0, paint);
            canvas.drawLine(0, (float) (HEIGHT * (i * 0.125)), WIDTH, (float) (HEIGHT * (i * 0.125)), paint);
            canvas.drawLine(0, (float) (HEIGHT * (i * 0.125)) + 1, WIDTH, (float) (HEIGHT * (i * 0.125)) + 1, paint);
            canvas.drawLine(0, (float) (HEIGHT * (i * 0.125)) + 2, WIDTH, (float) (HEIGHT * (i * 0.125)) + 2, paint);
        }

        for (int i = 0; i < GameSize; i++) {
            for (int y = 0; y < GameSize; y++) {
                if (((i + y) % 2) == 1) {
                    paint.setColor(Color.BLACK);
                    canvas.drawRect((float) (WIDTH * (i * 0.125)), (float) (HEIGHT * ((y) * 0.125)), (float) (WIDTH * ((i + 1) * 0.125)),
                            (float) (HEIGHT * ((y + 1) * 0.125)), paint);
                }

                if (i < 9 && y < 9 && (MatriceJeu[i][y] == "Pion" || MatriceJeu[i][y] == "Dame")) {

                    if (MatriceJeu[i][y] == "Pion") {
                        paint.setColor(Color.RED);
                        canvas.drawCircle((float) (((WIDTH * ((i - 1) * 0.125)) + ((WIDTH * (i * 0.125)) - (WIDTH * ((i - 1) * 0.125))) / 2)),
                                (float) (((HEIGHT * ((y - 1) * 0.125)) + ((HEIGHT * (y * 0.125)) - (HEIGHT * ((y - 1) * 0.125))) / 2)), 30, paint);
                    }

                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    protected void init() {
        // SET

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int EventAction = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();


        switch (EventAction) {
            case MotionEvent.ACTION_DOWN:
                // CHECK IF ON A SPAWN
                System.out.println("X: " + X + " Y: " + Y);
                break;

            case MotionEvent.ACTION_MOVE:
                // TOUCH & DRAG
                System.out.println("X: " + X + " Y: " + Y);
                break;
            case MotionEvent.ACTION_UP:
                // TOUCH DROPPING
                System.out.println("X: " + X + " Y: " + Y);
                break;
        }

        invalidate();
        return true;

    }
}
