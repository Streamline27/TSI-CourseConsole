package services.dao.student;

import models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import services.ModelRowMappers;

import java.util.List;

/**
 * Created by Vladislav on 5/7/2016.
 */
@Component
public class StudentDAOImpl implements StudentDAO {

    private @Autowired JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL   = "SELECT * FROM Student";
    private final String SQL_SELECT = "SELECT * FROM Student WHERE studentId=?";
    private final String SQL_INSERT = "INSERT INTO Student(studentId, firstName, lastName) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Student SET firstName=?, lastName=?, studentId=?  WHERE studentId=?";
    private final String SQL_DELETE = "DELETE FROM Student WHERE studentId=?";

        private final String SQL_BY_COURSE = "SELECT * FROM Student\n" +
                                             "JOIN courseSubscription ON student.studentId = courseSubscription.studentId" +
                                             " WHERE courseId = ?";


    @Override
    public void create(Student entity) {
        jdbcTemplate.update(SQL_INSERT, entity.getStudentId(), entity.getFirstName(), entity.getLastName());
        System.out.println("Created student. id = "+entity.getStudentId());
    }


    @Override
    public Student read(String id) {
        System.out.println("Read student. id = " +id);
        try {
            return  (Student) jdbcTemplate.queryForObject(SQL_SELECT, new Object[]{ id }, ModelRowMappers.mapStudent);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    @Override
    public List<Student> readAll() {
        System.out.println("Read all students.");
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, ModelRowMappers.mapStudent);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    @Override
    public void update(Student entity, String id) {
        System.out.println("Updated student. id = "+id);
        jdbcTemplate.update(SQL_UPDATE, entity.getFirstName(), entity.getLastName(), entity.getStudentId(), id);
    }


    @Override
    public void delete(String id) {
        System.out.println("Deleted student. id = "+id);
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Student> getByCourseId(String id) {
        System.out.println("Read students by id: "+id);
        return jdbcTemplate.query(SQL_BY_COURSE, new Object[]{ id }, ModelRowMappers.mapStudent);
    }
}
