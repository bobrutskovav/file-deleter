import java.io.File;
import java.util.ArrayList;

/**
 * Created by ABobrutskov on 04.05.2017.
 */
class Application {
    private ArrayList<String> fileExtensions;
    private final PathDetector detector;
    private final Finder finder;
    private final Deleter deleter;
    private Timer timer;
    private boolean isService;

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

    public void setFileExtension(ArrayList<String> fileExtension) {
        this.fileExtensions = fileExtension;
    }

// --Commented out by Inspection START (12.05.2017 16:30):
//    public ArrayList<String> getFileExtensions() {
//        return fileExtensions;
//    }
// --Commented out by Inspection STOP (12.05.2017 16:30)

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

// --Commented out by Inspection START (12.05.2017 16:30):
//    public boolean isService() {
//        return isService;
//    }
// --Commented out by Inspection STOP (12.05.2017 16:30)

    public void setService(boolean service) {
        isService = service;
    }

    public void setPeriodToDelete(String value) {
        if (value != null) {
            finder.setPeriodToDelete(value);
        }
    }

    public void setDeepSearch(boolean isDeepSearch) {
        finder.setDeepSearch(isDeepSearch);
    }

    public void setNeedDeleteToBin(boolean isNeedDeleteToBin) {
        deleter.setNeedDeleteToBin(isNeedDeleteToBin);
    }


}
