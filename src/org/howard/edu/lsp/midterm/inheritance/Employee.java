package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class representing a generic employee.
 * Encapsulates common attributes like hours worked and hourly rate.
 */
public abstract class Employee {
    protected double hoursWorked;
    protected double hourlyRate;

    /**
     * Constructs an Employee with the specified hours worked and hourly rate.
     * 
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the hourly pay rate
     */
    public Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Calculates and returns the employee's total pay.
     * Subclasses must implement this method to provide specific pay rules.
     * 
     * @return the calculated pay amount
     */
    public abstract double calculatePay();
}
