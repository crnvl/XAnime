package Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowEntry extends JPanel {

    private BufferedImage thumbnail;
    private ImageIcon icon;
    private JTextArea textArea;
    private JLabel iconLabel;
    private JButton selectButton;

    private boolean textCropped = false;
    private int number;

    private static final int WIDTH = 200;
    private static final int MAX_STRING_LENGTH = 25;

    public ShowEntry(String text, String iconPath, final int number, final GUI gui) throws IOException {
        super();

        textArea = new JTextArea(text);

        //LABEL ICON
        if(text.length() > MAX_STRING_LENGTH){
            textCropped = true;
            textArea.setText(text.replaceAll("(?<=^.{"+MAX_STRING_LENGTH+"}).*", "..."));
        }

        setToolTipText(text);

        textArea.setEditable(false);
        selectButton = new JButton("Select Show");

        iconLabel = new JLabel();
        thumbnail = ImageIO.read(new URL(iconPath));

        int scaledHeight = (int) ((double) thumbnail.getHeight() / (double) thumbnail.getWidth() * WIDTH);
        Image scaled = thumbnail.getScaledInstance(WIDTH, scaledHeight, Image.SCALE_DEFAULT);
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
