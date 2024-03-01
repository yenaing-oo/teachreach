package comp3350.teachreach.logic;

import comp3350.teachreach.logic.account.AccountCreatorException;
import comp3350.teachreach.objects.Account;

public interface IAccountCreator {

    //    Student createStudentAccount(String name,
//                                        String pronouns,
//                                        String major,
//                                        String email,
//                                        String password);
//
//    Tutor createTutorAccount(String name,
//                                    String pronouns,
//                                    String major,
//                                    String email,
//                                    String password);
    Account createAccount(String name,
                          String pronouns,
                          String major,
                          String email,
                          String password) throws AccountCreatorException;
}
