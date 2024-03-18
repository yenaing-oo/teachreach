package comp3350.teachreach.data.hsqldb;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDateTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class SessionHSQLDB implements ISessionPersistence
{
    private final String dbPath;

    public
    SessionHSQLDB(final String dbPath)
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
    ISession fromResultSet(final ResultSet rs) throws SQLException {
        final int studentID = rs.getInt("student_id");
        final int tutorID = rs.getInt("tutor_id");
        final int sessionID = rs.getInt("session_id");
        final boolean acceptedStatus = rs.getBoolean("accepted");
        final LocalDateTime startTime = DateTimeUtils.toLocalDateTime(rs.getTimestamp("start_date_time"));
        final LocalDateTime endTime = DateTimeUtils.toLocalDateTime(rs.getTimestamp("end_time_stamp"));
        final String location = rs.getString("location");
        ISession resultSession = new Session(sessionID,
                studentID,
                tutorID,
                new TimeSlice(startTime,
                        endTime),
                location);
        return acceptedStatus ? resultSession.approve() : resultSession;
    }

    @Override
    public ISession storeSession(IStudent student,
                                 ITutor tutor,
                                 ITimeSlice timeRange,
                                 String location) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO SESSIONS(STUDENT_ID, TUTOR_ID, " +
                            "START_DATE_TIME, END_DATE_TIME, " +
                            "LOCATION, ACCEPTED) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, student);
            pst.setInt(2, tutor);
            pst.setTimestamp(3, DateTimeUtils.toSqlTimestamp(timeRange.getStartTime()));
            pst.setTimestamp(4, DateTimeUtils.toSqlTimestamp(timeRange.getEndTime()));
            pst.setString(5, location);
            pst.setBoolean(6, false);
            final boolean success = pst.executeUpdate() == 1;
            if (!success) {
                throw new PersistenceException(
                        "New session mightn't be " + "stored!");
            }
            final ResultSet rs = pst.getGeneratedKeys();
            pst.close();
            if (rs.next()) {
                int sessionID = rs.getInt(1);
                rs.close();
                return new Session(sessionID,
                        student,
                        tutor,
                        timeRange,
                        location);
            } else {
                rs.close();
                c.close();
                throw new PersistenceException("SessionID not generated!");
            }
        } catch (final Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    ISession storeSession(ISession newSession)
    {
        return this.storeSession(newSession.getSessionStudentID(),
                                 newSession.getSessionTutorID(),
                                 newSession.getTime(),
                                 newSession.getSessionLocation());
    }

    @Override
    public boolean deleteSession(ISession session) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "DELETE FROM sessions WHERE SESSION_ID = ?");
            pst.setInt(1, session);
            boolean success = pst.executeUpdate() == 1;
            pst.close();
            return success;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    ISession updateSession(ISession session)
    {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "UPDATE sessions SET STUDENT_ID = ?, TUTOR_ID = ?, " +
                            "start_date_time = ?, end_date_time = ?, location = ?, " +
                            "accepted = ? WHERE session_id = ?");
            TimeSlice sessionTime = session.getTime();
            pst.setInt(1, session.getSessionStudentID());
            pst.setInt(2, session.getSessionTutorID());
            pst.setTimestamp(3, DateTimeUtils.toSqlTimestamp(sessionTime.getStartTime()));
            pst.setTimestamp(4, DateTimeUtils.toSqlTimestamp(sessionTime.getEndTime()));
            pst.setString(5, session.getSessionLocation());
            pst.setBoolean(6, session.getAcceptedStatus());
            pst.setInt(7, session.getSessionID());
            boolean success = pst.executeUpdate() == 1;
            pst.close();
            if (!success) {
                c.close();
                throw new PersistenceException("Session mightn't be updated!");
            }
            return session;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    Map<Integer, ISession> getSessions()
    {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM sessions");

            Map<Integer, ISession> resultMap = new HashMap<>();
            while (rs.next()) {
                final ISession resultSession = fromResultSet(rs);
                resultMap.put(resultSession.getSessionID(), resultSession);
            }
            rs.close();
            st.close();
            return resultMap;
        } catch (final Exception e) {
            throw new PersistenceException(e);
        }
    }
}
