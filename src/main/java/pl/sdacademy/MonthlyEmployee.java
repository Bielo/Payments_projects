package pl.sdacademy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class MonthlyEmployee extends Employee {

    private BigDecimal salary;

    public MonthlyEmployee(String name, String adress, String bankAccountNumber, BigDecimal salary) {
        super(name, adress, bankAccountNumber);
        this.salary = salary;
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return DateUtils.isLastWrokingDayOfMonth(day);
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        return isPaymentDay(day) ? salary : BigDecimal.ZERO;
    }

    public LocalDate findFirstDayOfWorkingPeroid(LocalDate paymentDate) {
        return LocalDate.of(paymentDate.getYear(), paymentDate.getMonth(), 1);
    }
}
