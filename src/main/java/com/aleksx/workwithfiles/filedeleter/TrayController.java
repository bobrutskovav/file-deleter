package com.aleksx.workwithfiles.filedeleter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by aleksx on 10.05.2017.
 */
class TrayController {
    private static final String TRAY_ICON_RESOURCE_NAME = "/image/file.png";
    private SettingsFrame settingsFrame;
    private SystemTray tray;
    private Image image;
    private TrayIcon icon;
    private JPopupMenu popupMenu;
    private Application app;
    private static boolean isSystemSupportTray = SystemTray.isSupported();


    public static boolean isSystemSupportTray() {
        return isSystemSupportTray;
    }

    public TrayController(Application appToControl) {

        this.app = appToControl;
        tray = SystemTray.getSystemTray();
        image = Toolkit.getDefaultToolkit().getImage(TrayController.class.getResource(TRAY_ICON_RESOURCE_NAME));
        setUpTray();
    }

    public void makeATray() {
        try {
            tray.add(icon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    private void setUpTray() {

        ActionListener exitListener = event -> {
            System.out.println("Exiting...");
            app.getTimer().setInterrupt(true);
            tray.remove(icon);
            System.exit(0);
        };

        ActionListener openSettingListener = event -> {
            settingsFrame = new SettingsFrame();
            settingsFrame.setVisible(true);
            settingsFrame.init(true);
        };

        popupMenu = new JPopupMenu();
        JMenuItem closeAppButton = new JMenuItem("Stop FD and Exit");
        JMenuItem openSettingButton = new JMenuItem("Open Settings");
        openSettingButton.addActionListener(openSettingListener);
        closeAppButton.addActionListener(exitListener);
        popupMenu.add(closeAppButton);
        popupMenu.add(openSettingButton);

        icon = new TrayIcon(image, "File Deleter");
        ActionListener clickOnIconListener = e -> {
            List<String> extensions = app.getFileExtensions();
            StringBuffer messageBuffer = new StringBuffer("File Deleter is running \n");
            extensions.forEach(ext -> messageBuffer.append(ext).append("\n"));
            icon.displayMessage("File Deleter Service",
                    messageBuffer.toString(),
                    TrayIcon.MessageType.INFO);
        };

        icon.setImageAutoSize(true);
        icon.addActionListener(clickOnIconListener);
        icon.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.setLocation(e.getX(), e.getY());
                    popupMenu.setInvoker(popupMenu);
                    popupMenu.setVisible(true);
                }
            }
        });
    }
}
