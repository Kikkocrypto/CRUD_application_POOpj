
import java.util.Scanner;

import Controller.GradeController;
import Controller.StudentController;
import Controller.SubjectController;
import Service.GradeService;
import Service.StudentService;
import Service.SubjectService;

public class ConsoleApp {
    private final Scanner sc = new Scanner(System.in);

    // Shared Services
    private final StudentService studentService = new StudentService();
    private final SubjectService subjectService = new SubjectService();
    private final GradeService gradeService = new GradeService();

    // Shared Controller
    private final StudentController stController = new StudentController(sc, studentService, gradeService);
    private final SubjectController subController = new SubjectController(sc, subjectService, gradeService);
    private final GradeController gradeController = new GradeController(gradeService, studentService, subjectService,
	    sc);

    public void start() {

	boolean running = true;
	while (running) {
	    System.out.println("------ MAIN MENU ------");
	    System.out.println("Click 1 to manage students:");
	    System.out.println("Click 2 to manage subjects:");
	    System.out.println("Click 3 to manage grades:");
	    System.out.println("Click 4 to exit.");
	    System.out.println("-------------------------");

	    String choice = sc.nextLine();

	    switch (choice) {
	    case "1":
		stController.start();
		break;
	    case "2":
		subController.start();
		break;
	    case "3":
		gradeController.start();
		break;
	    case "4":
		System.out.println("Exiting the application ...");
		running = false;
		break;
	    default:
		System.out.println("Not valid choice");
	    }
	}
    }
}
