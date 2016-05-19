package services.validation;

import models.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.dao.discipline.DisciplineDAO;

/**
 * Created by Vladislav on 5/19/2016.
 */

@Component
public class DisciplineValidatorImpl implements DisciplineValidator{

    @Autowired private DisciplineDAO disciplineDAO;

    @Override
    public boolean isValid(Discipline entity) {
        if (entity == null) return false;
        if (entity.getTitle()==null || entity.getTitle().equals("")) return false;
        return true;
    }

    @Override
    public boolean isPresent(Discipline entity) {
        return disciplineDAO.read(entity.getTitle())!=null;
    }
}
