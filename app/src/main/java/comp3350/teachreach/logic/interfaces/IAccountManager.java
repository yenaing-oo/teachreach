package comp3350.teachreach.logic.interfaces;

import comp3350.teachreach.logic.account.exceptions.AccountManagerException;

public
interface IAccountManager
{
    IAccountManager updateAccountUsername(String newName)
            throws AccountManagerException;

    IAccountManager updateAccountUserPronouns(String pronouns)
            throws AccountManagerException;

    IAccountManager updateAccountUserMajor(String major)
            throws AccountManagerException;

    IAccountManager updatePassword(String oldPlainPasswordFromUser,
                                   String newPlainPasswordFromUser)
            throws AccountManagerException;

    IAccountManager updateEmail(String password, String newEmail)
            throws AccountManagerException;
}
