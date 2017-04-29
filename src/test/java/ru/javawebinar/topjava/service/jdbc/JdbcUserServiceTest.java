package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

/**
 * Created by Kir on 30.04.2017.
 */

@ActiveProfiles({JDBC, POSTGRES_DB})
public class JdbcUserServiceTest extends MealServiceTest {

}
