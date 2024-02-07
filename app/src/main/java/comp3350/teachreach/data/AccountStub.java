package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountStub implements IAccountPersistence {
    ArrayList<Tutor> tutors;
    ArrayList<Student> students;

    public AccountStub() {
        this.tutors.add(new Tutor("Jackson Pankratz", "He/Him", "Computer Science", "pankratz25@myumanitoba.ca", "password", 13.5));
        this.tutors.add(new Tutor("Camryn Mcmillan", "She/Her", "Computer Science", "mcmill5@myumanitoba.ca", "password", 20));
        this.tutors.add(new Tutor("Justin Huang", "He/Him", "Computer Science", "huang15@myumanitoba.ca", "password", 17.5));
        this.tutors.add(new Tutor("Ashna Sharma", "She/Her", "Computer Science", "sharma7@myumanitoba.ca", "password", 11));
        this.tutors.add(new Tutor("Theo Brown", "They/Them", "Computer Science", "brown102@myumanitoba.ca", "password", 40.5));

        Random rand = new Random(7881702);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 24; k++) {
                    this.tutors.get(i).setAvailability(j, k, rand.nextInt(2) == 1);
                }
            }
            //retrieve course object, then input it
            // this.tutors.get(i).setCourses(courses);

            Student student1 = new Student("Rob Guderian", "He/Him", "Computer Science", "guder4@myumanitoba.ca", "password");
            this.students.add(student1);
        }
    }

    public Student storeStudent(Student newStudent) {
        this.students.add(newStudent);
        return newStudent;
    }

    public Student updateStudent(Student newStudent) {
        Student theStudent = getStudentByEmail(newStudent.getEmail());

        if (theStudent != null) {
            theStudent = newStudent;
        }

        return theStudent;
    }

    public Student getStudentByEmail(String email) {
        Iterator<Student> iterator = students.iterator();
        Student currentStudent = null;
        boolean found = false;

        while (!found && iterator.hasNext()) {
            currentStudent = iterator.next();
            found = currentStudent.getEmail().equals(email);
        }
        return currentStudent;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public Tutor storeTutor(Tutor newTutor) {
        tutors.add(newTutor);
        return newTutor;
    }

    public Tutor updateTutor(Tutor newTutor) {
        Tutor theTutor = getTutorByEmail(newTutor.getEmail());

        if (theTutor != null) {
            theTutor = newTutor;
        }

        return theTutor;
    }

    public Tutor getTutorByEmail(String email) {
        Iterator<Tutor> iterator = tutors.iterator();
        Tutor currentTutor = null;
        boolean found = false;

        while (!found && iterator.hasNext()) {
            currentTutor = iterator.next();
            found = currentTutor.getEmail().equals(email);
        }

        return currentTutor;
    }

    public ArrayList<Tutor> getTutors() {
        return this.tutors;
    }

    public ArrayList<Tutor> getTutorsByName(String name) {
        ArrayList<Tutor> result = new ArrayList<Tutor>();

        tutors.forEach((tutor) -> {
            if (tutor.getName().contains(name)) {
                result.add(tutor);
            }
        });

        return result;
    }
    //search tutor
//    public Tutor searchTutorbyName(Tutor tutor) {
//        Tutor Searched = null;
//        for (int i = 0; i < tutors.size(); i++) {
//            if (tutors.get(i).getName().equals(tutor.getName())) {
//                Searched = tutors.get(i);
//            }
//        }
//        return Searched;
//    }
    //search students
}