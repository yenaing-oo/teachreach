package comp3350.teachreach.logic;

import java.util.ArrayList;

import comp3350.teachreach.data.CourseStub;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public class SearchSortHandler {
    private final IAccountPersistence dataAccessTutor;
    private final CourseStub dataAccessCourse;

    public SearchSortHandler() {
        dataAccessTutor = Server.getAccounts();
        dataAccessCourse = Server.getCourses();
    }

    public SearchSortHandler(IAccountPersistence tutorDataAccess,
                             CourseStub courseDataAccess) {
        dataAccessTutor = tutorDataAccess;
        dataAccessCourse = courseDataAccess;
    }

    public ArrayList<Tutor> getListOfTutors() {
        return dataAccessTutor.getTutors();
    }

    public ArrayList<Course> getListOfCourses() {
        return dataAccessCourse.getCourses();
    }

//    public ArrayList<Tutor> searchTutorClass(Course course) {
//        ArrayList<Tutor> output = new ArrayList<Tutor>();
//        boolean flag = false;
//        for (int i = 0; i < dataAccessTutor.getTutors().size(); i++) {
//            ArrayList<Course> tutored = dataAccessTutor.getTutors().get(i).getCourses();
//            flag = false;
//            for (int j = 0; j < tutored.size(); j++) {
//                flag |= tutored.get(j).equals(course);
//            }
//            if (flag) {
//                output.add(dataAccessTutor.getTutors().get(i));
//            }
//        }
//
//        return output;
//    }

//    public ArrayList<Tutor> sortByRating() {
//        return ratingMergeSort(dataAccessTutor.getTutors());
//    }
//
//    private ArrayList<Tutor> ratingMergeSort(ArrayList<Tutor> tutors) {
//        if (tutors.size() <= 1) {
//            return tutors;
//        } else {
//            int middle = (tutors.size() - 1) / 2;
//            ArrayList<Tutor> left = ratingMergeSort((ArrayList<Tutor>) tutors.subList(0, middle));
//            ArrayList<Tutor> right = ratingMergeSort((ArrayList<Tutor>) tutors.subList(middle + 1, tutors.size() - 1));
//            return ratingMerge(left, right);
//        }
//    }
//
//    private ArrayList<Tutor> ratingMerge(ArrayList<Tutor> left, ArrayList<Tutor> right) {
//        int leftCount = 0;
//        int rightCount = 0;
//        ArrayList<Tutor> out = new ArrayList<Tutor>();
//
//        while (leftCount < left.size() && rightCount < right.size()) {
//            if (right.get(rightCount).getRating() > left.get(leftCount).getRating()) {
//                out.add(right.get(rightCount));
//                rightCount++;
//            } else {
//                out.add(left.get(leftCount));
//                leftCount++;
//            }
//        }
//        while (leftCount < left.size()) {
//            out.add(left.get(leftCount));
//            leftCount++;
//        }
//        while (rightCount < right.size()) {
//            out.add(left.get(leftCount));
//            rightCount++;
//        }
//
//        return out;
//    }

//    public ArrayList<Tutor> sortByPrice() {
//        return priceMergeSort(dataAccessTutor.getTutors());
//    }

//    private ArrayList<Tutor> priceMergeSort(ArrayList<Tutor> tutors) {
//        if (dataAccessTutor.getTutors().size() <= 1) {
//            return dataAccessTutor.getTutors();
//        } else {
//            int middle = (dataAccessTutor.getTutors().size() - 1) / 2;
//            ArrayList<Tutor> left = priceMergeSort((ArrayList<Tutor>) dataAccessTutor.getTutors().subList(0, middle));
//            ArrayList<Tutor> right = priceMergeSort((ArrayList<Tutor>) dataAccessTutor.getTutors().subList(middle + 1, tutors.size() - 1));
//            return priceMerge(left, right);
//        }
//    }

//    private ArrayList<Tutor> priceMerge(ArrayList<Tutor> left, ArrayList<Tutor> right) {
//        int leftCount = 0;
//        int rightCount = 0;
//        ArrayList<Tutor> out = new ArrayList<Tutor>();
//
//        while (leftCount < left.size() && rightCount < right.size()) {
//            if (right.get(rightCount).getHourlyRate() < left.get(leftCount).getHourlyRate()) {
//                out.add(right.get(rightCount));
//                rightCount++;
//            } else {
//                out.add(left.get(leftCount));
//                leftCount++;
//            }
//        }
//        while (leftCount < left.size()) {
//            out.add(left.get(leftCount));
//            leftCount++;
//        }
//        while (rightCount < right.size()) {
//            out.add(left.get(leftCount));
//            rightCount++;
//        }
//
//        return out;
//    }

//    public ArrayList<Tutor> tutorsByAvail(boolean[][] avail) {
//        ArrayList<Tutor> out = new ArrayList<Tutor>();
//        ArrayList<Tutor> copy = dataAccessTutor.getTutors();
//        for (int i = 0; i < avail.length; i++) {
//            for (int j = 0; j < avail[i].length; i++) {
//                if (avail[i][j] && copy.size() > 0) {
//                    for (int x = copy.size() - 1; x >= 0; x--) {
//                        if (copy.get(x).getAvailability()[i][j]) {
//                            out.add(copy.get(x));
//                            copy.remove(x);
//                        }
//                    }
//                }
//            }
//        }
//
//        return out;
//    }

    public ArrayList<Tutor> searchTutorByCourse(String courseCode) {
        //take arraylist
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
