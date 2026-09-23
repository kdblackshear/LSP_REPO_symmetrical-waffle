1.	How was your Assignment #2 solution organized?

	My Assignment #2 solution was designed in a procedural way, such that the file ETLPipeline.java did six different jobs within the single file. It read the input file, took in information about each employee, calculated each empolyee's pay, and writes the output to an output file, all while keeping track of the input file's metrics.
	
2.	What design changes did you make for Assignment #3?

	In Assignment #3, the solution is now split into 5 different classes, each handling a specific task. The Employee class encapsulates the employee data and calculates each employee's pay, pay level, and employment status. The EmployeeParser class validates the CSV, parses the rows, and checks for errors. The PipelineMetrics class encapsulates the rows read, transformed, and skipped, and handles printing the summary report. The EtlProcessor class carries out organizing all of the reading, parsing, transforming, and writing for each row. Lastly, the ETLPipeline class processes the entire program.
	
3.	What classes or abstractions did you introduce and why?

	I introduced the classes: Employee, EmployeeParser, PipelineMetrics, and EtlProcessor. Each of these classes carries out a unique job that contribute to the overall program. The classes introduced contribute to increasing the overall viability and increasing the scale of the original program. IF, for instance, something like the pay rate changes, only the Employee class needs to be edited, instead of combing through a single file to find the right place to change. 
	
4.	How did you divide responsibilities differently?

	The Employee class deals with employee information and handles business and pay rules/logic. EmployeeParser validates all the data in the input file and converts the text data into objects. PipelineMetrics handles the tracking of reading execution statistics, like rows read, transformed, and skipped. EtlProcessor handles the flow of both reading and writing to the input and output files, respectively. Finally, the ETLPipeline class processes the entire program.
	
5.	Why do you believe your Assignment #3 design is an improvement?

	My Assignment #3 design make the overall program much cleaner, as well as easier to be handled during it life cycle. It makes finding and repairing errors more convenient because they can be found in a specific class, which is much smaller, simpler, and cleaner than a single file carrying out every job. These factors make the program much more scalable and improves upon its opportunity for reuse and its flexibility. 
	
AI Interaction Transcript:
	
ME: Based on my code, can you give recommendations on how to improve using object oriented principles: [Assignment #2 Code]

GEMINI: Your current implementation relies heavily on procedural programming: everything lives in a single main method, forcing it to handle file I/O, string parsing, domain calculations, validation, and error reporting simultaneously.

Applying Object-Oriented Programming (OOP) principles like Single Responsibility, Encapsulation, and Abstraction will make this code far cleaner, modular, and easier to unit test.

Key Refactoring Recommendations
1. Single Responsibility Principle (SRP)
Right now, ETLPipeline does six jobs. Break these into clear, single-purpose components:

Domain Model (Employee): Encapsulates employee state and business logic (pay calculation, pay level, employment status).

CSV Reader/Writer: Handles raw file input/output and line parsing.

Transformer/Processor: Coordinates the flow of reading, validating, transforming, and outputting.

Pipeline Metrics/Summary: Tracks rowsRead, rowsTransformed, and rowsSkipped.

2. Encapsulate Business Logic inside a Domain Model
Instead of running procedural calculations directly on primitive variables inside a loop, create an Employee object that manages its own behavior:

Java
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

    public double calculateGrossPay() {
        double basePay = Math.min(hoursWorked, 40.0) * hourlyRate;
        double overtimePay = Math.max(0, hoursWorked - 40.0) * hourlyRate * 1.5;
        double total = basePay + overtimePay;

        if ("IT".equalsIgnoreCase(department)) {
            total *= 1.05;
        }
        return BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
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

    // Getters...
}
3. Extract Abstract Interfaces or Dedicated Service Classes
Abstracting the reader and writer allows you to swap CSV files for a database or API later without breaking your transformation pipeline:

Java
public interface EmployeeReader {
    List<Employee> readEmployees() throws IOException;
}

public interface EmployeeWriter {
    void writeEmployees(List<TransformedEmployee> employees) throws IOException;
}
4. Separate Validation Logic
Creating an explicit parsing/validation step keeps bad data handling isolated from domain logic:

Java
public class EmployeeParser {
    public Optional<Employee> parseLine(String line) {
        if (line == null || line.trim().isEmpty()) return Optional.empty();

        String[] fields = line.split(",", -1);
        if (fields.length != 5) return Optional.empty();

        try {
            int id = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            String dept = fields[2].trim();
            double hours = Double.parseDouble(fields[3].trim());
            double rate = Double.parseDouble(fields[4].trim());

            if (hours < 0 || rate < 0) return Optional.empty();

            return Optional.of(new Employee(id, name, dept, hours, rate));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
5. Encapsulate Pipeline Execution State
Keep track of progress metrics inside a dedicated object rather than dangling local counters:

Java
public class PipelineMetrics {
    private int rowsRead;
    private int rowsTransformed;
    private int rowsSkipped;

    public void incrementRead() { rowsRead++; }
    public void incrementTransformed() { rowsTransformed++; }
    public void incrementSkipped() { rowsSkipped++; }

    public void printSummary(String outputPath) {
        System.out.println("ETL processing complete.");
        System.out.println("Rows Read: " + rowsRead);
        System.out.println("Rows Transformed: " + rowsTransformed);
        System.out.println("Rows Skipped: " + rowsSkipped);
        System.out.println("Output file path written: " + outputPath);
    }
}
Clean Object-Oriented Architecture Example
After refactoring, your main method simply wires dependencies together and initiates the process:

Java
public class ETLPipeline {
    public static void main(String[] args) {
        File input = new File("data/employees.csv");
        File output = new File("data/transformed_employees.csv");

        EmployeeParser parser = new EmployeeParser();
        PipelineMetrics metrics = new PipelineMetrics();

        CsvETLService etlService = new CsvETLService(parser, metrics);
        etlService.process(input, output);
    }
}

ME: Can you show the best structure for this and explain why this is more efficient?

GEMINI: Here is the complete, production-ready structure refactored into distinct Object-Oriented classes, followed by an explanation of why this design is significantly more performant, maintainable, and robust.

Refactored OOP Architecture
1. The Domain Model (Employee.java)
Encapsulates employee data and core business domain logic (pay calculation, pay level classification, employment status).

Java
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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getHoursWorked() { return hoursWorked; }
    public double getHourlyRate() { return hourlyRate; }

    public double calculateGrossPay() {
        double baseHours = Math.min(hoursWorked, 40.0);
        double overtimeHours = Math.max(0.0, hoursWorked - 40.0);
        double grossPay = (baseHours * hourlyRate) + (overtimeHours * hourlyRate * 1.5);

        if ("IT".equalsIgnoreCase(department)) {
            grossPay *= 1.05;
        }

        return BigDecimal.valueOf(grossPay)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
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
        return String.format("%d,%s,%s,%.2f,%.2f,%.2f,%s,%s",
                id, name, department, hoursWorked, hourlyRate,
                calculateGrossPay(), getPayLevel(), getEmploymentStatus());
    }
}
2. The Data Parsing & Validation Service (EmployeeParser.java)
Isolates CSV validation, row parsing, and error-checking from business processing.

Java
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
3. Pipeline Telemetry (PipelineMetrics.java)
Encapsulates job counters and summary reporting.

Java
package org.howard.edu.lsp.assignment3;

public class PipelineMetrics {
    private int rowsRead = 0;
    private int rowsTransformed = 0;
    private int rowsSkipped = 0;

    public void incrementRead() { rowsRead++; }
    public void incrementTransformed() { rowsTransformed++; }
    public void incrementSkipped() { rowsSkipped++; }

    public void printSummary(String outputPath) {
        System.out.println("ETL processing complete.");
        System.out.println("Rows Read: " + rowsRead);
        System.out.println("Rows Transformed: " + rowsTransformed);
        System.out.println("Rows Skipped: " + rowsSkipped);
        System.out.println("Output file path written: " + outputPath);
    }
}
4. The Processor Component (EtlProcessor.java)
Orchestrates reading, parsing, transforming, and writing row-by-row using streaming without holding the entire dataset in memory.

Java
package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class EtlProcessor {
    private static final String CSV_HEADER = 
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

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

            // Skip header row in input file
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
5. Application Entrypoint (ETLPipeline.java)
Conducted purely as an orchestration layer.

Java
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
            System.err.println("Error processing the ETL file pipeline: " + e.getMessage());
        }
    }
}
Why This Structure is More Efficient & Effective
Aspect	Procedural Code (Original)	Object-Oriented Architecture (Refactored)
Memory Consumption	Single block holding mixed raw strings and primitive local variables inside stack frames throughout execution.	Stream-based Processing: Records are transformed row-by-row; garbage collection cleans up unreferenced objects per loop iteration O(1) memory usage.
Maintainability	Monolithic main method (~140 lines). Modifying overtime calculation or validation breaks surrounding I/O code.	Single Responsibility Principle (SRP): Each class has exactly one reason to change. Changing parsing rules only touches EmployeeParser.
Testability	Impossible to unit test business calculations or CSV parsing without loading actual physical files on disk.	Isolated Testing: Employee rules and EmployeeParser validation can be fully tested using unit tests without touching disk I/O.
Error Isolation	Failures in string parsing or number conversion are tangled with main stream management loops.	Clean boundary separation via Optional<Employee> handling invalid data gracefully without crashing execution state.
Extensibility	Hard-coded for single file inputs. Reading from database or API requires rewriting the logic.	Dependency injection ready: Swap BufferedReader with database iterators while reusing Employee and EmployeeParser.

ME: Can you explain what the 'private final' means?

GEMINI: In Java, using private final together when declaring instance variables (like private final int id; or private final String name;) combines two distinct features: access control and immutability.

Breakdown of the Keywords
1. private (Access Modifier)
What it means: The variable can only be accessed directly from within the class where it is defined. Outside classes cannot directly read or modify it.

Why use it: This enforces Encapsulation. By hiding the internal data, you prevent external code from corrupting the object's internal state. If outside classes need to read the value, you provide a public getter method (e.g., getId()).

2. final (Immutability Modifier)
What it means: The variable's reference or value can only be assigned once. After it is set (typically inside the constructor), it can never be changed or reassigned.

Why use it: This guarantees Immutability. Once an Employee object is created with an ID of 101, that ID remains 101 for the entire lifespan of that object.

How They Work Together in Practice
In our refactored Employee class:

Java
public class Employee {
    // 1. Fields declared private and final
    private final int id;
    private final String name;

    // 2. Assigned ONCE inside the constructor
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // 3. Exposed safely via getter (no setter exists!)
    public int getId() {
        return id;
    }
}
What private final prevents:
Java
Employee emp = new Employee(101, "Alice");

// ❌ COMPILE ERROR (due to 'private'):
// Cannot access directly from another class
emp.id = 102; 

// ❌ COMPILE ERROR (due to 'final'):
// Even inside the Employee class, reassignment is blocked!
public void updateId(int newId) {
    this.id = newId; // Error: Cannot assign a value to final variable 'id'
}
Core Benefits of private final
Thread Safety: Immutable objects are inherently thread-safe because multiple threads can read them simultaneously without worrying about one thread changing the data mid-execution.

Predictability: You never have to worry about subtle bugs where an object's identifier or core properties mysteriously change halfway through an ETL pipeline process.

Cleaner Design: It eliminates the need for setters (setId()), making the class easier to reason about.

ME: What about 'this'?

GEMINI: In Java, this is a keyword that refers to the current instance of the class—the specific object whose method or constructor is currently executing.

The 3 Main Uses of this
1. Distinguishing Fields from Parameters (Shadowing)
When a constructor or method parameter has the exact same name as an instance variable, the parameter "shadows" (hides) the instance variable. Using this explicitly points to the class's variable.

Java
public class Employee {
    private final int id; // Instance variable

    // Parameter is also named 'id'
    public Employee(int id) { 
        this.id = id; 
        // ^        ^
        // |        Refers to the parameter passed into constructor
        // Refers to the field belonging to THIS specific Employee object
    }
}
Without this.id = id;, writing id = id; would just assign the parameter to itself, leaving the instance variable uninitialized.

2. Passing the Current Object to Other Code
You can pass the current object instance to another class or method using this.

Java
public class EtlProcessor {
    public void process(Employee employee) { ... }
}

public class Employee {
    public void sendToProcessor(EtlProcessor processor) {
        // Passes THIS specific Employee instance into the processor
        processor.process(this); 
    }
}
3. Calling Another Constructor in the Same Class (Constructor Chaining)
You can use this(...) as a method call on the first line of a constructor to invoke another constructor in the same class.

Java
public class Employee {
    private final int id;
    private final String name;
    private final String department;

    // Secondary constructor with default department
    public Employee(int id, String name) {
        this(id, name, "Unassigned"); // Invokes the 3-parameter constructor below
    }

    // Main constructor
    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
}
Key Takeaway
Think of this as the object talking about itself. When new Employee(101, "Alice") runs, inside that object this literally translates to "my own instance properties and methods."


ME: Can you explain this line to me: public Optional<Employee> parseLine(String line)

GEMINI: This line is a method signature in Java. It defines how other parts of your code can call and interact with the parseLine function.

Here is the breakdown of each part from left to right:

Breakdown of Each Component
Java
public Optional<Employee> parseLine(String line)
Keyword / Element	Meaning
public	Access Level: Any class in your project can call this method.
Optional<Employee>	Return Type: Instead of returning an Employee directly (or returning null when parsing fails), it returns a container that may or may not contain an Employee.
parseLine	Method Name: The identifier used to call this action.
(String line)	Parameter: The input it expects—a single raw row/text string from your CSV file.
Why use Optional<Employee> instead of Employee?
In traditional Java, if a CSV line was corrupt or blank, you might return null:

Java
// Traditional approach (Risk of NullPointerException)
public Employee parseLine(String line) {
    if (invalidLine) return null; // ⚠️ DANGER
    return new Employee(...);
}
If the caller forgets to check if (employee == null), the app crashes with a NullPointerException.

Using Optional forces the caller to explicitly handle both success and failure cases:

Java
Optional<Employee> result = parser.parseLine("101, Alice, IT, 40, 25.0");

if (result.isPresent()) {
    Employee emp = result.get(); // Safely retrieve the employee
} else {
    // Row was bad/skipped - handle safely without crashing
}
Think of Optional<Employee> as a box:

Valid CSV row: The box arrives containing an Employee object (Optional.of(employee)).

Invalid or blank row: The box arrives empty (Optional.empty()), signaling to skip the row without throwing errors.

Can you remind me what 'catch' does?

In Java, catch is used alongside try to catch and handle errors (exceptions) so your program doesn't crash when something goes wrong.

Think of it like a safety net:

try contains code that might throw an error (like reading a missing file or parsing a non-numeric string).

catch intercepts that specific error and defines what to do instead.

How It Works
Java
try {
    // Code that might fail
    int id = Integer.parseInt("abc"); // 💥 Throws NumberFormatException!
} catch (NumberFormatException e) {
    // Code that runs ONLY if that specific error happens
    System.out.println("Invalid ID format! Skipping row...");
}
In Your ETL Pipeline Code
In your parser and pipeline, catch keeps bad data from crashing the whole process:

Java
try {
    employeeId = Integer.parseInt(rawEmpId);
    hoursWorked = Double.parseDouble(rawHours);
    hourlyRate = Double.parseDouble(rawRate);
} catch (NumberFormatException e) {
    // If a row has text like "N/A" instead of a number, 
    // catch intercepts it, skips the row, and keeps the program running.
    rowsSkipped++;
    continue;
}
Without catch, processing a single corrupt row in a CSV file with 10,000 employees would crash the entire application instantly.

Key Takeaways
Prevents Crashes: Allows the application to handle bad input or network/disk errors gracefully.

Error Object (e): The variable (like NumberFormatException e) holds details about what went wrong, such as e.getMessage().

Multiple Catches: You can chain multiple catch blocks after one try to handle different types of errors (e.g., catching FileNotFoundException separately from IOException).

ME: what makes using object oriented principles better than my original program?

GEMINI: Here is a direct comparison showing why transitioning your original procedural script into an Object-Oriented structure transformed the code from a fragile utility into a scalable application.

1. Clear Separation of Concerns (Single Responsibility Principle)
Original: Your main method was trying to do everything at once—parsing strings, validating numeric constraints, applying business math, keeping counters, and handling file I/O within a single 140-line loop.

OOP Version: Each class has one job:

Employee: Handles business rules and pay logic.

EmployeeParser: Converts text to objects and validates raw input.

PipelineMetrics: Tracks execution statistics.

EtlProcessor: Handles reading/writing flow.

Why it matters: If the company changes its overtime rate or IT bonus policy, you edit only Employee.java. You never risk breaking file I/O or line-skipping logic because they are completely isolated.

2. Real Unit Testability
Original: To test if your overtime calculation worked correctly for 45 hours, you had to physically edit data/employees.csv on your hard drive, run the entire program, and open data/transformed_employees.csv to inspect the text output.

OOP Version: You can write automated tests directly against Employee methods without touching the disk or reading files:

Java
@Test
void testOvertimeCalculation() {
    Employee emp = new Employee(1, "Alice", "Sales", 45.0, 20.0);
    // 40 hrs * $20 + 5 hrs * $30 = $950
    assertEquals(950.00, emp.calculateGrossPay());
}
3. High Reusability & Flexibility
Original: The logic was tightly coupled to CSV input files and CSV output files via BufferedReader and FileWriter.

OOP Version: The Employee domain logic and EmployeeParser don't care where the raw text comes from. If your assignment later asks you to process data from a database, a REST API, or a JSON file, you reuse 100% of your business calculations and validation logic without rewriting them.

4. Crash Prevention & Safe Failure Handling
Original: Returning raw primitive values across procedural loops relies on local variables staying synchronized. If bad data made it past an if check, an exception could crash the entire job mid-stream.

OOP Version: Using constructs like Optional<Employee> guarantees that invalid data is caught at the boundary. An incomplete or malformed record simply produces an Optional.empty(), making it impossible for unvalidated data to leak into calculation methods.

5. Low Cognitive Load & Readable Code
Original: Reading the nested if-else blocks inside your while loop required tracking 12+ variables at once (rawEmpId, hoursWorked, grossPay, payLevel, rowsSkipped, etc.) across 100 lines.

OOP Version: Reading processor.process(inputFile, outputFile) communicates high-level intent instantly. If you need details on how pay level is decided, you open Employee.getPayLevel()—five lines of clear logic in one place.

Summary Comparison
Metric	Original Procedural Code	Refactored OOP Code
Where logic lives	1 monolithic method	Self-contained objects
Bugs during updates	High risk (changes cascade)	Low risk (isolated scope)
Testing method	Manual file inspection	Automated unit testing
Data integrity	Guarded by scattered if statements	Guaranteed by private final immutability




Internet Resources:
	https://www.geeksforgeeks.org/system-design/oops-object-oriented-design/ 