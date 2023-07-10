package com.fuzekun.cache;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2023/7/9 14:31
 * @Description:
 */
public class OpratorFile {


//    @Test
    public void test() {
        String directoryPath = "D:\\blgs\\source\\_posts";
//        listFiles(directoryPath);
//        try {
//            changeFile(new File(directoryPath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            listFiles(directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeFile(File file) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        // 读取文件并进行过滤
        List<String> text = new ArrayList<>();
        String s;
        int flag = 1;
        while ((s = in.readLine()) != null) {
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
        // 修改原文件
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String t: text) {
            out.write(t);
            out.write("\n");
        }
        out.flush();
    }
    public void listFiles(String directoryPath) throws Exception {
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
                    changeFile(file);
                    System.out.println(file.getAbsolutePath() + "修改完成!");
                }
            }
        }
    }
}
