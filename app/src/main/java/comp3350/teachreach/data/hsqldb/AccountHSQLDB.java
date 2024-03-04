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

        return new Account(email, password);
    }

    @Override
    public synchronized IAccount storeAccount(IAccount newAccount) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO account VALUES(?, ?)");
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
                    "UPDATE account " +
                            "SET email = ?, password = ? " +
                            "WHERE email = ?");
            pst.setString(1, existingAccount.getEmail());
            pst.setString(2, existingAccount.getPassword());
            pst.setString(3, existingAccount.getEmail());
            boolean success = pst.executeUpdate() == 1;
            pst.close();
            return success;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public synchronized List<IAccount> getAccounts() {
        final List<IAccount> accounts = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM account");
            while (rs.next()) {
                accounts.add(fromResultSet(rs));
            }
            rs.close();
            st.close();
            return accounts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
