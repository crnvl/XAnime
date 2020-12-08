package Gui;

import Tools.Show;
import com.bulenkov.darcula.DarculaLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUI {
    private JTextField showSearchField;
    private JTable showsTable;
    private JButton showSearchButton;
    private JPanel mainPanel;
    private JTable episodesTable;
    private JPanel showPanel;
    private JScrollPane showScrollPanel;

    private DefaultTableModel showsModel = new DefaultTableModel();
    private DefaultTableModel episodesModel = new DefaultTableModel();

    private final String[] showsHeader = {"Name"};
    private final String[] episodesHeader = {"Episode Name"};

    private int selectedShow;
    private int selectedEpisode;
    private List<String> animes;
    private List<String> thumbnails;
    private List<String> episodes;

    public GUI() {

        final JFrame frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setTitle("XAnime Browser");
        frame.setVisible(true);

        //showsTable.setModel(showsModel);
        episodesTable.setModel(episodesModel);
        episodesTable.setRowHeight(50);

        showPanel.setLayout(new WrapLayout());
        showScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        showScrollPanel.getHorizontalScrollBar().setUnitIncrement(16);

        //---TEST ONLY
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10; j++) {
                showPanel.add(new ShowEntry("Nicks world", "https://cdn.discordapp.com/attachments/771679061344256000/781617130164060190/unknown.png", 0, this));
            }
            showPanel.add(new ShowEntry("janina sterben janina sterben janina sterben", "https://media.discordapp.net/attachments/771679061344256000/781617434146111548/unknown.png", 0, this));
        }

        showPanel.revalidate();
        //---TEST ONLY


        //SEARCH SHOW
        final GUI gui = this;
        showSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String searchQuery = showSearchField.getText();
                animes = Show.search(searchQuery, false);
                thumbnails = Show.search(searchQuery, true);
                if (animes.size() < 1) {
                    JOptionPane.showMessageDialog(null, searchQuery + " not found.");
                }

                Object[][] animeTest = new Object[animes.size()][1]; //[rows][columns]
                showPanel.removeAll();
                for (int i = 0; i < animes.size(); i++) {
                    String name = animes.get(i).replaceAll("https:\\/\\/4anime.to\\/anime\\/(.+)", "$1").replaceAll("-", " ");
                    animeTest[i][0] = name;
                    showPanel.add(new ShowEntry(name, thumbnails.get(i), i, gui));
                }
                showsModel.setDataVector(animeTest, showsHeader);
                showPanel.revalidate();
            }
        });

        episodesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedEpisode = episodesTable.getSelectedRow();
                String url = Show.getVideoURL(episodes.get(selectedEpisode));
                try {
                    Desktop.getDesktop().browse(new URL(url).toURI());
                } catch (IOException | URISyntaxException ioExceptionOrURISyntaxException) {
                    ioExceptionOrURISyntaxException.printStackTrace();
                }
            }
        });
    }

    private void loadEpisodes() {

        episodes = Show.getTitle(animes, selectedShow);
        Collections.reverse(episodes);
        Object[][] episodesArray = new Object[episodes.size()][1]; //[rows][columns]

        for (int i = 0; i < episodes.size(); i++) {
            episodesArray[i][0] = episodes.get(i).replaceAll("https:\\/\\/4anime.to\\/(.+)\\/\\?id=.+", "$1").replaceAll("-", " "); //replace all strich with a leerzeichen
        }
        episodesModel.setDataVector(episodesArray, episodesHeader);
    }

    public void selectShow(int show) {
        selectedShow = show;
        loadEpisodes();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new GUI();
    }
}