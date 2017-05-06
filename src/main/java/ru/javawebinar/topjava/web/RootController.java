package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        request.setAttribute("allMeals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }


    @RequestMapping(value = "/createmeal", method = RequestMethod.GET)
    public ModelAndView create(){
        return new ModelAndView("newmeal", "command", new Meal());
    }


    @RequestMapping(value = "/savemeal", method = RequestMethod.POST)
    public ModelAndView createSave(@ModelAttribute("meal") Meal meal){
        int userId = AuthorizedUser.id();
        checkNew(meal);
        LOG.info("create {} for User {}", meal, userId);

        mealService.save(meal, userId);

        return new ModelAndView("redirect:/meals");
    }


    @RequestMapping(value = "/updatemeal/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("id") int id){
        int userId = AuthorizedUser.id();

        Meal meal = mealService.get(id, userId);

        LOG.info("update {} for User {}", meal, userId);
        mealService.update(meal, userId);

        return new ModelAndView("updatemeal", "command", meal);
    }


    @RequestMapping(value = "/updatesavemeal", method = RequestMethod.POST)
    public ModelAndView updateSave(@ModelAttribute("meal") Meal meal){

        int userId = AuthorizedUser.id();
        checkNew(meal);

        LOG.info("create {} for User {}", meal, userId);

        mealService.update(meal, userId);

        return new ModelAndView("redirect:/meals");
    }


    @RequestMapping(value = "/deletemeal/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") int id){
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);

        mealService.delete(id, userId);

        return new ModelAndView("redirect:/meals");
    }


    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public ModelAndView meals() throws IOException {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);

        List<MealWithExceed> list = MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay());

        return new ModelAndView("meals","list",list);
    }


    @RequestMapping(value = "/filtermeals", method = RequestMethod.POST)
    public ModelAndView getBetween(@RequestParam("startDate") LocalDate startDate,
                             @RequestParam("endDate") LocalDate endDate,
                             @RequestParam("startTime") LocalTime startTime,
                             @RequestParam("endTime") LocalTime endTime) {

        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);

        List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );

        return new ModelAndView("meals", "list", list);
    }




}
