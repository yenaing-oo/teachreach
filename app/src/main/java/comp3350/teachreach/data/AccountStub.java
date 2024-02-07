package comp3350.teachreach.data;
import comp3350.teachreach.objects.*;
import java.util.ArrayList;
import java.util.Random;

public class AccountStub {
    ArrayList<Tutor> tutors;
    ArrayList<Student> students;

    public AccountStub() {
        this.tutors.add(new Tutor("Jackson Pankratz", "He/Him", "Computer Science", "pankratz25@myumanitoba.ca", "password", 13.5));
        this.tutors.add(new Tutor("Camryn Mcmillan", "She/Her", "Computer Science", "mcmill5@myumanitoba.ca", "password", 20));
        this.tutors.add(new Tutor("Justin Huang", "He/Him", "Computer Science", "huang15@myumanitoba.ca", "password", 17.5));
        this.tutors.add(new Tutor("Ashna Sharma", "She/Her", "Computer Science", "sharma7@myumanitoba.ca", "password", 11));
        this.tutors.add(new Tutor("Theo Brown", "They/Them", "Computer Science", "brown102@myumanitoba.ca", "password", 40.5));

        Random rand = new Random(7881702);

        for(int i=0; i<5; i++) {
            for(int j=0; j<7; j++) {
                for(int k=0; k<24; k++) {
                    this.tutors.get(i).setAvailability(j, k, rand.nextInt(2)==1);
                }
            }
            //retrieve course object, then input it
            this.tutors.get(i).setCourses(courses);

            Student student1 = new Student("Rob Guderian", "He/Him", "Computer Science", "guder4@myumanitoba.ca", "password");
            this.students.add(student1);
        }

    }
    //add your funciton here. eg add, edit, delete, search


    public void setCourses(Course course){
        //write the code;
    }
    public ArrayList<Tutor> getStubTutors() {
        return this.tutors;
    }

    public ArrayList<Student> getStubStudents() {
        return this.students;
    }

    //search tutor
    public Tutor searchTutorbyName(Tutor tutor) {
        Tutor Searched = null;
        for (int i = 0; i < tutors.size(); i++)
        {
            if(tutors.get(i).getName().equals(tutor.getName()))
            {
                Searched = tutors.get(i);
            }
        }
        return Searched;
    }
    //search students
}