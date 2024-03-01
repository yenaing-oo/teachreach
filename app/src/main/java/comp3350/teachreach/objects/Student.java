package comp3350.teachreach.objects;

public class Student extends User implements IStudent {

    public Student(String name, String pronouns, String major) {
        super(name, pronouns, major);
    }
}