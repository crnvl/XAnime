package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Tools.Page.*;

public class FourAnime {

    /* returns source url from video page */
    public static String getVideoURL(String videoPage) throws IOException {
        String htmlClass = "source";
        List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);

        return vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
    }

    public static String getVideoURLWithClass(String videoPage, String htmlClass) throws IOException {
        List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);

        return vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
    }

    /* returns all available titles with the searched name */
    public static List<String> search(String title) throws IOException {
        title = title.replace(" ", "+");
        String url = "https://4anime.to/?s=" + title;

        List<String> animUrl = new ArrayList<String>();
        List<String> urls = new ArrayList<String>();
        List<String> selection = getRawTagsOfCSSQ(url, "#headerDIV_95");

        for (int i = 0; i < selection.size(); i++) {
            urls.add(i, PageUtils.extractUrls(selection.get(i   )).get(0));
        }

        //urls = [https://4anime.to/anime/clannad-after-story, https://4anime.to/anime/clannad-after-story, https://4anime.to/anime/clannad-after-story]
        return urls;
    }

    /* returns all available titles with the searched name */
    public static List<String> getTitle(List<String> urls, int title) throws IOException {
        List<String> episodeSelection = getRawTagsOfClass(urls.get(title), "episodes range active");

        List<String> episodes = new ArrayList<String>();
        for (int i = 0; i < episodeSelection.size(); i++) {
            List<String> allUrls = PageUtils.extractUrls(episodeSelection.get(i));
            for (int j = 0; j <allUrls.size() ; j++) {
                episodes.add(i, allUrls.get(j));
            }

        }
        return episodes;
    }

    public static String getEpisode(List<String> urls, int episode) {
        return urls.get((urls.size() - episode) - 1);
    }

    public static String getAnime(String anime, int title, int episode) throws IOException {
        return getVideoURL(getEpisode(getTitle(search(anime), title), episode));
    }

}
