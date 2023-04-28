package com.scaler.assignments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assignment1 {
    public static void main(String[] args) throws IOException {
        List<String> strings = new ArrayList<>();
        strings.addAll(readFile("in1.txt"));
        strings.addAll(readFile("in2.txt"));
        Collections.sort(strings);
        FileWriter fileWriter = new FileWriter("out.txt");
        for (String str : strings) {
            fileWriter.write(str + System.lineSeparator());
        }
        fileWriter.close();
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
