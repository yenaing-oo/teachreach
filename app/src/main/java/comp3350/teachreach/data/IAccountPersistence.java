package comp3350.teachreach.data;

import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;
import java.util.ArrayList;

public interface IAccountPersistence {

  // Student
  Student storeStudent(Student newStudent);

  Student updateStudent(Student newStudent);

  Student getStudentByEmail(String email);

  ArrayList<Student> getStudents();

  // Tutor
  Tutor storeTutor(Tutor newTutor);

  Tutor updateTutor(Tutor newTutor);

  Tutor getTutorByEmail(String email);

  ArrayList<Tutor> getTutors();

  ArrayList<Tutor> getTutorsByName(String name);
}
