package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ETLPipeline {

	public static void main(String[] args) {
		//Relative file path variables
		String inputPath = "data/employees.csv";
		String outputPath = "data/transformed_employees.csv";
		
		//Counters to track data 
		int rowsRead = 0;
		int rowsTransformed = 0;
		int rowsSkipped = 0;
		
		//File objects
		File inputFile = new File(inputPath);
		File outputFile = new File(outputPath);
		
		//Check for destination folder, and creates if need be
		if (outputFile.getParentFile() != null) {
			outputFile.getParentFile().mkdirs();
		}
		
		//Open files
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
			
			//Write headers in output CSV file
			writer.write("EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus");
			writer.newLine();
			
			//Read first line in input file
			String line = reader.readLine();
			
			//Loop through each line in input file
			while ((line = reader.readLine()) != null) {
				rowsRead++;
				
				//trim whitespace and check if line is blank
				if (line.trim().isEmpty()) {
					rowsSkipped++;
					continue;
				}
				
				//Split file into fields
				String[] fields = line.split(",", -1);
				
				//Check for 5 fields
				if (fields.length != 5) {
					rowsSkipped++;
					continue;
				}
				
				//Normalizing fields: time whitespace
				String rawEmpId = fields[0].trim();
				String rawName = fields[1].trim();
				String rawDept = fields[2].trim();
				String rawHours = fields[3].trim();
				String rawRate = fields[4].trim();
				
				//Validate numeric values
				int employeeId;
				double hoursWorked;
				double hourlyRate;
				
				try {
					employeeId = Integer.parseInt(rawEmpId);
					hoursWorked = Double.parseDouble(rawHours);
					hourlyRate = Double.parseDouble(rawRate);
				} 
				
				//Check for text in numeric field
				catch (NumberFormatException e) {
					rowsSkipped++;
					continue;
				}
				
				//Check for negative numbers
				if (hoursWorked < 0 || hourlyRate < 0) {
					rowsSkipped++;
					continue;
				}
				
				//Normalize fields: convert employee's names to upper case
				String name = rawName.toUpperCase();
				
				String department = rawDept;
				
				//Calculate base and overtime pay
				double grossPay;
				if (hoursWorked <= 40.00) {
					grossPay = hoursWorked * hourlyRate;
				} else {
					double overtimeHours = hoursWorked - 40.00;
					grossPay = (40.00 * hourlyRate) + (overtimeHours * hourlyRate * 1.5);
				}
				
				//Apply IT bonus
				if ("IT".equals(department)) {
					grossPay *= 1.05;
				}
				
				//Round grossPay
				BigDecimal roundedGrossPayBD = BigDecimal.valueOf(grossPay).setScale(2, RoundingMode.HALF_UP);
				double roundedGrossPay = roundedGrossPayBD.doubleValue();
				
				//Determine pay level
				String payLevel;
				if (roundedGrossPay < 500.00) {
					payLevel = "Low";
				} else if (roundedGrossPay <= 999.99) {
					payLevel = "Standard";
				} else if (roundedGrossPay <= 1999.99) {
					payLevel = "High";
				} else {
					payLevel = "Executive";
				}
				
				//Determine employment status
				String employmentStatus;
				if (hoursWorked < 30.00) {
					employmentStatus = "Part-Time";
				} else {
					employmentStatus = "Full-Time";
				}
				
				//Write output
				String formattedHours = String.format("%.2f", hoursWorked);
				String formattedRate = String.format("%.2f", hourlyRate);
				String formattedGross = String.format("%.2f", roundedGrossPayBD);
				
				writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s", 
						employeeId, name, department, formattedHours, 
						formattedRate, formattedGross, payLevel, employmentStatus));
				writer.newLine();
				
				rowsTransformed++;
			}
		
		//Handle any access errors
		} catch (IOException e) {
			System.err.println("Error processing the ETL file pipeline: " + e.getMessage());
			return;
		}
		
		//Print execution summary
		System.out.println("ETL processing complete.");
		System.out.println("Rows Read: " + rowsRead);
		System.out.println("Rows Tarnsformed: " + rowsTransformed);
		System.out.println("Rows Skipped: " + rowsSkipped);
		System.out.println("Output file path written: " + outputPath);

	}

}
