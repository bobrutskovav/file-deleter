import java.nio.file.Path;
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
    private boolean isNeedDeleteToBin;

    public Application() {
        detector = new PathDetector();
        finder = new Finder();
        deleter = new Deleter();
    }

    private void doJob() {
        String path = detector.getNormalizedCurrentDir();
        finder.setPathToFindIn(path);
        finder.setFileExtensionsToFind(fileExtensions);
        ArrayList<Path> files = finder.findFiles();
        if (!files.isEmpty()) {
            deleter.deleteFiles(files, isNeedDeleteToBin);
        }


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


    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }


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
        this.isNeedDeleteToBin = isNeedDeleteToBin;
    }


}
