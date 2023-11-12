package controller;

import com.example.chessmobile.HelloApplication;
import com.example.chessmobile.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScreenController
{
    @FXML
    Button startBtn, optionBtn;

    @FXML
    public void handleStart()
    {
        HelloApplication.sceneChange(Scenes.SETTING_SCREEN);
    }
}