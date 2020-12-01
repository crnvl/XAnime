package Tools;

import java.io.IOException;
import java.util.List;

import static Tools.Page.getRawTagsOfURL;

public class Anime {

    public static String getURL() throws IOException {
        String url = "https://4anime.to/black-clover-episode-154?id=43980";
        String htmlClass = "source";
        List<String> vidUrl = getRawTagsOfURL(url, htmlClass);

        return vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
    }

}
