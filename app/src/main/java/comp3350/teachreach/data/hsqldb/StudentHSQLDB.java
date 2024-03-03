package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import comp3350.teachreach.data.IStudentPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IStudent;
import comp3350.teachreach.objects.Student;

public class StudentHSQLDB implements IStudentPersistence {

    private final String dbPath;

    public StudentHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true",
                "SA", "");
    }

    private IStudent fromResultSet(final ResultSet rs) throws SQLException {
        final String name = rs.getString("name");
        final String major = rs.getString("major");
        final String pronouns = rs.getString("pronouns");
        final String email = rs.getString("email");
        final String password = rs.getString("password");

        Account account = new Account(email, password);
        Student student = new Student(name, major, pronouns, account);
        account.setStudentProfile(student);
        return student;
    }

    @Override
    public IStudent storeStudent(IStudent newStudent) throws RuntimeException {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO student VALUES(?, ?, ?, ?)");
            pst.setString(1, newStudent.getOwner().getEmail());
            pst.setString(2, newStudent.getName());
            pst.setString(3, newStudent.getMajor());
            pst.setString(4, newStudent.getPronouns());
            pst.executeUpdate();
            return newStudent;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public IStudent updateStudent(IStudent newStudent) throws RuntimeException {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "UPDATE student " +
                            "SET name = ?, major = ?, pronouns = ?" +
                            "WHERE email = ?");
            pst.setString(1, newStudent.getName());
            pst.setString(2, newStudent.getMajor());
            pst.setString(3, newStudent.getPronouns());
            pst.setString(4, newStudent.getOwner().getEmail());
            pst.executeUpdate();
            return newStudent;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<IStudent> getStudentByEmail(String email) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM account " +
                            "JOIN student ON account.email = student.email " +
                            "WHERE account.email = ?");
            pst.setString(1, email);
            final ResultSet rs = pst.executeQuery();
            IStudent student = null;
            if (rs.next()) {
                student = fromResultSet(rs);
            }
            return Optional.ofNullable(student);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public ArrayList<IStudent> getStudents() {
        final ArrayList<IStudent> students = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM student" +
                    "JOIN account ON account.email = student.email");
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
