package com.yly.selector;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector 提供了一种机制，用于监视一个或多个NIO通道并识别一个或多个通道何时可用于数据传输，这样单个线程可以用于管理多个通道，从而管理多个网络连接
 */
public class EchoServer {

    private static final String POISON_PILL = "POISON_PILL";

    public static void main(String[] args) throws IOException {
        // 创建一个selector 对象,通过选择器，我们可以使用一个线程而不是多个线程来管理多个通道
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        // 在通道注册到selector 之前，它必须处于非阻塞模式
        serverSocket.configureBlocking(false);
        // 为了让选择器监视任何通道，我们必须向选择器注册这些通道
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        /**
         * Java NIO 使用面向缓冲区的模型而不是面向流的模型。因此，套接字通信通常是通过写入和读取缓冲区来进行的。
         * 因此，我们创建一个新的ByteBuffer,服务器将对其进行写入和读取。我们将其初始化为 256 字节，它只是一个任意值，具体取决于我们计划来回传输的数据量。
         */
        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            // 我们必须执行一个连续的过程来选择我们之前看到的就绪集。我们使用选择器的 select 方法进行选择，此方法会阻塞，直到至少一个通道准备好执行操作
            // 返回的整数表示通道已准备好进行操作的键的数量
            selector.select();
            /**
             * 我们获得的集合是SelectionKey对象，每个键代表一个已注册的通道，准备进行操作。
             * 之后，我们通常会迭代这个集合，对于每个键，我们获取通道并执行我们感兴趣的集合中出现的任何操作。
             * 在一个通道的生命周期内，它可能会被选择多次，因为它的键会出现在不同事件的就绪集中。这就是为什么我们必须有一个连续的循环来捕获和处理发生的通道事件。
             */
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {
        // 从 SelectionKey 对象访问正在监视的频道非常简单。我们只需调用 channel 方法
        SocketChannel client = (SocketChannel) key.channel();
        int r = client.read(buffer);
        if (r == -1 || new String(buffer.array()).trim()
                .equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        } else {
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = EchoServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}
