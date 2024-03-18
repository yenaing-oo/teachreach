package comp3350.teachreach.data.hsqldb;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDateTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.interfaces.ITutorAvailabilityPersistence;
import comp3350.teachreach.objects.TimeSlice;

public class TutorAvailabilityHSQLDB implements ITutorAvailabilityPersistence {

    private final String dbPath;

    public
    TutorAvailabilityHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException
    {
        return DriverManager.getConnection(String.format(
                "jdbc:hsqldb:file:%s;shutdown=true",
                dbPath), "SA", "");
    }

    //TUTOR_ID INTEGER NOT NULL, START_DATE_TIME TIMESTAMP WITH TIME ZONE NOT NULL, END_DATE_TIME TIMESTAMP WITH TIME ZONE NOT NULL, CONSTRAINT TA_PK PRIMARY KEY(TUTOR_ID, START_DATE_TIME, END_DATE_TIME), CONSTRAINT TA_FK FOREIGN KEY(TUTOR_ID) REFERENCES TUTORS(TUTOR_ID))
    private TimeSlice fromResultSet(final ResultSet rs) throws SQLException {
        final LocalDateTime startDateTime = DateTimeUtils.toLocalDateTime(rs.getTimestamp("START_DATE_TIME"));
        final LocalDateTime endDateTime = DateTimeUtils.toLocalDateTime(rs.getTimestamp("END_DATE_TIME"));

        return new TimeSlice(startDateTime, endDateTime);
    }

    public List<TimeSlice> getAvailability(int tutorID) {
        final List<TimeSlice> tutorTimeSlice = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement("SELECT * FROM TUTOR_AVAILABILITY WHERE tutor_id = ?");
            pst.setInt(1, tutorID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                final TimeSlice theTimeSlice = fromResultSet(rs);
                tutorTimeSlice.add(theTimeSlice);
            }
            pst.close();
            rs.close();
            return tutorTimeSlice;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    public boolean storeAvailability(int tutorID, TimeSlice timeSlice) {
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO tutor_locations VALUES(?, ?, ?)"
            );

            pst.setInt(1, tutorID);
            pst.setTimestamp(2, DateTimeUtils.toSqlTimestamp(timeSlice.getStartTime()));
            pst.setTimestamp(3, Timestamp.valueOf(String.valueOf(timeSlice.getEndTime())));

            final boolean success = pst.executeUpdate() == 1;
            if (!success) {
                throw new PersistenceException(
                        "New tutor availability mightn't be " + "stored!");
            }
            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }
}
