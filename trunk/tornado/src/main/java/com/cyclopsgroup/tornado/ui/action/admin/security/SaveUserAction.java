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

import org.apache.commons.lang.StringUtils;

import com.cyclopsgroup.tornado.persist.PersistenceManager;
import com.cyclopsgroup.tornado.security.entity.User;
import com.cyclopsgroup.waterview.Action;
import com.cyclopsgroup.waterview.ActionContext;
import com.cyclopsgroup.waterview.BaseServiceable;
import com.cyclopsgroup.waterview.RuntimeData;
import com.cyclopsgroup.waterview.utils.TypeUtils;

/**
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 *
 * Action to save user profile
 */
public class SaveUserAction
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
        String newPassword = data.getParams().getString( "new_password" );
        if ( StringUtils.isNotEmpty( newPassword )
            && !StringUtils.equals( newPassword, data.getParams().getString( "confirmed_password" ) ) )
        {
            context.error( "confirmed_password", "Two passwords are not the same" );
            return;
        }

        PersistenceManager persist = (PersistenceManager) lookupComponent( PersistenceManager.ROLE );
        User user = (User) persist.load( User.class, data.getParams().getString( "user_id" ) );

        TypeUtils.getBeanUtils().copyProperties( user, data.getParams().toProperties() );
        if ( StringUtils.isNotEmpty( newPassword ) )
        {
            user.setPrivatePassword( newPassword );
        }
        persist.update( user );
        context.addMessage( "User " + user.getDisplayName() + " is changed" );
    }
}
