package comp3350.teachreach.logic.account;

import comp3350.teachreach.objects.IAccount;

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
