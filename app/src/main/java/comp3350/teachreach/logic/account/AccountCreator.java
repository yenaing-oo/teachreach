package comp3350.teachreach.logic.account;

import java.util.Optional;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.logic.IAccountCreator;
import comp3350.teachreach.logic.Server;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.Student;

public class AccountCreator implements IAccountCreator {

    private final IAccountPersistence accountsDataAccess;
    private final IStudentPersistence studentsDataAccess;
    private final ITutorPersistence tutorsDataAccess;

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
    public Account createAccount(String name,
                                 String pronouns,
                                 String major,
                                 String email,
                                 String password) throws AccountCreatorException {

        boolean emptyName = !InputValidator.isNotEmpty(name);
        boolean emptyEmail = !InputValidator.isNotEmpty(email);
        boolean emptyPassword = !InputValidator.isNotEmpty(password);
        boolean invalidEmail = !InputValidator.isValidEmail(email);

        boolean inputIsValid = !(emptyName || emptyEmail || emptyPassword || invalidEmail);

        Optional<Account> newAccount = Optional.empty();

        if (inputIsValid) {
            Account.Builder newAc =
                    new Account.Builder(
                            email,
                            CredentialHandler.processPassword(password));
            newAc.studentProfile(new Student(name, pronouns, major));
            newAccount = newAc.build();
        }

        newAccount.ifPresent(account -> {
            this.accountsDataAccess.storeAccount(account);

            account.getStudentProfile().ifPresent(student -> {
                this.studentsDataAccess.storeStudent(student);
            });
        });

        return newAccount.orElseThrow(() ->
            AccountCreatorException.getException(
                emptyName,
                emptyEmail,
                emptyPassword,
                invalidEmail
            ));
    }

//    @Override
//    public Tutor createTutorAccount(String name, String pronouns, String major, String email, String password) {
//        boolean isValidInput = CredentialValidator.isNotEmpty(name) && CredentialValidator.isNotEmpty(email) && CredentialValidator.isNotEmpty(password) && CredentialValidator.isValidEmail(email);
//        Tutor newTutor = null;
//
//        if (isValidInput) {
//            newTutor = new Tutor(name, pronouns, major, email, CredentialValidator.processPassword(password));
//            accounts.storeTutor(newTutor);
//        }
//
//        return isValidInput ? newTutor : null;
}

