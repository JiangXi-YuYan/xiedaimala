package com.github.hcsp.io;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crawler {
    private static final String html = "https://sina.cn/";

    public static void main(String[] args){
        Integer i = null;
        if (i == 1) {
            System.out.println("something");
        }
    }

    public static List<String> sendHttpPost(String htmlPost) throws IOException {
        List<String> newsList = new ArrayList<>();
        Document document = getWebDocument(htmlPost);
        List<Element> list = document.select("a");
        for (Element element : list) {
            String getHref = element.attr("href");
            String getDataLink = element.attr("data-golink");
            if (getHref.contains("sina.cn")) {
                newsList.add(getHref);
            }
            if (getDataLink.contains("sina.cn")) {
                newsList.add(getDataLink);
            }
        }
        System.out.println(newsList);
        return newsList;
    }

    public static Document getWebDocument(String htmlPost) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(htmlPost);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4098.3 Safari/537.36");
        try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
            HttpEntity entity1 = response1.getEntity();
            String htmlTags = EntityUtils.toString(entity1);
            Document document = Jsoup.parse(htmlTags);
            EntityUtils.consume(entity1);
            return document;
        }
    }

    public static void getNewsWeb(String html) throws IOException {
        List<String> newsList = sendHttpPost(html);
        Set processLinks = new HashSet();
        String htmlFromWeb = newsList.remove(newsList.size() - 1);
        while (true) {
            if (processLinks.contains(htmlFromWeb)) {
                Document document = getWebDocument(htmlFromWeb);
            }
            processLinks.add(htmlFromWeb);
        }
    }
}
