package io.github.yesalam.acquaint.Pojo;

/**
 * Created by yesalam on 14-06-2017.
 */

public class SpinnerItem {
    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String name;
    String value;

    public SpinnerItem(String name,String value){
        this.name = name ;
        this.value = value;
    }

    public SpinnerItem(String name){this.name=name;}

    public String getName(){
        return name;
    }

    public String getValue(){
        if(value==null) return name;
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
