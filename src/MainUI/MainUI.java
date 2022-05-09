package MainUI;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception{
        //建立主面板
        BorderPane borderPane = new BorderPane();
        //初始化、建立目录树视图
        DirectoryTreeUI directoryTreeUI = new DirectoryTreeUI();
        //初始化、建立预览视图（包括上信息栏）
        PreviewInterfaceUI previewInterfaceUI = new PreviewInterfaceUI();
        //初始化、建立下信息栏栏视图
        LowerColumnUI lowerColumnUI = new LowerColumnUI();
        //开始安置各视图进入主面板
        borderPane.setLeft(directoryTreeUI.getStackPane());
        borderPane.setCenter(previewInterfaceUI.getInterfaceUI());
        borderPane.setBottom(lowerColumnUI.getBorderPaneLowerColumn());
        //加载画面
        Scene scene = new Scene(borderPane, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });


        try {
            PreviewInterfaceUI.reFlesh(DirectoryTreeUI.getTempFile());
            borderPane.setOnKeyPressed(e->{
                if(e.getCode() == KeyCode.CONTROL){
                    PreviewInterfaceUI.setMultipleChoice(true);
                    PreviewInterfaceUI.setMultipleChoiceEnding(false);
                    System.out.println("开始");
                    PreviewInterfaceUI.setChoicePictureNum(0);
                }
                borderPane.requestFocus();
            });
            borderPane.setOnKeyReleased(e->{
                if(e.getCode() == KeyCode.CONTROL){
                    PreviewInterfaceUI.setMultipleChoice(false);
                    System.out.println("结束");
                    PreviewInterfaceUI.initFlag();
                }
                borderPane.requestFocus();
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
