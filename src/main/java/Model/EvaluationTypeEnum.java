package Model;

public enum EvaluationTypeEnum {
    EXAM, COLLOQUIUM;

    @Override
    public String toString() {
	return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
