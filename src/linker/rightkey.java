package linker;

import java.util.Iterator;
import java.util.regex.*;
import MainUI.MainUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class rightkey {

    private byte[]fbuf = new byte[20971520];//20MB
    private int temp = 0;//缓冲区数组大小
    private String fn = "";//操控的文件的名字
    private String faffix = "";//文件后缀
    private String fileName = "";
    private String filePath = "";

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double x = 0;
    private double y = 0;

    public rightkey(File file) {
        fileName = file.getName();
        filePath = file.getAbsolutePath();
    }

    public rightkey() {

    }

    public void toCut(File file) {

        InputStream ips = null;
        try {
            ips = new FileInputStream(file);
            temp = ips.read(fbuf);
            //处理文件后缀
            int pos = 0;
            String name = fileName;
            int len = name.length();
            fn = "";
            while (name.charAt(pos) != '.') {
                fn += name.charAt(pos);
                pos++;
            }
            faffix = "";
            while(pos<len) {
                faffix += name.charAt(pos);
                pos++;
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ips != null) {
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.delete();
        }
    }

    public void toCopy(File f1) {
        temp = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f1);
            temp = fis.read(fbuf);//填入缓冲区数组，并且获得大小
            int pos = 0;
            String name = f1.getName();
            int len = name.length();
            fn = "";
            while (name.charAt(pos) != '.') {
                fn += name.charAt(pos);
                pos++;
            }
            faffix = "";
            while(pos<len) {
                faffix += name.charAt(pos);//保存".后缀"
                pos++;
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis!= null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void toPaste(File dir) {
        //dir为要复制到的文件夹，因此要新建复制的文件对象f2
        FileOutputStream fos = null;
        String[] dirFilesName = dir.list();
        int length = dirFilesName.length;
        String[] dirFns = refaffix(dirFilesName);
        String regex = "";
        int index = 0;
        for(int i=0;i<length;i++) {
            if(dirFilesName[i].equals(fileName)) {
//                fn = fn + "副本 + length";
                index = -1;
                if(fn.endsWith("-副本")) {
                    index = fn.length()-1;
                    break;
                }
                else if(fn.indexOf("-副本")>=0)
                {

                    index = fn.indexOf("-副本")+3;
                    break;
                }
                else {
                    fn = fn + "-副本";
                }
                break;
            }
        }
        int num = 0;
        regex = fn;
        System.out.println("regex= "+regex);
//        String match = "";
//        index++;//定位到”本“后面一个字符
//        for(int i=0;i<length;i++) {
//            if(dirFns[i].length()>=index) {
//                match = dirFns[i].substring(0,index);
//                if(match.equals(regex)) {
//                    num++;
//                }
//            }
//        }
        for(int i=0;i<length;i++) {
            index = -1;
            index = dirFns[i].indexOf(regex);
            if(index==0) {
                num++;
            }
        }
        System.out.println("num= "+num);
        if(num!=0) {
            fn = fn+num;
        }
//        for(int i=0;i<length;i++) {
//            if(dirFns[i].equals(fn)) {
//                num++;
//            }
//        }
//        if(num!=0) {
//            fn += num;
//        }
        File f2 = new File(dir.getAbsolutePath()+"/"+fn+faffix);
        try {
            fos = new FileOutputStream(f2);
            fos.write(fbuf,0,temp);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos!= null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void toRenameOne(File file,String newName) {
        //重命名
        String path = file.getParent();
//        System.out.println(path);
        File newfile = new File(path,newName);
        file.renameTo(newfile);

    }

    public void toRenameMore(List<File> files,String fn,List<String> faffixs,int bnum,int wnum) {
        //重命名多选
        int length = files.size();
        System.out.println("wnum= "+wnum);
        String bhaffix = "";
        ArrayList<String> bhaffixs = new ArrayList<>();
//        for (int i=bnum;i<length;i++) {
//            bhaffix = "";
//            bhaffix += i;
//            bhaffixs.add(bhaffix);
//        }
        int num = bnum;

        for (int i=0;i<length;i++) {
            bhaffix = "";
            bhaffix += num;
            num++;
            bhaffixs.add(bhaffix);
        }
        Iterator<String> it = bhaffixs.iterator();
        ArrayList<String> newbhaffixs = new ArrayList<>();
        int len;
        int rest;
        while(it.hasNext()){
            String str = it.next();
            len = str.length();
            rest = wnum-len;
            while(rest>0) {
                str = '0'+str;
                rest--;
            }
            newbhaffixs.add(str);
        }
//        for (int i=0;i<length;i++) {
//            len = bhaffixs.get(i).length();
//            rest = wnum-len;
//            while(rest>0) {
//                bhaffix = '0'+bhaffixs.get(i);
//            }
//            bhaffixs.set(i,bhaffix);
//        }
        String path = files.get(0).getParent();
        String newName = "";
        it = newbhaffixs.iterator();
        Iterator<String> it1 = faffixs.iterator();
        Iterator<File> it2 = files.iterator();
        while (it.hasNext()) {
            newName = fn+it.next()+it1.next();
            File newfile = new File(path,newName);
            it2.next().renameTo(newfile);
        }
//        for (int i=0;i<length;i++) {
//            newName = fn+bhaffixs.get(i)+faffixs.get(i);
//            File newfile = new File(path,newName);
//            files.get(i).renameTo(newfile);
//        }
    }

    public String[] refaffix(String[] filesName) {
        //去除文件后缀
        int length = filesName.length;
        String fn = "";
        String[] fns = new String[length];
        for (int i=0;i<length;i++) {
            String name = filesName[i];
            fn = "";
            int pos=0;
            int len = name.length();
            while (name.charAt(pos)!= '.') {
                fn += name.charAt(pos);
                pos++;
                if(pos==len) {
                    break;
                }
            }
            fns[i] = fn;
        }
        return fns;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
