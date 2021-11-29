package ru.aleksx.filedeleter;


import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private final Desktop desktop = Desktop.getDesktop();
    private final boolean isNeedDeleteToBin;

    Deleter(boolean isNeedDeleteToBin) {
        this.isNeedDeleteToBin = isNeedDeleteToBin;
    }

    void deleteFiles(List<Path> filesToDelete) {
        if (!filesToDelete.isEmpty()) {

            if (isNeedDeleteToBin
                    && Desktop.isDesktopSupported()
                    && desktop.isSupported(Desktop.Action.MOVE_TO_TRASH)) {
                System.out.println("Deleting to bin... " + filesToDelete);
                filesToDelete.forEach(fileToDelete -> {
                    try {
                        desktop.moveToTrash(fileToDelete.toFile());
                    } catch (Throwable e) {
                        e.printStackTrace();
                        System.out.printf("Can't delete file %s to bin!", fileToDelete.getFileName());
                    }
                });

            } else if (!isNeedDeleteToBin) {
                filesToDelete.parallelStream().forEach(file -> {
                    System.out.println("Deleting... " + file.getFileName().toString());
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            System.out.println("It's all clear!");
        }
    }


}


