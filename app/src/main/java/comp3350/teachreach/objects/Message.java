package comp3350.teachreach.objects;

import java.time.Instant;

public class Message {

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

    public int getTutorID() {
        return tutorID;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }
}
