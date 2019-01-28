package com.zeba.base.adapter;

import java.util.ArrayList;
import java.util.List;

public abstract class GridGroupItem {
    private int level;
    private int parentPosition;
    private int position;
    private List<GridGroupItem> childList=new ArrayList<>();

    public abstract int getType();

    public void addChild(GridGroupItem item){
        childList.add(item);
    }

    public List<GridGroupItem> getChildList(){
        return childList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
