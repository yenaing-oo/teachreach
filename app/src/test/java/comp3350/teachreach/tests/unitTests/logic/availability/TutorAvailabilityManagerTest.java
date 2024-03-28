package comp3350.teachreach.tests.unitTests.logic.availability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.logic.DAOs.AccessTutorAvailability;
import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.exceptions.availability.TutorAvailabilityManagerException;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

@RunWith(MockitoJUnitRunner.class)
public class TutorAvailabilityManagerTest {

    @Mock
    private AccessTutorAvailability mockAccessTutorAvailability;

    @InjectMocks
    private TutorAvailabilityManager availabilityManager;

    @Mock
    private ITutor mockTutor;

    @Mock
    private ITimeSlice mockTimeSlice;

    @Test
    public void testAddAvailability_Successful() throws TutorAvailabilityManagerException {
        LocalDate requestedDate = LocalDate.of(2022, 3, 29);

        // Configure mock behavior
        List<ITimeSlice> existingAvailability = new ArrayList<>();
        existingAvailability.add(new TimeSlice(requestedDate.atTime(9, 0), requestedDate.atTime(10, 0)));
        when(mockAccessTutorAvailability.getAvailability(mockTutor)).thenReturn(existingAvailability);

        ITimeSlice newTimeSlice = new TimeSlice(requestedDate.atTime(10, 0), requestedDate.atTime(11, 0));
        // Call method under test
        availabilityManager.addAvailability(mockTutor, newTimeSlice);

        // Verify that the addAvailability method of AccessTutorAvailability was called with correct arguments
        verify(mockAccessTutorAvailability).addAvailability(mockTutor, newTimeSlice);
    }

    @Test
    public void testAddAvailability_Overlap() {
        LocalDate requestedDate = LocalDate.of(2022, 3, 29);

        // Configure mock behavior
        List<ITimeSlice> existingAvailability = new ArrayList<>();
        existingAvailability.add(new TimeSlice(requestedDate.atTime(9, 0), requestedDate.atTime(10, 0)));
        when(mockAccessTutorAvailability.getAvailability(mockTutor)).thenReturn(existingAvailability);

        // Call method under test and verify that it throws an exception
        assertThrows(TutorAvailabilityManagerException.class, () ->
                availabilityManager.addAvailability(mockTutor, new TimeSlice(requestedDate.atTime(9, 30), requestedDate.atTime(10, 30)))
        );

        // Verify that the addAvailability method of AccessTutorAvailability was not called
        verify(mockAccessTutorAvailability, never()).addAvailability(any(), any());
    }

    @Test
    public void testRemoveAvailability_Successful() throws TutorAvailabilityManagerException {
        // Configure mock behavior
        List<ITimeSlice> existingAvailability = new ArrayList<>();
        existingAvailability.add(mockTimeSlice);
        when(mockAccessTutorAvailability.getAvailability(mockTutor)).thenReturn(existingAvailability);

        // Call method under test
        availabilityManager.removeAvailability(mockTutor, mockTimeSlice);

        // Verify that the removeAvailability method of AccessTutorAvailability was called with correct arguments
        verify(mockAccessTutorAvailability).removeAvailability(mockTutor, mockTimeSlice);
    }

    @Test
    public void testRemoveAvailability_NotExists() {
        // Configure mock behavior
        List<ITimeSlice> existingAvailability = new ArrayList<>();
        when(mockAccessTutorAvailability.getAvailability(mockTutor)).thenReturn(existingAvailability);

        // Call method under test and verify that it throws an exception
        assertThrows(TutorAvailabilityManagerException.class, () ->
                availabilityManager.removeAvailability(mockTutor, mockTimeSlice));

        // Verify that the removeAvailability method of AccessTutorAvailability was not called
        verify(mockAccessTutorAvailability, never()).removeAvailability(any(), any());
    }

    @Test
    public void testIsAvailableAt_Available() {
        LocalDate requestedDate = LocalDate.of(2022, 3, 29);

        // Create a mock time slice representing the requested time range
        ITimeSlice requestedTimeSlice = new TimeSlice(requestedDate.atTime(9, 0), requestedDate.atTime(9, 30));

        // Create a list of available time slices for the tutor
        List<ITimeSlice> availability = new ArrayList<>();
        ITimeSlice availableTimeRange = new TimeSlice(requestedDate.atTime(9, 0), requestedDate.atTime(11, 0));
        availability.add(availableTimeRange);

        // Configure mock behavior to return the list of available time slices
        when(mockAccessTutorAvailability.getAvailability(mockTutor)).thenReturn(availability);

        // Call method under test
        ITimeSlice result = availabilityManager.isAvailableAt(mockTutor, requestedTimeSlice);

        // Verify that the result is the same as the requested time slice
        assertEquals(availableTimeRange, result);
    }

    @Test
    public void testIsAvailableAt_NotAvailable() {

        // Create a mock time slice representing the requested time range
        ITimeSlice requestedTimeSlice = Mockito.mock(ITimeSlice.class);

        // Create a list of available time slices for the tutor
        List<ITimeSlice> availability = new ArrayList<>();

        // Configure mock behavior to return the list of available time slices
        when(mockAccessTutorAvailability.getAvailability(mockTutor)).thenReturn(availability);

        // Call method under test
        ITimeSlice result = availabilityManager.isAvailableAt(mockTutor, requestedTimeSlice);

        // Verify that the result is null (indicating not available)
        assertNull(result);
    }

    @Test
    public void testGetAvailabilityOnDay() {
        ITutor mockTutor = Mockito.mock(ITutor.class);
        ITimeSlice mockAvailableTimeSlice = Mockito.mock(ITimeSlice.class);
        LocalDate requestedDate = LocalDate.of(2022, 3, 29);

        List<ITimeSlice> availabilityOnDay = new ArrayList<>();
        availabilityOnDay.add(mockAvailableTimeSlice);

        // Configure mock behavior to return the list of available time slices
        when(mockAccessTutorAvailability.getAvailabilityOnDay(mockTutor, requestedDate)).thenReturn(availabilityOnDay);

        // Call method under test
        List<ITimeSlice> result = availabilityManager.getAvailabilityOnDay(mockTutor, requestedDate);

        // Verify that the result matches the expected availability on the requested date
        assertEquals(availabilityOnDay, result);
        assertEquals(1, result.size());
        assertEquals(availabilityOnDay.get(0), result.get(0));
    }

    @Test
    public void testGetAvailabilityAsSlots() {
        // Create a mock tutor
        ITutor mockTutor = Mockito.mock(ITutor.class);

        // Create a LocalDate object representing the requested date
        LocalDate requestedDate = LocalDate.of(2022, 3, 29);

        ITimeSlice timeSlice = new TimeSlice(requestedDate.atTime(9, 0), requestedDate.atTime(10, 0));
        // Create a list of available time slices for the tutor on the requested date
        List<ITimeSlice> availabilityOnDay = new ArrayList<>();
        availabilityOnDay.add(timeSlice);

        // Configure mock behavior to return the list of available time slices
        when(mockAccessTutorAvailability.getAvailabilityOnDay(mockTutor, requestedDate)).thenReturn(availabilityOnDay);

        // Call method under test
        List<ITimeSlice> result = availabilityManager.getAvailabilityAsSlots(mockTutor, requestedDate);

        List<ITimeSlice> expectedResult = new ArrayList<>();
        expectedResult.add(new TimeSlice(requestedDate.atTime(9, 0), requestedDate.atTime(9, 30)));
        expectedResult.add(new TimeSlice(requestedDate.atTime(9, 30), requestedDate.atTime(10, 0)));

        // Verify that the result matches the expected availability on the requested date
        for (ITimeSlice timeSlot : result) {
            assertEquals(expectedResult.get(result.indexOf(timeSlot)).getStartTime(), timeSlot.getStartTime());
            assertEquals(expectedResult.get(result.indexOf(timeSlot)).getEndTime(), timeSlot.getEndTime());
        }
    }
}
