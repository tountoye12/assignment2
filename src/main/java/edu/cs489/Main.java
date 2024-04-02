package edu.cs489;

import edu.cs489.model.Employee;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import edu.cs489.model.PensionPlan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main( String[] args ) {
        List<Employee> employeeList = new ArrayList<>();

        PensionPlan danielPP = new PensionPlan("EX1089",
                LocalDate.of(2023, 1, 17), 100.00);
        PensionPlan carlyPP = new PensionPlan("SM2307",
                LocalDate.of(2019, 11, 4), 1555.50);

        Employee danielAgar = new Employee(1L, "Daniel", "Agar",
                LocalDate.of(2018, 1, 17), 105945.50);
        danielAgar.setPensionPlan(danielPP);

        Employee bernardShaw = new Employee(2L, "Bernard", "Shaw",
                LocalDate.of(2018, 10, 3), 197750.00);

        Employee carlyAgar = new Employee(3L, "Carly", "Agar",
                LocalDate.of(2014, 5, 16), 842000.75);
        carlyAgar.setPensionPlan(carlyPP);

        Employee wesleySchneider = new Employee(4L, "Wesley", "Schneider",
                LocalDate.of(2018, 11, 2), 74500.00);

        employeeList = List.of(danielAgar, bernardShaw, carlyAgar, wesleySchneider);

        System.out.println();
        System.out.println("---- List of Employees ----");
        printEmployee(employeeList);
        System.out.println();

        System.out.println("---- New Enrollment ----");
        printMonthlyUpcomingEnrollments(employeeList);
    }

    private static void printEmployee(List<Employee> list) {
        StringBuilder jsonSb = new StringBuilder("[");
        jsonSb.append("\n");
        list.stream().sorted(Comparator.comparing(Employee::getLastName)
                        .thenComparing(Employee::getYearlySalary, Comparator.reverseOrder()))
                .forEach(emp -> {
                    jsonSb.append("   ");
                    jsonSb.append("{");
                    jsonSb.append("\"employeeId\":"); jsonSb.append(emp.getEmployeeId()); jsonSb.append(", ");
                    jsonSb.append("\"firstname\":"); jsonSb.append(emp.getFirstName()); jsonSb.append(", ");
                    jsonSb.append("\"lastname\":"); jsonSb.append(emp.getLastName()); jsonSb.append(", ");
                    jsonSb.append("\"employmentDate\":"); jsonSb.append(emp.getEmploymentDate()); jsonSb.append(", ");
                    jsonSb.append("\"yearlySalary\":"); jsonSb.append(emp.getYearlySalary()); jsonSb.append(", ");
                    jsonSb.append("\"pensionPlan\":");
                    jsonSb.append("{");
                    jsonSb.append("\"planReferenceNumber\":"); jsonSb.append(emp.getPensionPlan().getPlanReferenceNumber()); jsonSb.append(", ");
                    jsonSb.append("\"enrollmentDate\":"); jsonSb.append(emp.getPensionPlan().getEnrollmentDate()); jsonSb.append(", ");
                    jsonSb.append("\"monthlyContribution\":"); jsonSb.append(emp.getPensionPlan().getMonthlyContribution());
                    jsonSb.append("}");
                    jsonSb.append("}");
                    jsonSb.append("\n");

                });

        jsonSb.append("]");


        System.out.println(jsonSb);
    }

    private static void printMonthlyUpcomingEnrollments(List<Employee> list) {
        //Collections.sort(list, Comparator.comparing(Employee::getEmploymentDate));
        List<Employee> listOfNewEnrollment = new ArrayList<>();
        list.stream().sorted(Comparator.comparing(Employee::getEmploymentDate)).forEach(
                emp -> {
                    LocalDate currentDate = LocalDate.now();
                    LocalDate lastDayOfCurrentMonth = currentDate.withDayOfMonth(
                            currentDate.lengthOfMonth());
                    LocalDate firstDayOfCurrentMonth = currentDate.withDayOfMonth(1);

                    if(Objects.isNull(emp.getPensionPlan().getPlanReferenceNumber())
                            && (ChronoUnit.YEARS.between(emp.getEmploymentDate(), lastDayOfCurrentMonth) == 5) ||
                            ChronoUnit.YEARS.between(emp.getEmploymentDate(), firstDayOfCurrentMonth) == 5) {
                        listOfNewEnrollment.add(emp);
                    }

                }
        );

        StringBuilder jsonSb = new StringBuilder("[");
        jsonSb.append("\n");
        for(Employee emp: listOfNewEnrollment) {
            jsonSb.append("   ");
            jsonSb.append("{");
            jsonSb.append("\"employeeId\":"); jsonSb.append(emp.getEmployeeId()); jsonSb.append(", ");
            jsonSb.append("\"firstname\":"); jsonSb.append(emp.getFirstName()); jsonSb.append(", ");
            jsonSb.append("\"lastname\":"); jsonSb.append(emp.getLastName()); jsonSb.append(", ");
            jsonSb.append("\"employmentDate\":"); jsonSb.append(emp.getEmploymentDate()); jsonSb.append(", ");
            jsonSb.append("\"yearlySalary\":"); jsonSb.append(emp.getYearlySalary());
            jsonSb.append("}");
            jsonSb.append("\n");
        }
        jsonSb.append("]");

        System.out.println(jsonSb);
    }

}