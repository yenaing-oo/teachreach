package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.objects.Message;
import comp3350.teachreach.objects.interfaces.IMessage;

public class MessageHSQLDB implements comp3350.teachreach.data.interfaces.IMessagePersistence {
    private final String dbPath;

    public
    MessageHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException
    {
        return DriverManager.getConnection(String.format(
                "jdbc:hsqldb:file:%s;shutdown=true",
                dbPath), "SA", "");
    }

    private IMessage fromResultSet(final ResultSet rs) throws SQLException
    {
        final int       senderAccountID      = rs.getInt("SENDER_ID");
        final Timestamp     time   = rs.getTimestamp("TIME_SENT");

        final String    message      = rs.getString("MESSAGE");

        return  new Message(senderAccountID,
                time,
                message);
    }


    @Override
    public int createGroup(int studentAccountID, int tutorAccountID){
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO CHAT_GROUPS(STUDENT_ACCOUNT_ID, TUTOR_ACCOUNT_ID) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,  studentAccountID);
            pst.setInt(2, tutorAccountID);
            pst.executeUpdate();
            boolean success = pst.executeUpdate() == 1;
            pst.close();
            if (!success) {
                c.close();
                throw new PersistenceException("Chat Group mightn't be stored!");
            }
            final ResultSet rs = pst.getGeneratedKeys();
            return rs.getInt(1);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public IMessage storeMessage(int groupID, int senderAccountID, String message){
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement("INSERT INTO MESSAGES( group_ID, sender_ID, message"+
                    "VALUES()) VALUES(?,?,?) ");
            pst.setInt(1, groupID);
            pst.setInt(2, senderAccountID);
            pst.setString(3, message);

            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pst.close();
                rs.close();
                return fromResultSet(rs);
            }
            else {
                rs.close();
                c.close();
                throw new PersistenceException("Message not generated!");
            }

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }



    @Override
    public int searchGroupByIDs(int studentAccountID, int tutorAccountID){
        final int resultGroupID;
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM GROUPS WHERE STUDENT_ACCOUNT_ID = ? AND TUTOR_ACCOUNT_ID =?");
            pst.setInt(1, studentAccountID);
            pst.setInt(2, tutorAccountID);
            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                resultGroupID = rs.getInt("GROUP_ID");

            pst.close();
            rs.close();
            return resultGroupID;}
            else {
                rs.close();
                c.close();
                throw new PersistenceException("GroupID not generated!");
        }} catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<IMessage> retrieveAllMessageByGroupID(int groupID){
        List<IMessage> resultMessages = new ArrayList<>();
        try(final Connection c = connection()){
            final PreparedStatement pst = c.prepareStatement("Select * from MESSAGE where group_id = ? ORDER BY MESSAGE_ID ASC");
            pst.setInt(1, groupID);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                resultMessages.add(fromResultSet(rs));
            }
            pst.close();
            rs.close();
            return resultMessages;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }



    //public void deleteMessage(){}


///Modifies Message


 //   public void deleteGroup(){}

}
