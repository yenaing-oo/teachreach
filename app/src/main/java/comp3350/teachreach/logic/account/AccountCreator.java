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
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class AccountCreator implements IAccountCreator
{
    private final ICredentialHandler handler;
    private       AccessAccounts     accessAccounts = null;
    private       AccessStudents     accessStudents = null;
    private       AccessTutors       accessTutors   = null;
    private       IAccount           newAccount     = null;
    private       ITutor             newTutor       = null;
    private       IStudent           newStudent     = null;

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
            newStudent = new Student(newAccount.getAccountID());
            newStudent = accessStudents.insertStudent(newStudent);
            newAccount.setStudentID(newStudent.getStudentID());
            return this;
        } catch (final Exception e) {
            throw new AccountCreatorException("Failed to make new student :(",
                                              e);
        }
    }

    @Override
    public
    AccountCreator newTutorProfile() throws AccountCreatorException
    {
        if (this.newAccount == null) {
            throw new AccountCreatorException("Failed to set Tutor profile:-" +
                                              "(Account not initialized)");
        }
        try {
            newTutor = new Tutor(newAccount.getAccountID());
            newTutor = accessTutors.insertTutor(newTutor);
            newAccount.setTutorID(newTutor.getTutorID());
            return this;
        } catch (final Exception e) {
            throw new AccountCreatorException("Failed to make new tutor :(", e);
        }
    }

    @Override
    public
    IAccount buildAccount() throws AccountCreatorException
    {
        if (newAccount == null) {
            throw new AccountCreatorException("New account not created!");
        }
        if (!(newAccount.isStudent() || newAccount.isTutor())) {
            throw new AccountCreatorException(
                    "Account is neither a student nor a tutor :(");
        }
        assert newAccount.getAccountID() != -1;
        return newAccount;
    }

    @Override
    public
    ITutor getNewTutor() throws AccountCreatorException
    {
        if (newTutor == null) {
            throw new AccountCreatorException("New tutor not created");
        }
        if (newTutor.getAccountID() != this.buildAccount().getAccountID()) {
            newTutor.setAccountID(newAccount.getAccountID());
        }
        assert newAccount.getAccountID() != -1;
        assert newTutor.getTutorID() != -1;
        return newTutor;
    }

    @Override
    public
    IStudent getNewStudent() throws AccountCreatorException
    {
        if (newStudent == null) {
            throw new AccountCreatorException("New student not created");
        }
        if (newStudent.getStudentAccountID() !=
            this.buildAccount().getAccountID()) {
            newStudent.setStudentAccountID(newAccount.getAccountID());
        }
        assert newAccount.getAccountID() != -1;
        assert newStudent.getStudentID() != -1;
        return newStudent;
    }
}

