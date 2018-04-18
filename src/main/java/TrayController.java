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
    private SystemTray tray;
    private Image image;
    private TrayIcon icon;
    private JPopupMenu popupMenu;
    private Application app;


    public TrayController(Application appToControl) {
        if (SystemTray.isSupported()) {
            this.app = appToControl;
            tray = SystemTray.getSystemTray();
            image = Toolkit.getDefaultToolkit().getImage(TrayController.class.getResource("/image/icon32.png"));
            setUpTray();

        }
    }

    public void makeATray() {
        try {
            tray.add(icon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }



    private void setUpTray() {

        ActionListener exitListener = e -> {
            System.out.println("Exiting...");
            app.getTimer().setInterrupt(true);
            System.exit(0);
        };

        popupMenu = new JPopupMenu();
        JMenuItem defaultItem = new JMenuItem("Stop FD and Exit");
        defaultItem.addActionListener(exitListener);
        popupMenu.add(defaultItem);

        icon = new TrayIcon(image, "File Deleter");
        ActionListener actionListener = e -> {
            List<String> extensions = app.getFileExtensions();
            StringBuffer messageBuffer = new StringBuffer("File Deleter is running \n");
            extensions.forEach(ext -> messageBuffer.append(ext + "\n"));
            icon.displayMessage("File Deleter Service",
                    messageBuffer.toString(),
                    TrayIcon.MessageType.INFO);
        };

        icon.setImageAutoSize(true);
        icon.addActionListener(actionListener);
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
