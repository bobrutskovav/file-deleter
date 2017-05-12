import java.time.LocalDateTime;

/**
 * Created by aleksx on 04.05.2017.
 */
class Timer {
    private LocalDateTime nextDateToRun;
    private LocalDateTime currentTime;
    private boolean isInterrupt;
    private final String stringTimeToNext;

    public Timer(String periodToNextRun) {
        this.currentTime = LocalDateTime.now();
        this.stringTimeToNext = periodToNextRun;
        setNextDateToRun();
    }

    private void setNextDateToRun() {
        int minutesOrHours = parseParam(stringTimeToNext);
        if (stringTimeToNext.endsWith("h")) {
            nextDateToRun = currentTime.plusHours(minutesOrHours);
        } else if (stringTimeToNext.endsWith("m")) {
            nextDateToRun = currentTime.plusMinutes(minutesOrHours);
        } else {
            throw new IllegalArgumentException("Invalid Parameter for -s flag : " + stringTimeToNext);
        }
    }

    public void waitForNextJob() {
        while (currentTime.isBefore(nextDateToRun)) {
            try {
                Thread.sleep(60000);
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
