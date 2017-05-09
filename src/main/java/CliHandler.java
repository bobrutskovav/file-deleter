import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Created by aleksx on 04.05.2017.
 */
public class CliHandler {

    private Options options;
    private CommandLine line;

    public CliHandler() {

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

    public String getFileExtension() {
        return line.getOptionValue("fe", ".torrent");//By default app uses .torrent extension
    }

    public String getCooldownTime() {
        if (line.hasOption("s")) {
            return line.getOptionValue("s");
        } else {
            return "no";
        }
    }

    public boolean isService() {
        return line.hasOption("s");
    }

    public void printCliHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Torrent Deleter", "Read following instructions for tuning this app",
                options, "Developed by Aleksey Bobrutskov");
    }


}
