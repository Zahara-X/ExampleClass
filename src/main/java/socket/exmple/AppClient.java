package socket.exmple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class AppClient extends JPanel {
    private volatile int x, y, width, height;
    private DataOutputStream out;
    private DataInputStream in;
    private Set<Integer> keys = new CopyOnWriteArraySet<>();
    public AppClient(Socket socket) throws IOException {
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
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
        new Timer(16, e -> {
            if (keys.isEmpty()) return;
            // Формируем маску (1 байт)
            try {
                if (keys.contains(KeyEvent.VK_W)) out.writeInt(1 << 0);
                if (keys.contains(KeyEvent.VK_S)) out.writeInt(1 << 1);
                if (keys.contains(KeyEvent.VK_A)) out.writeInt(1 << 2);
                if (keys.contains(KeyEvent.VK_D)) out.writeInt(1 << 3);
                out.flush();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }).start();
        out.writeInt(0x2);
        out.flush();
        new Thread(() -> {
            try {
                while (true) {
                int codec = in.readInt();
                if(codec == 0x5) {
                    y = in.readInt();
                    x = in.readInt();
                    width = in.readInt();
                    height = in.readInt();
                    System.out.println(x + " " + y);
                }
                    this.repaint();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.GREEN);
      g.fillRect(x, y, width, height);
      Toolkit.getDefaultToolkit().sync();
    }
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("I need ip address :-)", 8080);
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Socket");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.add(new AppClient(socket));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}