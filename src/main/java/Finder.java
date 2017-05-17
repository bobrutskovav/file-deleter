import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * by aleksx on 03.05.2017.
 */
class Finder {
    private boolean isDeepSearch;
    private Path pathToFindIn;
    private String periodToDelete;
    private ArrayList<String> fileExtensions;
    private LocalDateTime deleteDate;

    private void findAllFilesInCurrentDirectory(ArrayList<Path> resultStash, Path path) throws IOException {
        ArrayList<Path> allFilesAndDirs = findAllFilesInCurrentDir(path);
        /**Получить все файлы
         * Пройти по всем файлам, спросить:
         * если ты директротия - рекурсия
         * если ты файл, то:
         * прогнать файл по разширениям
         * полученый результат - прогнать на проверку:
         * если ты старый - в результат
         */
        if (!allFilesAndDirs.isEmpty()) {
            for (Path file :
                    allFilesAndDirs) {
                if (isDeepSearch && Files.isDirectory(file)) {
                    findAllFilesInCurrentDirectory(resultStash, file);
                } else {
                    if (isValidExtension(file)) {
                        if (isOlderThanDeleteDate(file)) {
                            resultStash.add(file);
                        }
                    }
                }
            }
        }
    }

    private boolean isValidExtension(Path file) {
        String fileName = file.getFileName().toString();
        if (fileExtensions.contains("all")) {
            return !(fileName.contains("TorrentDeleter") && fileName.endsWith(".jar"));
        }

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


    public ArrayList<Path> findFiles() {
        if (deleteDate == null) {
            updateDeleteDate();
        }
        ArrayList<Path> result = new ArrayList<>();
        try {
            findAllFilesInCurrentDirectory(result, pathToFindIn);
        } catch (IOException ex) {
            System.out.println("Unable to find files, got errors");
            ex.printStackTrace();
        }
        updateDeleteDate();
        return result;
    }


    private boolean isOlderThanDeleteDate(Path file) throws IOException {
        boolean isOld = true;
        if (deleteDate != null) {
            LocalDateTime dateOfLastModifedOfFile;
            BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
            long currentlastModTimestamp = attributes.lastModifiedTime().toMillis();
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


    public void setPathToFindIn(String pathToFindIn) {

        this.pathToFindIn = Paths.get(pathToFindIn);
    }


    public void setFileExtensionsToFind(ArrayList<String> fileExtensionsToFind) {
        this.fileExtensions = fileExtensionsToFind;
    }


    private ArrayList<Path> findAllFilesInCurrentDir(Path dirPath) {
        ArrayList<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path entry : stream) {
                result.add(entry);
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

}


