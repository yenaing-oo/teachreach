package comp3350.teachreach.logic.communication;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessMessage;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.logic.exceptions.MessageHandleException;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class MessageHandler implements IMessageHandler
{
    private final AccessMessage accessMessage;

    private final AccessStudents accessStudents;

    private final AccessTutors accessTutors;

    //need change for test
    //implement method
    private AccessAccounts accessAccounts = null;

    public MessageHandler(){
        accessMessage = new AccessMessage();
        accessStudents = new AccessStudents();
        accessTutors = new AccessTutors();
        accessAccounts = new AccessAccounts();

    }

    public MessageHandler(AccessMessage accessMessage, AccessStudents accessStudents, AccessTutors accessTutors){
        this.accessMessage =  accessMessage;
        this.accessStudents = accessStudents;
        this.accessTutors = accessTutors;

    }

    public MessageHandler(AccessMessage accessMessage, AccessStudents accessStudents, AccessTutors accessTutors, AccessAccounts accessAccounts){
        this.accessMessage =  accessMessage;
        this.accessStudents = accessStudents;
        this.accessTutors = accessTutors;

    }

    //make IT test constructor
    public boolean validateReceivedMessage(IMessage message){
        return message.getMessage() != null && message.getTime() != null && message.getSenderID() > 0;
    }

    public boolean validateSentMessage(int groupID, int senderAccountID, String message){
        boolean valid = true;
        boolean account = false;
        int findStudentID = 0;
        int findTutorID = 0;

        Map<String, Integer> resultIDs = new HashMap<>();
        resultIDs = accessMessage.searchIDsByGroupID(groupID);

        try {
            IStudent findStudent = accessStudents.getStudentByAccountID(senderAccountID);
            findStudentID = findStudent.getStudentID();
            if( findStudentID >0)
                account = true;
        }
        catch(DataAccessException ignored){
        }

        try {
            ITutor findTutor = accessTutors.getTutorByAccountID(senderAccountID);
            findTutorID = findTutor.getTutorID();
            if( findTutorID >0)
                account = true;
        }
        catch(DataAccessException ignored) {
        }


        if(Objects.requireNonNull(resultIDs.get("StudentID")) != findStudentID && Objects.requireNonNull(resultIDs.get("TutorID")) != findTutorID){
            account = false;
        }
        if(message == null){
            valid = false;
        }
        return account && valid;
    }
    //public boolean validateSenderIDInGroup(){}// do it in database?

    public boolean checkExistGroup(int studentID, int tutorID){
        return (accessMessage.searchGroupByIDs(studentID,tutorID)> 0);
    }



    @Override
    public int createGroup(int studentID, int tutorID)  {

        return !checkExistGroup(studentID, tutorID) ? accessMessage.createGroup(studentID, tutorID) : -1;
    }

    @Override
    public int storeMessage(int groupID, int senderAccountID, String message) throws MessageHandleException {
      try {
          if (validateSentMessage(groupID, senderAccountID, message)) {
              return accessMessage.storeMessage(groupID, senderAccountID, message);
          }
      } catch(final Exception e){
          throw new MessageHandleException("Invalid Message.",e);
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
    @Override
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


    @Override
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
    @Override
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

    @Override
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

    public Map<String,Object> timeStampConverter(@NonNull Timestamp timestamp){

            Map<String, Object> result = new HashMap<>();

        LocalDateTime localDateTime = null; // Initialize localDateTime to null

        Instant instant = Instant.ofEpochMilli(timestamp.getTime());
             localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            // Extract various date-related components
            int year = localDateTime.getYear();
            int month = localDateTime.getMonthValue(); // Month is from 1 to 12
            int dayOfMonth = localDateTime.getDayOfMonth();
            int hour = localDateTime.getHour();
            int minute = localDateTime.getMinute();
            int second = localDateTime.getSecond();

            // Store the components and value in the result map
            result.put("year", year);
            result.put("month", month);
            result.put("dayOfMonth", dayOfMonth);
            result.put("hour", hour);
            result.put("minute", minute);
            result.put("second", second);


            return result;
    }

    @Override
    public List<Integer> retrieveAllGroupsByTutorID(int tutorID){
        return accessMessage.retrieveAllGroupsByTutorID(tutorID);
    }
    @Override
    public List<Integer> retrieveAllGroupsByStudentID(int studentID){
        return accessMessage.retrieveAllGroupsByStudentID(studentID);
    }

    public List<IAccount> retrieveAllChatAccountsByAccountID(int accountID){
        List<IAccount> users = new ArrayList<>();
        List<Integer> groups = new ArrayList<>();

        int findStudentID = -1;
        int findTutorID = -1;
        //find out the original account is a student/tutor
        try {
            IStudent findStudent = accessStudents.getStudentByAccountID(accountID);
            findStudentID = findStudent.getStudentID();
        }
        catch(DataAccessException ignored){
        }

        try {
            ITutor findTutor = accessTutors.getTutorByAccountID(accountID);
            findTutorID = findTutor.getTutorID();
        }
        catch(DataAccessException ignored) {
        }

        //find all chat groups (Accounts)
        if (findStudentID>0){
            groups = accessMessage.retrieveAllTutorIDsByStudentID(findStudentID);

            if(groups!= null){
                for (int group: groups
                ) {
                    IAccount user = accessAccounts.getAccountByAccountID(group).orElse(null);
                    users.add(user);

                }
            }

        }
        else if (findTutorID>0){
            groups = accessMessage.retrieveAllStudentIDsByTutorID(findTutorID);

            if(groups!= null){
                for (int group: groups
                ) {
                    IAccount user = accessAccounts.getAccountByAccountID(group).orElse(null);
                    users.add(user);

                }
            }
        }

        return users;

    }

}
