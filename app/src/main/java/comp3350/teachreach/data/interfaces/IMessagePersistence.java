package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IMessage;

public interface IMessagePersistence {
    int createGroup(int studentID, int tutorID);

    IMessage storeMessage(int groupID, int senderAccountID, String message);

    int searchGroupByIDs(int studentAccountID, int tutorAccountID);

    List<IMessage> retrieveAllMessageByGroupID(int groupID);

   // void deleteGroup();
}
