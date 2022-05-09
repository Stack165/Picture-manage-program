package slidePlay;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SlidePlayController implements Initializable {
    File file = new File("image");
    String imagePath = file.getAbsolutePath();
 /*   String mediaPath = "F:\\javaFX\\Picture-manage-program\\image\\alert.wav";
    Media  media = new Media(new File(mediaPath).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);*/

    private boolean isMenuHide = true;
    Image endImage = new Image(imagePath + "/black.png");  //放映结束最后一张照片
    boolean isPlaying = false;
    public Stage rootStage;
    public Label tip;  //提示信息（第一张或最后一张）
    private int index = 0;
    private Timeline timeline;
    public MenuItem[] menuItems = new MenuItem[5];
    public ContextMenu contextMenu = new ContextMenu();
    public ImageView slide;
    public StackPane rootPane;

    //初始化右键选择框
    public void initContextMenu() {
        menuItems[0] = new MenuItem("上一张");
        menuItems[1] = new MenuItem("下一张");
        menuItems[2] = new MenuItem("播放幻灯片");
        menuItems[3] = new MenuItem("暂停播放");
        menuItems[4] = new MenuItem("退出放映");

        //上一张
        menuItems[0].setOnAction(e -> {
            if (!isPlaying) {
                if (PlayList.slidePlayIndex >= 1) {
                    if (PlayList.slidePlayIndex == PlayList.listLength) {
                        tip.setText("");
                        rootPane.setStyle("-fx-background-color: white;");
                    }
                    PlayList.slidePlayIndex--;
                    slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex));
                } else {
                    tip.setText("已经是第一张幻灯片");
                }
            }
          //  slide.requestFocus();
        });

        //下一张
        menuItems[1].setOnAction(e -> {

            if (!isPlaying) {
                tip.setText("");
                if (PlayList.slidePlayIndex == PlayList.listLength) {
                    rootStage.close();
                }
                if (PlayList.slidePlayIndex < PlayList.listLength - 1) {
                    PlayList.slidePlayIndex++;
                    slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex));
                }
                if (PlayList.slidePlayIndex == PlayList.listLength - 1) {
                    PlayList.slidePlayIndex++;
                    slide.setImage(endImage);
                    rootPane.setStyle("-fx-background-color: black;");
                    tip.setText("放映结束，点击鼠标退出");
                }
            }
           // slide.requestFocus();
        });

        //播放幻灯片
        menuItems[2].setOnAction(e -> {
            if (PlayList.slidePlayIndex != PlayList.listLength && !isPlaying) {
                tip.setText("");
                PlayList.playNum = PlayList.slidePlayIndex;
                index = 0;
                isPlaying = true;
                //slide.opacityProperty().set(1);
                timeline.play();
            }
            //slide.requestFocus();
        });

        //暂停播放
        menuItems[3].setOnAction(e -> {
            if (PlayList.slidePlayIndex != PlayList.listLength && isPlaying) {
                tip.setText("");
                timeline.stop();
                isPlaying = false;
                if (PlayList.playNum - 1 >= 0) {
                    PlayList.slidePlayIndex = PlayList.playNum - 1;
                } else {
                    PlayList.slidePlayIndex = PlayList.listLength - 1;
                }
            }
            //slide.requestFocus();
        });

        //退出放映
        menuItems[4].setOnAction(e -> {
            rootStage.close();
        });

        //将items添加到contextMenu中
        contextMenu.getItems().addAll(menuItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tip.setText("");  //提示信息置空
        initContextMenu();  //初始化右键选择菜单
        slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex)); //设置第一张图片
        rootStage.setFullScreen(true);  //设置全屏


       /* KeyValue keyValue1 = new KeyValue(slide.opacityProperty(), 0);
        KeyValue keyValue2 = new KeyValue(slide.opacityProperty(), 1);*/

        //时间轴动画
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        EventHandler<ActionEvent> onFinished = (ActionEvent t) -> {
            if (index < PlayList.listLength) {
                slide.setImage(PlayList.imagesList.get(PlayList.playNum));
            } else {
                slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex));
                index = 0;
                PlayList.playNum = PlayList.slidePlayIndex;
                timeline.stop();
            }
            System.out.println(index);
            System.out.println(PlayList.playNum);
            index++;
            PlayList.playNum = (PlayList.playNum + 1) % PlayList.listLength;
        };
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.5), onFinished);
        timeline.getKeyFrames().add(keyFrame);


        //监控屏幕大小，设置图片大小
        rootPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                slide.fitHeightProperty().set(rootPane.getHeight());
            }
        });
        rootPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                slide.fitWidthProperty().set(rootPane.getWidth());
            }
        });

        //重新聚焦，不然无法监测键盘事件
        slide.requestFocus();

        slide.focusedProperty().addListener(e -> {
            System.out.println("isFocused:"+slide.isFocused());
                    if (!slide.isFocused()) {
                        slide.requestFocus();
                    }
                }
        );
    }

    //鼠标点击事件（处理左键）
    public void mouseClick(MouseEvent mouseEvent) {

        if (!contextMenu.isShowing() && !isPlaying && isMenuHide && mouseEvent.getButton() == MouseButton.PRIMARY) {
            //如果是最后的黑屏图片就退出
            if (PlayList.slidePlayIndex == PlayList.listLength) {
                rootStage.close();
            }

            //不是最后一张图片就切换播放下一张
            if (PlayList.slidePlayIndex < PlayList.listLength - 1) {
                tip.setText("");
                PlayList.slidePlayIndex++;
                slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex));
            }

            //如果是最后一张图片就播放黑屏图片并给出提示信息
            if (PlayList.slidePlayIndex == PlayList.listLength - 1) {
                slide.setImage(endImage);
                rootPane.setStyle("-fx-background-color: black;");
                tip.setText("放映结束，点击鼠标退出");
                PlayList.slidePlayIndex++;
            }
        }

        //左键点击退出右键菜单
        if (contextMenu.isShowing() && mouseEvent.getTarget() instanceof ImageView) {
            contextMenu.hide();
            isMenuHide = true;
        }
        //slide.requestFocus();
    }

    //监控鼠标滚轮事件
    public void mouseScroll(ScrollEvent scrollEvent) {
        //slide.requestFocus();
        //要先判断右键点击菜单有没有隐藏
        if (!isPlaying && isMenuHide) {
            //向上滑动，切换到上一张
            if (scrollEvent.getDeltaY() > 0 || scrollEvent.getDeltaX() > 0) {
                // System.out.println("back");
                if (PlayList.slidePlayIndex >= 1) {
                    if (PlayList.slidePlayIndex == PlayList.listLength) {
                        tip.setText("");
                        rootPane.setStyle("-fx-background-color: white;");
                    }
                    PlayList.slidePlayIndex--;
                    slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex));
                } else {
                    //如果已经是第一张，设置提示信息
                    tip.setText("已经是第一张幻灯片");
                }
            } else {
                //否则就是切换到下一张，滚轮向下滑动

                tip.setText("");
                // System.out.println("go");
                if (PlayList.slidePlayIndex == PlayList.listLength) {
                    rootStage.close();
                }
                if (PlayList.slidePlayIndex < PlayList.listLength - 1) {
                    PlayList.slidePlayIndex++;
                    slide.setImage(PlayList.imagesList.get(PlayList.slidePlayIndex));
                }
                if (PlayList.slidePlayIndex == PlayList.listLength - 1) {
                    PlayList.slidePlayIndex++;
                    slide.setImage(endImage);
                    rootPane.setStyle("-fx-background-color: black;");
                    tip.setText("放映结束，点击鼠标退出");
                }
            }
        }
    }

    //右键菜单事件监控
    public void menuClick(ContextMenuEvent contextMenuEvent) {
        System.out.println("ContextMenuEvent");
        //没播放幻灯片就设置“暂停键”颜色变淡
        if (isPlaying) {
            contextMenu.getItems().get(3).setStyle("-fx-text-fill:black");
            contextMenu.getItems().get(0).setStyle("-fx-text-fill:#d0c0c0");
            contextMenu.getItems().get(1).setStyle("-fx-text-fill:#d0c0c0");
        } else {
            contextMenu.getItems().get(0).setStyle("-fx-text-fill:black");
            contextMenu.getItems().get(1).setStyle("-fx-text-fill:black");
            contextMenu.getItems().get(3).setStyle("-fx-text-fill:#d0c0c0");
        }
        //如果是最后的黑屏界面，则没有播放和暂停功能
        if (PlayList.slidePlayIndex == PlayList.listLength) {
            contextMenu.getItems().get(2).setStyle("-fx-text-fill:#d0c0c0");
        } else {
            contextMenu.getItems().get(2).setStyle("-fx-text-fill:black");
        }
        contextMenu.show(rootPane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
        isMenuHide = false;
        //slide.requestFocus();
    }


    //监控键盘点击事件 （esc退出）
    public void keyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            rootStage.close();
        }
    }

}
