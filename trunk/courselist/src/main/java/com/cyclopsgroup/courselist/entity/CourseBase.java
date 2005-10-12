package com.cyclopsgroup.courselist.entity;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
abstract public class CourseBase implements Serializable {

    /** identifier field */
    private String id;

    /** persistent field */
    private String prefix;

    /** persistent field */
    private String courseCode;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String coRequisites;

    /** nullable persistent field */
    private boolean isDisabled;

    /** persistent field */
    private Set prerequisites;

    /** full constructor */
    public CourseBase(String prefix, String courseCode, String name, String description, String coRequisites, boolean isDisabled, Set prerequisites) {
        this.prefix = prefix;
        this.courseCode = courseCode;
        this.name = name;
        this.description = description;
        this.coRequisites = coRequisites;
        this.isDisabled = isDisabled;
        this.prerequisites = prerequisites;
    }

    /** default constructor */
    public CourseBase() {
    }

    /** minimal constructor */
    public CourseBase(String prefix, String courseCode, String name, Set prerequisites) {
        this.prefix = prefix;
        this.courseCode = courseCode;
        this.name = name;
        this.prerequisites = prerequisites;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public String getCoRequisites() {
        return this.coRequisites;
    }

    public void setCoRequisites(String coRequisites) {
        this.coRequisites = coRequisites;
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Set getPrerequisites() {
        return this.prerequisites;
    }

    public void setPrerequisites(Set prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
