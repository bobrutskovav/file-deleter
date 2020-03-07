package com.aleksx.workwithfiles.filedeleter;

import com.sun.jna.platform.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private final FileUtils utils32 = FileUtils.getInstance();
    private boolean systemHasATrash = utils32.hasTrash();

    void deleteFiles(List<Path> filesToDelete, boolean isNeedDeleteToBin) {
        if (!filesToDelete.isEmpty()) {

            if (isNeedDeleteToBin && systemHasATrash) {
                deleteFilesToBin(filesToDelete);
            } else {
                deleteFilesWithoutBin(filesToDelete);
            }
            System.out.println("It's all clear!");
        }
    }


    private File[] castPathListToArrayFiles(List<Path> listPath) {
        return listPath.stream()
                .map(Path::toFile)
                .toArray(File[]::new);
    }


    public void deleteFilesToBin(List<Path> filePaths) {
        try {
            System.out.println("Deleting to bin... " + filePaths.toString());
            File[] files = castPathListToArrayFiles(filePaths);
            utils32.moveToTrash(files);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't delete this files to bin, please get me a bug report on github.com/bobrutskovav");
        }
    }

    public void deleteFilesWithoutBin(List<Path> filePaths) {
        filePaths.parallelStream().forEach(file -> {
            System.out.println("Deleting without bin... " + file.getFileName().toString());
            try {
                Files.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}


