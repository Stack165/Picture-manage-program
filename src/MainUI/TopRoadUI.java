package MainUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileNotFoundException;

public class TopRoadUI {
    private File imagePath = new File("image");
    //返回前进按钮,以及置顶的图片数显示
    private Image imageLeft = new Image(imagePath.getAbsolutePath()+"/left.png");
    private Image imageRight = new Image(imagePath.getAbsolutePath()+"/right.png");
    private Image imagePictureNum = new Image(imagePath.getAbsolutePath()+"/imagePictureNum.png");
    private ImageView imageViewLeft = new ImageView(imageLeft);
    private ImageView imageViewRight = new ImageView(imageRight);
    private ImageView imageViewPictureNum = new ImageView(imagePictureNum);
    private Label buttonLeft = new Label();
    private Label buttonRight = new Label();
    private static Label labelPictureNum = new Label( " " + 0 + " 张图片");
    private static BorderPane roadBorderPane = new BorderPane();
    private static TextArea textArea = new TextArea();
    private HBox buttonLRT = new HBox();
    private HBox hBoxPictureNum = new HBox();

    public TopRoadUI(){
        //设置返回前进图标,以及置顶的图片数量显示
        buttonLeft.setGraphic(imageViewLeft);
        buttonRight.setGraphic(imageViewRight);
        buttonLRT.getChildren().addAll(buttonLeft,buttonRight);
        labelPictureNum.setStyle("-fx-font: 30 arial;");
        hBoxPictureNum.getChildren().addAll(imageViewPictureNum,labelPictureNum);
        //建立显示当前文件夹路径的文本区域
        textArea.setEditable(false);//不可手动修改或者在TextArea中添加文字
        textArea.setPrefHeight(1);
        textArea.setText("");
        textArea.setStyle("-fx-font: 15 arial;-fx-text-fill:pink");
        roadBorderPane.setLeft(buttonLRT);
        roadBorderPane.setCenter(textArea);
        roadBorderPane.setRight(hBoxPictureNum);

        //后退页监听
        buttonLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!DirectoryTreeUI.getStackFile().isEmpty()){
                    if(DirectoryTreeUI.getNowPage() >= 1){
                        DirectoryTreeUI.setNowPage(DirectoryTreeUI.getNowPage()-1);
                    }
                    try {
                        PreviewInterfaceUI.reFlesh(DirectoryTreeUI.getStackFile().get(DirectoryTreeUI.getNowPage()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //前进页监听
        buttonRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!DirectoryTreeUI.getStackFile().isEmpty()){
                    if(DirectoryTreeUI.getNowPage() == DirectoryTreeUI.getMaxPage()){
                        DirectoryTreeUI.setNowPage(DirectoryTreeUI.getNowPage()-1);
                    }
                    if(DirectoryTreeUI.getNowPage() < DirectoryTreeUI.getMaxPage()-1){
                        DirectoryTreeUI.setNowPage(DirectoryTreeUI.getNowPage()+1);
                    }
                    try {
                        PreviewInterfaceUI.reFlesh(DirectoryTreeUI.getStackFile().get(DirectoryTreeUI.getNowPage()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static BorderPane getRoadBorderPane() {
        return roadBorderPane;
    }

    public void setRoadBorderPane(BorderPane roadBorderPane) {
        this.roadBorderPane = roadBorderPane;
    }

    public static Label getLabelPictureNum() {
        return labelPictureNum;
    }

    public static void setLabelPictureNum(Label labelPictureNum) {
        TopRoadUI.labelPictureNum = labelPictureNum;
    }

    public static TextArea getTextArea() {
        return textArea;
    }

    public static void setTextArea(TextArea textArea) {
        TopRoadUI.textArea = textArea;
    }
}
