package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static comp3350.teachreach.objects.SessionStatus.ACCEPTED;
import static comp3350.teachreach.objects.SessionStatus.PENDING;
import static comp3350.teachreach.objects.SessionStatus.REJECTED;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.threeten.bp.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.logic.DAOs.AccessSessions;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ISession;

public class AccessSessionsTest {

    @Mock
    ISessionPersistence sessionPersistence;

    @InjectMocks
    AccessSessions accessSessions;

    @Test
    public void getSessionsTest() {
        Map<Integer, ISession> returns = new HashMap<>();
        returns.put(1, new Session(1, 1,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(2, new Session(2, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(3, new Session(3, 3,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(4, new Session(4, 4,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(5, new Session(5, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Library", REJECTED));
        returns.put(6, new Session(6, 6,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Library", REJECTED));
        returns.put(7, new Session(7, 7,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(8, new Session(8, 2,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(9, new Session(9, 9,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(10, new Session(10, 10,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(11, new Session(11, 11,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", REJECTED));
        returns.put(12, new Session(12, 12,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", REJECTED));
        when(sessionPersistence.getSessions()).thenReturn(returns);

        List<ISession> result = accessSessions.getSessions(new Tutor(1, 1));

        assertEquals("Issue with getSessions for tutor", result.size(), 6);

        result = accessSessions.getSessions(new Tutor(2, 2));

        assertEquals("Issue with getSessions for tutor", result.size(), 6);

        result = accessSessions.getSessions(new Student(2, 3));

        assertEquals("Issue with getSessions for student", result.size(), 3);

    }

    @Test
    public void getPendingSessionsTest() {
        Map<Integer, ISession> returns = new HashMap<>();
        returns.put(1, new Session(1, 1,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(2, new Session(2, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(3, new Session(3, 3,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(4, new Session(4, 4,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(5, new Session(5, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Library", REJECTED));
        returns.put(6, new Session(6, 6,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Library", REJECTED));
        returns.put(7, new Session(7, 7,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(8, new Session(8, 2,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(9, new Session(9, 9,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(10, new Session(10, 10,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(11, new Session(11, 11,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", REJECTED));
        returns.put(12, new Session(12, 12,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", REJECTED));
        when(sessionPersistence.getSessions()).thenReturn(returns);

        List<ISession> result = accessSessions.getPendingSessions(new Tutor(1, 1));

        assertEquals("Issue with getPendingSessions for tutor", result.size(), 2);

        result = accessSessions.getPendingSessions(new Tutor(2, 2));

        assertEquals("Issue with getPendingSessions for tutor", result.size(), 2);

        result = accessSessions.getPendingSessions(new Student(2, 3));

        assertEquals("Issue with getPendingSessions for student", result.size(), 2);
    }


    @Test
    public void getAcceptedSessionsTest() {
        Map<Integer, ISession> returns = new HashMap<>();
        returns.put(1, new Session(1, 1,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(2, new Session(2, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(3, new Session(3, 3,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(4, new Session(4, 4,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(5, new Session(5, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Library", REJECTED));
        returns.put(6, new Session(6, 6,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Library", REJECTED));
        returns.put(7, new Session(7, 7,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(8, new Session(8, 2,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(9, new Session(9, 9,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(10, new Session(10, 10,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(11, new Session(11, 11,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", REJECTED));
        returns.put(12, new Session(12, 12,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", REJECTED));
        when(sessionPersistence.getSessions()).thenReturn(returns);

        List<ISession> result = accessSessions.getAcceptedSessions(new Tutor(1, 1));

        assertEquals("Issue with getAcceptedSessions for tutor", result.size(), 2);

        result = accessSessions.getAcceptedSessions(new Tutor(2, 2));

        assertEquals("Issue with getAcceptedSessions for tutor", result.size(), 2);

        result = accessSessions.getAcceptedSessions(new Student(2, 3));

        assertEquals("Issue with getAcceptedSessions for Student", result.size(), 0);

    }

    @Test
    public void getRejectedSessionsTest() {
        Map<Integer, ISession> returns = new HashMap<>();
        returns.put(1, new Session(1, 1,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(2, new Session(2, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(3, new Session(3, 3,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(4, new Session(4, 4,1, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(5, new Session(5, 2,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Library", REJECTED));
        returns.put(6, new Session(6, 6,1, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Library", REJECTED));
        returns.put(7, new Session(7, 7,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", PENDING));
        returns.put(8, new Session(8, 2,4, new TimeSlice(LocalDateTime.of(2100, 12, 1, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", PENDING));
        returns.put(9, new Session(9, 9,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 12, 0 ), LocalDateTime.of(2100, 1, 2, 13, 0)), 15, "Library", ACCEPTED));
        returns.put(10, new Session(10, 10,5, new TimeSlice(LocalDateTime.of(2100, 12, 2, 10, 0 ), LocalDateTime.of(2100, 1, 2, 11, 0)), 15, "Library", ACCEPTED));
        returns.put(11, new Session(11, 11,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 12, 0 ), LocalDateTime.of(2100, 1, 1, 13, 0)), 15, "Coffee Shop", REJECTED));
        returns.put(12, new Session(12, 12,6, new TimeSlice(LocalDateTime.of(2100, 12, 3, 10, 0 ), LocalDateTime.of(2100, 1, 1, 11, 0)), 15, "Coffee Shop", REJECTED));
        when(sessionPersistence.getSessions()).thenReturn(returns);

        List<ISession> result = accessSessions.getRejectedSessions(new Tutor(1, 1));

        assertEquals("Issue with getRejectedSessions for tutor", result.size(), 2);

        result = accessSessions.getRejectedSessions(new Tutor(2, 2));

        assertEquals("Issue with getRejectedSessions for tutor", result.size(), 2);

        result = accessSessions.getRejectedSessions(new Tutor(2, 2));

        assertEquals("Issue with getRejectedSessions for student", result.size(), 1);

    }
}
