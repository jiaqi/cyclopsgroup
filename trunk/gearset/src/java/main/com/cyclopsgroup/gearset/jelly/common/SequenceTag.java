/* ==========================================================================
 * Copyright 2002-2004 Cyclops Group Community
 * 
 * Licensed under the Open Software License, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://opensource.org/licenses/osl-2.1.php
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * =========================================================================
 */
package com.cyclopsgroup.gearset.jelly.common;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;

import com.cyclopsgroup.gearset.jelly.ActionAcceptable;
import com.cyclopsgroup.gearset.jelly.SyntaxUtils;
import com.cyclopsgroup.gearset.runtime.Action;
import com.cyclopsgroup.gearset.runtime.Context;

/**
 * Sequence of executables
 * 
 * @author <a href="mailto:jiiaqi@yahoo.com">Jiaqi Guo </a>
 */
public class SequenceTag extends TagSupport implements ActionAcceptable, Action
{

    private LinkedList actions = new LinkedList();

    /**
     * Override method acceptAction in super class of SequenceTag
     * 
     * @see com.cyclopsgroup.gearset.jelly.ActionAcceptable#acceptAction(com.cyclopsgroup.gearset.runtime.Action)
     */
    public void acceptAction(Action action)
    {
        actions.add(action);
    }

    /**
     * Override method doTag in super class of SequenceTag
     * 
     * @see org.apache.commons.jelly.Tag#doTag(org.apache.commons.jelly.XMLOutput)
     */
    public void doTag(XMLOutput output) throws MissingAttributeException,
            JellyTagException
    {
        invokeBody(output);
        SyntaxUtils.checkParent(this, ActionAcceptable.class);
    }

    /**
     * Override method execute in super class of SequenceTag
     * 
     * @see com.cyclopsgroup.gearset.runtime.Action#execute(com.cyclopsgroup.gearset.runtime.Context)
     */
    public Object execute(Context ctx) throws Exception
    {
        Object ret = null;
        for (Iterator i = actions.iterator(); i.hasNext();)
        {
            Action executable = (Action) i.next();
            Object result = executable.execute(ctx);
            if (result != null)
            {
                ret = result;
            }
        }
        return ret;
    }

}