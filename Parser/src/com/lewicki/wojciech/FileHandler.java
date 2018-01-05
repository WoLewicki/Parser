package com.lewicki.wojciech;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    public List<String> readFile(String path) throws IOException
    {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        List<String> text = new ArrayList<>();
        while (scanner.hasNextLine()) {
            text.add(scanner.nextLine());
        }
        return text;
    }
}
