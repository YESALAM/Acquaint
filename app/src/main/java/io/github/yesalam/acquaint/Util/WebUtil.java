package io.github.yesalam.acquaint.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}
