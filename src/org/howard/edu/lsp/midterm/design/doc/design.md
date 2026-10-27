Class: Student
Responsibilities:
- Store and manage student demographic information (ID, name, email).
- Validate student identity attributes.
Collaborators:
- None


Class: Course
Responsibilities:
- Store course catalog details (course code, credit hours).
- Provide course metadata to other domain classes.
Collaborators:
- None


Class: Registration
Responsibilities:
- Represent the enrollment relationship between a specific Student and a Course.
- Maintain registration state (e.g., calculated tuition fee, timestamp).
Collaborators:
- Student
- Course


Class: TuitionCalculator
Responsibilities:
- Compute base tuition fees based on credit hours.
- Apply business discount rules (e.g., heavy course load discounts for >= 12 credits).
Collaborators:
- Course
- Registration


Class: RegistrationRepository (Interface)
Responsibilities:
- Define the contract for persisting and retrieving registration records.
Collaborators:
- Registration


Class: NotificationService (Interface)
Responsibilities:
- Define the contract for sending confirmation messages to students.
Collaborators:
- Student
- Registration


Class: RegistrationController (or RegistrationService)
Responsibilities:
- Coordinate the end-to-end registration workflow (orchestrating domain logic, persistence, and notifications).
Collaborators:
- Student
- Course
- Registration
- TuitionCalculator
- RegistrationRepository
- NotificationService