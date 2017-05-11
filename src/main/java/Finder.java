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
        for (File file :
                allFindedFiles) {
            if (isDeepSearch && file.isDirectory()) {
                findAllFilesInCurrentDirectory(file.getAbsolutePath());
            } else {
                if (isValidExtension(file) && isOlderThanDeleteDate(file)) result.add(file);
            }
        }
    }

    private boolean isValidExtension(File file) {
        if (fileExtensions.contains("all")) {
            return true;
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
        findAllFilesInCurrentDirectory(pathToFindIn);
        return result;
    }


    private boolean isOlderThanDeleteDate(File file) {
        boolean isOld = true;
        if (deleteDate != null) {
            LocalDateTime dateOfLastModifedOfFile;
            long currentlastModTimestamp = file.lastModified();
            dateOfLastModifedOfFile = parseLongToLocalDateTime(currentlastModTimestamp);
            if (deleteDate.isAfter(dateOfLastModifedOfFile)) {
                isOld = true;
            } else {
                isOld = false;
            }
        }
        return isOld;
    }


    private LocalDateTime parseLongToLocalDateTime(long timestamp) {
        {
            if (timestamp == 0)
                return null;
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
                    .getDefault().toZoneId());
        }
    }

    public String getPathToFindIn() {
        return pathToFindIn;
    }

    public void setPathToFindIn(String pathToFindIn) {
        this.pathToFindIn = pathToFindIn;
    }

    public ArrayList<String> getFileExtensionToFind() {
        return fileExtensions;
    }

    public void setFileExtensionsToFind(ArrayList<String> fileExtensionsToFind) {
        this.fileExtensions = fileExtensionsToFind;
    }


    private ArrayList<File> findAllFilesInCurrentDir(String dirPath) {
        File currentDir = new File(dirPath);
        List<File> files = Arrays.asList(currentDir.listFiles());
        ArrayList<File> result = new ArrayList<>(files);
        return result;
    }

    public void setDeleteDate(String period) {
        if (period.endsWith("d")) {
            //ToDo доделать тут
        }
    }

    public void setDeepSearch(boolean deepSearch) {
        isDeepSearch = deepSearch;
    }

    //ToDo выделить какой нибудь хелпер(из таймера тоже)
    private int parseParam(String param) {
        try {
            return Integer.parseInt(param.replaceAll("\\D+", ""));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid Parameter for -s flag : " + param);
        }
    }
}


