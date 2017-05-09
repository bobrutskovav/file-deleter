/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        CliHandler cliHandler = new CliHandler();
        try {
            cliHandler.parse(args);
        } catch (Exception e) {
            cliHandler.printCliHelp();
            System.exit(0);
        }
        Application app = new Application();
        app.setFileExtension(cliHandler.getFileExtension());

        if (cliHandler.isService()) {
            String timeToRestart = cliHandler.getCooldownTime();
            Timer timer = new Timer(timeToRestart);
            while (true) {
                app.doJob();
                timer.startWaitForNextRun();
            }
        } else {
            app.doJob();
    }
    }

}
