package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Data.DataManager;
import Model.EvaluationTypeEnum;
import Model.Grade;
import Model.Subject;
import Resources.Validation;
import Service.GradeService;
import Service.SubjectService;

public class SubjectController {
    private Scanner sc = new Scanner(System.in);
    private SubjectService subService;
    private GradeService gradeService;

    public SubjectController(Scanner sc, SubjectService subService, GradeService gradeService) {
	this.gradeService = gradeService;
	this.subService = subService;
	this.sc = sc;

    }

    public SubjectController() {
    }

    public void start() {
	loadAll();

	boolean back = false;
	while (!back) {
	    System.out.println("\n------ SUBJECT MENU ------");
	    System.out.println("1. Add subject:");
	    System.out.println("2. view all the subjects:");
	    System.out.println("3. Delete subject:");
	    System.out.println("4. Modify subject:");
	    System.out.println("-------------------------");
	    System.out.println("5. SAVE AND EXIT.");
	    System.out.println("-------------------------");

	    String choice = sc.nextLine();

	    switch (choice) {
	    case "1":
		addSubject();
		break;
	    case "2":
		listSubject();
		break;
	    case "3":
		deleteSubject();
		break;
	    case "4":
		modifySubject();
		break;
	    case "5":
		saveAll();
		back = true;
		break;
	    default:
		System.out.println("Not valid choice!");
	    }

	}
    }

    /////////////// READ (subject)///////////////
    private void listSubject() {
	if (subService.getAllSubjects().isEmpty()) {
	    System.out.println("No available subject, click 1 to add.");
	} else
	    subService.getAllSubjects().forEach(System.out::println);
    }

/////////////// INSERT (subject)///////////////
    private void addSubject() {
	EvaluationTypeEnum evaluationType = null;
	try {
	    System.out.println("---- You're inserting a subject now ----");
	    System.out.println("  0. Cancel and return to menu  ");

	    // --- SUBJECT NAME ---
	    System.out.println("Insert subject name: ");
	    String subjectName = sc.nextLine();
	    // return to the main menu
	    if (subjectName.equals("0"))
		return;
	    // Validation control on empty fields or numeric input
	    if (Validation.isNullOrEmpty(subjectName)) {
		System.out.println("ERROR: Empty field");
		return;
	    }
	    if (!Validation.isLettersOnly(subjectName)) {
		System.out.println("ERROR: Invalid subject name, don't use numeric values");
		return;
	    }
	    if (!Validation.isValidName(subjectName)) {
		System.out.println("ERROR: Invalid subject name, Characters less than 2 or more than 50");
		return;
	    }

	    // --- SUBJECT ACRONYM ---
	    System.out.println("Insert subject acronym: ");
	    String subjectAcronym = sc.nextLine();
	    if (subjectAcronym.equals("0"))
		return;
	    // Validation control on empty fields
	    if (Validation.isNullOrEmpty(subjectAcronym)) {
		System.out.println("ERROR: Invalid subject Acronym! Empty");
		return;
	    }
	    // Validation on input length
	    if (!Validation.isMoreThanTwoLessThanTen(subjectAcronym)) {
		System.out.println("ERROR: Invalid subject Acronym, it mus be >= 2 or <= 10");
		return;
	    }

	    // EVALUATION ENUM SETTER
	    System.out.println("Select an Evaluation Type: \n EXAM = 1, COLLOQUIUM = 2");
	    int choice = sc.nextInt();
	    switch (choice) {
	    case 1:
		evaluationType = EvaluationTypeEnum.EXAM;
		evaluationType.toString();
		break;
	    case 2:
		evaluationType = EvaluationTypeEnum.COLLOQUIUM;
		evaluationType.toString();
		break;
	    default:
		System.out.println("Not valid choice!");
		return;
	    }

	    Subject s = new Subject(subjectName, subjectAcronym, evaluationType);
	    subService.AddSubject(s);
	    System.out.println("------ subject successfully addded ------");
	    listSubject();
	} catch (Exception e) {
	    System.err.println("Error, cannot insert the subject: " + e.getMessage());
	}
    }

/////////////// UPDATE (subject)///////////////
    private void modifySubject() {
	EvaluationTypeEnum evaluationType = null;
	try {
	    Subject selected = selectSubjectFromList();
	    if (selected == null) {
		System.out.println("Subject not found");
		return;
	    }

	    // UPDATE SUBJECT NAME //
	    System.out.println("-------------------------");
	    System.out.println("Leave blank to keep current value.");
	    System.out.println("Current subject name: " + selected.getSubjectName());
	    System.out.println("New subject name:");
	    String newSubjectName = sc.nextLine();
	    if (!newSubjectName.isBlank()) {
		// Validation control on empty fields or numeric input
		if (!Validation.isLettersOnly(newSubjectName)) {
		    System.out.println("ERROR: Invalid subject name, don't use numeric values");
		    return;
		}
		if (!Validation.isValidName(newSubjectName)) {
		    System.out.println("ERROR: Invalid subject name, Characters less than 2 or more than 50");
		    return;
		}

		selected.setSubjectName(newSubjectName);
	    } else
		System.out.println("No changes to the subject name.");

	    // UPDATE ACRONYM //
	    System.out.println("-------------------------");
	    System.out.println("Current Acronym: " + selected.getAcronym());
	    System.out.print("New Acronym: ");
	    String newAcronym = sc.nextLine();
	    if (!newAcronym.isBlank()) {

		// Validation on input length
		if (!Validation.isMoreThanTwoLessThanTen(newAcronym)) {
		    System.out.println("ERROR: Invalid subject Acronym, it mus be >= 2 or <= 10");
		    return;
		}
		selected.setAcronym(newAcronym);
	    } else
		System.out.println("No changes to the subject acronym.");

	    // UPDATE EVALUATION TYPE //
	    System.out.println("-------------------------");
	    System.out.println("Current Evaluation Type selected: " + selected.getEvaluationType().toString());
	    System.out.println("--Select an Evaluation Type: EXAM = 1, COLLOQUIUM = 2");
	    System.out.println("Select a new Evaluation Type: ");
	    int choice = sc.nextInt();
	    switch (choice) {
	    case 1:
		evaluationType = EvaluationTypeEnum.EXAM;
		selected.setEvaluationType(evaluationType);
		break;
	    case 2:
		evaluationType = EvaluationTypeEnum.COLLOQUIUM;
		selected.setEvaluationType(evaluationType);
		break;
	    default:
		System.out.println("Not valid choice!");
	    }

	    System.out.println("Subject successfully updated!");

	} catch (Exception e) {
	    System.err.println("Error updating the student" + e.getMessage());
	}
    }

/////////////// DELETE (subject)///////////////
    private void deleteSubject() {
	Subject selected = selectSubjectFromList();

	if (selected != null) {
	    UUID subjectId = selected.getSubject_id();
	    subService.deleteSubjectById(selected.getSubject_id().toString());

	    // I used this to remove grades after the subject delete
	    List<Grade> gradesToRemove = gradeService.getGradesBySubject(subjectId);
	    for (Grade g : gradesToRemove) {
		gradeService.deleteGradeById(g.getGrade_id().toString());
	    }
	    System.out.println("Subject and relatives grades deleted.");
	} else
	    System.out.println("Error while deleting subject.");
    }

    // I used this function to get Student id (not the UUID) //
    Subject selectSubjectFromList() {
	System.out.println("  0. Cancel and return to menu  ");
	List<Subject> subjects = new ArrayList<>(subService.getAllSubjects());

	if (subjects.isEmpty()) {
	    System.out.println("No subject available, add one!");
	    return null;
	}

	System.out.println("\n----------SUBJECT LIST----------");
	for (int i = 0; i < subjects.size(); i++) {
	    Subject s = subjects.get(i);
	    System.out.println((i + 1) + "." + " " + s.getSubjectName() + " |" + s.getAcronym() + " |"
		    + s.getEvaluationType().toString());
	}

	System.out.println("\n----------SELECT A SUBJECT BY NUMBER----------");
	try {
	    int choice = Integer.parseInt(sc.nextLine());
	    if (choice == 0) {
		System.out.println("Operation cancelled.");
		return null;
	    }
	    if (choice < 1 || choice > subjects.size()) {
		System.out.println("Invalid selection.");
		return null;
	    }
	    return subjects.get(choice - 1);
	} catch (NumberFormatException e) {
	    System.out.println("Invalid input.");
	    return null;
	}
    }

    // SAVE STUDENT INTO A JSON FILE //
    private void saveAll() {
	try {
	    DataManager.save("subjects.json", subService.getAllSubjects());
	    System.out.println("Data successfully saved!");
	} catch (Exception e) {
	    System.err.println("Error during data saving: " + e.getMessage());
	}
    }

    // USED TO GET DATA FROM JSON FILE
    public void loadAll() {
	try {
	    List<Subject> subjects = DataManager.load("subjects.json", Subject[].class);
	    subService.setAllSubjects(subjects);
	} catch (Exception e) {
	    System.out.println("No File.");
	}

    }
}
