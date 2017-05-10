import java.io.File;

/**
 * Created by ABobrutskov on 04.05.2017.
 */
public class Application {
    private String fileExtension;
    private PathDetector detector;
    private Finder finder;
    private Deleter deleter;

    public Application() {
        detector = new PathDetector();
        finder = new Finder();
        deleter = new Deleter();
    }

    public void doJob() {
        String path = detector.getNormalizedCurrentDir();
        finder.setPathToFindIn(path);
        finder.setFileExtensionToFind(fileExtension);
        File[] files = finder.findFiles();
        deleter.setFilesToDelete(files);
        deleter.deleteFiles();

    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
