package comp3350.teachreach.objects;

public class Session {
    private Student student;
    private Tutor tutor;
    private int day;
    private int month;
    private int year;
    private int hour;
    private boolean accepted;
    private String location;

    public Session(Student student, Tutor tutor, int day, int month, int year, int hour, String location) {
        this.student = student;
        this.tutor = tutor;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.accepted = false;
        this.location = location;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student newstudent) {
        this.student = newstudent;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor newtutor) {
        this.tutor = newtutor;
    }


    public int getDay() {
        return this.day;
    }
    public int getMonth() {
        return this.month;
    }
    public int getYear() {
        return this.year;
    }

    public void setDay(int newDay) {
        this.day = newDay;
    }

    public void setMonth(int newMonth) {
        this.month = newMonth;
    }

    public void setYear(int newYear) {
        this.year = newYear;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int newHour) {
        this.hour = newHour;
    }

    public boolean getStage() {
        return this.accepted;
    }

    public void setStage(boolean decision) {
        this.accepted = decision;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
