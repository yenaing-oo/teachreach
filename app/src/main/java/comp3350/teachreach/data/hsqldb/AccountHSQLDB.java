package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.IAccount;

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
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        return new Account(email, password);
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
                    "SELECT * FROM account WHERE email = ?");
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
}
