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

    int gameSize = 9;


    public void setMatriceJeu(int x, int y, String value) {
        MatriceJeu[x][y] = value;
    }

    String[][] MatriceJeu;
    List<Pion> Pion = new ArrayList<Pion>();
    int indexPiece = -1;


    List<Position> listPostion = new ArrayList<Position>();
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


        System.out.println("1. Ondraw: ");
        float HEIGHT = getHeight();
        float WIDTH = getWidth();
        Paint paint = new Paint();
        Position po;


        if (MatriceJeu != null && MatriceJeu.length > 0) {

        } else {
            MatriceJeu = new String[9][9];
            MatriceJeu[8][1] = "Pion";
            MatriceJeu[2][1] = "Pion";
            MatriceJeu[4][1] = "Pion";
            MatriceJeu[6][1] = "Pion";
            MatriceJeu[1][2] = "Pion";
            MatriceJeu[3][2] = "Pion";
            MatriceJeu[5][2] = "Pion";
            MatriceJeu[7][2] = "Pion";
            MatriceJeu[5][5] = "aJouer";

            for (int i = 0; i < gameSize; i++) {
                for (int y = 0; y < gameSize; y++) {
                    if (i < 9 && y < 9) {
                        if (MatriceJeu[i][y] == "Pion") {
                            po = new Position((float) (((getWidth() * ((i - 1) * 0.125)) + ((getWidth() * (i * 0.125)) - (getWidth() * ((i - 1) * 0.125))) / 2)),
                                    (float) (((getHeight() * ((y - 1) * 0.125)) + ((getHeight() * (y * 0.125)) - (getHeight() * ((y - 1) * 0.125))) / 2)), 80);
                            Pion.add(new Pion(i, y, po));
                        }
                        if (MatriceJeu[i][y] == "aJouer") {
                            paint.setColor(Color.YELLOW);
                            canvas.drawCircle((float) (((getWidth() * ((i - 1) * 0.125)) + ((getWidth() * (i * 0.125)) - (getWidth() * ((i - 1) * 0.125))) / 2)),
                                    (float) (((getHeight() * ((y - 1) * 0.125)) + ((getHeight() * (y * 0.125)) - (getHeight() * ((y - 1) * 0.125))) / 2)), 80, paint);

                            listPostion.add(new Position((float) (((getWidth() * ((i - 1) * 0.125)) + ((getWidth() * (i * 0.125)) - (getWidth() * ((i - 1) * 0.125))) / 2)),
                                    (float) (((getHeight() * ((y - 1) * 0.125)) + ((getHeight() * (y * 0.125)) - (getHeight() * ((y - 1) * 0.125))) / 2)), 80));
                        }
                    }
                }
            }
        }


        paint.setColor(Color.DKGRAY);
        for (int i = 0; i < gameSize; i++) {
            canvas.drawLine((float) (WIDTH * (i * 0.125)), HEIGHT, (float) (WIDTH * (i * 0.125)), 0, paint);
            canvas.drawLine((float) ((WIDTH * (i * 0.125) + 1)), HEIGHT, (float) (WIDTH * (i * 0.125)) + 1, 0, paint);
            canvas.drawLine((float) ((WIDTH * (i * 0.125) + 2)), HEIGHT, (float) (WIDTH * (i * 0.125)) + 2, 0, paint);
            canvas.drawLine(0, (float) (HEIGHT * (i * 0.125)), WIDTH, (float) (HEIGHT * (i * 0.125)), paint);
            canvas.drawLine(0, (float) (HEIGHT * (i * 0.125)) + 1, WIDTH, (float) (HEIGHT * (i * 0.125)) + 1, paint);
            canvas.drawLine(0, (float) (HEIGHT * (i * 0.125)) + 2, WIDTH, (float) (HEIGHT * (i * 0.125)) + 2, paint);
        }

        for (int i = 0; i < gameSize; i++) {
            for (int y = 0; y < gameSize; y++) {
                if (((i + y) % 2) == 1) {
                    paint.setColor(Color.BLACK);
                    canvas.drawRect((float) (WIDTH * (i * 0.125)), (float) (HEIGHT * ((y) * 0.125)), (float) (WIDTH * ((i + 1) * 0.125)),
                            (float) (HEIGHT * ((y + 1) * 0.125)), paint);
                }

            }
        }
        for (int i = 0; i < Pion.size(); i++) {
            paint.setColor(Color.RED);
            canvas.drawCircle(Pion.get(i).getP().getX(),
                    Pion.get(i).getP().getY(), Pion.get(i).getP().getR(), paint);
        }
        for (int i = 0; i < listPostion.size(); i++) {
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(listPostion.get(i).getX(),
                    listPostion.get(i).getY(), listPostion.get(i).getR(), paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int EventAction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (EventAction) {
            case MotionEvent.ACTION_DOWN:
                // CHECK IF ON A SPAWN
                for (int i = 0; i < Pion.size(); i++) {
                    if (inCircle(X, Y, Pion.get(i).getP().getX(), Pion.get(i).getP().getY(), Pion.get(i).getP().getR())) {

                        X_Pawn = Pion.get(i).getP().getX();
                        Y_Pawn = Pion.get(i).getP().getY();
                        indexPiece = i;

                    }
                }
                System.out.println("1. ACTION_DOWN: " + indexPiece);
                if (indexPiece > -1) {
                    setMatriceJeu(Pion.get(indexPiece).getX(), Pion.get(indexPiece).getY(), "");

                }

                break;

            case MotionEvent.ACTION_MOVE:
                // TOUCH & DRAG DU PION CLIQUE

                if (indexPiece > -1) {
                    Pion.get(indexPiece).getP().setX(X);
                    Pion.get(indexPiece).getP().setY(Y);

                    System.out.println("2. ACTION_MOVE: ");
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                // TOUCH DROPPING
                System.out.println("3. ACTION_UP: ");
                if (indexPiece > -1) {
                    for (int i = 0; i < listPostion.size(); i++) {
                        if (inCircle(Pion.get(indexPiece).getP().getX(), Pion.get(indexPiece).getP().getY(),
                                listPostion.get(i).getX(), listPostion.get(i).getY(), listPostion.get(i).getR())) {
                            Pion.get(indexPiece).getP().setX(listPostion.get(i).getX());
                            Pion.get(indexPiece).getP().setY(listPostion.get(i).getY());
                            listPostion.remove(i);
                        } else {
                            Pion.get(indexPiece).getP().setX(X_Pawn);
                            Pion.get(indexPiece).getP().setY(Y_Pawn);
                        }
                    }
                    invalidate();
                }
                break;
        }
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
