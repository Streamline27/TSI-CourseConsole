package services.validation;

import models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.dao.student.StudentDAO;

/**
 * Created by Vladislav on 5/18/2016.
 */
@Component
public class StudentValidatorImpl implements StudentValidator{

    private StudentDAO studentDAO;

    @Autowired
    public StudentValidatorImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean isValid(Student entity) {
        if(entity.getFirstName() ==null || entity.getFirstName().equals("") ) return false;
        if(entity.getLastName() == null || entity.getLastName().equals("")  ) return false;
        if(entity.getStudentId() == null) return false;
        if(!entity.getStudentId().matches("[0-9]{5}-[0-9]{6}")) return false;
        if(studentDAO.read(entity.getStudentId())!=null) return false;
        return true;
    }
}
