package comp3350.teachreach.logic;

import at.favre.lib.crypto.bcrypt.BCrypt;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.AccountType;

public class LoginHandler {

    private IAccountPersistence accounts;

    public LoginHandler() {
        this.accounts = Server.getAccounts();
    }

    public boolean validateCredential(AccountType type, String email, String password) {
        Account theAccount;
        boolean result = false;

        if (type == AccountType.Student) {
            theAccount = accounts.getStudentByEmail(email);
        } else {
            theAccount = accounts.getTutorByEmail(email);
        }

        if (theAccount != null) {
            BCrypt.Result bResult = BCrypt.verifyer().verify(password.toCharArray(), theAccount.getPassword());
            result = bResult.verified;
        }
        return result;
    }
}
