package com.moskuza;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MeteorMouseAdapter extends MouseAdapter {
    Meteor[] meteors;
    MyPanel panel;

    MeteorMouseAdapter(Meteor[] meteors, MyPanel panel) {
        this.meteors = meteors;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        #if DEBUG
        System.out.println("Click count: " + e.getClickCount());
        #endif

        if (e.getClickCount() == 2) {
            for (Meteor meteor : meteors) {
                int x = e.getX();
                int y = e.getY();

                if (isTouchHitbox(x, y, meteor) && !meteor.isDestroyed()) {
                    #if DEBUG
                    System.out.println("Destroyed");
                    #endif
                    meteor.setExplodeX(meteor.getX());
                    meteor.setExplodeY(meteor.getY());
                    meteor.destroy();
                    this.panel.startExplosion(meteor);
                }
            }
        }
    }

    private boolean isTouchHitbox(int mouseX, int mouseY, Meteor meteor) {
        return mouseX > meteor.getX() &&
            mouseX < meteor.getX() + meteor.getWidth() &&
            mouseY > meteor.getY() &&
            mouseY < meteor.getY() + meteor.getHeight();
    }
}
