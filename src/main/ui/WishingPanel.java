package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.*;

public class WishingPanel extends JPanel {
    private WishHistoryApp wh;
    private JButton button;

    public WishingPanel(WishHistoryApp wh) {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.GRAY);
        this.wh = wh;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWishHistory(g);
    }

    private void drawWishHistory(Graphics g) {

    }

    protected void createButton(JComponent parent) {
        button = new JButton("Add Wish");
    }
}
