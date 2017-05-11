import java.io.File;
import java.util.ArrayList;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private ArrayList<File> filesToDelete;

    void deleteFiles() {
        if (filesToDelete.size() > 0) {
            for (File file :
                    filesToDelete) {
                System.out.println("Deleting... " + file.getName());
                file.delete();
            }
            System.out.println("It's all clear!");
        }
    }

    public void setFilesToDelete(ArrayList<File> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }
}
