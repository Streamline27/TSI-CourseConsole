package controllers;

import controllers.utils.ActivePage;
import models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import play.data.Form;
import play.mvc.Result;
import services.dao.student.StudentDAO;
import services.validation.StudentValidatorImpl;
import views.html.pages.students;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Vladislav on 5/8/2016.
 */

@org.springframework.stereotype.Controller
public class StudentsController {

    @Autowired private StudentDAO studentDAO;
    @Autowired private StudentValidatorImpl studentValidator;

    public Result students(){
        return getDefaultStudentPageResult();
    }

    public Result deleteStudent(){
        Form<Student> form = Form.form(Student.class).bindFromRequest();
        studentDAO.delete(form.get().getStudentId());
        return getDefaultStudentPageResult();
    }

    public Result addStudent(){
        return executeIfValid(
                student -> studentDAO.create(student),
                "Could not create student. Bad credentials.");
    }

    public Result updateStudent(){
        return executeIfValid(student -> {
                String oldStudentId = Form.form().bindFromRequest().get("oldStudentId");
                studentDAO.update(student, oldStudentId);
        }, "Could not update student. Bad credentials.");
    }


    private Result executeIfValid(Consumer<Student> action, String msg){
        Student student = Form.form(Student.class).bindFromRequest().get();

        if (studentValidator.isValid(student)){

            action.accept(student);
            return getDefaultStudentPageResult();
        }
        else{
            return getMessagedStudentPageResult(msg);
        }
    }

    private Result getMessagedStudentPageResult(String msg) {
        List<Student> studentList = studentDAO.readAll();
        return play.mvc.Controller.ok(students.render(studentList, msg, ActivePage.Students));
    }

    private Result getDefaultStudentPageResult(){
        return getMessagedStudentPageResult(null);
    }

}
