import java.io.File;

/**
 * by aleksx on 03.05.2017.
 */
class Deleter {
    private File[] filesToDelete;

    void deleteFiles() {
        if (filesToDelete.length > 0) {
            for (File file :
                    filesToDelete) {
                System.out.println("Deleting... " + file.getName());
                file.delete();
            }
            System.out.println("It's all clear!");
        }
    }

    public void setFilesToDelete(File[] filesToDelete) {
        this.filesToDelete = filesToDelete;
    }
}
