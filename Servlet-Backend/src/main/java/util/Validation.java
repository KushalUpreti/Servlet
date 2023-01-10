package util;

import java.util.List;
import java.util.regex.Pattern;

public class Validation {

    enum Validators {
        EMAIL,
        IS_MIN_CHAR,
        IS_MAX_CHAR,
        IS_EMPTY,
        IS_MIN
    }

    public static boolean isEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static boolean isMinChar(int target, String input) {
        return input.trim().length() >= target;
    }

    public static boolean isMaxChar(int target, String input) {
        return input.trim().length() <= target;
    }

    public static boolean isEmpty(String input) {

        return input.trim().equals("");
    }

    // TODO: Complete array validation
    public static boolean validate(String input, List<Validators> validationParams) {
        boolean isValid = true;
        for (Validators v : validationParams) {
            if (v == Validators.EMAIL) {
                isValid = isValid && isEmail(input);
            } else if (v == Validators.IS_EMPTY) {
                isValid = isValid && isEmpty(input);
            }
        }
        return isValid;
    }
}
