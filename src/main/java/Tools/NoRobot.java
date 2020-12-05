package Tools;

import org.jsoup.Jsoup;

import java.io.IOException;

public class NoRobot {

    public static String connect(String url) throws IOException {

        String source = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                .ignoreHttpErrors(true)
                .followRedirects(true)
                .get()
                .html();

        return source;
    }

}
