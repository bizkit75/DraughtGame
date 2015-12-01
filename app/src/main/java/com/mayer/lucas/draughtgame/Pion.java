package com.mayer.lucas.draughtgame;

/**
 * Created by lulz on 28/11/2015.
 */
public class Pion {

    int y;
    int x;
    Position p;

    public Pion(int x, int y, Position p){
        this.x = x;
        this.y = y;
        this.p = p;
    }
    public Pion( Position p){
        this.p = p;
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
