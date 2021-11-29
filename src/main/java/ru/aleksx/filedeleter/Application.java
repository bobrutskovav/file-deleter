package ru.aleksx.filedeleter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABobrutskov on 04.05.2017.
 */
public class Application {
    private final boolean isService;
    private final List<Job> jobs = new ArrayList<>();
    private final Timer timer;
    private final List<String> fileExtansions;


    public Application(List<String> fileExtensions,
                       List<String> ingoredExtensions,
                       List<String> targetPaths,
                       String cooldownTime,
                       String olderThanPeriod,
                       boolean isService,
                       boolean isDeepSearch,
                       boolean isNeedDeleteToBin) {
        this.isService = isService;
        this.timer = new Timer(cooldownTime);
        this.fileExtansions = fileExtensions;
        targetPaths.forEach(path -> jobs.add(new Job(
                new Deleter(isNeedDeleteToBin),
                new Finder(Paths.get(path.trim()),
                        isDeepSearch,
                        olderThanPeriod,
                        fileExtensions,
                        ingoredExtensions))));
    }

    private void doJobs() {
        jobs.forEach(Runnable::run);

    }

    public void start()  {
        if (isService) {
            while (!timer.isInterrupt()) {
                doJobs();
                timer.waitForNextJob();
            }
        } else {
            doJobs();
        }
    }

    public void interrupt() {
        jobs.forEach(Job::shutdown);
    }

    public List<String> getFileExtansions() {
        return fileExtansions;
    }


    private static class Job implements Runnable {
        private volatile boolean isShotDown;
        private final Finder finder;
        private final Deleter deleter;

        public Job(Deleter deleter, Finder finder) {
            this.finder = finder;
            this.deleter = deleter;
        }

        public void shutdown() {
            isShotDown = true;
        }

        @Override
        public void run() {
            List<Path> files = finder.findFiles();
            if (!files.isEmpty()) {
                if (!isShotDown) {
                    deleter.deleteFiles(files);
                }
            }

            try {
                if (!isShotDown) {
                    files = finder.findEmptyFolders();
                    System.out.println("Delete Empty Folders if exists...");
                    while (!files.isEmpty()) {
                        deleter.deleteFiles(files);
                        files = finder.findEmptyFolders();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
