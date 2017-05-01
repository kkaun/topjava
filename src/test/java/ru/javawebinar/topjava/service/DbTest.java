package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Kir on 29.04.2017.
 */
public abstract class DbTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private UserService userService;


    @Before
    public void setUp() throws Exception{

        userService.evictCache();
    }




}
