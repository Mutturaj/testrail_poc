package features;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public static String getCurrentWeekMonday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat("dd_MM_yyyy").format(c.getTime());
    }

    public static String formatDateWithOrdinal() {
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM");
        String monthPart = monthYearFormat.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return day + getOrdinalIndicator(day) + " " + monthPart;
    }

    private static String getOrdinalIndicator(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }

        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }


    public static long getEpoch() {
        return new Date().toInstant().getEpochSecond();
    }
}

