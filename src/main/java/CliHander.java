import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * Created by aleksx on 04.05.2017.
 */
public class CliHander {

    private Options options;
    private CommandLine line;

    public CliHander() {

        options = new Options();
        setOptions();
    }


    private void setOptions() {
        options.addOption("h", "Print help");
        options.addOption("s", "service", true, "Starts program like a service, set's cooldown period exmpl: 30m or 1h");
        options.addOption("fe", "file extension", true, "File extension to delete ,exmpl: .exe or .part1.rar, the default value is .torrent");
    }

    public void parse(String[] args) throws Exception {

        DefaultParser parser = new DefaultParser();
        line = parser.parse(options, args);
        if (line.hasOption("help")) {
            throw new Exception("Print help and exit");
        }
    }
}
