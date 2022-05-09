package slidePlay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.net.URL;


public class ViewImageStage extends Application {


    public void start() throws Exception {

        URL url = getClass().getResource("/viewImage.fxml");
        Stage stage = FXMLLoader.load(url);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
}
