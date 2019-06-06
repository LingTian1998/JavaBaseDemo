package main.TomcatServer.v3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServerV3 {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception{
        //项目加载（xml读取）

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
                        StringBuilder requestInfo = new StringBuilder();

                        while ((msg = reader.readLine())!=null){
                            if (msg.length()==0)
                                break;
                            requestInfo.append(msg).append("\r\n");
                        }
                        System.out.println("---------------收到请求！");
                        System.out.println(requestInfo.toString());

                        //用字符串分割的方式简单解析解析http协议 --
                        String firstLine = requestInfo.toString().split("\r\n")[0];
                        String projectName = null;
                        String servletPath = null;
                        String servletName = null;
                        //Servlet servlet = null;
                        //3、url定位servlet
                        //4、调用servlet


                        //http响应结果 200
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                        outputStream.write("Hello,World".getBytes());
                        outputStream.flush();
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
