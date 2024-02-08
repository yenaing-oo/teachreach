package comp3350.teachreach.objects;

import java.util.ArrayList;

public class Tutor extends User {
  private final ArrayList<Course> tutoredCourses;
  private final ArrayList<String> preferredLocations;
  private final boolean[][] availability;
  private final double hourlyRate;
  private final int reviewSum;
  private final int reviewCount;

  public Tutor(String name, String pronouns, String major, String email, String password) {
    super(name, pronouns, major, email, password);
    this.tutoredCourses = new ArrayList<Course>();
    this.preferredLocations = new ArrayList<String>();
    this.availability = new boolean[7][24];
    this.hourlyRate = 10; // Arbitrary default
    this.reviewSum = 0;
    this.reviewCount = 0;
  }

  public Tutor(
      String name,
      String pronouns,
      String major,
      String email,
      String password,
      double hourlyRate) {
    super(name, pronouns, major, email, password);
    tutoredCourses = new ArrayList<Course>();
    this.preferredLocations = new ArrayList<String>();
    this.availability = new boolean[7][24];
    this.reviewSum = 0;
    this.reviewCount = 0;
    this.hourlyRate = hourlyRate;
  }

  public ArrayList<Course> getCourses() {
    return tutoredCourses;
  }

  public Tutor addCourse(Course course) {
    this.tutoredCourses.add(course);
    return this;
  }

  public double getHourlyRate() {
    return this.hourlyRate;
  }

  public float getRating() {
    return this.reviewCount > 0 ? ((float) this.reviewSum / (float) this.reviewCount) : 0;
  }

  public int getReviewCount() {
    return this.reviewCount;
  }
}
