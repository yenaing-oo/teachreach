package comp3350.teachreach.tests.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.data.hsqldb.MessageHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IMessagePersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessMessage;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessMessageIT {
        private Server server;

        private static AccessMessage accessMessage = null;
    private static IAccountPersistence accessAccount = null;
    private static ITutorPersistence accessTutor = null;
    private static IStudentPersistence accessStudent = null;

       /*
        private AccessAccounts accessAccounts;
        private AccessStudents accessStudents;
        private AccessTutors accessTutors;
*/
        private IStudent testStudent;
        private ITutor testTutor;

        private int groupID;


        private File tempDB;


        @Before
        public
        void setUp() throws IOException
        {
            //accessMessage = server.getMessageDataAccess();
            //accessAccount = server.getAccountDataAccess();
            //accessTutor = server.getTutorDataAccess();
            //accessStudent = server.getStudentDataAccess();

           this.tempDB = TestUtils.copyDB();
           final IMessagePersistence persistence = new
                    MessageHSQLDB(this.tempDB
                    .getAbsolutePath()
                    .replace(
                            ".script",
                            ""));
            this.accessMessage = new AccessMessage(persistence);

            /*final IAccountPersistence persistence2 = new AccessAccounts();
             AccountHSQLDB(this.tempDB
                    .getAbsolutePath()
                    .replace(
                            ".script",
                            ""));
            this.accessAccounts = new AccessAccounts(persistence2);

            final ITutorPersistence persistence3 = new AccessTutors();
            TutorHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));
            this.accessTutors = new AccessTutors(persistence3);

            final IStudentPersistence persistence4 = new StudentHSQLDB(this.tempDB
                    .getAbsolutePath()
                    .replace(
                            ".script",
                            ""));
            this.accessStudents = new AccessStudents(persistence4);


            accessMessage = new AccessMessage();
            accessAccounts =new AccessAccounts();
            accessStudents = new AccessStudents();
            accessTutors = new AccessTutors();

                    IAccount tutor = new Account("guderr@myumanitoba.ca",
                    "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI",
                    "Robert Guderian",
                    "He/Him",
                    "Computer Science");


             IAccount student = new Account("mcmill5@myumanitoba.ca",
                    "$2a$12$LMXSy2E2SRxXOyUzT2hwuOV.",
                    "Camryn Mcmillan",
                    "She/Her",
                     "Computer Science"
                                    );


            accessAccount.storeAccount(tutor);
            accessAccount.storeAccount(student);
            this.testTutor = new Tutor(tutor.getAccountID());
            accessTutor.storeTutor(testTutor);
            this.testStudent = new Student(student.getAccountID());
            accessStudent.storeStudent(testStudent);
*/
        }

        @Test
        public void testCreateGroup(){
           this.groupID = accessMessage.createGroup(1,1);
           // this.groupID = accessMessage.createGroup(this.testStudent.getAccountID(),this.testTutor.getAccountID());
            assertEquals(1, this.groupID);

        }

    @Test
    public void testStoreMessage(){
        int groupID = accessMessage.createGroup(2,1);
        assertEquals(1,groupID);
         List<IMessage> messages = accessMessage.retrieveAllMessageByGroupID(groupID);
        assertEquals(2,messages.size());
        int message_ID = accessMessage.storeMessage(groupID,1,"HELLO WORLD!");
        assertEquals(1,message_ID);
            //IMessage testMessage = accessMessage.storeMessage(this.groupID,1,"HELLO WORLD!");
        //assertNotNull(testMessage);
            //assertEquals(testMessage.getMessage(), "HELLO WORLD!");
            //assertEquals(testMessage.getSenderID(),1);
            //assertNotNull(testMessage.getTime());
    }

    @Test
    public void testSearchGroupByIDs(){
         accessMessage.createGroup(1,1);
            int testGroupID = accessMessage.searchGroupByIDs(1,1);
        assertEquals( 1,testGroupID);
        accessMessage.createGroup(2,1);
         testGroupID = accessMessage.searchGroupByIDs(2,1);
        assertEquals( 2,testGroupID);
        assertTrue(testGroupID>0);
        testGroupID = accessMessage.searchGroupByIDs(3,4);
        assertFalse(testGroupID>0);
        assertEquals(-1,testGroupID);
        // assertEquals(testGroupID, this.groupID);

    }

    @Test
    public void testSearchIDsByGroupID(){
        int groupID = accessMessage.createGroup(2,1);
        Map<String, Integer> IDs = new HashMap<>();
        IDs = accessMessage.searchIDsByGroupID(groupID);
        assertEquals(2, Objects.requireNonNull(IDs.get("StudentID")).intValue());
        assertEquals(1, Objects.requireNonNull(IDs.get("TutorID")).intValue());

    }

    @Test
    public void testRetrieveAllMessageByGroupID(){
        int groupID = accessMessage.createGroup(1,1);
            List<IMessage> testMessages = accessMessage.retrieveAllMessageByGroupID(1);
            assertEquals(2,testMessages.size());
        List<IMessage> testMessages2 = accessMessage.retrieveAllMessageByGroupID(2);
        assertEquals(1,testMessages2.size());
        //assertNotNull(testMessages2.get(0).getMessage());
        assertEquals(1,testMessages.get(0).getSenderID());

       //assertNull(testMessages.get(0).getTime());
        accessMessage.storeMessage(1,1,"GOOD MORNING!");
        //accessMessage.storeMessage(this.groupID,1,"GOOD MORNING!");
        accessMessage.storeMessage(1,2,"I'M FINE THANK YOU!");
        //accessMessage.storeMessage(this.groupID,2,"I'M FINE THANK YOU!");
        testMessages = accessMessage.retrieveAllMessageByGroupID(1);
        assertEquals(4,testMessages.size());

    }

    @Test
    public void testTimeStamp(){
        int groupID = accessMessage.createGroup(1,1);
        int newMessage = accessMessage.storeMessage(1,1,"GOOD MORNING!");
        List<IMessage> testMessages = accessMessage.retrieveAllMessageByGroupID(1);
        assertEquals(3,testMessages.size());
        assertEquals("GOOD MORNING!",testMessages.get(2).getMessage());
        Timestamp time = testMessages.get(2).getTime();
        assertNotNull(time);

    }


    @Test
    public void test(){
        assertEquals(1,accessMessage.createGroup(1,1));
        assertEquals(2,accessMessage.createGroup(2,1));
        assertEquals(3,accessMessage.createGroup(2,2));
        List<Integer> studentIDs = accessMessage.retrieveAllStudentIDsByTutorID(2);
        assertEquals(1,studentIDs.size());
        System.out.println(studentIDs.get(0));//+"   "+studentIDs.get(1));
    }

    @Test
    public void test2(){
       // assertEquals(1,accessMessage.createGroup(1,1));
       // assertEquals(2,accessMessage.createGroup(1,2));
       // assertEquals(2,accessMessage.retrieveAllTutorIDsByStudentID(1).size());

        assertEquals(1,accessMessage.createGroup(2,1));
        assertEquals(2,accessMessage.createGroup(2,2));
        List<Integer> tutorIDs = accessMessage.retrieveAllTutorIDsByStudentID(2);
        assertEquals(1,tutorIDs.size());
        System.out.println(tutorIDs.get(0));

    }
    @After
    public
    void tearDown()
    {
        this.tempDB.delete();
    }
}
