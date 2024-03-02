package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.Optional;

import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.Student;

public class StudentStub implements IStudentPersistence {
    IAccountPersistence accountsDataAccess;
    ArrayList<IStudent> students;

    public StudentStub(IAccountPersistence accounts) {
        accountsDataAccess = accounts;
        students = new ArrayList<>();
    }

    @Override
    public IStudent storeStudent(IStudent newStudent) throws RuntimeException {
        if (accountsDataAccess.getAccountByEmail(
                newStudent.getOwner().getEmail()).isPresent()) {
            students.add(newStudent);
            return newStudent;
        } else {
            throw new RuntimeException(
                    "Failed to store new Student profile:-" +
                            "(Associated account not found)");
        }
    }

    @Override
    public IStudent updateStudent(IStudent newStudent) throws RuntimeException {
        return null;
    }

    @Override
    public Optional<IStudent> getStudentByEmail(String email) {
        return accountsDataAccess
                .getAccountByEmail(email)
                .map(Account::getStudentProfile);
    }

    @Override
    public ArrayList<IStudent> getStudents() {
        return null;
    }
}
