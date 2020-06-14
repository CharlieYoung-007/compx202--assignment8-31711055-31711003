package com.example.compx202_assignment8_31711055_31711003;

public class DrawCircle extends Objects {
    private double r;
    private int score = 0;

    /**
     * x - center x
     * y - center y
     * r - radius
     */
    public DrawCircle(double x, double y, double r, int score) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.score = score;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int newScore) {
        if (newScore <= 0) return;
        score += newScore;
    }

    public double distance(double x, double y) {
        return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

}
