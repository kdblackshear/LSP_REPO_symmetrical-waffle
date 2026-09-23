package org.howard.edu.lsp.assignment3;

import java.util.Optional;

public class EmployeeParser {
	public Optional<Employee> parseLine(String line) {
		if (line == null || line.trim().isEmpty()) {
			return Optional.empty();
		}
		
		String[] fields = line.split(",", -1);
		if (fields.length != 5) {
			return Optional.empty();
		}
		
		try {
			int id = Integer.parseInt(fields[0].trim());
			String name = fields[1].trim();
			String department = fields[2].trim();
			double hours = Double.parseDouble(fields[3].trim());
			double rate = Double.parseDouble(fields[4].trim());
			
			if (hours < 0 || rate < 0) {
				return Optional.empty();
			}
			
			return Optional.of(new Employee(id, name, department, hours, rate));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}