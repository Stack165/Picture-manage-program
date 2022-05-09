package application;

import MainUI.MainUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
/*        Button btn = new Button("播放幻灯片");
        VBox vBox = new VBox(btn);
        vBox.setAlignment(Pos.CENTER);
        PlayList list = new PlayList();
        for (int i = 0; i <= 5; i++) {
            String url = "F:\\javaFX\\Picture-manage-program\\image\\" + i + ".jpg";
            Image t = new Image(url);
            list.addPlayList(t);
        }
        btn.setOnAction(e -> {
            ViewImageStage sp = new ViewImageStage();
            try {
                primaryStage.close();
                sp.start(list);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        primaryStage.setScene(new Scene(vBox, 640, 360));
        primaryStage.setTitle("Test");
        primaryStage.show();*/

        MainUI x= new MainUI();
        x.start(primaryStage);
    }
}