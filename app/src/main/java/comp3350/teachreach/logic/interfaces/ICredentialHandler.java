package comp3350.teachreach.logic.interfaces;

import java.util.Optional;

import comp3350.teachreach.objects.interfaces.IAccount;

public
interface ICredentialHandler
{
    String processPassword(String plainPassword);

    Optional<IAccount> validateCredential(String email, String plainPassword);
}
