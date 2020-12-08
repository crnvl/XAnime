package Gui.old;

import Gui.ShowEntry;
import Gui.WrapLayout;
import Tools.Show;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIOld {
    private JTextField showSearchField;
    private JTable showsTable;
    private JButton showSearchButton;
    private JButton selectShowButton;
    private JButton linkOpenButton;
    private JPanel mainPanel;
    private JTable episodesTable;
    private JTextField selectedShowField;
    private JLabel showImageLabel;
    private JPanel showPanel;

    private DefaultTableModel showsModel = new DefaultTableModel();
    private DefaultTableModel episodesModel = new DefaultTableModel();

    private final String[] showsHeader = {"Name"};
    private final String[] episodesHeader = {"Number", "Name"};

    private int selectedShow;
    private int selectedEpisode;
    private List<String> animes;
    private List<String> thumbnails;
    private List<String> episodes;

    /**
     * This is only set to false for testing when 4anime is down
     */
    public boolean fourAnimeMode = true;

    public GUIOld() {
        final JFrame frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        showsTable.setModel(showsModel);
        episodesTable.setModel(episodesModel);

        showPanel.setLayout(new WrapLayout());

        //SEARCH SHOW
        showSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (fourAnimeMode) {
                    String searchQuery = showSearchField.getText();
                    animes = Show.search(searchQuery, false);
                    thumbnails = Show.search(searchQuery, true);
                } else {

                    String[] showList = {"nartuo 1", "mehr naruto", "aaaa naruuuto"};
                    animes = new ArrayList<>(Arrays.asList(showList));
                }

                Object[][] animeTest = new Object[animes.size()][1]; //[rows][columns]
                for (int i = 0; i < animes.size(); i++) {
                    String name = animes.get(i).replaceAll("https:\\/\\/4anime.to\\/anime\\/(.+)", "$1").replaceAll("-", " ");
                    animeTest[i][0] = name;
                    //showPanel.add(new ShowEntry(name, thumbnails.get(i), i));
                }
                showsModel.setDataVector(animeTest, showsHeader);
                frame.revalidate();
                frame.repaint();

            }
        });

        //SELECT SHOW
        showsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShow = showsTable.getSelectedRow();
                selectedShowField.setText(animes.get(selectedShow).replaceAll("https:\\/\\/4anime.to\\/anime\\/(.+)", "$1").replaceAll("-", " "));
                try {
                    BufferedImage myPicture = ImageIO.read(new URL(thumbnails.get(selectedShow)));
                    showImageLabel.setIcon(new ImageIcon(myPicture));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        });

        selectShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (fourAnimeMode) {
                    episodes = Show.getTitle(animes, selectedShow);
                } else {
                    String[] episodeList = {"prolog", "zweite episode", "hallo aa"};
                    episodes = new ArrayList<>(Arrays.asList(episodeList));
                }

                Object[][] episodesArray = new Object[episodes.size()][2]; //[rows][columns]

                for (int i = 0; i < episodes.size(); i++) {
                    episodesArray[i][1] = episodes.get(i).replaceAll("https:\\/\\/4anime.to\\/(.+)\\/\\?id=.+", "$1").replaceAll("-", " "); //replace all strich with a leerzeichen
                    episodesArray[i][0] = i + 1;
                }
                episodesModel.setDataVector(episodesArray, episodesHeader);
            }
        });

        linkOpenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = Show.getVideoURL(episodes.get(selectedEpisode));
                try {
                    Desktop.getDesktop().browse(new URL(url).toURI());
                } catch (IOException | URISyntaxException ioExceptionOrURISyntaxException){
                    ioExceptionOrURISyntaxException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new GUIOld();
    }
}