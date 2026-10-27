package org.howard.edu.lsp.midterm.inheritance;

/**
 * Represents a full-time employee who receives overtime pay (1.5x)
 * for hours worked beyond 40 per week.
 */
public class FullTimeEmployee extends Employee {

    /**
     * Constructs a FullTimeEmployee with the specified hours worked and hourly rate.
     * 
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the hourly pay rate
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates the pay for a full-time employee, including 1.5x overtime 
     * pay for any hours exceeding 40.
     * 
     * @return the total calculated pay
     */
    @Override
    public double calculatePay() {
        if (hoursWorked <= 40) {
            return hoursWorked * hourlyRate;
        } else {
            double regularPay = 40 * hourlyRate;
            double overtimeHours = hoursWorked - 40;
            double overtimePay = overtimeHours * hourlyRate * 1.5;
            return regularPay + overtimePay;
        }
    }
}
