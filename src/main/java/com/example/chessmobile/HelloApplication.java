package com.example.chessmobile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HelloApplication extends Application
{
    private static Stage primaryStageHolder = null;
    static HashMap<Scenes, Scene> sceneMap = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException
    {
        primaryStageHolder = stage;

        String startScreen = "StartScreen.fxml";
        String playScreen = "PlayScreen.fxml";
        String settingScreen = "SettingScreen.fxml";

        sceneMap.put(Scenes.START_SCREEN, buildScene(startScreen));
        sceneMap.put(Scenes.PLAY_SCREEN, buildScene(playScreen));
        sceneMap.put(Scenes.SETTING_SCREEN, buildScene(settingScreen));

        stage.setTitle("Chess");
        stage.setScene(sceneMap.get(Scenes.START_SCREEN));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    public static void sceneChange(Scenes sceneName)
    {
        if (sceneMap.containsKey(sceneName))
        {
            primaryStageHolder.setScene(sceneMap.get(sceneName));
        }
    }

    private Scene buildScene(String resource) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        return new Scene(fxmlLoader.load(), 335, 600);
    }
}