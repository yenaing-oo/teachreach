package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.logic.DAOs.AccessStudents ;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.tests.utils.TestUtils;

public
class AccessStudentIT
{
    private AccessStudents accessStudents;
    private File tempDB;

    @Before
    public
    void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final IStudentPersistence persistence = new StudentHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        this.accessStudents = new AccessStudents(persistence);
    }
    //
    //
    //
    @Test
    public void testGetStudentByAccountID(){
        IStudent testStudent = accessStudents.getStudentByStudentID(1);
        assertEquals(1, testStudent.getStudentID());
    }
    //Map<Integer, IStudent> getStudents()
    //IStudent getStudentByAccountID(int studentAccountID)
    //IStudent insertStudent(IStudent newStudent)
}
