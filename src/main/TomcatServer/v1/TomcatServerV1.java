package main.TomcatServer.v1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServerV1 {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("---Tomcat 服务器启动成功！");

        while (!serverSocket.isClosed()){
            final Socket socket = serverSocket.accept();
            threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        System.out.println("收到请求: ");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String msg = null;
                        while ((msg = reader.readLine())!=null){
                            if (msg.length()==0)
                                break;
                            System.out.println(msg);
                        }
                        System.out.println("---------------收到请求！");
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try{
                            socket.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
