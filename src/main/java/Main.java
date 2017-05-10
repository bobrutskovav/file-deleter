/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        Timer timer;
        Application app;
        TrayController controller;
        CliHandler cliHandler = new CliHandler();

        try {
            cliHandler.parse(args);

            app = new Application();
            String[] fileExtensions = cliHandler.getFileExtension();
            if (fileExtensions == null) {
                fileExtensions = new String[]{".torrent"};
            }
            app.setFileExtension(cliHandler.getFileExtension());

            if (cliHandler.isService()) {
                String timeToRestart = cliHandler.getCooldownTime();
                timer = new Timer(timeToRestart);
                controller = new TrayController(app.getFileExtensions(), timer);
                while (!timer.isInterrupt()) {
                    app.doJob();
                    timer.waitForNextJob();
                }
            } else {
                app.doJob();
                System.exit(0);
            }
        } catch (Exception e) {
            cliHandler.printCliHelp();
            System.exit(0);
        }
    }

}
