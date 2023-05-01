package com.scaler.assignments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assignment1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> strings = new ArrayList<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    strings.addAll(readFile("files/in1.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    strings.addAll(readFile("files/in2.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.sort(strings);
                File dir = new File("src/main/resources/files");
                dir.mkdirs();
                File file = new File(dir, "out.txt");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileWriter fw = null;
                try {
                    fw = new FileWriter( file.getAbsoluteFile( ) );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedWriter bw = new BufferedWriter( fw );
                for (String str : strings) {
                    try {
                        bw.write( str+System.lineSeparator() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t3.start();
        t3.join();
        }

    private static File getResourceFile(final String fileName)
    {
        URL url = Assignment1.class.getClassLoader()
                .getResource(fileName);
        if(url == null) {
            throw new IllegalArgumentException(fileName + " is not found 1");
        }
        File file = new File(url.getFile());
        return file;
    }

    private static List<String> readFile(String fileName) throws IOException {
        File file = getResourceFile(fileName);
        return Files.lines(file.toPath()).toList();
    }
}
