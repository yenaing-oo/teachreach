package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IStudent;

public
interface IStudentPersistence
{
    IStudent storeStudent(IStudent newStudent);

    IStudent updateStudent(IStudent newStudent);

    List<IStudent> getStudents();
}
