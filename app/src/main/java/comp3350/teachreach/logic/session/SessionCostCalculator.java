package comp3350.teachreach.logic.session;

import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;

public class SessionCostCalculator {
    private final ITutorProfileHandler tutorProfileHandler;

    public SessionCostCalculator(ITutorProfileHandler tutorProfileHandler) {
        this.tutorProfileHandler = tutorProfileHandler;
    }

    public double calculateSessionCost(int sessionDuration) {
        double tutorHourlyRate = tutorProfileHandler.getHourlyRate();
        return tutorHourlyRate * sessionDuration;
    }
}
