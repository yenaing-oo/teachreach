package comp3350.teachreach.objects;

public class Session {
    private Student student;
    private Tutor tutor;
    private int[] date
    private int hour;
    private boolean accepted;
    private String location;

    public Session(Student student, Tutor tutor, int day, int month, int year, int hour, String location) {
        this.student = student;
        this.tutor = tutor;
        this.date = {day, month, year};
        this.hour = hour;
        this.accepted = false;
        this.location = location;
    }

    public void acceptSession() {
        this.accepted = true;
    }
    
    public Student getStudent() {
        return this.student;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public int[] getDate() {
        return this.date;
    }

    public void setDate(int day, int month, int year) {
        this.date = {date,month,year}
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getHour(){ return this.hour; }
}