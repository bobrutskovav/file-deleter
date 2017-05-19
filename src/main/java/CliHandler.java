import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by aleksx on 04.05.2017.
 */
class CliHandler {

    private final Options options;
    private CommandLine line;

    public CliHandler() {

        options = new Options();
        setOptions();
    }


    private void setOptions() {
        options.addOption("h", "help", false, "Print help");
        options.addOption("s", "service", true, "Starts program like a service, set's rerun period, exmpl: 30m or 1h");
        Option fe = Option.builder("fe").desc("File extension to delete ,exmpl: .exe or .part1.rar, the default value is .torrent , can gets one or more arguments , exmlp : .torrent .exe .other").hasArgs().longOpt("fileextensions").build();
        options.addOption(fe);
        Option olderThan = Option.builder("od").desc("Set's period after which the file will be deleted(counting from the date of last change of the file) example : -od 30d or -od 2w or -od 3mn").hasArg().longOpt("olderthan").build();
        options.addOption(olderThan);
        Option deepSearch = Option.builder("ds").desc("Set's flag of recursive search in directories of current catalog").longOpt("deepsearch").build();
        options.addOption(deepSearch);
        Option isNotToBin = Option.builder("nb").desc("Set's flag of delete without bin, by default all files removes to bin, if your system support it (Slowly bu safety").longOpt("nobin").build();
        options.addOption(isNotToBin);
        Option ingoreExt = Option.builder("ie").desc("Files with this extensions will not be deleted,also you can add .ignore file to any catalog, app will be ignore this catalog and files on any operation").longOpt("ingoreextensions").hasArgs().build();
        options.addOption(ingoreExt);
    }

    public void parse(String[] args) throws Exception {

        DefaultParser parser = new DefaultParser();
        line = parser.parse(options, args);
        if (line.hasOption("h")) {
            printCliHelp();
            System.exit(0);
        }
    }

    public ArrayList<String> getFileExtensions() {
        return getSomeListStringFromOption("fe");
    }

    public ArrayList<String> getIgnoredFileExtensions() {
        return getSomeListStringFromOption("ie");
    }

    private ArrayList<String> getSomeListStringFromOption(String opt) {
        String[] array = line.getOptionValues(opt);
        ArrayList<String> result;
        if (array != null) {
            result = new ArrayList<>(Arrays.asList(array));
            return result;
        } else {
            result = new ArrayList<>();
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

    public boolean getIsNotToBin() {
        return !line.hasOption("nb");
    }

    public void printCliHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Torrent Deleter", "Read following instructions for tuning this app",
                options, "Developed by Aleksey Bobrutskov,\\n alekssh1fter@gmail.com ,\\n github.com/bobrutskovav");
    }


}
