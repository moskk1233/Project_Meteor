package com.moskuza;

import java.util.Random;

public class Meteor {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int direction;

    public static int MAX_SPEED = 10 + 1;
    public static int randomDirection() {
        return new Random().nextInt(1, 9);
    }

    Meteor(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = randomDirection();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() { return speed; }

    public int getDirection() { return direction; }

    public void move() {
        switch (this.direction) {
            case 1 -> y -= this.speed;
            case 2 -> y += this.speed;
            case 3 -> x -= this.speed;
            case 4 -> x += this.speed;
            case 5 -> {
                x -= this.speed;
                y -= this.speed;
            }
            case 6 -> {
                x += this.speed;
                y -= this.speed;
            }
            case 7 -> {
                x -= this.speed;
                y += this.speed;
            }
            case 8 -> {
                x += this.speed;
                y += this.speed;
            }
            default -> {}
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}