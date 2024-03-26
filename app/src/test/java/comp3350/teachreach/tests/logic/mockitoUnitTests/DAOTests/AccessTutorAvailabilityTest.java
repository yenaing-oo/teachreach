package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
import comp3350.teachreach.logic.DAOs.AccessTutorAvailability;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public class AccessTutorAvailabilityTest {

    @Mock
    private ITutorAvailabilityPersistence tutorAvailabilityPersistence;

    @InjectMocks
    private AccessTutorAvailability accessTutorAvailability;

    @Test
    public void getAvailabilityTest() {
        List<ITimeSlice> returns1 = new ArrayList<>();
        ITutor tutor1 = new Tutor(1,1);
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 10, 0), LocalDateTime.of(2100, 12, 10, 18, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 11, 10, 0), LocalDateTime.of(2100, 12, 11, 18, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 12, 10, 0), LocalDateTime.of(2100, 12, 12, 18, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 12, 19, 0), LocalDateTime.of(2100, 12, 13, 21, 0)));
        List<ITimeSlice> returns2 = new ArrayList<>();
        ITutor tutor2 = new Tutor(1,1);
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 9, 0), LocalDateTime.of(2100, 12, 10, 10, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 10, 30), LocalDateTime.of(2100, 12, 10, 12, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 13, 0), LocalDateTime.of(2100, 12, 10, 15, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 18, 15), LocalDateTime.of(2100, 12, 1, 20,  30)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 12, 19, 0), LocalDateTime.of(2100, 12, 13, 21, 0)));


        when(tutorAvailabilityPersistence.getAvailability(tutor1)).thenReturn(returns1);
        when(tutorAvailabilityPersistence.getAvailability(tutor2)).thenReturn(returns2);

        List<ITimeSlice> results = accessTutorAvailability.getAvailability(tutor1);

        assertEquals("Issue with getAvailability results", results.size(), 4);

        results = accessTutorAvailability.getAvailability(tutor2);

        assertEquals("Issue with getAvailability results", results.size(), 5);
    }

    @Test
    public void getAvailabilityOnDayTest() {
        List<ITimeSlice> returns1 = new ArrayList<>();
        ITutor tutor1 = new Tutor(1,1);
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 10, 0), LocalDateTime.of(2100, 12, 10, 18, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 11, 10, 0), LocalDateTime.of(2100, 12, 11, 18, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 12, 10, 0), LocalDateTime.of(2100, 12, 12, 18, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 12, 19, 0), LocalDateTime.of(2100, 12, 13, 21, 0)));
        List<ITimeSlice> returns2 = new ArrayList<>();
        ITutor tutor2 = new Tutor(1,1);
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 9, 0), LocalDateTime.of(2100, 12, 10, 10, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 10, 30), LocalDateTime.of(2100, 12, 10, 12, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 13, 0), LocalDateTime.of(2100, 12, 10, 15, 0)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 10, 18, 15), LocalDateTime.of(2100, 12, 10, 20,  30)));
        returns1.add(new TimeSlice(LocalDateTime.of(2100, 12, 13, 19, 0), LocalDateTime.of(2100, 12, 13, 21, 0)));

        LocalDate date10 = LocalDate.of(2100, 12, 10);
        LocalDate date11 = LocalDate.of(2100, 12, 11);
        LocalDate date12 = LocalDate.of(2100, 12, 12);
        LocalDate date13 = LocalDate.of(2100, 12, 13);


        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor1, date10)).thenReturn(returns1.subList(0,1));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor1, date11)).thenReturn(returns2.subList(1,2));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor1, date12)).thenReturn(returns1.subList(2,4));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor1, date13)).thenReturn(returns2.subList(0,0));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor2, date10)).thenReturn(returns1.subList(0,4));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor2, date11)).thenReturn(returns2.subList(0,0));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor2, date12)).thenReturn(returns2.subList(4,5));
        when(tutorAvailabilityPersistence.getAvailabilityOnDay(tutor2, date13)).thenReturn(returns2.subList(0,0));

        List<ITimeSlice> results = accessTutorAvailability.getAvailabilityOnDay(tutor1,date10);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 1);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor2,date10);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 4);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor1,date11);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 1);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor2,date11);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 0);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor1,date12);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 2);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor2,date12);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 1);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor1,date13);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 0);

        results = accessTutorAvailability.getAvailabilityOnDay(tutor2,date13);

        assertEquals("Issues with getAvailabilityOnDay", results.size(), 1);
    }
}
