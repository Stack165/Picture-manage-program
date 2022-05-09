package linker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class rename extends Application {

    private String newName;
    private String lastName;
    private String[] listNames;
    private List<File> dir;
    private List<File> choFiles = new ArrayList<>();
    private List<String> faffixs = new ArrayList<>();
    private TextField inbh = new TextField();
    private TextField inws = new TextField();
    private Label zhanshi = new Label();
    private TextField shuru = new TextField();
    private Button queren = new Button();
    private Button chongxie = new Button();
    private Button tuichu = new Button();

    public static void main(String[] args) {
        launch(args);
    }

    public rename(String[] ss, List<File> dir,List<File> choFiles) {
        listNames = ss;
        this.lastName = choFiles.get(0).getName();
        this.dir = choFiles;
        this.setChoFiles(choFiles);
        for(int i=0;i<choFiles.size();i++) {
            System.out.println(this.getChoFiles().get(i).getName());
        }
        saveAllAffixs();
    }

    public void saveAllAffixs() {
        int length = getChoFiles().size();
        for(int i=0;i<length;i++) {
            int len = getChoFiles().get(i).getName().length();
            int pos = 0;
            while(getChoFiles().get(i).getName().charAt(pos)!='.') {
                pos++;
            }
            String fx = "";
            while(pos<len) {
                fx += getChoFiles().get(i).getName().charAt(pos);
                pos++;
            }
            System.out.println(fx);
            faffixs.add(fx);

        }

    }


    public void startOne(File file) {
        AnchorPane ap = new AnchorPane();
        ap.setPrefWidth(300);
        ap.setPrefHeight(80);
        VBox vb = new VBox(10);
        vb.setPrefWidth(300);
        vb.setPrefHeight(90);
        zhanshi.setPrefHeight(20);
        zhanshi.setPrefWidth(300);
        shuru.setPrefHeight(30);
        shuru.setPrefWidth(300);
        HBox hb = new HBox(10);
        hb.setPrefSize(300,20);
        Pane pane = new Pane();
        pane.setPrefSize(150,20);
        queren.setText("确认");
        queren.setPrefSize(40,20);
        chongxie.setText("重写");
        chongxie.setPrefSize(40,20);
        tuichu.setText("退出");
        tuichu.setPrefSize(40,20);
        hb.getChildren().addAll(pane,queren,chongxie,tuichu);
        vb.getChildren().addAll(zhanshi,shuru, hb);
        ap.getChildren().addAll(vb);

        String string = "该文件的旧名字为：";
        string += lastName;
        zhanshi.setText(string);
        Scene scene = new Scene(ap);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        queren.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage thisStage = (Stage) queren.getScene().getWindow();
                String newfn = "";
                newfn = shuru.getText();
                int length = faffixs.size();
                if(newfn.equals("")) {
                    zhanshi.setText("错误，输入栏不能为空");
                    return;
                }
                //单选情况下要对比文件名是否存在，多选不需要
                newName = newfn + faffixs.get(0);
                int len = listNames.length;
                for(int j=0;j<len;j++) {
                    if(newName.equals(listNames[j])) {
                        System.out.println("newName: "+newName);
                        System.out.println("lastName: "+listNames[j]);
                        zhanshi.setText("错误，输入的文件名已经存在");
                        return;
                    }
                }
                rightkey rk = new rightkey(file);
                System.out.println("toOne");
                rk.toRenameOne(file,newName);
                thisStage.close();
            }
        });

        chongxie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shuru.setText("");
                String string = "该文件的旧名字为：";
                string += lastName;
                zhanshi.setText(string);
            }
        });

        tuichu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) tuichu.getScene().getWindow();
                stage.close();
            }
        });

    }

    public void startMore(File[] files) throws Exception{
        AnchorPane ap = new AnchorPane();
        ap.setPrefWidth(400);
        ap.setPrefHeight(80);
        VBox vb = new VBox(10);
        vb.setPrefWidth(400);
        vb.setPrefHeight(90);
        zhanshi.setPrefHeight(20);
        zhanshi.setPrefWidth(400);
        shuru.setPrefHeight(30);
        shuru.setPrefWidth(400);
        HBox hb = new HBox(10);
        hb.setPrefSize(200,20);
        HBox hb1 = new HBox(10);
        Label bianhao = new Label("起始编号:");
        bianhao.setPrefSize(60,20);
//        bianhao.setAlignment(Pos.CENTER_RIGHT);

        inbh.setPrefSize(60,20);
        hb1.getChildren().addAll(bianhao,inbh);
        HBox hb2 = new HBox(10);
        Label weishu = new Label("位数");
        weishu.setPrefSize(30,20);
        weishu.setAlignment(Pos.CENTER_RIGHT);

        inws.setPrefSize(50,20);
        hb2.getChildren().addAll(weishu,inws);
//        Pane pane = new Pane();
//        pane.setPrefSize(150,20);
        queren.setText("确认");
        queren.setPrefSize(40,20);
        chongxie.setText("重写");
        chongxie.setPrefSize(40,20);
        tuichu.setText("退出");
        tuichu.setPrefSize(40,20);
        hb.getChildren().addAll(hb1,hb2,queren,chongxie,tuichu);
        vb.getChildren().addAll(zhanshi,shuru, hb);
        ap.getChildren().addAll(vb);

        String string = "该文件的旧名字为：";
        string += lastName;
        zhanshi.setText(string);
        Scene scene = new Scene(ap);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        queren.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage thisStage = (Stage) queren.getScene().getWindow();
                String newfn = "";
                String bianhao = "";
                String weishu = "";
                newfn = shuru.getText();
                bianhao = inbh.getText();
                weishu = inws.getText();
                if(newfn.equals("")||bianhao.equals("")||weishu.equals("")) {
                    zhanshi.setText("错误，输入栏不能为空");
                    return;
                }
//        int length = listNames.length;
//        int pos = 0;
//        length = newName.length();
//        fn = "";
//        while (newName.charAt(pos) != '.') {
//            fn += newName.charAt(pos);
//            pos++;
//        }
//        faffix = "";
//        while (pos<length) {
//            faffix += newName.charAt(pos);
//            pos++;
//        }
//        length = getChoFiles().size();
                int bnum;//起始编号
                if(bianhao.length()==1) {
                    bnum = bianhao.charAt(0) - '0';
                }
                else {
                    bnum = Integer.parseInt(bianhao);
                }
                int wnum;//位数
                if(bianhao.length()==1) {
                    wnum = bianhao.charAt(0) - '0';
                }
                else {
                    wnum = Integer.parseInt(weishu);
                }
                System.out.println("bnum="+bnum);
                System.out.println("wnum="+wnum);
                rightkey rk = new rightkey();
                for(int i=0;i<files.length;i++) {
                    System.out.println(files[i].getName());
                    System.out.println(files[i].getAbsolutePath());
                }

//                rk.toRenameMore(files,fn,faffix,bnum,wnum);
                thisStage.close();
            }
        });

        chongxie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shuru.setText("");
                String string = "该文件的旧名字为：";
                string += lastName;
                zhanshi.setText(string);
            }
        });

        tuichu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) tuichu.getScene().getWindow();
                stage.close();
            }
        });

    }

    public void toConfirm() throws Exception{
        Stage thisStage = (Stage) queren.getScene().getWindow();
        String newfn = "";
        String bianhao = "";
        String weishu = "";
        newfn = shuru.getText();
        bianhao = inbh.getText();
        weishu = inws.getText();
        if(newfn.equals("")||bianhao.equals("")||weishu.equals("")) {
            zhanshi.setText("错误，输入栏不能为空");
            return;
        }
//        int length = listNames.length;
//        int pos = 0;
//        length = newName.length();
//        fn = "";
//        while (newName.charAt(pos) != '.') {
//            fn += newName.charAt(pos);
//            pos++;
//        }
//        faffix = "";
//        while (pos<length) {
//            faffix += newName.charAt(pos);
//            pos++;
//        }
//        length = getChoFiles().size();
        int bnum;//起始编号
        if(bianhao.length()==1) {
            bnum = bianhao.charAt(0) - '0';
        }
        else {
            bnum = Integer.parseInt(bianhao);
        }
        int wnum;//位数
        if(bianhao.length()==1) {
            wnum = bianhao.charAt(0) - '0';
        }
        else {
            wnum = Integer.parseInt(weishu);
        }



        rightkey rk = new rightkey();
//        rk.toRenameMore(files,fn,faffix,bnum,wnum);
        thisStage.close();
    }

    public void reIn() {
        shuru.setText("");
        String string = "该文件的旧名字为：";
        string += lastName;
        zhanshi.setText(string);
    }

    public void Exit() {
        Stage stage = (Stage) tuichu.getScene().getWindow();
        stage.close();
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public List<File> getChoFiles() {
        return choFiles;
    }

    public void setChoFiles(List<File> choFiles) {
        this.choFiles = choFiles;
    }
}
