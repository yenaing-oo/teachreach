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
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.NullTutor;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class AccountHSQLDB implements IAccountPersistence
{
    private final String         dbPath;
    private       List<IAccount> accounts;
    private       List<IAccount> tutors;

    public
    AccountHSQLDB(final String dbPath)
    {
        this.dbPath   = dbPath;
        this.accounts = null;
        this.tutors   = null;
    }

    private
    Connection connection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private
    Account fromResultSet(final ResultSet rs) throws SQLException
    {
        final int    accountID = rs.getInt("ACCOUNTID");
        final String email     = rs.getString("EMAIL");
        final String password  = rs.getString("PASSWORD");
        final int    tutorID   = rs.getInt("TUTORID");
        final String name      = rs.getString("NAME");
        final String pronouns  = rs.getString("PRONOUNS");
        final String major     = rs.getString("MAJOR");

        ITutor tutorProfile = new NullTutor();

        if (tutorID != -1) {
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement(
                        "SELECT * FROM TUTORS WHERE TUTORID=?");
                st.setInt(1, tutorID);

                final ResultSet tutorResultSet = st.executeQuery();
                tutorProfile = fromResultSetTutor(tutorResultSet,
                                                  name,
                                                  pronouns,
                                                  major);
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

    private
    ITutor fromResultSetTutor(final ResultSet rs,
                              String name,
                              String pronouns,
                              String major) throws SQLException
    {
        final int    tutorID         = rs.getInt("TUTORID");
        final double hourlyRate      = rs.getDouble("HOURLYRATE");
        final int    reviewSum       = rs.getInt("REVIEWSUM");
        final int    reviewCount     = rs.getInt("REVIEWCOUNT");
        final String availString     = rs.getString("AVAILABILITY");
        final String prefAvailString = rs.getString("PREFAVAILABILITY");

        ITutor tutorProfile = new Tutor(name,
                                        pronouns,
                                        major,
                                        hourlyRate,
                                        tutorID);
        tutorProfile.setReviewTotal(reviewSum);
        tutorProfile.setReviewCount(reviewCount);

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement(
                    "SELECT * FROM TUTOREDCOURSES WHERE TUTORID=?");
            st.setInt(1, tutorID);

            final ResultSet tutorResultSet = st.executeQuery();
            tutorProfile = fromResultSetTutor(tutorResultSet,
                                              name,
                                              pronouns,
                                              major);
            rs.close();
            st.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return tutorProfile;
    }

    @Override
    public
    IAccount storeAccount(IAccount newAccount)
    {
        try (final Connection c = connection()) {
            int tutorID = -1;
            if (newAccount.getTutorProfile().isPresent()) {
                ITutor tutorProfile = newAccount.getTutorProfile().get();
                final PreparedStatement pstTutor = c.prepareStatement(
                        "INSERT INTO TUTORS (HOURLYRATE, REVIEWSUM, " +
                        "REVIEWCOUNT) VALUES(?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                pstTutor.setDouble(1, tutorProfile.getHourlyRate());
                pstTutor.setInt(2, tutorProfile.getReviewTotalSum());
                pstTutor.setInt(3, tutorProfile.getReviewCount());
                pstTutor.executeUpdate();
                ResultSet tutorIDRS = pstTutor.getGeneratedKeys();
                if (tutorIDRS.next()) {
                    tutorID = tutorIDRS.getInt(1);
                    tutorProfile.setTutorID(tutorID);
                }

            }
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO ACCOUNTS (EMAIL, PASSWORD, TUTORID, NAME, " +
                    "PRONOUNS, MAJOR) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, newAccount.getEmail());
            pst.setString(2, newAccount.getPassword());
            pst.setInt(3, tutorID);
            pst.setString(2, newAccount.getStudentProfile().get().getName());
            pst.setString(2,
                          newAccount.getStudentProfile().get().getPronouns());
            pst.setString(2, newAccount.getStudentProfile().get().getMajor());
            pst.executeUpdate();
            ResultSet accountIDRS = pst.getGeneratedKeys();
            int       accountID   = accountIDRS.getInt(1);
            newAccount.getStudentProfile().get().setAccountID(accountID);
            if (newAccount.getTutorProfile().isPresent()) {
                newAccount.getTutorProfile().get().setAccountID(accountID);
            }
            pst.close();
            return newAccount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public
    boolean updateAccount(IAccount existingAccount)
    {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "UPDATE ACCOUNTS " +
                    "SET EMAIL = ?, PASSWORD = ?, TUTORID = ?, " +
                    "NAME = ?, PRONOUNS = ?, MAJOR = ? " +
                    "WHERE ACCOUNTID = ?");
            pst.setString(1, existingAccount.getEmail());
            pst.setString(2, existingAccount.getPassword());
            if (existingAccount.getTutorProfile().isPresent()) {
                pst.setInt(3,
                           existingAccount
                                   .getTutorProfile()
                                   .get()
                                   .getTutorID());
                pst.setString(4,
                              existingAccount
                                      .getTutorProfile()
                                      .get()
                                      .getName());
                pst.setString(5,
                              existingAccount
                                      .getTutorProfile()
                                      .get()
                                      .getPronouns());
                pst.setString(6,
                              existingAccount
                                      .getTutorProfile()
                                      .get()
                                      .getMajor());
            } else if (existingAccount.getStudentProfile().isPresent()) {
                pst.setString(4,
                              existingAccount
                                      .getStudentProfile()
                                      .get()
                                      .getName());
                pst.setString(5,
                              existingAccount
                                      .getStudentProfile()
                                      .get()
                                      .getPronouns());
                pst.setString(6,
                              existingAccount
                                      .getStudentProfile()
                                      .get()
                                      .getMajor());
                pst.setString(3, "-1");
            }

            boolean success = pst.executeUpdate() == 1;
            pst.close();
            for (int i = 0; i < this.accounts.size(); i++) {
                if (this.accounts.get(i).getAccountID() ==
                    existingAccount.getAccountID()) {
                    this.accounts.set(i, existingAccount);
                    break;
                }
            }
            return success;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public synchronized
    List<IAccount> getAccounts()
    {
        if (this.accounts == null) {
            this.accounts = new ArrayList<IAccount>();
            this.tutors   = new ArrayList<IAccount>();
            try (final Connection c = connection()) {
                final Statement st = c.createStatement();
                final ResultSet rs = st.executeQuery("SELECT * FROM account");
                while (rs.next()) {
                    IAccount resultAccount = fromResultSet(rs);
                    this.accounts.add(resultAccount);
                    if (resultAccount.getTutorProfile().isPresent()) {
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

    public synchronized
    List<String> getTutorLocations(int tutorID)
    {
        List<String> locations = new ArrayList<String>();

        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "Select LOCATIONNAME from TUTORLOCATIONS " +
                    "where TUTORID = ?");
            pst.setInt(1, tutorID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                locations.add(rs.getString("LOCATIONAME"));
            }
            rs.close();
            pst.close();
            return locations;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public synchronized
    List<TimeSlice> getTutorAvailability(int tutorID)
    {
        List<TimeSlice> availability = new ArrayList<TimeSlice>();

        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "Select START_DATE_TIME, END_DATE_TIME from " +
                    "TUTOR_AVAILABILITY " +
                    "where TUTORID = ?");
            pst.setInt(1, tutorID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                availability.add(fromResultSetTimeSlice(rs));
            }
            rs.close();
            pst.close();
            return availability;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public synchronized
    List<IAccount> getTutorAccounts()
    {
        if (tutors == null) {
            getAccounts();
        }
        return this.tutors;
    }

    private synchronized
    TimeSlice fromResultSetTimeSlice(ResultSet rs) throws SQLException
    {
        final Instant startTime = ((OffsetDateTime) rs.getObject(
                "start_date_time")).toInstant();
        final Instant endTime
                = ((OffsetDateTime) rs.getObject("end_date_time")).toInstant();
        return new TimeSlice(startTime,
                             endTime,
                             Duration.between(startTime, endTime));
    }
}
