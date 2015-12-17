package com.mayer.lucas.draughtgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Board extends View {
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set the dimension to the smaller of the 2 measures
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);


    }


    Game.Piece[][] plate;
    List<Pion> Pions = new ArrayList<Pion>();
    List<Game.Move> listPostion = new ArrayList<Game.Move>();
    Collection<Game.Move> listAllowedMoves ;
    int selectedPieceI = -1;
    boolean bool = true;



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

        float percent = (float) (1f / MainActivity.size);
        double calibrationHeight = ((1 * percent) * HEIGHT)/2;
        double calibrationWidth = ((1 * percent) * WIDTH)/2;
        int radiusPiece = (int) ((1f / MainActivity.size) * 350);

        Paint paint = new Paint();
        Position po;

        //DRAW RECTANGLES
        for (int i = 0; i < MainActivity.size; i++) {
            for (int y = 0; y < MainActivity.size; y++) {
                if (((i + y) % 2) == 1) {
                    paint.setColor(Color.BLACK);
                    canvas.drawRect((float) (WIDTH * (i * percent)), (float) (HEIGHT * ((y) * percent)), (float) (WIDTH * ((i + 1) * percent)),
                            (float) (HEIGHT * ((y + 1) * percent)), paint);
                }
            }
        }

        plate =  MainActivity.game.getPlate();

        if(bool){
            for (int i = 0; i < plate.length; i++) {
                for (int y = 0; y < plate.length; y++) {
                        if ((plate[i][y].equals(Game.Piece.BPawn))) {
                            po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                        (float) (((y * percent) *  getHeight()) + calibrationHeight), radiusPiece);
                                Pions.add(new Pion(i, y, po, "BPawn"));
                        }
                    if ((plate[i][y].equals(Game.Piece.WPawn))) {

                        po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                (float) (((y * percent) *  getHeight()) + calibrationHeight), radiusPiece);
                        Pions.add(new Pion(i, y, po, "WPawn"));
                    }

                        if (plate[i][y] == Game.Piece.BQueen) {
                            po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                    (float) (((y * percent) *  getHeight()) + calibrationHeight), radiusPiece);
                            Pions.add(new Pion(i, y, po, "BQueen"));

                        }
                    if(plate[i][y] == Game.Piece.WQueen){
                        po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                (float) (((y * percent) *  getHeight()) + calibrationHeight), radiusPiece);
                        Pions.add(new Pion(i, y, po, "WQueen"));
                    }
                }
                bool = false;
            }
            System.out.println("SIZE PION: " + Pions.size());
        }


        for (int i = 0; i < Pions.size(); i++) {
            if(Pions.get(i).getColor() == "BPawn"){
                paint.setColor(Color.RED);
            }
            if(Pions.get(i).getColor() == "WPawn"){
                paint.setColor(Color.GRAY);
            }
            if(Pions.get(i).getColor() == "WQueen"){
                paint.setColor(Color.YELLOW);
            }
            if(Pions.get(i).getColor() == "BQueen"){
                paint.setColor(Color.CYAN);
            }

            canvas.drawCircle(Pions.get(i).getP().getX(),
                    Pions.get(i).getP().getY(), Pions.get(i).getP().getR(), paint);
        }

        if(listAllowedMoves != null){

            for(Game.Move move : listAllowedMoves){
                paint.setColor(Color.YELLOW);
                canvas.drawCircle((float) ((( move.getDst().getX() * percent) * getWidth()) + calibrationWidth),
                        (float) ((( move.getDst().getY() * percent) *  getHeight()) + calibrationHeight), radiusPiece, paint);
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int EventAction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();
        float selectedPieceX = 0;
        float selectedPieceY = 0;
        float percent = (float) (1f / MainActivity.size);
        double calibrationHeight = ((1 *percent) * getHeight())/2;
        double calibrationWidth = ((1 *percent) * getWidth())/2;

        switch (EventAction) {
            case MotionEvent.ACTION_DOWN:
                bool = false;
                for (int i = 0; i < Pions.size(); i++) {
                    if (inCircle(X, Y, Pions.get(i).getP().getX(), Pions.get(i).getP().getY(), Pions.get(i).getP().getR())) {
                        selectedPieceX = Pions.get(i).getP().getX();
                        selectedPieceY = Pions.get(i).getP().getY();
                        selectedPieceI = i;
                        listAllowedMoves = MainActivity.game.allowedMoves(new Game.Position(Pions.get(i).getX(), Pions.get(i).getY()));
                        System.out.println("Size of Collection allowedMoves" + listAllowedMoves.size());
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                System.out.println("2. ACTION_MOVE: " + selectedPieceI);
                if (selectedPieceI > -1) {
                    Pions.get(selectedPieceI).getP().setX(X);
                    Pions.get(selectedPieceI).getP().setY(Y);
                    bool = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("3. ACTION_UP: ");
                if (selectedPieceI > -1) {
                    for(Game.Move move : listAllowedMoves){
                        float moveX = (float) (((move.getDst().getX() * percent) * getWidth()) + calibrationHeight);
                        float moveY = (float) (((move.getDst().getY() * percent) *  getHeight()) + calibrationWidth);

                        if (inCircle(X, Y, moveX, moveY, Pions.get(selectedPieceI).getP().getR())) {
                            MainActivity.game.move(move);
                        }
                    }
                    listAllowedMoves.clear();
                }
                Pions.clear();
                bool = true;
                break;


        }

        invalidate();
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
