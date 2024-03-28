package comp3350.teachreach.tests.unitTests.logic.availability;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import comp3350.teachreach.application.TRData;
import comp3350.teachreach.logic.availability.TimeSlotGenerator;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ITimeSlice;

public class TimeSlotGeneratorTest {
    @Before
    public void setUp() {
        TRData.setDefaultEnums();
    }

    @Test
    public void testGenerateTimeSlots_singleTimeSlot() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 26, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 26, 9, 30);
        ITimeSlice timeRange = new TimeSlice(startTime, endTime);

        List<ITimeSlice> timeSlots = TimeSlotGenerator.generateTimeSlots(timeRange);

        assertEquals(1, timeSlots.size());

        assertEquals(startTime, timeSlots.get(0).getStartTime());
        assertEquals(endTime, timeSlots.get(0).getEndTime());
    }

    @Test
    public void testGenerateTimeSlots_threeTimeSlots() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 26, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 26, 10, 30);
        ITimeSlice timeRange = new TimeSlice(startTime, endTime);

        List<ITimeSlice> timeSlots = TimeSlotGenerator.generateTimeSlots(timeRange);

        assertEquals(3, timeSlots.size());

        assertEquals(startTime, timeSlots.get(0).getStartTime());
        assertEquals(LocalDateTime.of(2024, 3, 26, 9, 30), timeSlots.get(0).getEndTime());

        assertEquals(LocalDateTime.of(2024, 3, 26, 9, 30), timeSlots.get(1).getStartTime());
        assertEquals(LocalDateTime.of(2024, 3, 26, 10, 0), timeSlots.get(1).getEndTime());

        assertEquals(LocalDateTime.of(2024, 3, 26, 10, 0), timeSlots.get(2).getStartTime());
        assertEquals(endTime, timeSlots.get(2).getEndTime());
    }

    @Test
    public void testGenerateTimeSlots_noTimeSlot() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 26, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 26, 9, 5);
        ITimeSlice timeRange = new TimeSlice(startTime, endTime);

        List<ITimeSlice> timeSlots = TimeSlotGenerator.generateTimeSlots(timeRange);

        assertEquals(0, timeSlots.size());
    }

    @Test
    public void testGenerateTimeSlots_edgeCase() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 26, 8, 59);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 26, 9, 31);
        ITimeSlice timeRange = new TimeSlice(startTime, endTime);

        List<ITimeSlice> timeSlots = TimeSlotGenerator.generateTimeSlots(timeRange);

        assertEquals(1, timeSlots.size());

        assertEquals(LocalDateTime.of(2024, 3, 26, 9, 0), timeSlots.get(0).getStartTime());
        assertEquals(LocalDateTime.of(2024, 3, 26, 9, 30), timeSlots.get(0).getEndTime());
    }

}