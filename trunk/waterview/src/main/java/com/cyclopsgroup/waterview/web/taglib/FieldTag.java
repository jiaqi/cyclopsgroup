/*
 * Copyright (c) 1999-2004 Evavi, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Evavi, Inc.
 * Use is subject to license terms. License Agreement available at
 * <a href="http://www.evavi.com" target="_blank">www.evavi.com</a>
 */
package com.cyclopsgroup.waterview.web.taglib;

import java.util.Date;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.lang.StringUtils;

import com.cyclopsgroup.waterview.jelly.JellyEngine;
import com.cyclopsgroup.waterview.spi.taglib.TagSupport;
import com.cyclopsgroup.waterview.utils.TypeUtils;
import com.cyclopsgroup.waterview.web.Field;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 * 
 * Field tag
 */
public class FieldTag extends TagSupport
{
    private Script bodyScript;

    private Field field;

    private Class fieldType = String.class;

    private Script headerScript;

    private String name;

    private boolean password;

    private boolean required;

    private String title;

    private String type = "string";

    private String value = StringUtils.EMPTY;

    /**
     * Overwrite or implement method processTag()
     *
     * @see com.cyclopsgroup.waterview.utils.TagSupportBase#processTag(org.apache.commons.jelly.XMLOutput)
     */
    protected void processTag(XMLOutput output) throws Exception
    {
        requireAttribute("name");
        FormTag formTag = (FormTag) findAncestorWithClass(FormTag.class);
        if (formTag == null)
        {
            throw new JellyTagException("Field must be inside a Form");
        }
        formTag.addFieldTag(this);
        if (formTag.isFormNew())
        {
            field = new Field(getName(), TypeUtils.getType(getType()));
            field.setRequired(isRequired());
            field.setValue((String) getValue());
            formTag.getForm().addField(field);
        }
        else
        {
            field = formTag.getForm().getField(getName());
        }

        invokeBody(output);

        if (getBodyScript() == null)
        {
            JellyEngine je = (JellyEngine) getServiceManager().lookup(
                    JellyEngine.ROLE);
            setBodyScript(je.getScript("fabric", "/widget/FormField.jelly"));
        }

        JellyContext jc = new JellyContext(context);
        jc.setVariable("field", field);
        jc.setVariable("fieldTag", this);
        jc.setVariable("form", formTag.getForm());
        jc.setVariable("formTag", formTag);
        getBodyScript().run(jc, output);
    }

    /**
     * @return Returns the bodyScript.
     */
    public Script getBodyScript()
    {
        return bodyScript;
    }

    /**
     * @return Returns the field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * @return Returns the headerScript.
     */
    public Script getHeaderScript()
    {
        return headerScript;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return Returns the type.
     */
    public String getType()
    {
        return type;
    }

    /**
     * @return Returns the value.
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Is field boolean
     *
     * @return True if field is boolean
     */
    public boolean isBooleanType()
    {
        return fieldType == Boolean.class;
    }

    /**
     * If the field is date
     *
     * @return True if type is date
     */
    public boolean isDateType()
    {
        return Date.class.isAssignableFrom(fieldType);
    }

    /**
     * If type is number
     *
     * @return True if type is number
     */
    public boolean isNumberType()
    {
        return Number.class.isAssignableFrom(fieldType);
    }

    /**
     * @return Returns the password.
     */
    public boolean isPassword()
    {
        return password;
    }

    /**
     * @return Returns the required.
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * If the type is text
     *
     * @return True if type is text
     */
    public boolean isTextType()
    {
        return fieldType == String.class;
    }

    /**
     * @param bodyScript The bodyScript to set.
     */
    public void setBodyScript(Script bodyScript)
    {
        this.bodyScript = bodyScript;
    }

    /**
     * @param field The field to set.
     */
    public void setField(Field field)
    {
        this.field = field;
    }

    /**
     * @param headerScript The headerScript to set.
     */
    public void setHeaderScript(Script headerScript)
    {
        this.headerScript = headerScript;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param password The password to set.
     */
    public void setPassword(boolean password)
    {
        this.password = password;
    }

    /**
     * @param required The required to set.
     */
    public void setRequired(boolean required)
    {
        this.required = required;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @param type The type to set.
     * 
     * @throws ClassNotFoundException 
     */
    public void setType(String type) throws ClassNotFoundException
    {
        this.type = type;
        this.fieldType = TypeUtils.getNonePrimitiveType(type);
        if (fieldType == null)
        {
            throw new ClassNotFoundException(type);
        }
    }

    /**
     * @param value The value to set.
     */
    public void setValue(Object value)
    {
        this.value = TypeUtils.toString(value);
    }
}
