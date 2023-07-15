package Data.Enum;

public enum EPattern {
    NAME_PATTERN("^[\\p{L} .'-]+$", "Please input name: ", "Invalid Name! Please try again"),
    EMAIL_PATTERN("^[A-Za-z0-9+_.-]+@gmail.com$", "Please input email: ", "Invalid Gmail! Please try again"),
    PASSWORD_PATTERN("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", "Please input password:", "Invalid Password. Password must contain at least 1 Character, 1 Number with 8-16 length"),
    PHONE_PATTERN("^(\\+?84|0)(3[2-9]|5[2689]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$","Please input Phone number: ", "Invalid Phone number! Please input Viet Nam's phone number");
    private final String pattern;
    private final String message;
    private final String errorMsg;

    EPattern(String pattern, String message, String errorMsg) {
        this.pattern = pattern;
        this.message = message;
        this.errorMsg = errorMsg;
    }

    public String getPattern() {
        return pattern;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
