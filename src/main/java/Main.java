/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        Timer timer;
        Application app;
        CliHandler cliHandler = new CliHandler();

        try {
            cliHandler.parse(args);

            app = new Application();
            app.setFileExtension(cliHandler.getFileExtension());

            if (cliHandler.isService()) {
                String timeToRestart = cliHandler.getCooldownTime();
                timer = new Timer(timeToRestart);
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
