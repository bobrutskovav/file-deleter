import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * by aleksx on 03.05.2017.
 */
class Main {
    public static void main(String[] args) {
        Application app;
        TrayController controller;
        WeakReference<CliHandler> cliHandler = new WeakReference<>(new CliHandler());

        try {
            cliHandler.get().parse(args);
            ArrayList<String> fileExtensions = cliHandler.get().getFileExtensions();
            if (fileExtensions.isEmpty()) {
                fileExtensions.add(".torrent");
            }
            ArrayList<String> ingoredExtensions = cliHandler.get().getIgnoredFileExtensions();
            app = new Application(fileExtensions, ingoredExtensions);

            if (cliHandler.get().isService()) {
                app.setService(true);
                String timeToRestart = cliHandler.get().getCooldownTime();
                app.setTimer(new Timer(timeToRestart));
                controller = new TrayController(app);
                controller.makeATray();

            }
            String isOlderValue = cliHandler.get().getOlderThenPerion();
            app.setPeriodToDelete(isOlderValue);
            app.setDeepSearch(cliHandler.get().getDeepSearch());
            app.setNeedDeleteToBin(cliHandler.get().getIsNotToBin());

            app.start();
            System.exit(0);

        } catch (Exception e) {
            cliHandler.get().printCliHelp();
            e.printStackTrace();
            System.exit(0);
        }
    }

}
