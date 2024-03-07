package comp3350.teachreach.logic.account;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.interfaces.IAccountManager;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class AccountManager implements IAccountManager
{
    private final AccessAccounts accessAccounts;
    private final AccessStudents     accessStudents;
    private final AccessTutors       accessTutors;
    private final ICredentialHandler credentialHandler;
    private final IAccount           theAccount;

    public
    AccountManager(IAccount theAccount)
    {
        accessAccounts = new AccessAccounts();
        accessStudents         = new AccessStudents();
        accessTutors           = new AccessTutors();
        this.credentialHandler = new CredentialHandler();
        this.theAccount        = theAccount;
    }

    @Override
    public
    IAccountManager updateAccountUsername(String newName)
    {
        this.theAccount.getStudentProfile().ifPresent(student -> {
            student.setName(newName);
            this.accessStudents.updateStudent(student);
        });
        this.theAccount.getTutorProfile().ifPresent(tutor -> {
            tutor.setName(newName);
            this.accessTutors.updateTutor(tutor);
        });
        return this;
    }

    @Override
    public
    IAccountManager updateAccountUserPronouns(String pronouns)
    {
        this.theAccount.getStudentProfile().ifPresent(student -> {
            student.setPronouns(pronouns);
            this.accessStudents.updateStudent(student);
        });
        this.theAccount.getTutorProfile().ifPresent(tutor -> {
            tutor.setPronouns(pronouns);
            this.accessTutors.updateTutor(tutor);
        });
        return this;
    }

    @Override
    public
    IAccountManager updatePassword(String email,
                                   String oldPassword,
                                   String newPassword)
    {
        this.accessAccounts.getAccountByEmail(email).ifPresent(account -> {
            if (credentialHandler.validateCredential(email, oldPassword)) {
                this.theAccount.setAccountPassword(credentialHandler.processPassword(
                        newPassword));
                this.accessAccounts.updateAccount(theAccount);
            }
        });
        return this;
    }

    @Override
    public
    IAccountManager updateEmail(String password,
                                String oldEmail,
                                String newEmail)
    {
        this.accessAccounts.getAccountByEmail(oldEmail).ifPresent(account -> {
            if (credentialHandler.validateCredential(oldEmail, password)) {
                this.theAccount.setAccountEmail(newEmail);
                this.accessAccounts.updateAccount(theAccount);
            }
        });
        return this;
    }
}
