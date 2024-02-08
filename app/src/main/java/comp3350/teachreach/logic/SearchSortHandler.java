package comp3350.teachreach.logic;

import comp3350.teachreach.data.CourseStub;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;
import java.util.ArrayList;

public class SearchSortHandler {
  private final IAccountPersistence dataAccessTutor;
  private final CourseStub dataAccessCourse;

  public SearchSortHandler() {
    dataAccessTutor = Server.getAccounts();
    dataAccessCourse = Server.getCourses();
  }

  public SearchSortHandler(IAccountPersistence tutorDataAccess, CourseStub courseDataAccess) {
    dataAccessTutor = tutorDataAccess;
    dataAccessCourse = courseDataAccess;
  }

  public ArrayList<Tutor> getListOfTutors() {
    return dataAccessTutor.getTutors();
  }

  public ArrayList<Course> getListOfCourses() {
    return dataAccessCourse.getCourses();
  }

  public ArrayList<Tutor> searchTutorByCourse(String courseCode) {
    ArrayList<Tutor> selectedTutor = new ArrayList<>();
    ArrayList<Tutor> tutors = dataAccessTutor.getTutors();

    for (int i = 0; i < tutors.size(); i++) {
      ArrayList<Course> tutorCourses = tutors.get(i).getCourses();
      if (tutorCourses != null) {
        int j = 0;
        boolean found = false;
        while (!found && j < tutorCourses.size()) {
          if (tutorCourses.get(j).getCourseCode().equals((courseCode))) {
            selectedTutor.add(tutors.get(i));
            found = true;
          }
          j++;
        }
      }
    }
    return selectedTutor;
  }
}
