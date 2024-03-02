package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.Optional;

import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.Student;

public interface IStudentPersistence {
    IStudent storeStudent(IStudent newStudent) throws RuntimeException;

    IStudent updateStudent(IStudent newStudent) throws RuntimeException;

    Optional<IStudent> getStudentByEmail(String email);

    ArrayList<IStudent> getStudents();
}
