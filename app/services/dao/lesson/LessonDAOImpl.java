package services.dao.lesson;

import models.Lesson;
import models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import services.ModelRowMappers;

import java.util.List;

/**
 * Created by Vladislav on 5/17/2016.
 */

@Component
public class LessonDAOImpl implements LessonDAO{

    private @Autowired JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL   = "SELECT * FROM Lesson";
    private final String SQL_SELECT = "SELECT * FROM Student WHERE LessonId=?";
    private final String SQL_INSERT = "INSERT INTO Lesson(room, lessonTime, courseId) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Lesson SET room=?, lessonTime=?, courseId=?  WHERE lessonId=?";
    private final String SQL_DELETE = "DELETE FROM Lesson WHERE lessonId=?";

    private final String SQL_BY_COURSE = "SELECT * FROM Lesson WHERE courseId = ?";

    @Override
    public void create(Lesson entity) {
        jdbcTemplate.update(SQL_INSERT, entity.getRoom(), entity.getLessonTime(), entity.getCourseId());
        System.out.println("Created lesson. id = "+entity.getLessonId());
    }

    @Override
    public Lesson read(Long id)
    {
        System.out.println("Read lesson. id = " +id);
        return  (Lesson) jdbcTemplate.queryForObject(SQL_SELECT, new Object[]{ id }, ModelRowMappers.mapLesson);
    }

    @Override
    public List<Lesson> readAll() {
        System.out.println("Read all Lessons.");
        return jdbcTemplate.query(SQL_SELECT_ALL, ModelRowMappers.mapLesson);
    }

    @Override
    public void update(Lesson entity, Long id) {
        System.out.println("Updated lesson. id = "+id);
        jdbcTemplate.update(SQL_UPDATE, entity.getRoom(), entity.getLessonTime(), entity.getCourseId(), id);
    }

    @Override
    public void delete(Long id) {
        System.out.println("Deleted lesson. id = "+id);
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Lesson> getByCourseId(String id) {
        return jdbcTemplate.query(SQL_BY_COURSE, new Object[]{ id }, ModelRowMappers.mapLesson);
    }
}
