package validation;

import java.util.regex.Pattern;

public class InputValidation {

    public boolean isEmailAddressValid(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public boolean isURLValid(String url) {
        String regexPattern = "((https?|https?)://)[a-zA-Z]{1,256}[.][a-zA-Z]{1,256}[.][a-zA-Z]{1,256}";
        return Pattern.compile(regexPattern)
                .matcher(url)
                .matches();
    }

    public boolean isPercentageValid(int percentage) {
        if (percentage > 100) {
            return false;
        } else if (percentage < 0) {
            return false;
        } else {
            return true;
        }
    }

    public String formatPostalCode(String postalCode) {
        if (postalCode.length() == 6 ) {
            
            String numbers = postalCode.substring(0, 4);
            if (numbers.substring(0, 1).equals("0")) {
                return "cant start with 0";
            }
            String letters = postalCode.substring(4, 6).toUpperCase();
            return numbers + " " + letters;
        } else {
            return "invalid size";
        }
    }
    
}
