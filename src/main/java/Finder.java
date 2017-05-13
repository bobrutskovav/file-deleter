import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * by aleksx on 03.05.2017.
 */
class Finder {
    private boolean isDeepSearch;
    private String pathToFindIn;
    private String periodToDelete;
    private ArrayList<String> fileExtensions;
    private ArrayList<File> result = new ArrayList<>();
    private LocalDateTime deleteDate;

    private void findAllFilesInCurrentDirectory(String path) {
        ArrayList<File> allFindedFiles = findAllFilesInCurrentDir(path);
        //Получить все файлы
        /**Пройти по всем файлам, спросить:
         * если ты директротия - рекурсия
         * если ты файл, то:
         * прогнать файл по разширениям
         * полученый результат - прогнать на проверку:
         * если ты старый - в результат
         */
        if (!allFindedFiles.isEmpty()) {
            for (File file :
                    allFindedFiles) {
                if (isDeepSearch && file.isDirectory()) {
                    findAllFilesInCurrentDirectory(file.getAbsolutePath());
                } else {
                    if (isValidExtension(file)) {
                        if (isOlderThanDeleteDate(file)) {
                            result.add(file);
                        }
                    }
                }
            }
        }
    }

    private boolean isValidExtension(File file) {
        if (fileExtensions.contains("all")) {
            return !(file.getName().contains("TorrentDeleter") && file.getName().endsWith(".jar"));
        }
        String fileName = file.getName();
        boolean isValid = false;
        for (String ext :
                fileExtensions) {
            if (fileName.endsWith(ext)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }


    public ArrayList<File> findFiles() {
        if (deleteDate == null) {
            updateDeleteDate();
        }
        findAllFilesInCurrentDirectory(pathToFindIn);
        updateDeleteDate();
        return result;
    }


    private boolean isOlderThanDeleteDate(File file) {
        boolean isOld = true;
        if (deleteDate != null) {
            LocalDateTime dateOfLastModifedOfFile;
            long currentlastModTimestamp = file.lastModified();
            dateOfLastModifedOfFile = parseLongToLocalDateTime(currentlastModTimestamp);
            isOld = deleteDate.isAfter(dateOfLastModifedOfFile);
        }
        return isOld;
    }


    private LocalDateTime parseLongToLocalDateTime(long timestamp) {
        {
            if (timestamp == 0)
                return null;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone
                    .getDefault().toZoneId());
        }
    }

// --Commented out by Inspection START (12.05.2017 16:30):
//    public String getPathToFindIn() {
//        return pathToFindIn;
//    }
// --Commented out by Inspection STOP (12.05.2017 16:30)

    public void setPathToFindIn(String pathToFindIn) {
        this.pathToFindIn = pathToFindIn;
    }

// --Commented out by Inspection START (12.05.2017 16:30):
//    public ArrayList<String> getFileExtensionToFind() {
//        return fileExtensions;
//    }
// --Commented out by Inspection STOP (12.05.2017 16:30)

    public void setFileExtensionsToFind(ArrayList<String> fileExtensionsToFind) {
        this.fileExtensions = fileExtensionsToFind;
    }


    private ArrayList<File> findAllFilesInCurrentDir(String dirPath) {
        File currentDir = new File(dirPath);
        List<File> files = Arrays.asList(currentDir.listFiles());
        return new ArrayList<>(files);
    }

    private void updateDeleteDate() {
        if (periodToDelete != null) {
            long paramValue = Helper.parseParamLong(periodToDelete);
            LocalDateTime now = LocalDateTime.now();
            if (periodToDelete.endsWith("d")) deleteDate = now.minusDays(paramValue);
            else if (periodToDelete.endsWith("w")) deleteDate = now.minusWeeks(paramValue);
            else if (periodToDelete.endsWith("mn")) deleteDate = now.minusMonths(paramValue);
        }
    }

    public void setPeriodToDelete(String periodToDelete) {
        this.periodToDelete = periodToDelete;
    }

    public void setDeepSearch(boolean deepSearch) {
        isDeepSearch = deepSearch;
    }

    public void clearResultStorage() {
        result.clear();
    }
}


