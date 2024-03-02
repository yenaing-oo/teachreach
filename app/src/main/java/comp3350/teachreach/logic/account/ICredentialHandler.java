package comp3350.teachreach.logic.account;

import at.favre.lib.crypto.bcrypt.BCrypt;

public interface ICredentialHandler {
    String processPassword(String plainPassword);

    boolean validateCredential(String email, String password);
}
