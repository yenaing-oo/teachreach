package comp3350.teachreach.data;

import java.util.ArrayList;
import java.util.Iterator;

import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountPersistence implements IAccountPersistance {

    private final String dbPath;

    public AccountPersistence(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Account fromResultSet(final ResultSet rs) throws SQLException {
        final int accountID = rs.getInt("ACCOUNTID");
        final String email = rs.getString("EMAIL");
        final String password = rs.getString("PASSWORD");
        final int tutorID = rs.getInt("TUTORID");
        final String name = rs.getString("NAME");
        final String pronouns = rs.getString("PRONOUNS");
        final String major = rs.getString("MAJOR");

        Tutor tutorProfile = new NullTutor();

        if (tutorID != -1) {
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("SELECT * FROM TUTORS WHERE TUTORID=?");
                st.setString(1, tutorID);

                final ResultSet tutorResultSet = st.executeQuery();
                tutorProfile = fromResultSetTutor(tutorResultSet, name, pronouns, major);
                rs.close();
                st.close();
            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }

        }
        Student studentProfile = new Student(name, pronouns, major);
        Account createdAccount = new Account(email, password, accountID);
        createdAccount.setTutorProfile(tutorProfile);

        return createdAccount;
    }

    private Tutor fromResultSetTutor(final ResultSet rs, String name, String pronouns, String major) {
        final int tutorID = rs.getInt("TUTORID");
        final double hourlyRate = rs.getDouble("HOURLYRATE");
        final int reviewSum = rs.getInt("REVIEWSUM");
        final int reviewCount = rs.getInt("REVIEWCOUNT");
        final String availString = rs.getString("AVAILABILITY");
        final String prefAvailString = rs.getString("PREFAVAILABILITY");

        Tutor tutorProfile = new Tutor(name, pronouns, major, hourlyRate, tutorID);
        tutorProfile.setFullAvailability(stringToAvail(availString));
        tutorProfile.setFullPrefAvailability(stringToAvail(prefAvailString));
        tutorProfile.setReviewSum(reviewSum);
        tutorProfile.setReviewCount(reviewCount);

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM TUTOREDCOURSES WHERE TUTORID=?");
            st.setString(1, tutorID);

            final ResultSet tutorResultSet = st.executeQuery();
            tutorProfile = fromResultSetTutor(tutorResultSet, name, pronouns, major);
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return tutorProfile;
    }

    private boolean[][] stringToAvail(String availabilityString) {
        boolean[][] output = new boolean[7][24];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                output[i][j] = Integer.parseInt(availabilityString.charAt((i * 24) + j)) == 1;
            }
        }

        return output;
    }

    private String availToString(boolean[][] availability) {
        String output = "";

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (availability[i][j]) {
                    output += "1";
                } else {
                    output += "0";
                }
            }
        }

        return output;
    }

    public Account storeAccount(Account newAccount) {

    }

    public Optional<Account> getAccountByEmail(String email) {

    }

}
