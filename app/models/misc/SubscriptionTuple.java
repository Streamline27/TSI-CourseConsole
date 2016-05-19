package models.misc;

import models.Course;
import models.CourseSubscription;
import models.Student;

/**
 * Created by Vladislav on 5/19/2016.
 */
public class SubscriptionTuple {
    private CourseSubscription subscription;
    private Student student;
    private Course course;

    public SubscriptionTuple(CourseSubscription subscription, Student student, Course course) {
        this.subscription = subscription;
        this.student = student;
        this.course = course;
    }

    public CourseSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(CourseSubscription subscription) {
        this.subscription = subscription;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
