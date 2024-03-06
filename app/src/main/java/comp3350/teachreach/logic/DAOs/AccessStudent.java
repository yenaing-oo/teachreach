package comp3350.teachreach.logic.DAOs;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.objects.interfaces.IStudent;

public
class AccessStudent
{
    private IStudentPersistence studentPersistence;
    private       List<IStudent> students;
    private final IStudent       student;

    public
    AccessStudent()
    {
        studentPersistence = Server.getStudentDataAccess();
        students           = null;
        student            = null;
    }

    public
    AccessStudent(final IStudentPersistence studentPersistence)
    {
        this();
        this.studentPersistence = studentPersistence;
    }

    public
    List<IStudent> getStudents()
    {
        students = studentPersistence.getStudents();
        return Collections.unmodifiableList(students);
    }

    public
    IStudent getStudentByAccountID(int studentAccountID)
    {
        if (students == null) {
            students = studentPersistence.getStudents();
        }
        students
                .stream()
                .filter(s -> s.getStudentAccountID() == studentAccountID)
                .findFirst()
                .orElseThrow(() -> new DataAccessException("Account not found",
                                                           new NoSuchElementException()));
        return student;
    }

    public
    IStudent insertStudent(IStudent newStudent)
    {
        return studentPersistence.storeStudent(newStudent);
    }

    public
    IStudent updateStudent(IStudent existingStudent)
    {
        return studentPersistence.updateStudent(existingStudent);
    }
}
