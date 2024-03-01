package comp3350.teachreach.objects;

public interface ISession {

    public void acceptSession();

    public Student getStudent();

    public void setStudent(Student newstudent);

    public Tutor getTutor();

    public void setTutor(Tutor newtutor);

    public int getDay();

    public int getMonth();

    public int getYear();

    public void setDay(int newDay);

    public void setMonth(int newMonth);

    public void setYear(int newYear);

    public void setHour(int newHour);

    public boolean getStage();

    public void setStage(boolean decision);

    public String getLocation();

    public void setLocation(String location);

    public int getHour();
}
