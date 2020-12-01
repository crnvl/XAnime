package Tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Page {

    public static Document getDoc(String url) throws IOException {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> GetElementsByClass(String url, String classS) throws IOException {
        Document doc = getDoc(url);
        if (doc == null)
            return new ArrayList<String>();
        Elements links = doc.getElementsByClass(classS);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.text());
        }
        return wordsList;
    }

    public static List<String> GetElementsByTag(String url, String tag) throws IOException {
        Document doc = getDoc(url);
        if (doc == null)
            return new ArrayList<String>();
        Elements links = doc.getElementsByTag(tag);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.text());
        }
        return wordsList;
    }

    public static List<String> GetElementsByCSSQ(String url, String cssQ) {
        try {
            Document doc = getDoc(url);
            if (doc == null)
                return new ArrayList<String>();
            Elements links = doc.select(cssQ);
            List<String> wordsList = new ArrayList<String>();
            for (Element link : links) {
                wordsList.add(link.text());
            }
            return wordsList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public static List<String> getRawTagsOfURL(String url, String tag) throws IOException {
        Document doc = getDoc(url);
        if (doc == null)
            return new ArrayList<String>();
        Elements links = doc.getElementsByTag(tag);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.toString());
        }
        return wordsList;
    }

}
