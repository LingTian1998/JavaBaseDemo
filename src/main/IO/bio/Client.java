package main.NetWork.bio;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String ip = "localhost";
    private static final int  port = 8080;
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(ip,port);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.println("Hello,World!");
        out.flush();
        socket.close();
    }
}
