package comp3350.teachreach.logic.account;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.logic.IAccountManager;
import comp3350.teachreach.objects.IAccount;

public class AccountManager implements IAccountManager {
    private final IAccountPersistence accountsDataAccess;
    private final IStudentPersistence studentsDataAccess;
    private final ITutorPersistence tutorsDataAccess;
    private final ICredentialHandler credentialHandler;
    private final IAccount theAccount;

    public AccountManager(IAccountPersistence accounts,
                          IStudentPersistence students,
                          ITutorPersistence tutors,
                          ICredentialHandler credentialHandler,
                          IAccount theAccount) {
        this.accountsDataAccess = accounts;
        this.studentsDataAccess = students;
        this.tutorsDataAccess = tutors;
        this.credentialHandler = new CredentialHandler(accountsDataAccess);
        this.theAccount = theAccount;
    }

    @Override
    public IAccountManager updateAccountUsername(String newName) {
        this.theAccount.getStudentProfile().ifPresent(student -> {
            student.setName(newName);
            this.studentsDataAccess.updateStudent(student);
        });
        this.theAccount.getTutorProfile().ifPresent(tutor -> {
            tutor.setName(newName);
            this.tutorsDataAccess.updateTutor(tutor);
        });
        return this;
    }

    @Override
    public IAccountManager updateAccountUserPronouns(String pronouns) {
        this.theAccount.getStudentProfile().ifPresent(student -> {
            student.setPronouns(pronouns);
            this.studentsDataAccess.updateStudent(student);
        });
        this.theAccount.getTutorProfile().ifPresent(tutor -> {
            tutor.setPronouns(pronouns);
            this.tutorsDataAccess.updateTutor(tutor);
        });
        return this;
    }

    @Override
    public IAccountManager updatePassword(
            String email,
            String oldPassword,
            String newPassword) {
        this.accountsDataAccess.getAccountByEmail(email).ifPresent(account -> {
            if (credentialHandler.validateCredential(email, oldPassword)) {
                this.theAccount.setPassword(
                        credentialHandler.processPassword(newPassword));
                this.accountsDataAccess.updateAccount(theAccount);
            }
        });
        return this;
    }

    @Override
    public IAccountManager updateEmail(String password,
                                       String oldEmail,
                                       String newEmail) {
        this.accountsDataAccess.getAccountByEmail(oldEmail).ifPresent(account -> {
            if (credentialHandler.validateCredential(oldEmail, password)) {
                this.theAccount.setEmail(newEmail);
                this.accountsDataAccess.updateAccount(theAccount);
            }
        });
        return this;
    }
}
