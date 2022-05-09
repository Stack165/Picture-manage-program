package linker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class delchos extends Application {

    private boolean choose = false;
    public Button confirm1 = new Button();
    public Button notconfirm1 = new Button();

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(200,80);
        Label label = new Label("是否选择删除");
        label.setPrefSize(200,60);
        confirm1.setPrefSize(50,20);
        confirm1.setText("确定");
        confirm1.setLayoutX(25);
        confirm1.setLayoutY(60);
        notconfirm1.setPrefSize(50,20);
        notconfirm1.setText("取消");
        notconfirm1.setLayoutY(120);
        notconfirm1.setLayoutY(60);
        ap.getChildren().addAll(label,confirm1,notconfirm1);
        Scene scene = new Scene(ap);
        primaryStage.setScene(scene);
        primaryStage.show();


        confirm1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setChoose(true);
                Stage stage = (Stage) confirm1.getScene().getWindow();
                stage.close();
            }
        });

        notconfirm1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setChoose(false);
                Stage stage = (Stage) notconfirm1.getScene().getWindow();
                stage.close();
            }
        });
    }




    public delchos() {

    }

    public void start() throws Exception {
        URL url = getClass().getResource("/resource/delete.fxml");
        Stage stage = FXMLLoader.load(url);
        stage.show();
    }

    public void confirm(){
        setChoose(true);
        Stage stage = (Stage) confirm1.getScene().getWindow();
        stage.close();
    }

    public void notconfirm() {
        setChoose(false);
        Stage stage = (Stage) notconfirm1.getScene().getWindow();
        stage.close();
    }


    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
