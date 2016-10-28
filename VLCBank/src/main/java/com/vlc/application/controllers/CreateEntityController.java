package com.vlc.application.controllers;

import com.vlc.application.resources.EntityContext;
import com.vlc.application.operation.AuthHandler;
import com.vlc.application.resources.SecurityContext;
import com.vlc.application.resources.entity.Branch;
import com.vlc.application.resources.entity.BranchRepository;
import com.vlc.application.resources.entity.User;
import com.vlc.application.resources.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CreateEntityController {

    @Autowired
    BranchRepository branchRepository;


    @Autowired
    AuthHandler authHandler;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/createBranch")
    public @ResponseBody
    EntityContext addBranch(@RequestBody EntityContext entityContext) {
        if (entityContext == null ||
                entityContext.getEntity() == null ||
                entityContext.getEntityType() == null ||
                entityContext.getSecurityContext() == null ||
                entityContext.getSecurityContext().getUser() == null) {
            return null;
        }
        else {
            User user = entityContext.getSecurityContext().getUser();
            User authorizedUser = authHandler.login(user.getUserName(), user.getPassword());
            System.out.println("authorizedUser = " + user.getPassword());
            if (authorizedUser == null || !authorizedUser.isAuthorized()) {
                user.setAuthorized(false);
            } else {
                entityContext.getSecurityContext().setUser(authorizedUser);
                branchRepository.save( entityContext.getEntity());
                Branch branchList = branchRepository.findByBranchName(entityContext.getEntity().getBranchName());
                if (branchList != null) {
                    entityContext.setEntity(branchList);
                }
            }
        }
        return entityContext;
    }
}
