package ru.aleksx.filedeleter;

import java.time.LocalDateTime;

/**
 * Created by aleksx on 04.05.2017.
 */
class Timer {
    private LocalDateTime nextDateToRun;
    private boolean isInterrupt;
    private final String stringTimeToNext;

    public Timer(String periodToNextRun) {
        this.stringTimeToNext = periodToNextRun;
        setNextDateToRun();
    }

    private void setNextDateToRun() {
        if (stringTimeToNext != null) {
            var currentTime = LocalDateTime.now();
            int minutesOrHours = Helper.parseParamInt(stringTimeToNext);
            if (stringTimeToNext.endsWith("h")) {
                nextDateToRun = currentTime.plusHours(minutesOrHours);
            } else if (stringTimeToNext.endsWith("m")) {
                nextDateToRun = currentTime.plusMinutes(minutesOrHours);
            } else {
                throw new IllegalArgumentException("Invalid Parameter for -s flag : " + stringTimeToNext);
            }
        }
    }

    public void waitForNextJob() {
        while (LocalDateTime.now().isBefore(nextDateToRun)) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
