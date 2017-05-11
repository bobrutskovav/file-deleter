/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {
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
            app.setFileExtension(fileExtensions);

            if (cliHandler.isService()) {
                app.setService(true);
                String timeToRestart = cliHandler.getCooldownTime();
                app.setTimer(new Timer(timeToRestart));
                controller = new TrayController(app);
                controller.makeATray();

            }
            app.start();
            System.exit(0);

        } catch (Exception e) {
            cliHandler.printCliHelp();
            System.exit(0);
        }
    }

}
