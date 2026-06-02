package socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServerSocket {
    private static Player player = new Player(100,100,100,100);
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket((8080), 5000);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }
    static class Player {
        private volatile int x;
        private volatile int y;
        private volatile int width;
        private volatile int height;
        public Player(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    static class ClientHandler implements Runnable {
        private DataOutputStream out;
        private DataInputStream in;
        private Socket socket;
        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        @Override
        public void run() {
            try {
                while (true) {
                    int codec = this.in.readInt();
                    if((codec & (1 << 0)) != 0) player.y -= 10;
                    if((codec & (1 << 1)) != 0) player.y += 10;
                    if((codec & (1 << 2)) != 0) player.x -= 10;
                    if((codec & (1 << 3)) != 0) player.x += 10;
                    if(codec == 0x2) spawnPlayer();
                    spawnPlayer();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
        public void spawnPlayer() {
            try {
                this.out.writeInt(0x5);
                this.out.writeInt(player.y);
                this.out.writeInt(player.x);
                this.out.writeInt(player.width);
                this.out.writeInt(player.height);
                this.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}