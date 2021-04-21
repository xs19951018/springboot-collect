package com.my.springbootmariogame.base.panel;

import com.my.springbootmariogame.base.role.MyRole;

import javax.swing.*;
import java.awt.*;

public class MyRolePanel extends JPanel implements Runnable {

    private MyRole myRole;

    public MyRolePanel() {
        myRole = new MyRole();
        Thread thread = new Thread();
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            myRole.updateRole();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        myRole.drawRole(g, this);
    }
}
