package comp3350.teachreach.tests.unitTests.logic.session;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.threeten.bp.LocalDate;

import comp3350.teachreach.logic.DAOs.AccessSessions;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.exceptions.availability.TutorAvailabilityManagerException;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.logic.session.SessionHandler;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

@RunWith(MockitoJUnitRunner.class)
public class SessionHandlerTest {

    @Mock
    private ITutorAvailabilityManager mockTutorAvailabilityManager;

    @Mock
    private AccessSessions mockAccessSessions;

    @Mock
    private AccessTutors mockAccessTutors;

    @InjectMocks
    private SessionHandler sessionHandler;

    @Test
    public void testBookSession() throws TutorAvailabilityManagerException {
        ITutor tutor = new Tutor(123, 123);
        LocalDate date = LocalDate.of(2022, 3, 29);
        ITimeSlice availableTimeRange = new TimeSlice(date.atTime(10, 0), date.atTime(12, 0));
        ITimeSlice sessionTimeRange = new TimeSlice(date.atTime(11, 0), date.atTime(11, 30));
        ISession session = new Session(1, 321, 123, sessionTimeRange, 12.0, "Library", 0);

        // Set up
        when(mockAccessTutors.getTutorByTutorID(123)).thenReturn(tutor);
        when(mockTutorAvailabilityManager.isAvailableAt(tutor, sessionTimeRange)).thenReturn(availableTimeRange);
        when(mockAccessSessions.storeSession(session)).thenReturn(session);

        // Call method
        ISession result = sessionHandler.bookSession(session);

        // Verify interactions
        verify(mockTutorAvailabilityManager).removeAvailability(any(ITutor.class), any(ITimeSlice.class));
        verify(mockTutorAvailabilityManager, times(2)).addAvailability(any(ITutor.class), any(ITimeSlice.class));
        verify(mockAccessSessions).storeSession(session);

        // Check result
        assertEquals(session, result);
    }

}

