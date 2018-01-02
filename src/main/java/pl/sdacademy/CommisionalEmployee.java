package pl.sdacademy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CommisionalEmployee extends Employee {

    private BigDecimal salary;
    private BigDecimal commisionRate;
    private List<Invoice> invoices;

    public CommisionalEmployee(String name, String adress, String bankAccountNumber, BigDecimal salary, BigDecimal commisionRate) {
        super(name, adress, bankAccountNumber);
        this.salary = salary;
        this.commisionRate = commisionRate;
        this.invoices = new ArrayList<>();
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    @Override
    public boolean isPaymentDay(LocalDate date) {
        return isEverySecondFriday(date) || isLastWorkingDay(date);
    }

    private boolean isLastWorkingDay(LocalDate date) {
        return date.getMonth() == Month.DECEMBER ? DateUtils.isLastWrokingDayOfMonth(date) : false;
    }

    private boolean isEverySecondFriday(LocalDate date) {
        LocalDate secondFriday = findSecondFridayOfTheYear(date);
        int secondFridaDayOfYear = secondFriday.getDayOfYear();
        int dateDayOfYear = date.getDayOfYear();
        return (dateDayOfYear - secondFridaDayOfYear) % 14 == 0;
    }

    private LocalDate findSecondFridayOfTheYear(LocalDate day) {
        LocalDate date = LocalDate.of(day.getYear(), 01, 1);
        while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusDays(1);
        }
        return date.plusDays(7);
    }

    @Override
    public BigDecimal calculatePayment(LocalDate date) {
        if (isPaymentDay(date)) {
            LocalDate firstDayOfWorkingPeroid = findFirstDayOfWorkingPeroid(date);
            List<Invoice> invoices = findInvoicesWithinPeroid(firstDayOfWorkingPeroid, date);
            return calculatePayment(invoices);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal calculatePayment(List<Invoice> invoices) {
        BigDecimal paymentFromInvoices = BigDecimal.ZERO;
        for (Invoice invoice : invoices) {
            paymentFromInvoices = paymentFromInvoices.add(invoice.getValue());
        }
        paymentFromInvoices = paymentFromInvoices.multiply(commisionRate);
        return paymentFromInvoices.add(salary);
    }

    private List<Invoice> findInvoicesWithinPeroid(LocalDate firstDay, LocalDate lastDay) {
        return invoices.stream().filter(invoice -> invoice.betweenDays(firstDay, lastDay)).collect(Collectors.toList());

    }

    @Override
    public LocalDate findFirstDayOfWorkingPeroid(LocalDate paymentDate) {
        if (isEverySecondFriday(paymentDate)) {
            LocalDate firstDay = paymentDate.minusDays(11);
            //uwaga na zmianę roku
            if (firstDay.getYear() < paymentDate.getYear()) {
                firstDay = LocalDate.of(paymentDate.getYear(), 1, 1);
            }
            return firstDay;
        } else if (isLastWorkingDay(paymentDate)) {
            while (!isEverySecondFriday(paymentDate)) {
                paymentDate = paymentDate.minusDays(1);
            }
            return paymentDate.plusDays(3);
        }else{
            throw new IllegalArgumentException("Nie można wywoływać dla dnia ,który nie jest dniem wypłaty");
        }
    }
}
