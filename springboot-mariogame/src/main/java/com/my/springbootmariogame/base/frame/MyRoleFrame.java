package com.my.springbootmariogame.base.frame;

import com.my.springbootmariogame.base.panel.MyRolePanel;

import javax.swing.*;

public class MyRoleFrame extends JFrame {

    public static void main(String[] args) {
        MyRoleFrame frame = new MyRoleFrame();
        frame.add(new MyRolePanel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 375);
        frame.setTitle("myrole");
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
