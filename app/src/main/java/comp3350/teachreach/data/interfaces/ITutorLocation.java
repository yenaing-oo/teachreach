package comp3350.teachreach.data.interfaces;

import java.util.List;

public interface ITutorLocation {
    List<String> getTutorLocationByTID(int tutor_id);

    boolean storeTutorLocation(int tutor_id, String location);
}
