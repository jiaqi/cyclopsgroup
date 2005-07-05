/* ==========================================================================
 * Copyright 2002-2005 Cyclops Group Community
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
package com.cyclopsgroup.tornado.components.form;

import com.cyclopsgroup.tornado.core.form.Form;
import com.cyclopsgroup.tornado.core.form.FormDefinition;
import com.cyclopsgroup.waterview.PageRuntime;

public interface FormManager
{
    String ROLE = FormManager.class.getName();

    Form getForm(FormDefinition defintion, String formId, PageRuntime runtime);

    FormDefinition getFormDefinition(String formName);
}
