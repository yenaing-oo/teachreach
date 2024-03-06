package comp3350.teachreach.logic.account;

public
class AccountCreatorException extends Exception
{
    public
    AccountCreatorException(String message)
    {
        super(message);
    }

    public static
    AccountCreatorException getException(boolean emptyEmail,
                                         boolean emptyPassword,
                                         boolean invalidEmail)
    {
        if (emptyEmail) {
            return new AccountCreatorException("Email cannot be empty");
        } else if (emptyPassword) {
            return new AccountCreatorException("Password cannot be empty");
        } else if (invalidEmail) {
            return new AccountCreatorException("Email provided is invalid");
        } else {
            return new AccountCreatorException("New Account wasn't created.");
        }
    }
}
