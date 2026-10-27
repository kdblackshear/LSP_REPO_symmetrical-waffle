package org.howard.edu.lsp.midterm.inheritance;

/**
 * Represents a part-time employee who receives straight hourly pay 
 * regardless of total hours worked.
 */
public class PartTimeEmployee extends Employee {

    /**
     * Constructs a PartTimeEmployee with the specified hours worked and hourly rate.
     * 
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the hourly pay rate
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates the straight hourly pay for a part-time employee.
     * 
     * @return the total calculated pay
     */
    @Override
    public double calculatePay() {
        return hoursWorked * hourlyRate;
    }
}
