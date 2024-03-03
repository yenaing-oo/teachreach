package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.ICourse;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class TutorHSQLDB implements ITutorPersistence {
    private final String dbPath;

    public TutorHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true",
                "SA", "");
    }

    private ITutor fromResultSet(final ResultSet rs) throws SQLException {
        final String email = rs.getString("account.email");
        final String password = rs.getString("account.password");

        final String studentName = rs.getString("student.name");
        final String studentMajor = rs.getString("student.major");
        final String studentPronouns = rs.getString("student.pronouns");

        Account resultAccount = new Account(email, password);
        resultAccount.setStudentProfile(new Student(
                studentName,
                studentMajor,
                studentPronouns,
                resultAccount));

        final String tutorName = rs.getString("tutor.name");
        final String tutorMajor = rs.getString("tutor.major");
        final String tutorPronouns = rs.getString("tutor.pronouns");

        Tutor resultTutor = new Tutor(
                tutorName,
                tutorMajor,
                tutorPronouns,
                resultAccount);
        resultAccount.setTutorProfile(resultTutor);
        getTutoredCourses(email).forEach(resultTutor::addCourse);
        // TO-DO:
        //      set up rest of the profile

        return resultTutor;
    }

    private ICourse fromResultSetCourse(final ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("tutor_course.tutored_course_code"),
                rs.getString("course.course_name"));
    }

    private List<ICourse> getTutoredCourses(String email) {
        final List<ICourse> resultCourses = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM tutor_course " +
                            "JOIN tutor ON tutor.email = tutor_course.email " +
                            "JOIN course ON tutor_course.tutored_course_code " +
                            "= course.course_code " +
                            "WHERE tutor.email = ?");
            while (rs.next()) {
                final ICourse course = fromResultSetCourse(rs);
                resultCourses.add(course);
            }
            rs.close();
            st.close();
            return resultCourses;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public ITutor storeTutor(ITutor newTutor) throws RuntimeException {
        return null;
    }

    @Override
    public ITutor updateTutor(ITutor newTutor) throws RuntimeException {
        return null;
    }

    @Override
    public Optional<ITutor> getTutorByEmail(String email) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM tutor " +
                            "JOIN account ON account.email = tutor.email " +
                            "WHERE tutor.email = ?");
            pst.setString(1, email);
            final ResultSet rs = pst.executeQuery();
            ITutor tutor = null;
            if (rs.next()) {
                tutor = fromResultSet(rs);
            }
            return Optional.ofNullable(tutor);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public ArrayList<ITutor> getTutors() {
        return null;
    }

    @Override
    public ArrayList<ITutor> getTutorsByName(String name) {
        return null;
    }
}
