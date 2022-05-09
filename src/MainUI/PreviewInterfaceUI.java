package MainUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import linker.rightkey;
import slidePlay.PlayList;
import slidePlay.ViewImageStage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PreviewInterfaceUI {
    private static PlayList playList = new PlayList();
    //图片数量统计
    private static int numOfImage = 0;
    //图片总大小统计
    private static double sizeOfImage = 0;
    //定义预览界面的流面板
    private static FlowPane flowPane = new FlowPane(5,5);
    //预览界面整体架构定义
    private static List<File> arrayListImageFile = new ArrayList<>();
    private static List<VBox> arrayListVbox = new ArrayList<>();
    private static Image image;
    private static Label labelName;
    private Label labelImage;
    private static ImageView imageView;
    private static VBox vBox;
    //鼠标点击数标记数组
    private static List<Integer> flag = new ArrayList<>();
    //当前选中的图片数量
    private static int choicePictureNum = 0;
    //当前选中的图片文件
    private static List<File> arrayListImageFileChoice = new ArrayList<>();
    //多选模式下选中的节点下标的记录数组
    private static List<Integer> arrayListPointed = new ArrayList<>();
    //判定是否进入多选模式，默认单选
    private static boolean isMultipleChoice = false;
    //判定此右键单击是否需要清空多选后选中的图片，默认为真
    private static boolean isMultipleChoiceEnding = true;
    //右键菜单
    private static ContextMenu contextMenu = new ContextMenu();
    private static MenuItem[] menuItems = new MenuItem[7];
    //粘贴操作用的缓冲区列表
    public static List<rightkey> bufs = new ArrayList<>();
    //重命名控件
    private static String newName;
    private static String lastName;
    private static String[] listNames;
    private static List<String> faffixs = new ArrayList<>();
    private static TextField inbh = new TextField();
    private static TextField inws = new TextField();
    private static Label zhanshi = new Label();
    private static TextField shuru = new TextField();
    private static Button queren = new Button();
    private static Button chongxie = new Button();
    private static Button tuichu = new Button();
    //删除确认界面
    public static Button confirm1 = new Button();
    public static Button notconfirm1 = new Button();
    //主界面
    private ScrollPane scrollPane = new ScrollPane(flowPane);
    private static BorderPane interfaceUI = new BorderPane();
    private static boolean firstTime = true;

    //初始化预览视图
    public PreviewInterfaceUI(){
        //设置右键菜单栏
        menuItems[0] = new MenuItem("打开");
        menuItems[1] = new MenuItem("刷新");
        menuItems[2] = new MenuItem("剪切");
        menuItems[3] = new MenuItem("复制");
        menuItems[4] = new MenuItem("粘贴");
        menuItems[5] = new MenuItem("删除");
        menuItems[6] = new MenuItem("重命名");
        contextMenu.getItems().addAll(menuItems);
        //建立预览界面面板
        //预览流板
        flowPane.setPadding(new Insets(10,20,20,20));
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setStyle("-fx-background-color: rgb(255,255,255)");
        flowPane.setPrefSize(579.8,600);
        TopRoadUI topRoadUI = new TopRoadUI();
        interfaceUI.setTop(topRoadUI.getRoadBorderPane());
        //为预览界面的流面板添加入滑动面板
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        interfaceUI.setCenter(scrollPane);
    }

    //分析当前预览视图
    public static void PreInterfaceUI(File file) throws FileNotFoundException {
        preInterfaceClean();
        if(firstTime){
            firstTime = false;
        }else{
            analysisDirectory(file);
        }
    }

    //初始化当前预览视图的数据
    public static void preInterfaceClean(){
        //初始化设置，包括清空、大小、距离设置
        sizeOfImage = 0;
        setNumOfImage(0);
        setChoicePictureNum(0);
        flowPane.getChildren().clear();
        arrayListImageFile.removeAll(arrayListImageFile);
        arrayListVbox.removeAll(arrayListVbox);
        cleanArrayChoices();
        initFlag();
    }

    // 分析文件目录
    private static void analysisDirectory(File dir) throws FileNotFoundException {
        System.out.println("正在分析文件");
        int i = 0;
        File[] files = dir.listFiles();
        if(files!=null){
            for(File f : files){
                if(!f.isDirectory()){
                    if(f.getName().endsWith(".png")||f.getName().endsWith(".jpg")||f.getName().endsWith(".jpeg")
                            ||f.getName().endsWith(".gif")||f.getName().endsWith(".bmp")||
                            f.getName().endsWith(".PNG")||f.getName().endsWith(".JPG")||f.getName().endsWith(".JPEG")
                            ||f.getName().endsWith(".GIF")||f.getName().endsWith(".BMP")){
                        System.out.println(f.getAbsolutePath());
                        arrayListImageFile.add(f);
                        setSizeOfImage(getSizeOfImage()+f.length()/1024.0);
                        setNumOfImage(getNumOfImage()+1);
                        image = new Image("file:" + f.getAbsolutePath(),120,120,true,true,true);
                        labelName= new Label(f.getName());
                        imageView = new ImageView(image);
                        //labelImage = new Label();
                        vBox = new VBox();
                        //限定宽长、像素大小
                        labelName.setWrapText(true);
                        labelName.setPrefWidth(120);
                        imageView.setPreserveRatio(true);
                        //labelImage.setGraphic(imageView);
                        //自动换行
                        //labelImage.setAlignment(Pos.BASELINE_CENTER);
                        //文字居中
                        labelName.setAlignment(Pos.CENTER);
                        vBox.getChildren().addAll(imageView,labelName);
                        arrayListVbox.add(vBox);
                        flag.add(0);
                        i++;
                        flowPane.getChildren().add(vBox);
                    }
                }
            }
        }
        PreviewInterfaceUI.imageSetOnAction();
    }


    //鼠标监控
    public static void imageSetOnAction(){
        //清空选中的内容
        //初始化点击标签
        int i =0;
        initFlag();
        flowPane.setOnMouseClicked(e->{
            MouseButton eButton = e.getButton();
            if (e.getTarget() instanceof Label){
                System.out.println("点击了节点");
            }else if (e.getTarget() instanceof Text){
                System.out.println("点击了文本");
            }else if ((e.getTarget() instanceof FlowPane) && eButton == MouseButton.PRIMARY){
                //左键点击空白区域，清空选中的图片数据
                System.out.println("点击了空白区域");
                contextMenu.hide();
                initFlag();
                setMultipleChoiceEnding(true);
                cleanArrayChoices();
                setChoicePictureNum(0);
                cleanImageStyle();
            }else if (e.getTarget() instanceof FlowPane && eButton == MouseButton.SECONDARY){
                //右键空白区域
                rightClickMenuMonitoring(flowPane,e.getScreenX(),e.getScreenY());
            }
        });
        if(!arrayListVbox.isEmpty()){
            for(VBox t : arrayListVbox){
                int finalI = i;
                arrayListVbox.get(i).setOnMouseClicked(mouseEvent -> {
                    MouseButton mouseButton = mouseEvent.getButton();
                    double x,y;
                    x = mouseEvent.getScreenX();
                    y = mouseEvent.getScreenY();
                    if(!isMultipleChoice){
                        //左键监控,单选模式
                        if (mouseButton == MouseButton.PRIMARY&&!isMultipleChoice) {
                            contextMenu.hide();
                            for(int k = 0;k < flag.size();k++){
                                if(flag.get(k) == 1 && k!=finalI){
                                    initFlag();
                                }
                                if(flag.get(k) == 2 && k!=finalI){
                                    initFlag();
                                }
                            }
                            if(flag.get(finalI) < 2){
                                flag.set(finalI,flag.get(finalI)+1);
                            }
                            setChoicePictureNum(0);
                            for(int k = 0;k < getNumOfImage();k++){
                                if(flag.get(k) != 0){
                                    choicePictureNum++;
                                }
                            }
                            LowerColumnUI.setLabelDataInformationText();
                            //被单击第一下
                            if(flag.get(finalI) == 1){
                                System.out.println("单击第一次");

                                //点击后重置全部图片的Style颜色
                                cleanImageStyle();

                                //初始化选中的VBox数组和下标数组
                                cleanArrayChoices();

                                intPutPointed(finalI);
                                choiceAdd(finalI);
                                //将点击的图片Style设置为浅蓝色
                                arrayListVbox.get(finalI).setStyle("-fx-background-color: #cef4f8 ;");
                            }else if(flag.get(finalI) == 2){
                                System.out.println("单击第二次");

                                //初始化选中的VBox数组和下标数组
                                cleanArrayChoices();

                                intPutPointed(finalI);
                                choiceAdd(finalI);
                                slidePlay();
                            }
                        }else if(mouseButton == mouseButton.SECONDARY){
                            contextMenu.hide();
                            //右键单击监控
                            if(!isMultipleChoiceEnding){
                                System.out.println("多选模式下结束后存好了VBox数组");
                                System.out.println("这是多选结束后的右键单击");
                                //测试展示
                                if(!arrayListPointed.isEmpty()){
                                    int l = 0;
                                    for(Integer u : arrayListPointed ){
                                        System.out.println("被放入的下标是"+arrayListPointed.get(l));
                                        l++;
                                    }
                                    System.out.println("下标数量:"+arrayListPointed.size());
                                }
                                setChoicePictureNum(arrayListPointed.size());
                                LowerColumnUI.setLabelDataInformationText();
                                contextMenu.show(flowPane,x,y);
                                //////////////////////////////
                                //右键菜单监控
                                rightClickMenuMonitoring(flowPane,x,y);
                            }else if(isMultipleChoiceEnding){
                                System.out.println("这是右键单击");
                                setChoicePictureNum(0);
                                choicePictureNum++;
                                LowerColumnUI.setLabelDataInformationText();

                                //点击后重置全部图片的Style颜色
                                cleanImageStyle();

                                //初始化选中的VBox数组和下标数组
                                cleanArrayChoices();

                                intPutPointed(finalI);
                                choiceAdd(finalI);
                                arrayListVbox.get(finalI).setStyle("-fx-background-color: #cef4f8 ;");
                                System.out.println("单选模式下存好了VBox数组");
                                //测试展示
                                if(!arrayListPointed.isEmpty()){
                                    int l = 0;
                                    for(Integer u : arrayListPointed ){
                                        System.out.println("被放入的下标是"+arrayListPointed.get(l));
                                        l++;
                                    }
                                    System.out.println("下标数量:"+arrayListPointed.size());
                                }
                                contextMenu.show(flowPane,x,y);
                                //////////////////////////////
                                //右键菜单监控
                                rightClickMenuMonitoring(flowPane,x,y);
                            }
                        }
                    }else{
                        //多选模式单击
                        if(mouseButton == MouseButton.PRIMARY && isMultipleChoice){
                            contextMenu.hide();
                            //多选左键单击
                            if(flag.get(finalI) == 0){
                                flag.set(finalI,flag.get(finalI)+1);
                                //第一次进来，添加如选中的节点中
                                choiceAdd(finalI);
                                intPutPointed(finalI);
                                setChoicePictureNum(arrayListImageFileChoice.size());
                                LowerColumnUI.setLabelDataInformationText();
                                arrayListVbox.get(finalI).setStyle("-fx-background-color: #cef4f8 ;");
                                choicePictureNum++;
                            }
                        }else if(mouseButton == mouseButton.SECONDARY && isMultipleChoice){
                            contextMenu.hide();
                            //多选右键单击
                            System.out.println("多选模式下存好了VBox数组");
                            //测试展示
                            if(!arrayListPointed.isEmpty()){
                                int l = 0;
                                for(Integer u : arrayListPointed ){
                                    System.out.println("被放入的下标是"+arrayListPointed.get(l));
                                    l++;
                                }
                                System.out.println("下标数量:"+arrayListPointed.size());
                            }

                            //////////////////////////////
                            //右键菜单监控
                            rightClickMenuMonitoring(flowPane,x,y);
                        }
                    }
                });
                i++;
            }
        }
    }


    //右键事件监控
    public static void rightClickMenuMonitoring(FlowPane flowPane, double x, double y){
        //////////////////////////////
        //右键菜单监控
        contextMenu.show(flowPane,x,y);
        int ri = 0;
        for(MenuItem r:menuItems){
            int finalRi = ri;
            menuItems[ri].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    switch(finalRi){
                        case 0:{
                            System.out.println("打开"+finalRi);
                            if(!arrayListImageFileChoice.isEmpty()){
                                slidePlay();
                            }
                            break;
                        }
                        case 1:{
                            System.out.println("刷新"+finalRi);
                            try {
                                reFlesh(DirectoryTreeUI.getTempFile());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 2:{
                            System.out.println("剪切"+finalRi);
                            bufs.clear();
                            int length = arrayListImageFileChoice.size();
                            for(int i=0;i<length;i++) {
                                File file = arrayListImageFileChoice.get(i);
                                rightkey fileRK = new rightkey(file);
                                fileRK.toCut(file);
                                bufs.add(fileRK);//装入缓冲区
                            }
                            try {
                                reFlesh(DirectoryTreeUI.getTempFile());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 3:{
                            System.out.println("复制"+finalRi);
                            bufs.clear();
                            int length = arrayListImageFileChoice.size();
                            for(int i=0;i<length;i++) {
                                File file = arrayListImageFileChoice.get(i);
                                rightkey fileRK = new rightkey(file);
                                fileRK.toCopy(file);
                                bufs.add(fileRK);//装入缓冲区
                            }
                            break;
                        }
                        case 4:{
                            System.out.println("粘贴"+finalRi);
                            int length = bufs.size();
                            for(int i=0;i<length;i++) {
                                bufs.get(i).toPaste(DirectoryTreeUI.getTempFile());
                            }
                            try {
                                reFlesh(DirectoryTreeUI.getTempFile());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 5:{
                            System.out.println("删除"+finalRi);
                            try {
                                Stage stage = new Stage();
                                deleteConfirmationPlay(stage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        }
                        case 6:{
                            System.out.println("重命名"+finalRi);
                            listNames = DirectoryTreeUI.getTempFile().list();
                            lastName = arrayListImageFileChoice.get(0).getName();
                            List<File> chosfiles = arrayListImageFileChoice;
                            saveAllAffixs();
//                            rename rn = new rename(filesNames,arrayListImageFile,arrayListImageFileChoice);
                            try {
                                int length = chosfiles.size();
                                if(length == 1) {
                                    startOne(arrayListImageFileChoice.get(0));
                                }
                                else if(length>1) {
                                    startMore();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            });
            ri++;
        }
    }

    //初始化鼠标点击次数标记数组
    public static void initFlag(){
        if(!flag.isEmpty()){
            for(int k = 0;k < flag.size();k++){
                flag.set(k,0);
            }
        }
    }

    //刷新面板
    public static void reFlesh(File file) throws FileNotFoundException {
        PreInterfaceUI(file);
        numOfImage = getNumOfImage();
        TopRoadUI.getLabelPictureNum().setText(" " + numOfImage + " 张图片");
        LowerColumnUI.setLabelDataInformationText();
    }

    //将选中的图片的下标传入数组
    public static void intPutPointed(int fi){
        if(!arrayListPointed.isEmpty()){
            int i = 0;
            for(Integer t:arrayListPointed){
                if(arrayListPointed.get(i) == fi){
                    return;
                }
            }
        }
        arrayListPointed.add(fi);
        return;
    }

    //初始化选中的下标数组、图片文件
    public static void cleanArrayChoices(){
        if(!arrayListPointed.isEmpty()){
            arrayListPointed.removeAll(arrayListPointed);
        }
        if(!arrayListImageFileChoice.isEmpty()){
            arrayListImageFileChoice.removeAll(arrayListImageFileChoice);
        }
    }

    //初始化图片的背景
    public static void cleanImageStyle(){
        if(!arrayListVbox.isEmpty()){
            int j = 0;
            //点击后重置全部图片的Style颜色
            for(j = 0;j < arrayListVbox.size();j++){
                arrayListVbox.get(j).setStyle(null);
            }
        }
    }

    //幻灯片播放
    public static void slidePlay(){
        if(!arrayListImageFileChoice.isEmpty()){
            playList.initList(arrayListImageFileChoice);
        } else {
            playList.initList(arrayListImageFile);
        }
        ViewImageStage x = new ViewImageStage();
        try {
            x.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将选中的各图片数据传入选中数组
    public static void choiceAdd(int i){
        arrayListImageFileChoice.add(arrayListImageFile.get(i));
    }

    //重命名

    public static void saveAllAffixs() {
        int length = arrayListImageFileChoice.size();
        for(int i=0;i<length;i++) {
            int len = arrayListImageFileChoice.get(i).getName().length();
            int pos = 0;
            while(arrayListImageFileChoice.get(i).getName().charAt(pos)!='.') {
                pos++;
            }
            String fx = "";
            while(pos<len) {
                fx += arrayListImageFileChoice.get(i).getName().charAt(pos);
                pos++;
            }
            faffixs.add(fx);

        }

    }

    public static void startOne(File file) {
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
                try {
                    reFlesh(DirectoryTreeUI.getTempFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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

    public static void startMore() throws Exception{
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
                int bnum;//起始编号
                if(bianhao.length()==1) {
                    bnum = bianhao.charAt(0) - '0';
                }
                else {
                    bnum = Integer.parseInt(bianhao);
                }
                int wnum;//位数
                if(bianhao.length()==1) {
                    wnum = weishu.charAt(0) - '0';
                }
                else {
                    wnum = Integer.parseInt(weishu);
                }
                if(bianhao.length()>wnum) {
                    zhanshi.setText("错误，输入的编号长度大于你输入的位数");
                    return;
                }
//                System.out.println("bnum="+bnum);
//                System.out.println("wnum="+wnum);
                rightkey rk = new rightkey();
//                for(int i=0;i<arrayListImageFileChoice.size();i++) {
//                    System.out.println(arrayListImageFileChoice.get(i).getName());
//                    System.out.println(files[i].getAbsolutePath());
//                }

                rk.toRenameMore(arrayListImageFileChoice,newfn,faffixs,bnum,wnum);
                try {
                    reFlesh(DirectoryTreeUI.getTempFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                thisStage.close();
            }
        });

        chongxie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shuru.setText("");
                inbh.setText("");
                inws.setText("");
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

    //右键删除确认框
    public static void deleteConfirmationPlay(Stage primaryStage) throws Exception {
        StackPane ap = new StackPane();
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Label label = new Label("是否选择删除");
        confirm1.setText("确定");
        confirm1.setPrefWidth(60);
        notconfirm1.setText("取消");
        notconfirm1.setPrefWidth(60);
        label.setPrefWidth(120);
        label.setStyle("-fx-font: 20 arial;-fx-text-fill:red");
        confirm1.setStyle("-fx-font: 18 arial;-fx-text-fill:pink");
        notconfirm1.setStyle("-fx-font: 18 arial;-fx-text-fill:red");
        label.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(confirm1,notconfirm1);
        vBox.getChildren().addAll(label,hBox);
        ap.getChildren().add(vBox);
        Scene scene = new Scene(ap);
        primaryStage.setScene(scene);
        primaryStage.show();


        confirm1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int length = arrayListImageFileChoice.size();
                for(int i=0;i<length;i++) {
                    File file = arrayListImageFileChoice.get(i);
//                    rightkey fileRK = new rightkey(file);
//                    fileRK.todelete(file);
                    file.delete();
                }
                //每个文件都删除后刷新
                try {
                    reFlesh(DirectoryTreeUI.getTempFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) confirm1.getScene().getWindow();
                stage.close();
            }
        });

        notconfirm1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) notconfirm1.getScene().getWindow();
                stage.close();
            }
        });
    }

    public static int getNumOfImage() {
        return numOfImage;
    }

    public static void setNumOfImage(int imageCount) {
        PreviewInterfaceUI.numOfImage = imageCount;
    }

    public List<VBox> getArrayListVbox() {
        return arrayListVbox;
    }

    public static int getChoicePictureNum() {
        return choicePictureNum;
    }

    public static void setChoicePictureNum(int choicePictureNum) {
        PreviewInterfaceUI.choicePictureNum = choicePictureNum;
    }

    public static boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public static void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }

    public static boolean isMultipleChoiceEnding() {
        return isMultipleChoiceEnding;
    }

    public static void setMultipleChoiceEnding(boolean multipleChoiceEnding) {
        isMultipleChoiceEnding = multipleChoiceEnding;
    }

    public static double getSizeOfImage() {
        return sizeOfImage;
    }

    public static void setSizeOfImage(double sizeOfImage) {
        PreviewInterfaceUI.sizeOfImage = sizeOfImage;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public static BorderPane getInterfaceUI() {
        return interfaceUI;
    }

    public static void setInterfaceUI(BorderPane interfaceUI) {
        PreviewInterfaceUI.interfaceUI = interfaceUI;
    }

    public static FlowPane getFlowPane() {
        return flowPane;
    }

    public static void setFlowPane(FlowPane flowPane) {
        PreviewInterfaceUI.flowPane = flowPane;
    }

}
