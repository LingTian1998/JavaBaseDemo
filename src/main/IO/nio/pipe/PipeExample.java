package main.IO.nio.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeExample {
    public static void main(String[] args) throws Exception{
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();

        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put(newData.getBytes());

        buffer.flip();
        while (buffer.hasRemaining()){
            sinkChannel.write(buffer);
        }
        System.out.println("Writing Done!");


        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer1 = ByteBuffer.allocate(48);
        int bufferRead = sourceChannel.read(buffer1);

        if (bufferRead!=-1){
            buffer1.flip();
            while (buffer1.hasRemaining()){
                System.out.print((char) buffer1.get());
            }
            buffer1.clear();
        }
        System.out.println();
        System.out.println("Reading Done!");
    }
}
