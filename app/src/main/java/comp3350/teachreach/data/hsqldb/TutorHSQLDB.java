package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
        final int    tutorID     = rs.getInt("tutor_id");
        final int    accountId   = rs.getInt("account_id");
        final double hourlyRate  = rs.getDouble("hourly_rate");
        final int    reviewSum   = rs.getInt("review_sum");
        final int    reviewCount = rs.getInt("review_count");

        return new Tutor(tutorID,
                         accountId,
                         hourlyRate,
                         reviewSum,
                         reviewCount);
    }

    @Override
    public
    ITutor storeTutor(ITutor newTutor) throws PersistenceException
    {
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO tutors VALUES(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, newTutor.getAccountID());
            pst.setDouble(2, newTutor.getHourlyRate());
            pst.setInt(3, newTutor.getReviewSum());
            pst.setInt(4, newTutor.getReviewCount());
            pst.executeUpdate();
            final ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                rs.close();
                return newTutor.setTutorID(rs.getInt(1));
            } else {
                rs.close();
                throw new PersistenceException("Tutor mightn't be updated!");
            }
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
    Map<Integer, ITutor> getTutors()
    {
        final Map<Integer, ITutor> tutors = new HashMap<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM tutors");
            while (rs.next()) {
                final ITutor theTutor = fromResultSet(rs);
                tutors.put(theTutor.getTutorID(), theTutor);
            }
            st.close();
            rs.close();
            return tutors;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
