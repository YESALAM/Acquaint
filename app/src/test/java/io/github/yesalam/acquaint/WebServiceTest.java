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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    public void test(){
        String select = "<select class=\"data-txt Impair valid\" data-val=\"true\" data-val-number=\"The field Client must be a number.\" data-val-required=\"Please select a Client.\" id=\"ClientId\" name=\"ClientId\"><option value=\"\">Select a Client</option>\n" +
                "<option value=\"101\">Indiabulls</option>\n" +
                "<option selected=\"selected\" value=\"100\">State Bank of India</option>\n" +
                "</select>";

        Document document = Jsoup.parse(select);
        Element element = document.getElementsByTag("select").first();
        Element selected = element.getElementsByAttributeValue("selected","selected").first();
        String value = selected.text();
        assertEquals(value,"State Bank of India");

    }




}
