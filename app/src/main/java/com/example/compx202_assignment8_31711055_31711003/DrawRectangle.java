package com.example.compx202_assignment8_31711055_31711003;

public class DrawRectangle extends Objects{
    private double width;
    private double height;

    /**
     * x - left corner x
     * y - right corner y
     */
    public DrawRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
