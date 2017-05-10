import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * by aleksx on 03.05.2017.
 */
class Finder {
    private String pathToFindIn;
    private String[] fileExtensions;

    private File[] findAllFilesInCurrentDirectory() {


        ArrayList<File> allFindedFiles = new ArrayList<>();
        File dir = new File(pathToFindIn);
        for (String fileExtensionToFind :
                fileExtensions) {
            if (!fileExtensionToFind.startsWith(".")) {
                System.err.println("Extension of the files to delete must be starts with a dot (.) expml: .exe or '.txt'");
            } else {
                allFindedFiles.addAll(findAllFilesWithExtension(fileExtensionToFind, dir));
            }
        }

        return (File[]) allFindedFiles.toArray();

    }

    File[] findFiles() {
        return findAllFilesInCurrentDirectory();
    }


    private ArrayList<File> findAllFilesWithExtension(String fileExtension, File catalog) {
        return (ArrayList<File>) Arrays.asList(catalog.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(fileExtension);
            }
        }));
    }

    public String getPathToFindIn() {
        return pathToFindIn;
    }

    public void setPathToFindIn(String pathToFindIn) {
        this.pathToFindIn = pathToFindIn;
    }

    public String[] getFileExtensionToFind() {
        return fileExtensions;
    }

    public void setFileExtensionsToFind(String[] fileExtensionsToFind) {
        this.fileExtensions = fileExtensionsToFind;
    }
}
