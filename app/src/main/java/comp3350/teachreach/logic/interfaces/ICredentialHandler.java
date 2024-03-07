package comp3350.teachreach.logic.interfaces;

public
interface ICredentialHandler
{
    String processPassword(String plainPassword);

    boolean validateCredential(String email, String plainPassword);
}
