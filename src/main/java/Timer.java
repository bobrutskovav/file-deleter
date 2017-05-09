import java.time.LocalDateTime;

/**
 * Created by aleksx on 04.05.2017.
 */
class Timer {
    private LocalDateTime nextDateToRun;
    private LocalDateTime currentTime;
    private boolean isInterrupt;

    public Timer(String periodToNextRun) {
        this.currentTime = LocalDateTime.now();
        setNextDateToRun(periodToNextRun);
    }

    private void setNextDateToRun(String param) {
        int minutesOrHours = parseParam(param);
        if (param.endsWith("h")) {
            nextDateToRun = currentTime.plusHours(minutesOrHours);
        } else if (param.endsWith("m")) {
            nextDateToRun = currentTime.plusMinutes(minutesOrHours);
        } else {
            throw new IllegalArgumentException("Invalid Parameter for -s flag : " + param);
        }
    }

    public void waitForNextJob() {
        while (currentTime.isBefore(nextDateToRun)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime = LocalDateTime.now();
        }

    }

    private int parseParam(String param) {
        try {
            return Integer.parseInt(param.replaceAll("\\D+", ""));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid Parameter for -s flag : " + param);
        }
    }

    public boolean isInterrupt() {
        return isInterrupt;
    }

    public void setInterrupt(boolean interrupt) {
        isInterrupt = interrupt;
    }
}
