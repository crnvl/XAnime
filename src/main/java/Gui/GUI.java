package Gui;

import Tools.Show;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUI {
    private JTextField showSearchField;
    private JTable showsTable;
    private JButton showSearchButton;
    private JButton selectShowButton;
    private JButton linkOpenButton;
    private JPanel mainPanel;
    private JTable table1;

    private DefaultTableModel showsModel = new DefaultTableModel();

    private String[] showsHeader = {"Name"};

    public GUI() {
        JFrame frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        showsTable.setModel(showsModel);

        showSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> anime = null;

                if (false) {
                    try {
                        String searchQuery = showSearchField.getText();
                        anime = Show.search(searchQuery);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {

                    String[] showList = {"nartuo 1", "mehr naruto", "aaaa naruuuto"};
                    anime = new ArrayList<>(Arrays.asList(showList));
                }

                Object[][] animeTest = new Object[anime.size()][1]; //[rows][columns]
                for (int i = 0; i < anime.size(); i++) {
                    animeTest[i][0] = anime.get(i);
                }
                showsModel.setDataVector(animeTest, showsHeader);

            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}