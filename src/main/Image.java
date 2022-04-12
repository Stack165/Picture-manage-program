package main;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.InputStream;

public class Image extends javafx.scene.image.Image {
    /*Supported image formats are:BMP,GIF,JPEG,PNG*/
    private java.awt.Image picture;//图片
    private java.awt.Image Thumbnail; //缩略图
    private String self_path;//图片路径
    private String father_path;//父路径
    private String name;//名称
    private String filename_Extension;//文件后缀名

    public Image(String s) {
        super(s);
    }

    public Image(String s, boolean b) {
        super(s, b);
    }

    public Image(String s, double v, double v1, boolean b, boolean b1) {
        super(s, v, v1, b, b1);
    }

    public Image(String s, double v, double v1, boolean b, boolean b1, boolean b2) {
        super(s, v, v1, b, b1, b2);
    }

    public Image(InputStream inputStream) {
        super(inputStream);
    }

    public Image(InputStream inputStream, double v, double v1, boolean b, boolean b1) {
        super(inputStream, v, v1, b, b1);
    }
}
