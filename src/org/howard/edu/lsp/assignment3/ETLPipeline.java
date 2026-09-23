package org.howard.edu.lsp.assignment3;

import java.io.File;
import java.io.IOException;

public class ETLPipeline {

	public static void main(String[] args) {
		String inputPath = "data/employees.csv";
		String outputPath = "data/transformed_employees.csv";
		
		File inputFile = new File(inputPath);
		File outputFile = new File(outputPath);
		
		EmployeeParser parser = new EmployeeParser();
		EtlProcessor processor = new EtlProcessor(parser);
		
		try {
			PipelineMetrics metrics = processor.process(inputFile, outputFile);
			metrics.printSummary(outputPath);
		} catch (IOException e) {
			System.err.println("Error processing the ETL pipeline: " + e.getMessage());
		}
		
	}

}
