package comp3350.teachreach.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final int senderAccountID = rs.getInt("SENDER_ID");
        final Timestamp time = rs.getTimestamp("TIME_SENT");
        //if (rs.wasNull()) {
          //  throw new PersistenceException("Timestamp is null");
        //}
        final String message = rs.getString("MESSAGE");

        return new Message(senderAccountID, time, message);
        /*final int       senderAccountID      = rs.getInt("SENDER_ID");
        final Timestamp     time   = rs.getTimestamp("TIME_SENT");
       //if(time == null){
         //   throw new PersistenceException("timeStamp not retrieved!");
        //}
        final String    message      = rs.getString("MESSAGE");

        return  new Message(senderAccountID,
                time,
                message);*/
    }


    @Override
    public int createGroup(int studentID, int tutorID){
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "INSERT INTO CHAT_GROUPS (STUDENT_ID, TUTOR_ID) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, studentID);
            pst.setInt(2, tutorID);
            boolean success = pst.executeUpdate() == 1;
            if (!success) {
                pst.close();
                c.close();
                throw new PersistenceException("Chat Group mightn't be stored!");
            }

            final ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                pst.close();
                rs.close();
                return generatedKey;
            } else {
                pst.close();
                rs.close();
                throw new PersistenceException("No generated keys found");
            }

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int storeMessage(int groupID, int senderAccountID, String message) {
        try (final Connection c = connection()) {
            final PreparedStatement pst = c.prepareStatement("INSERT INTO MESSAGES(GROUP_ID, SENDER_ID, MESSAGE) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, groupID);
            pst.setInt(2, senderAccountID);
            pst.setString(3, message);
            final boolean success = pst.executeUpdate() == 1;
            if (success) {
                int messageID;

                final ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    messageID =  rs.getInt(1);
                    rs.close();
                    pst.close();
                    return messageID;
                } else {
                    rs.close();
                    pst.close();
                    throw new PersistenceException("Message mightn't be updated!");
            }
            }else {
                pst.close();
                throw new PersistenceException("Failed to store message");
            }

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }



    @Override
    public int searchGroupByIDs(int studentID, int tutorID){
        final int resultGroupID;
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM CHAT_GROUPS WHERE STUDENT_ID = ? AND TUTOR_ID = ?");
                    //"SELECT * FROM CHAT_GROUPS WHERE student_account_id = ? AND tutor_account_id =?");
            pst.setInt(1, studentID);
            pst.setInt(2, tutorID);
            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                resultGroupID = rs.getInt("GROUP_ID");
            pst.close();
            rs.close();
            return resultGroupID;
            }
            else {
                rs.close();
                c.close();
                throw new PersistenceException("GroupID not generated!");
        }} catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public Map<String, Integer> searchIDsByGroupID(int groupID){
        final Map<String, Integer>  resultIDs = new HashMap<>();
        try (final Connection c = this.connection()) {
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM CHAT_GROUPS WHERE GROUP_ID = ?");
            //"SELECT * FROM CHAT_GROUPS WHERE student_account_id = ? AND tutor_account_id =?");
            pst.setInt(1, groupID);

            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int studentID = rs.getInt("STUDENT_ID");
                int tutorID = rs.getInt("TUTOR_ID");
                resultIDs.put("StudentID",studentID);
                resultIDs.put("TutorID",tutorID);
                pst.close();
                rs.close();
                return resultIDs;}
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
            final PreparedStatement pst = c.prepareStatement(
                    "SELECT * FROM MESSAGES where group_id = ? ORDER BY MESSAGE_ID ASC");
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
