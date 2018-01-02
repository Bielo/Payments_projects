package pl.sdacademy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkingDayTest {
    @Test
    @DisplayName("should return true when day is between")
    void shouldReturnTrueWhenDayIsBetween() {
        LocalDate startDay = LocalDate.of(2017,11,23);
        LocalDate betweenDate = LocalDate.of(2017,11,24);
        LocalDate endDay = LocalDate.of(2017,11,25);
        WorkingDay workingDay = new WorkingDay(betweenDate, 10);
        assertTrue(workingDay.betweenDays(startDay,endDay));
    }

    @Test
    @DisplayName("should return false when day is not between")
    void shouldReturnFalseWhenDayIsNotBeteewn(){
        LocalDate startDay = LocalDate.of(2017,11,23);
        LocalDate betweenDate = LocalDate.of(2017,11,20);
        LocalDate endDay = LocalDate.of(2017,11,24);
        WorkingDay workingDay = new WorkingDay(betweenDate,10);
        assertFalse(workingDay.betweenDays(startDay,endDay));
    }

}