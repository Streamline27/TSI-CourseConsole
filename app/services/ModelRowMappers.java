package services;

import models.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by Vladislav on 5/7/2016.
 */
public class ModelRowMappers {
    public static RowMapper mapStudent = (rs, i) ->
            new Student(rs.getString("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"));

    public static RowMapper mapCourse = (rs, i) ->
            new Course(rs.getString("courseId"),
                       rs.getBoolean("isFinished"),
                       rs.getInt("lessonNumber"),
                       rs.getBigDecimal("price"),
                       rs.getString("discipline"));

    public static RowMapper mapDiscipline = (rs, i) ->
            new Discipline(rs.getString("title"));

    public static RowMapper mapSubscription = (rs, i) ->
            new CourseSubscription(rs.getLong("subscriptionId"),
                                   rs.getString("studentId"),
                                   rs.getString("courseId"),
                                   rs.getBoolean("isPayed"));

    public static RowMapper mapLesson = (rs, i) ->
                new Lesson(rs.getLong("lessonId"),
                           rs.getString("room"),
                           rs.getTimestamp("lessonTime"),
                           rs.getString("courseId"));

}
