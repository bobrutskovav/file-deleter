/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        PathDetector detector = new PathDetector();

        String fileExtension = ".torrent";
        if (args.length != 0) {
            fileExtension = args[0];
        }

        Finder finder = new Finder(detector.getNormalizedCurrentDir(), fileExtension);
        Deleter deleter = new Deleter(finder.findFiles());
        deleter.deleteFiles();
        System.exit(0);
    }
}
