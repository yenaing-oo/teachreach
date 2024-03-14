package comp3350.teachreach.logic.communication;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import comp3350.teachreach.logic.DAOs.AccessMessage;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class MessageHandler implements comp3350.teachreach.logic.interfaces.IMessageHandler {
    private final AccessMessage accessMessage;

    private final AccessStudents accessStudents;

    private final AccessTutors accessTutors;

    public MessageHandler(){
        accessMessage = new AccessMessage();
        accessStudents = new AccessStudents();
        accessTutors = new AccessTutors();

    }

    //make IT test constructor
    public boolean validateReceivedMessage(IMessage message){
        return message.getMessage() != null && message.getTime() != null && message.getSenderID() > 0;
    }

    public boolean validateSentMessage(int groupID, int senderAccountID, String message){
        boolean valid = true;
        Map<String, Integer> resultIDs = new HashMap<>();
        resultIDs = accessMessage.searchIDsByGroupID(groupID);
        IStudent findStudent = accessStudents.getStudentByAccountID(senderAccountID);
        int findStudentID = findStudent.getStudentID();
        ITutor findTutor = accessTutors.getTutorByAccountID(senderAccountID);
        int findTutorID = findTutor.getTutorID();
        if(resultIDs.get("StudentID").intValue() != findStudentID && resultIDs.get("TutorID").intValue() != findTutorID){
            valid = false;
        }
        if(message == null){
            valid = false;
        }
        return valid;
    }
    //public boolean validateSenderIDInGroup(){}// do it in database?

    public boolean checkExistGroup(int studentID, int tutorID){
        int result = searchGroupByIDs(studentID,tutorID);
        return result > 0;
    }



    @Override
    public int createGroup(int studentID, int tutorID) throws Exception {
        try{
            if(!checkExistGroup(studentID,tutorID)) {
                return accessMessage.createGroup(studentID, tutorID);
            }
        }
        catch(final Exception e){
            throw new Exception("Group already Existed.",e);
        }
        return -1;
    }

    @Override
    public int storeMessage(int groupID, int senderAccountID, String message) throws Exception {
      try {
          if (validateSentMessage(groupID, senderAccountID, message)) {
              return accessMessage.storeMessage(groupID, senderAccountID, message);
          }
      } catch(final Exception e){
          throw new Exception("Invalid Message.",e);
      }
    return -1;
    }

    @Override
    public int searchGroupByIDs(int studentID, int tutorID){
        return accessMessage.searchGroupByIDs(studentID,tutorID);
    }

    @Override
    public List<IMessage> retrieveAllMessageByGroupID(int groupID){
        return accessMessage.retrieveAllMessageByGroupID(groupID);
    }

    //custom function1
    public Map<Integer, Map<java.sql.Timestamp, String>> chatHistoryOfGroupV1(int groupID){
        List<IMessage> messages = accessMessage.retrieveAllMessageByGroupID(groupID);
        Map<Integer, Map<java.sql.Timestamp, String>> chatHistory = new HashMap<>();

        for (IMessage message : messages) {
            if(validateReceivedMessage(message)){
            int senderID = message.getSenderID();
            java.sql.Timestamp timestamp = message.getTime();
            String content = message.getMessage();

            if (!chatHistory.containsKey(senderID)) {
                chatHistory.put(senderID, new HashMap<>());
            }

            chatHistory.get(senderID).put(timestamp, content);
            }

        }

        return chatHistory;
    }


    public Map<java.sql.Timestamp, Map<Integer, String>> chatHistoryOfGroupV2(int groupID){
        List<IMessage> messages = accessMessage.retrieveAllMessageByGroupID(groupID);
        Map<java.sql.Timestamp, Map<Integer, String>> chatHistory = new TreeMap<>();

        for (IMessage message : messages) {
            if(validateReceivedMessage(message)) {
                int senderID = message.getSenderID();
                java.sql.Timestamp timestamp = message.getTime();
                String content = message.getMessage();

                if (!chatHistory.containsKey(timestamp)) {
                    chatHistory.put(timestamp, new HashMap<>());
                }
                chatHistory.get(timestamp).put(senderID, content);
            }
        }

        return chatHistory;


    }
    public Map<Integer, Map<java.sql.Timestamp, String>> chatHistoryOfGroupV3(List<IMessage> messages){
        Map<Integer, Map<java.sql.Timestamp, String>> chatHistory = new HashMap<>();

        for (IMessage message : messages) {
            if(validateReceivedMessage(message)) {
                int senderID = message.getSenderID();
                java.sql.Timestamp timestamp = message.getTime();
                String content = message.getMessage();

                if (!chatHistory.containsKey(senderID)) {
                    chatHistory.put(senderID, new HashMap<>());
                }
                chatHistory.get(senderID).put(timestamp, content);
            }
        }

        return chatHistory;
    }

    public Map<java.sql.Timestamp, Map<Integer, String>> chatHistoryOfGroupV4(List<IMessage> messages){
        Map<java.sql.Timestamp, Map<Integer, String>> chatHistory = new TreeMap<>();

        for (IMessage message : messages) {
            if(validateReceivedMessage(message)) {
                int senderID = message.getSenderID();
                java.sql.Timestamp timestamp = message.getTime();
                String content = message.getMessage();

                if (!chatHistory.containsKey(timestamp)) {
                    chatHistory.put(timestamp, new HashMap<>());
                }
                chatHistory.get(timestamp).put(senderID, content);
            }
        }

        return chatHistory;


    }
}
