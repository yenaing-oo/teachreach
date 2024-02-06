
import comp3350.teachreach.objects;

import java.util.ArrayList;

public class SearchSort {

    public ArrayList<Tutor> searchTutorClass(ArrayList<Tutor> tutors, Course course) {
        ArrayList<Tutor> output = new ArrayList<Tutor>();
        boolean flag = false;
        for(int i=0; i<tutors.size(); i++) {
            ArrayList<Course> tutored = tutors[i].getCourses
            flag = false;
            for(int j=0; j<tutored.size(); j++) {
                flag |= tutored[j].equals(course);
            }
            if(flag) {
                output.add(tutors[i]);
            }
        }
        
        return output;
    }

    public ArrayList<Tutor> sortByRating(ArrayList<Tutor> tutors) {
        
    }
}
