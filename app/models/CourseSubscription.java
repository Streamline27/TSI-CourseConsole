package models;

/**
 * Created by Vladislav on 5/17/2016.
 */
public class CourseSubscription {

    private Long subscriptionId;
    private String studentId;
    private String courseId;
    private boolean isPayed;

    public CourseSubscription() {
    }

    public CourseSubscription(Long subscriptionId, String studentId, String courseId, boolean isPayed) {
        this.subscriptionId = subscriptionId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.isPayed = isPayed;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }
}
