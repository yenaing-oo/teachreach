package comp3350.teachreach.logic.account;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordManager {
    public static String encryptPassword(String plainPassword) {
        return BCrypt
                .withDefaults()
                .hashToString(12, plainPassword.toCharArray());
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt
                .verifyer().
                verify(plainPassword.toCharArray(), hashedPassword)
                .verified;
    }
}
