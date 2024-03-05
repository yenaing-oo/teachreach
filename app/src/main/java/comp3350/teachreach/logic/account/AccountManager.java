package comp3350.teachreach.logic.account;

import comp3350.teachreach.logic.interfaces.IAccountManager;
import comp3350.teachreach.logic.DAOs.AccessAccount;
import comp3350.teachreach.logic.DAOs.AccessStudent;
import comp3350.teachreach.logic.DAOs.AccessTutor;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public class AccountManager implements IAccountManager {
    private final AccessAccount accessAccount;
    private final AccessStudent accessStudent;
    private final AccessTutor accessTutor;
    private final ICredentialHandler credentialHandler;
    private final IAccount theAccount;

    public AccountManager(IAccount theAccount) {
        accessAccount = new AccessAccount();
        accessStudent = new AccessStudent();
        accessTutor = new AccessTutor();
        this.credentialHandler = new CredentialHandler();
        this.theAccount = theAccount;
    }

    @Override
    public IAccountManager updateAccountUsername(String newName) {
        this.theAccount.getStudentProfile().ifPresent(student -> {
            student.setName(newName);
            this.accessStudent.updateStudent(student);
        });
        this.theAccount.getTutorProfile().ifPresent(tutor -> {
            tutor.setName(newName);
            this.accessTutor.updateTutor(tutor);
        });
        return this;
    }

    @Override
    public IAccountManager updateAccountUserPronouns(String pronouns) {
        this.theAccount.getStudentProfile().ifPresent(student -> {
            student.setPronouns(pronouns);
            this.accessStudent.updateStudent(student);
        });
        this.theAccount.getTutorProfile().ifPresent(tutor -> {
            tutor.setPronouns(pronouns);
            this.accessTutor.updateTutor(tutor);
        });
        return this;
    }

    @Override
    public IAccountManager updatePassword(
            String email,
            String oldPassword,
            String newPassword) {
        this.accessAccount.getAccountByEmail(email).ifPresent(account -> {
            if (credentialHandler.validateCredential(email, oldPassword)) {
                this.theAccount.setPassword(
                        credentialHandler.processPassword(newPassword));
                this.accessAccount.updateAccount(theAccount);
            }
        });
        return this;
    }

    @Override
    public IAccountManager updateEmail(String password,
                                       String oldEmail,
                                       String newEmail) {
        this.accessAccount.getAccountByEmail(oldEmail).ifPresent(account -> {
            if (credentialHandler.validateCredential(oldEmail, password)) {
                this.theAccount.setEmail(newEmail);
                this.accessAccount.updateAccount(theAccount);
            }
        });
        return this;
    }
}
