package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.interfaces.ITutoredCoursesPersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;

public class TutoredCoursesHSQLDB implements ITutoredCoursesPersistence {
    private final String dbPath;

    public TutoredCoursesHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException
    {
        return DriverManager.getConnection(String.format(
                "jdbc:hsqldb:file:%s;shutdown=true",
                dbPath), "SA", "");
    }
    private ICourse fromResultSet(final ResultSet rs) throws SQLException
    {
        //CREATE MEMORY TABLE PUBLIC.TUTOR_SESSIONS(TUTOR_ID INTEGER NOT NULL, SESSION_ID INTEGER NOT NULL, CONSTRAINT TS_PK PRIMARY KEY(TUTOR_ID, SESSION_ID), CONSTRAINT TS_FK_0 FOREIGN KEY(TUTOR_ID) REFERENCES PUBLIC.TUTORS(TUTOR_ID), CONSTRAINT TS_FK_1 FOREIGN KEY(SESSION_ID) REFERENCES PUBLIC.SESSIONS(SESSION_ID))
        final String    courseID     = rs.getString("course_id");
        final String  courseName     = rs.getString("course_Name");
        return new Course(courseID,courseName);
    }

    public List<ICourse> getTutorCourseByTID(int tutorID) {
        final List<ICourse> tutoredCourse = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement("SELECT * FROM tutored_courses WHERE tutor_id = ?");
            pst.setInt(1, tutorID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                final ICourse theCourse = fromResultSet(rs);
                tutoredCourse.add(theCourse);
            }
            pst.close();
            rs.close();
            return tutoredCourse;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }
    @Override
    public boolean storeTutorCourse(int tutorID, int sessionID){
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO tutored_courses VALUES(?, ?)"
                     );

            pst.setInt(1, tutorID);
            pst.setDouble(2,sessionID);

            final boolean success = pst.executeUpdate() == 1;
            if (!success) {
                throw new PersistenceException(
                        "New tutored course mightn't be " + "stored!");
            }
            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }
}
