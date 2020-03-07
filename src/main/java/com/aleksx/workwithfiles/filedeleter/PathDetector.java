package com.aleksx.workwithfiles.filedeleter;

import java.io.File;

/**
 * by aleksx on 03.05.2017.
 */
class PathDetector {
    private String currentDirectory;

    String getNormalizedCurrentDir() {
        if (currentDirectory == null) {
            setCurrentDir();
            int lastDotIndex = currentDirectory.lastIndexOf(".");
            StringBuffer buffer = new StringBuffer(currentDirectory)
                    .delete(lastDotIndex, lastDotIndex + 2);
            currentDirectory = buffer.toString();
            System.out.println("Current normalized directory : " + currentDirectory);
        }

        return currentDirectory;
    }

    private void setCurrentDir() {
        File file = new File(".");
        currentDirectory = file.getAbsolutePath();

    }
}
