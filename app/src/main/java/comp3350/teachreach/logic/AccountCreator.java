package comp3350.teachreach.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;
import comp3350.teachreach.data.AccountStub;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountCreator implements IAccountCreator {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9" + ".-]+\\" + ".[A-Za-z0-9.-]+$";

    private IAccountPersistence accounts;

    public AccountCreator() {
        accounts = new AccountStub();
    }

    public AccountCreator(IAccountPersistence accounts) {
        this.accounts = accounts;
    }

    @Override
    public Student createStudentAccount(String name, String pronouns, String major, String email, String password) {
        String processedPassword = processPassword(password);
        Student newStudent = new Student(name, pronouns, major, email, processedPassword);

        if (!isValidEmail(email)) {
            newStudent = null;
            // throw new Exception("Not an email!");
        } else {
            accounts.storeStudent(newStudent);
        }
        return newStudent;
    }

    @Override
    public Tutor createTutorAccount(String name, String pronouns, String major, String email, String password) {
        String processedPassword = processPassword(password);
        Tutor newTutor = new Tutor(name, pronouns, major, email, processedPassword);
        boolean validEmail = isValidEmail(email);

        if (!validEmail) {
            newTutor = null;
            // throw new Exception("Not an email!");
        } else {
            accounts.storeTutor(newTutor);
        }

        return newTutor;
    }

//    public Account createAccount(AccountType type, String name, String pronouns, String major, String email, String password) throws Exception {
//        Account newAccount = null;
//
//        name = removeSpace(name);
//        pronouns = removeSpace(pronouns);
//        major = removeSpace(major);
//        email = removeSpace(email);
//
//        boolean validEmail = isValidEmail(email);
//
//
//        if (type == AccountType.Tutor) {
//            newAccount = new Tutor(name, pronouns, major, email, password);
//        } else if (type == AccountType.Student) {
//            newAccount = new Student(name, pronouns, major, email, password);
//        }
//
//        if (!validEmail) {
//            throw new Exception("Not an email");
//        }
//        return newAccount;
//    }

    private static String processPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12,
                plainPassword.toCharArray());
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

//    private static boolean isValidInput(String in) {
//        boolean isValid = false;
//
//        return isValid;
//    }

//    private static String removeSpace(String line) {
//        return line.replaceAll("\\s+", " ").trim();
//    }

}
