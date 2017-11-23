package pl.sdacademy;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class HourlyEmployee extends Employee {

    private static final int REGULAR_HOURS = 8;
    private static final BigDecimal OVERTIME_RATE = new BigDecimal("1.5");

    private BigDecimal hourlyRate;
    private List<WorkingDay> workingDays;

    public HourlyEmployee(String name, String adress, String bankAccountNumber, BigDecimal hourlyRate) {
        super(name, adress, bankAccountNumber);
        this.hourlyRate = hourlyRate;
        workingDays = new LinkedList<>();
    }
}
