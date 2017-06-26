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

    @Override
    public boolean equals(Object obj) {
        if(value.equalsIgnoreCase("0") && ((SpinnerItem)obj).value.equalsIgnoreCase("")) return true;
        if(value.equalsIgnoreCase("") && ((SpinnerItem)obj).value.equalsIgnoreCase("0")) return true;
        return this.value.equalsIgnoreCase(((SpinnerItem)obj).value);
    }

    public SpinnerItem(String name, String value){
        this.name = name ;
        this.value = value;
    }

    public SpinnerItem(String value){this.value=value;}

    public String getName(){
        return name;
    }

    public String getText(){
        String ret = "" ;
        try {
            int key = Integer.parseInt(value);
            if(key==0) name = "";
            else ret = name ;
        }catch (NumberFormatException nfe){
            ret = name ;
        }
        return ret;
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
