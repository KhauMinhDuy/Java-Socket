package thithugiuaki;

import java.util.regex.Pattern;

public class Regex {

    public static boolean checkValidatePassword(String password) {
	String s = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
	return Pattern.matches(s, password);
    }
}
