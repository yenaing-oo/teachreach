package comp3350.teachreach.tests.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.data.hsqldb.MessageHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.IMessagePersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessMessage;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.exceptions.MessageHandleException;
import comp3350.teachreach.objects.Message;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.tests.utils.TestUtils;

public class MessageHandlerTest {

    private MessageHandler messageHandler;
    private IMessage testMessage;
    private File tempDB;
    @Before
    public void setUp() throws IOException {
        System.out.println("Starting a new test for MessageHandler");
        this.tempDB = TestUtils.copyDB();
        final IMessagePersistence persistence = new
                MessageHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        final IStudentPersistence persistence2 = new
                StudentHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
        final ITutorPersistence persistence3 = new
                TutorHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));

        this.messageHandler = new MessageHandler(new AccessMessage(persistence), new AccessStudents(persistence2), new AccessTutors(persistence3));
        //messageHandler = new MessageHandler();
    }

    @Test
    public void testCheckExistGroup(){
        assertFalse(messageHandler.checkExistGroup(2,2));
    }


    @Test
    public void testCreateGroup() throws MessageHandleException {
        assertEquals(1,messageHandler.createGroup(2,1));
    }

    @Test
    public void testValidateSentMessage() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertTrue(messageHandler.validateSentMessage(groupID,2,"Hello World!"));

    }

    @Test
    public void testStoreMessage() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertFalse(messageHandler.storeMessage(groupID,1,"HELLO WORLD")>0);
        assertTrue(messageHandler.storeMessage(groupID,2,"HELLO WORLD")>0);
    }

//    public void testValidateReceivedMessage(){
//        messageHandler.validateReceivedMessage()
//    }


    @Test
    public void testSearchGroupByIDs() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertEquals(messageHandler.searchGroupByIDs(2, 1), groupID);
    }

//

    @Test
    public void testRetrieveAllMessageByGroupID() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertEquals(1,groupID);
        assertEquals(2,messageHandler.retrieveAllMessageByGroupID(groupID).size());
    }

//
//
//

    @Test
    public void testChatHistoryOfGroupV1() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertEquals(1,groupID);

        messageHandler.storeMessage(groupID,2,"HELLO WORLD");
        Map<Integer,Map<Timestamp,String>> messages = messageHandler.chatHistoryOfGroupV1(groupID);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());


        assertTrue(messages.containsKey(2));
        assertTrue(messages.get(2).containsValue("HELLO WORLD"));

        for (Map.Entry<Integer, Map<Timestamp, String>> entry : messages.entrySet()) {
            int senderID = entry.getKey();
            Map<Timestamp, String> senderMessages = entry.getValue();

            for (Map.Entry<Timestamp, String> messageEntry : senderMessages.entrySet()) {
                Timestamp timestamp = messageEntry.getKey();
                String messageContent = messageEntry.getValue();

                System.out.println("Sender ID: " + senderID);
                System.out.println("Timestamp: " + timestamp);
                System.out.println("Message: " + messageContent);
                Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
                long localDateTime = timestamp2.getTime();
                System.out.println("Timestamp2: " + timestamp);

            }
        }

    }
    @Test
    public void testChatHistoryOfGroupV2() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertEquals(1,groupID);
        messageHandler.storeMessage(groupID,2,"HELLO WORLD");
        Map<Timestamp,Map<Integer,String>> messages = messageHandler.chatHistoryOfGroupV2(groupID);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());


        for (Map.Entry<Timestamp, Map<Integer, String>> entry : messages.entrySet()) {
            Timestamp timestamp = entry.getKey();
            Map<Integer, String> senderMessages = entry.getValue();

            assertNotNull(timestamp);
            assertFalse(senderMessages.isEmpty());

            assertTrue(senderMessages.containsKey(2));
            assertTrue(senderMessages.containsValue("HELLO WORLD"));

            for (Map.Entry<Integer, String> senderMessage : senderMessages.entrySet()) {
                int senderID = senderMessage.getKey();
                String message = senderMessage.getValue();

                // Print sender ID and message
                System.out.println("Sender ID: " + senderID);
                System.out.println("Message: " + message);
                Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
                long localDateTime = timestamp2.getTime();
                System.out.println("Timestamp2: " + timestamp);
            }

        }
    }

    @Test
    public void testChatHistoryOfGroupV3() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2,1);
        assertEquals(1,groupID);
        messageHandler.storeMessage(groupID,2,"HELLO WORLD");
        List<IMessage> messagesList = messageHandler.retrieveAllMessageByGroupID(groupID);
        Map<Integer,Map<Timestamp,String>> messagesMap = messageHandler.chatHistoryOfGroupV3(messagesList);
        assertNotNull(messagesMap);
        assertFalse(messagesMap.isEmpty());


        assertTrue(messagesMap.containsKey(2));
        assertTrue(messagesMap.get(2).containsValue("HELLO WORLD"));

        for (Map.Entry<Integer, Map<Timestamp, String>> entry : messagesMap.entrySet()) {
            int senderID = entry.getKey();
            Map<Timestamp, String> senderMessages = entry.getValue();

            for (Map.Entry<Timestamp, String> messageEntry : senderMessages.entrySet()) {
                Timestamp timestamp = messageEntry.getKey();
                String messageContent = messageEntry.getValue();

                System.out.println("Sender ID: " + senderID);
                System.out.println("Timestamp: " + timestamp);
                System.out.println("Message: " + messageContent);
                Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
                long localDateTime = timestamp2.getTime();
                System.out.println("Timestamp2: " + timestamp);

            }
        }
    }
    @Test
    public void testChatHistoryOfGroupV4() throws MessageHandleException, InterruptedException {
        int groupID = messageHandler.createGroup(2,1);
        assertEquals(1,groupID);
        messageHandler.storeMessage(groupID,2,"HELLO WORLD");
        Thread.sleep(5000);
        messageHandler.storeMessage(groupID,3,"HELLO TUTOR");
        assertEquals(4,messageHandler.retrieveAllMessageByGroupID(groupID).size());
        List<IMessage> messagesList = messageHandler.retrieveAllMessageByGroupID(groupID);
        Map<Timestamp,Map<Integer,String>> messagesMap = messageHandler.chatHistoryOfGroupV4(messagesList);

        assertNotNull(messagesMap);
        assertFalse(messagesMap.isEmpty());


        for (Map.Entry<Timestamp, Map<Integer, String>> entry : messagesMap.entrySet()) {
            Timestamp timestamp = entry.getKey();
            Map<Integer, String> senderMessages = entry.getValue();

            assertNotNull(timestamp);
            assertFalse(senderMessages.isEmpty());

            //assertTrue(senderMessages.containsKey(2));
            //assertTrue(senderMessages.containsValue("HELLO WORLD"));

            for (Map.Entry<Integer, String> senderMessage : senderMessages.entrySet()) {
                int senderID = senderMessage.getKey();
                String message = senderMessage.getValue();

                // Print sender ID and message
                System.out.println("TimeStamp: " + timestamp);
                System.out.println("Sender ID: " + senderID);
                System.out.println("Message: " + message);

                Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
                long localDateTime = timestamp2.getTime();
                System.out.println("Timestamp2: " + localDateTime);
            }

        }


    }

//    public void test6(){
//        messageHandler.searchID
//    }


}
