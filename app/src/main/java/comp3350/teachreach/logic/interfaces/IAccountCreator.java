package comp3350.teachreach.logic.interfaces;

import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.objects.interfaces.IAccount;

public interface IAccountCreator {
    IAccountCreator createAccount(
            String email,
            String password) throws AccountCreatorException;

    IAccountCreator setStudentProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException;

    IAccountCreator setTutorProfile(
            String username,
            String major,
            String pronoun) throws RuntimeException;

    IAccount buildAccount() throws AccountCreatorException;
}
