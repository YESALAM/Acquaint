package io.github.yesalam.acquaint;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.github.yesalam.acquaint.Util.WebService;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.WebUtil.getPostDataString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yesalam on 12-06-2017.
 */

public class WebServiceTest {
    WebService webService = new WebService();

    @Test
    public void loginTest() throws Exception {
        String UrlParam = "UserName=Gwalioroffice1&Password=Mohnish123&RememberMe=false";
        HashMap<String,String> map = new HashMap();
        map.put("UserName","Gwalioroffice1");
        map.put("Password","Mohnish123");
        map.put("RememberMe","true");
        map.put("RememberMe","false");
        //webService.sendGet(ACQUAINT_URL);
        String response = webService.sendPost(ACQUAINT_URL,getPostDataString(map));

        Document document = Jsoup.parse(response);
        Element elementuser = document.getElementById("UserName");
        Element elementwel = document.getElementById("wel");
        assertNotNull(elementuser);
        assertNotNull(elementwel);


    }

    @Test
    public void testPost() throws IOException {
        String response = webService.testPost(ACQUAINT_URL);
        Document document = Jsoup.parse(response);
        Element elementuser = document.getElementById("UserName");
        Element elementwel = document.getElementById("wel");
        assertNotNull(elementuser);
        assertNotNull(elementwel);
    }

    @Test
    public void postTest() throws UnsupportedEncodingException {
        HashMap<String,String> map = new HashMap();
        map.put("name","sadare");
        map.put("email","abc@gmail.com");

        String url = "http://localhost/welcome.php";
        String response = webService.sendPost(url,getPostDataString(map));
        assertEquals("sadare",response);
    }


}
