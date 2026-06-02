package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GuiCubeTest extends JPanel {
    private Set<Integer> keys = new HashSet<>();
    private int x, y, speed = 10;
    public GuiCubeTest() {
        this.setPreferredSize(new Dimension(1000,650));
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                keys.remove(e.getKeyCode());
            }
        });
        new Timer(16 , e -> {
            if(keys.contains(KeyEvent.VK_W)) y -= speed;
            if(keys.contains(KeyEvent.VK_S)) y += speed;
            if(keys.contains(KeyEvent.VK_A)) x -= speed;
            if(keys.contains(KeyEvent.VK_D)) x += speed;
            this.repaint();
        }).start();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, 100, 100);
        Toolkit.getDefaultToolkit().sync();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GuiCubeTest());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}