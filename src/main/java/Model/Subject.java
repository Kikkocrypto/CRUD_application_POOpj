package Model;

import java.io.Serializable;
import java.util.UUID;

public class Subject implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID subject_id;
    private String subjectName;
    private String acronym;
    private EvaluationTypeEnum evaluationType;

    public Subject() {
    }

    public Subject(String subjectName, String acronym, EvaluationTypeEnum evaluationType) {
	this.subject_id = UUID.randomUUID();
	this.subjectName = subjectName;
	this.acronym = acronym;
	this.evaluationType = evaluationType;
    }

    public UUID getSubject_id() {
	return subject_id;
    }

    public void setSubject_id(UUID subject_id) {
	this.subject_id = subject_id;
    }

    public String getSubjectName() {
	return subjectName;
    }

    public void setSubjectName(String subjectName) {
	this.subjectName = subjectName;
    }

    public String getAcronym() {
	return acronym;
    }

    public void setAcronym(String acronym) {
	this.acronym = acronym;
    }

    public EvaluationTypeEnum getEvaluationType() {
	return evaluationType;
    }

    public void setEvaluationType(EvaluationTypeEnum evaluationType) {
	this.evaluationType = evaluationType;
    }

    @Override
    public String toString() {
	return "Subject [subject_id=" + subject_id + ", subjectName=" + subjectName + ", acronym=" + acronym
		+ ", evaluationType=" + evaluationType.toString() + "]";
    }

}
