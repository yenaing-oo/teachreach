package comp3350.teachreach.tests.logic;

public class MessageHandlerTest {
/*
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

        final IAccountPersistence persistence4 = new
                AccountHSQLDB(this.tempDB
                .getAbsolutePath()
                .replace(
                        ".script",
                        ""));

        this.messageHandler = new MessageHandler(new AccessMessage(persistence), new AccessStudents(persistence2), new AccessTutors(persistence3), new AccessAccounts(persistence4));
    }

    @Test
    public void testCheckExistGroup() {
        assertFalse(messageHandler.checkExistGroup(2, 2));
    }


    @Test
    public void testCreateGroup() throws MessageHandleException {
        assertTrue(messageHandler.createGroup(2, 1) > 0);
    }

    @Test
    public void testValidateSentMessage() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2, 1);
        assertTrue(messageHandler.validateSentMessage(groupID, 2, "Hello World!"));

    }

    @Test
    public void testStoreMessage() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2, 1);
        assertTrue(messageHandler.storeMessage(groupID, 2, "HELLO WORLD") > 0);
        assertTrue(messageHandler.storeMessage(groupID, 3, "HELLO WORLD") > 0);
    }


    @Test
    public void testSearchGroupByIDs() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2, 1);
        assertTrue(messageHandler.createGroup(1, 2) > 0);
        assertEquals(messageHandler.searchGroupByIDs(2, 1), groupID);
        assertTrue(messageHandler.searchGroupByIDs(1, 2) > 0);
    }


    @Test
    public void testRetrieveAllMessageByGroupID() throws MessageHandleException {
        messageHandler.storeMessage(1, 1, "Test Messages");
        assertTrue(messageHandler.retrieveAllMessageByGroupID(1).size() > 0);
    }


    @Test
    public void testChatHistoryOfGroupV1() throws MessageHandleException {
        int groupID = messageHandler.createGroup(2, 1);
        assertEquals(2, groupID);

        messageHandler.storeMessage(groupID, 2, "HELLO WORLD");
        Map<Integer, Map<Timestamp, String>> messages = messageHandler.chatHistoryOfGroupV1(groupID);
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
        int groupID = messageHandler.createGroup(2, 1);
        assertEquals(2, groupID);
        messageHandler.storeMessage(groupID, 2, "HELLO WORLD");
        Map<Timestamp, Map<Integer, String>> messages = messageHandler.chatHistoryOfGroupV2(groupID);
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
        int groupID = messageHandler.createGroup(2, 1);
        assertEquals(2, groupID);
        messageHandler.storeMessage(groupID, 2, "HELLO WORLD");
        List<IMessage> messagesList = messageHandler.retrieveAllMessageByGroupID(groupID);
        Map<Integer, Map<Timestamp, String>> messagesMap = messageHandler.chatHistoryOfGroupV3(messagesList);
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
        int groupID = messageHandler.createGroup(2, 1);
        assertEquals(2, groupID);
        messageHandler.storeMessage(groupID, 2, "HELLO WORLD");
        Thread.sleep(5000);
        messageHandler.storeMessage(groupID, 3, "HELLO TUTOR");
        assertEquals(2, messageHandler.retrieveAllMessageByGroupID(groupID).size());
        List<IMessage> messagesList = messageHandler.retrieveAllMessageByGroupID(groupID);
        Map<Timestamp, Map<Integer, String>> messagesMap = messageHandler.chatHistoryOfGroupV4(messagesList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        assertNotNull(messagesMap);
        assertFalse(messagesMap.isEmpty());


        for (Map.Entry<Timestamp, Map<Integer, String>> entry : messagesMap.entrySet()) {
            Timestamp timestamp = entry.getKey();
            Map<Integer, String> senderMessages = entry.getValue();

            assertNotNull(timestamp);
            assertFalse(senderMessages.isEmpty());

            LocalDateTime localDateTime = null;

            Instant instant = Instant.ofEpochMilli(timestamp.getTime());
            localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());


            int year = localDateTime.getYear();
            int month = localDateTime.getMonthValue();
            int day = localDateTime.getDayOfMonth();
            int hour = localDateTime.getHour();
            int DayOfMonth = localDateTime.getDayOfMonth();

            // Print date, year, and hour
            System.out.println("Date: " + year + "-" + month + "-" + day);
            System.out.println("Year: " + year);
            System.out.println("Hour: " + hour);
            System.out.println("DayofMonth: " + DayOfMonth);

            for (Map.Entry<Integer, String> senderMessage : senderMessages.entrySet()) {
                int senderID = senderMessage.getKey();
                String message = senderMessage.getValue();

                // Print sender ID and message
                System.out.println("TimeStamp: " + timestamp);
                System.out.println("Sender ID: " + senderID);
                System.out.println("Message: " + message);
                System.out.println(timestamp.getTime());
                System.out.println(timestamp.getNanos());
                //String localDateTime = timestamp.toInstan
                System.out.println(dateFormat.format(timestamp));


                //Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
                //long localDateTime = timestamp2.getTime();
                //System.out.println("Timestamp2: " + localDateTime);
            }

        }


    }


    @Test
    public void test() {

        assertEquals(2, messageHandler.createGroup(1, 2));
        assertEquals(3, messageHandler.createGroup(2, 2));
        assertEquals(4, messageHandler.createGroup(2, 1));
        List<IAccount> contactAccounts = messageHandler.retrieveAllChatAccountsByAccountID(1);
        assertEquals(2, contactAccounts.size());


    }

    @Test
    public void test2() {

        messageHandler.createGroup(1, 1);
        messageHandler.createGroup(1, 2);
        messageHandler.createGroup(2, 2);
        messageHandler.createGroup(2, 1);
        List<Integer> contactAccounts = messageHandler.retrieveAllGroupsByTutorID(2);
        assertEquals(2, contactAccounts.size());
        System.out.println(contactAccounts.get(0) + "  " + contactAccounts.get(1));
    }

    @Test
    public void test3() {
        assertEquals(2, messageHandler.createGroup(1, 2));
        List<Integer> contactAccounts = messageHandler.retrieveAllGroupsByStudentID(1);
        assertEquals(2, contactAccounts.size());
        System.out.println(contactAccounts.get(0));
    }

*/
}
