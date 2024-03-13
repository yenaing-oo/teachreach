package comp3350.teachreach.objects;

import java.time.Instant;

public class Message implements comp3350.teachreach.objects.interfaces.IMessage {

    private int tutorID;

    private int studentID;

    private String message;

    public Message(int tutorID,
                   int studentID,
                   String message){
        this.tutorID = tutorID;
        this.studentID = studentID;
        this.message = message;
    }

    @Override
    public int getTutorID() {
        return tutorID;
    }

    @Override
    public int getStudentID() {
        return studentID;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }
}
