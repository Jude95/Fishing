package com.jude.fishing.widget;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuchenxi on 15/12/19.
 */
public class JSHTMLCleaner {

    List<HtmlCleaner> mCleaner = new ArrayList<>();

    public void addCleaner(HtmlCleaner cleaner){
        mCleaner.add(cleaner);
    }

    public String createCreanJS(String url){
        return "javascript:\n" +
                createCleanJsTool()+
                createCleanJsCore(url);
    }


    private String createCleanJsTool(){
        return "function removeElement(_element){\n" +
                "         if(_element){\n" +
                "              var _parentElement = _element.parentNode;\n" +
                "              if(_parentElement){\n" +
                "                 _parentElement.removeChild(_element);\n" +
                "              }\n" +
                "         }\n" +
                "         \n" +
                "}\n" +
                "function getElementsByClassName(n) {\n" +
                "    var classElements = [],allElements = document.getElementsByTagName('*');\n" +
                "    for (var i=0; i< allElements.length; i++ )\n" +
                "   {\n" +
                "       if (allElements[i].className == n ) {\n" +
                "           classElements[classElements.length] = allElements[i];\n" +
                "        }\n" +
                "   }\n" +
                "   return classElements;\n" +
                "}\n"+
                "function removeElementByClass(n){\n" +
                "         var elements =getElementsByClassName(n);\n" +
                "         for(var i = 0;i < elements.length;i++){\n"+
                "             removeElement(elements[i]);\n"+
                "         }\n"+
                "}\n"+
                "function removeElementById(id){\n" +
                "         var element =document.getElementById(id);\n" +
                "         removeElement(element);\n"+
                "}\n"+
                "function del_ff(elem){\n" +
                "    var elem_child = elem.childNodes;\n" +
                "    for(var i=0; i<elem_child.length;i++){\n" +
                "        if(elem_child[i].nodeName == \"#text\" && !/\\s/.test(elem_child.nodeValue)){\n" +
                "                elem.removeChild(elem_child)\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
    }

    private String createCleanJsCore(String url){
        StringBuilder js = new StringBuilder();
        for (HtmlCleaner htmlCleaner : mCleaner) {
            if (htmlCleaner.willWork(url)){
                //清除Id部分
                if (htmlCleaner.getDirtyElementListById()!=null){
                    for (String s : htmlCleaner.getDirtyElementListById()) {
                        js.append("removeElementById(\""+s+"\");\n");
                    }
                }
                //清除class部分
                if (htmlCleaner.getDirtyElementListByClass()!=null){
                    for (String s : htmlCleaner.getDirtyElementListByClass()) {
                        js.append("removeElementByClass(\""+s+"\");\n");
                    }
                }
                String extension = htmlCleaner.getJSExtension();
                if (!TextUtils.isEmpty(extension)){
                    js.append(extension);
                }
            }

        }
        return js.toString();
    }

    public interface HtmlCleaner {

        boolean willWork(String url);

        List<String> getDirtyElementListByClass();
        List<String> getDirtyElementListById();
        String getJSExtension();
    }
}
