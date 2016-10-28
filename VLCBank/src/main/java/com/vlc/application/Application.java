package com.vlc.application;

import com.vlc.application.controllers.CreateEntityController;
import com.vlc.application.resources.entity.BranchRepository;
import com.vlc.application.resources.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class Application {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BranchRepository branchRepository;
    @Autowired
    CreateEntityController createEntityController;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
