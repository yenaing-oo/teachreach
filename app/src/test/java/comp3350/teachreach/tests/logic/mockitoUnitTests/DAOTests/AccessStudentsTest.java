package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.DataAccessException;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.interfaces.IStudent;

public class AccessStudentsTest {

    @Mock
    private IStudentPersistence studentPersistence;

    @InjectMocks
    private AccessStudents accessStudents;

    @Test
    public void getStudentsTest() {
        Map<Integer, IStudent> returns = new HashMap<Integer, IStudent>();

        returns.put(1, new Student(1, 6));
        returns.put(2, new Student(2, 7));
        returns.put(3, new Student(3, 8));
        returns.put(4, new Student(4, 9));
        returns.put(5, new Student(5, 10));

        when(studentPersistence.getStudents()).thenReturn(returns);

        Map<Integer, IStudent> results = accessStudents.getStudents();

        assertEquals("Incorrect result from getStudents", results.size(), 5);
        assertEquals("Incorrect result from getStudents", results.get(1), 6);
        assertEquals("Incorrect result from getStudents", results.get(2), 7);
        assertEquals("Incorrect result from getStudents", results.get(3), 8);
        assertEquals("Incorrect result from getStudents", results.get(4), 9);
        assertEquals("Incorrect result from getStudents", results.get(5), 10);

        assertThrows("Result from getStudents is not an unmodifiable map", UnsupportedOperationException.class,
                () -> results.put(6, new Student(6, 11)));
    }

    @Test
    public void getStudentByAccountID() {
        Map<Integer, IStudent> returns = new HashMap<Integer, IStudent>();

        returns.put(1, new Student(1, 6));
        returns.put(2, new Student(2, 7));
        returns.put(3, new Student(3, 8));
        returns.put(4, new Student(4, 9));
        returns.put(5, new Student(5, 10));

        when(studentPersistence.getStudents()).thenReturn(returns);

        IStudent resultStudent = accessStudents.getStudentByAccountID(8);

        assertEquals("Incorrect result from getStudentByAccountID", resultStudent.getStudentID(), 3);

        assertThrows("DataAccessException expected, but not thrown", DataAccessException.class, () -> accessStudents.getStudentByAccountID(11));
    }

    @Test
    public void getStudentByStudentID() {
        Map<Integer, IStudent> returns = new HashMap<Integer, IStudent>();

        returns.put(1, new Student(1, 6));
        returns.put(2, new Student(2, 7));
        returns.put(3, new Student(3, 8));
        returns.put(4, new Student(4, 9));
        returns.put(5, new Student(5, 10));

        when(studentPersistence.getStudents()).thenReturn(returns);

        IStudent resultStudent = accessStudents.getStudentByAccountID(3);

        assertEquals("Incorrect result from getStudentByAccountID", resultStudent.getAccountID(), 8);

        assertThrows("DataAccessException expected, but not thrown", DataAccessException.class, () -> accessStudents.getStudentByStudentID(11));
    }
}
