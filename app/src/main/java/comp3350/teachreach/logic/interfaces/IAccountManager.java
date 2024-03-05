package comp3350.teachreach.logic.interfaces;

public interface IAccountManager {
    IAccountManager updateAccountUsername(String newName);

    IAccountManager updateAccountUserPronouns(String pronouns);

    IAccountManager updatePassword(String email,
                                   String oldPassword,
                                   String newPassword);

    IAccountManager updateEmail(String password,
                                String oldEmail,
                                String newEmail);
}
