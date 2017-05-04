/**
 * Created by ABobrutskov on 04.05.2017.
 */
public class Application {
    private String fileExtension;


    public Application(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void doJob() {

        PathDetector detector = new PathDetector();
        Finder finder = new Finder(detector.getNormalizedCurrentDir(), fileExtension);
        Deleter deleter = new Deleter(finder.findFiles());
        deleter.deleteFiles();
        System.exit(0);

    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
