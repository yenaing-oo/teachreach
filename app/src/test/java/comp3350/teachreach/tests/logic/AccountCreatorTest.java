package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.teachreach.logic.AccountCreator;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountCreatorTest {
    private AccountCreator accountCreator;

    @Before
    public void setUp() {
        System.out.println("Starting test for AccountCreator");
        accountCreator = new AccountCreator();
    }

    @Test
    public void testCreateStudent() {
        Account newStudent = accountCreator.createStudentAccount("Alice", "he" +
                "/him", "CS", "alice@example.com", "qwerasdfaadd");
        assertNotNull(newStudent);
        assertTrue("New account should be a student", newStudent instanceof Student);
        System.out.println("Finished testCreateStudent");
    }

    @Test
    public void testCreateTutor() {
        Account newTutor = accountCreator.createTutorAccount("Bob", "he/him",
                "CS", "bob@example.com", "qawsedrfccss");
        assertNotNull(newTutor);
        assertTrue("New account should be a Tutor", newTutor instanceof Tutor);
        System.out.println("Finished testCreateTutor");
    }
}
