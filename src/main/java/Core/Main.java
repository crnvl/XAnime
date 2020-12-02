package Core;

import Tools.FourAnime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Specify an Anime:");
        String s = br.readLine();

        List<String> titles = FourAnime.search(s);
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "    |   " + titles.get(i));
        }

        System.out.println("Select a title by typing one of the available numbers:");
        s = br.readLine();

        List<String> episodes = FourAnime.getTitle(titles, Integer.parseInt(s));
        for (int i = 0; i < episodes.size(); i++) {
            System.out.println(i + "    |   " + episodes.get((episodes.size() - i) - 1));
        }

        System.out.println("Select an episode by typing one of the available numbers:");
        s = br.readLine();

        String url = FourAnime.getEpisode(episodes, Integer.parseInt(s));
        System.out.println("Ready to watch: " + FourAnime.getVideoURL(url));

    }

}
