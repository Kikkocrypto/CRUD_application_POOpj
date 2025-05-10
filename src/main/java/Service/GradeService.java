package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Model.Grade;

public class GradeService {
    private Map<UUID, Grade> grades = new HashMap<>();

    public void addGrade(Grade s) {
	grades.put(s.getGrade_id(), s);
    }

    public List<Grade> getAllGrades() {
	return new ArrayList<>(grades.values());
    }

    public List<Grade> getGradesByStudent(UUID student_id) {
	return grades.values().stream().filter(g -> g.getStudent_id().equals(student_id)).toList();
    }

    public List<Grade> getGradesBySubject(UUID subject_id) {
	return grades.values().stream().filter(g -> g.getSubject_id().equals(subject_id)).toList();
    }

    public void deleteGradeById(String id) {
	UUID uuid = UUID.fromString(id);
	grades.remove(uuid);
    }

    public void setAllGrades(List<Grade> gradeList) {
	gradeList.forEach(g -> grades.put(g.getGrade_id(), g));
    }
}
