package com.vlc.application.resources;

import com.vlc.application.resources.entity.Branch;

/**
 * Created by prageeth.g on 27/10/2016.
 */
public class EntityContext {
    private String entityType;
    private Branch entity;
    private SecurityContext securityContext;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Branch getEntity() {
        return entity;
    }

    public void setEntity(Branch entity) {
        this.entity = entity;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
