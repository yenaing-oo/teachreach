package comp3350.teachreach.logic;

import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.IStudent;

public interface IAccountCreator {
    IAccountCreator createAccount(
            String email,
            String password) throws AccountCreatorException;

    IAccount setStudentProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException;

    IAccount setTutorProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException;
}
