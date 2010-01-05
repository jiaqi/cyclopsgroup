package org.cyclopsgroup.fiar.service.pojo;

import java.io.Serializable;

import org.joda.time.DateTime;

public class FiarGameMove
    implements Serializable
{
    private DateTime moveDate;

    private FiarGamePlayer player;

    private String userId;

    private int version;

    private int x;

    private int y;

    public final DateTime getMoveDate()
    {
        return moveDate;
    }

    public final FiarGamePlayer getPlayer()
    {
        return player;
    }

    public final String getUserId()
    {
        return userId;
    }

    public final int getVersion()
    {
        return version;
    }

    public final int getX()
    {
        return x;
    }

    public final int getY()
    {
        return y;
    }

    public final void setMoveDate( DateTime moveDate )
    {
        this.moveDate = moveDate;
    }

    public final void setPlayer( FiarGamePlayer player )
    {
        this.player = player;
    }

    public final void setUserId( String userId )
    {
        this.userId = userId;
    }

    public final void setVersion( int version )
    {
        this.version = version;
    }

    public final void setX( int x )
    {
        this.x = x;
    }

    public final void setY( int y )
    {
        this.y = y;
    }
}
