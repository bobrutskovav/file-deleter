import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by aleksx on 04.05.2017.
 */
class Timer {
    private LocalDateTime nextDateToRun;
    private LocalDateTime currentTime;
    private boolean isNeedToRun;

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
        }
    }

    public void startWaitForNextRun() {
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
        return Integer.parseInt(param.split("\\d")[0]);
    }


}
