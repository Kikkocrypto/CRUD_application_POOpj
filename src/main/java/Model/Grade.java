package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Grade implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID grade_id;
    private UUID student_id;
    private UUID subject_id;
    private int grade;

    private LocalDate gradingDate;

    public Grade() {
    }

    public Grade(UUID student_id, UUID subject_id, int grade) {
	this.grade_id = UUID.randomUUID();
	this.student_id = student_id;
	this.subject_id = subject_id;
	this.grade = grade;
	this.gradingDate = LocalDate.now();
    }

    public UUID getGrade_id() {
	return grade_id;
    }

    public void setGrade_id(UUID grade_id) {
	this.grade_id = grade_id;
    }

    public UUID getStudent_id() {
	return student_id;
    }

    public void setStudent_id(UUID student_id) {
	this.student_id = student_id;
    }

    public UUID getSubject_id() {
	return subject_id;
    }

    public void setSubject_id(UUID subject_id) {
	this.subject_id = subject_id;
    }

    public int getGrade() {
	return grade;
    }

    public void setGrade(int grade) {
	this.grade = grade;
    }

    public LocalDate getGradingDate() {
	return gradingDate;
    }

    public void setGradingDate(LocalDate gradingDate) {
	this.gradingDate = gradingDate;
    }

    @Override
    public String toString() {
	return "Grade: " + grade + ", Date: " + gradingDate + ", Student ID: " + student_id + ", Subject ID: "
		+ subject_id;
    }

}
