package org.example.netty.gui;

import org.example.netty.NettyClient;
import org.example.netty.entity_client.GameDataClient;
import org.example.netty.entity_client.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GameClient extends JPanel {
    private static final GameDataClient gameDataClient = new Player();
    private Set<Integer> keys = new HashSet<>();
    public GameClient() {
        this.setPreferredSize(new Dimension(1000,650));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                 keys.remove(e.getKeyCode());
            }
        });
        new Timer(16, e -> {
           if(keys.contains(KeyEvent.VK_W)) gameDataClient.move((1 << 0));
           if(keys.contains(KeyEvent.VK_S)) gameDataClient.move((1 << 1));
           if(keys.contains(KeyEvent.VK_A)) gameDataClient.move((1 << 2));
           if(keys.contains(KeyEvent.VK_D)) gameDataClient.move((1 << 3));
           this.repaint();
        }).start();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRect(gameDataClient.getX(), gameDataClient.getY(),gameDataClient.getWidth(),gameDataClient.getHeight());
        Toolkit.getDefaultToolkit().sync();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Netty");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.add(new GameClient());
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
        new NettyClient(gameDataClient).connect("127.0.0.1", 7070);
    }
}