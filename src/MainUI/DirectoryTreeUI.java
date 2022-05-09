package MainUI;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeUI {
    private static File tempFile;
    private static List<File> stackFile = new ArrayList<>();
    private static int nowPage = 0;
    private static int maxPage = 0;
    private static StackPane stackPane = new StackPane();
    public static File ROOT_FILE = FileSystemView.getFileSystemView().getRoots()[0];


    public DirectoryTreeUI(){
        //建立储存TreeView用的HHox
        HBox hBox = new HBox();
        //建立TreeView
        TreeView<File> treeView = new TreeView<>();
        //获取电脑文件根节点
        FileTreeItem fileTreeItem = new FileTreeItem(ROOT_FILE);
        treeView.setRoot(fileTreeItem);
        treeView.setShowRoot(false);
        treeView.setMinWidth(250);
        hBox.getChildren().add(treeView);
        //开始进行整体界面的布局
        stackPane.getChildren().add(hBox);

        //在这里判断点击了哪一个文件夹newValue
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String path = newValue.getValue().getAbsolutePath();
            setTempFile(newValue.getValue());
            stackFile.add(tempFile);
            nowPage++;
            maxPage++;
            switch (newValue.getValue().getName()){
                case "::{20D04FE0-3AEA-1069-A2D8-08002B30309D}":{
                    path = "此电脑";
                    break;
                }
                case "WPS网盘":{
                    path = "WPS网盘";
                    break;
                }
                case "::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}":{
                    path = "网络";
                    break;
                }
                case "::{031E4825-7B94-4DC3-B131-E946B44C8DD5}":{
                    path = "库";
                    break;
                }
                default:
                    break;
            }
            TopRoadUI.getTextArea().setText(path);
            try {
                PreviewInterfaceUI.reFlesh(tempFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });

        //将FileTreeItem文本换成文件夹名称-去除前置的地址
        treeView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>(){
            @Override
            public TreeCell<File> call(TreeView<File> p) {
                return new TreeCell<File>(){
                    @Override
                    protected void updateItem(File item, boolean empty){
                        super.updateItem(item,empty);
                        setText((empty || item == null) ? "" : FileSystemView.getFileSystemView().getSystemDisplayName(item));
                    }
                };
            }
        });

    }

    public static StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public static File getTempFile() {
        return tempFile;
    }

    public static void setTempFile(File tempFile) {
        DirectoryTreeUI.tempFile = tempFile;
    }

    public static List<File> getStackFile() {
        return stackFile;
    }

    public static void setStackFile(List<File> stackFile) {
        DirectoryTreeUI.stackFile = stackFile;
    }

    public static int getNowPage() {
        return nowPage;
    }

    public static void setNowPage(int nowPage) {
        DirectoryTreeUI.nowPage = nowPage;
    }

    public static int getMaxPage() {
        return maxPage;
    }

    public static void setMaxPage(int maxPage) {
        DirectoryTreeUI.maxPage = maxPage;
    }

    public static File getRootFile() {
        return ROOT_FILE;
    }

    public static void setRootFile(File rootFile) {
        ROOT_FILE = rootFile;
    }
}
