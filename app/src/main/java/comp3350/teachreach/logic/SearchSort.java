package comp3350.teachreach.logic;

import java.util.ArrayList;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;

public class SearchSort {

    public ArrayList<Tutor> searchTutorClass(ArrayList<Tutor> tutors, Course course) {
        ArrayList<Tutor> output = new ArrayList<Tutor>();
        boolean flag = false;
        for (int i = 0; i < tutors.size(); i++) {
            ArrayList<Course> tutored = tutors.get(i).getCourses();
            flag = false;
            for (int j = 0; j < tutored.size(); j++) {
                flag |= tutored.get(j).equals(course);
            }
            if (flag) {
                output.add(tutors.get(i));
            }
        }

        return output;
    }

    public ArrayList<Tutor> sortByRating(ArrayList<Tutor> tutors) {
        return ratingMergeSort(tutors);
    }

    private ArrayList<Tutor> ratingMergeSort(ArrayList<Tutor> tutors) {
        if (tutors.size() <= 1) {
            return tutors;
        } else {
            int middle = (tutors.size() - 1) / 2;
            ArrayList<Tutor> left = ratingMergeSort((ArrayList<Tutor>) tutors.subList(0, middle));
            ArrayList<Tutor> right = ratingMergeSort((ArrayList<Tutor>) tutors.subList(middle + 1, tutors.size() - 1));
            return ratingMerge(left, right);
        }
    }

    private ArrayList<Tutor> ratingMerge(ArrayList<Tutor> left, ArrayList<Tutor> right) {
        int leftCount = 0;
        int rightCount = 0;
        ArrayList<Tutor> out = new ArrayList<Tutor>();

        while (leftCount < left.size() && rightCount < right.size()) {
            if (right.get(rightCount).getRating() > left.get(leftCount).getRating()) {
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
            out.add(left.get(leftCount));
            rightCount++;
        }

        return out;
    }

    public ArrayList<Tutor> sortByPrice(ArrayList<Tutor> tutors) {
        return priceMergeSort(tutors);
    }

    private ArrayList<Tutor> priceMergeSort(ArrayList<Tutor> tutors) {
        if (tutors.size() <= 1) {
            return tutors;
        } else {
            int middle = (tutors.size() - 1) / 2;
            ArrayList<Tutor> left = priceMergeSort((ArrayList<Tutor>) tutors.subList(0, middle));
            ArrayList<Tutor> right = priceMergeSort((ArrayList<Tutor>) tutors.subList(middle + 1, tutors.size() - 1));
            return priceMerge(left, right);
        }
    }

    private ArrayList<Tutor> priceMerge(ArrayList<Tutor> left, ArrayList<Tutor> right) {
        int leftCount = 0;
        int rightCount = 0;
        ArrayList<Tutor> out = new ArrayList<Tutor>();

        while (leftCount < left.size() && rightCount < right.size()) {
            if (right.get(rightCount).getHourlyRate() < left.get(leftCount).getHourlyRate()) {
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
            out.add(left.get(leftCount));
            rightCount++;
        }

        return out;
    }

    public ArrayList<Tutor> tutorsByAvail(ArrayList<Tutor> tutors, boolean[][] avail) {
        ArrayList<Tutor> out = new ArrayList<Tutor>();
        ArrayList<Tutor> copy = (ArrayList<Tutor>) tutors.clone();
        for (int i = 0; i < avail.length; i++) {
            for (int j = 0; j < avail[i].length; i++) {
                if (avail[i][j] && copy.size() > 0) {
                    for (int x = copy.size() - 1; x >= 0; x--) {
                        if (copy.get(x).getAvailability()[i][j]) {
                            out.add(copy.get(x));
                            copy.remove(x);
                        }
                    }
                }
            }
        }

        return out;
    }
}
