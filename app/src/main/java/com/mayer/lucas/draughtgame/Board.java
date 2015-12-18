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

    private Game.Piece[][] plate;
    private List<Pieces> pieces = new ArrayList<Pieces>();
    private List<Game.Move> listPostion = new ArrayList<Game.Move>();
    private Collection<Game.Move> listAllowedMoves;
    private int selectedPieceI = -1;
    private boolean bool = true;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.WHITE);
        float HEIGHT = getHeight();
        float WIDTH = getWidth();

        float percent = (float) (1f / MainActivity.size);
        double calibrationHeight = ((1 * percent) * HEIGHT) / 2;
        double calibrationWidth = ((1 * percent) * WIDTH) / 2;
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

        //GET MATRICE
        plate = MainActivity.game.getPlate();

        //ADD PIECES FROM MATRICE INSIDE LIST WITH POSITIONS
        if (bool) {
            pieces.clear();
            for (int i = 0; i < plate.length; i++) {
                for (int y = 0; y < plate.length; y++) {
                    if ((plate[i][y].equals(Game.Piece.BPawn))) {
                        po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                (float) (((y * percent) * getHeight()) + calibrationHeight), radiusPiece);
                        pieces.add(new Pieces(i, y, po, "BPawn"));
                    }
                    if ((plate[i][y].equals(Game.Piece.WPawn))) {

                        po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                (float) (((y * percent) * getHeight()) + calibrationHeight), radiusPiece);
                        pieces.add(new Pieces(i, y, po, "WPawn"));
                    }

                    if (plate[i][y] == Game.Piece.BQueen) {
                        po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                (float) (((y * percent) * getHeight()) + calibrationHeight), radiusPiece);
                        pieces.add(new Pieces(i, y, po, "BQueen"));

                    }
                    if (plate[i][y] == Game.Piece.WQueen) {
                        po = new Position((float) (((i * percent) * getWidth()) + calibrationWidth),
                                (float) (((y * percent) * getHeight()) + calibrationHeight), radiusPiece);
                        pieces.add(new Pieces(i, y, po, "WQueen"));
                    }
                }
            }
        }

        //DISPLAY PIECES WITH POSITIONS
        for (int i = 0; i < pieces.size(); i++) {
            if (i != selectedPieceI) {
                if (pieces.get(i).getColor() == "BPawn") {
                    paint.setColor(Color.BLACK);
                }
                if (pieces.get(i).getColor() == "WPawn") {
                    paint.setColor(Color.GRAY);
                }
                if (pieces.get(i).getColor() == "WQueen") {
                    paint.setColor(Color.DKGRAY);
                }
                if (pieces.get(i).getColor() == "BQueen") {
                    paint.setColor(Color.rgb(102, 0, 51));
                }
            } else {
                paint.setColor(Color.GREEN);
            }

            canvas.drawCircle(pieces.get(i).getP().getX(),
                    pieces.get(i).getP().getY(), pieces.get(i).getP().getR(), paint);
        }

        // DISPLAY ALLOWED POSITIONS
        if (listAllowedMoves != null) {
            for (Game.Move move : listAllowedMoves) {
                paint.setColor(Color.YELLOW);
                canvas.drawCircle((float) (((move.getDst().getX() * percent) * getWidth()) + calibrationWidth),
                        (float) (((move.getDst().getY() * percent) * getHeight()) + calibrationHeight), radiusPiece, paint);
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
        double calibrationHeight = ((1 * percent) * getHeight()) / 2;
        double calibrationWidth = ((1 * percent) * getWidth()) / 2;

        switch (EventAction) {
            //CHECK IF PIECE
            case MotionEvent.ACTION_DOWN:
                bool = false;
                for (int i = 0; i < pieces.size(); i++) {
                    if (inCircle(X, Y, pieces.get(i).getP().getX(), pieces.get(i).getP().getY(), pieces.get(i).getP().getR())) {
                        selectedPieceX = pieces.get(i).getP().getX();
                        selectedPieceY = pieces.get(i).getP().getY();
                        selectedPieceI = i;
                        listAllowedMoves = MainActivity.game.allowedMoves(new Game.Position(pieces.get(i).getX(), pieces.get(i).getY()));
                    }
                }
                break;
            //DRAG PIECE
            case MotionEvent.ACTION_MOVE:
                if (selectedPieceI > -1) {
                    pieces.get(selectedPieceI).getP().setX(X);
                    pieces.get(selectedPieceI).getP().setY(Y);
                    bool = false;
                }
                break;
            //SET MOVE OR NOT
            case MotionEvent.ACTION_UP:
                if (selectedPieceI > -1) {
                    for (Game.Move move : listAllowedMoves) {
                        float moveX = (float) (((move.getDst().getX() * percent) * getWidth()) + calibrationHeight);
                        float moveY = (float) (((move.getDst().getY() * percent) * getHeight()) + calibrationWidth);

                        if (inCircle(X, Y, moveX, moveY, pieces.get(selectedPieceI).getP().getR())) {
                            MainActivity.game.move(move);
                            MainActivity.textViewTurn.setText(MainActivity.game.turn().toString());
                        }
                    }
                    selectedPieceI = -1;
                    listAllowedMoves.clear();
                }
                pieces.clear();
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
