package comp3350.teachreach.logic.DAOs;

public
class DataAccessException extends RuntimeException
{
    public
    DataAccessException(String message)
    {
        super(message);
    }

    public
    DataAccessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
