package com.lewicki.wojciech;

import java.util.ArrayList;
import java.util.List;

public class DocumentParser {
    private final List<String> regexList;

    DocumentParser(List<String> regexList){
        this.regexList = regexList;
    }


    public Node parseDocument(List<String> text)
    {
        List<String> content = new ArrayList<>();
        while(!text.get(0).matches(regexList.get(0))) // adds preabule/intro of the document to content till encounters highestRegex
        {
            content.add(text.get(0));
            text.remove(0);
        }
        List<Node> childrenList = getChildrenList(text);
        return new Node(null,null,content,childrenList);
    }

    public Node makeNode(List<String> textPiece,String nodeRegex)
    {
        String id = getId(textPiece,nodeRegex);
        String title = getTitle(textPiece,nodeRegex);
        textPiece = cleanAfterTakingIdAndTitle(textPiece,nodeRegex,id);
        List<String> content = getContent(textPiece,nodeRegex);
        List<Node> childrenList = getChildrenList(textPiece);
        Node node = new Node(id,title,content,childrenList);
        return node;
    }

    public String getId(List<String> textPiece,String regex)
    {
        String line = textPiece.get(0);
        switch (regex)
        {
            case "^DZIAŁ\\sX{0,3}(IX|IV|V?I{0,3})[A-Z]$":
                return line;
            case "^Rozdział\\s\\w+$":
                return line;
            case "^[A-ZĄĆŹÓŻĘŁŃŚ,]{2,}(\\s+[A-ZĄĆŹÓŻĘŁŃŚ,]+)*$":
                return line;
            case "^Art\\.\\s\\d{1,3}[a-z]*\\..*$":
                return line.split(" ")[0]+" "+line.split(" ")[1];
            case "^\\d+\\.\\s+.+$":
                return line.split(" ")[0];
            case "^\\d+[a-z]*[)]\\s+.+$":
                return line.split(" ")[0];
            case "^[a-z][)].*$":
                return line.split(" ")[0];
            default:
                return null;
        }
    }
    public String getTitle(List<String> textPiece,String regex)
    {
        if(hasTitle(regex))
        {
            String firstLine = textPiece.get(1);
            String secondLine = textPiece.get(2);
            if(titleHasTwoLines(secondLine,regex)) {
                return firstLine + " " + secondLine;
            }
            return firstLine;
        }
        return null;
    }

    public List<String> getContent(List<String> section,String regex)
    {
        List<String> lowerRegexList = getLowerRegexList(regex);
        List<String> content = new ArrayList<>();
        int i=0;
        while(i<section.size() && !matchesAnyRegex(section.get(i),lowerRegexList))
        {
            content.add(section.get(i));
            i++;
        }
        return content;
    }

    public List<Node> getChildrenList(List<String> textPiece)
    {
        String firstRegex = getFirstRegex(textPiece);
        if(firstRegex==null) return null;
        List<Node> childrenList = new ArrayList<>();
        List<ArrayList<String>> listOfSections = getListOfTextPieces(textPiece,firstRegex);
        if(listOfSections.size()==0) return null;
        for(List<String> newSection:listOfSections)
        {
            Node child = makeNode(newSection,getRegex(newSection.get(0)));
            childrenList.add(child);
        }
        return childrenList;
    }



    public String getFirstRegex(List<String> text)
    {
        for(String line:text)
        {
            if(matchesAnyRegex(line,regexList))
            {
                return getRegex(line);
            }
        }
        return null;
    }

    public  String getRegex(String line)
    {
        for(String regex: this.regexList)
        {
            if(line.matches(regex)) return regex;
        }
        return null;
    }

    public List<ArrayList<String>> getListOfTextPieces(List<String> textPiece, String firstRegex)
    {
        List<ArrayList<String>> listOfTextPieces = new ArrayList<ArrayList<String>>();
        List<String> higherRegexList = getHigherRegexList(firstRegex);
        for(int i=0;i<textPiece.size();i++){
            if(matchesAnyRegex(textPiece.get(i),higherRegexList))
            {
                String regex = getRegex(textPiece.get(i));
                ArrayList<String> newTextPiece = new ArrayList<>();
                newTextPiece.add(textPiece.get(i));
                higherRegexList = getHigherRegexList(regex);
                for(int j=i+1;j< textPiece.size() && !matchesAnyRegex(textPiece.get(j),higherRegexList);j++)
                {
                    newTextPiece.add(textPiece.get(j));
                    i=j-1;
                }
                listOfTextPieces.add(newTextPiece);
            }
        }
        return listOfTextPieces;
    }

    public List<String> cleanAfterTakingIdAndTitle(List<String> text,String regex,String id)
    {
        if(text.get(0).equals(id))
        {
            text.remove(0); //deletes the whole line with id
        }
        else
        {
            text.set(0,text.get(0).replace(id+" ","")); //deletes id from line
        }
        if(hasTitle(regex))
        {
            text.remove(0); //deletes title
            if(titleHasTwoLines(text.get(0),regex)){
                text.remove(0); //deletes second line of title when it exists
            }
        }
        return text;
    }

    public boolean titleHasTwoLines(String secondLine, String regex)
    {
        return  (!matchesAnyRegex(secondLine,getLowerRegexList(regex)) && !secondLine.matches("^Art.*$")); //second condition is set to prevent situation like this "Art. 115-129. (pominięte)"
    }

    public boolean hasTitle(String regex)
    {
        if(regex.equals("^DZIAŁ\\sX{0,3}(IX|IV|V?I{0,3})[A-Z]$") || regex.equals("^Rozdział\\s\\w+$")){
            return true;
        }
        return false;
    }

    public List<String> getLowerRegexList(String regex)
    {
        return regexList.subList(regexList.indexOf(regex),regexList.size());
    }

    public List<String> getHigherRegexList(String regex)
    {
        return regexList.subList(0,regexList.indexOf(regex)+1);
    }

    public Boolean matchesAnyRegex(String line, List<String> regexList)
    {
        for(String regex: regexList)
        {
            if(line.matches(regex)) return true;
        }
        return false;
    }
}

