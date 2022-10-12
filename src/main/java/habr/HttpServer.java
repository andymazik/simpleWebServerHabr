package habr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client accepted");
            new Thread(new SocketProcessor(socket)).start();
        }
    }
}
