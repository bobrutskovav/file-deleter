import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aleksx on 04.05.2017.
 */
class CliHandler {

    private Options options;
    private CommandLine line;

    public CliHandler() {

        options = new Options();
        setOptions();
    }


    private void setOptions() {
        options.addOption("h", "Print help");
        options.addOption("s", "service", true, "Starts program like a service, set's cooldown period, exmpl: 30m or 1h");
        Option fe = Option.builder("fe").desc("File extension to delete ,exmpl: .exe or .part1.rar, the default value is .torrent , can gets one or more arguments , exmlp : .torrent .exe .other").hasArgs().longOpt("fileextensions").build();
        options.addOption(fe);
        Option olderThan = Option.builder("od").desc("Set's period, sets the period after which the file will be deleted(counting from the date of last change of the file) example : -od 30d or -od 2w or -od 3mn").hasArg().longOpt("olderthan").build();
        options.addOption(olderThan);
        Option deepSearch = Option.builder("ds").desc("Set's flag of recursive search in directories of current catalog").longOpt("deepsearch").build();
        options.addOption(deepSearch);
    }

    public void parse(String[] args) throws Exception {

        DefaultParser parser = new DefaultParser();
        line = parser.parse(options, args);
        if (line.hasOption("help")) {
            printCliHelp();
            System.exit(0);
        }
    }

    public ArrayList<String> getFileExtension() {
        String[] array = line.getOptionValues("fe");
        List<String> tempList;
        ArrayList<String> result;
        if (array != null) {
            tempList = Arrays.asList(array);
            result = new ArrayList<String>(tempList);
            return result;
        } else {
            result = new ArrayList<String>();
            return result;
        }
    }

    public String getCooldownTime() {
        return line.getOptionValue("s");
    }

    public boolean isService() {
        return line.hasOption("s");
    }

    public String getOlderThenPerion() {
        return line.getOptionValue("od");
    }

    public boolean getDeepSearch() {
        return line.hasOption("ds");
    }

    public void printCliHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Torrent Deleter", "Read following instructions for tuning this app",
                options, "Developed by Aleksey Bobrutskov, alekssh1fter@gmail.com , github.com/bobrutskovav");
    }


}
