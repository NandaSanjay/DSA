package codeviber.utils;

import org.codeviber.dto.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeUtil {

    public static void main(String[] args) {
        List<Employee> emps = new ArrayList<>();
        emps.add(new Employee("Sanjay", "sanjay.s.nanda@oracle.com"));
        emps.add(new Employee("Raghav", "raghavendrajalluri@hpe.com"));
        emps.add(new Employee("Sham", "sham.sunder@hpe.com"));
        emps.add(new Employee("Aditya", "Aditya.Raj@oracle.com"));

        Map<String, List<String>> collect = emps.stream()
                .map(Employee::getEmail)
                .collect(Collectors.groupingBy(email -> email.substring(email.indexOf("@")),
                                Collectors.toList()));
        System.out.println(collect);

        String names = emps.stream().map(Employee::getName)
                .map(String::toUpperCase)
                .collect(Collectors.joining("|"));
        System.out.println(names);

    }


}
