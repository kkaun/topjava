package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@RestController
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String get(@PathVariable("id") int id, HttpServletRequest request)
            throws ServletException, IOException {

        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);

        Meal meal = service.get(id, userId);

        request.setAttribute("meal", meal);
        return "redirect:/meal";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);

        return "redirect:/meals";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);

        request.setAttribute("allMeals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));

        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(HttpServletRequest request) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");

        final Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

        int userId = AuthorizedUser.id();
        checkNew(meal);

        LOG.info("create {} for User {}", meal, userId);

        if(service.save(meal, userId) != null) {
            request.setAttribute("created", "created");
        }

        return "redirect:/meals";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(Meal meal, @PathVariable("id") int id, HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        checkIdConsistent(meal, id);
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);

        request.setAttribute("updated", "updated");

        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/filter", method = RequestMethod.GET)
    public String getBetween(HttpServletRequest request, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);

        request.setAttribute("filteredMeals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        ));

        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}