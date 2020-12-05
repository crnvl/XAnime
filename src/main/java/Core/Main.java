package Core;

import Tools.Show;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Show.config("https://4anime.to/", "/?s=", "episodes range active", "#headerDIV_95", "source");
        Show.config("https://animekisa.tv/", "/search?q=", "infovan", ".lisbox22 .an", "jw-media jw-reset");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Specify an Anime:");
        String s = br.readLine();

        List<String> titles = Show.search(s);
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "    |   " + titles.get(i));
        }

        System.out.println("Select a title by typing one of the available numbers:");
        s = br.readLine();

        List<String> episodes = Show.getTitle(titles, Integer.parseInt(s));
        for (int i = 0; i < episodes.size(); i++) {
            System.out.println(i + "    |   " + episodes.get((episodes.size() - i) - 1));
        }

        System.out.println("Select an episode by typing one of the available numbers:");
        s = br.readLine();

        String url = Show.getEpisode(episodes, Integer.parseInt(s));
        System.out.println("Ready to watch: " + Show.getVideoURL(url));



    }

}
