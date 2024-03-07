package comp3350.teachreach.logic.account.exceptions;

public
class AccountManagerException extends Exception
{
    public
    AccountManagerException(String message)
    {
        super(message);
    }

    public
    AccountManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
