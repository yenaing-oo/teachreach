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

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;

public class AccountHSQLDB implements IAccountPersistence {
    private final String dbPath;

    public AccountHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:hsqldb:file:" + dbPath + ";shutdown=true",
                "SA", "");
    }

    private IAccount fromResultSet(final ResultSet rs) throws SQLException {
        final String email = rs.getString("account.email");
        final String password = rs.getString("account.password");

        final String studentName = rs.getString("student.name");
        final String studentMajor = rs.getString("student.major");
        final String studentPronouns = rs.getString("student.pronouns");

        Account resultAccount = new Account(email, password);
        resultAccount.setStudentProfile(new Student(
                studentName,
                studentMajor,
                studentPronouns,
                resultAccount));

        if (rs.getString("tutor.email") != null) {
            final String tutorName = rs.getString("student.name");
            final String tutorMajor = rs.getString("student.major");
            final String tutorPronouns = rs.getString("student.pronouns");
            resultAccount.setTutorProfile(new Tutor(
                    tutorName,
                    tutorMajor,
                    tutorPronouns,
                    resultAccount));
        }

        return resultAccount;
    }

    @Override
    public IAccount storeAccount(IAccount newAccount) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO account VALUES(?, ?)");
            pst.setString(1, newAccount.getEmail());
            pst.setString(2, newAccount.getPassword());
            pst.executeUpdate();
            return newAccount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateAccount(IAccount existingAccount) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "UPDATE account " +
                            "SET email = ?, password = ? " +
                            "WHERE email = ?");
            pst.setString(1, existingAccount.getEmail());
            pst.setString(2, existingAccount.getPassword());
            pst.setString(3, existingAccount.getEmail());
            pst.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<IAccount> getAccountByEmail(String email) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM account " +
                            "JOIN student ON student.email = account.email " +
                            "JOIN tutor ON tutor.email = account.email " +
                            "WHERE account.email = ?");
            pst.setString(1, email);
            final ResultSet rs = pst.executeQuery();
            IAccount account = null;
            if (rs.next()) {
                account = fromResultSet(rs);
            }
            return Optional.ofNullable(account);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<IAccount> getAccounts() {
        final List<IAccount> accounts = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM account " +
                            "JOIN student ON student.email = account.email " +
                            "JOIN tutor ON tutor.email = account.email");
            while (rs.next()) {
                final IAccount account = fromResultSet(rs);
                accounts.add(account);
            }
            rs.close();
            st.close();
            return accounts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
