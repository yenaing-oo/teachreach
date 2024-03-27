package comp3350.teachreach.logic.session;

import comp3350.teachreach.objects.interfaces.ITutor;

public
class SessionCostCalculator implements comp3350.teachreach.logic.interfaces.ISessionCostCalculator
{
    public
    SessionCostCalculator()
    {
    }

    @Override
    public double calculateSessionCost(ITutor t, int sessionDurationInMinutes) {
        double tutorHourlyRate = t.getHourlyRate();
        return tutorHourlyRate * sessionDurationInMinutes / 60.0;
    }
}
