package com.vlc.application.resources.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by prageethmahendra on 27/10/2016.
 */

public interface BranchRepository extends CrudRepository<Branch, Long> {
    @Query("select b from Branch b " +
            "where b.branchName = ?1")
    Branch findByBranchName(String branchName);
}
