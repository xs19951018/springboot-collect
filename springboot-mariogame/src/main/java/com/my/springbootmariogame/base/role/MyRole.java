package com.my.springbootmariogame.base.role;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class MyRole {

    private int posX = 0, posY = 0;
    private Image[] pic = null;
    private int posIndex = 0;
    boolean mFacus = true;

    public MyRole() {
        pic = new Image[4];
        for (int i = 0; i < 4; i++) {
            pic[i] = Toolkit.getDefaultToolkit().getImage("\\base\\image\\pic" + i + ".png");
        }
    }

    public void init(int x, int y) {
        posX = x;
        posY = y;
    }

    public void set(int x, int y) {
        posX = x;
        posY = y;
    }

    public void drawRole(Graphics g, JPanel p) {
        g.drawImage(pic[posIndex], posX, posY, (ImageObserver) p);
        posIndex ++;
        if (posIndex == 4) posIndex = 0;
    }

    public void updateRole() {
        if (mFacus) posX += 15;
        if (posX == 300) posX = 0;
    }

}
