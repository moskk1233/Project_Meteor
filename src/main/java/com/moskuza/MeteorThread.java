package com.moskuza;

import java.util.Random;

public class MeteorThread extends Thread {
    private final Meteor meteor;
    private final MyPanel panel;
    private static final int SLEEP_TIME = 25;

    MeteorThread(Meteor meteor, MyPanel panel) {
        this.meteor = meteor;
        this.panel = panel;
    }

    @Override
    public void run() {
        while(true) {
            int x = meteor.getX();
            int y = meteor.getY();
            int width = panel.getWidth() - panel.SPACE_OFFSET_X;
            int height = panel.getHeight() - panel.SPACE_OFFSET_Y;

            // เช็คว่าอุกกาบาตชนขอบไหม ถ้าชนให้เปลี่ยนทิศทางและความเร็ว
            if (x <= 0 || x >= width || y <= 0 || y >= height) {
                int meteorSpeed = new Random().nextInt(1, Meteor.MAX_SPEED);
                this.meteor.setSpeed(meteorSpeed);
                this.meteor.setDirection(Meteor.randomDirection());

                if (x <= 0) meteor.setX(0);
                else if (x >= width) meteor.setX(width);
                if (y <= 0) meteor.setY(0);
                else if (y >= height) meteor.setY(height);
            }
            this.meteor.move();
            this.panel.repaint();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ignored) {}
        }
    }
}