package org.example.netty.gui;

import org.example.netty.NettyClient;
import org.example.netty.entity.Cube;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class ExamplePanel extends JPanel {
    private volatile int currentX = 100;
    private volatile int currentY = 100;
    private volatile int volCount = -1;
    private static final Cube cube = new Cube();
    private final Set<Integer> keys = new HashSet<>();
    public ExamplePanel() {
        this.setPreferredSize(new Dimension(1000,650));
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

        new Timer(30, e -> {
            int packet = 0x0;
            if(keys.contains(KeyEvent.VK_W)) packet |= (1 << 0);
            if(keys.contains(KeyEvent.VK_S)) packet |= (1 << 1);
            if(keys.contains(KeyEvent.VK_A)) packet |= (1 << 2);
            if(keys.contains(KeyEvent.VK_D)) packet |= (1 << 3);
            if(packet != volCount) {
                cube.moveKey(packet);
                volCount = packet;
            }
        }).start();
        new Timer(16, e ->{
            this.repaint();
        }).start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.GREEN);
        g.fillRect(cube.getX(), cube.getY(), cube.getWidth(), cube.getHeight());
        Toolkit.getDefaultToolkit().sync();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Example Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ExamplePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new NettyClient(cube).connect("localhost", 7075);
    }
}