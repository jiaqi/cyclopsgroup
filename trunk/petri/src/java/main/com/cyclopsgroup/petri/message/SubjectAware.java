/*
 * Copyright (c) 1999-2004 Evavi, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Evavi, Inc.
 * Use is subject to license terms. License Agreement available at
 * <a href="http://www.evavi.com" target="_blank">www.evavi.com</a>
 */
package com.cyclopsgroup.petri.message;

/**
 * Message which knows which subject it's gonna to invoke
 *
 * @author <a href="mailto:jiaqi.guo@evavi.com">Jiaqi Guo</a>
 */
public interface SubjectAware
{

	/**
	 * Get subject object
	 *
	 * @return Subject object
	 */
	Object getSubject();
	/**
	 * Get id of the subject
	 *
	 * @return Id of subject
	 */
	String getSubjectId();
}