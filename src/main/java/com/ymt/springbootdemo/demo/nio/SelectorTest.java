package com.ymt.springbootdemo.demo.nio;

import java.io.IOException;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by @author yangmingtian on 2019/12/17
 */
public class SelectorTest {
    public static void main(String[] args) throws IOException {

//        RandomAccessFile file = new RandomAccessFile("C:\\Users\\yangmingtian\\Desktop\\新建文本文档.txt", "rw");
//        FileChannel channel = file.getChannel();

        Selector selector = Selector.open();
        SelectableChannel channel1 = new SelectableChannel() {
            @Override
            protected void implCloseChannel() throws IOException {

            }

            @Override
            public SelectorProvider provider() {
                return null;
            }

            @Override
            public int validOps() {
                return 0;
            }

            @Override
            public boolean isRegistered() {
                return false;
            }

            @Override
            public SelectionKey keyFor(Selector sel) {
                return null;
            }

            @Override
            public SelectionKey register(Selector sel, int ops, Object att) throws ClosedChannelException {
                return null;
            }

            @Override
            public SelectableChannel configureBlocking(boolean block) throws IOException {
                return this;
            }

            @Override
            public boolean isBlocking() {
                return false;
            }

            @Override
            public Object blockingLock() {
                return null;
            }
        };

        channel1.configureBlocking(false);
        SelectionKey key = channel1.register(selector, SelectionKey.OP_READ);

        key.isConnectable();

        int interestSet = key.interestOps();

        Selector selector1 = key.selector();
        SelectableChannel channel = key.channel();


        key.attach(new Object());
        Object attachment = key.attachment();

        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey k = iterator.next();
            if (k.isAcceptable()) {

            } else if (k.isConnectable()) {

            } else if (k.isReadable()) {

            } else if (k.isWritable()) {

            }
            iterator.remove();
        }
    }
}
