package main.NetWork.nio;

import org.bson.ByteBuf;

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
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("localhost",9999));

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);

        int nKeys = selector.select();

        SelectionKey selectionKey = null;
        if (nKeys>0){
            Set<SelectionKey> keys = selector.keys();

            for (SelectionKey key: keys){
                if (key.isConnectable()){
                    System.out.println("连接到远程服务器");
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.configureBlocking(false);
                    selectionKey = socketChannel.register(selector,SelectionKey.OP_WRITE);
                    socketChannel.finishConnect();
                }
                else if (key.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(64);
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int bufferRead = socketChannel.read(buffer);

                    while (bufferRead!=-1){
                        System.out.print(buffer.get());
                    }

                    buffer.flip();
                    if (buffer!=null)
                        buffer.clear();
                }
                else if (key.isWritable()){
                    key.interestOps(key.interestOps()&(~SelectionKey.OP_WRITE));
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(64);

                    String msg = "Hello,World!";
                    int writtenedSize = socketChannel.write(buffer);
                    buffer.put(msg.getBytes());
                    socketChannel.write(buffer);
                    System.out.println("向服务器端发送信息.");
                    if (writtenedSize==0){
                        key.interestOps(key.interestOps()| SelectionKey.OP_WRITE);
                    }
                }
            }
            selector.selectedKeys().clear();
        }
    }

    private static String getInputString(){
        return scanner.nextLine();
    }
}
