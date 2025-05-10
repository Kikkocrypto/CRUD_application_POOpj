package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Data.DataManager;
import Model.Grade;
import Model.Student;
import Resources.Validation;
import Service.GradeService;
import Service.StudentService;

public class StudentController {
    private StudentService studentService;
    private GradeService gradeService;
    private Scanner sc = new Scanner(System.in);

    public StudentController(Scanner sc, StudentService studentService, GradeService gradeService) {
	this.gradeService = gradeService;
	this.studentService = studentService;
	this.sc = sc;
    }

    public StudentController() {
    }

    public void start() {
	loadAll();

	boolean back = false;
	while (!back) {
	    System.out.println("\n------ STUDENT MENU ------");
	    System.out.println("1. Add student:");
	    System.out.println("2. view all the students:");
	    System.out.println("3. Delete student:");
	    System.out.println("4. Modify student:");
	    System.out.println("-------------------------");
	    System.out.println("5. SAVE AND EXIT.");
	    System.out.println("-------------------------");

	    String choice = sc.nextLine();

	    switch (choice) {
	    case "1":
		addStudent();
		break;
	    case "2":
		listStudents();
		break;
	    case "3":
		deleteStudent();
		break;
	    case "4":
		modifyStudent();
		break;
	    case "5":
		saveAll();
		back = true;
		break;
	    default:
		System.out.println("Not valid choice");
	    }
	}

    }

    /////////////// INSERT (student)///////////////
    private void addStudent() {

	try {
	    System.out.println("\n  0. Cancel and return to menu  \n)");

	    System.out.println("\n---- You're inserting a student now ----");
	    // --- USER FIRST NAME ---
	    System.out.println("Insert student Name: ");
	    String firstName = sc.nextLine();
	    if (firstName.equals("0"))
		return;
	    // Validation control on empty fields or numeric input
	    if (Validation.isNullOrEmpty(firstName)) {
		System.out.println("ERROR: Empty field");
		return;
	    }
	    if (!Validation.isLettersOnly(firstName)) {
		System.out.println("ERROR: Invalid first name, don't use numeric values");
		return;
	    }
	    if (!Validation.isValidName(firstName)) {
		System.out.println("ERROR: Invalid first name, Characters less than 2 or more than 50");
		return;
	    }

	    // --- STUDENT LAST NAME ---
	    System.out.println("Insert student last name: ");
	    String lastName = sc.nextLine();
	    if (lastName.equals("0"))
		return;
	    // Validation control on empty fields or numeric input
	    if (Validation.isNullOrEmpty(lastName)) {
		System.out.println("ERROR: Empty field");
		return;
	    }
	    if (!Validation.isLettersOnly(lastName)) {
		System.out.println("ERROR: Invalid last name, don't use numeric values");
		return;
	    }
	    if (!Validation.isValidName(lastName)) {
		System.out.println("ERROR: Invalid last name, Characters less than 2 or more than 50");
		return;
	    }

	    // --- EMAIL ---
	    System.out.println("Insert student email: ");
	    String email = sc.nextLine();
	    if (email.equals("0"))
		return;
	    // Validation control on email (should have '@' and '.')
	    if (!Validation.isValidEmail(email)) {
		System.out.println("ERROR: Insert a valid Email!");
		return;
	    }
	    if (Validation.isNullOrEmpty(email)) {
		System.out.println("ERROR: Empty field!");
		return;
	    }

	    // --- ACADEMIC GROUP ---
	    System.out.println("Insert an academic group name: ");
	    String academicGroup = sc.nextLine();
	    if (academicGroup.equals("0"))
		return;
	    // Validation control on empty fields
	    if (Validation.isNullOrEmpty(academicGroup)) {
		System.out.println("ERROR: Invalid student academic group! Empty");
		return;
	    }
	    // Validation on input length
	    if (!Validation.isMoreThanTwoLessThanTen(academicGroup)) {
		System.out.println("ERROR: Invalid student academic group, it mus be >= 2 or <= 10");
		return;
	    }

	    // INSERT STUDENT
	    Student s = new Student(firstName, lastName, email, academicGroup);
	    studentService.AddStudent(s);
	    studentService.getAllStudents();
	    System.out.println("------ Student successfully addded ------");
	} catch (Exception e) {
	    System.err.println("Error, cannot insert the student: " + e.getMessage());
	}
    }

    /////////////// READ (student)///////////////
    private void listStudents() {
	if (studentService.getAllStudents().isEmpty()) {
	    System.out.println("No available students, click 1 to add.");
	} else
	    studentService.getAllStudents().forEach(System.out::println);
    }

    /////////////// UPDATE (student)///////////////
    private void modifyStudent() {

	try {
	    Student selected = selectStudentFromList();
	    if (selected == null) {
		System.out.println("Student not found.");
		return;
	    }

	    // UPDATE FIRST NAME //
	    System.out.println("Leave blank to keep current value.");
	    System.out.println("-------------------------");
	    System.out.println("Current first name: " + selected.getFirstName());
	    System.out.println("New First name:");
	    String newFirstName = sc.nextLine();
	    if (!newFirstName.isBlank()) {
		if (!Validation.isLettersOnly(newFirstName)) {
		    System.out.println("ERROR: Invalid first name, don't use numeric values");
		    return;
		}
		if (!Validation.isValidName(newFirstName)) {
		    System.out.println("ERROR: Invalid first name, Characters less than 2 or more than 50");
		    return;
		}

		selected.setFirstName(newFirstName);
	    }
	    else {
		System.out.println("No changes to the first name.");
	    }

	    // UPDATE LAST NAME //
	    System.out.println("-------------------------");
	    System.out.println("Current last name: " + selected.getLastName());
	    System.out.print("New last name: ");
	    String newLast = sc.nextLine();
	    if (!newLast.isBlank()) {
		if (!Validation.isLettersOnly(newLast)) {
		    System.out.println("ERROR: Invalid last name, don't use numeric values");
		    return;
		}
		if (!Validation.isValidName(newLast)) {
		    System.out.println("ERROR: Invalid last name, characters must be between 2 and 50");
		    return;
		}

		selected.setLastName(newLast);
	    } else {
		System.out.println("No changes to last name.");
	    }

	    // UPDATE EMAIL NAME //
	    System.out.println("-------------------------");
	    System.out.println("Current email: " + selected.getEmail());
	    System.out.print("New email: ");
	    String newEmail = sc.nextLine();
	    if (!newEmail.isBlank()) {
		if (!Validation.isValidEmail(newEmail)) {
		    System.out.println("ERROR: Insert a valid Email!");
		    return;
		}

		selected.setEmail(newEmail);
	    } else {
		System.out.println("No changes to the email.");
	    }

	    // UPDATE ACADEMIC GROUP //
	    System.out.println("-------------------------");
	    System.out.println("Current group: " + selected.getAcademicGroup());
	    System.out.print("New group: ");
	    String newGroup = sc.nextLine();
	    if (!newGroup.isBlank()) {

		// Validation on input length
		if (!Validation.isMoreThanTwoLessThanTen(newGroup)) {
		    System.out.println("ERROR: Invalid student academic group, it mus be >= 2 or <= 10");
		    return;
		}
		selected.setAcademicGroup(newGroup);
	    } else {
		System.out.println("No changes to the academic group.");
	    }

	    System.out.println("Student successfully updated!");
	} catch (Exception e) {
	    System.err.println("Error updating the student" + e.getMessage());
	}
    }

    /////////////// DELETE (student)///////////////
    private void deleteStudent() {
	Student selected = selectStudentFromList();
	try {
	    if (selected != null) {
		UUID studentId = selected.getStudent_id();

		studentService.deleteStudentById(studentId.toString());

		// I used this to remove grades after the student delete
		List<Grade> gradesToRemove = gradeService.getGradesByStudent(studentId);
		for (Grade g : gradesToRemove) {
		    gradeService.deleteGradeById(g.getGrade_id().toString());
		}
		System.out.println("Student and related grades deleted.");
	    } else
		System.out.println("Error while deleting student.");
	} catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	}
    }

    // I used this function to get Student id (not the UUID) //
    Student selectStudentFromList() {
	System.out.println("\n  0. Cancel and return to menu  \n");
	List<Student> students = new ArrayList<>(studentService.getAllStudents());

	if (students.isEmpty()) {
	    System.out.println("No students available, add one!");
	    return null;
	}

	System.out.println("------------STUDENTS LIST-------------");
	for (int i = 0; i < students.size(); i++) {
	    Student s = students.get(i);
	    System.out.println((i + 1) + ". " + s.getFirstName() + " " + s.getLastName() + " - " + s.getEmail());
	}
	System.out.println("\n----------SELECT A STUDENT BY NUMBER----------");
	try {
	    int choice = Integer.parseInt(sc.nextLine());

	    if (choice == 0) {
		System.out.println("Operation cancelled.");
		return null;
	    }

	    if (choice < 1 || choice > students.size()) {
		System.out.println("Invalid selection.");
		return null;
	    }
	    return students.get(choice - 1);
	} catch (NumberFormatException e) {
	    System.out.println("Invalid input.");
	    return null;
	}
    }

    // SAVE STUDENT INTO A JSON FILE //
    private void saveAll() {
	try {
	    DataManager.save("students.json", studentService.getAllStudents());
	    System.out.println("Data successfully saved!");
	} catch (Exception e) {
	    System.err.println("Error during data saving: " + e.getMessage());
	}
    }

    // GET ALL DATA FROM JSON FILE //
    public void loadAll() {
	try {
	    List<Student> students = DataManager.load("students.json", Student[].class);
	    studentService.setAllStudents(students);
	} catch (Exception e) {
	    System.out.println("No File.");
	}
    }
}
