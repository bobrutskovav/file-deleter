import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private ArrayList<String> ignoredExtensions;

    private void findAllFilesInCurrentDirectory(List<Path> resultStash, Path path) throws IOException {
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
        if (fileName.contains("TorrentDeleter") && fileName.endsWith(".jar")) return false;
        if (fileExtensions.contains("all")) {
            if (ignoredExtensions.isEmpty()) return true;
            else {
                for (String iExt :
                        ignoredExtensions) {
                    if (fileName.endsWith(iExt)) return false;

                }
            }

        }

        for (String ext :
                fileExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return true;
    }


    public List<Path> findFiles() {
        if (deleteDate == null) {
            updateDeleteDate();
        }
        List<Path> result = new ArrayList<>();
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


    public List<Path> findEmptyFolders() throws IOException {
        List<Path> result = new ArrayList<>();
        findEmptyFoldersInCurrentDir(result, pathToFindIn);
        return result;

    }

    private List<Path> findAllFolders(Path pathToFindIn) {
        ArrayList<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathToFindIn)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    result.add(entry);
                }
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void findEmptyFoldersInCurrentDir(List<Path> result, Path pathToFindIn) throws IOException {
        List<Path> foldersInCurrent = findAllFolders(pathToFindIn);

        for (Path folder : foldersInCurrent) {
            if (isDeepSearch && !isEmptyFolder(folder)) {
                findEmptyFoldersInCurrentDir(result, folder);
            } else if (isEmptyFolder(folder)) {
                result.add(folder);
            }
        }
    }

    private boolean isEmptyFolder(Path folder) throws IOException {
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(folder);
        try {
            return !dirStream.iterator().hasNext();
        } finally {
            dirStream.close();
        }

    }


    public void setIgnoredExtensions(ArrayList<String> ignoredExtensions) {
        this.ignoredExtensions = ignoredExtensions;
    }
}


