package comp3350.teachreach.logic.DAOs;

import java.util.List;
import java.util.Map;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.interfaces.IMessagePersistence;
import comp3350.teachreach.data.interfaces.ISessionPersistence;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.IMessage;

public class AccessMessage {

    private static IMessagePersistence messagePersistence;
   // private static Map<String, ICourse> courses = null;

    public
    AccessMessage ()
    {
        AccessMessage.messagePersistence = Server.getMessageDataAccess();
    }

    public
    AccessMessage (IMessagePersistence messageDataAccess)
    {
        AccessMessage.messagePersistence = messageDataAccess;

    }

    public  void createGroup(int studentAccountID, int tutorAccountID){
        try{
            messagePersistence.createGroup(studentAccountID,tutorAccountID);
        }
        catch (final Exception e) {
            throw new DataAccessException("Group might've already exist!", e);
        }
    }



    public void storeMessage(int groupID, int senderAccountID, String message){
        try{
            messagePersistence.storeMessage(groupID,senderAccountID,message);
        }
        catch (final Exception e) {
            throw new DataAccessException("Exception on storing Message!", e);
        }
    }
    public int searchGroupByIDs(int studentAccountID, int tutorAccountID){
        try{
            return messagePersistence.searchGroupByIDs(studentAccountID,  tutorAccountID);
        }
        catch (final Exception e) {
            throw new DataAccessException("Exception on searching Group By IDs!", e);
        }
    }

    public List<IMessage> retrieveAllMessageByGroupID(int groupID){
        try{
            return messagePersistence.retrieveAllMessageByGroupID(groupID);
        }
        catch (final Exception e) {
            throw new DataAccessException("Exception on searching Group By IDs!", e);
        }
    }
}
