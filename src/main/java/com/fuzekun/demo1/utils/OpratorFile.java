package com.fuzekun.demo1.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: Zekun Fu
 * @date: 2023/7/9 14:31
 * @Description:
 */
public class OpratorFile {
    public static void main(String[] args) throws Exception{
        String path = "D:\\projects\\java\\projects\\niukeP\\demo\\2_springdemo\\demo1\\src\\main\\java\\com\\fuzekun\\demo1\\utils\\";

        String fileName = "蓝桥杯算法训练-比较";
        String suffix = ".md";
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\projects\\java\\projects\\niukeP\\demo\\2_springdemo\\demo1\\src\\main\\java\\com\\fuzekun\\demo1\\utils\\蓝桥杯算法训练-比较.md"))));
        List<String> text = new ArrayList<>();
        String s;
        int flag = 1;
        while ((s = file.readLine()) != null) {
            String t = s.replaceAll(" ", "");
            if (s.equals("categories:")) {
                flag = 0;
                continue;
            }
            if (flag == 1)
                text.add(s);
            if (t.equals("")|| t.charAt(0) != '-') {
                flag = 1;
            }
        }
        // 创建新文件
        File nFile = new File(path + fileName + "backup" + suffix);
        nFile.createNewFile();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nFile)));
        for (String t: text) {
            out.write(t);
            out.write("\n");
        }
        out.flush();
    }
    public static void listFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // 如果指定路径不是目录，则直接返回
        if (!directory.isDirectory()) {
            System.out.println(directoryPath + " is not a directory.");
            return;
        }

        // 获取目录下所有文件和子目录
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果当前文件是一个目录，则递归调用listFiles方法
                    listFiles(file.getAbsolutePath());
                } else if (file.getName().endsWith(".md")) {
                    // 如果当前文件是以.md结尾的文件，则输出文件路径
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }
}
