package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import comp3350.teachreach.data.exceptions.DuplicateEmailException;
import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.account.AccountCreator;
import comp3350.teachreach.logic.exceptions.AccountCreatorException;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.exceptions.input.InvalidEmailException;
import comp3350.teachreach.logic.exceptions.input.InvalidPasswordException;
import comp3350.teachreach.logic.interfaces.IAccountCreator;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessStudentIT
{
    private IStudent       testStudent;
    private AccessStudents accessStudents;
    private File           tempDB;

    @Before
    public void setUp() throws
                        IOException,
                        InvalidNameException,
                        DuplicateEmailException,
                        InvalidPasswordException,
                        InvalidEmailException,
                        AccountCreatorException
    {
        this.tempDB = TestUtils.copyDB();
        final IAccountPersistence accountPersistence
                = new AccountHSQLDB(this.tempDB
                                            .getAbsolutePath()
                                            .replace(".script", ""));
        final IStudentPersistence studentPersistence
                = new StudentHSQLDB(this.tempDB
                                            .getAbsolutePath()
                                            .replace(".script", ""));
        final ITutorPersistence tutorPersistence = new TutorHSQLDB(this.tempDB
                                                                           .getAbsolutePath()
                                                                           .replace(
                                                                                   ".script",
                                                                                   ""));
        IAccountCreator accountCreator = new AccountCreator(accountPersistence,
                                                            studentPersistence,
                                                            tutorPersistence);
        this.accessStudents = new AccessStudents(studentPersistence);
        testStudent         = accountCreator.createStudentAccount("a@a.a",
                                                                  "a",
                                                                  "a",
                                                                  "a/am",
                                                                  "a");
    }

    @Test
    public void testGetStudentByStudentID()
    {
        IStudent testS
                =
                accessStudents.getStudentByStudentID(testStudent.getStudentID());
        assertEquals(testStudent.getStudentID(), testS.getStudentID());
    }

    @Test
    public void testGetStudents()
    {
        Map<Integer, IStudent> testStudents;
        testStudents = accessStudents.getStudents();
        assertEquals(1, testStudents.size());
    }

    @After
    public void tearDown()
    {
        this.tempDB.delete();
    }
}
