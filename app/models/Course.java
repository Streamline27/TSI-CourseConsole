package models;

import java.math.BigDecimal;

/**
 * Created by Vladislav on 5/8/2016.
 */
public class Course {
    private String courseId;
    private Boolean isFinished;
    private Integer lessonNumber;
    private BigDecimal price;
    private String disciplineTitle;

    public Course(String courseId, Boolean isFinished, Integer lessonNumber, BigDecimal price, String disciplineTitle) {
        this.courseId = courseId;
        this.isFinished = isFinished;
        this.lessonNumber = lessonNumber;
        this.price = price;
        this.disciplineTitle = disciplineTitle;
    }

    public Course() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Integer lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDisciplineTitle() {
        return disciplineTitle;
    }

    public void setDisciplineTitle(String disciplineTitle) {
        this.disciplineTitle = disciplineTitle;
    }
}
