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

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.objects.NullTutor;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountHSQLDB implements IAccountPersistence {
    private final String dbPath;
    private List<IAccount> accounts;
    private List<IAccount> tutors;

    public AccountHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        this.accounts = null;
        this.tutors = null;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true",
                "SA", "");
    }

    private Account fromResultSet(final ResultSet rs) throws SQLException {
        final int accountID = rs.getInt("ACCOUNTID");
        final String email = rs.getString("EMAIL");
        final String password = rs.getString("PASSWORD");
        final int tutorID = rs.getInt("TUTORID");
        final String name = rs.getString("NAME");
        final String pronouns = rs.getString("PRONOUNS");
        final String major = rs.getString("MAJOR");

        ITutor tutorProfile = new NullTutor();

        if (tutorID != -1) {
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("SELECT * FROM TUTORS WHERE TUTORID=?");
                st.setInt(1, tutorID);

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
    private ITutor fromResultSetTutor(final ResultSet rs, String name, String pronouns, String major) throws SQLException {
        final int tutorID = rs.getInt("TUTORID");
        final double hourlyRate = rs.getDouble("HOURLYRATE");
        final int reviewSum = rs.getInt("REVIEWSUM");
        final int reviewCount = rs.getInt("REVIEWCOUNT");
        final String availString = rs.getString("AVAILABILITY");
        final String prefAvailString = rs.getString("PREFAVAILABILITY");

        ITutor tutorProfile = new Tutor(name, pronouns, major, hourlyRate, tutorID);
        tutorProfile.setReviewTotal(reviewSum);
        tutorProfile.setReviewCount(reviewCount);

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM TUTOREDCOURSES WHERE TUTORID=?");
            st.setInt(1, tutorID);

            final ResultSet tutorResultSet = st.executeQuery();
            tutorProfile = fromResultSetTutor(tutorResultSet, name, pronouns, major);
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return tutorProfile;
    }

    @Override
    public synchronized IAccount storeAccount(IAccount newAccount) {
        try (final Connection c = connection()) {
            if(newAccount.getTutorProfile().isPresent()) {
                ITutor tutorProfile = newAccount.getTutorProfile().get();
                final PreparedStatement pstTutor = c.prepareStatement(
                        "INSERT INTO TUTORS (HOURLYRATE, REVIEWSUM, REVIEWCOUNT) VALUES(?, ?, ?)");
                pstTutor.setDouble(1, tutorProfile.getHourlyRate());
                pstTutor.setInt(2, tutorProfile.getReviewTotalSum());
                pstTutor.setInt(3, tutorProfile.getReviewCount());
                pstTutor.executeUpdate();
                ResultSet tutorIDRS = pstTutor.getGeneratedKeys();
                if(tutorIDRS.next()) {

                }
            }
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO ACCOUNTS (EMAIL, PASSWORD,  VALUES(?, ?)");
            pst.setString(1, newAccount.getEmail());
            pst.setString(2, newAccount.getPassword());
            pst.executeUpdate();
            pst.close();
            return newAccount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public synchronized boolean updateAccount(IAccount existingAccount) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "UPDATE ACCOUNTS " +
                            "SET EMAIL = ?, PASSWORD = ?, TUTORID = ?, " +
                            "NAME = ?, PRONOUNS = ?, MAJOR = ? " +
                            "WHERE ACCOUNTID = ?");
            pst.setString(1, existingAccount.getEmail());
            pst.setString(2, existingAccount.getPassword());
            if(existingAccount.getTutorProfile().isPresent()) {
                pst.setInt(3, existingAccount.getTutorProfile().get().getTutorID());
                pst.setString(4, existingAccount.getTutorProfile().get().getName());
                pst.setString(5, existingAccount.getTutorProfile().get().getPronouns());
                pst.setString(6, existingAccount.getTutorProfile().get().getMajor());
            }
            else if(existingAccount.getStudentProfile().isPresent()) {
                pst.setString(4, existingAccount.getStudentProfile().get().getName());
                pst.setString(5, existingAccount.getStudentProfile().get().getPronouns());
                pst.setString(6, existingAccount.getStudentProfile().get().getMajor());
                pst.setString(3, "-1");
            }

            boolean success = pst.executeUpdate() == 1;
            pst.close();
            for(int i=0; i<this.accounts.size(); i++) {
                if(this.accounts.get(i).getAccountID() == existingAccount.getAccountID()) {
                    this.accounts.set(i,existingAccount);
                    break;
                }
            }
            return success;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public synchronized List<IAccount> getAccounts() {
        if(this.accounts == null) {
            this.accounts = new ArrayList<IAccount>();
            this.tutors = new ArrayList<IAccount>();
            try (final Connection c = connection()) {
                final Statement st = c.createStatement();
                final ResultSet rs = st.executeQuery(
                        "SELECT * FROM account");
                while (rs.next()) {
                    IAccount resultAccount = fromResultSet(rs);
                    this.accounts.add(resultAccount);
                    if(resultAccount.getTutorProfile().isPresent()) {
                        this.tutors.add(resultAccount);
                    }
                }
                rs.close();
                st.close();
            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }
        }
        return this.accounts;
    }

    public synchronized List<IAccount> getTutorAccounts() {
        if(tutors == null) {
            getAccounts();
        }
        return this.tutors;
    }
}
