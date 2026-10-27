Conversation with Gemini:

ME: Can you help me refactor this design and give detailed explanations of the solution: [Question 3 decription and source code]

GEMINI: Here is the complete, production-ready refactored design using inheritance, method overriding, and polymorphism.

In accordance with your requirements, all classes belong to the package org.howard.edu.lsp.midterm.inheritance, each resides in its own .java file, and proper Javadoc comments are included.

Part 1 – Refactored Implementation Classes
1. [Employee.java code]

2. [FullTimeEmployee.java code]

3. [PartTimeEmployee.java code]

4. [Contractor.java code]

Part 2 – Detailed Design Explanation
1. Removal of Conditional Logic & Violation of Open-Closed Principle
The original EmployeePayCalculator class used a brittle if-else chain checking strings ("FULL_TIME", "PART_TIME", "CONTRACTOR"). Every time a new employee type or pay rule was added, you would have to modify the existing class—violating the Open-Closed Principle (OCP).

2. Inheritance & State Encapsulation
Abstract Base Class (Employee): We created an abstract parent class that centralizes shared attributes (hoursWorked and hourlyRate) and enforces constructor reuse using super(...). This eliminates code duplication across subclasses.

Abstract Method (calculatePay()): By declaring public abstract double calculatePay();, we guarantee that every subclass must supply its own custom implementation of the pay algorithm.

3. Polymorphism in Action
Polymorphism allows the provided Driver to treat instances of FullTimeEmployee, PartTimeEmployee, and Contractor uniformly as Employee references (e.g., test(..., new FullTimeEmployee(...), ...)). When employee.calculatePay() is invoked in the driver loop, Java's dynamic method dispatch automatically executes the correct overridden version of calculatePay() specific to that runtime object type, completely eliminating type-checking conditionals.

4. Specific Business Logic Implementation
FullTimeEmployee: Evaluates whether hoursWorked exceeds 40. If so, standard pay is computed for the first 40 hours, and overtime hours are compensated at 
1.5×hourlyRate.

PartTimeEmployee: Applies straight hourly pay (hoursWorked * hourlyRate) regardless of whether hours cross the 40-hour threshold.

Contractor: Multiplies standard earnings (hoursWorked * hourlyRate) by 1.15 to apply the required 15% premium.