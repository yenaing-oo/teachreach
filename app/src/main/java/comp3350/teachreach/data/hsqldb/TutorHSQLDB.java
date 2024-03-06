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
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class TutorHSQLDB implements ITutorPersistence
{
    private final String dbPath;

    public
    TutorHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }

    private
    Connection connection() throws SQLException
    {
        return DriverManager.getConnection(String.format(
                "jdbc:hsqldb:file:%s;shutdown=true",
                dbPath), "SA", "");
    }

    private
    ITutor fromResultSet(final ResultSet rs) throws SQLException
    {
        final String tutorName     = rs.getString("tutor.name");
        final String tutorMajor    = rs.getString("tutor.major");
        final String tutorPronouns = rs.getString("tutor.pronouns");

        return new Tutor(tutorName, tutorMajor, tutorPronouns);
    }

    @Override
    public
    ITutor storeTutor(ITutor newTutor) throws PersistenceException
    {
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO tutor VALUES(?, ?, ?, ?)");

            pst.setString(2, newTutor.getUserName());
            pst.setString(3, newTutor.getUserMajor());
            pst.setString(4, newTutor.getUserPronouns());
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
    public
    ITutor updateTutor(ITutor newTutor) throws PersistenceException
    {
        return null;
    }

    @Override
    public
    Optional<ITutor> getTutorByEmail(String email)
    {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM tutor " +
                    "JOIN account ON account.email = tutor.email " +
                    "WHERE tutor.email = ?");
            pst.setString(1, email);
            final ResultSet rs    = pst.executeQuery();
            ITutor          tutor = null;
            if (rs.next()) {
                tutor = fromResultSet(rs);
            }
            return Optional.ofNullable(tutor);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    List<ITutor> getTutors()
    {
        final List<ITutor> tutors = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM tutor");
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
    public
    List<ITutor> getTutorsByName(String name)
    {
        return null;
    }
}
