package comp3350.teachreach.logic.account;

import static comp3350.teachreach.logic.account.AccountCreatorException.getException;

import java.util.Optional;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.logic.IAccountCreator;
import comp3350.teachreach.logic.Server;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountCreator implements IAccountCreator {

    private final IAccountPersistence accountsDataAccess;
    private final IStudentPersistence studentsDataAccess;
    private final ITutorPersistence tutorsDataAccess;

    private Optional<Account> newAccount = Optional.empty();

    public AccountCreator() {
        accountsDataAccess = Server.getAccountDataAccess();
        studentsDataAccess = Server.getStudentDataAccess();
        tutorsDataAccess = Server.getTutorDataAccess();
    }

    public AccountCreator(IAccountPersistence accounts,
                          IStudentPersistence students,
                          ITutorPersistence tutors) {
        this.accountsDataAccess = accounts;
        this.studentsDataAccess = students;
        this.tutorsDataAccess = tutors;
    }

    @Override
    public AccountCreator createAccount(
            String email,
            String password) throws AccountCreatorException {

        boolean emptyEmail = !InputValidator.isNotEmpty(email);
        boolean emptyPassword = !InputValidator.isNotEmpty(password);
        boolean invalidEmail = !InputValidator.isValidEmail(email);

        boolean inputIsValid = !(emptyEmail || emptyPassword || invalidEmail);

        if (inputIsValid) {
            newAccount = Optional.of(
                    new Account(
                            email,
                            CredentialHandler.processPassword(password)));
            this.accountsDataAccess.storeAccount(newAccount.get());
        } else {
            throw getException(
                    emptyEmail,
                    emptyPassword,
                    invalidEmail);
        }

        return this;
    }

    @Override
    public IAccount setStudentProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException {
        this.newAccount.ifPresent(account -> {
            Student newStudent = new Student(
                    username,
                    major,
                    pronoun,
                    this.newAccount.get());
            account.setStudentProfile(newStudent);
            studentsDataAccess.storeStudent(newStudent);
        });
        return this.newAccount.orElseThrow(() ->
                new RuntimeException("Failed to set Student profile:-"
                        + "(Account not found)"));
    }

    @Override
    public IAccount setTutorProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException {
        this.newAccount.ifPresent(account -> {
            Tutor newTutor = new Tutor(
                    username,
                    major,
                    pronoun,
                    this.newAccount.get());
            account.setTutorProfile(newTutor);
            tutorsDataAccess.storeTutor(newTutor);
        });
        return this.newAccount.orElseThrow(() ->
                new RuntimeException("Failed to set Tutor profile:-"
                        + "(Account not found)"));
    }
}

