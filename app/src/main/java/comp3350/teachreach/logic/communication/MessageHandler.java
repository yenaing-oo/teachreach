package comp3350.teachreach.logic.communication;

import java.util.List;

import comp3350.teachreach.logic.DAOs.AccessMessage;
import comp3350.teachreach.objects.interfaces.IMessage;

public class MessageHandler implements comp3350.teachreach.logic.interfaces.IMessageHandler {
    private final AccessMessage accessMessage;

    public MessageHandler(){
        accessMessage = new AccessMessage();

    }

    //make IT test constructor

    @Override
    public int createGroup(int studentID, int tutorID){
        return accessMessage.createGroup(studentID,tutorID);
    }

    @Override
    public int storeMessage(int groupID, int senderAccountID, String message){
        return accessMessage.storeMessage(groupID,senderAccountID,message);
    }

    @Override
    public int searchGroupByIDs(int studentID, int tutorID){
        return accessMessage.searchGroupByIDs(studentID,tutorID);
    }

    @Override
    public List<IMessage> retrieveAllMessageByGroupID(int groupID){
        return accessMessage.retrieveAllMessageByGroupID(groupID);
    }
}
