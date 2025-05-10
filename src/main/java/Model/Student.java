package Model;

import java.io.Serializable;
import java.util.UUID;

public class Student implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID student_id;
    private String firstName;
    private String lastName;
    private String email;
    private String academicGroup;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String academicGroup) {
	this.student_id = UUID.randomUUID();
	this.firstName = firstName;
	this.lastName = lastName;
	this.academicGroup = academicGroup;
	this.email = email;
    }

    public UUID getStudent_id() {
	return student_id;
    }

    public void setStudent_id(UUID student_id) {
	this.student_id = student_id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getAcademicGroup() {
	return academicGroup.toString();
    }

    public void setAcademicGroup(String academicGroup) {
	this.academicGroup = academicGroup;
    }

    @Override
    public String toString() {
	return "Student [student_id=" + student_id + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
		+ email + ", AcademicGroup=" + academicGroup + "]";
    }

}
