package com.lewicki.wojciech;

import com.beust.jcommander.Parameter;

public class Arguments {

    @Parameter(names = {"-p","--path"}, description = "File path", arity = 1, required = true)
    private String path;
    @Parameter(names = {"-e","--element"}, description = "element to print" + "  examples: Art. 5. or DZIAŁ III,Rozdział 1",arity = 1,required = false)
    private String element;
    @Parameter(names = {"-w","--whole"}, description = "'true' - whole document to print",arity = 1)
    private Boolean whole;
    @Parameter(names = {"-ar","--articleRange"}, description = "article range to print" + "  examples: Art. 5.-Art. 10.",arity = 1)
    private String articleRange;
    @Parameter(names = {"-c","--content"}, description = "'true' - print whole content,'false' - print only table of content",arity = 1,required = true)
    private Boolean content;
    @Parameter(names = "--help", help = false)
    private boolean help = false;

    public Boolean getContent() {
        return content;
    }
    public Boolean getWhole() {
        return whole;
    }
    public String getArticleRange() {
        return articleRange;
    }

    public String getElement() {
        return element;
    }

    public String getPath() {
        return path;
    }
}
