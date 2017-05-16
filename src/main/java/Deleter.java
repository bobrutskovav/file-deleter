import com.sun.jna.platform.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private final FileUtils utils32 = FileUtils.getInstance();
    private ArrayList<File> filesToDelete;
    private boolean isNeedDeleteToBin;

    void deleteFiles() {
        if (filesToDelete.size() > 0) {

            if (isNeedDeleteToBin && utils32.hasTrash()) {
                System.out.println("Deleting to bin... " + filesToDelete.toString());
                try {
                    File[] filesTemp = filesToDelete.toArray(new File[filesToDelete.size()]);
                    utils32.moveToTrash(filesTemp);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Can't delete this files to bin,please get me a bug report on github.com/bobrutskovav");
                }
            } else if (!isNeedDeleteToBin) {
                filesToDelete.parallelStream().forEach(file -> {
                    System.out.println("Deleting... " + file.getName());
                    file.delete();
                });


            }

            System.out.println("It's all clear!");
        }
    }

    public void setFilesToDelete(ArrayList<File> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    public void setNeedDeleteToBin(boolean needDeleteToBin) {
        isNeedDeleteToBin = needDeleteToBin;
    }
}


