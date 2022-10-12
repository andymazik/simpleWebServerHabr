package habr;

import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable {
    private Socket s;
    private InputStream is;
    private OutputStream os;

    public SocketProcessor(Socket s) throws IOException {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }

    @Override
    public void run() {
        try {
            readInputHeaders();
            writeResponse("<html><body><h1>Hello from Habrahabr</h1></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Client processing finished!");
    }

    private void writeResponse(String s) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        os.write(result.getBytes());
        os.flush();
    }

    private void readInputHeaders() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while (true) {
            String s = br.readLine();
            if (s == null || s.trim().length() == 0) {
                break;
            }
        }
    }


}
