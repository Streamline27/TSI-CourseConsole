package controllers;

import controllers.utils.ActivePage;
import models.Course;
import models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.mvc.Result;
import services.dao.course.CourseDAO;
import services.dao.student.StudentDAO;
import views.html.pages.groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Vladislav on 5/15/2016.
 */

@Controller
public class GroupController {

    @Autowired private CourseDAO courseDAO;
    @Autowired private StudentDAO studentDAO;

    public Result defaultResult(){
        List<Course> courseList = courseDAO.readAll();
        List<Student> studentList = getByCourseId(courseList);
        return play.mvc.Controller.ok(groups.render(studentList, courseList, ActivePage.Groups));
    }

    public Result getByCourseId(String id){
        List<Student> studentList = studentDAO.getByCourseId(id);
        List<Course> courseList = courseDAO.readAll();
        makeCourseWithIdFirstInList(id, courseList);
        return play.mvc.Controller.ok(groups.render(studentList, courseList, ActivePage.Groups));
    }

    private void makeCourseWithIdFirstInList(String id, List<Course> courseList) {
        int currentId = 0;
        while (!courseList.get(currentId).getCourseId().equals(id)) currentId++;
        Collections.swap(courseList, 0, currentId);
    }

    private List<Student> getByCourseId(List<Course> courseList) {
        if (courseList.size()>0) return studentDAO.getByCourseId(courseList.get(0).getCourseId());
        else return new ArrayList<>();
    }


}
