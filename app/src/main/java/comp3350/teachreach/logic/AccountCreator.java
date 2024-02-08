package comp3350.teachreach.logic;

import at.favre.lib.crypto.bcrypt.BCrypt;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountCreator implements IAccountCreator {

  private static final String EMAIL_PATTERN =
      "^[A-Za-z0-9+_.-]+@[A-Za-z0-9" + ".-]+\\" + ".[A-Za-z0-9.-]+$";

  private final IAccountPersistence accounts;

  public AccountCreator() {
    accounts = Server.getAccounts();
  }

  private static String processPassword(String plainPassword) {
    return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
  }

  private static boolean isValidEmail(String email) {
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  private static boolean isNotEmpty(String input) {
    boolean result = false;
    if (input != null) {
      result = !input.equals("");
    }
    return result;
  }

  @Override
  public Student createStudentAccount(
      String name, String pronouns, String major, String email, String password) {

    boolean isValidInput =
        isNotEmpty(name) && isNotEmpty(email) && isNotEmpty(password) && isValidEmail(email);
    Student newStudent = null;

    if (isValidInput) {
      newStudent = new Student(name, pronouns, major, email, processPassword(password));
      accounts.storeStudent(newStudent);
    }

    return isValidInput ? newStudent : null;
  }

  @Override
  public Tutor createTutorAccount(
      String name, String pronouns, String major, String email, String password) {
    boolean isValidInput =
        isNotEmpty(name) && isNotEmpty(email) && isNotEmpty(password) && isValidEmail(email);
    Tutor newTutor = null;

    if (isValidInput) {
      newTutor = new Tutor(name, pronouns, major, email, processPassword(password));
      accounts.storeTutor(newTutor);
    }

    return isValidInput ? newTutor : null;
  }
}
