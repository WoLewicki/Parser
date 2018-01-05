package com.lewicki.wojciech;

import java.util.Arrays;
import java.util.List;

public class DocumentParserCreator {
    final private List<String> constitutionRegexList = Arrays.asList("^Rozdział\\s\\w+$", "^[A-ZĄĆŹÓŻĘŁŃŚ,]{2,}(\\s+[A-ZĄĆŹÓŻĘŁŃŚ,]+)*$", "^Art\\.\\s\\d{1,3}[a-z]*\\..*$", "^\\d+\\.\\s+.+$", "^\\d+[a-z]*[)]\\s+.+$", "^[a-z][)].*$");
    final private List<String> uokikRegexList = Arrays.asList("^DZIAŁ\\sX{0,3}(IX|IV|V?I{0,3})[A-Z]$", "^Rozdział\\s\\w+$", "^Art\\.\\s\\d{1,3}[a-z]*\\..*$", "^\\d+\\.\\s+.+$", "^\\d+[a-z]*[)]\\s+.+$", "^[a-z][)].*$");

    public DocumentParser createAppropriateParser(List<String> text)
    {
        if(text.stream().anyMatch(line->line.matches(uokikRegexList.get(0)))) {
            return new DocumentParser(uokikRegexList);
        }
        else{
            return new DocumentParser(constitutionRegexList);
        }
    }

}
