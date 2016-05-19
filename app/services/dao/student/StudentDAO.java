package services.dao.student;

import models.Student;
import services.dao.CRUDOperationDAO;

import java.util.List;

/**
 * Created by Vladislav on 5/7/2016.
 */
public interface StudentDAO extends CRUDOperationDAO<Student, String> {
    List<Student> getByCourseId(String id);
}
