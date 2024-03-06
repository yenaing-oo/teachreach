package comp3350.teachreach.data.interfaces;

import java.util.Map;

import comp3350.teachreach.objects.interfaces.ITutor;

public
interface ITutorPersistence
{
    ITutor storeTutor(ITutor newTutor) throws RuntimeException;

    ITutor updateTutor(ITutor newTutor) throws RuntimeException;

    Map<Integer, ITutor> getTutors();
}
