package com.fuzekun.demo1.OtherTest;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author: Zekun Fu
 * @date: 2022/9/20 11:40
 * @Description: 获取文件中strait和bend后面的长度
 */
public class Summary {

    public static void main(String[] args) throws Exception{
        String fileName = System.getProperty("user.dir") + "/highways.txt";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String line;
        double sum = 0;
        while((line = br.readLine()) != null){
            //process the line
            line = line.toLowerCase();         // 转成小写
            line = line.trim();                // 去除所有空格
            if (line.startsWith("straight")) {
                System.out.println(line);
//                int bg = -1, ed = -1;
//                String val = "";
//                for (int i = 0; i <  line.toCharArray().length; i++) {
//                    Character ch = line.charAt(i);
//                    if (ch.equals('(')) {
//                        bg = i + 2;
//                        while (line.charAt(bg) != '\"') {
//                            val += line.charAt(bg);
//                            bg ++;
//                        }
//                        break;
////                        System.out.println("bg = " + line.charAt(bg));
//                    }
//                }
                int bg = line.indexOf('(') + 1;
                System.out.println(line.charAt(bg));
                int ed = line.indexOf(')') - 1;
                System.out.println(ed);
                String val = line.substring(bg, ed + 1);   // 长度ed - bg + 1
                sum += Double.valueOf(val);
            }
            else if (line.startsWith("bend")) {
                System.out.println(line);
                int bg = line.indexOf('(') + 1;
                int ed = line.indexOf(',') - 1;
                String val = line.substring(bg, ed + 1);
                sum += Double.valueOf(val);
            }
        }
        System.out.println("总长为:" + sum / 1000.0 + "km");
        System.out.println("时速120开:" + sum / (1000.0 * 120) + "h");
        br.close();

//        Arrays.sort();

    }
}
