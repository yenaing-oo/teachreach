package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.teachreach.logic.BookingHandler;
import comp3350.teachreach.logic.SearchSort;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public class SearchSortTest {
    //set up

    private SearchSort searchSort;
    @Before
    public void setUp() {
        System.out.println("Starting test for SearchSort");
        searchSort = new SearchSort();
    }

    @Test
    public void testListOfTutor(){
        System.out.println("\nStarting testGetListOfTutor");
        ArrayList<Tutor> tutorList = searchSort.getListOfTutors();

        assertNotNull(tutorList);
        assertEquals(5, tutorList.size());

        System.out.println("Finished testGetListOfTutor");
    }

    @Test
    public void testListOfCourse(){
        System.out.println("\nStarting testGetListOfTutor");
        ArrayList<Course> courseList = searchSort.getListOfCourses();

        assertNotNull(courseList);
        assertEquals(5, courseList.size());

        System.out.println("Finished testGetListOfTutor");
    }



    @Test
    public void testSearchTutorByCourse(){
        System.out.println("\nStarting testSearchTutorByCourse");

        ArrayList<Tutor> tutorList = searchSort.searchTutorByCourse("COMP 2080");
        //the course is not implemented yet!
        assertNotNull(tutorList);
        assertEquals(1, tutorList.size());
        //assrtEquals();

        System.out.println("Finished testSearchTutorByCourse");
    }

    @Test
    public void testSearchTutorByCourseNull(){
        System.out.println("\nStarting testSearchTutorByCourseNull");

        ArrayList<Tutor> tutorList = searchSort.searchTutorByCourse("12345");
        //the course is not implemented yet!
        assertNull(tutorList);
        assertEquals(0, tutorList.size());
        //assrtEquals();

        System.out.println("Finished testSearchTutorByCourseNull");
    }

    @Test
    public void testSortByRating(){
        System.out.println("\nStarting testSortByRating");

        ArrayList<Tutor> tutorList;
        tutorList = searchSort.sortByRating();
        //**not implement yet!**
        assertNotNull(tutorList);
        assertEquals(1, tutorList.size());
        //assrtEquals();

        System.out.println("Finished testSortByRating()");
    }

    @Test
    public void testSortByPrice(){
        System.out.println("\nStarting testSortByPrice");

        ArrayList<Tutor> tutorList;
        tutorList = searchSort.sortByPrice();

        assertNotNull(tutorList);
        assertEquals(5, tutorList.size());
        assertEquals(11,tutorList.get(1).getHourlyRate(),0);
        assertEquals(13.5,tutorList.get(2).getHourlyRate(),0);
        assertEquals(17.5, tutorList.get(3).getHourlyRate(),0);
        assertEquals(20, tutorList.get(4).getHourlyRate(),0);
        assertEquals(40.5, tutorList.get(5).getHourlyRate(),0);

        System.out.println("Finished testSortByPrice()");
    }


    /*
    public void testTutorByAvail(){
        System.out.println("\nStarting testTutorByAvail");

        ArrayList<Tutor> tutorList;
        boolean [][] avail;

        tutorList = searchSort.tutorsByAvail(avail[1][3]);

        assertNotNull(tutorList);
        assertEquals(5, tutorList.size());
        assertEquals(11,tutorList.get(1).getHourlyRate(),0);
        assertEquals(13.5,tutorList.get(2).getHourlyRate(),0);
        assertEquals(17.5, tutorList.get(3).getHourlyRate(),0);
        assertEquals(20, tutorList.get(4).getHourlyRate(),0);
        assertEquals(40.5, tutorList.get(5).getHourlyRate(),0);

        System.out.println("Finished testSortByPrice()");
    }
    */


    //tutorsByAvail(boolean[][] avail)
// make test for retrieve tutor list
    //make test for retrueve course list
    //searchbyclass(object
    //searchbyclass(name
    //sort by price
    //sort by rating
    //search tutor by availability?? if yes then make some availability false
}
