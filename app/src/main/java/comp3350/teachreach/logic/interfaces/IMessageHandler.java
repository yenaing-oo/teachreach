package comp3350.teachreach.logic.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IMessage;

public interface IMessageHandler {
    int createGroup(int studentID, int tutorID);

    int storeMessage(int groupID, int senderAccountID, String message);

    int searchGroupByIDs(int studentID, int tutorID);

    List<IMessage> retrieveAllMessageByGroupID(int groupID);
}