package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class EtlProcessor {
	private static final String CSV_HEADER = "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";
	
	private final EmployeeParser parser;
	
	public EtlProcessor(EmployeeParser parser) {
		this.parser = parser;
	}
	
	public PipelineMetrics process(File inputFile, File outputFile) throws IOException {
		PipelineMetrics metrics = new PipelineMetrics();
		
		if (outputFile.getParentFile() != null) {
			outputFile.getParentFile().mkdirs();
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
			
			writer.write(CSV_HEADER);
			writer.newLine();
			
			String line = reader.readLine();
			
			while ((line = reader.readLine()) != null) {
				metrics.incrementRead();
				
				Optional<Employee> employeeOpt = parser.parseLine(line);
				if (employeeOpt.isEmpty()) {
					metrics.incrementSkipped();
					continue;
				}
				
				Employee employee = employeeOpt.get();
				writer.write(employee.toCsvRow());
				writer.newLine();
				metrics.incrementTransformed();
			}
		}
		
		return metrics;
	}
}