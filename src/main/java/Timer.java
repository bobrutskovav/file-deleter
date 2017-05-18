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
        this.stringTimeToNext = periodToNextRun;
        setNextDateToRun();
    }

    private void setNextDateToRun() {
        currentTime = LocalDateTime.now();
        int minutesOrHours = Helper.parseParamInt(stringTimeToNext);
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
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime = LocalDateTime.now();
        }
        setNextDateToRun();
    }


    public boolean isInterrupt() {
        return isInterrupt;
    }

    public void setInterrupt(boolean interrupt) {
        isInterrupt = interrupt;
    }

}
