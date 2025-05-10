package Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Data.DataManager;
import Model.Grade;
import Model.Student;
import Model.Subject;
import Resources.ExportToCsv;
import Service.GradeService;
import Service.StudentService;
import Service.SubjectService;

public class GradeController {
    private GradeService gradeService = new GradeService();

    // I used it for dependency injection
    private StudentController stController;
    private StudentService studentService;

    private SubjectController subController;
    private SubjectService subjectService;

    private Scanner sc;

    public GradeController(GradeService gradeService, StudentService studentService, SubjectService subjectService,
	    Scanner sc) {
	this.gradeService = gradeService;
	this.studentService = studentService;
	this.subjectService = subjectService;
	this.sc = sc;
	this.stController = new StudentController(sc, studentService, gradeService);
	this.subController = new SubjectController(sc, subjectService, gradeService);
    }

    public void start() {
	stController.loadAll();
	subController.loadAll();
	loadAll();

	boolean back = false;
	while (!back) {
	    System.out.println("\n------ GRADE MENU ------");
	    System.out.println("1. Add grade");
	    System.out.println("2. view all the grades");
	    System.out.println("3. Delete grade");
	    System.out.println("4. Modify grade");
	    System.out.println("\n------ STUDENTS STATUS ------");
	    System.out.println("5. View students academic status");
	    System.out.println("6. View academic status by student");
	    System.out.println("7. View academic status by subject");
	    System.out.println("\n------ EXPORT TO CSV ------");
	    System.out.println("8. Export Gradebook");
	    System.out.println("-------------------------");
	    System.out.println("9. SAVE AND EXIT.");
	    System.out.println("-------------------------");

	    String choice = sc.nextLine();

	    switch (choice) {
	    case "1":
		addGrade();
		break;
	    case "2":
		listGrades();
		break;
	    case "3":
		deleteGrade();
		break;
	    case "4":
		modifyGrade();
		break;
	    case "5":
		showAllStudentStatus();
		break;
	    case "6":
		showSingleStudentStatus();
		break;
	    case "7":
		showSingleSubjectStatus();
		break;
	    case "8":
		exportGradebook();
		break;
	    case "9":
		saveAll();
		back = true;
		break;
	    default:
		System.out.println("Not valid choice");
	    }
	}

    }

    /////////////// INSERT (grade)///////////////
    private void addGrade() {
	Student student = stController.selectStudentFromList();

	if (student == null)
	    return;

	Subject subject = subController.selectSubjectFromList();
	if (subject == null)
	    return;

	System.out.println("\n--------------------");
	System.out.println("Enter grade (0-30), or 'x' to cancel: ");
	String input = sc.nextLine();
	if (input.equalsIgnoreCase("x"))
	    return;

	try {
	    int value = Integer.parseInt(input);
	    if (value < 0 || value > 30) {
		System.out.println("Invalid grade value.");
		return;
	    }

	    Grade g = new Grade(student.getStudent_id(), subject.getSubject_id(), value);
	    gradeService.addGrade(g);
	    System.out.println("Grade successfully recorded!");
	} catch (NumberFormatException e) {
	    System.out.println("Invalid input. Grade not recorded.");
	}
    }

    /////////////// READ (grades)///////////////
    private void listGrades() {
	List<Grade> grades = gradeService.getAllGrades();
	List<Student> students = studentService.getAllStudents();
	List<Subject> subjects = subjectService.getAllSubjects();

	if (grades.isEmpty()) {
	    System.out.println("No available grades, click 1 to add.");
	    return;
	} else {
	    System.out.println("\n------ GRADE LIST ------");
	    // Used to get the name of the student
	    for (Grade g : grades) {
		Student student = students.stream().filter(s -> s.getStudent_id().equals(g.getStudent_id())).findFirst()
			.orElse(null);

		// Used to get the name of the subject
		Subject subject = subjects.stream().filter(sub -> sub.getSubject_id().equals(g.getSubject_id()))
			.findFirst().orElse(null);

		if (student == null || subject == null)
		    continue;

		String studentName = (student != null) ? student.getFirstName() + " " + student.getLastName()
			: "Unknown Student";
		String subjectName = (subject != null) ? subject.getSubjectName() : "Unknown subject";

		String passFail = (g.getGrade() <= 18) ? "FAIL" : "PASS";

		System.out.printf("Grade: %d | Student: %s | Subject: %s | Date: %s | result: %s%n", g.getGrade(),
			studentName, subjectName, g.getGradingDate(), passFail);
	    }
	}
    }

    /////////////// UPDATE (grades)///////////////

    private void modifyGrade() {
	try {
	    Grade selected = selectGradeFromList();
	    if (selected == null) {
		System.out.println("Grade not found or cancelled.");
		return;
	    }

	    System.out.println("Current grade value: " + selected.getGrade());
	    System.out.print("New grade (0–30), leave blank to keep current: ");
	    String input = sc.nextLine();

	    if (!input.isBlank()) {
		int newGrade = Integer.parseInt(input);
		if (newGrade < 0 || newGrade > 30) {
		    System.out.println("Invalid grade value.");
		    return;
		}
		selected.setGrade(newGrade);
		selected.setGradingDate(LocalDate.now()); // aggiorna la data
		System.out.println("Grade successfully updated!");
	    } else {
		System.out.println("No changes made.");
	    }

	} catch (NumberFormatException e) {
	    System.out.println("Invalid input. Grade must be a number.");
	} catch (Exception e) {
	    System.err.println("Error updating grade: " + e.getMessage());
	}
    }

    /////////////// DELETE (grades)///////////////

    private void deleteGrade() {
	Grade selected = selectGradeFromList();
	if (selected != null) {
	    gradeService.deleteGradeById(selected.getGrade_id().toString());
	    System.out.println("Grade deleted.");
	} else
	    System.out.println("Error while deleting grade.");
    }

    /// Function to get gradesID
    Grade selectGradeFromList() {
	System.out.println("\n  0. Cancel and return to menu  \n");
	List<Grade> grades = new ArrayList<>(gradeService.getAllGrades());
	List<Student> students = new ArrayList<>(studentService.getAllStudents());
	List<Subject> subjects = new ArrayList<>(subjectService.getAllSubjects());

	if (grades.isEmpty()) {
	    System.out.println("No grades available, add one!");
	    return null;
	}

	for (int i = 0; i < grades.size(); i++) {
	    Grade g = grades.get(i);

	    // I'm Getting the student id using a stream
	    Student student = students.stream().filter(s -> s.getStudent_id().equals(g.getStudent_id())).findFirst()
		    .orElse(null);

	    // I'm Getting the subject id using a stream
	    Subject subject = subjects.stream().filter(s -> s.getSubject_id().equals(g.getSubject_id())).findFirst()
		    .orElse(null);

	    // In this case I''ve assigned Student/Subject data to a variable, to be able to
	    // print every info about them.
	    String studentInfo = (student != null) ? student.getFirstName() + " " + student.getLastName()
		    : "Unknown Student";
	    String subjectInfo = (subject != null) ? subject.getSubjectName() : "Unknown Subject";

	    // Print everything
	    System.out.println((i + 1) + ". " + g.getGrade() + " | " + studentInfo + " | " + subjectInfo + " | "
		    + g.getGradingDate());

	}

	System.out.print("Select a grade by number: ");
	try {
	    int choice = Integer.parseInt(sc.nextLine());

	    if (choice == 0) {
		System.out.println("Operation cancelled.");
		return null;
	    }

	    if (choice < 1 || choice > grades.size()) {
		System.out.println("Invalid selection.");
		return null;
	    }
	    return grades.get(choice - 1);
	} catch (NumberFormatException e) {
	    System.out.println("Invalid input.");
	    return null;
	}
    }

    // Function used to print the student status
    private String getAcademicStatus(double average) {
	if (average >= 28 && average <= 30)
	    return "Excellent";
	if (average >= 24)
	    return "Very Good";
	if (average >= 18)
	    return "You did it! (maybe)";
	return "You should study more!";
    }

    // SHOW THE STUDENTS TOTAL AVERAGE/STATUS
    private void showAllStudentStatus() {
	List<Student> students = studentService.getAllStudents();

	System.out.println("\n---- ACADEMIC STUDENT STATUS ----");
	for (Student s : students) {
	    List<Grade> grades = gradeService.getGradesByStudent(s.getStudent_id());
	    if (grades.isEmpty())
		continue;

	    double avg = grades.stream().mapToInt(Grade::getGrade).average().orElse(0);

	    String status = getAcademicStatus(avg);
	    System.out.printf("\n%s %s | Average: %.2f | Status: %s", s.getFirstName(), s.getLastName(), avg, status);
	}
    }

    // SHOW THE SINGLE STUDENT TOTAL AVERAGE/STATUS
    private void showSingleStudentStatus() {
	System.out.println("Select a student");
	Student selected = stController.selectStudentFromList();

	if (selected == null) {
	    System.out.println("No student selected");
	    return;
	}

	// Get all the grades of the student
	List<Grade> grades = gradeService.getGradesByStudent(selected.getStudent_id());
	if (grades.isEmpty()) {
	    System.out.println("No grades available for this student.");
	    return;
	}

	// I used this Stream to get the grades and calculate the average.
	double avg = grades.stream().mapToInt(Grade::getGrade).average().orElse(0);
	String status = getAcademicStatus(avg);

	System.out.printf("\n%s %s | Average: %.2f | status: %s%n", selected.getFirstName(), selected.getLastName(),
		avg, status);
    }

    // SHOW THE SUBJECT TOTAL AVERAGE/STATUS
    private void showSingleSubjectStatus() {
	System.out.println("Select a subject");
	Subject selected = subController.selectSubjectFromList();

	if (selected == null) {
	    System.out.println("No subject selected");
	    return;
	}

	List<Grade> grades = gradeService.getGradesBySubject(selected.getSubject_id());
	if (grades.isEmpty()) {
	    System.out.println("No grades available for this subject!");
	    return;
	}

	double avg = grades.stream().mapToInt(Grade::getGrade).average().orElse(0);

	System.out.printf("%s | %.2f", selected.getSubjectName(), avg);

    }

    // SAVE GRADE INTO A JSON FILE //
    private void saveAll() {
	try {
	    // With this I'm removing every grade 'chained' to subjects or students that
	    // have been removed
	    List<Grade> validGrades = gradeService.getAllGrades().stream()
		    .filter(g -> studentService.getAllStudents().stream()
			    .anyMatch(s -> s.getStudent_id().equals(g.getStudent_id())))
		    .filter(g -> subjectService.getAllSubjects().stream()
			    .anyMatch(sub -> sub.getSubject_id().equals(g.getSubject_id())))
		    .toList();

	    gradeService.setAllGrades(new ArrayList<>(validGrades));
	    DataManager.save("grades.json", validGrades);
	    System.out.println("Data successfully saved!");
	} catch (Exception e) {
	    System.err.println("Error during data saving: " + e.getMessage());
	}
    }

// GET ALL DATA FROM JSON FILE //
    private void loadAll() {
	try {
	    List<Grade> grades = DataManager.load("grades.json", Grade[].class);
	    gradeService.setAllGrades(grades);
	} catch (Exception e) {
	    System.out.println("Error loading grades: " + e.getMessage());
	    e.printStackTrace();
	}
    }

    //////////////////////////// EXPORT FUNCTIONS ////////////////////////////
    // METHOD USED TO EXPORT THE WHOLE GRADEBOOK
    private void exportGradebook() {
	List<Grade> grades = gradeService.getAllGrades();
	List<Student> students = studentService.getAllStudents();
	List<Subject> subjects = subjectService.getAllSubjects();

	List<String[]> rows = new ArrayList<>();
	rows.add(new String[] { "Student", "Subject", "Grade", "Date" });

	for (Grade g : grades) {
	    Student student = students.stream().filter(s -> s.getStudent_id().equals(g.getStudent_id())).findFirst()
		    .orElse(null);

	    Subject subject = subjects.stream().filter(s -> s.getSubject_id().equals(g.getSubject_id())).findFirst()
		    .orElse(null);

	    String studentName = (student != null) ? student.getFirstName() + " " + student.getLastName()
		    : "Unknown Student";
	    String subjectName = (subject != null) ? subject.getSubjectName() : "Unknown Subject";

	    rows.add(new String[] { studentName, subjectName, String.valueOf(g.getGrade()),
		    g.getGradingDate().toString() });
	}

	ExportToCsv.exportToCsvFile("gradebook.csv", rows);
    }
}
