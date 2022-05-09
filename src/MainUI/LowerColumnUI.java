package MainUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class LowerColumnUI {
    //设置主界面下框
    private File imagePath = new File("image");
    private Label labelWuYong = new Label("");
    private VBox vBoxWuYong = new VBox();
    private static Label labelDataInformation = new Label( );
    private Image imageSlidePicture = new Image(imagePath.getAbsolutePath()+"/SlidePicture.png");
    private ImageView imageViewSlidePicture = new ImageView(imageSlidePicture);
    private Label labelSlidePicture = new Label();
    private static BorderPane borderPaneLowerColumn = new BorderPane();

    public LowerColumnUI(){
        //设置下栏
        labelDataInformation.setText(" " + PreviewInterfaceUI.getNumOfImage() + "张图片(" + PreviewInterfaceUI.getSizeOfImage() +" MB) - 选中" + PreviewInterfaceUI.getChoicePictureNum() + "张图片");
        vBoxWuYong.getChildren().addAll(labelWuYong,labelDataInformation);
        borderPaneLowerColumn.setLeft(vBoxWuYong);
        labelSlidePicture.setGraphic(imageViewSlidePicture);
        borderPaneLowerColumn.setCenter(labelSlidePicture);

        //播放监听
        labelSlidePicture.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("开始播放");
                PreviewInterfaceUI.slidePlay();
            }
        });
    }

    public static BorderPane getBorderPaneLowerColumn() {
        return borderPaneLowerColumn;
    }

    public void setBorderPaneLowerColumn(BorderPane borderPaneLowerColumn) {
        this.borderPaneLowerColumn = borderPaneLowerColumn;
    }

    public static Label getLabelDataInformation() {
        return labelDataInformation;
    }

    public static void setLabelDataInformation(Label labelDataInformation) {
        LowerColumnUI.labelDataInformation = labelDataInformation;
    }

    public static void setLabelDataInformationText(){
        if(PreviewInterfaceUI.getSizeOfImage() < 1024){
            String strSize = String.format("%.2f",PreviewInterfaceUI.getSizeOfImage());
            getLabelDataInformation().setText(" " + PreviewInterfaceUI.getNumOfImage() + "张图片(" + strSize +" KB) - 选中" + PreviewInterfaceUI.getChoicePictureNum() + "张图片");
        }else {
            double size = PreviewInterfaceUI.getSizeOfImage() / 1024;
            String strSize = String.format("%.2f",size);
            getLabelDataInformation().setText(" " + PreviewInterfaceUI.getNumOfImage() + "张图片(" + strSize +" MB) - 选中" + PreviewInterfaceUI.getChoicePictureNum() + "张图片");
        }
    }
}
