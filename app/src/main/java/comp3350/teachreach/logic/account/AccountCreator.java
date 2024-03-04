package comp3350.teachreach.logic.account;

import static comp3350.teachreach.logic.account.AccountCreatorException.getException;

import java.util.List;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.logic.dataAccessObject.AccessAccount;
import comp3350.teachreach.logic.dataAccessObject.AccessStudent;
import comp3350.teachreach.logic.dataAccessObject.AccessTutor;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountCreator implements IAccountCreator {

    private final ICredentialHandler handler;
    private AccessAccount accessAccount = null;
    private List<IAccount> accounts = null;
    private IAccount account = null;
    private AccessStudent accessStudent = null;
    private List<IStudent> students = null;
    private IStudent student = null;
    private AccessTutor accessTutor = null;
    private List<ITutor> tutors = null;
    private ITutor tutor = null;

    public AccountCreator() {
        accessAccount = new AccessAccount();
        accounts = accessAccount.getAccounts();

        accessStudent = new AccessStudent();
        students = accessStudent.getStudents();

        accessTutor = new AccessTutor();
        tutors = accessTutor.getTutors();

        handler = new CredentialHandler();
    }

    public AccountCreator(IAccountPersistence accounts,
                          IStudentPersistence students,
                          ITutorPersistence tutors) {
        this.accessAccount = new AccessAccount(accounts);
        this.accounts = accessAccount.getAccounts();

        this.accessStudent = new AccessStudent(students);
        this.students = accessStudent.getStudents();

        this.accessTutor = new AccessTutor(tutors);
        this.tutors = accessTutor.getTutors();

        this.handler = new CredentialHandler(accounts);
    }

    @Override
    public AccountCreator createAccount(
            String email,
            String password) throws AccountCreatorException {

        boolean emptyEmail = !InputValidator.isNotEmpty(email);
        boolean emptyPassword = !InputValidator.isNotEmpty(password);
        boolean invalidEmail = !InputValidator.isValidEmail(email);

        boolean inputIsValid = !(emptyEmail || emptyPassword || invalidEmail);

        if (accounts.stream().anyMatch(a -> a.getEmail().equals(email))) {
            throw new AccountCreatorException("Account already exist!");
        }

        if (inputIsValid) {
            account = new Account(email, handler.processPassword(password));
            this.accessAccount.insertAccount(account);
        } else {
            throw getException(emptyEmail, emptyPassword, invalidEmail);
        }
        return this;
    }

    @Override
    public AccountCreator setStudentProfile(
            String username, String major, String pronoun)
            throws RuntimeException {
        if (this.account == null) {
            throw new RuntimeException(
                    "Failed to set Student profile:-"
                            + "(Account not initialized)");
        }

        Student newStudent = new Student(
                account.getEmail(),
                username, major, pronoun);
        accessStudent.insertStudent(newStudent);

        this.account.setStudentProfile(newStudent);
        return this;
    }

    @Override
    public AccountCreator setTutorProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException {
        if (this.account == null) {
            throw new RuntimeException(
                    "Failed to set Tutor profile:-"
                            + "(Account not initialized)");
        }

        Tutor newTutor = new Tutor(
                account.getEmail(),
                username, major, pronoun);
        accessTutor.insertTutor(newTutor);

        this.account.setTutorProfile(newTutor);
        return this;
    }

    @Override
    public IAccount buildAccount() throws AccountCreatorException {
        if (account == null) {
            throw getException(false, false, false);
        }
        return account;
    }
}

