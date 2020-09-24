package com.example.bookuilib;

import java.util.ArrayList;
import java.util.List;

public class TxtPage {

    private int poosition;

    private String title;

    private int titleLines;

    private List<TxtLine> txtLists = null;

    private String content;

    private List<String> lines = new ArrayList<>();

    public TxtPage(int position){
        this.poosition = position;
    }


   public String getContent(){
        StringBuilder s = new StringBuilder();
        for (String line : lines) {
            s.append(line);
        }
        return s.toString();
    }

    public void addLine(String line){
        lines.add(line);
    }
    public void addLines(List<String> lines){
        lines.addAll(lines);
    }

    public List<String> getLines() {
        return lines;
    }

    public String getLine(int i) {
        return lines.get(i);
    }

    public int getSize() {
        return lines.size();
    }

    public String getTitle() {
        return title;
    }

    public int getTitleLines() {
        return titleLines;
    }

    public List<TxtLine> getTxtLists() {
        return txtLists;
    }

    public int getPoosition() {
        return poosition;
    }
}
