package comp3350.teachreach.logic.DAOs;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.ITutorLocation;

public class AccessTutorLocation {
    private static ITutorLocation TutorLocationPersistence;

    public AccessTutorLocation(){
        TutorLocationPersistence = Server.getTutorLocationAccess();
    }

    public AccessTutorLocation(ITutorLocation tutorLocationAccess){
        AccessTutorLocation.TutorLocationPersistence = tutorLocationAccess;
    }
}
