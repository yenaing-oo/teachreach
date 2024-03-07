package comp3350.teachreach.logic.DAOs;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutorAvailability;

public class AccessTutorAvailability {
    private static ITutorAvailability accessTutorAvailability;
    public
    AccessTutorAvailability()
    {
        accessTutorAvailability = Server.getStudentDataAccess();
        students           = studentPersistence.getStudents();
    }
}
