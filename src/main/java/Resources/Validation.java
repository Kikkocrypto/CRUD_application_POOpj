package Resources;

import java.util.regex.Pattern;

public class Validation {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
	    Pattern.CASE_INSENSITIVE);
    private static final Pattern LETTERS_ONLY_PATTERN = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$");

    public static boolean isNullOrEmpty(String input) {
	return input == null || input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
	return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isLettersOnly(String input) {
	return input != null && LETTERS_ONLY_PATTERN.matcher(input).matches();
    }

    public static boolean isLengthBetween(String input, int min, int max) {
	return input != null && input.length() >= min && input.length() <= max;
    }

    public static boolean isMoreThanTwoLessThanTen(String group) {
	return isLengthBetween(group, 2, 10);
    }

    public static boolean isValidName(String name) {
	return isLettersOnly(name) && isLengthBetween(name, 2, 50);
    }

    public static boolean isValidGrade(int grade) {
	return grade >= 0 && grade <= 30;
    }
}
