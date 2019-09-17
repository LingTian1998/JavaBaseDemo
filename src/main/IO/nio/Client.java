package main.IO.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
          clientTest();
//        SocketChannel channel = SocketChannel.open();
//        channel.configureBlocking(false);
//        channel.connect(new InetSocketAddress("localhost",9999));
//
//        Selector selector = Selector.open();
//        channel.register(selector, SelectionKey.OP_CONNECT);
//
//        while (true) {
//            int nKeys = selector.select();
//            System.out.println(nKeys);
//
//            SelectionKey selectionKey = null;
//            if (nKeys > 0) {
//                Set<SelectionKey> keys = selector.keys();
//
//                for (SelectionKey key : keys) {
//                    if (key.isConnectable()) {
//                        System.out.println("连接到远程服务器");
//                        SocketChannel socketChannel = (SocketChannel) key.channel();
//                        socketChannel.configureBlocking(false);
//                        selectionKey = socketChannel.register(selector, SelectionKey.OP_WRITE);
//                    } else if (key.isWritable()) {
//                        System.out.println("写就绪！");
//                        SocketChannel socketChannel = (SocketChannel) key.channel();
//                        ByteBuffer buffer = ByteBuffer.allocate(64);
//
//                        String msg = "Hello,World!";
//                        int writtenedSize = socketChannel.write(buffer);
//                        buffer.put(msg.getBytes());
//                        socketChannel.write(buffer);
//                        System.out.println("向服务器端发送信息.");
//                        if (writtenedSize == 0) {
//                            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
//                        }
//                    }
//                }
//                selector.selectedKeys().clear();
//            }
//        }
    }

    private static String getInputString(){
        return scanner.nextLine();
    }

    public static void clientTest() throws Exception{
        int i =0;
        while (i<10) {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9999));
            socketChannel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(48);

            buffer.put("Hello,World!".getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            socketChannel.close();
            i++;
        }
    }
}
