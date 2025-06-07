package com.teatroingressos.pi20251.util;

import com.teatroingressos.pi20251.app.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneSwap {

    public static void setRoot(Scene scene, String fxml) throws IOException
    {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().sizeToScene();
    }

    private static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/teatroingressos/pi20251/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
