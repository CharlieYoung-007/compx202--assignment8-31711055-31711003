package com.example.compx202_assignment8_31711055_31711003;

/**
 * initial the objects in the game screen
 */

abstract public class Objects {
    protected double x;
    protected double y;
    protected int color;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
