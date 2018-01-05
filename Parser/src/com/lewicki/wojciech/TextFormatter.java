package com.lewicki.wojciech;

import java.util.List;
import java.util.stream.Collectors;

public class TextFormatter {
    final private String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
    final private String copyrightRegex = "^©.*$";
    final private String singleCharacterRegex = "^.{1}$";


    public List<String> formatText(List<String> text)
    {
        text=clearText(text);
        text= combineHyphenatedWords(text);
        return text;
    }

    public List<String> clearText(List<String>text)
    {
        return text.stream().filter(line -> !line.matches(dateRegex)
                            && !line.matches(copyrightRegex)
                            && !line.matches(singleCharacterRegex)).collect(Collectors.toList());
    }

    public List<String> combineHyphenatedWords(List<String>text)
    {
        for(int i=0;i<text.size();i++){
            if(text.get(i).matches("^.*[-]$"))
            {
                String firstLine = text.get(i);
                String secondLine = text.get(i+1);

                String wordBeginning = firstLine.substring(firstLine.lastIndexOf(' ') + 1); //gets the last word of the string
                wordBeginning = wordBeginning.replaceFirst(".$",""); //deletes '-'

                firstLine = firstLine.substring(0,firstLine.lastIndexOf(" ")); //removes the word taken to the second line
                secondLine = wordBeginning.concat(secondLine);

                text.set(i,firstLine);
                text.set(i+1,secondLine);
            }
        }
        return text;
    }
}

