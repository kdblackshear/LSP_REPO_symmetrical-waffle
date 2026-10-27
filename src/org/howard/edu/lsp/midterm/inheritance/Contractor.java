package org.howard.edu.lsp.midterm.inheritance;

/**
 * Represents an independent contractor who receives a 15% premium 
 * on all hours worked.
 */
public class Contractor extends Employee {

    /**
     * Constructs a Contractor with the specified hours worked and hourly rate.
     * 
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the hourly pay rate
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates the pay for a contractor, applying a 15% premium 
     * to the total earnings.
     * 
     * @return the total calculated pay including the premium
     */
    @Override
    public double calculatePay() {
        return (hoursWorked * hourlyRate) * 1.15;
    }
}
