package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IMessage;

public interface IMessagePersistence {
    void createGroup(int studentAccountID, int tutorAccountID);

    void storeMessage(int groupID, int senderAccountID, String message);

    int searchGroupByIDs(int studentAccountID, int tutorAccountID);

    List<IMessage> retrieveAllMessageByGroupID(int groupID);

   // void deleteGroup();
}
