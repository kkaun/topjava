package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

/**
 * Created by Kir on 30.04.2017.
 */
@ActiveProfiles({JPA, POSTGRES_DB})
public class DataJpaUserServiceTest extends UserServiceTest {
}
