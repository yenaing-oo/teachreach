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

import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;
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
        final String tutorName = rs.getString("tutor.name");
        final String tutorMajor = rs.getString("tutor.major");
        final String tutorPronouns = rs.getString("tutor.pronouns");

        return new Tutor(
                tutorName,
                tutorMajor,
                tutorPronouns);
    }

    private ICourse fromResultSetCourse(final ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("tutor_course.tutored_course_code"),
                rs.getString("course.course_name"));
    }

    private List<ICourse> getTutoredCourses(String tutorEmail) {
        final List<ICourse> resultCourses = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM tutor_course " +
                            "JOIN tutor " +
                            "ON tutor.email = tutor_course.tutor_email " +
                            "JOIN course " +
                            "ON tutored_course_code = course_code " +
                            "WHERE tutor.email = ?");
            pst.setString(1, tutorEmail);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                final ICourse course = fromResultSetCourse(rs);
                resultCourses.add(course);
            }
            pst.close();
            rs.close();
            return resultCourses;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public ITutor storeTutor(ITutor newTutor) throws PersistenceException {
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO tutor VALUES(?, ?, ?, ?)");

            pst.setString(2, newTutor.getName());
            pst.setString(3, newTutor.getMajor());
            pst.setString(4, newTutor.getPronouns());
            pst.executeUpdate();
            final ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                newTutor.setTutorID(rs.getInt(1));
            } else {
                throw new SQLException();
            }
            return newTutor;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public ITutor updateTutor(ITutor newTutor) throws PersistenceException {
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
    public List<ITutor> getTutors() {
        final List<ITutor> tutors = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM tutor");
            while (rs.next()) {
                tutors.add(fromResultSet(rs));
            }
            st.close();
            rs.close();
            return tutors;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ITutor> getTutorsByName(String name) {
        return null;
    }
}
