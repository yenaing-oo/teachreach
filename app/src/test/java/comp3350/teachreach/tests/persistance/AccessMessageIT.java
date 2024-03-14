package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.hsqldb.MessageHSQLDB;
import comp3350.teachreach.data.hsqldb.StudentHSQLDB;
import comp3350.teachreach.data.hsqldb.TutorHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.IMessagePersistence;
import comp3350.teachreach.data.interfaces.IStudentPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessMessage;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.Message;
import comp3350.teachreach.objects.Student;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.tests.utils.TestUtils;
import comp3350.teachreach.application.Server;

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
        // assertEquals(testGroupID, this.groupID);

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

    @After
    public
    void tearDown()
    {
        this.tempDB.delete();
    }
}