package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Tools.Page.*;

public class Show {

    /*String baseUrl = "https://4anime.to/";
    String searchQuery = "/?s=";
    String titleClass = "episodes range active";
    String searchCSSQ = "#headerDIV_95";*/

    static String baseUrl;
    static String searchQuery;
    static String titleClass;
    static String searchCSSQ;
    static String htmlClass;

    public static void config(String base_Url, String search_Query, String title_Class, String search_CSSQ, String html_Class) {
        baseUrl = base_Url;
        searchQuery = search_Query;
        titleClass = title_Class;
        searchCSSQ = search_CSSQ;
        htmlClass = html_Class;
    }

    /* returns source url from video page */
    public static String getVideoURL(String videoPage) throws IOException {
        String returnA = null;
        System.out.println(videoPage);
        System.out.println(htmlClass);
        if (baseUrl == "https://animekisa.tv/") {
            /*PageUtils.selectPlayer("playerselect2 select_servers", videoPage);*/
            List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);
            System.out.println(vidUrl);
        }else {
            List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);
            returnA =  vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
        }
        return returnA;
    }

    @Deprecated
    public static String getVideoURLWithClass(String videoPage, String htmlClass) throws IOException {
        List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);

        return vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
    }

    /* returns all available titles with the searched name */
    public static List<String> search(String title) throws IOException {
        title = title.replace(" ", "+");
        String url = baseUrl + searchQuery + title;

        List<String> urls = new ArrayList<String>();
        List<String> prep = new ArrayList<String>();
        List<String> selection = getRawTagsOfCSSQ(url, searchCSSQ);

        if (baseUrl == "https://animekisa.tv/") {
            for (int i = 0; i < selection.size(); i++) {
                String[] ar = selection.get(i).replaceAll("<a class=\"an\" href=\"", "").split(">");

                prep.add(i, baseUrl + ar[0].replace("/", "").replaceAll("<a class=\"an hidemobile\" href=\"\"", "").replaceAll("\"", ""));
            }
            for (int i = 0; i < prep.size(); i++) {
                    urls.add(i, PageUtils.extractUrls(prep.get(i)).get(0));
            }

            for (int i = 0; i < urls.size(); i++) {
                if(urls.get(i).endsWith("https://animekisa.tv/")) {
                    urls.remove(i);
                }
            }
        } else {
            for (int i = 0; i < selection.size(); i++) {
                urls.add(i, PageUtils.extractUrls(selection.get(i)).get(0));
            }
        }

        return urls;
    }

    /* returns all available titles with the searched name */
    public static List<String> getTitle(List<String> urls, int title) throws IOException {
        List<String> episodeSelection = getRawTagsOfClass(urls.get(title), titleClass);

        List<String> episodes = null;
        if (baseUrl == "https://animekisa.tv/") {
            episodes = new ArrayList<String>();

            for (int i = 0; i < episodeSelection.size(); i++) {
                String ar[] = episodeSelection.get(i).replaceAll("<a class=\"infovan\" href=\"", "").replaceAll("\"", "").split(">");
                    episodes.add(i, baseUrl + ar[0]);
            }
        }else {
            episodes = new ArrayList<String>();
            for (int i = 0; i < episodeSelection.size(); i++) {
                List<String> allUrls = PageUtils.extractUrls(episodeSelection.get(i));
                for (int j = 0; j <allUrls.size() ; j++) {
                    episodes.add(i, allUrls.get(j));
                }

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