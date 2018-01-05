package com.lewicki.wojciech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BillService {
    private Node bill;
    BillService(Node bill)
    {
        this.bill = bill;
    }

    public void show(Arguments arguments) throws IllegalArgumentException
    {
        if(arguments.getElement()==null && arguments.getArticleRange()==null && arguments.getWhole()==null){
            throw new IllegalArgumentException("You didn't pass elements to print. You have to pass at least one option from these (-w,-ar,-e)");
        }
        List<Node> elementsToShow = new ArrayList<>();
        if(arguments.getWhole()!=null && arguments.getWhole())
        {
            elementsToShow.add(bill);
        }
        if(arguments.getArticleRange()!=null)
        {
            elementsToShow.addAll(getArticleRange(arguments.getArticleRange().split("-")));
        }
        if(arguments.getElement()!=null)
        {
            elementsToShow.add(bill.findSpecificNode(arguments.getElement()));
        }

        for(Node element : elementsToShow)
        {
            element.print(arguments.getContent());
        }

    }

    private List<Node> makeArticleList()
    {
        String regex = "^Art\\.\\s\\d+[a-z]*\\..*$";
        List<Node> articleList = new ArrayList<>();
        Stack<Node> stack = new Stack<Node>();
        stack.addAll(bill.getChildrenList());
        while(!stack.empty())
        {
            Node node = stack.pop();
            if(node.getId().matches(regex))
            {
                articleList.add(node);
            }
            else
            {
                stack.addAll(node.getChildrenList());
            }

        }
        Collections.reverse(articleList);
        return articleList;
    }
    public List<Node> getArticleRange(String[] articles) throws IllegalArgumentException
    {
        if(articles.length==2)
        {
            Node article1 = bill.findSpecificNode(articles[0]);
            Node article2 = bill.findSpecificNode(articles[1]);
            List<Node> articleList = makeArticleList();
            int article1Index = articleList.indexOf(article1);
            int article2Index = articleList.indexOf(article2);
            if(article1Index>article2Index) throw new IllegalArgumentException("Order of articles is inappropriate. First article must be lower. Look at the example: Art.4.-Art.12.");

            return articleList.subList(article1Index, article2Index + 1);
        }
        throw new IllegalArgumentException("Range of articles is inappropriate. Give two articles and '-' between them like this Art. 5.-Art. 10.");
    }
}
