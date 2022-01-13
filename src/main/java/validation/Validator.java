package validation;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
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
        if (percentage > 100) {
            return false;
        } else if (percentage < 0) {
            return false;
        } else {
            return true;
        }
    }

    /** validates if the grade is valid according to the given guidelines
     * @param grade
     * @return boolean
     */
    public boolean isGradeValid(double grade){
        if (grade > 10) {
            return false;
        } else if (grade < 1) {
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
                return null;
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
     *  Dates must be in YYYY-dd-MM or else false will be returned.
     * @param date
     * @param objectType
     * @return boolean
     */
    public boolean isDateValid(String date, String objectType) throws DateTimeException {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        format.setLenient(false);
        //Checks if date can exist
        try {
            format.parse(date);
            System.out.println("Given StringDate: " + date);
            String sqlDate = Date.valueOf(date).toString();
            System.out.println("Given Date: " + sqlDate);
            System.out.println("Current Date: " + LocalDate.now());
            //Sql.date turns leap years into the day next after so this is to prevent that from happening
            if(!Date.valueOf(date).toString().equals(date)){
                return false;
            }
            //Compares the given date to the current date
            if ("Student".equals(objectType)) {
                if (Date.valueOf(date).before(Date.valueOf(LocalDate.now()))) {
                    return true;
                } else {
                    return false;
                }
            } else if ("Enrollment".equals(objectType)) {
                if (Date.valueOf(date).after(Date.valueOf(LocalDate.now()))) {
                    return true;
                } else if (date.equals(Date.valueOf(LocalDate.now()))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (ParseException e) {
            System.out.println(e);
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return false;
        }


    }
}
