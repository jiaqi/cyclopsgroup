/*
 * Copyright (c) 1999-2004 Evavi, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Evavi, Inc.
 * Use is subject to license terms. License Agreement available at
 * <a href="http://www.evavi.com" target="_blank">www.evavi.com</a>
 */
package com.cyclopsgroup.petri.persistence.memory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.cyclopsgroup.petri.Case;

/**
 * Memory implementation of case object
 *
 * @author <a href="mailto:jiaqi.guo@evavi.com">Jiaqi Guo</a>
 */
public class MemoryCase implements Case
{

    private HashMap attributes = new HashMap();

    private String flowId;

    private String id;

    private HashSet states = new HashSet();

    /**
     * Constructor of class MemoryCase
     *
     * @param caseId
     * @param flowDefinitionId
     */
    public MemoryCase(String caseId, String flowDefinitionId)
    {
        id = caseId;
        flowId = flowDefinitionId;
    }

    /**
     * Copy all value from another case to this one
     *
     * @param anotherCase
     */
    public void copyFrom(Case anotherCase)
    {
        // NOTE: anotherCase usually is Wrapper of this case itself.
        // So anotherCase.getAttributes() returns the same reference of attributes property in this class
        // New attributes value must be saved temporarily before attributes.clear() is called
        HashMap newAttributes = new HashMap(anotherCase.getAttributes());
        attributes.clear();
        attributes.putAll(newAttributes);
        id = anotherCase.getId();
        flowId = anotherCase.getFlowId();
        //Same for states
        String[] newStates = anotherCase.getCurrentStates();
        states.clear();
        CollectionUtils.addAll(states, newStates);
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.Case#getAttributes()
     */
    public Map getAttributes()
    {
        return attributes;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.Case#getCurrentStates()
     */
    public String[] getCurrentStates()
    {
        return (String[]) states.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.Case#getFlowId()
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * Override or implement method of parent class or interface
     *
     * @see com.cyclopsgroup.petri.Case#getId()
     */
    public String getId()
    {
        return id;
    }
}