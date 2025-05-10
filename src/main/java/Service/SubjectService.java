package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Model.Subject;

public class SubjectService {
    private Map<UUID, Subject> subjects = new HashMap<>();

    public void AddSubject(Subject s) {
	subjects.put(s.getSubject_id(), s);
    }

    public void deleteSubjectById(String subject_id) {
	UUID uuid = UUID.fromString(subject_id);
	subjects.remove(uuid);
	;
    }

    public Subject getSubjectById(String subject_id) {
	UUID uuid = UUID.fromString(subject_id);
	return subjects.get(uuid);
    }

    public List<Subject> getAllSubjects() {
	return new ArrayList<>(subjects.values());
    }

    public void setAllSubjects(List<Subject> loaded) {
	subjects.clear();
	for (Subject s : loaded)
	    subjects.put(s.getSubject_id(), s);
    }

}
