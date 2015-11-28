package com.mayer.lucas.draughtgame;

/**
 * Created by lulz on 28/11/2015.
 */
public class Position {

    float x;
    float y;
    float r;

    public Position(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setY(float y) {
        this.y = y;
    }
}
