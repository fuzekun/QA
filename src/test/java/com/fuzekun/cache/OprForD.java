package com.fuzekun.cache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * @author: Zekun Fu
 * @date: 2023/7/10 14:59
 * @Description:
 */
public class OprForD {

    public static void main(String[] args) throws IOException {
        // 遍历当前目录
        String curPath = "D:\\projects\\java\\projects\\niukeP\\demo\\2_springdemo\\demo1\\src\\main\\java\\com\\fuzekun\\demo1\\controller";
        Files.walk(Paths.get(curPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(OprForD::processFile);
    }

    private static void processFile(Path filePath) {
        try {
            // 读取文件内容
            String content = new String(Files.readAllBytes(filePath));
            // 查找 return 和 引号
            int idxReturn = content.indexOf("return");
            if (idxReturn >= 0) {
                int idxQuote = content.indexOf('"', idxReturn);
                if (idxQuote >= 0) {
                    // 查找第一个 /
                    int idxFirstSlash = content.indexOf('/', idxQuote);
                    if (idxFirstSlash >= 0) {
                        // 删除第一个 /
                        content = content.substring(0, idxFirstSlash) + content.substring(idxFirstSlash + 1);
                        // 将修改后的内容写回文件
                        Files.write(filePath, content.getBytes());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
