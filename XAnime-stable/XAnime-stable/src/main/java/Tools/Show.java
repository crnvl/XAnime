package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Tools.Page.*;

public class Show {

    /**
     * XAnime is a JSoup Website Scraper, designed to fetch Anime Shows from streaming services
     */
    static String baseUrl = "https://4anime.to/";
    static String searchQuery = "?s=";
    static String titleClass = "episodes range active";
    static String searchCSSQ = "#headerDIV_95";
    public static List<String> thumbnails;

    /**
     * Configurates the values for future search queries. If none is set, a default will be used
     * @param base_Url is the url the request should be sent to
     * @param search_Query represents the extension to query a search via that website
     * @param title_Class should be the HTML class name which holds a list of all episodes
     * @param search_CSSQ holds the CSS Query name for the HTML Block which holds a list of all search results
     **/
    @Deprecated
    public static void config(String base_Url, String search_Query, String title_Class, String search_CSSQ) {
        baseUrl = base_Url;
        searchQuery = search_Query;
        titleClass = title_Class;
        searchCSSQ = search_CSSQ;
    }

    /**
     * Processes the video page's HTML and returns the raw, embedded video link
      * @param videoPage should be a single video page URL which contains the raw Video in their HTML Sourcecode
     * @return gives the raw video URL contained in the video page
     */
    public static String getVideoURL(String videoPage) {
        String returnA;
            List<String> vidUrl = getRawTagsOfURL(videoPage, "source");
            returnA =  vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
        return returnA;
    }

    /**
     * Processes the video page's HTML and returns the raw, embedded video link
     * @param videoPage should be a single video page URL which contains the raw Video in their HTML Sourcecode
     * @param htmlClass can be a custom HTML Class, which contains the raw video link
     * @return gives the raw video URL contained in the video page
     * @throws IOException
     */
    @Deprecated
    public static String getVideoURLWithClass(String videoPage, String htmlClass) throws IOException {
        List<String> vidUrl = getRawTagsOfURL(videoPage, htmlClass);
        return vidUrl.get(0).replaceAll("<source src=\"", "").replaceAll("\" type=\"video/mp4\">", "");
    }

    /**
     * Query a string search
     * @param title can be anything. Preferably the show you want to request info of
     * @param thumbnails select if {@link #thumbnails} should contain the cover art of the dedicated shows. Turning it off doesn't save bandwidth, but takes less time to process.
     * @return a list of URLs to the Title Pages which contain the amount of Episodes and their descriptions
     */
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

    /**
     *
     * @param urls should be the List of URLs returned by {@link #search(String, boolean)}
     * @param title represents the index of the URL-List to
     * @return a List of all episodes for the selected Show
     */
    public static List<String> getTitle(List<String> urls, int title) {
        List<String> episodeSelection = getRawTagsOfClass(urls.get(title), titleClass);
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

    /**
     * Gives the description for one of the selected titles through the list
     * @param urls should be the List of URLs returned by {@link #search(String, boolean)}
     * @param title represents the index of the URL-List to
     * @return a String containing the full description
     */
    public static String getDescription(List<String> urls, int title) {
        String desc = String.valueOf(getElementsByCSSQ(urls.get(title), "#description-mob"));
        String[] split = desc.split("READ MORE");
        if(split.length > 1) {
            desc = split[1].replaceAll("Description |^ ?", "").replaceAll("READ LESS]", "");
        } else {
            desc = split[0].replaceAll("\\[Description ", "").replaceAll("]$", "");
        }
        return desc;
    }

    /**
     * Gives out the video page for a selected episode
     * @param urls should be the result of {@link #getTitle(List, int)}
     * @param episode the index of the episode in the List urls.
     * @return the video page url which can be processed through {@link #getVideoURL(String)}
     */
    public static String getEpisode(List<String> urls, int episode) {
        return urls.get((urls.size() - episode) - 1);
    }

    /**
     * A all-in-one method to quickly request a video URL as a one-liner
     * @param anime represents the search query from {@link #search(String, boolean)}
     * @param title represents the index of the show in the list of the {@link #search(String, boolean)} result
     * @param episode represents the index of the episode in the list of the {@link #getTitle(List, int)} result
     * @return a link to the raw video
     */
    public static String getAnime(String anime, int title, int episode) {
        return getVideoURL(getEpisode(getTitle(search(anime, false), title), episode));
    }

}
