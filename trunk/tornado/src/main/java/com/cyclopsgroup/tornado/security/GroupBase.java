package com.cyclopsgroup.tornado.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
abstract public class GroupBase implements Serializable {

    /** identifier field */
    private String id;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private boolean isDisabled;

    /** full constructor */
    public GroupBase(String name, String description, boolean isDisabled) {
        this.name = name;
        this.description = description;
        this.isDisabled = isDisabled;
    }

    /** default constructor */
    public GroupBase() {
    }

    /** minimal constructor */
    public GroupBase(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
