package main.NetWork.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class FileChannelExample {
    public static void main(String[] args) throws Exception{
        //加载解码器
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();

        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\文档\\Notes\\Kafka学习.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(48);
        CharBuffer charBuffer = CharBuffer.allocate(48);

        int bytesRead = fileChannel.read(buffer);
        char[] temp = null;
        byte[] remainByte = null;
        int leftNum = 0;
        while (bytesRead != -1) {
            //System.out.println("Read" + bytesRead); 乱码输出
            buffer.flip();
            decoder.decode(buffer,charBuffer,true);
            charBuffer.flip();
            remainByte = null;
            leftNum = buffer.limit() - buffer.position();
            if (leftNum > 0){ //记录未转换的字节
                remainByte = new byte[leftNum];
                buffer.get(remainByte,0,leftNum);
            }

            //输出已转换的字符
            temp = new char[charBuffer.length()];
            while (charBuffer.hasRemaining()){
                charBuffer.get(temp);
                System.out.print(new String(temp));
            }

//            while (buffer.hasRemaining()) {
//                System.out.print((char) buffer.get());
//            }

            charBuffer.clear();
            buffer.clear();
            if(remainByte!=null){
                buffer.put(remainByte);
            }
            bytesRead = fileChannel.read(buffer);
        }
        randomAccessFile.close();
    }
}
