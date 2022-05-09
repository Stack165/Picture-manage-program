package slidePlay;


import javafx.scene.image.Image;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PlayList {
    public static LinkedList<Image> imagesList = new LinkedList<>();
    public static int playIndex = 0;  //图片查看界面当前显示图片
    public static int slidePlayIndex = 0;  //幻灯片播放界面当前显示图片
    public static int listLength = 0;  //链表长度
    public static double waitTime = 0;  //幻灯片播放等待时间
    public static int playNum = 0;   //幻灯片播放图片序号 对应链表

    public static void reset() {
        playIndex = 0;
        waitTime = 0;
        playNum = 0;
    }

    public static String getName(int index) {
        String url = imagesList.get(index).getUrl();
        String name;
        int temp = url.lastIndexOf("\\");
        /*System.out.println(url);
        System.out.println(temp);*/
        name = url.substring(temp + 1);
        return name;
    }

    //更新播放列表数据
    public static void refresh() {
        listLength = imagesList.size();
    }

    public PlayList() {
    }

    public static void addPlayList(Image newImage) {
        imagesList.add(newImage);
        refresh();
    }

    //初始化链表
    public static void initList(List<File> arrayListImageViewChoice) {
        imagesList.clear();
        reset();
        for (File file : arrayListImageViewChoice) {
            addPlayList(new Image(file.getAbsolutePath()));
        }
    }

    public static void initList(List<File> arrayListImageViewChoice, int index) {
        imagesList.clear();
        reset();
        playIndex = index;
        for (File file : arrayListImageViewChoice) {
            addPlayList(new Image(file.getAbsolutePath()));
        }
    }

}
