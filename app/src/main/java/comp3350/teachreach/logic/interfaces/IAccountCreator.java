package comp3350.teachreach.logic.interfaces;

import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public
interface IAccountCreator
{
    AccountCreator createAccount(String email,
                                 String password,
                                 String name,
                                 String pronouns,
                                 String major) throws AccountCreatorException;

    IAccountCreator newStudentProfile() throws AccountCreatorException;

    IAccountCreator newTutorProfile() throws AccountCreatorException;

    IAccount buildAccount() throws AccountCreatorException;

    ITutor getNewTutor() throws AccountCreatorException;

    IStudent getNewStudent() throws AccountCreatorException;
}
