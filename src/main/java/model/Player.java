package model;

public class Player
{
    private final String userName;
    private final boolean isWhite;
    private final TurnTimer timer;
    private ChessColorEnum color;

    public Player(String userName, boolean isWhite)
    {
        this.userName = userName;
        this.isWhite = isWhite;
        this.timer = new TurnTimer(5, 0);
        this.color = isWhite ? ChessColorEnum.WHITE : ChessColorEnum.BLACK;
    }

    public Player(String userName, boolean isWhite, int minutes, int seconds)
    {
        this.userName = userName;
        this.isWhite = isWhite;
        this.timer = new TurnTimer(minutes, seconds);
    }

    public String getUserName()
    {
        return userName;
    }

    public boolean isWhite()
    {
        return isWhite;
    }

    public TurnTimer getTimer()
    {
        return timer;
    }

    public ChessColorEnum getColor()
    {
        return color;
    }
}
