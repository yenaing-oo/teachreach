//package comp3350.teachreach.data.stubs;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//import comp3350.teachreach.data.interfaces.ISessionPersistence;
//import comp3350.teachreach.objects.Session;
//import comp3350.teachreach.objects.interfaces.ISession;
//import comp3350.teachreach.objects.interfaces.IStudent;
//import comp3350.teachreach.objects.interfaces.ITimeSlice;
//import comp3350.teachreach.objects.interfaces.ITutor;
//
//public
//class SessionStub implements ISessionPersistence
//{
//    private static Map<Integer, ISession> sessions;
//    private static int                    sessionCount = 1;
//
//    public
//    SessionStub()
//    {
//        if (sessions == null) {
//            SessionStub.sessions = new HashMap<>();
//        }
//    }
//
//    /**
//     * @param student   the student's id
//     * @param tutor     the tutor's id
//     * @param timeRange a TimeSlice consist of start and end Instant
//     * @param location  location set for this session
//     * @return the new session stored with an assigned sessionID
//     */
//    @Override
//    public ISession storeSession(IStudent student,
//                                 ITutor tutor,
//                                 ITimeSlice timeRange,
//                                 String location) {
//        ISession newSession = sessions.put(sessionCount,
//                new Session(sessionCount,
//                        student,
//                        tutor,
//                        timeRange,
//                        location));
//        sessionCount++;
//        return newSession;
//    }
//
//    /**
//     * @param newSession a new ISession object to be inserted
//     * @return the newSession stored with an assigned sessionID
//     */
//    @Override
//    public
//    ISession storeSession(ISession newSession)
//    {
//        return storeSession(newSession.getSessionStudentID(),
//                            newSession.getSessionTutorID(),
//                            newSession.getTime(),
//                            newSession.getSessionLocation());
//    }
//
//    /**
//     * @param session ID of session to be deleted
//     * @return if deletion is successful
//     */
//    @Override
//    public boolean deleteSession(ISession session) {
//        return false;
//    }
//
//    /**
//     * @param existingSession an existing session to be updated
//     * @return the updated session object
//     */
//    @Override
//    public
//    ISession updateSession(ISession existingSession)
//    {
//        if (SessionStub.sessions.containsKey(existingSession.getSessionID())) {
//            return SessionStub.sessions.put(existingSession.getSessionID(),
//                                            existingSession);
//        } else {
//            throw new NoSuchElementException();
//        }
//    }
//
//    /**
//     * @return a Map of Integer sessionID -> ISession objects
//     */
//    @Override
//    public
//    Map<Integer, ISession> getSessions()
//    {
//        return SessionStub.sessions;
//    }
//}
