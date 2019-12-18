package com.ymt.springbootdemo.demo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by @author yangmingtian on 2019/12/17
 */
public class ChannelTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\yangmingtian\\Desktop\\新建文本文档.txt", "rw");
        FileChannel channel = file.getChannel();
        long position = channel.position();

        ByteBuffer buf = ByteBuffer.allocate(48);
//        buf.put((byte) 3);

        int read = channel.read(buf);
        while (read != -1) {
            System.out.println("Read: " + read);

            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }

            buf.clear();
            read = channel.read(buf);
        }


        file.close();
    }
}
