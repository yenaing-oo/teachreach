package comp3350.teachreach.data.interfaces;

import java.util.Map;

import comp3350.teachreach.objects.interfaces.IStudent;

public
interface IStudentPersistence
{
    IStudent storeStudent(IStudent newStudent);

    IStudent storeStudent(int accountID) throws RuntimeException;

    Map<Integer, IStudent> getStudents();
}
