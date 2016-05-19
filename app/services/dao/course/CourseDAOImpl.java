package services.dao.course;

import models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import services.ModelRowMappers;

import java.util.List;

/**
 * Created by Vladislav on 5/8/2016.
 */
@Component
public class CourseDAOImpl implements CourseDAO{

    private @Autowired JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL   = "SELECT * FROM Course";
    private final String SQL_SELECT = "SELECT * FROM Course WHERE courseId=?";
    private final String SQL_INSERT = "INSERT INTO Course(courseId, isFinished, lessonNumber, price, discipline)" +
                                      " VALUES (?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Course SET isFinished=?, lessonNumber=?, price=?, discipline=?, courseId=? WHERE courseId=?";
    private final String SQL_DELETE = "DELETE FROM Course WHERE courseId=?";



    @Override
    public void create(Course entity) {
        jdbcTemplate.update(SQL_INSERT,
                entity.getCourseId(),
                entity.getFinished(),
                entity.getLessonNumber(),
                entity.getPrice(),
                entity.getDisciplineTitle());
        System.out.println("Created course. id = "+entity.getCourseId());
    }

    @Override
    public Course read(String id) {
        System.out.println("Read course. id = " +id);
        return  (Course) jdbcTemplate.queryForObject(SQL_SELECT, new Object[]{ id }, ModelRowMappers.mapCourse);
    }

    @Override
    public List<Course> readAll() {
        System.out.println("Read all courses.");
        return jdbcTemplate.query(SQL_SELECT_ALL, ModelRowMappers.mapCourse);
    }

    @Override
    public void update(Course entity, String id) {
        System.out.println("Updated course. id = "+entity.getCourseId());
        jdbcTemplate.update(SQL_UPDATE,
                entity.getFinished(),
                entity.getLessonNumber(),
                entity.getPrice(),
                entity.getDisciplineTitle(),
                entity.getCourseId(),
                id);
    }

    @Override
    public void delete(String id) {
        System.out.println("Deleted course. id = "+id);
        jdbcTemplate.update(SQL_DELETE, id);
    }


}
