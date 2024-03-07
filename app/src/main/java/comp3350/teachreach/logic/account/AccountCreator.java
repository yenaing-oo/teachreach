package comp3350.teachreach.logic.account;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.interfaces.IAccountCreator;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;

public
class AccountCreator implements IAccountCreator
{

    private final ICredentialHandler handler;
    private       AccessAccounts     accessAccounts = null;
    private       AccessStudents     accessStudents = null;
    private       AccessTutors       accessTutors   = null;
    private       IAccount           newAccount     = null;

    public
    AccountCreator()
    {
        accessAccounts = new AccessAccounts();
        accessStudents = new AccessStudents();
        accessTutors   = new AccessTutors();
        handler        = new CredentialHandler();
    }

    public
    AccountCreator(IAccountPersistence accounts,
                   IStudentPersistence students,
                   ITutorPersistence tutors)
    {
        this.accessAccounts = new AccessAccounts(accounts);
        this.accessStudents = new AccessStudents(students);
        this.accessTutors   = new AccessTutors(tutors);
        this.handler        = new CredentialHandler(accounts);
    }

    @Override
    public
    AccountCreator createAccount(String email,
                                 String password,
                                 String name,
                                 String pronouns,
                                 String major) throws AccountCreatorException
    {
        boolean emptyEmail    = !InputValidator.isNotEmpty(email);
        boolean emptyPassword = !InputValidator.isNotEmpty(password);
        boolean emptyName     = !InputValidator.isNotEmpty(name);
        boolean invalidEmail  = !InputValidator.isValidEmail(email);
        boolean inputIsValid = !(emptyEmail || emptyPassword || invalidEmail ||
                                 emptyName);
        if (!inputIsValid) {
            throw new AccountCreatorException(emptyEmail || invalidEmail ?
                                              "Please check if your email " +
                                              "address is valid!" :
                                              emptyPassword ?
                                              "Password cannot be empty" :
                                              emptyName ?
                                              "Name cannot be empty" :
                                              "Invalid input!");
        }
        try {
            newAccount = new Account(email,
                                     handler.processPassword(password),
                                     name,
                                     pronouns,
                                     major);
            newAccount = accessAccounts.insertAccount(newAccount);
            return this;
        } catch (final Exception e) {
            throw new AccountCreatorException(
                    "Account with associated email might already exist!",
                    e);
        }
    }

    @Override
    public
    AccountCreator newStudentProfile() throws AccountCreatorException
    {
        if (this.newAccount == null) {
            throw new AccountCreatorException(
                    "Failed to create a new student profile:-" +
                    "(Account not initialized)");
        }
        try {
            IStudent newStudent = new Student(newAccount.getAccountID());
            newStudent = accessStudents.insertStudent(newStudent);
            newAccount.setStudentID(newStudent.getStudentID());
        } catch (final Exception e) {
            throw new AccountCreatorException("Failed to make new student :(",
                                              e);
        }

        this.account.setStudentProfile(newStudent);
        return this;
    }

    @Override
    public
    AccountCreator setTutorProfile(String username,
                                   String major,
                                   String pronoun) throws RuntimeException
    {
        if (this.account == null) {
            throw new RuntimeException("Failed to set Tutor profile:-" +
                                       "(Account not initialized)");
        }

        Tutor newTutor = new Tutor(username,
                                   major,
                                   pronoun,
                                   account.getAccountID());
        accessTutors.insertTutor(newTutor);

        this.account.setTutorProfile(newTutor);
        return this;
    }

    @Override
    public
    IAccount buildAccount() throws AccountCreatorException
    {
        if (account == null) {
            throw getException(false, false, false);
        }
        return account;
    }
}

