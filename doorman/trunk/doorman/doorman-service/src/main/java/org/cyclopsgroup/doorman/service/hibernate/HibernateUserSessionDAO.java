package org.cyclopsgroup.doorman.service.hibernate;

import java.util.Date;

import org.cyclopsgroup.doorman.service.dao.UserSessionDAO;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.cyclopsgroup.doorman.service.storage.StoredUserSession;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate implementation of user session DAO
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
class HibernateUserSessionDAO
    extends HibernateDaoSupport
    implements UserSessionDAO
{
    /**
     * @param sessionFactory Hibernate session factory
     */
    HibernateUserSessionDAO( SessionFactory sessionFactory )
    {
        setSessionFactory( sessionFactory );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void createNew( StoredUserSession session )
    {
        session.setCreationDate( new DateTime().withZone( DateTimeZone.UTC ).toDate() );
        getHibernateTemplate().save( session );
    }

    /**
     * @inheritDoc
     */
    @Override
    public StoredUserSession findById( String sessionId )
    {
        return (StoredUserSession) getHibernateTemplate().get( StoredUserSession.class, sessionId );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateUser( String sessionId, StoredUser user )
    {
        StoredUserSession session =
            (StoredUserSession) getHibernateTemplate().load( StoredUserSession.class, sessionId );
        session.setUser( user );
        Date now = new DateTime().withZone( DateTimeZone.UTC ).toDate();
        session.setLastModified( now );
        if ( user != null )
        {
            session.setLastVerification( now );
        }
        getHibernateTemplate().update( session );
    }
}
