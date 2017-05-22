package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kir on 21.05.2017.
 */
@RestController
@RequestMapping("/ajax/meals")
public class MealAjaxController extends AbstractMealController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("dateTime") LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") String calories) {

        Meal meal = new Meal(id, dateTime, description, Integer.parseInt(calories));
        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }


}
