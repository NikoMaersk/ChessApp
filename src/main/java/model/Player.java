package model;

public class Player
{
    private String userName;
    private boolean isWhite;
    private TurnTimer timer;

    public Player(String userName, boolean isWhite)
    {
        this.userName = userName;
        this.isWhite = isWhite;
        this.timer = new TurnTimer(5, 0);
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
}
