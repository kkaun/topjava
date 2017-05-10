package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("meals")
public class MealsRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealsRestController.class);

    @Autowired
    private final MealService mealService;

    @Autowired
    public MealsRestController(MealService service) {
        this.mealService = service;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listMeals() throws IOException {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        ModelAndView modelAndView = new ModelAndView("meals");
        List<MealWithExceed> allMeals = MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay());
        modelAndView.addObject("allMeals", allMeals);
        return modelAndView;
    }

    @RequestMapping(value = "/filteredlist", method = RequestMethod.POST)
    public ModelAndView listFilteredMeals(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) throws IOException {

        int userId = AuthorizedUser.id();
        LOG.info("getAll FILTERED for User {}", userId);

        ModelAndView modelAndView = new ModelAndView("meals");

        modelAndView.addObject("filteredMeals", MealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(
                        startDate != null ? LocalDate.parse(startDate) : DateTimeUtil.MIN_DATE,
                        endDate != null ? LocalDate.parse(endDate) : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? LocalTime.parse(startTime) : LocalTime.MIN,
                endTime != null ? LocalTime.parse(endTime) : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        ));
        return modelAndView;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addMealPage(@ModelAttribute Meal meal){
        int userId = AuthorizedUser.id();
        LOG.info("createPage {} for User {}", meal, userId);
        ModelAndView modelAndView = new ModelAndView("newmeal");
        modelAndView.addObject("meal", new Meal());
        return modelAndView;
    }


    @RequestMapping(value = "/add/process", method = RequestMethod.POST)
    public ModelAndView addingMeal(@ModelAttribute Meal meal) throws Exception {
        int userId = AuthorizedUser.id();
        checkNew(meal);
        LOG.info("create {} for User {}", meal, userId);
        ModelAndView modelAndView = new ModelAndView("redirect:/meals/list");
        mealService.save(meal, userId);
        return modelAndView;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editMealPage(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView("editmeal");
        int userId = AuthorizedUser.id();
        Meal meal = mealService.get(id, userId);
        LOG.info("updatePage {} for User {}", meal, userId);
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editingMeal(@ModelAttribute("meal") Meal meal, @PathVariable("id") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("redirect:/meals/list");
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", meal, userId);
        mealService.update(meal, userId);
        return modelAndView;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") int id){
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        mealService.delete(id, userId);
        return new ModelAndView("redirect:/meals/list");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(){
        return new ModelAndView("redirect:/");
    }

}