package com.lewicki.wojciech;

import com.beust.jcommander.JCommander;
import java.io.IOException;
import java.util.List;

public class Main {
     public static void main(String[] args) throws IOException {

        Arguments arguments = new Arguments();
        JCommander  jCommander = new JCommander(arguments);
        jCommander.setProgramName("Document Parser");

        try{
            jCommander.parse(args);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            jCommander.usage();
            System.exit(0);
        }
        FileHandler fileHandler = new FileHandler();
        TextFormatter textFormatter = new TextFormatter();
        List<String> text=null;
        try{
            text = fileHandler.readFile(arguments.getPath());
        }catch (IOException e)
        {
            System.out.println("File reading error");
            System.exit(1);
        }
        text=textFormatter.formatText(text);

        DocumentParserCreator documentParserCreator = new DocumentParserCreator();
        DocumentParser documentParser = documentParserCreator.createAppropriateParser(text);
        System.out.println(text.get(0));
        Node bill = documentParser.parseDocument(text);
        BillService billService = new BillService(bill);
        try {
            billService.show(arguments);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            jCommander.usage();
        }

    }

}
