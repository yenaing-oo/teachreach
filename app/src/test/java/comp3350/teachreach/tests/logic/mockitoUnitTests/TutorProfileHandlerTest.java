package comp3350.teachreach.tests.logic.mockitoUnitTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutorLocation;
import comp3350.teachreach.logic.DAOs.AccessTutoredCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ICourse;

@RunWith(MockitoJUnitRunner.class)
public class TutorProfileHandlerTest {

    @Mock
    private AccessTutors accessTutors;
    @Mock
    private AccessTutoredCourses accessTutoredCourses;
    @Mock
    private AccessTutorLocation accessTutorLocation;
    @Mock
    private AccessCourses accessCourses;

    @InjectMocks
    TutorProfileHandler tutorProfileHandler;

    @Test
    public void getCourseCodeListTest() {
        List<ICourse> returns = new ArrayList<>();

        returns.add(new Course("COMP 2080",
                "Analysis of Algorithms"));
        returns.add(new Course("COMP 1010",
                "Introduction to Computer Science"));
        returns.add(new Course("COMP 1012",
                "Introduction to Computer Science for Engineers"));
        returns.add(new Course("COMP 2150",
                "Object Orientation"));

        when(accessTutoredCourses.getTutoredCoursesByTutorID(anyInt())).thenReturn(returns);

        List<String> results = tutorProfileHandler.getCourseCodeList(new Tutor(1,1));

        assertEquals("Issues with getCourseCodeList", results.size(), 4);
    }
    @Test
    public void getCourseDescriptionListTest() {
        List<ICourse> returns = new ArrayList<>();

        returns.add(new Course("COMP 2080",
                 "Analysis of Algorithms"));
        returns.add(new Course("COMP 1010",
                 "Introduction to Computer Science"));
        returns.add(new Course("COMP 1012",
                 "Introduction to Computer Science for Engineers"));
        returns.add(new Course("COMP 2150",
                 "Object Orientation"));
        when(accessTutoredCourses.getTutoredCoursesByTutorID(anyInt())).thenReturn(returns);

        List<String> results = tutorProfileHandler.getCourseCodeList(new Tutor(1,1));

        assertEquals("Issues with getCourseDescriptionList", results.size(), 4);

    }
}
