package model;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The class {@code TurnTimer} uses {@link java.util.Timer}
 * to create a {@code timer} counting down
 */
public class TurnTimer
{
    private int turnTimeSeconds;
    private int turnTimeMinutes;
    private boolean isRunning;

    private final IntegerProperty turnTimeSecondsProperty;
    private final IntegerProperty turnTimeMinutesProperty;
    private Timer timer;

    /**
     * Creates an instance of turnTimer
     * @param minutes representing the timers minutes
     * @param seconds representing the timers seconds
     */
    public TurnTimer(int minutes, int seconds)
    {
        turnTimeSeconds = seconds;
        turnTimeMinutes = minutes;
        turnTimeSecondsProperty = new SimpleIntegerProperty(turnTimeSeconds);
        turnTimeMinutesProperty = new SimpleIntegerProperty(turnTimeMinutes);
    }

    /**
     * Initializes a {@code Timer} on a {@code daemon thread} and starts a countdown task to zero based on the {@code TurnTimer}
     * instance variables {@code turnTimeMinutes} and {@code turnTimeSeconds}
     *
     * <p>Sets the boolean {@code isRunning} to true while the {@code timer} is running
     * and to false once the {@code timer} hits zero
     *
     */
    public synchronized void startTurn()
    {
        timer = new Timer(true);
        isRunning = true;

        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater(() ->
                {
                    if (turnTimeSeconds == 0)
                    {
                        turnTimeSeconds = 60;
                        if (turnTimeMinutes == 0)
                        {
                            cancel();
                            isRunning = false;
                            return;
                        }
                        else
                        {
                            turnTimeMinutes--;
                        }
                    }
                    turnTimeSeconds--;

                    turnTimeSecondsProperty.set(turnTimeSeconds);
                    turnTimeMinutesProperty.set(turnTimeMinutes);
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * Calls {@code cancel()} on the {@code Timer} if not null
     * and changes the boolean {@code isRunning} to false
     */
    public synchronized void endTurn()
    {
        if (timer != null)
        {
            timer.cancel();
            timer = null;
            isRunning = false;
        }
    }

    /**
     * Cancel any active {@code timer} and sets new minute and seconds
     * @param minutes minutes to replace with
     * @param seconds seconds to replace with
     */
    public void reset(int minutes, int seconds)
    {
        endTurn();
        setTurnTimeMinutes(minutes);
        setTurnTime(seconds);
    }

    /**
     * @return the seconds used by the {@code TurnTimer}
     */
    public int getTurnTimeSeconds()
    {
        return turnTimeSeconds;
    }

    /**
     * @return the seconds property to be used with databinding
     */
    public IntegerProperty getTurnTimeSecondsProperty()
    {
        return turnTimeSecondsProperty;
    }

    /**
     * @return the minutes used by the {@code TurnTimer}
     */
    public int getTurnTimeMinutes()
    {
        return turnTimeMinutes;
    }

    /**
     * @return the minutes property to be used with databinding
     */
    public IntegerProperty getTurnTimeMinutesProperty()
    {
        return turnTimeMinutesProperty;
    }

    /**
     * private method to set the seconds.
     * For public method see {@linkplain #reset(int, int)}
     * @param turnTimeSeconds seconds to be set to
     */
    private void setTurnTime(int turnTimeSeconds)
    {
        this.turnTimeSeconds = turnTimeSeconds;
        turnTimeSecondsProperty.set(turnTimeSeconds);
    }

    /**
     * private method to set the minutes. For public method see {@linkplain #reset(int, int)}
     * @param turnTimeMinutes minutes to be set to
     */
    private void setTurnTimeMinutes(int turnTimeMinutes)
    {
        this.turnTimeMinutes = turnTimeMinutes;
        turnTimeMinutesProperty.set(turnTimeMinutes);
    }

    /**
     * @return the running state of the {@code TurnTimer}
     */
    public boolean isRunning()
    {
        return isRunning;
    }
}
