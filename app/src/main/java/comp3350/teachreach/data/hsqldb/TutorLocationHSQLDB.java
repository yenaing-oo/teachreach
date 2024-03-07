package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorLocationHSQLDB {
    private final String dbPath;

    public
    TutorLocationHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException
    {
        return DriverManager.getConnection(String.format(
                "jdbc:hsqldb:file:%s;shutdown=true",
                dbPath), "SA", "");
    }

    //TUTOR_ID INTEGER NOT NULL, LOCATION_NAME VARCHAR(32) NOT NULL, CONSTRAINT TL_PK PRIMARY KEY(TUTOR_ID, LOCATION_NAME), CONSTRAINT TL_FK FOREIGN KEY(TUTOR_ID) REFERENCES TUTORS(TUTOR_ID) ON DELETE CASCADE)
    private String fromResultSet(final ResultSet rs) throws SQLException
    {
        final String    location     = rs.getString("location_name");

        return location;
    }

    public List<String> getTutorLocationByTID(int tutor_id){
        final List<String> tutorLocation = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement("SELECT * FROM tutored_locations WHERE tutor_id = ?");
            pst.setInt(1, tutor_id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                final String theLocation = fromResultSet(rs);
                tutorLocation.add(theLocation);
            }
            pst.close();
            rs.close();
            return tutorLocation;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public boolean storeTutorLocation(int tutor_id, String location){
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO tutor_locations VALUES(?, ?)"
            );

            pst.setInt(1, tutor_id);
            pst.setString(2, location);

            final boolean success = pst.executeUpdate() == 1;
            if (!success) {
                throw new PersistenceException(
                        "New tutor location mightn't be " + "stored!");
            }
            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


}
