package validation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {

    /** validates if the emailAddress is valid according to the given guidelines
     * @param emailAddress
     * @return boolean
     */

    public boolean isEmailAddressValid(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@[a-zA-Z0-9]+[.][a-zA-Z0-9]+";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    /** validates if the URL is valid according to the given guidelines
     * @param url
     * @return boolean
     */
    public boolean isURLValid(String url) {
        String regexPattern = "((https?|https?)://)[a-zA-Z]{1,256}[.][a-zA-Z]{1,256}[.][a-zA-Z]{1,256}";
        return Pattern.compile(regexPattern)
                .matcher(url)
                .matches();
    }

    /** validates if the percentage is valid according to the given guidelines
     * @param percentage
     * @return boolean
     */

    public boolean isPercentageValid(int percentage) {
        if (percentage >= 100) {
            return false;
        } else if (percentage <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /** validates if the grade is valid according to the given guidelines
     * @param grade
     * @return boolean
     */
    public boolean isGradeValid(int grade){
        if (grade >= 10) {
            return false;
        } else if (grade <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /** Formats the postal code, so it's valid according to the given guidelines.
     * @param postalCode
     * @return String
     */
    public String formatPostalCode(String postalCode) {
        if (postalCode.length() == 6) {
            String numbers = postalCode.substring(0, 4);
            if (numbers.substring(0, 1).equals("0")) {
                return "cant start with 0";
            }
            String letters = postalCode.substring(4, 6).toUpperCase();
            return numbers + " " + letters;
        } else {
            return null;
        }
    }

    /**
     * Validates a date by comparing the current date.
     * If objectType equals Student then the date will need to be before the current date.
     * If objectType equals Enrollment then the date will need to be equal or after the current date.
     *
     * @param date
     * @param objectType
     * @return boolean
     */
    public boolean validateDate(Date date, String objectType) {
        //Compares the given date to the current date
        if ("Student".equals(objectType)) {
            if (date.before(Date.valueOf(LocalDate.now()))) {
                return true;
            } else {
                return false;
            }
        } else if ("Enrollment".equals(objectType)) {
            if (date.after(Date.valueOf(LocalDate.now()))) {
                return true;
            } else if (date.equals(Date.valueOf(LocalDate.now()))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


}
