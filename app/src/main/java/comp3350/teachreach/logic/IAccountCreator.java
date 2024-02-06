package comp3350.teachreach.logic;

import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public interface IAccountCreator {

    Student createStudentAccount(String name,
                                        String pronouns,
                                        String major,
                                        String email,
                                        String password);

    Tutor createTutorAccount(String name,
                                    String pronouns,
                                    String major,
                                    String email,
                                    String password);
}
