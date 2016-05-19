package models;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Vladislav on 5/17/2016.
 */
public class Lesson {
    private Long lessonId;
    private String room;
    private Timestamp lessonTime;
    private String courseId;

    public Lesson() {
    }

    public Lesson(Long lessonId, String room, Timestamp lessonTime, String courseId) {
        this.lessonId = lessonId;
        this.room = room;
        this.lessonTime = lessonTime;
        this.courseId = courseId;
    }

    public Lesson(String room, Timestamp lessonTime, String courseId) {
        this.room = room;
        this.lessonTime = lessonTime;
        this.courseId = courseId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Timestamp getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Timestamp lessonTime) {
        this.lessonTime = lessonTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
