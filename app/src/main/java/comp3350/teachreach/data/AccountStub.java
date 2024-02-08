package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
// import java.util.Random;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountStub implements IAccountPersistence {

    ArrayList<Tutor> tutors;
    ArrayList<Student> students;

    CourseStub accessCourseStub;

    ArrayList<Course> courses;


    public AccountStub() {
        tutors = new ArrayList<>();
        students = new ArrayList<>();
        accessCourseStub = new CourseStub();
        courses = accessCourseStub.getCourses();

        this.tutors.add(new Tutor("Jackson Pankratz", "He/Him", "Computer Science", "pankratz25@myumanitoba.ca", "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI.RdOKAHYdRhgiw7Z5YxTd6.beOG", 13.5));
        this.tutors.add(new Tutor("Camryn Mcmillan", "She/Her", "Computer Science", "mcmill5@myumanitoba.ca", "$2a$12$LMXSy2E2SRxXOyUzT2hwuOV..lVkQHj5sVFgrTQF4QpJWVbo9CBie", 20));
        this.tutors.add(new Tutor("Justin Huang", "He/Him", "Computer Science", "huang15@myumanitoba.ca", "$2a$12$r9yuopZw8rOLVK.L9FU6k.kaZu3GtrcTvc/PBNleKVcWx/YE6Hc4G", 17.5));
        this.tutors.add(new Tutor("Ashna Sharma", "She/Her", "Computer Science", "sharma7@myumanitoba.ca", "$2a$12$bnFp/uerz96t0CZwkRhNyuOFQTg54d9K0Pzkhdh/.8p2/ec1SFxjm", 11));
        this.tutors.add(new Tutor("Theo Brown", "They/Them", "Computer Science", "brown102@myumanitoba.ca", "$2a$12$i/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg.PXVGNnjF4ld46hJe", 40.5));

        //Random rand = new Random(7881702);

        for (int i = 0; i < 5; i++) {
            this.tutors.get(i).setCourses(courses.get(i));
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 24; k++) {
                    this.tutors.get(i).setAvailability(j, k, true);
                }
            }
        }



        this.tutors.get(1).setAvailability(1,1,false);

        this.tutors.get(1).setAvailability(1,2,false);
        this.tutors.get(1).setAvailability(1,3,false);
        this.tutors.get(1).setAvailability(1,4,false);
        this.tutors.get(1).setAvailability(1,5,false);
        System.out.println(Arrays.deepToString(this.tutors.get(1).getAvailability()));
        //this.tutors.get(1).setAvailability(1,6,true);

        Student student1 = new Student("Rob Guderian", "He/Him", "Computer Science", "guder4@myumanitoba.ca", "$2a$12$i/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg.PXVGNnjF4ld46hJe");
        this.students.add(student1);

    }

    @Override
    public Student storeStudent(Student newStudent) {
        boolean alreadyExist = getStudentByEmail(newStudent.getEmail()) != null;
        if (!alreadyExist) {
            this.students.add(newStudent);
        }
        return alreadyExist ? null : newStudent;
    }

    @Override
    public Student updateStudent(Student newStudent) {
        Student theStudent = getStudentByEmail(newStudent.getEmail());

        if (theStudent != null) {
            theStudent.setEmail(newStudent.getEmail());
            theStudent.setMajor(newStudent.getMajor());
            theStudent.setName(newStudent.getName());
            theStudent.setPronouns(newStudent.getPronouns());
        }

        return theStudent;
    }

    @Override
    public Student getStudentByEmail(String email) {
        Iterator<Student> iterator = students.iterator();
        Student currentStudent = null;
        boolean found = false;

        while (!found && iterator.hasNext()) {
            currentStudent = iterator.next();
            found = currentStudent.getEmail().equals(email);
        }
        return found ? currentStudent : null;
    }

    @Override
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public Tutor storeTutor(Tutor newTutor) {
        boolean alreadyExist = getStudentByEmail(newTutor.getEmail()) != null;
        if (!alreadyExist) {
            this.tutors.add(newTutor);
        }
        return alreadyExist ? null : newTutor;
    }

    @Override
    public Tutor updateTutor(Tutor newTutor) {
        Tutor theTutor = getTutorByEmail(newTutor.getEmail());

        if (theTutor != null) {
            theTutor = newTutor;
        }

        return theTutor;
    }

    @Override
    public Tutor getTutorByEmail(String email) {
        Iterator<Tutor> iterator = tutors.iterator();
        Tutor currentTutor = null;
        boolean found = false;

        while (!found && iterator.hasNext()) {
            currentTutor = iterator.next();
            found = currentTutor.getEmail().equals(email);
        }

        return found ? currentTutor : null;
    }

    @Override
    public ArrayList<Tutor> getTutors() {
        return this.tutors;
    }

    @Override
    public ArrayList<Tutor> getTutorsByName(String name) {
        ArrayList<Tutor> result = new ArrayList<>();

        tutors.forEach((tutor) -> {
            if (tutor.getName().contains(name)) {
                result.add(tutor);
            }
        });

        return result;
    }
}