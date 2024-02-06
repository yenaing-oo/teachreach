import comp3350.teachreach.objects;
import java.util.ArrayList;
import java.util.Random;

public class stubCreation {
    ArrayList<Tutor> tutors;
    ArrayList<Course> courses;

    public stubCreation() {
        this.tutors.add(new Tutor("Jackson Pankratz", "He/Him", "Computer Science", "pankratz25@myumanitoba.ca", "password", 13.5));
        this.tutors.add(new Tutor("Camryn Mcmillan", "She/Her", "Computer Science", "mcmill5@myumanitoba.ca", "password", 20));
        this.tutors.add(new Tutor("Justin Huang", "He/Him", "Computer Science", "huang15@myumanitoba.ca", "password", 17.5));
        this.tutors.add(new Tutor("Ashna Sharma", "She/Her", "Computer Science", "sharma7@myumanitoba.ca", "password", 11));
        this.tutors.add(new Tutor("Theo Brown", "They/Them", "Computer Science", "brown102@myumanitoba.ca", "password", 40.5));

        this.courses.add(new Course("COMP 2080", "Analysis of Algorithms"));
        this.courses.add(new Course("COMP 1010", "Introduction to Computer Science"));
        this.courses.add(new Course("COMP 1012", "Introduction to Computer Science for Engineers"));
        this.courses.add(new Course("COMP 2150", "Object Orientation"));
        this.courses.add(new Course("COMP 3380", "Databases Concepts and Usage"));

        Random rand = new Random(7881702);

        for(int i=0; i<5; i++) {
            for(int j=0; j<7; j++) {
                for(int k=0; k<24; k++) {
                    this.tutors[i].setAvailability(j, k, rand.nextInt(2)==1);
                }
            }
            this.tutors[i].setCourses(courses);
        }

    }

    public ArrayList<Tutor> getStubTutors() {
        return this.tutors;
    }
}