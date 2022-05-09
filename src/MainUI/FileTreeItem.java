package MainUI;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeItem extends TreeItem<File> {
    //判断树节点是否被初始化，没有初始化为真
    private boolean notInitialized = true;
    private final File file;
    private File temp = new File("image");
    private final String newpath = temp.getAbsolutePath()+"/MUIIcon.png";


    public FileTreeItem(File file) {
        //super(file,MUIIcon.getFileIconTo(file));
        super(file);
        this.file=file;
    }

    //重写getchildren方法，让节点被展开时加载子目录
    @Override
    public ObservableList<TreeItem<File>> getChildren() {

        ObservableList<TreeItem<File>> children = super.getChildren();
        //没有加载子目录时，则加载子目录作为树节点的孩子
        if (this.notInitialized && this.isExpanded()) {

            this.notInitialized = false;    //设置没有初始化为假

            /*判断树节点的文件是否是目录，
             *如果是目录，着把目录里面的所有的文件目录添加入树节点的孩子中。
             */

            if (this.getFile().isDirectory()) {
                for (File f :file.listFiles()) {
                    //如果文件是目录，则把它加到树节点上
                    if (f.isDirectory()) {
                        children.add(new FileTreeItem(f));
                    }
                }

            }
        }
        return children;
    }

    //重写叶子方法，如果该文件不是目录，则返回真
    @Override
    public boolean isLeaf() {

        return !file.isDirectory();
    }

    public File getFile() {
        return file;
    }


}

