package com.aleksx.workwithfiles.filedeleter;

import com.alee.laf.WebLookAndFeel;

import java.util.List;

/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    static Application app;
    static TrayController controller;
    static CliHandler cliHandler = new CliHandler();

    public static void main(String[] args) {


        try {
            cliHandler.parse(args);
            List<String> fileExtensions = cliHandler.getFileExtensions();
            if (fileExtensions.isEmpty()) {
                fileExtensions.add(".torrent");
            }
            List<String> ingoredExtensions = cliHandler.getIgnoredFileExtensions();
            app = new Application(fileExtensions, ingoredExtensions);

            if (cliHandler.isService()) {
                app.setService(true);
                String timeToRestart = cliHandler.getCooldownTime();
                app.setTimer(new Timer(timeToRestart));
                if (TrayController.isSystemSupportTray()) {
                    WebLookAndFeel.install();
                    controller = new TrayController(app);
                    controller.makeATray();
                }
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
