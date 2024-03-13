package comp3350.teachreach.objects.interfaces;

public interface IMessage {
    int getTutorID();

    int getStudentID();

    String getMessage();

    void setMessage(String message);

    void setStudentID(int studentID);

    void setTutorID(int tutorID);
}
