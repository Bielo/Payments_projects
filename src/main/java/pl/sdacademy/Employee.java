package pl.sdacademy;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Employee {
    private String name;
    private String adress;
    private String bankAccountNumber;
}
