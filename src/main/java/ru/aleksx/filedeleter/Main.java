package ru.aleksx.filedeleter;

import java.lang.ref.WeakReference;
import java.util.List;

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
            var fileExtensions = cliHandler.getFileExtensions();
            if (fileExtensions.isEmpty()) {
                fileExtensions.add(".torrent");
            }
            var ingoredExtensions = cliHandler.getIgnoredFileExtensions();
            var targetDirs = cliHandler.getTargetPaths();
            if (targetDirs.isEmpty()) {
                targetDirs.add(System.getProperty("user.dir"));
            }
            app = new Application(fileExtensions,
                    ingoredExtensions,
                    targetDirs,
                    cliHandler.getCooldownTime(),
                    cliHandler.getOlderThenPerion(),
                    cliHandler.isService(),
                    cliHandler.getDeepSearch(),
                    cliHandler.getIsNotToBin());

            if (cliHandler.isService()) {
                if (TrayController.isSystemSupportTray()) {
                    controller = new TrayController(app);
                    controller.makeATray();
                }
            }
            app.start();
            System.exit(0);

        } catch (Exception e) {
            cliHandler.printCliHelp();
            e.printStackTrace();
            System.exit(1);
        }
    }

}
