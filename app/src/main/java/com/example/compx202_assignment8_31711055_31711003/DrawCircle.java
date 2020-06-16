package com.example.compx202_assignment8_31711055_31711003;


public class DrawCircle extends Objects {
    private double r;
    private int score;
    private final double sqrt2 = Math.sqrt(2);

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

    //Judge whether two circles intersect
    public boolean cIntersect(DrawCircle circle) {
        return (r + circle.r) >= distance(circle.x, circle.y);
    }

    //There is a range when colliding with a square
    public boolean inRange(double min1, double max1, double min2, double max2) {
        return Math.max(min1, max1) >= Math.min(min2, max2) && Math.min(min1, max1) <= Math.max(min2, max2);
    }

    //Judge the range of colliding with the square
    public boolean rectIntersect(DrawRectangle rect) {
        return inRange(x - r / sqrt2, x + r / sqrt2, rect.getX(), rect.getX() + rect.getWidth()) &&
                inRange(y - r / sqrt2, y + r / sqrt2, rect.getY(), rect.getY() + rect.getHeight());
    }

    //After colliding with a square
    public boolean afterCollisionOb(DrawRectangle rect) {
        double angleChanged = angleCollision(rect);
        return angleChanged <= 60 && angleChanged >= -60 ||
                angleChanged >= 120 && angleChanged <= 180 ||
                angleChanged >= -180 && angleChanged <= -120;
    }

    //Set angle after collision
    public double angleCollision(DrawRectangle rect) {
        return Math.toDegrees(Math.atan2(rect.y - y, rect.x - x));
    }

}
