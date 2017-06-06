package io.github.yesalam.acquaint.Util;

/**
 * Created by yesalam on 06-06-2017.
 */

public class WebUtil {

    public static byte[] byteCodeit(String userid,String password){
        //String temp = "UserName=Gwalioroffice1&Password=Mohnish123&RememberMe=false" ;
        StringBuilder sb = new StringBuilder("UserName=");
        sb.append(userid);
        sb.append("&Password=");
        sb.append(password);
        sb.append("&RememberMe=");
        sb.append("true");

        return sb.toString().getBytes();
    }
}
