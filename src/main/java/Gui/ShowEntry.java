package Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ShowEntry extends JPanel {

    BufferedImage thumbnail;
    ImageIcon icon;
    JLabel textLabel;
    JLabel iconLabel;

    private static int width = 100;

    public ShowEntry(String text, String iconPath) {
        super();

        //LABEL ICON
        textLabel = new JLabel(text);

        iconLabel = new JLabel();
        try {
            thumbnail = ImageIO.read(new URL(iconPath));
        } catch (IOException ignored) {
        }
        int scaledHeight = (int) ((double) thumbnail.getHeight() / (double) thumbnail.getWidth() * width);
        Image scaled = thumbnail.getScaledInstance(width, scaledHeight, Image.SCALE_DEFAULT);
        icon = new ImageIcon(scaled);
        iconLabel.setIcon(icon);

        setLayout(new BorderLayout());
        add(textLabel, BorderLayout.SOUTH);
        add(iconLabel, BorderLayout.CENTER);
    }
}
