package comp3350.teachreach.data;

import java.util.ArrayList;

import comp3350.teachreach.objects.Student;

public interface IStudentPersistence {
    Student storeStudent(Student newStudent);

    Student updateStudent(Student newStudent);

    ArrayList<Student> getStudents();
}
