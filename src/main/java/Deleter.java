import com.sun.jna.platform.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private final FileUtils utils32 = FileUtils.getInstance();

    void deleteFiles(List<Path> filesToDelete, boolean isNeedDeleteToBin) {
        if (filesToDelete.size() > 0) {

            if (isNeedDeleteToBin && utils32.hasTrash()) {
                System.out.println("Deleting to bin... " + filesToDelete.toString());

                try {
                    File[] filesTemp = castPathListToArrrayFiles(filesToDelete);
                    utils32.moveToTrash(filesTemp);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Can't delete this files to bin,please get me a bug report on github.com/bobrutskovav");
                }
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


    private File[] castPathListToArrrayFiles(List<Path> listPath) {
        List<File> temp = new ArrayList<>();
        listPath.forEach(f -> temp.add(f.toFile()));
        File[] arr = temp.toArray(new File[temp.size()]);
        return arr;
    }


}


