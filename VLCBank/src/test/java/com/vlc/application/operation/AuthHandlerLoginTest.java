package com.vlc.application.operation;

import com.vlc.application.resources.entity.User;
import com.vlc.application.resources.entity.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by prageeth.g on 27/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthHandlerLoginTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    AuthHandler authHandler;

    @Autowired
    UserRepository userRepository;

    @Test
    public void whenUsernameOrPasswordNotSpecifiedThenReturnNull() {
        AuthHandler authHandler = new AuthHandler();
        Assert.assertNull(authHandler.login(null, null));
    }

    @Test
    public void whenNoRepositoryAccessReturnNull() {
        AuthHandler authHandler = new AuthHandler();
        Assert.assertNull(authHandler.login("John", "Cena"));
    }

    @Test
    public void whenUsernameOrPasswordNotValidThenReturnNull() {
        User user = new User("John", "Cena");
        userRepository.save(user);
        Assert.assertNull(authHandler.login("John", "Cena1"));
        Assert.assertNull(authHandler.login("John1", "Cena"));
        Assert.assertNotNull(authHandler.login("John", "Cena"));
        userRepository.delete(user);
    }

    @Test
    public void whenUsernamePasswordCorrectThenAuthorize() {
        User user = new User("John", "Cena", false);
        userRepository.save(user);
        Assert.assertNotNull(authHandler.login("John", "Cena"));
        Assert.assertFalse(authHandler.login("John", "Cena").isAuthorized());
        user.setAuthorized(true);
        userRepository.save(user);
        Assert.assertTrue(authHandler.login("John", "Cena").isAuthorized());
    }

    @Test
    public void whenAuthHandlerCreatedThenCreateDummyData() {
        Assert.assertTrue(userRepository.findAll().iterator().hasNext());
        for (User user : userRepository.findAll()) {
            userRepository.delete(user);
        }
        Assert.assertFalse(userRepository.findAll().iterator().hasNext());
        authHandler.init();
        Assert.assertTrue(userRepository.findAll().iterator().hasNext());
    }
}
