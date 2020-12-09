package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Tools.Page.*;

public class Show {

    static String baseUrl = "https://4anime.to/";
    static String searchQuery = "?s=";
    static String titleClass = "episodes range active";
    static String searchCSSQ = "#headerDIV_95";
    public static List<String> thumbnails;

    @Deprecated
    public static void config(String base_Url, String search_Query, String title_Class, String search_CSSQ) {
        baseUrl = base_Url;
        searchQuery = search_Query;
        titleClass = title_Class;
        searchCSSQ = search_CSSQ;
    }

    /* returns source url from video page */
    public static String getVideoURL(String videoPage) {
        String returnA;
        List<String> vidUrl = getRawTagsOfURL(videoPage, "source");
        returnA =  vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
        return returnA;
    }

    @Deprecated
    public static String getVideoURLWithClass(String videoPage, String htmlClass) throws IOException {
        List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);
        return vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
    }

    /* returns all available titles with the searched name */
    public static List<String> search(String title, boolean thumbnails) {
        title = title.replace(" ", "+");
        String url = baseUrl + searchQuery + title;
        List<String> urls = new ArrayList<String>();
        List<String> selection = getRawTagsOfCSSQ(url, searchCSSQ);
        for (int i = 0; i < selection.size(); i++) {
            urls.add(i, PageUtils.extractUrls(selection.get(i)).get(0));
        }
        if(thumbnails) {
            for (int i = 0; i < selection.size(); i++) {
                urls.add(i, PageUtils.extractUrls(selection.get(i)).get(1));
            }
        }
        return urls;
    }

    /* returns all available titles with the searched name */
    public static List<String> getTitle(List<String> urls, int title) {
        List<String> episodeSelection = getRawTagsOfCSSQ(urls.get(title), titleClass);
        List<String> episodes;
        episodes = new ArrayList<String>();
        for (int i = 0; i < episodeSelection.size(); i++) {
            List<String> allUrls = PageUtils.extractUrls(episodeSelection.get(i));
            for (int j = 0; j <allUrls.size() ; j++) {
                episodes.add(i, allUrls.get(j));
            }

        }
        return episodes;
    }

    public static String getDescription(List<String> urls, int title) {
        String desc = String.valueOf(getElementsByCSSQ(urls.get(title), "#description-mob"));
            String[] split = desc.split("… READ MORE");
            desc = split[1].replaceAll("Description ", ""). replaceAll("READ LESS", "");
        return desc;
    }

    public static String getEpisode(List<String> urls, int episode) {
        return urls.get((urls.size() - episode) - 1);
    }

    public static String getAnime(String anime, int title, int episode) {
        return getVideoURL(getEpisode(getTitle(search(anime, false), title), episode));
    }

}