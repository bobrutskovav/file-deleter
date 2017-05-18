import java.util.ArrayList;

/**
 * by aleksx on 03.05.2017.
 */
class Main {
    public static void main(String[] args) {
        Application app;
        TrayController controller;
        CliHandler cliHandler = new CliHandler();

        try {
            cliHandler.parse(args);
            ArrayList<String> fileExtensions = cliHandler.getFileExtensions();
            if (fileExtensions.isEmpty()) {
                fileExtensions.add(".torrent");
            }
            ArrayList<String> ingoredExtensions = cliHandler.getIgnoredFileExtensions();
            app = new Application(fileExtensions, ingoredExtensions);

            if (cliHandler.isService()) {
                app.setService(true);
                String timeToRestart = cliHandler.getCooldownTime();
                app.setTimer(new Timer(timeToRestart));
                controller = new TrayController(app);
                controller.makeATray();

            }
            String isOlderValue = cliHandler.getOlderThenPerion();
            app.setPeriodToDelete(isOlderValue);
            app.setDeepSearch(cliHandler.getDeepSearch());
            app.setNeedDeleteToBin(cliHandler.getIsNotToBin());

            app.start();
            System.exit(0);

        } catch (Exception e) {
            cliHandler.printCliHelp();
            e.printStackTrace();
            System.exit(0);
        }
    }

}
