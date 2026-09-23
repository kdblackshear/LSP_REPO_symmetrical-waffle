package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Employee {
	private final int id;
	private final String name;
	private final String department;
	private final double hoursWorked;
	private final double hourlyRate;
	
	public Employee(int id, String name, String department, double hoursWorked, double hourlyRate) {
		this.id = id;
		this.name = name.toUpperCase();
		this.department = department;
		this.hoursWorked = hoursWorked;
		this.hourlyRate = hourlyRate;
	}
	
	public int getId() {return id;}
	public String getName() {return name;}
	public String getDepartment() {return department;}
	public double getHoursWorked() {return hoursWorked;}
	public double getHourlyRate() {return hourlyRate;}
	
	public double calculateGrossPay() {
		double baseHours = Math.min(hoursWorked, 40.0);
		double overtimeHours = Math.max(0.0, hoursWorked - 40.0);
		double grossPay = (baseHours * hourlyRate) + (overtimeHours * hourlyRate * 1.5);
		
		if ("IT".equalsIgnoreCase(department)) {
			grossPay *= 1.05;
		}
		
		return BigDecimal.valueOf(grossPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public String getPayLevel() {
		double pay = calculateGrossPay();
		if (pay < 500.00) return "Low";
		if (pay <= 999.99) return "Standard";
		if (pay <= 1999.99) return "High";
		return "Executive";
	}
	
	public String getEmploymentStatus() {
		return hoursWorked < 30.00 ? "Part-Time" : "Full-Time";
	}
	
	public String toCsvRow() {
		return String.format("%d,%s,%s,%.2f,%.2f,%.2f,%s,%s", id, name, department, hoursWorked, hourlyRate, 
				calculateGrossPay(), getPayLevel(), getEmploymentStatus());
	}
}