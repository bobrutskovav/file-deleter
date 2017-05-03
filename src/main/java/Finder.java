import java.io.File;
import java.io.FilenameFilter;

/**
 * by aleksx on 03.05.2017.
 */
class Finder {
    private String pathToFindIn;
    private String fileExtensionToFind;

    Finder(String pathToFindIn, String fileExtensionToFind) {
        this.pathToFindIn = pathToFindIn;
        this.fileExtensionToFind = fileExtensionToFind;
    }

    private File[] findAllFilesInCurrentDirectory() {

        File dir = new File(pathToFindIn);
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(fileExtensionToFind);
            }
        });
    }

    File[] findFiles() {
        return findAllFilesInCurrentDirectory();
    }
}
