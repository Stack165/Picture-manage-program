package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.File;
import main.Image;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn = new Button("按钮A using JDK-" + System.getProperty("java.version"));
        VBox vBox = new VBox(btn);
        vBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(vBox, 640, 360));
        primaryStage.setTitle("Test");
        primaryStage.show();
        File b = new File("image/1.jpg");

        Image a = new Image(b.getAbsolutePath());
        System.out.println(a.getUrl());
    }
}