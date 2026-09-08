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
				
				//Split string into fields
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
				
				
				
			}
		}

	}

}
