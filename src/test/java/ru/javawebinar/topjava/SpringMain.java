package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {

    private static final Logger LOG = LoggerFactory.getLogger(SpringMain.class);

    public static void main(String[] args) {
        // java 7 Automatic resource management
        //try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/mock.xml")) {

        try(GenericXmlApplicationContext ctx = new GenericXmlApplicationContext()){

            ctx.getEnvironment().setActiveProfiles(Profiles.POSTGRES_DB);
            ctx.load("spring/spring-app.xml", "spring/mock.xml");
            ctx.refresh();

            LOG.info("\n" + Arrays.toString(ctx.getBeanDefinitionNames()));
            MealRestController adminController = ctx.getBean(MealRestController.class);
            adminController.delete(7);


//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
//            System.out.println();
//
//            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealWithExceed> filteredMealsWithExceeded =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
