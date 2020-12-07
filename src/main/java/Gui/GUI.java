package Gui;

import Tools.Show;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
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

    private String[] showsHeader = {"name"};

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
                String searchQuery = showSearchField.getText();
                try {
                    List<String> titles = Show.search(searchQuery);
                    for(String s : titles){
                        System.out.println(s);
                    }
                    Object[][] anime = {titles.toArray()};
                    System.out.println(Arrays.toString(anime));
                    showsModel.setDataVector(anime, showsHeader);
                } catch (IOException ignored) {
                }

            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}