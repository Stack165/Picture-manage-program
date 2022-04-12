package main;

import java.net.URI;

public class File extends java.io.File {
    private String prefix; //文件前缀名
    private String suffix; //文件后缀名

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setPrefix(String fileName) {
        String t = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            t = fileName.substring(0, i - 1);
        }
        System.out.println("File extension is : " + t);
        this.prefix = t;
    }

    public void setSuffix(String fileName) {
        String t = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            t = fileName.substring(i + 1);
        }
        System.out.println("File extension is : " + t);
        this.suffix = t;
    }

    public File(String pathname) {
        super(pathname);
        //设置前后缀
        setSuffix(this.getName());
        setPrefix(this.getName());

    }

    public File(String parent, String child) {
        super(parent, child);
    }

    public File(java.io.File parent, String child) {
        super(parent, child);
    }

    public File(URI uri) {
        super(uri);
    }


}
