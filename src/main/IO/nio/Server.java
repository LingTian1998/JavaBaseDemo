package main.NetWork.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        Selector selector = Selector.open();


        serverSocket.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int nKeys = selector.select();
        SelectionKey selectionKey = null;
        if (nKeys>0){
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey key: keys){
                if (key.isAcceptable()){
                    ServerSocketChannel serversocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    if (serversocketChannel==null){
                        continue;
                    }
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);

                    int keyNum = selector.select();
                    Set<SelectionKey> tempKeys = selector.keys();
                    for (SelectionKey tempKey: tempKeys){
                        if (tempKey.isReadable()){
                            System.out.println("isReadable！");
                        }
                        if (tempKey.isWritable()){
                            System.out.println("isWritable!");
                        }
                    }
                }
            }
        }
    }
}
