import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.stream.Stream;

/**
 * Created by aleksx on 10.05.2017.
 */
public class TrayController {
    String[] fileExtensions;
    private Timer timer;
    private SystemTray tray;
    private Image image;
    private TrayIcon icon;
    private JPopupMenu popupMenu;


    public TrayController(String[] fileExtensionsToShow, Timer timerToControl) {
        if (SystemTray.isSupported()) {
            timer = timerToControl;
            this.fileExtensions = fileExtensionsToShow;
            tray = SystemTray.getSystemTray();
            image = Toolkit.getDefaultToolkit().getImage(TrayController.class.getResource("/image/icon32.png"));
            setUpTray();
            try {
                tray.add(icon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }


    private void setUpTray() {

        ActionListener exitListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        };

        popupMenu = new JPopupMenu();
        JMenuItem defaultItem = new JMenuItem("Stop TD and Exit");
        defaultItem.addActionListener(exitListener);
        popupMenu.add(defaultItem);

        icon = new TrayIcon(image, "Torrent Deleter");
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                StringBuffer massageBuffer = new StringBuffer("Torrent Deleter is running for files :");
                Stream.of(fileExtensions).forEach(s -> massageBuffer.append("\\n " + s));
                icon.displayMessage("Torrent Deleter Service",
                        massageBuffer.toString(),
                        TrayIcon.MessageType.INFO);
            }
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
