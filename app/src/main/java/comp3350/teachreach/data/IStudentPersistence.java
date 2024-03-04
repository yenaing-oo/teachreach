package comp3350.teachreach.data;

import java.util.List;

import comp3350.teachreach.objects.IStudent;

public interface IStudentPersistence {
    IStudent storeStudent(IStudent newStudent);

    IStudent updateStudent(IStudent newStudent);

    List<IStudent> getStudents();
}
