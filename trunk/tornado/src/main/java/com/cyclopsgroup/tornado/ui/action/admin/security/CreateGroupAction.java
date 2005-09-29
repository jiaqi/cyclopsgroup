/* ==========================================================================
 * Copyright 2002-2005 Cyclops Group Community
 * 
 * Licensed under the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE
 * (CDDL) Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/cddl1.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * =========================================================================
 */
package com.cyclopsgroup.tornado.ui.action.admin.security;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.cyclopsgroup.tornado.hibernate.HibernateService;
import com.cyclopsgroup.tornado.security.entity.Group;
import com.cyclopsgroup.waterview.Action;
import com.cyclopsgroup.waterview.ActionContext;
import com.cyclopsgroup.waterview.BaseServiceable;
import com.cyclopsgroup.waterview.RuntimeData;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 *
 * Action to create group
 */
public class CreateGroupAction
    extends BaseServiceable
    implements Action
{

    /**
     * Overwrite or implement method execute()
     *
     * @see com.cyclopsgroup.waterview.Action#execute(com.cyclopsgroup.waterview.RuntimeData, com.cyclopsgroup.waterview.ActionContext)
     */
    public void execute( RuntimeData data, ActionContext context )
        throws Exception
    {
        HibernateService hib = (HibernateService) lookupComponent( HibernateService.ROLE );
        Session s = hib.getSession();

        String groupName = data.getParams().getString( "name" );
        List existing = s.createCriteria( Group.class ).add( Expression.eq( "name", groupName ) )
            .add( Expression.eq( "isDisabled", Boolean.FALSE ) ).list();
        if ( !existing.isEmpty() )
        {
            context.error( "name", "Group " + groupName + " alread exists, try another name" );
            return;
        }

        Group group = new Group();
        group.setName( groupName );
        group.setDescription( data.getParams().getString( "description" ) );
        s.save( group );
        context.addMessage( "Group " + groupName + " is created" );
    }
}
