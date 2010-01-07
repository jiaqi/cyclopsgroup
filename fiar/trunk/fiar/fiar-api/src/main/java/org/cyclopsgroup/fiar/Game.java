package org.cyclopsgroup.fiar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

/**
 * A game information
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@XmlRootElement( name = "Game" )
public class Game
{
    private GameBoard board;

    private DateTime creationTime;

    private String creatorId;

    private String gameId;

    private String gameName;

    private GameState gameState;

    private int version;

    @XmlElement
    public final GameBoard getBoard()
    {
        return board;
    }

    @XmlJavaTypeAdapter( DateTimeAdapter.class )
    @XmlElement
    public final DateTime getCreationTime()
    {
        return creationTime;
    }

    @XmlElement
    public final String getCreatorId()
    {
        return creatorId;
    }

    @XmlElement
    public final String getGameId()
    {
        return gameId;
    }

    @XmlElement
    public final String getGameName()
    {
        return gameName;
    }

    @XmlElement
    public final GameState getGameState()
    {
        return gameState;
    }

    public final int getVersion()
    {
        return version;
    }

    public final void setBoard( GameBoard board )
    {
        this.board = board;
    }

    public final void setCreationTime( DateTime creationTime )
    {
        this.creationTime = creationTime;
    }

    public final void setCreatorId( String creatorId )
    {
        this.creatorId = creatorId;
    }

    public final void setGameId( String gameId )
    {
        this.gameId = gameId;
    }

    public final void setGameName( String name )
    {
        this.gameName = name;
    }

    public final void setGameState( GameState gameState )
    {
        this.gameState = gameState;
    }

    public final void setVersion( int version )
    {
        this.version = version;
    }
}
