package org.cyclopsgroup.doorman.service.storage;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "user_signup_request" )
public class StoredUserSignupRequest
{
    private String displayName;

    private String emailAddress;

    private String password;

    private Date requestDate;

    private String requestId;

    private String requestToken;

    private String userName;

    @Column( name = "display_name", nullable = false, length = 64 )
    public final String getDisplayName()
    {
        return displayName;
    }

    @Column( name = "email_address", nullable = false, length = 256 )
    public final String getEmailAddress()
    {
        return emailAddress;
    }

    @Column( name = "password", nullable = false, length = 32 )
    public final String getPassword()
    {
        return password;
    }

    @Column( name = "request_date", nullable = false )
    @Temporal( TemporalType.DATE )
    public final Date getRequestDate()
    {
        return requestDate;
    }

    @Id
    @Column( name = "request_id", length = 32 )
    public final String getRequestId()
    {
        return requestId;
    }

    @Column( name = "request_token", nullable = false, length = 64 )
    public final String getRequestToken()
    {
        return requestToken;
    }

    @Column( name = "user_name", nullable = false, length = 64 )
    public final String getUserName()
    {
        return userName;
    }

    public final void setDisplayName( String displayName )
    {
        this.displayName = displayName;
    }

    public final void setEmailAddress( String emailAddress )
    {
        this.emailAddress = emailAddress;
    }

    public final void setPassword( String password )
    {
        this.password = password;
    }

    public final void setRequestDate( Date requestDate )
    {
        this.requestDate = requestDate;
    }

    public final void setRequestId( String requestId )
    {
        this.requestId = requestId;
    }

    public final void setRequestToken( String requestToken )
    {
        this.requestToken = requestToken;
    }

    public final void setUserName( String userName )
    {
        this.userName = userName;
    }
}
