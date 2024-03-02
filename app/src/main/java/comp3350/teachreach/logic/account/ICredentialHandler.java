package comp3350.teachreach.logic.account;

public interface ICredentialHandler {
    String processPassword(String plainPassword);

    boolean validateCredential(String email, String password);
}
