package comp3350.teachreach.logic.interfaces;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.objects.interfaces.IMessage;

public interface MessageHandler {
    int createGroup(int studentID, int tutorID) throws Exception;

    int storeMessage(int groupID, int senderAccountID, String message) throws Exception;

    int searchGroupByIDs(int studentID, int tutorID);

    List<IMessage> retrieveAllMessageByGroupID(int groupID);

    //custom function1
    Map<Integer, Map<Timestamp, String>> chatHistoryOfGroupV1(int groupID);

    Map<Timestamp, Map<Integer, String>> chatHistoryOfGroupV2(int groupID);

    Map<Integer, Map<Timestamp, String>> chatHistoryOfGroupV3(List<IMessage> messages);

    Map<Timestamp, Map<Integer, String>> chatHistoryOfGroupV4(List<IMessage> messages);
}
