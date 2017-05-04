import java.sql.Time;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {

        ResourceBundle bundle = ResourceBundle.getBundle("help", Locale.getDefault());
        String helpString = bundle.getString("help");
        if (args.length > 0 && args[0].equals("-help")) {

            System.out.println(helpString);
            System.exit(0);
        }

        String fileExtension = ".torrent";
        if (args.length == 1 && args[0].startsWith(".")) {
            fileExtension = args[0];
        } else if (args.length == 3 && args[2].startsWith(".")) {
            fileExtension = args[2];
        }
        Application app = new Application(fileExtension);

        if (args.length >= 2) {
            switch (args[1]) {
                case "-s":
                    String timeToRestart = args[1];
                    Timer timer = new Timer(timeToRestart);
                    if (args.length == 3 && args[2].startsWith(".")) {
                        app.setFileExtension(args[2]);
                    }
                    while (true) {
                        app.doJob();
                        timer.startWaitForNextRun();
                    }
                default:
                    System.out.println(bundle.getString(helpString));

            }
        } else {
            app.doJob();
        }

    }

}
