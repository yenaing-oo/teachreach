package comp3350.teachreach.tests.logic.session;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import comp3350.teachreach.logic.session.SessionCostCalculator;
import comp3350.teachreach.objects.interfaces.ITutor;

public class SessionCostCalculatorTest {
    @Test
    public void testCalculateSessionCost() {
        // Create a mock tutor
        ITutor mockTutor = mock(ITutor.class);

        // Set up mock behavior
        double hourlyRate = 25.0; // Hourly rate of the tutor
        when(mockTutor.getHourlyRate()).thenReturn(hourlyRate);

        // Session duration in hours
        int sessionDurationInMinutes = 120;

        // Create an instance of SessionCostCalculator
        SessionCostCalculator calculator = new SessionCostCalculator();

        // Calculate the expected session cost
        double expectedCost = hourlyRate * sessionDurationInMinutes / 60.0;

        // Call the calculateSessionCost method and get the actual session cost
        double actualCost = calculator.calculateSessionCost(mockTutor, sessionDurationInMinutes);

        // Verify that the actual session cost matches the expected session cost
        assertEquals(expectedCost, actualCost, 0.001); // Using delta for double comparison
    }
}

