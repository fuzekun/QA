package com.fuzekun.cache;

import java.io.*;

/**
 * @author: Zekun Fu
 * @date: 2023/7/10 8:39
 * @Description:
 */
public class OpratorForJar {
    public static void main(String[] args) {
        String inputFileName = "D:\\build.sql";
        String outputFileName = "D:\\BUILD(2).sql";
        File inputFile = new File(inputFileName);
        File outputFile = new File(outputFileName);
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(outputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String upperLine = line.toUpperCase();
                writer.write(upperLine);
                writer.newLine();
            }
            System.out.println("File converted successfully.");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
