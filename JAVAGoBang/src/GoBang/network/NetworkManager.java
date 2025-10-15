package GoBang.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager {

    public interface MoveHandler {
        void onMoveReceived(int x, int y);
        void onConnectionEstablished(boolean asHost);
        void onConnectionClosed(String message);
    }

    private final Object lock = new Object();
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter writer;
    private Thread listenerThread;
    private Thread acceptThread;
    private MoveHandler handler;
    private volatile boolean shuttingDown;

    public void setMoveHandler(MoveHandler handler){
        this.handler = handler;
    }

    public synchronized void host(int port) throws IOException {
        close();
        shuttingDown = false;
        serverSocket = new ServerSocket(port);
        acceptThread = new Thread(() -> {
            try {
                Socket client = serverSocket.accept();
                setupConnection(client, true);
            } catch (IOException e) {
                if(!shuttingDown){
                    notifyConnectionClosed("無法接受連線: " + e.getMessage());
                }
            }
        }, "Gobang-AcceptThread");
        acceptThread.setDaemon(true);
        acceptThread.start();
    }

    public synchronized void connect(String host, int port) throws IOException {
        close();
        shuttingDown = false;
        Socket client = new Socket(host, port);
        setupConnection(client, false);
    }

    private void setupConnection(Socket client, boolean asHost) throws IOException {
        synchronized (lock) {
            this.socket = client;
            this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }
                serverSocket = null;
            }
        }
        startListener();
        if(handler != null){
            handler.onConnectionEstablished(asHost);
        }
    }

    private void startListener(){
        listenerThread = new Thread(() -> {
            boolean remoteClosed = false;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null){
                    String[] parts = line.split(",");
                    if(parts.length == 2){
                        try {
                            int x = Integer.parseInt(parts[0].trim());
                            int y = Integer.parseInt(parts[1].trim());
                            if(handler != null){
                                handler.onMoveReceived(x, y);
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                remoteClosed = true;
            } catch (IOException e) {
                if(!shuttingDown){
                    notifyConnectionClosed("連線發生錯誤: " + e.getMessage());
                }
            } finally {
                if(remoteClosed && !shuttingDown){
                    notifyConnectionClosed("對手已離線");
                }
                close();
            }
        }, "Gobang-NetworkListener");
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void sendMove(int x, int y){
        PrintWriter out;
        synchronized (lock) {
            out = this.writer;
        }
        if(out != null){
            out.println(x + "," + y);
        }
    }

    public synchronized boolean isConnected(){
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public void close(){
        synchronized (lock) {
            shuttingDown = true;
            if(writer != null){
                writer.close();
                writer = null;
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
                socket = null;
            }
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }
                serverSocket = null;
            }
            if(listenerThread != null){
                listenerThread.interrupt();
                listenerThread = null;
            }
            if(acceptThread != null){
                acceptThread.interrupt();
                acceptThread = null;
            }
        }
    }

    private void notifyConnectionClosed(String message){
        if(handler != null){
            handler.onConnectionClosed(message);
        }
    }
}
