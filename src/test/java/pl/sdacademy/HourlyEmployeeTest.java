package pl.sdacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HourlyEmployeeTest {

    private HourlyEmployee hourlyEmployee;

    @BeforeEach
    void init(){
        hourlyEmployee = new HourlyEmployee("name", "address", "number", BigDecimal.ONE);
    }


    @Test
    void shouldReturnTrueWhenDayIsFriday() {
        assertTrue(hourlyEmployee.isPaymentDay(LocalDate.of(2017, 11, 24)), "should return true");
        assertFalse(hourlyEmployee.isPaymentDay(LocalDate.of(2017, 11, 25)), "should return false");
    }

    @Test
    void shoudlReturnZeroForNonPaymentDay() {
        BigDecimal payment = hourlyEmployee.calculatePayment(LocalDate.of(2017,11,25));
        assertEquals(payment, BigDecimal.ZERO);
    }

    @Test
    void shouldReturnRegularPayment(){
        WorkingDay workingDay = new WorkingDay(LocalDate.of(2017,11,24),8);
        hourlyEmployee.addWorkingDay(workingDay);
        BigDecimal payment = hourlyEmployee.calculatePayment(LocalDate.of(2017,11,24));
        assertEquals(payment,BigDecimal.ONE.multiply(new BigDecimal(8)));
    }

    @Test
    void shouldReturnPaymentFromTwoWorkingDays(){
        WorkingDay workingDay = new WorkingDay(LocalDate.of(2017,11,24),8);
        WorkingDay workingDay1 = new WorkingDay(LocalDate.of(2017,11,23),10);
        WorkingDay workingDay2 = new WorkingDay(LocalDate.of(2017,11,22),10);
        WorkingDay workingDay3 = new WorkingDay(LocalDate.of(2017,11,19),8);
        hourlyEmployee.addWorkingDay(workingDay);
        hourlyEmployee.addWorkingDay(workingDay1);
        hourlyEmployee.addWorkingDay(workingDay2);
        hourlyEmployee.addWorkingDay(workingDay3);
        BigDecimal payment = hourlyEmployee.calculatePayment(LocalDate.of(2017,11,24));
        BigDecimal expectetResult = new BigDecimal(30);
        assertTrue(payment.compareTo(expectetResult) == 0);

    }

    @Test
    void findFirstDayOfWorkingPeroid() {
        LocalDate firstDay = hourlyEmployee.findFirstDayOfWorkingPeroid(LocalDate.of(2017, 11, 24));
        assertTrue(firstDay.equals(LocalDate.of(2017, 11, 20)));
    }

}