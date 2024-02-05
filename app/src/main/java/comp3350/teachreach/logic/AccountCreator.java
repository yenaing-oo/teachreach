package comp3350.teachreach.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comp3350.teachreach.objects.*;

public class AccountCreator {

    public Account createAccount(AccountType type, String name, String pronouns, String major, String email, String password) throws Exception {
        Account newAccount = null;

        name = removeSpace(name);
        pronouns = removeSpace(pronouns);
        major = removeSpace(major);
        email = removeSpace(email);

        boolean validEmail = isValidEmail(email);


        if (type == AccountType.Tutor) {
            newAccount = new Tutor(name, pronouns, major, email, password);
        } else if (type == AccountType.Student) {
            newAccount = new Student(name, pronouns, major, email, password);
        }

        if (!validEmail) {
            throw new Exception("Not an email");
        }
        return newAccount;
    }

    private static boolean isValidInput(String in) {
        boolean isValid = false;

        return isValid;
    }

    private static String removeSpace(String line) {
        return line.replaceAll("\\s+", " ").trim();
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
