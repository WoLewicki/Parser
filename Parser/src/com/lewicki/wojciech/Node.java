package com.lewicki.wojciech;

import java.util.*;

public class Node {
    private final String id;
    private final String title;
    private final List<String> content;
    private final List<Node> childrenList;


    Node(String id, String title, List<String> content,List<Node> childrenList)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.childrenList = childrenList;
    }


    public void print(boolean content)
    {
        if(content || (!content && id!=null && (id.matches("^DZIAŁ\\sX{0,3}(IX|IV|V?I{0,3})[A-Z]$") || id.matches("^Rozdział\\s\\w+$") || id.matches("^[A-ZĄĆŹÓŻĘŁŃŚ,]{2,}(\\s+[A-ZĄĆŹÓŻĘŁŃŚ,]+)*$"))))
        {
            if (id != null && title!=null) System.out.println(id + " " + title);
            else if (id != null) System.out.println(id);
        }
        if(content) {
            for (String line : this.content) System.out.println(line);
        }
        if (childrenList != null) {
            for (Node child : childrenList) child.print(content);
        }

    }


    private Node findNode(String id)
    {
        if(this.id!=null && this.id.equals(id))
        {
           return this;
        }
        else if(this.childrenList!=null)
        {
            for(Node child : childrenList)
            {
                if(child.findNode(id)!=null)
                    return child.findNode(id);
            }
        }
        return null;
    }


    public Node findSpecificNode(String requirements) throws IllegalArgumentException
    {
        String [] nodeIdList = requirements.split(",");
        String id = nodeIdList[0];
        Node node = this.findNode(id);
        for(int i=1;i<nodeIdList.length;i++)
        {
            id = nodeIdList[i];
            node = node.findNode(id);
        }
        if(node==null) throw new IllegalArgumentException("Program couldn't find element having this id: " + id );
        return node;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContent() {
        return content;
    }

    public List<Node> getChildrenList() {
        return childrenList;
    }
}
