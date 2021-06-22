package ru.aleksx.filedeleter;

/**
 * Created by Aleks on 13.05.2017.
 */
class Helper {

    private Helper() {
    }

    public static long parseParamLong(String param) {
        try {
            return Long.parseLong(param.replaceAll("\\D+", ""));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid Parameter for -od flag : " + param);
        }
    }


    public static int parseParamInt(String param) {
        try {
            return Integer.parseInt(param.replaceAll("\\D+", ""));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid Parameter for -s flag : " + param);
        }
    }
}
