package controller;

import model.Player;
import service.ObserverPlayer;
import com.example.chessmobile.HelloApplication;
import com.example.chessmobile.Scenes;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingScreenController
{
    @FXML
    Button whiteBtn, blackBtn;

    private boolean isWhite = true;

    public void initialize()
    {
    }

    @FXML
    private void handlePlay()
    {
        Player player = new Player("Niko", isWhite);
        ObserverPlayer.getObserver().publish(Scenes.SETTING_SCREEN, player);
        HelloApplication.sceneChange(Scenes.PLAY_SCREEN);
    }

    @FXML
    private void handleChooseColor(Event e)
    {
        isWhite = e.getSource() == whiteBtn;
    }
}
