package comp3350.teachreach.logic.profile;

import java.util.NoSuchElementException;

import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.ITutor;

public class StudentProfile implements IUserProfile{
    private IStudent theStudent;
    private IStudentPersistence studentsDataAccess;

    public StudentProfile(
            IStudent theStudent,
            IStudentPersistence students) {

        this.theStudent = theStudent;
        this.studentsDataAccess = students;
    }

    public StudentProfile(
            String studentEmail,
            IStudentPersistence students) throws NoSuchElementException {

        this.studentsDataAccess = students;
        this.theStudent =
                studentsDataAccess
                        .getStudentByEmail(studentEmail)
                        .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String getUserEmail() {
        return this.theStudent.getOwner().getEmail();
    }

    @Override
    public String getUserName() {
        return this.theStudent.getName();
    }

    @Override
    public String getUserPronouns() {
        return this.theStudent.getPronouns();
    }

    @Override
    public String getUserMajor() {
        return this.theStudent.getMajor();
    }

    @Override
    public IAccount getUserAccount() {
        return this.theStudent.getOwner();
    }

    @Override
    public IUserProfile setUserName(String name) {
        this.theStudent.setName(name);
        return this;
    }

    @Override
    public IUserProfile setUserPronouns(String pronouns) {
        this.theStudent.setPronouns(pronouns);
        return this;
    }

    @Override
    public IUserProfile setUserMajor(String major) {
        this.theStudent.setMajor(major);
        return this;
    }

    @Override
    public void updateUserProfile() {
        this.studentsDataAccess.updateStudent(theStudent);
    }
}
