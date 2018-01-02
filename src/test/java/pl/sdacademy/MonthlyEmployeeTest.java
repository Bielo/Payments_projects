package pl.sdacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyEmployeeTest {

    private static final BigDecimal SALARY = new BigDecimal(100);

    private MonthlyEmployee monthlyEmployee;

    @BeforeEach
    void init(){
        monthlyEmployee = new MonthlyEmployee("name", "address", "number",SALARY);
    }

    @Test
    void isPaymentDay() {
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2017,11,30)));
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2017,11,27)));
    }

    @Test
    void calculatePayment() {
        assertTrue(monthlyEmployee.calculatePayment(LocalDate.of(2017,11,27)).compareTo(BigDecimal.ZERO) == 0);
        assertTrue(monthlyEmployee.calculatePayment(LocalDate.of(2017,11,30)).compareTo(SALARY) == 0);
    }

    @Test
    void findFirstDayOfWorkingPeroid() {
        assertEquals(monthlyEmployee.findFirstDayOfWorkingPeroid(LocalDate.of(2017,11,27)),LocalDate.of(2017,11,1));
    }

}