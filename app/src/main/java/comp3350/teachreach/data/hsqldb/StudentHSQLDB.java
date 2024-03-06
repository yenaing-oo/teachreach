package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.interfaces.IStudent;

public
class StudentHSQLDB implements IStudentPersistence
{

    private final String dbPath;

    public
    StudentHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }

    private
    Connection connection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private
    IStudent fromResultSet(final ResultSet rs) throws SQLException
    {
        final String name     = rs.getString("name");
        final String major    = rs.getString("major");
        final String pronouns = rs.getString("pronouns");
        final String email    = rs.getString("email");

        Student student = new Student(email, name, major, pronouns);
        return student;
    }

    @Override
    public
    IStudent storeStudent(IStudent newStudent) throws RuntimeException
    {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO student VALUES(?, ?, ?, ?)");
            pst.setString(1, newStudent.getEmail());
            pst.setString(2, newStudent.getName());
            pst.setString(3, newStudent.getMajor());
            pst.setString(4, newStudent.getPronouns());
            pst.executeUpdate();
            pst.close();
            return newStudent;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    IStudent updateStudent(IStudent newStudent) throws RuntimeException
    {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement("UPDATE student " +
                                                             "SET name = ?, major = ?, pronouns = ?" +
                                                             "WHERE email = ?");
            pst.setString(1, newStudent.getName());
            pst.setString(2, newStudent.getMajor());
            pst.setString(3, newStudent.getPronouns());
            pst.setString(4, newStudent.getEmail());
            pst.executeUpdate();
            pst.close();
            return newStudent;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    List<IStudent> getStudents()
    {
        final List<IStudent> students = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                final IStudent student = fromResultSet(rs);
                students.add(student);
            }
            rs.close();
            st.close();
            return students;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
