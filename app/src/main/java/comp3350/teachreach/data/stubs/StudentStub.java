package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.objects.interfaces.IStudent;

public class StudentStub implements IStudentPersistence {
    List<IStudent> students;

    public StudentStub() {
        students = new ArrayList<>();
    }

    @Override
    public IStudent storeStudent(IStudent newStudent) {
        return getStudentByEmail(newStudent.getEmail())
                .orElseGet(() -> {
                    students.add(newStudent);
                    return newStudent;
                });
    }

    @Override
    public IStudent updateStudent(IStudent newStudent) {
        AtomicReference<IStudent> theStudent = new AtomicReference<>(newStudent);
        getStudentByEmail(newStudent.getEmail())
                .ifPresentOrElse(s -> {
                    s.setName(newStudent.getName());
                    s.setMajor(newStudent.getMajor());
                    s.setPronouns(newStudent.getPronouns());
                    theStudent.set(s);
                }, () -> {
                    storeStudent(newStudent);
                });
        return theStudent.get();
    }

    private Optional<IStudent> getStudentByEmail(String email) {
        return students.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<IStudent> getStudents() {
        return this.students;
    }
}
