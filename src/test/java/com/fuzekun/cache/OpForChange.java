package com.fuzekun.cache;


import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * @author: Zekun Fu
 * @date: 2023/7/13 16:16
 * @Description:
 */
public class OpForChange {

    public static void main(String[] args){
        String folderPath = "D:\\projects\\java\\projects\\niukeP\\demo\\2_springdemo\\demo1\\src\\main\\resources\\templates\\site";
        changeImage(folderPath);
    }

    public static void changeImage(String folderPath) {

        // 遍历当前文件夹下的所有html文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".html")) {
                // 处理html文件
                try {
                    Document doc = Jsoup.parse(file, "UTF-8");
                    for (Element img : doc.select("img.img-thumbnail")) {
                        img.attr("src", "http://qiniu.fuzekun.top/image-20230713163923900.png");
                    }
                    System.out.println("Modified img src of " + file.getName());
                    // 保存修改后的文件
                    doc.outputSettings().prettyPrint(true);
                    doc.outputSettings().charset("UTF-8");
                    doc.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
                    doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
                    doc.outputSettings().indentAmount(4);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                    out.write(doc.html());
                    out.flush();
                    out.close();
//                    Jsoup.connect(file.getAbsolutePath()).requestBody(doc.outerHtml()).post();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void changeTitle(String folderPath) {

        // 获取当前文件夹路径


        // 遍历当前文件夹下的所有html文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".html")) {
                // 处理html文件
                try {
                    Document doc = Jsoup.parse(file, "UTF-8");
                    String title = doc.title();
                    if (title.contains("-")) {
                        title = "PQA" + title.substring(title.lastIndexOf("-"));
                        doc.title(title);
                        System.out.println("Modified title of " + file.getName());
                        // 保存修改后的文件
                        doc.outputSettings().prettyPrint(true);
                        doc.outputSettings().charset("UTF-8");
                        doc.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
                        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
                        doc.outputSettings().indentAmount(4);
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                        out.write(doc.html());
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void ChangeFooter() {

        // 获取当前文件夹路径
        String folderPath = "D:\\projects\\java\\projects\\niukeP\\demo\\2_springdemo\\demo1\\src\\main\\resources\\templates\\site";

        // 遍历当前文件夹下的所有html文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".html")) {
//                if (!file.getName().equals("discuss-detail.html")) continue;
                // 处理html文件
                try {
                    Document doc = Jsoup.parse(file, "UTF-8");
                    Element footer = doc.select("footer").first();
                    if (footer != null) {
                        footer.attr("th:replace", "index::footer");
                        System.out.println("Added th:replace to " + file.getName());
                        // 保存修改后的文件
                        doc.outputSettings().prettyPrint(true);
                        doc.outputSettings().charset("UTF-8");
                        doc.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
                        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
                        doc.outputSettings().indentAmount(4);
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                        out.write(doc.html());
                        out.flush();
                        out.close();
//                        Jsoup.connect(file.getAbsolutePath()).requestBody(doc.outerHtml()).post();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
