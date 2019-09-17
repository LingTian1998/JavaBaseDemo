package main.NetWork.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class FileTransferExample {
    private static String fromTest = "D:\\fromTest.txt";
    private static String toTest = "D:\\toTest.txt";
    public static void transferFromTest() throws Exception{
        RandomAccessFile randomAccessFile1 = new RandomAccessFile(fromTest,"rw");
        FileChannel fileChannel1 = randomAccessFile1.getChannel();

        RandomAccessFile randomAccessFile2 = new RandomAccessFile(toTest,"rw");
        FileChannel fileChannel2 = randomAccessFile2.getChannel();

        long position = 0;
        long count = fileChannel1.size();

        fileChannel2.transferFrom(fileChannel1,position,count);

        ByteBuffer buffer = ByteBuffer.allocate(64);
        int bufferRead = fileChannel2.read(buffer);

        while (bufferRead!=-1) {
            System.out.println("Read" + bufferRead);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }

            buffer.clear();
            bufferRead = fileChannel2.read(buffer);
        }
    }

    public static void transferToTest() throws Exception{
        RandomAccessFile fromFile = new RandomAccessFile(fromTest,"rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile(toTest,"rw");
        FileChannel toChannel = toFile.getChannel();

        long position =0;
        long count = fromChannel.size();
        fromChannel.transferTo(count, position, toChannel);

        ByteBuffer buffer = ByteBuffer.allocate(64);
        int bufferRead = toChannel.read(buffer);

        while (bufferRead!=-1) {
            System.out.println("Read" + bufferRead);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }

            buffer.clear();
            bufferRead = toChannel.read(buffer);
        }
    }
    public static void main(String[] args) throws Exception{
        transferToTest();
    }
}
