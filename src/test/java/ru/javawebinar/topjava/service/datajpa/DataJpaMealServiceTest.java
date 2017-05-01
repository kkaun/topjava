package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.JPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

/**
 * Created by Kir on 30.04.2017.
 */
@ActiveProfiles({DATAJPA, POSTGRES_DB})
public class DataJpaMealServiceTest extends MealServiceTest {
}
