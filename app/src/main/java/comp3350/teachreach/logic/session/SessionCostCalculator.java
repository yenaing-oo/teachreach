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
    public
    double calculateSessionCost(ITutor t, int sessionDuration)
    {
        double tutorHourlyRate = t.getHourlyRate();
        return tutorHourlyRate * sessionDuration;
    }
}
