package service;

import model.Player;
import com.example.chessmobile.Scenes;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObserverPlayer implements Observer<Player>
{
    private static ObserverPlayer observer;
    private final static List<Pair<Scenes, Consumer<Player>>> subscriberList = new ArrayList<>();

    private ObserverPlayer()
    {}

    @Override
    public void publish(Scenes sceneName, Player object)
    {
        for (Pair<Scenes, Consumer<Player>> data : subscriberList)
        {
            if (data.getKey() == sceneName)
            {
                data.getValue().accept(object);
            }
        }
    }

    @Override
    public void subscribe(Scenes sceneName, Consumer<Player> playerConsumer)
    {
        subscriberList.add(new Pair<>(sceneName, playerConsumer));
    }

    @Override
    public void unsubscribe(Scenes sceneName, Consumer<Player> consumer)
    {
        subscriberList.removeIf(subscriber -> subscriber.getKey() == sceneName && subscriber.getValue() == consumer);
    }

    @Override
    public void unsubscribeAll(Scenes sceneName)
    {
        subscriberList.removeIf(subscriber -> subscriber.getKey() == sceneName);
    }

    public static ObserverPlayer getObserver()
    {
        if (observer == null)
        {
            observer = new ObserverPlayer();
        }
        return observer;
    }
}
