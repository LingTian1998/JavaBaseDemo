package main.TomcatServer.v3;

import java.io.File;
import java.io.FileFilter;

/**
 * 项目加载
 */
public class ProjectUtil {
    public static void Load(){
//      1、url确定项目 -- webapps扫描
        String webapps = ""; //webapps的路径
        //0、解压 ---- jdk自带工具
        //1、遍历文件夹
        final File[] files = new File(webapps).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
//      2、读取web.xml配置（或者注解）
        for (File file: files){
            //读取xml （引入工具包）
        }
    }

    public class Webxml{
        /* 项目文件夹地址*/

    }
}
