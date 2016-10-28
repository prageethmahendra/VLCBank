package com.vlc.application.resources.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by prageeth.g on 27/10/2016.
 */
@Entity
public class Branch {

    @Id
    @GeneratedValue
    private long id;
    private String branchName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Branch &&
                ((Branch) obj).getId() == this.id &&
                (((Branch) obj).getBranchName() == this.branchName ||
                        ((Branch) obj).getBranchName() != null && this.branchName != null &&
                                ((Branch) obj).getBranchName().equals(this.branchName));
    }
}
