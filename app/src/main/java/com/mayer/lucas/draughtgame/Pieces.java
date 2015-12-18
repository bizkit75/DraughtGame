package com.mayer.lucas.draughtgame;

/**
 * Created by lulz on 28/11/2015.
 */
public class Pieces {

    int y;
    int x;
    Position p;

    public String getColor() {
        return color;
    }

    String color;

    public Pieces(int x, int y, Position p, String c){
        this.x = x;
        this.y = y;
        this.p = p;
        this.color=c;
    }
    public Pieces(Position p){
        this.p = p;
    }
    public Pieces(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position getP() {
        return p;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setP(Position p) {
        this.p = p;
    }


}
