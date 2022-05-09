package slidePlay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.net.URL;

public class SlidePlayStage extends Application {

    public void start() throws Exception {
        PlayList.playNum = PlayList.playIndex;
        PlayList.slidePlayIndex = PlayList.playIndex;
        URL url = getClass().getResource("/slidePlay.fxml");
        Stage stage = FXMLLoader.load(url);
        stage.show();
    }

    @Override
    public void start(Stage stage) {

    }
}
