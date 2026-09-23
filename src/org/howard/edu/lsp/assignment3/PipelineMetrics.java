package org.howard.edu.lsp.assignment3;

public class PipelineMetrics {
	private int rowsRead = 0;
	private int rowsTransformed = 0;
	private int rowsSkipped = 0;
	
	public void incrementRead() {rowsRead++;}
	public void incrementTransformed() {rowsTransformed++;}
	public void incrementSkipped() {rowsSkipped++;}
	
	public void printSummary(String outputPath) {
		System.out.println("ETL processing complete.");
		System.out.println("Rows Read: " + rowsRead);
		System.out.println("Rows Tarnsformed: " + rowsTransformed);
		System.out.println("Rows Skipped: " + rowsSkipped);
		System.out.println("Output file path written: " + outputPath);
	}
}