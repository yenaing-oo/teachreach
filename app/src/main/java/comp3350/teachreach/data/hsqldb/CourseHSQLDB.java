package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import comp3350.teachreach.data.interfaces.ICoursePersistence;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.interfaces.ICourse;

public
class CourseHSQLDB implements ICoursePersistence
{
    private final String dbPath;

    private Map<String, ICourse> courses;

    public
    CourseHSQLDB(final String dbPath)
    {
        this.dbPath  = dbPath;
        this.courses = null;
    }

    private
    Connection connection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private
    Course fromResultSet(final ResultSet rs) throws SQLException
    {
        final String courseCode = rs.getString("course_code");
        final String courseName = rs.getString("course_name");
        return new Course(courseCode, courseName);
    }

    @Override
    public
    Map<String, ICourse> getCourses() throws PersistenceException
    {
        if (this.courses == null) {
            this.courses = new HashMap<>();
        }
        try (final Connection c = this.connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM course");
            ICourse         theCourse;
            while (rs.next()) {
                theCourse = fromResultSet(rs);
                this.courses.putIfAbsent(theCourse.getCourseCode(), theCourse);
            }
            st.close();
            rs.close();
            return this.courses;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    Optional<ICourse> addCourse(String courseCode, String courseName)
            throws PersistenceException
    {
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO course VALUES(?, ?)");
            pst.setString(1, courseCode);
            pst.setString(2, courseName);
            pst.executeUpdate();
            pst.close();
            return Optional.ofNullable(this.courses.putIfAbsent(courseCode,
                                                                new Course(
                                                                        courseCode,
                                                                        courseName)));
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    Optional<ICourse> getCourseByCourseCode(String courseCode)
    {
        if (courses == null) {
            courses = getCourses();
        }
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM course " + "WHERE course_code = ?");
            pst.setString(1, courseCode);
            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                resultCourse = fromResultSet(rs);
            }
            pst.close();
            rs.close();
            return Optional.ofNullable(resultCourse);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    List<ICourse> getCoursesByName(String courseName)
    {
        List<ICourse> resultCourses = new ArrayList<>();
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM course WHERE course_name LIKE ?");
            pst.setString(1, "%" + courseName + "%");
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                resultCourses.add(fromResultSet(rs));
            }
            pst.close();
            rs.close();
            return resultCourses;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public
    List<ICourse> getTutoredCourses(int tutorID)
    {
        List<ICourse> resultCourses = new ArrayList<>();
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT COURSEID, COURSENAME FROM TUTOREDCOURSES " +
                    "INNER JOIN ON TUTOREDCOURSES.COURSEID=COURSE.COURSEID" +
                    "WHERE TUTORID = ?");
            pst.setInt(1, tutorID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                resultCourses.add(fromResultSet(rs));
            }
            pst.close();
            rs.close();
            return resultCourses;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
