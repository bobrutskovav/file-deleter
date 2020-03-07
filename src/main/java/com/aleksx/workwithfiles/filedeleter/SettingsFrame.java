package com.aleksx.workwithfiles.filedeleter;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {
    private JList<String> scanningpaths = new JList<>();
    private JButton addPathButton = new JButton();


    public void init(boolean isVisible) {

        this.setBounds(new Rectangle(400, 500));
        scanningpaths.setBounds(50, 100, 50, 100);
        addPathButton.setBounds(100, 20, 20, 100);
        this.getContentPane().add(scanningpaths);
        this.getContentPane().add(addPathButton);
        scanningpaths.setVisible(isVisible);
        addPathButton.setVisible(isVisible);
    }
}
