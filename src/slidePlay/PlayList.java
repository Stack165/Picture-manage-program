package slidePlay;

import main.Image;

import java.util.LinkedList;

public class PlayList {
    public static LinkedList<Image> imagesList = new LinkedList<>();

    private static int listLength = 0;

    private static double waitTime = 0;

    //更新播放列表数据
    public static boolean refresh() {
        listLength = imagesList.size();
        return true;
    }

    public void setWaitTime(double time){
        waitTime =time;
    }

    public int getListLength(){
        return listLength;
    }

    public double getWaitTime(){
        return waitTime;
    }

    PlayList() {
    }

}
