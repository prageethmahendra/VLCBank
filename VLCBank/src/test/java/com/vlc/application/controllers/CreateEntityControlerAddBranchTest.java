package com.vlc.application.controllers;

import com.vlc.application.operation.AuthHandler;
import com.vlc.application.resources.EntityContext;
import com.vlc.application.resources.SecurityContext;
import com.vlc.application.resources.entity.Branch;
import com.vlc.application.resources.entity.BranchRepository;
import com.vlc.application.resources.entity.User;
import com.vlc.application.resources.entity.UserRepository;
import javafx.beans.binding.When;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.verification.AtLeast;
import org.mockito.internal.verification.AtMost;
import org.mockito.internal.verification.NoMoreInteractions;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* Created by prageeth.g on 27/10/2016.
*/
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateEntityControlerAddBranchTest {
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.mock(UserRepository.class);
    }

    @InjectMocks
    CreateEntityController createEntityController = new CreateEntityController();

    @Mock
    BranchRepository branchRepository;

    @Mock
    AuthHandler authHandler;

    @Test
    public void whenRequestNullThenReturnNull() {
        Assert.assertNull(createEntityController.addBranch(null));
    }

    @Test
    public void whenRequestWithoutSecurityContextThenReturnNull() {
        EntityContext entityContext = new EntityContext();
        Assert.assertNull(createEntityController.addBranch(entityContext));
    }


    @Test
    public void whenRequestWithoutUserThenReturnNull() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();
        entityContext.setSecurityContext(securityContext);
        Assert.assertNull(createEntityController.addBranch(entityContext));
    }

    @Test
    public void whenRequestWithoutEntityThenReturnNull() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();
        securityContext.setUser(new User());
        entityContext.setSecurityContext(securityContext);
        Assert.assertNull(createEntityController.addBranch(entityContext));
    }

    @Test
    public void whenRequestWithoutEntityTypeThenReturnNull() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();
        securityContext.setUser(new User());
        entityContext.setEntity(new Branch());
        entityContext.setSecurityContext(securityContext);
        Assert.assertNull(createEntityController.addBranch(entityContext));
    }

    @Test
    public void whenRequestCorrectlyThenReturnEntityContextWithSameEntityType() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();
        securityContext.setUser(new User());
        entityContext.setEntity(new Branch());
        entityContext.setEntityType("Branch");
        entityContext.setSecurityContext(securityContext);
        Assert.assertNotNull(createEntityController.addBranch(entityContext));
        Assert.assertEquals(createEntityController.addBranch(entityContext).getClass(), EntityContext.class);
        EntityContext returnedEntityContext = createEntityController.addBranch(entityContext);
        Assert.assertEquals(returnedEntityContext.getEntity(), entityContext.getEntity());
    }

    @Test
    public void whenRequestedThenAuthenticateUser() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();

        User user = new User("John", "Cena");
        securityContext.setUser(user);
        entityContext.setSecurityContext(securityContext);

        entityContext.setEntity(new Branch());
        entityContext.setEntityType("Branch");
        Assert.assertNotNull(createEntityController.addBranch(entityContext));
        Assert.assertEquals(createEntityController.addBranch(entityContext).getClass(), EntityContext.class);
        Mockito.verify(authHandler, new AtLeast(1)).login("John", "Cena");
    }

    @Test
    public void whenNotAuthorizedThenDontSave() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();

        User user = new User("john", "Cena", false);
        securityContext.setUser(user);
        entityContext.setSecurityContext(securityContext);

        Branch branch = new Branch();
        entityContext.setEntity(branch);
        entityContext.setEntityType("Branch");
        Mockito.doReturn(user).when(authHandler).login(user.getUserName(), user.getPassword());
        EntityContext returnedEntityContext = createEntityController.addBranch(entityContext);
        Mockito.verify(branchRepository, new NoMoreInteractions()).save(branch);
    }

    @Test
    public void whenNotAuthorizedThenReturnWithUnauthorizedUser() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();

        User user = new User("John", "Cena", false);
        securityContext.setUser(user);
        entityContext.setSecurityContext(securityContext);

        Branch branch = new Branch();
        entityContext.setEntity(branch);
        entityContext.setEntityType("Branch");
        Mockito.doReturn(user).when(authHandler).login(user.getUserName(), user.getPassword());
        EntityContext returnedEntityContext = createEntityController.addBranch(entityContext);
        Assert.assertFalse(returnedEntityContext.getSecurityContext().getUser().isAuthorized());
    }

    @Test
    public void whenAuthorizedThenSave() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();

        User user = new User("Prageeth", "Prageeth321", true);
        securityContext.setUser(user);
        entityContext.setSecurityContext(securityContext);

        Branch branch = new Branch();
        entityContext.setEntity(branch);
        entityContext.setEntityType("Branch");
        Mockito.doReturn(user).when(authHandler).login(user.getUserName(), user.getPassword());

        EntityContext returnedEntityContext = createEntityController.addBranch(entityContext);
        Mockito.verify(branchRepository, new AtLeast(1)).save(branch);
    }

    @Test
    public void whenSavedThenReturnWithSavedEntity() {
        EntityContext entityContext = new EntityContext();
        SecurityContext securityContext = new SecurityContext();

        User user = new User("Prageeth", "Prageeth321", true);
        securityContext.setUser(user);
        entityContext.setSecurityContext(securityContext);

        Branch branch = new Branch();
        branch.setBranchName("A1");
        entityContext.setEntity(branch);
        entityContext.setEntityType("Branch");
        Mockito.doReturn(user).when(authHandler).login(user.getUserName(), user.getPassword());
        Branch savedBranch = new Branch();
        savedBranch.setBranchName("B1");
        Mockito.doReturn(savedBranch).when(branchRepository).findByBranchName(branch.getBranchName());
        EntityContext returnedEntityContext = createEntityController.addBranch(entityContext);
        Assert.assertEquals(savedBranch, returnedEntityContext.getEntity());
        Assert.assertNotEquals(savedBranch, branch);
    }
}
