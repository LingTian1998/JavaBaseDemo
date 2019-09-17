package main.NetWork.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private static final int port = 8080;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;
        while (true){
            socket = serverSocket.accept();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder("");
            String string = null;
            while ((string = bufferedReader.readLine())!=null){
                stringBuilder.append(string);
            }
            threadPool.execute(new Server().new Handler(stringBuilder.toString()));
        }
    }

    class Handler implements Runnable{
        public String msg = null;
        public Handler(String msg){
            this.msg = msg;
        }

        public void run() {
            System.out.println(msg);
        }
    }
}
