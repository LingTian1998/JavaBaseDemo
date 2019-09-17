package main.IO.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        Selector selector = Selector.open();


        serverSocket.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int nKeys = selector.select();
            SelectionKey selectionKey = null;
            if (nKeys > 0) {
                Set<SelectionKey> keys = selector.keys();
                for (SelectionKey key : keys) {
                    if (key.isAcceptable()) {
                        System.out.println("Now,key is acceptable!");
                        //一般注册OP_ACCEPT的都是serversocketChannel
                        ServerSocketChannel serversocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if (socketChannel == null) {
                            continue;
                        }
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    else if (key.isReadable()){
                        System.out.println("Now, key is readable!");
                        SocketChannel socketChannel = (SocketChannel)key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(48);
                        int bufferRead = socketChannel.read(buffer);

                        while (bufferRead!=-1){
                            buffer.flip();
                            while (buffer.hasRemaining()) {
                                System.out.print((char) buffer.get());
                            }
                            buffer.clear();
                            bufferRead = socketChannel.read(buffer);
                        }
                        System.out.println();
                    }
                }
            }
        }
    }
}
