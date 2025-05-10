package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Model.Student;

public class StudentService {
    private Map<UUID, Student> students = new HashMap<>();

    public void AddStudent(Student s) {
	students.put(s.getStudent_id(), s);
    }

    public void deleteStudentById(String student_id) {
	UUID uuid = UUID.fromString(student_id);
	students.remove(uuid);
    }

    public Student getStudentById(String id) {
	return students.get(UUID.fromString(id));
    }

    public List<Student> getAllStudents() {
	return new ArrayList<>(students.values());
    }

    public void setAllStudents(List<Student> loaded) {
	students.clear();
	for (Student s : loaded)
	    students.put(s.getStudent_id(), s);
    }

}
