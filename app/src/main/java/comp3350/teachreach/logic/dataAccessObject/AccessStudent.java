package comp3350.teachreach.logic.dataAccessObject;

import java.util.Collections;
import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.objects.IStudent;

public class AccessStudent {
    private IStudentPersistence studentPersistence;
    private List<IStudent> students;
    private IStudent student;

    public AccessStudent() {
        studentPersistence = Server.getStudentDataAccess();
        students = null;
        student = null;
    }

    public AccessStudent(final IStudentPersistence studentPersistence) {
        this();
        this.studentPersistence = studentPersistence;
    }

    public List<IStudent> getStudents() {
        students = studentPersistence.getStudents();
        return Collections.unmodifiableList(students);
    }

    public IStudent getStudentByEmail(String email) {
        if (students == null) {
            students = studentPersistence.getStudents();
        }
        students.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .ifPresentOrElse(s -> student = s, () -> {
                    student = null;
                    students = null;
                });
        return student;
    }

    public IStudent insertStudent(IStudent newStudent) {
        return studentPersistence.storeStudent(newStudent);
    }

    public IStudent updateStudent(IStudent existingStudent) {
        return studentPersistence.updateStudent(existingStudent);
    }
}
