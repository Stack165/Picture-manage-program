package slidePlay;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewImageController implements Initializable {
    public ScrollPane imageScrollPane;
    public Stage rootStage;
    /*
        private int index = 0;
        private Timeline timeline;
    */
    public StackPane imagePane;
    public Label tip;
    public Button rotate;
    @FXML
    private Button enLarge;
    @FXML
    private Button enSmall;
    @FXML
    private Button goForward;
    @FXML
    private Button goBack;
    @FXML
    private Button play;
    @FXML
    private AnchorPane bottomPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView mainImageView;
    @FXML
    private AnchorPane topPane;

    File file = new File("icon");
    String imagePath = file.getAbsolutePath();

    //放大事件
    @FXML
    public void enlargeClick() {
        tip.setText("");
        double add = 50;
        double h = mainImageView.getFitHeight() + add;
        double w = mainImageView.getFitWidth() + add;
        double hr = (imagePane.getPrefHeight() + add) / imagePane.getPrefHeight();
        double wr = (imagePane.getPrefWidth() + add) / imagePane.getPrefWidth();
        mainImageView.setFitHeight(h);
        mainImageView.setFitWidth(w);
        imagePane.setPrefHeight(imagePane.getPrefHeight() + add);
        imagePane.setPrefWidth(imagePane.getPrefWidth() + add);
        imageScrollPane.setHvalue(imageScrollPane.getHvalue() * hr);
        imageScrollPane.setVvalue(imageScrollPane.getVvalue() * wr);
        enSmall.setGraphic(new ImageView(new Image(imagePath+"/缩小.png")));

/*        System.out.println("imagePane.Height:" + imagePane.getPrefHeight() + " imagePane.getPrefWidth" + imagePane.getPrefWidth());
        System.out.println("mainImageView h:" + mainImageView.getFitHeight() + " w: " + mainImageView.getFitWidth());
        System.out.println("imageScrollPane w" + imageScrollPane.getWidth() + " h" + imageScrollPane.getHeight());*/
    }

    //缩小事件
    @FXML
    public void enSmallClick() {
        tip.setText("");

        double add = 50;
        double h = mainImageView.getFitHeight() - add;
        if (h >= (imageScrollPane.getPrefHeight() - 15) * 0.9) {
            mainImageView.setFitHeight(h);
            imagePane.setPrefHeight(imagePane.getPrefHeight() - add);
        }
        if(h-add < (imageScrollPane.getPrefHeight() - 15) * 0.9){
            enSmall.setGraphic(new ImageView(new Image(imagePath+"/缩小(1).png")));
        }
        double w = mainImageView.getFitWidth() - add;
        if (w >= (imageScrollPane.getPrefWidth() - 15) * 0.9) {
            mainImageView.setFitWidth(w);
            imagePane.setPrefWidth(imagePane.getPrefWidth() - add);
        }
        if(h-add < (imageScrollPane.getPrefHeight() - 15) * 0.9){
            enSmall.setGraphic(new ImageView(new Image(imagePath+"/缩小(1).png")));
        }
/*        imageScrollPane.setHvalue(0.5);
        imageScrollPane.setVvalue(0.5);*/
    }

    //后退
    @FXML
    public void goBackClick() {
        if (PlayList.playIndex >= 1) {
            PlayList.playIndex--;
            mainImageView.setImage(PlayList.imagesList.get(PlayList.playIndex));
            reset();
            rootStage.setTitle("图片-" + PlayList.getName(PlayList.playIndex));
            tip.setText("");
        } else {
            tip.setText("已经是第一张图片");
        }

    }

    //前进
    @FXML
    public void goForwardClick() {

        if (PlayList.playIndex < PlayList.listLength - 1) {
            PlayList.playIndex++;
            mainImageView.setImage(PlayList.imagesList.get(PlayList.playIndex));
            reset();
            rootStage.setTitle("图片-" + PlayList.getName(PlayList.playIndex));
            tip.setText("");
        } else {
            tip.setText("已经是最后一张图片");
        }
    }

    //播放
    @FXML
    public void playClick() {
        tip.setText("");
        SlidePlayStage newStage = new SlidePlayStage();
        try {
            newStage.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重新设置一些参数
    public void reset() {
        enSmall.setGraphic(new ImageView(new Image(imagePath+"/缩小(1).png")));
        mainImageView.setRotate(0);
        imagePane.setPrefWidth(rootPane.getWidth() - 15);
        imagePane.setPrefHeight(imageScrollPane.getPrefHeight() - 15);
        mainImageView.fitHeightProperty().set(imagePane.getPrefHeight() * 0.9);
        mainImageView.fitWidthProperty().set(imagePane.getPrefWidth() * 0.9);
        imageScrollPane.setHvalue(0.5);
        imageScrollPane.setVvalue(0.5);
    }

    //初始化图标
    public void initIcon() {


        //初始化图标
        Image[] a = new Image[6];
        ImageView[] b = new ImageView[6];
        a[0] = new Image(imagePath + "/后退.png");
        a[1] = new Image(imagePath + "/前进.png");
        a[2] = new Image(imagePath + "/全屏.png");
        a[3] = new Image(imagePath + "/放大.png");
        a[4] = new Image(imagePath + "/缩小(1).png");
        a[5] = new Image(imagePath + "/旋转.png");
        for (int i = 0; i < 6; i++) {
            b[i] = new ImageView(a[i]);
            b[i].setPreserveRatio(true);
            b[i].setFitWidth(32);
            b[i].setFitHeight(32);
        }
        goBack.setText("");
        goBack.setGraphic(b[0]);
        goForward.setText("");
        goForward.setGraphic(b[1]);
        play.setText("");
        play.setGraphic(b[2]);
        enLarge.setText("");
        enLarge.setGraphic(b[3]);
        enSmall.setText("");
        enSmall.setGraphic(b[4]);
        rotate.setText("");
        rotate.setGraphic(b[5]);
        //初始化提示信息
        rootStage.setTitle("图片-" + PlayList.getName(PlayList.playIndex));
        tip.setText("");
        //tip.setTextFill();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
/*        System.out.println("imagePane.Height:" + imagePane.getPrefHeight() + " imagePane.getPrefWidth" + imagePane.getPrefWidth());
        System.out.println("mainImageView h:" + mainImageView.getFitHeight() + " w: " + mainImageView.getFitWidth());
        System.out.println("imageScrollPane w" + imageScrollPane.getWidth() + " h" + imageScrollPane.getHeight());*/
        imageScrollPane.setHvalue(0.5);
        imageScrollPane.setVvalue(0.5);
        initIcon(); //初始化图标

        mainImageView.setPreserveRatio(true);
        mainImageView.setImage(PlayList.imagesList.get(PlayList.playIndex));
        mainImageView.setSmooth(true);

        rootPane.heightProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                topPane.prefHeightProperty().set(rootPane.getHeight() - 50);
                imageScrollPane.prefHeightProperty().set(topPane.getPrefHeight());
                imagePane.setPrefHeight(imageScrollPane.getPrefHeight() - 15);
                mainImageView.fitHeightProperty().set(imagePane.getPrefHeight() * 0.9);
                mainImageView.fitWidthProperty().set(imagePane.getPrefWidth() * 0.9);
                System.out.println("scrollPane h=" + imageScrollPane.getPrefHeight() + " w=" + imageScrollPane.getWidth());
                System.out.println("imagePane  h=" + imagePane.getPrefHeight() + " w=" + imagePane.getWidth());
                //System.out.println("stage h=" + rootStage.getHeight() + "  w=" + rootStage.getWidth());

                imageScrollPane.setHvalue(0.5);
                imageScrollPane.setVvalue(0.5);
            }
        });

        rootPane.widthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                bottomPane.prefWidthProperty().set(rootPane.getWidth());
                imageScrollPane.prefWidthProperty().set(rootPane.getWidth());
                imagePane.setPrefWidth(imageScrollPane.getPrefWidth() - 15);
                mainImageView.fitWidthProperty().set(imagePane.getPrefWidth() * 0.9);
                mainImageView.fitHeightProperty().set(imagePane.getPrefHeight() * 0.9);
                play.setLayoutX(bottomPane.getPrefWidth() - 32);
                rotate.setLayoutX(bottomPane.getPrefWidth() / 2 - 16);
                goBack.setLayoutX(bottomPane.getPrefWidth() / 2 + 16);
                goForward.setLayoutX(bottomPane.getPrefWidth() / 2 + 48);
                enLarge.setLayoutX(bottomPane.getPrefWidth() / 2 - 80);
                enSmall.setLayoutX(bottomPane.getPrefWidth() / 2 - 48);
                tip.setLayoutX(bottomPane.getPrefWidth() / 2 - 75);
                //System.out.println("stage h=" + rootStage.getHeight() + "  w=" + rootStage.getWidth());
/*
                System.out.println("ww  mainImageView h=" + mainImageView.getFitHeight() + "  w=" + mainImageView.getFitWidth());
*/
                imageScrollPane.setHvalue(0.5);
                imageScrollPane.setVvalue(0.5);
            }
        });
    }

    public void rotateImage(MouseEvent mouseEvent) {
        mainImageView.setRotate(mainImageView.getRotate()+90);
    }
}
