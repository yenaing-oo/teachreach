package comp3350.teachreach.logic.account;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.account.exceptions.AccountManagerException;
import comp3350.teachreach.logic.interfaces.IAccountManager;
import comp3350.teachreach.logic.interfaces.ICredentialHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public
class AccountManager implements IAccountManager
{
    private final AccessAccounts     accessAccounts;
    private final ICredentialHandler credentialHandler;
    private       IAccount           theAccount;

    public
    AccountManager(IAccount theAccount)
    {
        accessAccounts         = new AccessAccounts();
        this.credentialHandler = new CredentialHandler();
        this.theAccount        = theAccount;
    }

    public
    AccountManager(IAccount theAccount, IAccountPersistence accountPersistence)
    {
        accessAccounts         = new AccessAccounts(accountPersistence);
        this.credentialHandler = new CredentialHandler();
        this.theAccount        = theAccount;
    }

    @Override
    public
    IAccountManager updateAccountUsername(String newName)
            throws AccountManagerException
    {
        try {
            theAccount = accessAccounts.updateAccount(theAccount.setUserName(
                    newName));
            return this;
        } catch (final Exception e) {
            throw new AccountManagerException("Failed to update user name :(",
                                              e);
        }
    }

    @Override
    public
    IAccountManager updateAccountUserPronouns(String pronouns)
            throws AccountManagerException
    {
        try {
            theAccount
                    = accessAccounts.updateAccount(theAccount.setUserPronouns(
                    pronouns));
            return this;
        } catch (final Exception e) {
            throw new AccountManagerException(
                    "Failed to update user pronouns :(",
                    e);
        }
    }

    @Override
    public
    IAccountManager updateAccountUserMajor(String major)
            throws AccountManagerException
    {
        return null;
    }

    @Override
    public
    IAccountManager updatePassword(String oldPlainPassword,
                                   String newPlainPassword)
            throws AccountManagerException
    {
        try {
            assert credentialHandler.validateCredential(theAccount.getAccountEmail(),
                                                        oldPlainPassword);

            theAccount
                    =
                    accessAccounts.updateAccount(theAccount.setAccountPassword(
                    credentialHandler.processPassword(newPlainPassword)));

            return this;
        } catch (final AssertionError e) {
            throw new AccountManagerException("Invalid password!", e);
        } catch (final Exception e) {
            throw new AccountManagerException(
                    "Failed while updating password :(",
                    e);
        }
    }

    @Override
    public
    IAccountManager updateEmail(String plainPassword, String newEmail)
            throws AccountManagerException
    {
        try {
            assert credentialHandler.validateCredential(theAccount.getAccountEmail(),
                                                        plainPassword);
            theAccount
                    = accessAccounts.updateAccount(theAccount.setAccountEmail(
                    newEmail));
            return this;
        } catch (final AssertionError e) {
            throw new AccountManagerException("Invalid email or password!", e);
        } catch (final Exception e) {
            throw new AccountManagerException(
                    "Failed to update account email :(",
                    e);
        }
    }
}
