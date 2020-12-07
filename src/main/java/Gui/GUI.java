package Gui;

import Tools.Show;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {
    private JTextField showSearchField;
    private JTable showsTable;
    private JButton showSearchButton;
    private JButton selectShowButton;
    private JButton linkOpenButton;
    private JPanel mainPanel;
    private JTable episodesTable;
    private JTextField selectedShowField;

    private DefaultTableModel showsModel = new DefaultTableModel();
    private DefaultTableModel episodesModel = new DefaultTableModel();

    private final String[] showsHeader = {"Name"};
    private final String[] episodesHeader = {"Number", "Name"};

    private int selectedShow;
    private int selectedEpisode;
    private List<String> animes;
    private List<String> episodes;

    public GUI() {
        JFrame frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        showsTable.setModel(showsModel);
        episodesTable.setModel(episodesModel);

        showSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (false) {
                    try {
                        String searchQuery = showSearchField.getText();
                        animes = Show.search(searchQuery);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {

                    String[] showList = {"nartuo 1", "mehr naruto", "aaaa naruuuto"};
                    animes = new ArrayList<>(Arrays.asList(showList));
                }

                Object[][] animeTest = new Object[animes.size()][1]; //[rows][columns]
                for (int i = 0; i < animes.size(); i++) {
                    animeTest[i][0] = animes.get(i);
                }
                showsModel.setDataVector(animeTest, showsHeader);

            }
        });

        showsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShow = showsTable.getSelectedRow();
                selectedShowField.setText(animes.get(selectedShow));
            }

        });

        selectShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    System.out.println("Getting show");

                    if(false){
                        try {
                            episodes = Show.getTitle(animes, selectedShow);
                        } catch (IOException ignored) {
                        }
                    } else {
                        String[] episodeList = {"prolog", "zweite episode", "hallo aa"};
                        animes = new ArrayList<>(Arrays.asList(episodeList));
                    }

                    Object[][] episodesArray = new Object[animes.size()][2]; //[rows][columns]

                    for (int i = 0; i < animes.size(); i++) {
                        episodesArray[i][0] = animes.get(i);
                        episodesArray[i][1] = i;
                    }
                    episodesModel.setDataVector(episodesArray, episodesHeader);
            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}