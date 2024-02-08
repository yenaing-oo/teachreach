package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import comp3350.teachreach.data.AccountStub;
import comp3350.teachreach.data.CourseStub;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class SearchSortHandlerTest {

  private SearchSortHandler searchSortHandler;
  private IAccountPersistence tutorStub;
  private CourseStub courseStub;

  @Before
  public void setUp() {
    System.out.println("Starting test for SearchSortHandler");
    tutorStub = new AccountStub();
    courseStub = new CourseStub();
    searchSortHandler = new SearchSortHandler(tutorStub, courseStub);
  }

  @Test
  public void testListOfTutor() {
    System.out.println("\nStarting testGetListOfTutor");
    ArrayList<Tutor> tutorList = searchSortHandler.getListOfTutors();

    assertNotNull(tutorList);
    assertEquals(5, tutorList.size());

    System.out.println("Finished testGetListOfTutor");
  }

  @Test
  public void testListOfCourse() {
    System.out.println("\nStarting testGetListOfTutor");
    ArrayList<Course> courseList = searchSortHandler.getListOfCourses();

    assertNotNull(courseList);
    assertEquals(5, courseList.size());

    System.out.println("Finished testGetListOfTutor");
  }

  @Test
  public void testSearchTutorByCourse() {
    System.out.println("\nStarting testSearchTutorByCourse");
    tutorStub.updateTutor(
        tutorStub.getTutors().get(0).addCourse(new Course("2080", "COMP " + "2080")));

    ArrayList<Tutor> tutorList = searchSortHandler.searchTutorByCourse("2080");
    assertNotNull(tutorList);
    assertEquals(1, tutorList.size());
    assertEquals(tutorList.get(0), tutorStub.getTutors().get(0));

    System.out.println("Finished testSearchTutorByCourse");
  }

  @Test
  public void testSearchTutorByCourseNull() {
    System.out.println("\nStarting testSearchTutorByCourseNull");

    ArrayList<Tutor> tutorList = searchSortHandler.searchTutorByCourse("12345");

    assertEquals(0, tutorList.size());

    System.out.println("Finished testSearchTutorByCourseNull");
  }
}
