package org.cyclopsgroup.doorman.service.core;

import java.util.Arrays;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.cyclopsgroup.caff.util.UUIDUtils;
import org.cyclopsgroup.doorman.api.SessionService;
import org.cyclopsgroup.doorman.api.User;
import org.cyclopsgroup.doorman.api.UserOperationResult;
import org.cyclopsgroup.doorman.api.UserSession;
import org.cyclopsgroup.doorman.api.UserSessionAttributes;
import org.cyclopsgroup.doorman.api.UserSignUpResponse;
import org.cyclopsgroup.doorman.service.dao.DAOFactory;
import org.cyclopsgroup.doorman.service.dao.DataOperationException;
import org.cyclopsgroup.doorman.service.dao.UserDAO;
import org.cyclopsgroup.doorman.service.dao.UserSessionDAO;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.cyclopsgroup.doorman.service.storage.StoredUserSession;
import org.cyclopsgroup.doorman.service.storage.StoredUserSignUpRequest;
import org.cyclopsgroup.doorman.service.storage.StoredUserState;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of session service
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Service
public class DefaultSessionService
    implements SessionService
{
    private static final Log LOG = LogFactory.getLog( DefaultSessionService.class );

    private final UserDAO userDao;

    private final UserSessionDAO userSessionDao;

    private Email emailSettings;

    private boolean enableEmail;

    private String domainName = "babieslog.com";

    /**
     * @param domainName Name of domain under which users are registered
     */
    public final void setDomainName( String domainName )
    {
        this.domainName = domainName;
    }

    /**
     * @param enableEmail True if to send email confirmations
     */
    public final void setEnableEmail( boolean enableEmail )
    {
        this.enableEmail = enableEmail;
    }

    /**
     * @param emailSettings A email where SMTP settings are configured
     */
    public final void setEmailSettings( Email emailSettings )
    {
        this.emailSettings = emailSettings;
    }

    /**
     * @param daoFactory Factory instance that creates necessary DAOs
     */
    @Autowired
    public DefaultSessionService( DAOFactory daoFactory )
    {
        this.userSessionDao = daoFactory.createUserSessionDAO();
        this.userDao = daoFactory.createUserDAO();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult confirmSignUp( String sessionId, String token )
    {
        StoredUser user;
        try
        {
            user = userDao.createUser( token );
        }
        catch ( DataOperationException e )
        {
            LOG.error( "Can't create user with token " + token, e );
            return e.getOperationResult();
        }
        userSessionDao.updateUser( sessionId, user );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSession getSession( String sessionId )
    {
        StoredUserSession s = userSessionDao.pingSession( sessionId );
        if ( s == null )
        {
            return null;
        }
        return createUserSession( s );
    }

    private static UserSession createUserSession( StoredUserSession s )
    {
        UserSession session = new UserSession();
        session.setCreationDate( new LocalDateTime( s.getCreationDate() ).toDateTime( DateTimeZone.UTC ) );
        session.setLastActivity( new LocalDateTime( s.getLastModified() ).toDateTime( DateTimeZone.UTC ) );
        session.setSessionId( s.getSessionId() );

        // Set attributes
        UserSessionAttributes attributes = new UserSessionAttributes();
        attributes.setAcceptLanguage( s.getAcceptLanguage() );
        attributes.setIpAddress( s.getIpAddress() );
        attributes.setUserAgent( s.getUserAgent() );
        session.setAttributes( attributes );

        // Set user
        StoredUser u = s.getUser();
        if ( u != null )
        {
            session.setUser( ServiceUtils.createUser( u ) );
        }
        return session;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSession pingSession( String sessionId )
    {
        StoredUserSession session = userSessionDao.pingSession( sessionId );
        if ( session == null )
        {
            throw new IllegalStateException( "Session " + sessionId + " doesn't exist" );
        }
        return createUserSession( session );
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSignUpResponse requestSignUp( String sessionId, User user )
    {
        StoredUser existingUser = userDao.findByNameOrId( user.getUserName() );
        if ( existingUser != null )
        {
            return new UserSignUpResponse( UserOperationResult.IDENTITY_EXISTED, null );
        }
        StoredUserSignUpRequest request = new StoredUserSignUpRequest();
        request.setDisplayName( user.getDisplayName() );
        request.setEmailAddress( user.getEmailAddress() );
        request.setPassword( user.getPassword() );
        String id = UUIDUtils.randomStringId();
        request.setRequestId( id );
        request.setRequestToken( id );
        request.setUserName( user.getUserName() );
        request.setDomainName( domainName );
        request.setTimeZoneId( user.getTimeZoneId() );
        userDao.saveSignupRequest( request );

        if ( enableEmail )
        {
            try
            {
                SimpleEmail mail = new SimpleEmail();
                if ( emailSettings != null )
                {

                    PropertyUtils.copyProperties( mail, emailSettings );
                }
                mail.setTo( Arrays.asList( user.getEmailAddress() ) );
                mail.setSubject( "Account registration " + user.getUserName() + " from " + domainName );
                mail.setMsg( "Your registration code is " + id );
            }
            catch ( Exception e )
            {
                throw new RuntimeException( "Can't sending email with settings " + emailSettings, e );
            }

        }
        LOG.info( "Sign up request " + id + " is saved" );
        return new UserSignUpResponse( UserOperationResult.SUCCESSFUL, id );
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult signIn( String sessionId, String userName, String password )
    {
        StoredUser u = userDao.findByNameOrId( userName );
        if ( u == null )
        {
            return UserOperationResult.NO_SUCH_IDENTITY;
        }
        if ( !StringUtils.equals( password, u.getPassword() ) )
        {
            return UserOperationResult.AUTHENTICATION_FAILURE;
        }
        userSessionDao.updateUser( sessionId, u );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult signOut( String sessionId )
    {
        userSessionDao.updateUser( sessionId, null );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult signUp( String sessionId, User user )
    {
        StoredUser existing = userDao.findByNameOrId( user.getUserName() );
        if ( existing != null )
        {
            return UserOperationResult.IDENTITY_EXISTED;
        }

        String uid = UUIDUtils.randomStringId();
        StoredUser u = new StoredUser();
        u.setDomainName( domainName );
        u.setPassword( user.getPassword() );
        u.setUserId( uid );
        u.setUserState( StoredUserState.ACTIVE );
        ServiceUtils.copyUser( user, u );
        userDao.createUser( u );
        userSessionDao.updateUser( sessionId, u );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSession startSession( String sessionId, UserSessionAttributes attributes )
    {
        Validate.notNull( sessionId, "Session ID can't be NULL" );

        StoredUserSession s = new StoredUserSession();
        s.setSessionId( sessionId );
        if ( attributes != null )
        {
            s.setAcceptLanguage( attributes.getAcceptLanguage() );
            s.setIpAddress( attributes.getIpAddress() );
            s.setUserAgent( attributes.getUserAgent() );
        }
        userSessionDao.createNew( s );
        return createUserSession( s );
    }
}
