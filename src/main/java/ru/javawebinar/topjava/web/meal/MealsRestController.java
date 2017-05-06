package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

//@RestController
//@RequestMapping("meals")
public class MealsRestController {
//    private static final Logger LOG = LoggerFactory.getLogger(MealsRestController.class);
//
//    @Autowired
//    private final MealService service;
//
//    @Autowired
//    public MealsRestController(MealService service) {
//        this.service = service;
//    }

//    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
//    public String get(@PathVariable("id") int id, HttpServletRequest request)
//            throws ServletException, IOException {
//
//        int userId = AuthorizedUser.id();
//        LOG.info("get meal {} for User {}", id, userId);
//
//        request.setAttribute("meal", service.get(id, userId));
//
//        return "redirect:/meal";
//    }



//    private int getId(HttpServletRequest request) {
//        String paramId = Objects.requireNonNull(request.getParameter("id"));
//        return Integer.valueOf(paramId);
//    }
}