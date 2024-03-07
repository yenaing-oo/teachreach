package comp3350.teachreach.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.data.interfaces.ITutoredCoursesPersistence;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutoredCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class SearchSortHandler implements ISearchSortHandler
{
    private final AccessTutoredCourses accessTutoredCourses;
    private final AccessTutors         accessTutors;
    private final AccessCourses        accessCourses;

    public
    SearchSortHandler()
    {
        accessTutoredCourses = new AccessTutoredCourses();
        accessTutors         = new AccessTutors();
        accessCourses        = new AccessCourses();
    }

    public
    SearchSortHandler(ITutoredCoursesPersistence tutoredCoursesDataAccess,
                      ITutorPersistence tutorPersistence,
                      ICoursePersistence coursePersistence)
    {
        accessTutoredCourses
                      = new AccessTutoredCourses(tutoredCoursesDataAccess);
        accessTutors  = new AccessTutors(tutorPersistence);
        accessCourses = new AccessCourses(coursePersistence);
    }

    @Override
    public
    List<ITutor> getTutors()
    {
        return new ArrayList<>(accessTutors.getTutors().values());
    }

    @Override
    public
    List<ICourse> getCourses()
    {
        return new ArrayList<>(accessCourses.getCourses().values());
    }

    @Override
    public
    List<ITutor> getTutorsByCourse(ICourse course)
    {

        List<ITutor> allTutors = this.getTutors();
        return allTutors
                .stream()
                .filter(t -> accessTutoredCourses
                        .getTutoredCoursesByTutorID(t.getTutorID())
                        .contains(course))
                .collect(Collectors.toList());

        //        for (ITutor tutor : allTutors) {
        //            List<ICourse> tutoredCourses = .getCourses();
        //            if (tutoredCourses.contains(course)) {
        //                output.add(tutor);
        //            }
        //        }
        //
        //        return output;
    }

    @Override
    public
    List<ITutor> getTutorsByRating()
    {
        return ratingMergeSort(this.getTutors());
    }

    @Override
    public
    List<ITutor> getTutorsByHourlyRateAsc()
    {
        return priceMergeSort(this.getTutors());
    }

    @Override
    public
    List<ITutor> getTutorsByHourlyRateDesc()
    {
        List<ITutor> result = priceMergeSort(this.getTutors());
        Collections.reverse(result);
        return result;
    }

    private
    List<ITutor> ratingMergeSort(List<ITutor> tutors)
    {
        if (tutors.size() <= 1) {
            return tutors;
        } else {
            int          middle = tutors.size() / 2;
            List<ITutor> left   = ratingMergeSort(tutors.subList(0, middle));
            List<ITutor> right = ratingMergeSort(tutors.subList(middle,
                                                                tutors.size()));
            return ratingMerge(left, right);
        }
    }

    private
    List<ITutor> ratingMerge(List<ITutor> left, List<ITutor> right)
    {
        int          leftCount  = 0;
        int          rightCount = 0;
        List<ITutor> out        = new ArrayList<>();

        while (leftCount < left.size() && rightCount < right.size()) {
            ITutorProfileHandler rightTutor = new TutorProfileHandler(right.get(
                    rightCount));
            ITutorProfileHandler leftTutor = new TutorProfileHandler(right.get(
                    leftCount));

            if (rightTutor.getAvgReview() > leftTutor.getAvgReview()) {
                out.add(right.get(rightCount));
                rightCount++;
            } else {
                out.add(left.get(leftCount));
                leftCount++;
            }
        }
        while (leftCount < left.size()) {
            out.add(left.get(leftCount));
            leftCount++;
        }
        while (rightCount < right.size()) {
            out.add(right.get(rightCount));
            rightCount++;
        }

        return out;
    }

    private
    List<ITutor> priceMergeSort(List<ITutor> tutors)
    {
        if (tutors.size() <= 1) {
            return tutors;
        } else {
            int          middle = (tutors.size() - 1) / 2;
            List<ITutor> left   = priceMergeSort(tutors.subList(0, middle));
            List<ITutor> right = priceMergeSort(tutors.subList(middle + 1,
                                                               tutors.size() -
                                                               1));
            return priceMerge(left, right);
        }
    }

    private
    List<ITutor> priceMerge(List<ITutor> left, List<ITutor> right)
    {
        int          leftCount  = 0;
        int          rightCount = 0;
        List<ITutor> out        = new ArrayList<>();

        while (leftCount < left.size() && rightCount < right.size()) {
            if (right.get(rightCount).getHourlyRate() <
                left.get(leftCount).getHourlyRate()) {
                out.add(right.get(rightCount));
                rightCount++;
            } else {
                out.add(left.get(leftCount));
                leftCount++;
            }
        }
        while (leftCount < left.size()) {
            out.add(left.get(leftCount));
            leftCount++;
        }
        while (rightCount < right.size()) {
            out.add(right.get(rightCount));
            rightCount++;
        }

        return out;
    }
}
