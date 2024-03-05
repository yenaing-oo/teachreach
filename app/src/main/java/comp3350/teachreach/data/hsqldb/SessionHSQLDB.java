package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.objects.Session;
import comp3350.teachreach.objects.TimeSlice;

public class SessionHSQLDB implements ISessionPersistence {
    private final String dbPath;

    private List<ISession> sessions;

    public SessionHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        this.sessions = null;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true",
                "SA", "");
    }

    private ISession fromResultSet(final ResultSet rs) throws SQLException {
        return null;
    }

    private Optional<ISession> fromResultSetWithinRange(
            final ResultSet rs, final TimeSlice range) throws SQLException {
        ISession resultSession;
        final Instant startTime = ((OffsetDateTime) rs
                .getObject("start_date_time"))
                .toInstant();
        final Instant endTime = ((OffsetDateTime) rs
                .getObject("end_date_time"))
                .toInstant();
        final TimeSlice sessionTime = new TimeSlice(
                startTime, endTime,
                Duration.between(startTime, endTime));
        resultSession = range.canContain(sessionTime) ? fromResultSet(rs) : null;
        return Optional.ofNullable(resultSession);
    }

    @Override
    public ISession storeSession(
            IStudent theStudent, ITutor theTutor,
            TimeSlice sessionTime, String location) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO SESSION" +
                            "(STUDENTID, TUTORID, " +
                            "START_DATE_TIME, END_DATE_TIME, " +
                            "LOCATION, ACCEPTED) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, theStudent.getAccountID());
            pst.setInt(2, theTutor.getTutorID());
            pst.setObject(3,
                    OffsetDateTime.ofInstant(
                            sessionTime.getStartTime(), ZoneId.systemDefault()));
            pst.setObject(4,
                    OffsetDateTime.ofInstant(
                            sessionTime.getEndTime(), ZoneId.systemDefault()));
            pst.setString(5, location);
            pst.setBoolean(6, false);
            assert (pst.executeUpdate() == 1);

            ResultSet generatedSessionID = pst.getGeneratedKeys();
            pst.close();
            if (generatedSessionID.next()) {
                int sessionID = generatedSessionID.getInt(1);
                generatedSessionID.close();
                return new Session(
                        theStudent, theTutor, sessionTime, location)
                        .setSessionID(sessionID);
            } else {
                throw new SQLException("Failed while storing Session");
            }
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deleteSession(ISession session) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "DELETE FROM session WHERE SESSION_ID = ?");
            pst.setInt(1, session.getSessionID());
            boolean success = pst.executeUpdate() == 1;
            pst.close();
            return success;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean updateSession(ISession session) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "UPDATE session " +
                            "SET STUDENTID = ?, TUTORID = ?, " +
                            "start_date_time = ?, end_date_time = ?, " +
                            "location = ?, accepted = ?" +
                            "WHERE session_id = ?");
            TimeSlice sessionTime = session.getTime();
            pst.setInt(1, session.getStudent().getAccountID());
            pst.setInt(2, session.getTutor().getTutorID());
            pst.setObject(3, OffsetDateTime.ofInstant(
                    sessionTime.getStartTime(), ZoneId.systemDefault()));
            pst.setObject(4, OffsetDateTime.ofInstant(
                    sessionTime.getEndTime(), ZoneId.systemDefault()));
            pst.setString(5, session.getLocation());
            pst.setBoolean(6, session.getStage());
            pst.setInt(7, session.getSessionID());
            boolean success = pst.executeUpdate() == 1;
            pst.close();
            return success;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ISession> getSessionsByRangeForStudent(
            int studentID, TimeSlice range) {
        final List<ISession> sessions = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM session " +
                            "WHERE STUDENTID = ?");
            pst.setInt(1, studentID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                fromResultSetWithinRange(rs, range).ifPresent(sessions::add);
            }
            pst.close();
            rs.close();
            return sessions;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ISession> getSessionsByRangeForTutor(
            int tutorID, TimeSlice range) {
        final List<ISession> sessions = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM session WHERE TUTORID = ?");
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                fromResultSetWithinRange(rs, range).ifPresent(sessions::add);
            }
            pst.close();
            rs.close();
            return sessions;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ISession> getPendingSessionRequests(int tutorID) {
        final List<ISession> sessions = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM session " +
                            "WHERE TUTORID = ? AND ACCEPTED = ?");
            pst.setInt(1, tutorID);
            pst.setBoolean(2, false);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sessions.add(fromResultSet(rs));
            }
            pst.close();
            rs.close();
            return sessions;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
