import java.io.File;
import java.util.ArrayList;

/**
 * Created by ABobrutskov on 04.05.2017.
 */
class Application {
    private String[] fileExtensions;
    private PathDetector detector;
    private Finder finder;
    private Deleter deleter;
    private Timer timer;
    boolean isService;

    public Application() {
        detector = new PathDetector();
        finder = new Finder();
        deleter = new Deleter();
    }

    private void doJob() {
        String path = detector.getNormalizedCurrentDir();
        finder.setPathToFindIn(path);
        finder.setFileExtensionsToFind(fileExtensions);
        ArrayList<File> files = finder.findFiles();
        deleter.setFilesToDelete(files);
        deleter.deleteFiles();

    }

    public void start() {
        if (isService) {
            while (!timer.isInterrupt()) {
                doJob();
                timer.waitForNextJob();
            }
        } else {
            doJob();
        }
    }

    public void setFileExtension(String[] fileExtension) {
        this.fileExtensions = fileExtension;
    }

    public String[] getFileExtensions() {
        return fileExtensions;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }
}
