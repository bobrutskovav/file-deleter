import java.util.Locale;
import java.util.ResourceBundle;

/**
 * by aleksx on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-help")) {
            ResourceBundle bundle = ResourceBundle.getBundle("help", Locale.getDefault());
            System.out.println(bundle.getString("help"));
            System.exit(0);
        }
        PathDetector detector = new PathDetector();

        String fileExtension = ".torrent";
        if (args.length > 0 && args[0].startsWith(".")) {
            fileExtension = args[0];
        }

        Finder finder = new Finder(detector.getNormalizedCurrentDir(), fileExtension);
        Deleter deleter = new Deleter(finder.findFiles());
        deleter.deleteFiles();
        System.exit(0);
    }
}
