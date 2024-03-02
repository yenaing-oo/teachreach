//package comp3350.teachreach.tests.logic;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import comp3350.teachreach.data.AccountStub;
//import comp3350.teachreach.data.CourseStub;
//import comp3350.teachreach.data.IAccountPersistence;
//import comp3350.teachreach.data.ITutorPersistence;
//import comp3350.teachreach.data.TutorStub;
//import comp3350.teachreach.logic.SearchSortHandler;
//import comp3350.teachreach.objects.Course;
//import comp3350.teachreach.objects.ITutor;
//import comp3350.teachreach.objects.Tutor;
//
//public class SearchSortHandlerTest {
//    //set up
//
//    private SearchSortHandler searchSortHandler;
//    private ITutorPersistence tutorStub;
//    private CourseStub courseStub;
//
//    @Before
//    public void setUp() {
//        System.out.println("Starting test for SearchSortHandler");
//        tutorStub = new TutorStub();
//        courseStub = new CourseStub();
//        searchSortHandler = new SearchSortHandler(tutorStub, courseStub);
//    }
//
//    @Test
//    public void testListOfTutor() {
//        System.out.println("\nStarting testGetListOfTutor");
//        ArrayList<ITutor> tutorList = searchSortHandler.getListOfTutors();
//
//        assertNotNull(tutorList);
//        assertEquals(5, tutorList.size());
//
//        System.out.println("Finished testGetListOfTutor");
//    }
//
//    @Test
//    public void testListOfCourse() {
//        System.out.println("\nStarting testGetListOfTutor");
//        ArrayList<Course> courseList = searchSortHandler.getListOfCourses();
//
//        assertNotNull(courseList);
//        assertEquals(5, courseList.size());
//
//        System.out.println("Finished testGetListOfTutor");
//    }
//
//
//    @Test
//    public void testSearchTutorByCourse() {
//        System.out.println("\nStarting testSearchTutorByCourse");
//        tutorStub.updateTutor(tutorStub.getTutors().get(0).addCourse(new Course("2080", "COMP " + "2080")));
//
//        ArrayList<Tutor> tutorList = searchSortHandler.searchTutorByCourse("2080");
//        assertNotNull(tutorList);
//        assertEquals(1, tutorList.size());
//        assertEquals(tutorList.get(0), tutorStub.getTutors().get(0));
//
//        System.out.println("Finished testSearchTutorByCourse");
//    }
//
//    @Test
//    public void testSearchTutorByCourseNull() {
//        System.out.println("\nStarting testSearchTutorByCourseNull");
//
//        ArrayList<Tutor> tutorList = searchSortHandler.searchTutorByCourse("12345");
//
//        assertEquals(0, tutorList.size());
//
//        System.out.println("Finished testSearchTutorByCourseNull");
//    }
//
////    @Test
////    public void testSortByRating() {
////        System.out.println("\nStarting testSortByRating");
////
////        ArrayList<Tutor> tutorList;
////        tutorList = searchSortHandler.sortByRating();
////        //**not implement yet!**
////        assertNotNull(tutorList);
////        assertEquals(1, tutorList.size());
////        //assrtEquals();
////
////        System.out.println("Finished testSortByRating()");
////    }
////
////    @Test
////    public void testSortByPrice() {
////        System.out.println("\nStarting testSortByPrice");
////
////        ArrayList<Tutor> tutorList;
////        tutorList = searchSortHandler.sortByPrice();
////
////        assertNotNull(tutorList);
////        assertEquals(5, tutorList.size());
////        assertEquals(11, tutorList.get(1).getHourlyRate(), 0);
////        assertEquals(13.5, tutorList.get(2).getHourlyRate(), 0);
////        assertEquals(17.5, tutorList.get(3).getHourlyRate(), 0);
////        assertEquals(20, tutorList.get(4).getHourlyRate(), 0);
////        assertEquals(40.5, tutorList.get(5).getHourlyRate(), 0);
////
////        System.out.println("Finished testSortByPrice()");
////    }
//}
