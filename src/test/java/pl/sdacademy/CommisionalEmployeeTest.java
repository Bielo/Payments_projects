package pl.sdacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommisionalEmployeeTest {

    private CommisionalEmployee commisionalEmployee;
    private static final BigDecimal SALARY = new BigDecimal(100);
    private final BigDecimal INVOICE_VALUE = new BigDecimal(100);
    private static final BigDecimal COMMISION_RATE = new BigDecimal(0.25);

    @BeforeEach
    void init(){
        commisionalEmployee = new CommisionalEmployee("name", "address", "number", SALARY,COMMISION_RATE);
    }

    @Test
    void isPaymentDay() {
        assertTrue(commisionalEmployee.isPaymentDay(LocalDate.of(2017,01,13)));
        assertTrue(commisionalEmployee.isPaymentDay(LocalDate.of(2017,12,29)));
        assertFalse(commisionalEmployee.isPaymentDay(LocalDate.of(2017,01,20)));
    }

    @Test
    void calculatePayment() {
        //jedna faktura sprzed 6.11
        Invoice invoice = new Invoice(LocalDate.of(2017,11,1),INVOICE_VALUE);
        commisionalEmployee.addInvoice(invoice);

        // jedna faktura pomiedzy 6.11 a 17.11
        invoice = new Invoice(LocalDate.of(2017,11,6),INVOICE_VALUE);
        commisionalEmployee.addInvoice(invoice);

        // jedna faktura po 17.11
        invoice = new Invoice(LocalDate.of(2017,11,24),INVOICE_VALUE);
        commisionalEmployee.addInvoice(invoice);

        BigDecimal payment = commisionalEmployee.calculatePayment(LocalDate.of(2017,11,17));

        assertTrue(payment.compareTo(SALARY.add(INVOICE_VALUE.multiply(COMMISION_RATE))) ==0);

    }

    @Test
    void findFirstDayOfWorkingPeroid() {
        //should return 01.01.2016 for given 08.01.2016 (without going back to 2015)
        LocalDate firstDay = commisionalEmployee.findFirstDayOfWorkingPeroid(LocalDate.of(2016,1,8));
        assertTrue(firstDay.equals(LocalDate.of(2016,1,1)));

        //should return 06.11.2017 for given 17.11.2017, from monday to friday
        firstDay = commisionalEmployee.findFirstDayOfWorkingPeroid(LocalDate.of(2017,11,17));
        assertTrue(firstDay.equals(LocalDate.of(2017,11,6)));

        //should return 26.12.2016 for given 30.12.2016 , from monday to last working day
        firstDay= commisionalEmployee.findFirstDayOfWorkingPeroid(LocalDate.of(2016,12,30));
        assertTrue(firstDay.equals(LocalDate.of(2016,12,26)));

        // should throw exception for non payment day
        assertThrows(IllegalArgumentException.class,()-> commisionalEmployee.findFirstDayOfWorkingPeroid(LocalDate.of(2016,1,1)));

    }

}