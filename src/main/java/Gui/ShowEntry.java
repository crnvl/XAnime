package Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ShowEntry extends JPanel {

    private BufferedImage thumbnail;
    private ImageIcon icon;
    private JTextArea textArea;
    private JLabel iconLabel;
    private JButton selectButton;

    private boolean textCropped = false;
    private int number;

    private static int width = 200;

    public ShowEntry(String text, String iconPath, final int number, final GUI gui) {
        super();

        textArea = new JTextArea(text);

        //LABEL ICON
        if(text.length() > 33){
            textCropped = true;
            textArea.setText(text.replaceAll("(?<=^.{33}).*", "..."));
            textArea.setToolTipText(text);
        }
        textArea.setEditable(false);
        selectButton = new JButton("Select Show");

        iconLabel = new JLabel();
        try {
            thumbnail = ImageIO.read(new URL(iconPath));
        } catch (IOException ignored) {
        }
        int scaledHeight = (int) ((double) thumbnail.getHeight() / (double) thumbnail.getWidth() * width);
        Image scaled = thumbnail.getScaledInstance(width, scaledHeight, Image.SCALE_DEFAULT);
        icon = new ImageIcon(scaled);
        iconLabel.setIcon(icon);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.selectShow(number);
            }
        });

        setLayout(new BorderLayout());
        add(selectButton, BorderLayout.SOUTH);
        add(textArea, BorderLayout.CENTER);
        add(iconLabel, BorderLayout.NORTH);
    }

    public int getNumber(){
        return number;
    }
}
