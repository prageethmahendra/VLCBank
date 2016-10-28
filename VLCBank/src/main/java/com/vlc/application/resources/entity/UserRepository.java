package com.vlc.application.resources.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by prageethmahendra on 27/10/2016.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u " +
            "where u.userName = ?1 and u.password = ?2")
    List<User> findByUserPassword(String userName, String password);
}
