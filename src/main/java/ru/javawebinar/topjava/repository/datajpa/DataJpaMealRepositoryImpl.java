package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    private static final Sort DATE_SORT = new Sort("id");

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {

        //Можно так:
        //User user = new User();
        //user.setId(userId);
        //meal.setUser(user);

        meal.setUser(crudUserRepository.getOne(userId));

        if(!meal.isNew() && get(meal.getId(), userId) == null){
            return null;
        }
        else {
            return crudRepository.save(meal);
        }
    }


    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }


    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findOne(id);
        User user = meal.getUser();
        if(user.getId() == id){
            return meal;
        } else{
            return null;
        }
    }


    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll(DATE_SORT);
    }


    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }

}
