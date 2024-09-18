package com.moskuza;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Random;

public class MyPanel extends JPanel {
    private final int meteorAmount;
    private final Image[] meteorImages;
    private final Meteor[] meteors;

    public int SPACE_OFFSET_X = 50;
    public int SPACE_OFFSET_Y = 80;

    MyPanel(int meteorAmount) {
        // กำหนดค่าหน้าจอ
        this.setSize(800, 600);

        // sync ตัวแปร
        this.meteorAmount = meteorAmount;

        // โหลดภาพอุกกาบาต
        this.meteorImages = new Image[this.meteorAmount];
        this.meteors = new Meteor[this.meteorAmount];

        // สร้าง Thread ให้อุกกาบาตขยับได้
        MeteorThread[] meteorThread = new MeteorThread[this.meteorAmount];

        for (int i = 0; i < this.meteorImages.length; i++) {
            int randomImage = new Random().nextInt(1, 11);
            URL imageURL = getClass().getResource("/images/" + randomImage + ".png");
            this.meteorImages[i] = Toolkit.getDefaultToolkit().createImage(imageURL);

            // สุ่มตำแหน่งให้อุกกาบาต
            int randomX = new Random().nextInt(this.getWidth());
            int randomY = new Random().nextInt(this.getHeight());

            // กำหนดค่าไม่ให้เกินขอบ
            randomX = Math.min(randomX, this.getWidth() - SPACE_OFFSET_X);
            randomY = Math.min(randomX, this.getHeight() - SPACE_OFFSET_Y);

            int meteorSpeed = new Random().nextInt(1, Meteor.MAX_SPEED);
            this.meteors[i] = new Meteor(randomX, randomY, 60, 60, meteorSpeed);
            meteorThread[i] = new MeteorThread(this.meteors[i], this);
            meteorThread[i].start();
        }
    }

    @Override
    public void paint(Graphics g) {
        // วาดพื้นหลัง
        g.setColor(new Color(0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // วาดรูปอุกกาบาตแบบสุ่มตำแหน่ง
        g.setColor(Color.YELLOW);
        for (int i = 0; i < this.meteorAmount; i++) {
            Meteor meteor = this.meteors[i];
            int meteorPosX = meteor.getX();
            int meteorPosY = meteor.getY();
            int meteorWidth = meteor.getWidth();
            int meteorHeight = meteor.getHeight();

            g.drawImage(this.meteorImages[i], meteorPosX, meteorPosY, meteorWidth, meteorHeight, this);

            #if DEBUG == "1"
            g.drawRect(meteorPosX, meteorPosY, meteorWidth, meteorHeight);
            g.drawString("X: " + meteorPosX + " Y: " + meteorPosY, meteorPosX, meteorPosY - 5);
            g.drawString("Speed: " + meteor.getSpeed(), meteorPosX, meteorPosY + meteorHeight + 15);
            #endif

            for (int j = i + 1; j < this.meteorAmount; j++) {
                Meteor otherMeteor = this.meteors[j];
                if (checkCollision(meteor, otherMeteor)) {
                    handleCollision(meteor, otherMeteor);
                }
            }
        }
    }
    private boolean checkCollision(Meteor m1, Meteor m2) {
        return m1.getX() < m2.getX() + m2.getWidth() &&
                m1.getX() + m1.getWidth() > m2.getX() &&
                m1.getY() < m2.getY() + m2.getHeight() &&
                m1.getY() + m1.getHeight() > m2.getY();
    }

    private void handleCollision(Meteor m1, Meteor m2) {
        // สุ่มทิศทางใหม่สำหรับทั้งสองอุกกาบาต
        m1.setDirection(Meteor.randomDirection());
        m2.setDirection(Meteor.randomDirection());

        // เพิ่มความเร็วเล็กน้อยเพื่อให้เห็นผลการกระเด้งชัดเจนขึ้น
        m1.setSpeed(new Random().nextInt(1, Meteor.MAX_SPEED));
        m2.setSpeed(new Random().nextInt(1, Meteor.MAX_SPEED));

        // แยกอุกกาบาตออกจากกันเล็กน้อยเพื่อป้องกันการติดกัน
        int separationDistance = 5;
        if (m1.getX() < m2.getX()) {
            m1.setX(m1.getX() - separationDistance);
            m2.setX(m2.getX() + separationDistance);
        } else {
            m1.setX(m1.getX() + separationDistance);
            m2.setX(m2.getX() - separationDistance);
        }

        if (m1.getY() < m2.getY()) {
            m1.setY(m1.getY() - separationDistance);
            m2.setY(m2.getY() + separationDistance);
        } else {
            m1.setY(m1.getY() + separationDistance);
            m2.setY(m2.getY() - separationDistance);
        }
    }
}