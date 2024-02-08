package comp3350.teachreach.logic;

import comp3350.teachreach.data.*;

public class Server {
  private static IAccountPersistence accountsDataAccess;
  private static CourseStub courses;
  private static SessionStub sessions;

  public static synchronized IAccountPersistence getAccounts() {
    if (accountsDataAccess == null) {
      accountsDataAccess = new AccountStub();
    }

    return accountsDataAccess;
  }

  public static synchronized CourseStub getCourses() {
    if (courses == null) {
      courses = new CourseStub();
    }

    return courses;
  }

  public static synchronized SessionStub getSessions() {
    if (sessions == null) {
      sessions = new SessionStub();
    }

    return sessions;
  }
}
