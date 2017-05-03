import java.io.File;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private File[] filesToDelete;

    Deleter(File[] filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    void deleteFiles() {
        if (filesToDelete.length > 0) {
            for (File file :
                    filesToDelete) {
                file.delete();
            }
        }
    }
}
