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

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    int GameSize = 9;
    String[][] MatriceJeu = new String[9][9];
    List<Pion> Pion = new ArrayList<Pion>();
    protected boolean firstboard = true;
    float X_Pawn = 0;
    float Y_Pawn = 0;


    public Board(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);

    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.WHITE);

        float HEIGHT = getHeight();
        float WIDTH = getWidth();
        Paint paint = new Paint();
        Pion p;
        Position po;

        MatriceJeu[8][1] = "Pion";
        MatriceJeu[2][1] = "Pion";
        MatriceJeu[4][1] = "Pion";
        MatriceJeu[6][1] = "Pion";
        MatriceJeu[1][2] = "Pion";
        MatriceJeu[3][2] = "Pion";
        MatriceJeu[5][2] = "Pion";
        MatriceJeu[7][2] = "Pion";
        MatriceJeu[5][5] = "aJouer";


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

                    System.out.println("firstboard: " + firstboard);


                    if (i < 9 && y < 9) {
                        if (firstboard == true) {
                            if (MatriceJeu[i][y] == "Pion") {
                                po = new Position((float) (((getWidth() * ((i - 1) * 0.125)) + ((getWidth() * (i * 0.125)) - (getWidth() * ((i - 1) * 0.125))) / 2)),
                                        (float) (((getHeight() * ((y - 1) * 0.125)) + ((getHeight() * (y * 0.125)) - (getHeight() * ((y - 1) * 0.125))) / 2)), 80);
                                Pion.add(p = new Pion(i, y, po));
                                p = null;
                            }


                        }
                    }

                }
                if (MatriceJeu[i][y] == "aJouer") {

                    System.out.println("X : " + (float) (((getWidth() * ((i - 1) * 0.125)) + ((getWidth() * (i * 0.125)) - (getWidth() * ((i - 1) * 0.125))) / 2)));
                    System.out.println("Y :" + (float) (((getHeight() * ((y - 1) * 0.125)) + ((getHeight() * (y * 0.125)) - (getHeight() * ((y - 1) * 0.125))) / 2)));
                    paint.setColor(Color.YELLOW);
                    canvas.drawCircle((float) (((getWidth() * ((i - 1) * 0.125)) + ((getWidth() * (i * 0.125)) - (getWidth() * ((i - 1) * 0.125))) / 2)),
                            (float) (((getHeight() * ((y - 1) * 0.125)) + ((getHeight() * (y * 0.125)) - (getHeight() * ((y - 1) * 0.125))) / 2)), 80, paint);

                }
            }
        }
        for (int i = 0; i < Pion.size(); i++) {
            paint.setColor(Color.RED);
            canvas.drawCircle(Pion.get(i).getP().getX(),
                    Pion.get(i).getP().getY(), Pion.get(i).getP().getR(), paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    protected void init() {
        // SET
        Pion p;
        Position po;

        for (int i = 0; i < GameSize; i++) {
            for (int y = 0; y < GameSize; y++) {


            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int EventAction = event.getAction();
        Paint paint = new Paint();
        int X = (int) event.getX();
        int Y = (int) event.getY();


        int indicePawn = 0;

        switch (EventAction) {

            case MotionEvent.ACTION_DOWN:
                // CHECK IF ON A SPAWN
                // System.out.println("X: " + X + " Y: " + Y);
                for (int i = 0; i < Pion.size(); i++) {
                    if (((Pion.get(i).getP().getX() < X) && ((X < ((Pion.get(i).getP().getX() + Pion.get(i).getP().getR()) * 2) * 1.25)))
                            && ((Pion.get(i).getP().getY() < Y) && (Y < ((Pion.get(i).getP().getY() + Pion.get(i).getP().getR()) * 2) * 1.25))) {
                        X_Pawn = Pion.get(i).getP().getX();
                        Y_Pawn = Pion.get(i).getP().getY();
                        indicePawn = i;

                    }
                }


                break;

            case MotionEvent.ACTION_MOVE:
                // TOUCH & DRAG DU PION CLIQUE
                if (inCircle(X, Y, Pion.get(indicePawn).getP().getX(), Pion.get(indicePawn).getP().getY(), Pion.get(indicePawn).getP().getR())) {
                    firstboard = false;
                    Pion.get(indicePawn).getP().setX(X);
                    Pion.get(indicePawn).getP().setY(Y);
                    invalidate();
                }


                break;
            case MotionEvent.ACTION_UP:
                // TOUCH DROPPING
                System.out.println(" XPAWN: " + X_Pawn);
                System.out.println(" YPAWN: " + Y_Pawn);
                Pion.get(indicePawn).getP().setX(X_Pawn);
                Pion.get(indicePawn).getP().setY(Y_Pawn);


                invalidate();
                break;
        }

        firstboard = true;
        return true;

    }

    private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        if ((dx + dy) < Math.pow(circleRadius, 2)) {
            return true;
        } else {
            return false;
        }
    }
}
