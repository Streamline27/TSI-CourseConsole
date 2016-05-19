package controllers;

import controllers.utils.ActivePage;
import models.Course;
import models.CourseSubscription;
import models.Student;
import models.misc.SubscriptionTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.data.Form;
import play.mvc.Result;
import services.dao.course.CourseDAO;
import services.dao.student.StudentDAO;
import services.dao.subscription.SubscriptionDAO;
import services.validation.SubscriptionValidator;
import views.html.pages.subscriptions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Vladislav on 5/15/2016.
 */

@Controller
public class SubscriptionController {

    @Autowired private SubscriptionDAO subscriptionDAO;
    @Autowired private StudentDAO studentDAO;
    @Autowired private CourseDAO courseDAO;
    @Autowired private SubscriptionValidator subscriptionValidator;

    public Result subscriptions(){
        return getDefaultSubscriptionPageResult();
    }

    public Result addSubscription(){
        CourseSubscription subscription = Form.form(CourseSubscription.class).bindFromRequest().get();

        if (subscriptionValidator.isValid(subscription) && !subscriptionValidator.isPresent(subscription)){
            subscriptionDAO.create(subscription);
            return getDefaultSubscriptionPageResult();
        }

        return getMessagedSubscriptionPageResult("Could not create subscription. Bad credentials.");
    }

    public Result updateSubscription(){
        CourseSubscription subscription = Form.form(CourseSubscription.class).bindFromRequest().get();

        if (subscriptionValidator.isValid(subscription)){
            CourseSubscription subscriptionToUpdate = subscriptionDAO.read(subscription.getSubscriptionId());
            subscriptionToUpdate.setPayed(subscription.isPayed());
            subscriptionDAO.update(subscriptionToUpdate, subscription.getSubscriptionId());
            return getDefaultSubscriptionPageResult();
        }

        return getMessagedSubscriptionPageResult("Could not update subscription. Bad credentials.");


    }

    public Result deleteSubscription(){
        CourseSubscription subscription = Form.form(CourseSubscription.class).bindFromRequest().get();
        subscriptionDAO.delete(subscription.getSubscriptionId());
        return getDefaultSubscriptionPageResult();
    }

    private Result getDefaultSubscriptionPageResult(){
        return getMessagedSubscriptionPageResult(null);
    }

    private Result getMessagedSubscriptionPageResult(String errorMessage){
        List<SubscriptionTuple> subscriptionList = getSubscriptionTupleList(subscriptionDAO.readAll());
        List<Student> studentList = studentDAO.readAll();
        List<Course> courseList = courseDAO.readAll();
        return play.mvc.Controller.ok(subscriptions.render(subscriptionList, studentList, courseList, errorMessage, ActivePage.Subscriptions));
    }

    private List<SubscriptionTuple> getSubscriptionTupleList(List<CourseSubscription> courseSubscriptions){
        List<SubscriptionTuple> subscriptionTuples = new ArrayList<>();
        for (CourseSubscription s : courseSubscriptions){
            Course subscribedCourse = courseDAO.read(s.getCourseId());
            Student subscribedStudent  = studentDAO.read(s.getStudentId());
            SubscriptionTuple tuple = new SubscriptionTuple(s, subscribedStudent, subscribedCourse);
            subscriptionTuples.add(tuple);
        }
        return subscriptionTuples;
    }


}
