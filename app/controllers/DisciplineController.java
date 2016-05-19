package controllers;

import controllers.utils.ActivePage;
import models.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.data.Form;
import play.mvc.Result;
import services.dao.discipline.DisciplineDAO;
import services.validation.DisciplineValidator;
import views.html.pages.disciplines;

import java.util.List;
import java.util.function.Consumer;


/**
 * Created by Vladislav on 5/17/2016.
 */

@Controller
public class DisciplineController {

    @Autowired private DisciplineDAO disciplineDAO;
    @Autowired private DisciplineValidator disciplineValidator;

    public Result disciplines(){
        return getDefaultDisiciplineResult();
    }

    public Result addDiscipline(){
        Discipline discipline = Form.form(Discipline.class).bindFromRequest().get();

        if (disciplineValidator.isValid(discipline) && !disciplineValidator.isPresent(discipline)){
            disciplineDAO.create(discipline);
            return getDefaultDisiciplineResult();
        }
        return getMessagedDisciplineResult("Could not create disciplne. Bad credentials.");
    }

    public Result updateDiscipline(){
        Discipline discipline = Form.form(Discipline.class).bindFromRequest().get();

        if (disciplineValidator.isValid(discipline) && !disciplineValidator.isPresent(discipline)){
            String oldTitle = Form.form().bindFromRequest().get("oldTitle");
            disciplineDAO.update(discipline, oldTitle);
            return getDefaultDisiciplineResult();
        }
        return getMessagedDisciplineResult("Could not update disciplne. Bad credentials.");

    }

    public Result deleteDiscipline(){
        Discipline discipline = Form.form(Discipline.class).bindFromRequest().get();
        disciplineDAO.delete(discipline.getTitle());
        return getDefaultDisiciplineResult();
    }

    private Result getDefaultDisiciplineResult(){
        return getMessagedDisciplineResult(null);
    }

    private Result getMessagedDisciplineResult(String message){
        List<Discipline> disciplineList = disciplineDAO.readAll();
        return play.mvc.Controller.ok(disciplines.render(disciplineList, message, ActivePage.Disciplines));
    }


}
