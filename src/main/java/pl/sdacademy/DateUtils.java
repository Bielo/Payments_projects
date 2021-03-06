package pl.sdacademy;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {

    public static boolean isLastWrokingDayOfMonth(LocalDate date){
        LocalDate lastWorkingDay = LocalDate.of(date.getYear(), date.getMonth(), date.lengthOfMonth());
        if (lastWorkingDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            lastWorkingDay = lastWorkingDay.minusDays(2);
        } else if (lastWorkingDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            lastWorkingDay = lastWorkingDay.minusDays(1);
        }
        return date.equals(lastWorkingDay);

    }
}
